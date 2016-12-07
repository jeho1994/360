package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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

/**
 * A Panel that contains all the Student related functionality to list the
 * students, search the students, add a new student, modify values within the
 * student, and view all inforamtion for a student.
 * 
 * @author Jieun Lee: (View panel) View student by UWnetId / Add Student Degree
 *         / Edit Student Degree / Add Student Employment / ADD Student Skill /
 *         ADD Student Internship / Edit Email
 * @author Thomas Van Riper (Add panel)
 * @author Louis Yang (Search panel)
 * @version 12-06-2016
 *
 */
public class StudentGUI extends JPanel implements ActionListener {
	
	/** A Date format */
	public static final SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("MM-yyyy");

	/** A serial number for StudentGUI() */
	private static final long serialVersionUID = 3475519788254348975L;

	/**  The various quarters a student can graduate.*/
	private static final String[] TERMS = { "Summer", "Fall", "Winter", "Spring" };

	/** Panels */
	private JPanel pnlContent, pnlButtons, pnlSearch, pnlAdd, pnlView;
	
	/** Buttons */
	private JButton btnSearch, btnAdd, btnView, btnSearchStudent, btnAddStudent, btnViewStudent, btnEditEmail,
			btnAddDegree, btnEditDegree, btnAddSkill, btnAddIntern, btnAddEmploy;

	/** AddPanel text fields. */
	private HintTextField txfFirst, txfGPA, txfMiddle, txfLast, txfEmail, txfUWNetID, txtfViewUWID, txtfTransfer,
			txtfEmpEmployer, txtfEmpPosition, txtfEmpSalary, txtfEmpComment, txtfIntEmployer, txtfIntPosition, txfSearch;

	/** A table for displaying Students */
	private JTable table, stuDegreeTable, stuSkillTable, stuEmployTable, stuInternTable;

	/** A scroll pane */
	private JScrollPane scrollPane, stuDegreeSPane, stuSkillSPane, stuEmploySPane, stuInternSPane;

	/** A drop down box for the different degree options. */
	private JComboBox<Object> cmbDegree, cmbTerm, cmbYear, cmbEmpFromM, cmbEmpFromY, cmbIntToM, cmbIntToY, cmbIntFromM,
			cmbIntFromY, cmbStudentDegree;

	/** A warning for different invalid inputs for the add student panel. */
	private JLabel lblWarning;

	/** Check boxes for Skills */
	private JCheckBox[] ckbSkill;
	
	/** Column names for stuent search table*/
	private String[] myStudentColumnNames = { "uwnetid", "fName", "mName", "lName", "email" };
	
	/** A list of Students */
	private List<Student> myStudentList;
	
	/** A list of StudentDegrees*/
	private List<StudentDegree> myStudentDegree;
	
	/** A list of StudentSkills*/
	private List<StudentSkill> myStudentSkills;
	
	/** A list of StudentInternships */
	private List<StudentInternship> myStudentInters;
	
	/** A list of StudentEmployments */
	private List<StudentEmployment> myStudentEmployemnt;
	
	/** A list of Skill ids */
	private List<String> mySkillIds;
	
	/** A student data*/
	private Object[][] myData;
	
	/** A StudentDegree data*/
	private Object[] myStudentDegreeData;
	
	/** A student for view panel */
	private Student myViewStudent;
	
	/** A Student's UW net id for view panel*/
	private String myViewStudentUWnetId;
	
	/** Flags */
	private boolean hasEmployment, isGpaSelected;

	/* constuctor */

	/**
	 * Consturcts StudentGUI
	 */
	public StudentGUI() {
		setLayout(new BorderLayout());
		myStudentList = getStudentData(null);
		createComponents();
		setVisible(true);
		setSize(500, 700);

	}

	/* Create components */
	/**
	 * Create components
	 */
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

