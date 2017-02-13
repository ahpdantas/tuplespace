package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.TupleSpaceApp;

public class DeleteEnvironmentListener implements ActionListener {
	private TupleSpaceApp app;
	
	public DeleteEnvironmentListener(TupleSpaceApp app){
		this.app = app;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Deletando ambiente");
		String s = (String)JOptionPane.showInputDialog(
                null,
                "Delete Environment",
                "Customized Dialog",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
		if ((s != null) && (s.length() > 0)) {
			app.manager.deleteEnvironment(s);
			app.updateJavaSpace();
		}
	}

}
