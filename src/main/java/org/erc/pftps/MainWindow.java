package org.erc.pftps;


import javax.swing.JFrame;


public class MainWindow extends JFrame{

	private static final long serialVersionUID = 3422060991230203001L;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	
	private void initialize() {
		//setTitle(Messages.getString("ExplorerWindow.title")); //$NON-NLS-1$
		//setIconImage(Toolkit.getDefaultToolkit().getImage(ExplorerWindow.class.getResource(Images.SEARCH))); //$NON-NLS-1$
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
