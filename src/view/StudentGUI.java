package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import component.HintTextField;
import model.Student;
import model.StudentCollection;

public class StudentGUI extends JPanel implements ActionListener, TableModelListener  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3475519788254348975L;
	
	/* Fields */
	private JPanel pnlContent, pnlButtons, pnlSearch, pnlAdd, pnlEdit;
	private JButton btnSearch, btnAdd, btnEdit, btnSearchStudent, btnAddStudent, btnEditStudent;
		
	/**AddPanel text fields.*/
	private HintTextField txfFirst, txfMiddle, txfLast, txfEmail, txfStudentNumber, txfUWNetID;
	
	/** A table for displaying Students */
	private JTable table;
	
	/** A scroll pane */
	private JScrollPane scrollPane;
	
	/** A label for Search Item panel */
	private JLabel lblSearch;;
	
	/** A text field for Search Item panel */
	private JTextField txfSearch;
	
	/** A dropdown box for the different degree options.*/
	private JComboBox<String> cmbDegree;
	
	/**A warning for different invalid inputs for the add student panel.*/
	private JLabel lblWarning;
	
	private String[] myStudentColumnNames = { "uwnetid", "fName", "mName", "lName", "email"};
	private List<Student> myList;
	private Object[][] myData;
	
	
	
	
	/* constuctor */
	
	public StudentGUI() {
		setLayout(new BorderLayout());
//		myList = getData(null); // TODO after conncting DB
		createComponents();
		setVisible(true);
		setSize(500, 700);
		
	}
	
	
	/* Create components */
	
	
	private void createComponents() {
		
		pnlContent = new JPanel();
		pnlContent.add(createSearchPanel());
		lblWarning = new JLabel();
		lblWarning.setForeground(Color.RED);
		lblWarning.setFont(new Font("Arial", Font.ITALIC, 12));
		add(pnlContent, BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.NORTH);	
		add(lblWarning, BorderLayout.SOUTH);
	}
	
	
	private JPanel createButtonPanel() {
		// A button panel at the top for list, search, add
		pnlButtons = new JPanel();

		// buttons at the top
		btnSearch = new JButton("Student Search");
		btnSearch.addActionListener(this);

		btnAdd = new JButton("Add Student");
		btnAdd.addActionListener(this);
		
		btnEdit = new JButton("Edit Student");
		btnEdit.addActionListener(this);

		pnlButtons.add(btnSearch);
		pnlButtons.add(btnAdd);
		pnlButtons.add(btnEdit);
		
		return pnlButtons;
	}
	
	private JPanel createSearchPanel() {
		pnlSearch = new JPanel();
		lblSearch = new JLabel("Enter first or last name: ");
		txfSearch = new JTextField(25);
		btnSearchStudent = new JButton("Search");
		btnSearchStudent.addActionListener(this);
		pnlSearch.add(lblSearch);
		pnlSearch.add(txfSearch);
		pnlSearch.add(btnSearchStudent);
		return pnlSearch;
	}
	
	private JPanel createAddPanel() {
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(5, 0));
		JPanel pnlName = new JPanel();
		pnlName.setLayout(new GridLayout(1, 0));
		txfFirst = new HintTextField("First Name");	
		txfFirst.setColumns(8);
		txfMiddle = new HintTextField("Middle Name");
		txfMiddle.setColumns(8);
		txfLast = new HintTextField("Last Name");
		txfLast.setColumns(8);
		pnlName.add(txfFirst);
		pnlName.add(txfMiddle);
		pnlName.add(txfLast);
		
		txfEmail = new HintTextField("E-mail");
		
		JPanel pnlInfo = new JPanel();
		pnlInfo.setLayout(new GridLayout(1, 0));
		txfStudentNumber = new HintTextField("Student ID Number");
		txfUWNetID = new HintTextField("UWNetID");
		
		pnlInfo.add(txfStudentNumber);
		pnlInfo.add(txfUWNetID);
		
		JPanel pnlDegree = new JPanel();
		pnlDegree.setLayout(new GridLayout(1, 0));
		cmbDegree = new JComboBox<String>();
		pnlDegree.add(new JLabel("Program"));
		pnlDegree.add(cmbDegree);
		
		
		JPanel pnlButton = new JPanel();
		btnAddStudent = new JButton("Add");
		btnAddStudent.addActionListener(this);
		pnlButton.add(btnAddStudent);
				
		pnlAdd.add(pnlName);
		pnlAdd.add(txfEmail);
		pnlAdd.add(pnlInfo);
		pnlAdd.add(pnlDegree);
		pnlAdd.add(pnlButton);
		
		return pnlAdd;
	}
	
	private JPanel createEditPanel() {
		pnlEdit = new JPanel();
		
		//TODO - Louis adds "Edit student" panel
		
		return pnlEdit;
	}
	
	
	
	private List<Student> getData(final String theSearchKey) {
		if (theSearchKey != null)
			myList = StudentCollection.search(theSearchKey);
		else
			myList = StudentCollection.getStudents();

		if (myList != null) {
			myData = new Object[myList.size()][myStudentColumnNames.length];
			for (int i = 0; i < myList.size(); i++) {
				myData[i][0] = myList.get(i).getUWNetID();
				myData[i][1] = myList.get(i).getFirstName();
				myData[i][2] = myList.get(i).getMiddleName();
				myData[i][3] = myList.get(i).getLastName();
				myData[i][4] = myList.get(i).getEmail();
			}
		}
		return myList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch) {
			pnlSearch = createSearchPanel();
			pnlContent.removeAll();
			pnlContent.add(pnlSearch);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAdd) {
			pnlContent.removeAll();
			pnlContent.add(createAddPanel());
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnEdit) {
			//TODO - Louis
		} else if (e.getSource() == btnSearchStudent) {
			performSearchStudent();
		} else if (e.getSource() == btnAddStudent) {
			performAddStudent();
		} else if (e.getSource() == btnEditStudent) {
			performEditStudent();
		}
		
	}


	private void performEditStudent() {
		// TODO - Louis adds 
		
	}


	private void performAddStudent() {
		String first = txfFirst.getText().trim();
		String middle = txfMiddle.getText().trim();
		String last = txfLast.getText().trim();
		String email = txfEmail.getText().trim();
		String studentNumber = txfStudentNumber.getText();
		String uwNetID = txfUWNetID.getText().trim();
		int intStudentNumber = -1;
		
		boolean validFirst = first.matches("^[\\p{L} .'-]+$");
		boolean validLast = last.matches("^[\\p{L} .'-]+$");
		
		if (first.length() < 2 || last.length() < 2 || !validFirst || !validLast)
		{
			lblWarning.setText("Please enter a valid student first and last name. " +
					"First and last namme cannot be blank, must be at least two characters, " +
					"and can only contain letters.");
			return;
		}
		
		if (!middle.isEmpty() && !middle.matches("^[\\p{L} .'-]+$") || middle.length() < 2)
		{
			lblWarning.setText("Please enter a valid student middle name. Middle name "
					+ "can only contain letters and must be at least two characters.");
			return;
		}
		
		if (!email.contains("@") || !email.contains("."))
		{
			lblWarning.setText("Please enter a valid e-mail address.");
			return;
		}
		
		try
		{
			intStudentNumber = Integer.parseInt(studentNumber);
		}
		catch (NumberFormatException e)
		{
			lblWarning.setText("Please enter a valid student ID number. Student ID numbers must be " +
					"seven digits long and contain only numbers.");
			return;
		}
		if (studentNumber.length() != 7 || intStudentNumber < 0)
		{
			lblWarning.setText("Please enter a valid student ID number. Student ID numbers must be " +
					"seven digits long and contain only numbers.");
			return;
		}
		
		if (uwNetID.isEmpty())
		{
			lblWarning.setText("Please enter a valid UWNetID.");
			return;
		}
		
		Student student = new Student(first, middle, last, email, intStudentNumber,
				uwNetID);
		StudentCollection.add(student);
		btnSearch.doClick();
	}


	private void performSearchStudent() {
		String searchKey = txfSearch.getText();
		if (searchKey.length() > 0) {
			myList = getData(searchKey);
			pnlContent.removeAll();
			table = new JTable(myData, myStudentColumnNames);
			table.getModel().addTableModelListener(this);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			this.repaint();
			txfSearch.setText("");
		}
	}


	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}
}
