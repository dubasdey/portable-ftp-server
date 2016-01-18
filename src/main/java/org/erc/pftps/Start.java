package org.erc.pftps;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.erc.pftps.services.FTPServer;

public class Start {

	public static void main(String[] args) {
		
		if(args == null || args.length<1){
	       java.awt.EventQueue.invokeLater ( new Runnable() {
		        public void run() {
		    		try{
		    			JFrame.setDefaultLookAndFeelDecorated(true);
		    			JDialog.setDefaultLookAndFeelDecorated(true);
		    			System.setProperty("sun.awt.noerasebackground", "true");
		    			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						MainWindow window = new MainWindow();
						window.setVisible(true);
					} catch (Exception e) {
						//log.error(e);
					}
		        }
	        });
		}else{
			FTPServer server = new FTPServer();
			server.setUser(args[0], args[1], args[2]);
			server.start();
		}
	}

}
