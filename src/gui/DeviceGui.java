package gui;

import logic.Device;

public class DeviceGui extends javax.swing.tree.DefaultMutableTreeNode {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeviceGui(Device dev){
		this.userObject = dev;
	}
}
