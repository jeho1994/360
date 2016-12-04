package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import component.HintTextField;
import data.DegreeDB;
import model.Degree;

/**
 * ProgramGUI lists the available Institute of Technology programs and
 * allows users to add new programs.
 * @author Thomas Van Riper
 * December 3rd, 2016
 *
 */
public class ProgramGUI extends JPanel implements ActionListener, TableModelListener {
	/**
	 * An auto generated serial version UID.
	 */
	private static final long serialVersionUID = 8529565249406455688L;
	
	/**The range of degrees.*/
	private static final String[] DEGREES = {"BA", "BS", "MA", "MS", "PhD"}; 
	
	/**HintTextField fields.*/
	private HintTextField txfProgramName;
	
	/**JButton fields.*/
	private JButton btnAdd, btnAddProgram, btnList;
	
	/**JComboBox fields.*/
	private JComboBox<String> cmbLevel;
	
	/**JLabel fields.*/
	private JLabel lblWarning;
	
	/**JPanel fields.*/
	private JPanel pnlAdd, pnlButtons, pnlContent, pnlList;
	
	/**A scroll pane to list the programs.*/
	private JScrollPane scrollPane;
	
	/**A table to list the programs offered.*/
	private JTable tblList;
	
	/**
	 * Constructs the program GUI by calling methods to create
	 * components.
	 */
	public ProgramGUI() {
		setLayout(new BorderLayout());
		
		createComponents();
		setVisible(true);
		setSize(500, 700);
	}
	
	/**
	 * Adds a new program to the list of available degree offerings.
	 */
	private void addProgram() {
		String name = txfProgramName.getText().trim();
		if (name.isEmpty()) {
			lblWarning.setText("Please enter a program name.");
			return;
		}
		
		Degree degree = new Degree(name, cmbLevel.getSelectedItem().toString());
		DegreeDB.addDegree(degree);
		lblWarning.setText("");
		btnList.doClick();
	}
	
	/**
	 * Creates the GUI components.
	 */
	private void createComponents() {
		pnlContent = new JPanel();
		pnlContent.add(createListPanel());
		
		lblWarning = new JLabel();
		lblWarning.setForeground(Color.RED);
		lblWarning.setFont(new Font("Arial", Font.ITALIC, 12));
		
		add(createButtonPanel(), BorderLayout.NORTH);
		add(pnlContent, BorderLayout.CENTER);
		add(lblWarning, BorderLayout.SOUTH);
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
		
		String[] columnNames = {"Program", "Degree"};
		tblList = new JTable(getData(), columnNames);
		tblList.getModel().addTableModelListener(this);
		scrollPane = new JScrollPane(tblList);
		pnlList.add(scrollPane);
		
		return pnlList;
	}
	
	/**
	 * Retrieves the program data from the Degree database.
	 */
	private Object[][] getData() {
		List<Degree> degrees = null;
		try {
			degrees = DegreeDB.getDegrees();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Object[][] data = new Object[degrees.size()][2];
		if (degrees != null) {
			for (int i = 0; i < degrees.size(); i++) {
				data[i][0] = degrees.get(i).getProgram();
				data[i][1] = degrees.get(i).getLevel();
			}
		}
		
		return data;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd) {
			lblWarning.setText("");
			pnlContent.removeAll();
			pnlContent.add(createAddPanel());
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnList) {
			lblWarning.setText("");
			pnlContent.removeAll();
			pnlContent.add(createListPanel());
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAddProgram) {
			addProgram();
		}
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		//The programs are not editable, so refresh if anyone tries to
		//edit the list. Don't give them false hope.
		btnList.doClick();
	}
}
