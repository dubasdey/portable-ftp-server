/*
    This file is part of PortableFtpServer.

    PortableFtpServer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    PortableFtpServer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with PortableFtpServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.erc.pftps;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * The Class MessageConsole.
 */
public class MessageConsole {
	
	/** The text component. */
	private final JTextComponent textComponent;
	
	/** The document. */
	private final Document document;
	
	/** The limit lines listener. */
	private DocumentListener limitLinesListener;

	/**
	 * Instantiates a new message console.
	 *
	 * @param textComponent the text component
	 */
	public MessageConsole(JTextComponent textComponent){
		this.textComponent = textComponent;
		this.document = textComponent.getDocument();
		textComponent.setEditable(false);
	}

	/**
	 * Redirect out.
	 */
	public void redirectOut(){
		System.setOut( new PrintStream(new ConsoleOutputStream(Color.LIGHT_GRAY), true));
	}

	/**
	 * Redirect err.
	 */
	public void redirectErr(){	
		System.setErr( new PrintStream(new ConsoleOutputStream(Color.red), true));
		
	}

	/**
	 * Sets the message lines.
	 *
	 * @param lines the new message lines
	 */
	public void setMessageLines(int lines) {
		if (limitLinesListener != null){
			document.removeDocumentListener( limitLinesListener );
		}
		limitLinesListener = new LimitLinesDocumentListener(lines);
		document.addDocumentListener( limitLinesListener );
	}

	/**
	 * The listener interface for receiving limitLinesDocument events.
	 * The class that is interested in processing a limitLinesDocument
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addLimitLinesDocumentListener<code> method. When
	 * the limitLinesDocument event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see LimitLinesDocumentEvent
	 */
	class LimitLinesDocumentListener implements DocumentListener {
		
		/** The maximum lines. */
		private final int maximumLines;

		/**
		 * Instantiates a new limit lines document listener.
		 *
		 * @param maximumLines the maximum lines
		 */
		public LimitLinesDocumentListener(int maximumLines) {
			this.maximumLines = maximumLines;
		}

		/* (non-Javadoc)
		 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
		 */
                @Override
                public void insertUpdate(final DocumentEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            removeLines(e);
                        }
                    });
                }

		/* (non-Javadoc)
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
                @Override
		public void removeUpdate(DocumentEvent e) {}
		
		/* (non-Javadoc)
		 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
		 */
                @Override
		public void changedUpdate(DocumentEvent e) {}

		/**
		 * Removes the lines.
		 *
		 * @param e the e
		 */
		private void removeLines(DocumentEvent e) {
			Document document = e.getDocument();
			Element root = document.getDefaultRootElement();
			while (root.getElementCount() > maximumLines) {
				Element line = root.getElement(0);
				int end = line.getEndOffset();
				try {
					document.remove(0, end);
				} catch(BadLocationException ble) {
					//System.out.println(ble);
				}
			}
		}
	}
	
	/**
	 * The Class ConsoleOutputStream.
	 */
	class ConsoleOutputStream extends ByteArrayOutputStream {

		/** The eol. */
		private final String EOL = System.getProperty("line.separator");
		
		/** The attributes. */
		private SimpleAttributeSet attributes;
		
		/** The buffer. */
		private final StringBuffer buffer = new StringBuffer(80);
		
		/** The is first line. */
		private boolean isFirstLine;

		/**
		 * Instantiates a new console output stream.
		 *
		 * @param textColor the text color
		 * @param printStream the print stream
		 */
		public ConsoleOutputStream(Color textColor) {
			if (textColor != null) {
				attributes = new SimpleAttributeSet();
				StyleConstants.setForeground(attributes, textColor);
			}
			isFirstLine = true;
		}

		/* (non-Javadoc)
		 * @see java.io.OutputStream#flush()
		 */
                @Override
		public void flush() {
			String message = toString();
			if (message.length() == 0) return;
			handleAppend(message);
			reset();
		}

		/**
		 * Handle append.
		 *
		 * @param message the message
		 */
		private void handleAppend(String message){
			if (document.getLength() == 0){
				buffer.setLength(0);
			}
			if (EOL.equals(message)){
				buffer.append(message);
			} else {
				buffer.append(message);
				clearBuffer();
			}
		}

		/**
		 * Clear buffer.
		 */
		private void clearBuffer() {
			if (isFirstLine && document.getLength() != 0) {
			    buffer.insert(0, "\n");
			}
			isFirstLine = false;
			String line = buffer.toString();
			try {
				int offset = document.getLength();
				document.insertString(offset, line, attributes);
				textComponent.setCaretPosition( document.getLength() );
			} catch (BadLocationException ble) {}
			buffer.setLength(0);
		}
	}
}