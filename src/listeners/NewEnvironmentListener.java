package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.TupleSpaceApp;

public class NewEnvironmentListener implements ActionListener {
	private TupleSpaceApp app;
	
	public NewEnvironmentListener(TupleSpaceApp app){
		this.app = app;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Adicionando novo ambiente");
		String s = (String)JOptionPane.showInputDialog(
                null,
                "New Environment",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
		if ((s != null) && (s.length() > 0)) {
			app.manager.createEnvironment(s);
			app.updateJavaSpace();
		}
	}

}
