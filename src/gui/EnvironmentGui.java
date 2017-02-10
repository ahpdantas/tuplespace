package gui;

import logic.Device;
import logic.Environment;


public class EnvironmentGui extends javax.swing.tree.DefaultMutableTreeNode{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EnvironmentGui(Environment env){
		this.userObject = env;
		for( Device d: ((Environment) this.userObject).getDevices() ){
			this.add(new DeviceGui(d));
		}
	}
}
