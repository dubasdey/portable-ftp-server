package org.erc.pftps;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Start {

	public static void main(String[] args) {
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
	}

}
