package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class EmploymentGUI extends JPanel {
	
	
	private String[] myEmploymentColumnNames = {"Employment ID", "Employer"};
	
	public EmploymentGUI() {
		setLayout(new BorderLayout());
//		myList = getData(null); // TODO after conncting DB
		createComponents();
		setVisible(true);
		setSize(500, 700);
	}

	private void createComponents() {
		// TODO Auto-generated method stub
		
	}

}
