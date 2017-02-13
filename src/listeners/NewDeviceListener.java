package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import app.TupleSpaceApp;

public class NewDeviceListener implements ActionListener {
	private TupleSpaceApp app;
	
	public NewDeviceListener(TupleSpaceApp app){
		this.app = app;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Adicionando novo dispositivo");
		
		JTextField dev = new JTextField();
		JTextField env = new JTextField();
		Object[] message = {
		    "Device:", dev,
		    "Environment:", env
		};

		int option = JOptionPane.showConfirmDialog(null, message, "New Device", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			if( (dev.getText() != null) && (dev.getText().length() > 0)){
				if( (env.getText() != null) && (env.getText().length() > 0) ){
					this.app.manager.createDevice(dev.getText(), env.getText());
					this.app.updateJavaSpace();
				}
			}
		} else {
		    System.out.println("New device canceled");
		}
		
		
	}

}
