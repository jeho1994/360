package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

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
	
	/** A table for displaying Students */
	private JTable table;
	
	/** A scroll pane */
	private JScrollPane scrollPane;
	
	/** A label for Seach Item panel */
	private JLabel lblSearch;;
	
	/** A text field for Seach Item panel */
	private JTextField txfSearch;
	
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
		
		add(createSearchPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.NORTH);	
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
		
		//TODO - Thomas adds "Add student" panel
		
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
			//TODO - Thomas
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
		// TODO Thomas adds
		
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