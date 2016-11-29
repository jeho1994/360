package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class SkillGUI extends JPanel {
	
	
	private String[] mySkillColumnNames = { "uwnetid", "fName", "mName", "lName", "email"};
	
	public SkillGUI() {
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
