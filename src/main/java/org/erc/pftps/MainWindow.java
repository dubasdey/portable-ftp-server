/*
    This file is part of PortableFtpServer.

    PortableFtpServer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with PortableFtpServer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.erc.pftps;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JFormattedTextField;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.SwingConstants;

import org.erc.pftps.services.FTPServer;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;


public class MainWindow extends JFrame{

	private static final long serialVersionUID = 3422060991230203001L;
	private JTextField txtUser;
	private JTextField txtPassword;
	private JTextField txtFolder;
	private JFormattedTextField txtPort;
	private JTextArea txtLog;
	private JButton btStartStop;
	
	private MessageConsole messageConsole;
	
	private FTPServer ftpServer;
	private JScrollPane scrollPane;
	
	/**
	 * Create the application.
	 */
	public MainWindow() {

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Portable FTP Server");
		setBounds(10, 10, 530, 340);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y);
	    
	    
		getContentPane().setLayout(null);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(122, 46, 46, 14);
		getContentPane().add(lblUser);
		
		txtUser = new JTextField();
		txtUser.setBounds(178, 43, 86, 20);
		getContentPane().add(txtUser);
		txtUser.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(274, 46, 46, 14);
		getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(330, 43, 86, 20);
		getContentPane().add(txtPassword);
		
		txtFolder = new JTextField();
		txtFolder.setEditable(false);
		txtFolder.setBounds(66, 12, 404, 20);
		getContentPane().add(txtFolder);
		txtFolder.setColumns(10);
		
		JLabel lblFolder = new JLabel("Folder");
		lblFolder.setBounds(10, 15, 46, 14);
		getContentPane().add(lblFolder);
		
		btStartStop = new JButton("...");
		btStartStop.setBounds(480, 11, 33, 23);
		getContentPane().add(btStartStop);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(10, 46, 46, 14);
		getContentPane().add(lblPort);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ftpServer.setUser(txtUser.getText(), txtPassword.getText(), txtFolder.getText());
				ftpServer.start();
			}
		});
		btnStart.setBounds(426, 42, 89, 23);
		getContentPane().add(btnStart);
		
		txtPort = new JFormattedTextField();
		txtPort.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPort.setText("21");
		txtPort.setColumns(5);
		txtPort.setBounds(66, 43, 46, 20);
		getContentPane().add(txtPort);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 503, 227);
		getContentPane().add(scrollPane);

		txtLog = new JTextArea();
		scrollPane.setViewportView(txtLog);
		txtLog.setText("Ready.");
		txtLog.setForeground(Color.LIGHT_GRAY);
		txtLog.setBackground(Color.BLACK);
		txtLog.setFont(new Font("Monospaced", Font.PLAIN, 10));
		txtLog.setColumns(20);
		txtLog.setRows(20);
		txtLog.setEditable(false);
		
		messageConsole = new MessageConsole(txtLog);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{getContentPane(), lblFolder, btStartStop, lblPort, txtPort, lblUser, txtUser, lblPassword, txtPassword, btnStart}));
		messageConsole.setMessageLines(100);
		messageConsole.redirectErr();
		messageConsole.redirectOut();
		
		ftpServer = new FTPServer();
		
		
	}
}
