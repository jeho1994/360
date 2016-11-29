package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class ProgramGUI extends JPanel {
	
	
	private String[] myProgramColumnNames = {"Skill ID", "Skill Name"};
	
	public ProgramGUI() {
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
