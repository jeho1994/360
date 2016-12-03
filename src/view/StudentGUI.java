package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import component.HintTextField;
import data.DegreeDB;
import data.SkillDB;
import data.StudentDegreeDB;
import data.StudentEmploymentDB;
import data.StudentInternshipDB;
import data.StudentSkillDB;
import model.Degree;
import model.Skill;
import model.Student;
import model.StudentCollection;
import model.StudentDegree;
import model.StudentEmployment;
import model.StudentInternship;
import model.StudentSkill;

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
	private JTable table, stuDegreeTable, stuSkillTable, stuEmployTable, stuInternTable;
	
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
	private List<Student> myStudentList;
	private List<StudentDegree> myStudentDegree;
	private List<Skill> myAllSkills;
	private List<StudentSkill> myStudentSkills;
	private List<String> mySkillIds;
	private Object[][] myData;
	private Student myViewStudent;
	private String myViewStudentUWnetId;
	
	
	
	
	/* constuctor */
	
	public StudentGUI() {
		setLayout(new BorderLayout());
		myStudentList = getData(null);
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
		myViewStudent = StudentCollection.getStudentById(theId);
		

		// student personal info
		JPanel basicPanel  = new JPanel(new GridLayout(3, 5));

		basicPanel.add(new JLabel("UWnetID: "));
		basicPanel.add(new JLabel(myViewStudent.getUWNetID()));
		basicPanel.add(new JLabel(""));
		basicPanel.add(new JLabel(""));
		basicPanel.add(new JLabel(""));
		
		String fname = myViewStudent.getFirstName();
		String mname = myViewStudent.getMiddleName();
		String lname = myViewStudent.getLastName();
		
		basicPanel.add(new JLabel("Name: "));
		if (mname != null) {
			basicPanel.add(new JLabel(lname.toUpperCase() + ", " + fname + " " + mname));
		} else {
			basicPanel.add(new JLabel(lname.toUpperCase() + ", " + fname));
		}
		basicPanel.add(new JLabel(""));
		basicPanel.add(new JLabel(""));
		basicPanel.add(new JLabel(""));
		
		basicPanel.add(new JLabel("E-mail: "));
		basicPanel.add(new JLabel(myViewStudent.getEmail()));

		btnEditEmail = new JButton("Update E-mail");
		btnEditEmail.addActionListener(this);
		basicPanel.add(btnEditEmail);
		
		// tables panel
		JPanel tablePanel = new JPanel(new GridLayout(5, 0));
		
		// student degree table
	
		JPanel degreePanel = new JPanel(new BorderLayout());
		String[] degreeColumn = {"Degree Level", "Program Name", "GPA", "Graudation Term", "Graduation Year", "Transfer From"};
		int countDegrees = 0;
		Object[][] degrees = null;
		try {
			myStudentDegree = StudentDegreeDB.getStudentDegrees();
			countDegrees = StudentDegreeDB.getStudentDegreeOfUWNetID(myViewStudentUWnetId).size();
			degrees = new Object[countDegrees][degreeColumn.length];
			for (int i = 0; i < countDegrees; i++) {
				StudentDegree sd = StudentDegreeDB.getStudentDegreeOfUWNetID(myViewStudentUWnetId).get(i);
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
		stuDegreeTable.getModel().addTableModelListener(this);
		stuDegreeSPane = new JScrollPane(stuDegreeTable);
		Dimension d = stuDegreeTable.getPreferredSize();
		stuDegreeSPane.setPreferredSize(new Dimension(d.width * 2 , stuDegreeTable.getRowHeight() * (stuDegreeTable.getRowCount() + 6)));
		btnAddDegree = new JButton("Add Degree");
		btnAddDegree.addActionListener(this);
		JPanel degreeLabelPanel = new JPanel(new BorderLayout());
		JLabel degreeLabel = new JLabel(" DEGREES (To update GPA, Graduation year and term, double click the cell to be updated)");
		degreeLabel.setForeground(Color.WHITE);
		degreeLabel.setFont(MainGUI.UW_TITLE_FONT);
		degreeLabelPanel.add(degreeLabel, BorderLayout.WEST);
		degreeLabelPanel.add(btnAddDegree, BorderLayout.EAST);
		degreeLabelPanel.setBackground(MainGUI.UW_PURPLE);
		degreeLabelPanel.setForeground(Color.WHITE);
	
		degreePanel.add(degreeLabelPanel, BorderLayout.NORTH);
		degreePanel.add(stuDegreeSPane, BorderLayout.CENTER);
		
		
		
		// student skill table
		JPanel skillPanel = new JPanel(new BorderLayout());
		String[] skillColumn = {"Skill"};
		int countSkills = 0;
		Object[][] skills = null;
		mySkillIds =  new ArrayList<String>();
		try {
			countSkills = StudentSkillDB.getStudentSKillsOfUWNetID(myViewStudentUWnetId).size();
			skills = new Object[countSkills][skillColumn.length];
			for (int i = 0; i < countSkills; i++) {
				String skillId = StudentSkillDB.getStudentSKillsOfUWNetID(myViewStudentUWnetId).get(i).getSkillId();
				skills[i][0] = SkillDB.getSkillByID(skillId);
				mySkillIds.add(skillId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuSkillTable = new JTable(skills, skillColumn);
		stuSkillSPane = new JScrollPane(stuSkillTable);
		stuSkillSPane.setPreferredSize(new Dimension(d.width + (d.width/2) , stuSkillTable.getRowHeight() * (stuSkillTable.getRowCount() + 6)));
		
		btnAddSkill = new JButton("Add Skill");
		btnAddSkill.addActionListener(this);
		
		JPanel skillLabelPanel = new JPanel(new BorderLayout());
		JLabel skillLabel = new JLabel(" SKILLS ");
		skillLabel.setForeground(Color.WHITE);
		skillLabel.setFont(MainGUI.UW_TITLE_FONT);
		skillLabelPanel.add(skillLabel, BorderLayout.WEST);
		skillLabelPanel.add(btnAddSkill, BorderLayout.EAST);
		skillLabelPanel.setBackground(MainGUI.UW_PURPLE);
		skillPanel.add(stuSkillSPane, BorderLayout.CENTER);
		skillPanel.add(skillLabelPanel, BorderLayout.NORTH);
		
		
		// student internship table
		JPanel internPanel = new JPanel(new BorderLayout());
		String[] internColumn = {"Employer", "Position", "Date From", "Date To"};
		int countinterns = 0;
		Object[][] interns = null;
		try {
			countinterns = StudentInternshipDB.getInternshipsOfUWNetID(myViewStudentUWnetId).size();
			interns = new Object[countinterns][internColumn.length];
			for (int i = 0; i < countinterns; i++) {
				StudentInternship si = StudentInternshipDB.getInternshipsOfUWNetID(myViewStudentUWnetId).get(i);
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
		stuInternSPane.setPreferredSize(new Dimension(d.width + (d.width/2) , stuInternTable.getRowHeight() * (stuInternTable.getRowCount() + 5)));
		btnAddIntern = new JButton("Add Internship");
		btnAddIntern.addActionListener(this);
		
		JPanel internLabelPanel = new JPanel(new BorderLayout());
		JLabel internLabel = new JLabel(" INTERNSHIPS ");
		internLabel.setForeground(Color.WHITE);
		internLabel.setFont(MainGUI.UW_TITLE_FONT);
		internLabelPanel.add(internLabel, BorderLayout.WEST);
		internLabelPanel.add(btnAddIntern, BorderLayout.EAST);
		internLabelPanel.setBackground(MainGUI.UW_PURPLE);
		internPanel.add(stuInternSPane, BorderLayout.CENTER);
		internPanel.add(internLabelPanel, BorderLayout.NORTH);
		
		// student employment table
		JPanel employPanel = new JPanel(new BorderLayout());
		String[] employColumn = {"Employer", "Position", "Salary", "Date From", "Comment"};
		int countemploys = 0;
		Object[][] employs = null;
		try {
			countemploys = StudentEmploymentDB.getStudentEmploymentsOfUWNetID(myViewStudentUWnetId).size();
			employs = new Object[countemploys][employColumn.length];
			for (int i = 0; i < countemploys; i++) {
				StudentEmployment se = StudentEmploymentDB.getStudentEmploymentsOfUWNetID(myViewStudentUWnetId).get(i);
					employs[i][0] = se.getEmployer();
					employs[i][1] = se.getPosition();
					employs[i][2] = se.getSalary();
					employs[i][3] = se.getStartDate().toString();
					employs[i][4] = se.getComment();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuEmployTable = new JTable(employs, employColumn);
		stuEmploySPane = new JScrollPane(stuEmployTable);
		stuEmploySPane.setPreferredSize(new Dimension(d.width + (d.width/2) , stuEmployTable.getRowHeight() * (stuEmployTable.getRowCount() + 5)));
		btnAddEmploy = new JButton("Add Employment");
		btnAddEmploy.addActionListener(this);
		
		JPanel employLabelPanel = new JPanel(new BorderLayout());
		JLabel employLabel = new JLabel(" EMPLOYMENTS ");
		employLabel.setForeground(Color.WHITE);
		employLabel.setFont(MainGUI.UW_TITLE_FONT);
		employLabelPanel.add(employLabel, BorderLayout.WEST);
		employLabelPanel.add(btnAddEmploy, BorderLayout.EAST);
		employLabelPanel.setBackground(MainGUI.UW_PURPLE);
		employPanel.add(employLabelPanel, BorderLayout.NORTH);
		employPanel.add(stuEmploySPane, BorderLayout.CENTER);
		

		// add all tables in viewSPane
//		tablePanel = new JScrollPane(tablePanel);
		tablePanel.add(degreePanel);
		tablePanel.add(skillPanel);
		tablePanel.add(internPanel);
		tablePanel.add(employPanel);
//		
		
		
		panel.add(basicPanel, BorderLayout.NORTH);
		panel.add(tablePanel, BorderLayout.CENTER);
		return panel;
	}
	

	private JPanel createAddDegreeOptionPanel() {
		JPanel panel  = new JPanel(new BorderLayout());
		JPanel pnlDegree = new JPanel();
		pnlDegree.setLayout(new GridLayout(1, 0));
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
		
		panel.add(pnlDegree, BorderLayout.NORTH);
		panel.add(pnlGraduation, BorderLayout.CENTER);
		panel.add(pnlGPA, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel createAddInternOptionPanel() {
		JPanel panel  = new JPanel(new BorderLayout());
		//TODO -jieun
		return panel;
	}
	
	private JPanel createAddEmployOptionPanel() {
		JPanel panel  = new JPanel(new BorderLayout());
		//TODO -jieun
		return panel;
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
			myViewStudentUWnetId = txtfViewUWID.getText();
			performViewStudent(myViewStudentUWnetId);
		} else if (e.getSource() == btnEditEmail) {
			String email = JOptionPane.showInputDialog(new JFrame(), "Enter e-mail address", null);
			if (StudentCollection.updateEmail(myViewStudent, email)) {
				JOptionPane.showMessageDialog(null, "E-mail is updated successfully!");
				myStudentList = getData(null);
				performViewStudent(myViewStudentUWnetId);
			}
		} else if (e.getSource() == btnAddDegree) {
			performAddDegree();
		} else if (e.getSource() == btnAddSkill) {
			performAddSkill();
		} else if (e.getSource() == btnAddIntern) {
			performAddIntern();
		} else if (e.getSource() == btnAddEmploy) {
			performAddEmploy();
		}

	}
	
	private void performAddEmploy() {
		//TODO - jieun
	}
	
	private void performAddIntern() {
		//TODO -jieun
	}

	
	private void performAddSkill() {

		JPanel panel = new JPanel();
		
		try {
			myAllSkills = SkillDB.getSkills();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JCheckBox checkbox;
		for (Skill s: myAllSkills) {
			checkbox = new JCheckBox(s.getSkillName());
			panel.add(checkbox);
		}
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Select Skills", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			//TODO - add skill check box
		}
	}
	
	private void performAddDegree() {
		int result = JOptionPane.showConfirmDialog(null, createAddDegreeOptionPanel(), "Add Degree", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String gpa = txfGPA.getText().trim();
			double dblGPA = 0.0;
			
			try {
				dblGPA = Double.parseDouble(gpa);
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null, "GPA should be between non-negative number");
				return;
			}
			
			if (dblGPA < 0 || dblGPA > 4.0) {
				JOptionPane.showMessageDialog(null, "GPA should be between 0.0 and 4.0");
				return;
			}
			
			Degree degree = (Degree) cmbDegree.getSelectedItem();
			String term = (String) cmbTerm.getSelectedItem();
			String year = Integer.toString((int)cmbYear.getSelectedItem());
			StudentDegree studentDegree = new StudentDegree(myViewStudentUWnetId, degree.getId(), term, year,
					dblGPA);
			
			if (!StudentDegreeDB.addStudentDegree(studentDegree).startsWith("Updated StudentDegree Successfully")) {
				JOptionPane.showMessageDialog(null, "Update failed");
			} else {
				JOptionPane.showMessageDialog(null, "Update Successfully");
			}
			
			myStudentList = getData(null);
			performViewStudent(myViewStudentUWnetId);
		}
	}


	private void performViewStudent(final String theId) {
		// get textfield
		if (theId.length() > 0) { 
			myStudentList = getData(theId);
			pnlContent.removeAll();
			pnlContent.add(createStudentViewPanel(theId));
			pnlContent.revalidate();
			this.repaint();
		}
		txtfViewUWID.setText("");
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
			myStudentList = getData(searchKey);
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
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel) e.getSource();
		String columnName = model.getColumnName(column);
		Object data = model.getValueAt(row, column);
		if (data != null && ((String) data).length() != 0) {
			StudentDegree sd = myStudentDegree.get(row);
			if (columnName.equalsIgnoreCase("GPA") 
					|| columnName.equalsIgnoreCase("graduation_term") 
					|| columnName.equalsIgnoreCase("graduation_year")) {
				
				if (!StudentDegreeDB.updateStudentDegree(sd, columnName, data).startsWith("Updated StudentDegree Successfully")) {
					JOptionPane.showMessageDialog(null, "Update failed");
				}

			}
		}
	}
	
	
	/*
	 * Get student data
	 */
	
	private List<Student> getData(final String theSearchKey) {
		if (theSearchKey != null)
			myStudentList = StudentCollection.search(theSearchKey);
		else
			myStudentList = StudentCollection.getStudents();

		if (myStudentList != null) {
			myData = new Object[myStudentList.size()][myStudentColumnNames.length];
			for (int i = 0; i < myStudentList.size(); i++) {
				myData[i][0] = myStudentList.get(i).getUWNetID();
				myData[i][1] = myStudentList.get(i).getFirstName();
				myData[i][2] = myStudentList.get(i).getMiddleName();
				myData[i][3] = myStudentList.get(i).getLastName();
				if (myStudentList.get(i).getEmail() != null) {
					myData[i][4] = myStudentList.get(i).getEmail();
				} else {
					myData[i][4] = "";
				}
			}
		}
		return myStudentList;
	}
	
	
}
