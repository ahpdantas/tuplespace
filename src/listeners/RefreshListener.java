package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import app.TupleSpaceApp;

public class RefreshListener implements ActionListener {
	private TupleSpaceApp app;
	
	public RefreshListener(TupleSpaceApp app){
		this.app = app;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Atualizando lista de dispositivos");
		this.app.updateJavaSpace();
	}

}
