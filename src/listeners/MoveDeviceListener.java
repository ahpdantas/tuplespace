package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import app.TupleSpaceApp;

public class MoveDeviceListener implements ActionListener {
	private TupleSpaceApp app;
	
	public MoveDeviceListener(TupleSpaceApp app){
		this.app = app;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Movendo dispositivo");
		JTextField dev = new JTextField();
		JTextField env = new JTextField();
		Object[] message = {
		    "Device:", dev,
		    "Environment:", env
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Move Device", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			if( (dev.getText() != null) && (dev.getText().length() > 0)){
				if( (env.getText() != null) && (env.getText().length() > 0) ){
					this.app.manager.moveDevice(dev.getText(), env.getText());
					this.app.updateJavaSpace();
				}
			}
		} else {
		    System.out.println("Move device canceled");
		}
	}

}
