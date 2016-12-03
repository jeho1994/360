package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import data.DegreeDB;
import data.SkillDB;
import data.StudentDegreeDB;
import data.StudentEmploymentDB;
import data.StudentInternshipDB;
import data.StudentSkillDB;
import model.Degree;
import model.Student;
import model.StudentCollection;
import model.StudentDegree;
import model.StudentInternship;

public class StudentGUI extends JPanel implements ActionListener, TableModelListener  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3475519788254348975L;
	
	/**
	 * The various quarters a student can graduate.
	 */
	private static final String[] TERMS = {"Summer", "Fall", "Winter", "Spring"};
	
	/* Fields */
	private JPanel pnlContent, pnlButtons, pnlSearch, pnlAdd, pnlView;
	private JButton btnSearch, btnAdd, btnView, btnSearchStudent, btnAddStudent, btnViewStudent, btnEditEmail, btnAddDegree, btnAddSkill, btnAddIntern, btnAddEmploy;
		
	/**AddPanel text fields.*/
	private HintTextField txfFirst, txfGPA, txfMiddle, txfLast, txfEmail, txfUWNetID, txtfViewUWID;
	
	/** A table for displaying Students */
	private JTable table, stuDegreeTable, stuSkillTable, stuEmployTemble, stuInternTable;
	
	/** A scroll pane */
	private JScrollPane scrollPane, viewSPane, stuDegreeSPane, stuSkillSPane, stuEmploySPane, stuInternSPane;
	
	/** A label for Search Item panel */
	private JLabel lblSearch;
	
	/** A text field for Search Item panel */
	private JTextField txfSearch;
	
	/** A drop down box for the different degree options.*/
	private JComboBox<Object> cmbDegree;
	
	/**A drop down box for the graduation term and year.*/
	private JComboBox<Object> cmbTerm, cmbYear;
	
	/**A warning for different invalid inputs for the add student panel.*/
	private JLabel lblWarning;
	
	private String[] myStudentColumnNames = { "uwnetid", "fName", "mName", "lName", "email"};
	private List<Student> myList;
	private Object[][] myData;
	
	
	
	
	/* constuctor */
	
	public StudentGUI() {
		setLayout(new BorderLayout());
		myList = getData(null);
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

		btnView = new JButton("View/Update Student");
		btnView.addActionListener(this);

		pnlButtons.add(btnSearch);
		pnlButtons.add(btnAdd);
		pnlButtons.add(btnView);
		
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
		pnlAdd.setLayout(new GridLayout(7, 0));
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
		txfUWNetID = new HintTextField("UWNetID");
		
		JPanel pnlDegree = new JPanel();
		pnlDegree.setLayout(new GridLayout(1, 0));
//		DegreeDB degree = new DegreeDB();
		List<Degree> degrees = null;
		try {
			degrees = DegreeDB.getDegrees();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (degrees != null) {
			cmbDegree = new JComboBox<Object>(degrees.toArray());
		}
		pnlDegree.add(new JLabel("Program"));
		pnlDegree.add(cmbDegree);
		
		JPanel pnlGPA = new JPanel();
		txfGPA = new HintTextField("GPA");
		txfGPA.setColumns(5);
		pnlGPA.add(new JLabel("Grade Point Average"));
		pnlGPA.add(txfGPA);
		
		JPanel pnlGraduation = new JPanel();
		pnlGraduation.add(new JLabel("Anticipated Graduation"));
		cmbTerm = new JComboBox<Object>(TERMS);
		Date theDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Integer[] years = new Integer[10];
		years[0] = Integer.parseInt(formatter.format(theDate));
		for (int i = 1; i < 10; i++) {
			years[i] = years[0] + i;
		}
		cmbYear = new JComboBox<Object>(years);
		pnlGraduation.add(cmbTerm);
		pnlGraduation.add(cmbYear);
		
		JPanel pnlButton = new JPanel();
		btnAddStudent = new JButton("Add");
		btnAddStudent.addActionListener(this);
		pnlButton.add(btnAddStudent);
				
		pnlAdd.add(pnlName);
		pnlAdd.add(txfEmail);
		pnlAdd.add(txfUWNetID);
		pnlAdd.add(pnlDegree);
		pnlAdd.add(pnlGPA);
		pnlAdd.add(pnlGraduation);
		pnlAdd.add(pnlButton);
		
		return pnlAdd;
	}
	
	private JPanel createViewPanel() {
		pnlView = new JPanel();
		txtfViewUWID = new HintTextField("UWnetID");
		txtfViewUWID.setColumns(10);
		btnViewStudent = new JButton("View");
		btnViewStudent.addActionListener(this);
		pnlView.add(txtfViewUWID);
		pnlView.add(btnViewStudent);
		
		return pnlView;
	}
	
	
	private JPanel createStudentViewPanel(final String theId) {
		JPanel panel  = new JPanel(new BorderLayout());
		Student student = StudentCollection.getStudentById(theId);
		
		// student personal info
		JPanel upperPanel = new JPanel(new BorderLayout());
		JPanel basicPanel  = new JPanel(new GridLayout(3, 3));

		basicPanel.add(new JLabel("UWnetID: "));
		basicPanel.add(new JLabel(student.getUWNetID()));
		basicPanel.add(new JLabel(""));
		
		String fname = student.getFirstName();
		String mname = student.getMiddleName();
		String lname = student.getLastName();
		
		basicPanel.add(new JLabel("Name: "));
		if (mname != null) {
			basicPanel.add(new JLabel(lname.toUpperCase() + ", " + fname + " " + mname));
		} else {
			basicPanel.add(new JLabel(lname.toUpperCase() + ", " + fname));
		}
		basicPanel.add(new JLabel(""));
		
		basicPanel.add(new JLabel("E-mail: "));
		basicPanel.add(new JLabel(student.getEmail()));

		btnEditEmail = new JButton("Update E-mail");
		btnEditEmail.addActionListener(this);
		basicPanel.add(btnEditEmail);
		
		upperPanel.add(basicPanel, BorderLayout.CENTER);

		// tables panel
		JPanel tablePanel = new JPanel(new GridLayout(5,0));
		
		// student degree table
		JPanel degreePanel = new JPanel(new BorderLayout());
		String[] degreeColumn = {"Degree Level", "Program Name", "GPA", "Graudation Term", "Graduation Year", "Transfer From"};
		int countDegrees = 0;
		Object[][] degrees = null;
		try {
			countDegrees = StudentDegreeDB.getStudentDegreeOfUWNetID(student.getUWNetID()).size();
			degrees = new Object[countDegrees][degreeColumn.length];
			for (int i = 0; i < countDegrees; i++) {
				StudentDegree sd = StudentDegreeDB.getStudentDegreeOfUWNetID(student.getUWNetID()).get(i);
				degrees[i][0] = DegreeDB.getDegree(sd.getDegreeId()).getLevel();
				degrees[i][1] = DegreeDB.getDegree(sd.getDegreeId()).getProgram();
				degrees[i][2] = sd.getGPA();
				degrees[i][3] = sd.getGraduationTerm();
				degrees[i][4] = sd.getGraduationYear();
				if (sd.getTransferCollege() != null) {
					degrees[i][5] = sd.getTransferCollege();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuDegreeTable = new JTable(degrees, degreeColumn);
		stuDegreeSPane = new JScrollPane(stuDegreeTable);
		btnAddDegree = new JButton("Add Degree");
		btnAddDegree.addActionListener(this);
		degreePanel.add(stuDegreeSPane, BorderLayout.CENTER);
		degreePanel.add(btnAddDegree, BorderLayout.EAST);
		
		
		// student skill table
		JPanel skillPanel = new JPanel(new BorderLayout());
		String[] skillColumn = {"Skill"};
		int countSkills = 0;
		Object[][] skills = null;
		
		try {
			countSkills = StudentSkillDB.getStudentSkills().size();
			skills = new Object[countSkills][skillColumn.length];
			for (int i = 0; i < countSkills; i++) {
				String skillId = StudentSkillDB.getStudentSkills().get(i).getSkillId();
				skills[i][0] = SkillDB.getSkillByID(skillId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuSkillTable = new JTable(skills, skillColumn);
		stuSkillSPane = new JScrollPane(stuSkillTable);
		btnAddSkill = new JButton("Add Skill");
		btnAddSkill.addActionListener(this);
		skillPanel.add(stuSkillSPane, BorderLayout.CENTER);
		skillPanel.add(btnAddSkill, BorderLayout.EAST);
		
		// student internship table
		JPanel internPanel = new JPanel(new BorderLayout());
		String[] internColumn = {"Employer", "Position", "Date From", "Date To"};
		int countinterns = 0;
		Object[][] interns = null;
		try {
			countinterns = StudentInternshipDB.getInternships().size();
			interns = new Object[countinterns][internColumn.length];
			for (int i = 0; i < countinterns; i++) {
				StudentInternship si = StudentInternshipDB.getInternships().get(i);
				interns[i][0] = si.getEmployer();
				interns[i][1] = si.getPosition();
				interns[i][2] = si.getStartDate();
				interns[i][3] = si.getEndDate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuInternTable = new JTable(interns, internColumn);
		stuInternSPane = new JScrollPane(stuInternTable);
		btnAddIntern = new JButton("Add Internship");
		btnAddIntern.addActionListener(this);
		internPanel.add(stuInternSPane, BorderLayout.CENTER);
		internPanel.add(btnAddIntern, BorderLayout.EAST);
		
		// student employment table
		JPanel employPanel = new JPanel(new BorderLayout());
		String[] employColumn = {"Employer", "Position", "Salary", "Date From", "Comment"};
		int countemploys = 0;
		Object[][] employs = null;
		try {
			countinterns = StudentEmploymentDB.getStudentEmployments().size();
			interns = new Object[countinterns][internColumn.length];
			for (int i = 0; i < countinterns; i++) {
				//TODO
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuInternTable = new JTable(interns, internColumn);
		stuInternSPane = new JScrollPane(stuInternTable);
		btnAddIntern = new JButton("Add Internship");
		btnAddIntern.addActionListener(this);
		internPanel.add(stuInternSPane, BorderLayout.CENTER);
		internPanel.add(btnAddIntern, BorderLayout.EAST);
		

		// add all tables in vieSpne
		
		viewSPane = new JScrollPane(tablePanel);
		panel.add(upperPanel, BorderLayout.NORTH);
		panel.add(viewSPane, BorderLayout.CENTER);
		return panel;
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
				if (myList.get(i).getEmail() != null) {
					myData[i][4] = myList.get(i).getEmail();
				} else {
					myData[i][4] = "";
				}
			}
		}
		return myList;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch) {
			lblWarning.setText("");
			pnlSearch = createSearchPanel();
			pnlContent.removeAll();
			pnlContent.add(pnlSearch);
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnAdd) {
			lblWarning.setText("");
			pnlContent.removeAll();
			pnlContent.add(createAddPanel());
			pnlContent.revalidate();
			this.repaint();
		} else if (e.getSource() == btnView) {
			lblWarning.setText("");
			pnlContent.removeAll();
			pnlContent.add(createViewPanel());
			pnlContent.revalidate();
			this.repaint();
			
		} else if (e.getSource() == btnSearchStudent) {
			performSearchStudent();
		} else if (e.getSource() == btnAddStudent) {
			performAddStudent();
		} else if (e.getSource() == btnViewStudent) {
			performViewStudent();
		} else if (e.getSource() == btnEditEmail) {
			performEditEmail();
		} else if (e.getSource() == btnAddDegree) {
			//TODO
		} else if (e.getSource() == btnAddSkill) {
			//TODO
		} else if (e.getSource() == btnAddIntern) {
			//TODO
		} else if (e.getSource() == btnAddEmploy) {
			//TODO
		}
	}
	
	private void performEditEmail() {
		
	}


	private void performViewStudent() {
		// get textfield
		String id = txtfViewUWID.getText();
		System.out.println(id);
		if (id.length() > 0) { 
			myList = getData(id);
			pnlContent.removeAll();
			pnlContent.add(createStudentViewPanel(id));
			pnlContent.revalidate();
			this.repaint();
		}
		
	}


	private void performAddStudent() {
		String first = txfFirst.getText().trim();
		String middle = txfMiddle.getText().trim();
		String last = txfLast.getText().trim();
		String email = txfEmail.getText().trim();
		String uwNetID = txfUWNetID.getText().trim();
		String gpa = txfGPA.getText().trim();
		double dblGPA = 0.0;
		
		boolean validFirst = first.matches("^[\\p{L} .'-]+$");
		boolean validLast = last.matches("^[\\p{L} .'-]+$");
		
		if (first.length() < 2 || last.length() < 2 || !validFirst || !validLast) {
			lblWarning.setText("Please enter a valid student first and last name. " +
					"First and last name cannot be blank, must be at least two characters, " +
					"and can only contain letters.");
			return;
		}
		
		if (!middle.isEmpty() && (!middle.matches("^[\\p{L} .'-]+$") || middle.length() < 2)) {
			lblWarning.setText("Please enter a valid student middle name. Middle name "
					+ "can only contain letters and must be at least two characters.");
			return;
		}
		
		if (!email.isEmpty() && (!email.contains("@") || !email.contains("."))) {
			lblWarning.setText("Please enter a valid e-mail address.");
			return;
		}
		
		if (uwNetID.isEmpty()) {
			lblWarning.setText("Please enter a valid UWNetID.");
			return;
		}
		
		boolean uwNetIDIsTaken = false;
		List<Student> studentList = StudentCollection.getStudents();
		for (Student student : studentList)
		{
			if (uwNetID.equals(student.getUWNetID()))
			{
				uwNetIDIsTaken = true;
				break;
			}
		}
		
		if (uwNetIDIsTaken)
		{
			lblWarning.setText("That UW NetID is taken. Please enter a valid UW NetID.");
			return;
		}
		
		try {
			dblGPA = Double.parseDouble(gpa);
		} catch (NumberFormatException e) {
			lblWarning.setText("Please enter a valid GPA. GPA should be entered as a non "
					+ "negative decimal.");
			return;
		}
		
		if (dblGPA < 0 || dblGPA > 4.0) {
			lblWarning.setText("Please enter a valid GPA. GPA should be entered as a non "
					+ "negative decimal and cannot exceed 4.0.");
			return;
		}
		
		Degree degree = (Degree) cmbDegree.getSelectedItem();
		String term = (String) cmbTerm.getSelectedItem();
		String year = Integer.toString((int)cmbYear.getSelectedItem());
		StudentDegree studentDegree = new StudentDegree(uwNetID, degree.getId(), term, year,
				dblGPA);
		
		lblWarning.setText("");
		Student student = null;
		if (middle.isEmpty())
		{
			student = new Student(first, last, uwNetID);
		}
		else
		{
			student = new Student(first, middle, last, uwNetID);
		}
		
		if (!email.isEmpty())
		{
			student.setEmail(email);
		}
		
		student.setDegree(studentDegree);
		StudentDegreeDB.addStudentDegree(studentDegree);
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
