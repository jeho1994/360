package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import component.HintTextField;

public class ProgramGUI extends JPanel implements ActionListener {
	
	
	/**
	 * An auto generated serial version UID.
	 */
	private static final long serialVersionUID = 8529565249406455688L;
	
	private static final String[] DEGREES = {"BA", "BS", "MA", "MS", "PhD"}; 

	private String[] myProgramColumnNames = {"Skill ID", "Skill Name"};
	
	/**HintTextField fields.*/
	private HintTextField txfProgramName;
	
	/**JButton fields.*/
	private JButton btnAdd, btnAddProgram, btnList;
	
	/**JComboBox fields.*/
	private JComboBox<String> cmbLevel;
	
	/**JPanel fields.*/
	private JPanel pnlAdd, pnlButtons, pnlContent, pnlList;
	
	public ProgramGUI() {
		setLayout(new BorderLayout());
//		myList = getData(null); // TODO after conncting DB
		createComponents();
		setVisible(true);
		setSize(500, 700);
	}
	
	private void createComponents() {
		pnlContent = new JPanel();
		
		add(createButtonPanel(), BorderLayout.NORTH);
		add(pnlContent, BorderLayout.CENTER);
	}
	
	/**
	 * Creates a panel to add a new program.
	 * @return pnlAdd
	 */
	private JPanel createAddPanel() {
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(2, 0));
		
		JPanel pnlInfo = new JPanel();
		txfProgramName = new HintTextField("Program Name");
		txfProgramName.setColumns(20);
		cmbLevel = new JComboBox<String>(DEGREES);
		pnlInfo.add(txfProgramName);
		pnlInfo.add(new JLabel("Degree"));
		pnlInfo.add(cmbLevel);
		
		JPanel pnlButton = new JPanel();
		btnAddProgram = new JButton("Add");
		btnAddProgram.addActionListener(this);
		pnlButton.add(btnAddProgram);
		
		pnlAdd.add(pnlInfo);
		pnlAdd.add(pnlButton);
		
		return pnlAdd;
	}

	/**
	 * Creates a button panel to navigate to view or add a program.
	 * @return pnlButtons
	 */
	private JPanel createButtonPanel() {
		pnlButtons = new JPanel();
		
		btnList = new JButton("Program List");
		btnList.addActionListener(this);
		
		btnAdd = new JButton("Add Program");
		btnAdd.addActionListener(this);
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnAdd);
		
		return pnlButtons;
	}
	
	/**
	 * Creates a panel that lists the available programs.
	 * @return pnlList
	 */
	private JPanel createListPanel() {
		pnlList = new JPanel();
		
		return pnlList;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnAdd) {
			pnlContent.removeAll();
			pnlContent.add(createAddPanel());
			pnlContent.revalidate();
			this.repaint();
		}
		
	}
}
