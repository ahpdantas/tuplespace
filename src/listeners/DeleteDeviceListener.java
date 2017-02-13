package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.TupleSpaceApp;

public class DeleteDeviceListener implements ActionListener {
	private TupleSpaceApp app;
	
	public DeleteDeviceListener(TupleSpaceApp app){
		this.app = app;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Deletando dispositivo");
		String s = (String)JOptionPane.showInputDialog(
                null,
                "Delete Device",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
		if ((s != null) && (s.length() > 0)) {
			app.manager.deleteDevice(s);
			app.updateJavaSpace();
		}
	}

}