	/**
	 * Create button panels on top
	 * @return A button panel
	 */
	private JPanel createButtonPanel() {
		pnlButtons = new JPanel();

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

	/**
	 * Create a searching panel
	 * @return A Search panel
	 */
	private JPanel createSearchPanel() {
		pnlSearch = new JPanel();
		txfSearch = new HintTextField("Enter First or Last Name");
		txfSearch.setColumns(25);
		btnSearchStudent = new JButton("Search");
		btnSearchStudent.addActionListener(this);
		
		pnlSearch.add(txfSearch);
		pnlSearch.add(btnSearchStudent);
		return pnlSearch;
	}

	/**
	 * Create a Add panel
	 * @return A Add panel
	 */
	private JPanel createAddPanel() {
		pnlAdd = new JPanel(new BorderLayout());
		JPanel panel = new JPanel(new GridLayout(8, 0));
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

		txtfTransfer = new HintTextField("Transfer College");
		txtfTransfer.setColumns(25);

		pnlDegree.add(new JLabel("Transfer College"));
		pnlDegree.add(txtfTransfer);

		JPanel pnlButton = new JPanel();
		btnAddStudent = new JButton("Add");
		btnAddStudent.addActionListener(this);
		pnlButton.add(btnAddStudent);
		
		JPanel titlePane = new JPanel();
		titlePane.setBackground(MainGUI.UW_PURPLE);
		JLabel titleLabel = new JLabel("Add Student");
		titleLabel.setFont(MainGUI.UW_BIG_FONT);
		titleLabel.setForeground(Color.WHITE);
		titlePane.add(titleLabel);
		
		panel.add(titlePane);
		panel.add(pnlName);
		panel.add(txfEmail);
		panel.add(txfUWNetID);
		panel.add(pnlDegree);
		panel.add(createGPAPanel());
		panel.add(createGraudationDatePanel());
		panel.add(pnlButton);

		pnlAdd.add(panel, BorderLayout.CENTER);
		return pnlAdd;
	}

	/**
	 * Create a View Search panel
	 * @return A View Search panel
	 */
	private JPanel createViewPanel() {
		pnlView = new JPanel();
		myViewStudentUWnetId = null;
		txtfViewUWID = new HintTextField("UWnetID");
		txtfViewUWID.setColumns(25);
		btnViewStudent = new JButton("View");
		btnViewStudent.addActionListener(this);
		pnlView.add(txtfViewUWID);
		pnlView.add(btnViewStudent);

		return pnlView;
	}

	/**
	 * Create a View Student panel
	 * @param theId The student UW net Id
	 * @return A View Student panel
	 */
	private JPanel createStudentViewPanel(final String theId) {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel basicPanel = new JPanel(new GridLayout(3, 5));
		basicPanel.add(new JLabel("UWnetID: "));
		basicPanel.add(new JLabel(theId));
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
		String[] degreeColumn = { "Degree Level", "Program Name", "GPA", "Graudation Term", "Graduation Year",
				"Transfer From" };
		int countDegrees = 0;
		Object[][] degrees = null;
		try {
			myStudentDegree = StudentDegreeDB.getStudentDegreeOfUWNetID(myViewStudentUWnetId);
			countDegrees = myStudentDegree.size();
			degrees = new Object[countDegrees][degreeColumn.length];
			for (int i = 0; i < countDegrees; i++) {
				StudentDegree sd = myStudentDegree.get(i);
				degrees[i][0] = DegreeDB.getDegree(sd.getDegreeId()).getLevel();
				degrees[i][1] = DegreeDB.getDegree(sd.getDegreeId()).getProgram();
				degrees[i][2] = sd.getGPA();
				degrees[i][3] = sd.getGraduationTerm();
				degrees[i][4] = sd.getGraduationYear();
				degrees[i][5] = sd.getTransferCollege();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuDegreeTable = new JTable(degrees, degreeColumn);
		stuDegreeTable.setEnabled(false);
		stuDegreeSPane = new JScrollPane(stuDegreeTable);
		Dimension d = stuDegreeTable.getPreferredSize();
		stuDegreeSPane.setPreferredSize(new Dimension(d.width * 2, stuDegreeTable.getRowHeight() * (6)));
		btnAddDegree = new JButton("Add Degree");
		btnAddDegree.addActionListener(this);
		btnEditDegree = new JButton("Edit Degree");
		btnEditDegree.addActionListener(this);

		final JPanel degreeLabelPanel = new JPanel(new GridLayout(0, 8));
		final JLabel degreeLabel = new JLabel(" DEGREES (" + stuDegreeTable.getRowCount() + ")");
		degreeLabel.setForeground(Color.WHITE);
		degreeLabel.setFont(MainGUI.UW_TITLE_FONT);
		degreeLabelPanel.add(degreeLabel);

		final JPanel degreeBtnPanel = new JPanel();
		degreeBtnPanel.setBackground(MainGUI.UW_PURPLE);
		degreeBtnPanel.add(btnAddDegree);
		degreeBtnPanel.add(btnEditDegree);

		for (int i = 0; i < 5; i++) {
			degreeLabelPanel.add(new JLabel());
		}

		degreeLabelPanel.add(btnAddDegree);
		degreeLabelPanel.add(btnEditDegree);
		degreeLabelPanel.setBackground(MainGUI.UW_PURPLE);
		degreeLabelPanel.setForeground(Color.WHITE);

		degreePanel.add(degreeLabelPanel, BorderLayout.NORTH);
		degreePanel.add(stuDegreeSPane, BorderLayout.CENTER);

		// student skill table
		JPanel skillPanel = new JPanel(new BorderLayout());
		String[] skillColumn = { "Skill" };
		int countSkills = 0;
		Object[][] skills = null;
		mySkillIds = new ArrayList<String>();

		try {
			myStudentSkills = StudentSkillDB.getStudentSKillsOfUWNetID(myViewStudentUWnetId);
			countSkills = myStudentSkills.size();
			skills = new Object[countSkills][skillColumn.length];
			for (int i = 0; i < countSkills; i++) {
				String skillId = myStudentSkills.get(i).getSkillId();
				Skill skill = SkillDB.getSkillByID(skillId);
				skills[i][0] = skill.getSkillName();
				mySkillIds.add(skillId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuSkillTable = new JTable(skills, skillColumn);
		stuSkillTable.setEnabled(false);
		stuSkillSPane = new JScrollPane(stuSkillTable);
		stuSkillSPane.setPreferredSize(new Dimension(d.width + (d.width / 2), stuSkillTable.getRowHeight() * (6)));

		btnAddSkill = new JButton("Add Skill");
		btnAddSkill.addActionListener(this);

		JPanel skillLabelPanel = new JPanel(new BorderLayout());
		JLabel skillLabel = new JLabel(" SKILLS (" + stuSkillTable.getRowCount() + ")");
		skillLabel.setForeground(Color.WHITE);
		skillLabel.setFont(MainGUI.UW_TITLE_FONT);
		skillLabelPanel.add(skillLabel, BorderLayout.WEST);
		skillLabelPanel.add(btnAddSkill, BorderLayout.EAST);
		skillLabelPanel.setBackground(MainGUI.UW_PURPLE);
		skillPanel.add(stuSkillSPane, BorderLayout.CENTER);
		skillPanel.add(skillLabelPanel, BorderLayout.NORTH);

		// student internship table
		JPanel internPanel = new JPanel(new BorderLayout());
		String[] internColumn = { "Employer", "Position", "Date From", "Date To" };
		int countinterns = 0;
		Object[][] interns = null;
		try {
			myStudentInters = StudentInternshipDB.getInternshipsOfUWNetID(myViewStudentUWnetId);
			countinterns = myStudentInters.size();
			interns = new Object[countinterns][internColumn.length];
			for (int i = 0; i < countinterns; i++) {
				StudentInternship si = myStudentInters.get(i);
				interns[i][0] = si.getEmployer();
				interns[i][1] = si.getPosition();
				interns[i][2] = si.getStartDate();
				interns[i][3] = si.getEndDate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuInternTable = new JTable(interns, internColumn);
		stuInternTable.setEnabled(false);
		stuInternSPane = new JScrollPane(stuInternTable);
		stuInternSPane.setPreferredSize(new Dimension(d.width + (d.width / 2), stuInternTable.getRowHeight() * (6)));
		btnAddIntern = new JButton("Add Internship");
		btnAddIntern.addActionListener(this);

		JPanel internLabelPanel = new JPanel(new BorderLayout());
		JLabel internLabel = new JLabel(" INTERNSHIPS (" + stuInternTable.getRowCount() + ")");
		internLabel.setForeground(Color.WHITE);
		internLabel.setFont(MainGUI.UW_TITLE_FONT);
		internLabelPanel.add(internLabel, BorderLayout.WEST);
		internLabelPanel.add(btnAddIntern, BorderLayout.EAST);
		internLabelPanel.setBackground(MainGUI.UW_PURPLE);
		internPanel.add(stuInternSPane, BorderLayout.CENTER);
		internPanel.add(internLabelPanel, BorderLayout.NORTH);

		// student employment table
		JPanel employPanel = new JPanel(new BorderLayout());
		String[] employColumn = { "Employer", "Position", "Salary", "Date From", "Comment" };
		int countemploys = 0;
		Object[][] employs = null;
		try {
			myStudentEmployemnt = StudentEmploymentDB.getStudentEmploymentsOfUWNetID(myViewStudentUWnetId);
			countemploys = myStudentEmployemnt.size();
			employs = new Object[countemploys][employColumn.length];
			for (int i = 0; i < countemploys; i++) {
				StudentEmployment se = myStudentEmployemnt.get(i);
				employs[i][0] = se.getEmployer();
				employs[i][1] = se.getPosition();
				if (se.getComment() != null && se.getComment().length() > 0) {
					employs[i][2] = "";
					employs[i][3] = "";
				} else {
					employs[i][2] = se.getSalary();
					employs[i][3] = se.getStartDate().toString();
				}
				employs[i][4] = se.getComment();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stuEmployTable = new JTable(employs, employColumn);
		stuEmployTable.setEnabled(false);
		stuEmploySPane = new JScrollPane(stuEmployTable);
		stuEmploySPane.setPreferredSize(new Dimension(d.width + (d.width / 2), stuEmployTable.getRowHeight() * (6)));
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
		tablePanel.add(degreePanel);
		tablePanel.add(skillPanel);
		tablePanel.add(internPanel);
		tablePanel.add(employPanel);

		panel.add(basicPanel, BorderLayout.NORTH);
		panel.add(tablePanel, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Create Add Degree Option panel for View Student
	 * @return Add Degree Option panel for View Student
	 */
	private JPanel createAddDegreeOptionPanel() {
		JPanel panel = new JPanel(new BorderLayout());
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
		panel.add(pnlDegree, BorderLayout.NORTH);
		panel.add(createGraudationDatePanel(), BorderLayout.CENTER);
		panel.add(createGPAPanel(), BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * Create Add Internship Option panel for View Student
	 * @return A Add Internship Option panel for View Student
	 */
	private JPanel createAddInternOptionPanel() { // ADD INTER OPTION - DONE
		JPanel panel = new JPanel(new GridLayout(3, 2));

		txtfIntEmployer = new HintTextField("Employer");
		txtfIntEmployer.setColumns(25);
		txtfIntPosition = new HintTextField("Job Position");
		txtfIntPosition.setColumns(25);

		JPanel datePanel = new JPanel();
		Integer[] monthList = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		cmbIntFromM = new JComboBox<Object>(monthList);
		cmbIntToM = new JComboBox<Object>(monthList);
		Date theDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Integer[] years = new Integer[10];
		years[0] = Integer.parseInt(formatter.format(theDate));
		for (int i = 1; i < 10; i++) {
			years[i] = years[0] + i;
		}
		cmbIntFromY = new JComboBox<Object>(years);
		cmbIntToY = new JComboBox<Object>(years);
		datePanel.add(new JLabel("From:"));
		datePanel.add(cmbIntFromM);
		datePanel.add(cmbIntFromY);
		datePanel.add(new JLabel("To:"));
		datePanel.add(cmbIntToM);
		datePanel.add(cmbIntToY);

		panel.add(new JLabel("Employer:"));
		panel.add(txtfIntEmployer);
		panel.add(new JLabel("Position:"));
		panel.add(txtfIntPosition);
		panel.add(new JLabel("Date:"));
		panel.add(datePanel);
		return panel;
	}

	/**
	 * Create Add Employment for a Student in View Student panel
	 * @return A Add Employment panel in View Student panel
	 */
	private JPanel createAddEmployOptionPanel() { // ADD EMP OPTION - DONE
		final JPanel panel = new JPanel(new BorderLayout());

		final JPanel rbtnPanel = new JPanel();
		final ButtonGroup group = new ButtonGroup();
		final JRadioButton empRbut = new JRadioButton("Enter Employment");
		empRbut.setSelected(true);
		hasEmployment = true;
		empRbut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtfEmpComment.setEnabled(false);
				txtfEmpEmployer.setEnabled(true);
				txtfEmpPosition.setEnabled(true);
				txtfEmpSalary.setEnabled(true);
				cmbEmpFromM.setEnabled(true);
				cmbEmpFromY.setEnabled(true);
				hasEmployment = true;
			}
		});
		final JRadioButton comentRbut = new JRadioButton("Leave a comment");
		comentRbut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtfEmpComment.setEnabled(true);
				txtfEmpEmployer.setEnabled(false);
				txtfEmpPosition.setEnabled(false);
				txtfEmpSalary.setEnabled(false);
				cmbEmpFromM.setEnabled(false);
				cmbEmpFromY.setEnabled(false);
				hasEmployment = false;
			}
		});
		group.add(empRbut);
		group.add(comentRbut);
		rbtnPanel.add(empRbut);
		rbtnPanel.add(comentRbut);
		panel.add(rbtnPanel, BorderLayout.NORTH);
		panel.add(createAddEmployOptionSubPanle(), BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Create a sub panel for Add Employment  Option panel for View Student
	 * @return Add Employment Option panel for View Student
	 */
	private JPanel createAddEmployOptionSubPanle() {
		JPanel panel = new JPanel(new GridLayout(5, 2));
		txtfEmpEmployer = new HintTextField("Employer");
		txtfEmpEmployer.setColumns(25);
		txtfEmpPosition = new HintTextField("Job Position");
		txtfEmpPosition.setColumns(25);
		txtfEmpSalary = new HintTextField("Salary");
		txtfEmpSalary.setColumns(25);
		txtfEmpComment = new HintTextField("Comment");
		txtfEmpComment.setColumns(25);

		JPanel datePanel = new JPanel();
		Integer[] monthList = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		cmbEmpFromM = new JComboBox<Object>(monthList);
		Date theDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		Integer[] years = new Integer[10];
		years[0] = Integer.parseInt(formatter.format(theDate));
		for (int i = 1; i < 10; i++) {
			years[i] = years[0] + i;
		}
		cmbEmpFromY = new JComboBox<Object>(years);
		datePanel.add(new JLabel("From:"));
		datePanel.add(cmbEmpFromM);
		datePanel.add(cmbEmpFromY);

		txtfEmpComment.setEnabled(false);
		txtfEmpEmployer.setEnabled(true);
		txtfEmpPosition.setEnabled(true);
		txtfEmpSalary.setEnabled(true);
		cmbEmpFromM.setEnabled(true);
		cmbEmpFromY.setEnabled(true);
		hasEmployment = true;

		panel.add(new JLabel("Employer:"));
		panel.add(txtfEmpEmployer);
		panel.add(new JLabel("Position:"));
		panel.add(txtfEmpPosition);
		panel.add(new JLabel("Salary:"));
		panel.add(txtfEmpSalary);
		panel.add(new JLabel("Date From:"));
		panel.add(datePanel);
		panel.add(new JLabel("Comment"));
		panel.add(txtfEmpComment);
		return panel;
	}

	/**
	 * Create Edit Degree Option panel for View Student
	 * @return Edit Degree Option panel for View Student
	 */
	private JPanel createEditDegreePanel() {
		final JPanel panel = new JPanel(new BorderLayout());

		final JPanel updatePanel = new JPanel(new BorderLayout());
		final JPanel selectPanel = new JPanel();

		myStudentDegreeData = new Object[myStudentDegree.size()];
		for (int i = 0; i < myStudentDegree.size(); i++) {
			try {
				myStudentDegreeData[i] = DegreeDB.getDegree(myStudentDegree.get(i).getDegreeId());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		if (myStudentDegreeData != null) {
			cmbStudentDegree = new JComboBox<Object>(myStudentDegreeData);
		}
		selectPanel.add(new JLabel("Select Degree"));
		selectPanel.add(cmbStudentDegree);
		updatePanel.add(selectPanel, BorderLayout.NORTH);
		updatePanel.add(createGraudationDatePanel(), BorderLayout.CENTER);
		updatePanel.add(createGPAPanel(), BorderLayout.SOUTH);

		final JPanel btnPanel = new JPanel();
		final ButtonGroup btngroup = new ButtonGroup();
		final JRadioButton gpaRbtn = new JRadioButton("Updeate GPA");
		final JRadioButton graduationRbtn = new JRadioButton("Updeate Graduation");
		gpaRbtn.setSelected(true);
		txfGPA.setEnabled(true);
		cmbTerm.setEnabled(false);
		cmbYear.setEnabled(false);
		isGpaSelected = true;
		gpaRbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txfGPA.setEnabled(true);
				cmbTerm.setEnabled(false);
				cmbYear.setEnabled(false);
				isGpaSelected = true;
			}
		});
		graduationRbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txfGPA.setEnabled(false);
				cmbTerm.setEnabled(true);
				cmbYear.setEnabled(true);
				isGpaSelected = false;
			}
		});
		btngroup.add(gpaRbtn);
		btngroup.add(graduationRbtn);
		btnPanel.add(gpaRbtn);
		btnPanel.add(graduationRbtn);

		panel.add(btnPanel, BorderLayout.NORTH);
		panel.add(updatePanel, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Create Add Skill for a Student in View Student panel
	 * @return A Add Skill panel in View Student panel
	 */
	private JPanel createAddSkillOptionPanel() {
		final JPanel panel = new JPanel(new BorderLayout());

		List<Skill> skills = null;
		JPanel skillPanel = null;
		try {
			skills = SkillDB.getSkills();
			ckbSkill = new JCheckBox[skills.size()];
			int numRows = skills.size() / 5;
			int numColumns = skills.size() % 5;
			skillPanel = new JPanel(new GridLayout(numColumns, numRows));
			if (skills != null) {
				for (int i = 0; i < skills.size(); i++) {
					ckbSkill[i] = new JCheckBox(skills.get(i).toString());
					skillPanel.add(ckbSkill[i]);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		panel.add(new JLabel("Select Student's Skills"), BorderLayout.NORTH);
		panel.add(skillPanel, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Create a panel for entering GPA
	 * @return A panel for entering GPA
	 */
	private JPanel createGPAPanel() {
		JPanel pnlGPA = new JPanel();
		txfGPA = new HintTextField("GPA");
		txfGPA.setColumns(5);
		pnlGPA.add(new JLabel("Grade Point Average"));
		pnlGPA.add(txfGPA);
		return pnlGPA;
	}

	/**
	 * Create a panel for selecting graduation term and year
	 * @return A panel for selecting graduation term and year
	 */
	private JPanel createGraudationDatePanel() {
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
		return pnlGraduation;
	}

	/**
	 * Listen to buttons.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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
			myViewStudent = StudentCollection.getStudentById(myViewStudentUWnetId);
			if (myViewStudent == null) {
				JOptionPane.showMessageDialog(null, "No studennt in DB");
				return;
			}
			txtfViewUWID.setText(null);
			performViewStudent(myViewStudentUWnetId);
		} else if (e.getSource() == btnEditEmail) {
			performEditStudentEmail();
		} else if (e.getSource() == btnAddDegree) {
			performAddDegree();
		} else if (e.getSource() == btnEditDegree) {
			try {
				performEditDegree();
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == btnAddSkill) {
			performAddSkill();
		} else if (e.getSource() == btnAddIntern) {
			performAddIntern();
		} else if (e.getSource() == btnAddEmploy) {
			performAddEmploy();
		}

	}

	/**
	 * Perform Add employment for a student.
	 */
	private void performAddEmploy() {

		int result = JOptionPane.showConfirmDialog(null, createAddEmployOptionPanel(), "Add Employmnet",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			String employer = txtfEmpEmployer.getText();
			String position = txtfEmpPosition.getText();
			String salary_str = txtfEmpSalary.getText();
			double salary = 0.00;
			String comment = txtfEmpComment.getText();

			String fromMon = cmbEmpFromM.getSelectedItem().toString();
			String fromYr = cmbEmpFromY.getSelectedItem().toString();

			if (hasEmployment) {
				if (employer == null || employer.length() < 2) {
					JOptionPane.showMessageDialog(null, "Enter employer (at least 2 letters)");
					return;
				}
				if (position == null || position.length() < 2) {
					JOptionPane.showMessageDialog(null, "Enter job position(at least 2 letters)");
					return;
				}
				try {
					salary = Double.parseDouble(salary_str);
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Salary should be non-negative number");
					return;
				}
				if (fromMon == null || fromYr == null) {
					JOptionPane.showMessageDialog(null, "Select \'Date From\'");
					return;
				}
			} else {
				if (comment == null || comment.length() < 1) {
					JOptionPane.showMessageDialog(null, "Leave Comment");
					return;
				}
			}
			try {
				Date fromDate = SQL_DATE_FORMAT.parse(fromMon + "-" + fromYr);
				java.sql.Date sql_fromDate = new java.sql.Date(fromDate.getTime());
				StudentEmployment employment;
				if (hasEmployment) {
					employment = new StudentEmployment(myViewStudentUWnetId, employer, position, salary, sql_fromDate);
				} else {
					employment = new StudentEmployment(myViewStudentUWnetId, null, null, 0, sql_fromDate, comment);
				}

				if (!StudentEmploymentDB.add(employment)) {
					JOptionPane.showMessageDialog(null, "failed to add");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Add Employment successfully");
				}

			} catch (ParseException e) {

				e.printStackTrace();
			}

			// refresh panel to show updated info
			try {
				myStudentEmployemnt = StudentEmploymentDB.getStudentEmploymentsOfUWNetID(myViewStudentUWnetId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			performViewStudent(myViewStudentUWnetId);

			// clear
			txtfEmpComment.setText(null);
			txtfEmpEmployer.setText(null);
			txtfEmpPosition.setText(null);
			txtfEmpSalary.setText(null);
			cmbEmpFromM.setSelectedIndex(0);
			cmbEmpFromY.setSelectedIndex(0);
			hasEmployment = false;
		}
	}

	/**
	 * Perform Add Internship for a student.
	 */
	private void performAddIntern() {
		int result = JOptionPane.showConfirmDialog(null, createAddInternOptionPanel(), "Add Internship",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String employer = txtfIntEmployer.getText();
			String position = txtfIntPosition.getText();
			String fromMon = cmbIntFromM.getSelectedItem().toString();
			String fromYr = cmbIntFromY.getSelectedItem().toString();
			String toMon = cmbIntToM.getSelectedItem().toString();
			String toYr = cmbIntToY.getSelectedItem().toString();

			if (employer == null || employer.length() < 2) {
				JOptionPane.showMessageDialog(null, "Enter employer (at least 2 letters)");
				return;
			}
			if (position == null || position.length() < 2) {
				JOptionPane.showMessageDialog(null, "Enter job position(at least 2 letters)");
				return;
			}
			if (fromMon == null || fromYr == null || toMon == null || toYr == null) {
				JOptionPane.showMessageDialog(null, "Select \'Date From\' and \'Date To\'");
				return;
			}
			int fyear = Integer.valueOf(fromYr);
			int tyear = Integer.valueOf(toYr);
			int fmon = Integer.valueOf(fromMon);
			int tmon = Integer.valueOf(toMon);
			if ((fyear == tyear && fmon > tmon) || (tyear < fyear)) {
				JOptionPane.showMessageDialog(null, "\'Date To\' cannot be before \'Date From\'");
				return;
			}
			Date fromDate;
			try {
				fromDate = SQL_DATE_FORMAT.parse(fromMon + "-" + fromYr);
				java.sql.Date sql_fromDate = new java.sql.Date(fromDate.getTime());
				Date toDate = SQL_DATE_FORMAT.parse(toMon + "-" + toYr);
				java.sql.Date sql_toDate = new java.sql.Date(toDate.getTime());

				StudentInternship internship = new StudentInternship(sql_fromDate, sql_toDate, position,
						myViewStudentUWnetId, employer);

				if (!StudentInternshipDB.add(internship)) {
					JOptionPane.showMessageDialog(null, "failed to add");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Add internship successfully");
				}
			} catch (ParseException e) {

				e.printStackTrace();
			}

			// refresh panel to show updated info
			try {
				myStudentInters = StudentInternshipDB.getInternshipsOfUWNetID(myViewStudentUWnetId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			performViewStudent(myViewStudentUWnetId);

			// clear
			txtfIntEmployer.setText(null);
			txtfIntPosition.setText(null);
			cmbIntFromM.setSelectedIndex(0);
			cmbIntFromY.setSelectedIndex(0);
			cmbIntToM.setSelectedIndex(0);
			cmbIntToY.setSelectedIndex(0);
		}
	}

	/**
	 * Perform Add Skill for a student.
	 */
	@SuppressWarnings("unused")
	private void performAddSkill() {

		int result = JOptionPane.showConfirmDialog(null, createAddSkillOptionPanel(), "Select Skills",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {

			List<String> selected = new ArrayList<String>();
			for (JCheckBox cb : ckbSkill) {
				if (cb.isSelected()) {
					selected.add(cb.getText());

				}
			}
			try {
				for (int i = 0; i < selected.size(); i++) {
					if (myStudentSkills.size() > 0) {
						for (int j = 0; j < myStudentSkills.size(); j++) {
							Skill skill = SkillDB.getSkillByID(myStudentSkills.get(j).getSkillId());
							if (skill.getSkillName().equalsIgnoreCase(selected.get(i).toString())) {
								break;
							} else {
								Skill addSkill = SkillDB.getSkillBySkillName(selected.get(i).toString());
								StudentSkill stuSkill = new StudentSkill(myViewStudentUWnetId, addSkill.getId());
								if (!StudentSkillDB.addStudentSkill(stuSkill)) {
									JOptionPane.showMessageDialog(null, "failed to add skill");
									return;
								}
								break;
							}
						}
					} else {
						Skill addSkill = SkillDB.getSkillBySkillName(selected.get(i).toString());
						StudentSkill stuSkill = new StudentSkill(myViewStudentUWnetId, addSkill.getId());
						if (!StudentSkillDB.addStudentSkill(stuSkill)) {
							JOptionPane.showMessageDialog(null, "failed to add skill");
							return;
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null, "Add Skills Successfully");

			// refresh panel to show updated info
			try {
				myStudentSkills = StudentSkillDB.getStudentSKillsOfUWNetID(myViewStudentUWnetId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			performViewStudent(myViewStudentUWnetId);
		}
	}

	/**
	 * Perform Add Degree for a student.
	 */
	private void performAddDegree() {
		int result = JOptionPane.showConfirmDialog(null, createAddDegreeOptionPanel(), "Add Degree",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String gpa = txfGPA.getText().trim();
			double dblGPA = 0.0;

			try {
				dblGPA = Double.parseDouble(gpa);
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(null, "GPA should be non-negative number");
				return;
			}

			if (dblGPA < 0 || dblGPA > 4.0) {
				JOptionPane.showMessageDialog(null, "GPA should be between 0.0 and 4.0");
				return;
			}

			Degree degree = (Degree) cmbDegree.getSelectedItem();
			String term = cmbTerm.getSelectedItem().toString();
			String year = cmbYear.getSelectedItem().toString();

			StudentDegree studentDegree = new StudentDegree(myViewStudentUWnetId, degree.getId(), term, year, dblGPA,
					null);
			try {
				if (!StudentDegreeDB.addStudentDegree(studentDegree)) {
					JOptionPane.showMessageDialog(null, "failed to Add");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Add Degree Successfully");

				}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			// refresh panel to show updated info
			try {
				myStudentDegree = StudentDegreeDB.getStudentDegreeOfUWNetID(myViewStudentUWnetId);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			performViewStudent(myViewStudentUWnetId);

			// clear
			txfGPA.setText(null);
			cmbDegree.setSelectedIndex(0);
			cmbTerm.setSelectedIndex(0);
			cmbYear.setSelectedIndex(0);
		}
	}

	/**
	 * Perform View a student with given UW net id.
	 * @param theId The uwnetid
	 */
	private void performViewStudent(final String theId) { 
		if (theId.length() > 0) {
			myStudentList = getStudentData(theId);
			pnlContent.removeAll();
			pnlContent.add(createStudentViewPanel(theId));
			pnlContent.revalidate();
			this.repaint();
		}
		txtfViewUWID.setText("");
	}

	/**
	 * Perform Edit Degree for a student.
	 * 
	 * @throws HeadlessException
	 * @throws SQLException
	 */
	private void performEditDegree() throws HeadlessException, SQLException {

		// option pane
		int result = JOptionPane.showConfirmDialog(null, createEditDegreePanel(), "Update Degree",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {

			Degree degree = (Degree) cmbStudentDegree.getSelectedItem();
			StudentDegree stuDegree = null;
			try {
				stuDegree = StudentDegreeDB.getStudentDegreeID(myViewStudentUWnetId, degree.getId());

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (isGpaSelected) {
				String gpa = txfGPA.getText().trim();
				double dblGPA = 0.0;
				try {
					dblGPA = Double.parseDouble(gpa);
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "GPA should be non-negative number");
					return;
				}
				if (dblGPA < 0 || dblGPA > 4.0) {
					JOptionPane.showMessageDialog(null, "GPA should be between 0.0 and 4.0");
					return;
				}
				if (!StudentDegreeDB.updateStudentDegree(stuDegree, "gpa", dblGPA)) {
					JOptionPane.showMessageDialog(null, "failed to Update");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Update GPA Successfully");
				}
			} else {
				String term = cmbTerm.getSelectedItem().toString();
				String year = cmbYear.getSelectedItem().toString();
				if (!StudentDegreeDB.updateStudentDegree(stuDegree, "graduation_term", term)) {
					JOptionPane.showMessageDialog(null, "failed to update");
					return;
				}
				if (!StudentDegreeDB.updateStudentDegree(stuDegree, "graduation_year", year)) {
					JOptionPane.showMessageDialog(null, "failed to update");
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Update sudent's degree successfully");
				}
			}

			// refresh panel to show updated info
			try {
				myStudentDegree = StudentDegreeDB.getStudentDegreeOfUWNetID(myViewStudentUWnetId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			performViewStudent(myViewStudentUWnetId);

			// clear
			txfGPA.setText(null);
			cmbStudentDegree.setSelectedIndex(0);
			cmbTerm.setSelectedIndex(0);
			cmbYear.setSelectedIndex(0);
		}
	}

	/**
	 * Perform Edit Email for a student.
	 */
	private void performEditStudentEmail() { // DONE
		final JPanel emailPanel = new JPanel();

		HintTextField email  = new HintTextField("Format: xxxxxx@email.com");
		email.setColumns(20);
		
		emailPanel.add(email);
		
		int result = JOptionPane.showConfirmDialog(null, emailPanel, "Edit Email",JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			String emailinput = email.getText();
			if (emailinput == null || emailinput.length() < 3 || (!emailinput.contains("@") && !emailinput.contains("."))) {
				JOptionPane.showMessageDialog(null, "Failed to update e-mail!");
				return;
			}
			
			if (StudentCollection.updateEmail(myViewStudent, emailinput)) {
				JOptionPane.showMessageDialog(null, "E-mail is updated successfully!");
				myViewStudent = StudentCollection.getStudentById(myViewStudentUWnetId);
				performViewStudent(myViewStudentUWnetId);
			}
		}
	}

	/**
	 * Perform Add a student with degree information.
	 */
	private void performAddStudent() {
		String first = txfFirst.getText().trim();
		String middle = txfMiddle.getText().trim();
		String last = txfLast.getText().trim();
		String email = txfEmail.getText().trim();
		String uwNetID = txfUWNetID.getText().trim();
		String gpa = txfGPA.getText().trim();
		String transfer = txtfTransfer.getText();
		double dblGPA = 0.0;
		boolean validFirst = first.matches("^[\\p{L} .'-]+$");
		boolean validLast = last.matches("^[\\p{L} .'-]+$");

		if (first.length() < 2 || last.length() < 2 || !validFirst || !validLast) {
			lblWarning.setText("Please enter a valid student first and last name. "
					+ "First and last name cannot be blank, must be at least two characters, "
					+ "and can only contain letters.");
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
		for (Student student : studentList) {
			if (uwNetID.equals(student.getUWNetID())) {
				uwNetIDIsTaken = true;
				break;
			}
		}
		if (uwNetIDIsTaken) {
			lblWarning.setText("That UW NetID is taken. Please enter a valid UW NetID.");
			return;
		}
		try {
			dblGPA = Double.parseDouble(gpa);
		} catch (NumberFormatException e) {
			lblWarning.setText("Please enter a valid GPA. GPA should be entered as a non " + "negative decimal.");
			return;
		}
		if (dblGPA < 0 || dblGPA > 4.0) {
			lblWarning.setText("Please enter a valid GPA. GPA should be entered as a non "
					+ "negative decimal and cannot exceed 4.0.");
			return;
		}
		Degree degree = (Degree) cmbDegree.getSelectedItem();
		String term = (String) cmbTerm.getSelectedItem();
		String year = Integer.toString((int) cmbYear.getSelectedItem());
		StudentDegree studentDegree;
		if (transfer == null || transfer.length() < 1)
		{
			studentDegree = new StudentDegree(uwNetID, degree.getId(), term, year, dblGPA);
		}
		else
		{
			studentDegree = new StudentDegree(uwNetID, degree.getId(), term, year, dblGPA, transfer);
		}
		lblWarning.setText("");
		Student student = null;
		if (middle.isEmpty()) {
			student = new Student(first, last, uwNetID);
		} else {
			student = new Student(first, middle, last, uwNetID);
		}
		if (!email.isEmpty()) {
			student.setEmail(email);
		}
		try {
			StudentDegreeDB.addStudentDegree(studentDegree);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StudentCollection.add(student);
		btnSearch.doClick();

		// clear
		txfGPA.setText(null);
		cmbDegree.setSelectedIndex(0);
		cmbTerm.setSelectedIndex(0);
		cmbYear.setSelectedIndex(0);
	}

	/**
	 * Perform Search students by first or last name.
	 */
	private void performSearchStudent() {
		String searchKey = txfSearch.getText();
		if (searchKey.length() > 0) {
			myStudentList = getStudentData(searchKey);
			pnlContent.removeAll();
			table = new JTable(myData, myStudentColumnNames);
			scrollPane = new JScrollPane(table);
			pnlContent.add(scrollPane);
			pnlContent.revalidate();
			this.repaint();
			txfSearch.setText("");
		}
	}
	

	/*
	 * Get student data
	 */

	/**
	 * Get Student data
	 * 
	 * @param theSearchKey The search keyword (first name or last name)
	 * @return A list of Students
	 */
	private List<Student> getStudentData(final String theSearchKey) {
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
				myData[i][4] = myStudentList.get(i).getEmail();
			}
		}
		return myStudentList;
	}

}
