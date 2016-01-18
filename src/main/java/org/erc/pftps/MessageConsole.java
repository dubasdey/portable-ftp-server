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

public class MessageConsole {
	
	private JTextComponent textComponent;
	private Document document;
	
	private DocumentListener limitLinesListener;

	public MessageConsole(JTextComponent textComponent){
		this.textComponent = textComponent;
		this.document = textComponent.getDocument();
		textComponent.setEditable(false);
	}

	public void redirectOut(){
		ConsoleOutputStream cos = new ConsoleOutputStream(Color.LIGHT_GRAY, null);
		System.setOut( new PrintStream(cos, true));
	}

	public void redirectErr(){	
		ConsoleOutputStream cos = new ConsoleOutputStream(Color.red, null);
		System.setErr( new PrintStream(cos, true) );
		
	}

	public void setMessageLines(int lines) {
		if (limitLinesListener != null){
			document.removeDocumentListener( limitLinesListener );
		}
		limitLinesListener = new LimitLinesDocumentListener(lines);
		document.addDocumentListener( limitLinesListener );
	}

	class LimitLinesDocumentListener implements DocumentListener {
		
		private int maximumLines;

		public LimitLinesDocumentListener(int maximumLines) {
			this.maximumLines = maximumLines;
		}

		public void insertUpdate(final DocumentEvent e) {
			SwingUtilities.invokeLater( new Runnable() { public void run() { removeLines(e); } });
		}

		public void removeUpdate(DocumentEvent e) {}
		public void changedUpdate(DocumentEvent e) {}

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
	
	class ConsoleOutputStream extends ByteArrayOutputStream {

		private final String EOL = System.getProperty("line.separator");
		private SimpleAttributeSet attributes;
		private PrintStream printStream;
		private StringBuffer buffer = new StringBuffer(80);
		private boolean isFirstLine;

		public ConsoleOutputStream(Color textColor, PrintStream printStream) {
			if (textColor != null) {
				attributes = new SimpleAttributeSet();
				StyleConstants.setForeground(attributes, textColor);
			}
			this.printStream = printStream;
			isFirstLine = true;
		}

		public void flush() {
			String message = toString();
			if (message.length() == 0) return;
			handleAppend(message);
			reset();
		}

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
			}catch (BadLocationException ble) {}

			if (printStream != null) {
				printStream.print(line);
			}
			buffer.setLength(0);
		}
	}
}