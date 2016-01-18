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
