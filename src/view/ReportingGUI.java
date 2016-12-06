package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import component.HintTextField;
import data.DegreeDB;
import data.SkillDB;
import data.StudentDB;
import data.StudentDegreeDB;
import data.StudentEmploymentDB;
import model.Degree;
import model.Skill;
import model.Student;
import model.StudentCollection;
import model.StudentDegree;
import model.StudentSkill;
import model.StudentEmployment;

/**
 * ReportingGUI displays the various options for running reports on student
 * data.
 * @author Thomas Van Riper
 * December 2nd, 2016
 *
 */
public class ReportingGUI extends JPanel implements ActionListener, TableModelListener
{
	/**
	 * An auto generated serial version UID.
	 */
	private static final long serialVersionUID = -412717764910629392L;
	private static final String[] STUDENT_DATA_COLUMN_NAMES = {"First", "Last", "UW NetID", "E-mail"};
	
	private static final String[] STUDENT_DATA_BY_DATE = {"First", "Last", "UW NetID", "Degree", "Employer", "Job Position"};
		
	/**JButton fields.*/
	private JButton btnGrad, btnGetStudentData, btnSkill, btnSkillSubmit, btnGraduationSubmit;
	
	/**JCheckBox fields.*/
	private JCheckBox ckbIntern, ckbNoIntern, ckbTransfer, ckbNoTransfer;
	
	/**JList fields.*/
	private JList<Object> lstPrograms, lstSkills, lstTerms;
	
	/**JPanel fields.*/
	private JPanel pnlButtons, pnlContent, pnlSkillSearch, pnlResult, pnlGraduationSearch, pnlGraduationResults;	
	
	/**JScrollPane to scroll through offered programs and skills.*/
	private JScrollPane scrollPane, scrollPaneSkills, scrollPaneStudents, scrollPaneTerm, scrollPaneGradInfo;
	
	/**JTable fields.*/
	private JTable tblStudents, tblGradStudents;
	
	/**A 2D array to format a JTable of student data.*/
	private Object[][] studentData, gradData;
	
	/**HintTextField fields.*/
	private HintTextField txfYearName;
	
	public ReportingGUI()
	{
		setLayout(new BorderLayout());
		
		createComponents();
		setVisible(true);
		setSize(500, 700);
	}

	private void createComponents()
	{
		pnlContent = new JPanel();
		
		add(createButtonPanel(), BorderLayout.NORTH);
		add(pnlContent, BorderLayout.CENTER);
	}
	
	private JPanel createButtonPanel()
	{
		pnlButtons = new JPanel();
		
		btnGrad = new JButton("Employment by Graduation Date");
		btnGrad.addActionListener(this);
		
		btnSkill = new JButton("Employment and Skills");
		btnSkill.addActionListener(this);
		
		pnlButtons.add(btnGrad);
		pnlButtons.add(btnSkill);
		
		return pnlButtons;
	}
	
	private JPanel createSkillSearchPanel()
	{
		pnlSkillSearch = new JPanel();
		pnlSkillSearch.setLayout(new BoxLayout(pnlSkillSearch, BoxLayout.Y_AXIS));
		
		JLabel lblIntern = new JLabel("Select from students who completed an internship?");
		lblIntern.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		JPanel pnlIntern = new JPanel();
		ckbNoIntern = new JCheckBox("Did not complete an internship");
		ckbNoIntern.addActionListener(this);
		ckbIntern = new JCheckBox("Completed an internship");
		ckbIntern.addActionListener(this);
		pnlIntern.add(ckbNoIntern);
		pnlIntern.add(ckbIntern);
		
		JLabel lblTransfer = new JLabel("Select from students who transferred from a" +
				" two-year college?");
		lblTransfer.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		JPanel pnlTransfer = new JPanel();
		ckbNoTransfer = new JCheckBox("Did not transfer");
		ckbNoTransfer.addActionListener(this);
		ckbTransfer = new JCheckBox("Transferred");
		ckbTransfer.addActionListener(this);
		pnlTransfer.add(ckbNoTransfer);
		pnlTransfer.add(ckbTransfer);
		
		JLabel lblProgram = new JLabel("<html>Select students<br> from a program: </html>");
		lblProgram.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		JPanel pnlProgramLabel = new JPanel();
		pnlProgramLabel.add(lblProgram);
		Object[] degreeList = null;
		try
		{
			degreeList = DegreeDB.getDegrees().toArray();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if (degreeList != null)
		{
			lstPrograms = new JList<Object>(degreeList);
		}
		
		scrollPane = new JScrollPane(lstPrograms);
		pnlProgramLabel.setPreferredSize(scrollPane.getPreferredSize());
		JPanel pnlProgram = new JPanel();
		pnlProgram.add(pnlProgramLabel);
		pnlProgram.add(scrollPane);
		
		JLabel lblSkill = new JLabel("<html>Select<br> skills: </html>");
		lblSkill.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		JPanel pnlSkillLabel = new JPanel();
		pnlSkillLabel.add(lblSkill);
		Object[] skillList = null;
		try
		{
			skillList = SkillDB.getSkills().toArray();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if (skillList != null)
		{
			lstSkills = new JList<Object>(skillList);
		}
		
		scrollPaneSkills = new JScrollPane(lstSkills);
		pnlSkillLabel.setPreferredSize(scrollPaneSkills.getPreferredSize());
		JPanel pnlSkills = new JPanel();
		pnlSkills.add(pnlSkillLabel);
		pnlSkills.add(scrollPaneSkills);
		
		JPanel pnlSubmit = new JPanel();
		btnSkillSubmit = new JButton("Submit");
		btnSkillSubmit.addActionListener(this);
		pnlSubmit.add(btnSkillSubmit);
		
		pnlSkillSearch.add(lblIntern);
		pnlSkillSearch.add(pnlIntern);
		pnlSkillSearch.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlSkillSearch.add(lblTransfer);
		pnlSkillSearch.add(pnlTransfer);
		pnlSkillSearch.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlSkillSearch.add(pnlProgram);
		pnlSkillSearch.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlSkillSearch.add(pnlSkills);
		pnlSkillSearch.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlSkillSearch.add(pnlSubmit);
		
		return pnlSkillSearch;
	}
	
	/**
	 * Creates the data needed to format a JTable of students.
	 * @return A list of students.
	 */
	private void getData(List<Student> filterList) 
	{
		studentData = new Object[filterList.size()][STUDENT_DATA_COLUMN_NAMES.length];
		for (int i = 0; i < filterList.size(); i++)
		{
			studentData[i][0] = filterList.get(i).getFirstName();
			studentData[i][1] = filterList.get(i).getLastName();
			studentData[i][2] = filterList.get(i).getUWNetID();
			if (filterList.get(i).getEmail() != null)
			{
				studentData[i][3] = filterList.get(i).getEmail();
			}
			else
			{
				studentData[i][3] = "";
			}
		}
	}

	/**
	 * Generates a report based on the selected criteria.
	 */
	private JPanel getSkillSearchReport()
	{
		List<Student> studentList = StudentCollection.getStudents();
		List<Student> removalList = new ArrayList<Student>();
		// has internship
		if (ckbNoIntern.isSelected())
		{
			for (Student student : studentList)
			{
				if (!student.getInternship().isEmpty())
				{
					removalList.add(student);
				}
				 // TODO Jieun made methods for reporting in stuCollection 
				//  hasInternship()
				
				if (StudentCollection.hasInternship(student.getUWNetID())) {
					removalList.add(student);
				}
				
//				if (!student.getInternship().isEmpty())
//				{
//					removalList.add(student);
//				}
			}
		}
		else if (ckbIntern.isSelected())
		{
			for (Student student : studentList)
			{
				if (student.getInternship().isEmpty())
				{
					removalList.add(student);
				}
			}
		}
		
		if (ckbNoTransfer.isSelected())
		{
			for (Student student : studentList)
			{
				if (student.getDegree().get(0).getTransferCollege() != null ||
						!student.getDegree().get(0).getTransferCollege().isEmpty())
				{
					removalList.add(student);
				}
			}
		}
		else if (ckbTransfer.isSelected())
		{
			for (Student student : studentList)
			{
				if (student.getDegree().get(0).getTransferCollege() == null ||
						student.getDegree().get(0).getTransferCollege().isEmpty())
				{
					removalList.add(student);
				}
			}
		}
		
		if (!lstPrograms.isSelectionEmpty())
		{
			for (Student student : studentList)
			{
				boolean programIsSelected = false;
				if (student.getDegree() != null)
				{
					for (StudentDegree studentDegree : student.getDegree())
					{
						for (int i = 0; i < lstPrograms.getSelectedValuesList().size(); i++)
						{
							Degree degree = (Degree) lstPrograms.getSelectedValuesList().get(i);
							try
							{
								if (DegreeDB.getDegree(studentDegree.getDegreeId()).getProgram().equals(degree.getProgram()) &&
										DegreeDB.getDegree(studentDegree.getDegreeId()).getLevel().equals(degree.getLevel()))
								{
									programIsSelected = true;
								}
							} catch (SQLException e)
							{
								e.printStackTrace();
							}
						}
						
						if (!programIsSelected)
						{
							removalList.add(student);
						}
					}
				}
			}
		}
		
		if (!lstSkills.isSelectionEmpty())
		{
			for (Student student : studentList)
			{
				boolean skillIsSelected = false;
				if (student.getSkills() != null)
				{
					for (StudentSkill studentSkill : student.getSkills())
					{
						for (int i = 0; i < lstSkills.getSelectedValuesList().size(); i++)
						{
							Skill skill = (Skill) lstSkills.getSelectedValuesList().get(i);
							try
							{
								if (SkillDB.getSkillByID(studentSkill.getSkillId()).getSkillName().equals(skill.getSkillName()))
								{
									skillIsSelected = true;
								}
							} catch (SQLException e)
							{
								e.printStackTrace();
							}
						}
					}
				}
				
				if (!skillIsSelected)
				{
					removalList.add(student);
				}
			}
		}
		
		studentList.removeAll(removalList);
		
		List<Student> filterList = new ArrayList<Student>();
		for (Student student : studentList)
		{
			if (student.getEmployment() != null)
			{
				filterList.add(student);
			}
			for (Student aStudent : studentList) 
			{
				//TODO Jieun corrected
				if (!StudentCollection.hasInternship(student.getUWNetID())) {
					removalList.add(student);
				}
//				if (student.getInternship().isEmpty())
//				{
//					removalList.add(student);
//				}
			}
		}

		
		// has transferred 
		if (ckbNoTransfer.isSelected())
		{
			for (Student student : studentList) 
			{
				// TODO Jieun corrected ------------------------------------------------ HAS ERROR???????!!!!!! 
				if (StudentCollection.hasTransferred(student.getUWNetID())) {
					removalList.add(student);
				}
				
//				if (student.getDegree().get(0).getTransferCollege() != null ||
//						!student.getDegree().get(0).getTransferCollege().isEmpty())
//				{
//					removalList.add(student);
//				}
			}
			
		}
		else if (ckbTransfer.isSelected())
		{
			for (Student student : studentList) //TODO jieun corrected
			{
				// TODO Jieun corrected
				if (!StudentCollection.hasTransferred(student.getUWNetID())) {
					removalList.add(student);
				}

//				if (student.getDegree().get(0).getTransferCollege() == null ||
//						student.getDegree().get(0).getTransferCollege().isEmpty())
//				{
//					removalList.add(student);
//				}
			}
		}

		
		if (!lstPrograms.isSelectionEmpty())
		{
			
			// TODO Jieun corrected
			Degree degree = (Degree) lstPrograms.getSelectedValue();
			for (Student student : studentList) //TODO
			{
				
				if (degree != null) {
					if (!StudentCollection.isInProgram(student.getUWNetID(), degree.getId())) {
						removalList.add(student);
					}
				}
				
				
//				boolean programIsSelected = false;
//				if (student.getDegree() != null)
//				{
//					for (StudentDegree studentDegree : student.getDegree())
//					{
//						for (int i = 0; i < lstPrograms.getSelectedValuesList().size(); i++)
//						{
//							Degree degree = (Degree) lstPrograms.getSelectedValuesList().get(i);
//							try
//							{
//								if (DegreeDB.getDegree(studentDegree.getDegreeId()).getProgram().equals(degree.getProgram()) &&
//										DegreeDB.getDegree(studentDegree.getDegreeId()).getLevel().equals(degree.getLevel()))
//								{
//									programIsSelected = true;
//								}
//							} catch (SQLException e)
//							{
//								e.printStackTrace();
//							}
//						}
//
//						if (!programIsSelected)
//						{
//							removalList.add(student);
//						}
//					}
//				}
			}
		}

		
		// has skills
		if (!lstSkills.isSelectionEmpty())
		{
			// TODO Jieun corrected
			Skill skill = (Skill) lstSkills.getSelectedValue();
			for (Student student : studentList)
			{
				if (skill != null) {
					if (!StudentCollection.hasSkill(student.getUWNetID(), skill.getId())) {
						removalList.add(student);
					}
				}
				
				
//				boolean skillIsSelected = false;
//				if (student.getSkills() != null)
//				{
//					for (StudentSkill studentSkill : student.getSkills())
//					{
//						for (int i = 0; i < lstSkills.getSelectedValuesList().size(); i++)
//						{
//							Skill skill = (Skill) lstSkills.getSelectedValuesList().get(i);
//							try
//							{
//								if (SkillDB.getSkillByID(studentSkill.getSkillId()).getSkillName().equals(skill.getSkillName()))
//								{
//									skillIsSelected = true;
//								}
//							} catch (SQLException e)
//							{
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//				
//				if (!skillIsSelected)
//				{
//					removalList.add(student);
//				}
			}
		}

		
		studentList.removeAll(removalList);
		
	
		//List<Student> filterList = new ArrayList<Student>();
		
		// check employment
		for (Student student : studentList)
		{
			//TODO Jieun corrected
			if (StudentCollection.hasEmployment(student.getUWNetID())) {
				filterList.add(student);
			}
			
//			if (student.getEmployment() != null)
//			{
//				filterList.add(student);
//			}
		}
		
		pnlResult = new JPanel();
		pnlResult.setLayout(new BoxLayout(pnlResult, BoxLayout.Y_AXIS));
		
		double percent = 0;
		if (studentList.size() > 0)
		{
			percent = ((double)filterList.size() / (double)studentList.size()) * 100;
		}
		NumberFormat formatter = new DecimalFormat("#0.0");
		String label = formatter.format(percent) + "% of students are employed.";
		JLabel lblStats = new JLabel(label);
		lblStats.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblStats.setAlignmentX(CENTER_ALIGNMENT);
		
		getData(filterList);
		tblStudents = new JTable(studentData, STUDENT_DATA_COLUMN_NAMES);
		tblStudents.getModel().addTableModelListener(this);
		
		getData(filterList);
		tblStudents = new JTable(studentData, STUDENT_DATA_COLUMN_NAMES);
		scrollPaneStudents = new JScrollPane(tblStudents);
		
		pnlResult.add(lblStats);
		pnlResult.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlResult.add(scrollPaneStudents);
		pnlResult.add(Box.createRigidArea(new Dimension(0, 10)));
		
		return pnlResult;
	}
	
	
	
	
	
	
	
	
	
	/*
	 * Louis===========================
	 */

	
	private JPanel createGraduationSearchPanel() {
		pnlGraduationSearch = new JPanel();
		pnlGraduationSearch.setLayout(new BoxLayout(pnlGraduationSearch, BoxLayout.Y_AXIS));
		
		JLabel lblYear = new JLabel("Input a Year to Search from");
		lblYear.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblYear.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel pnlYear = new JPanel();
		txfYearName = new HintTextField("Year");
		txfYearName.setColumns(20);
		pnlYear.add(txfYearName);
		
		JLabel lblTerm = new JLabel("Input a Graduation Term to Search from");
		lblTerm.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblTerm.setAlignmentX(Component.CENTER_ALIGNMENT);
		Object[] termList = {"Autumn", "Winter", "Spring", "Summer"};
		lstTerms = new JList<Object>(termList);
		lstTerms.setVisibleRowCount(4);
		scrollPaneTerm = new JScrollPane(lstTerms);
		JPanel pnlTerm = new JPanel();
		pnlTerm.add(scrollPaneTerm);
		
		JPanel pnlSubmit = new JPanel();
		btnGraduationSubmit = new JButton("Submit");
		btnGraduationSubmit.addActionListener(this);
		pnlSubmit.add(btnGraduationSubmit);
		
		pnlGraduationSearch.add(Box.createRigidArea(new Dimension(0, 20)));
		pnlGraduationSearch.add(lblYear);
		pnlGraduationSearch.add(pnlYear);
		pnlGraduationSearch.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlGraduationSearch.add(lblTerm);
		pnlGraduationSearch.add(pnlTerm);
		pnlGraduationSearch.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlGraduationSearch.add(pnlSubmit);
		
		return pnlGraduationSearch;
	}
	
	private JPanel getGraduationSearchReport() throws SQLException {
		List<StudentDegree> studentDegreeList;
		int year = Integer.parseInt(txfYearName.getText().trim());
		String quarter = "";
		if (!lstTerms.isSelectionEmpty()) {
				studentDegreeList = StudentDegreeDB.getStudentDegreeByYear(year);
		} else {
			studentDegreeList = StudentDegreeDB.getStudentDegreeByYear(year, (String)lstTerms.getSelectedValue());
			quarter = (String) lstTerms.getSelectedValue();
		}
		
		pnlGraduationResults = new JPanel();
		pnlGraduationResults.setLayout(new BoxLayout(pnlGraduationResults, BoxLayout.Y_AXIS));
		
		int totalEmployed = getGraduationData(studentDegreeList);

		double percent = 0;
		if (studentDegreeList.size() > 0)
		{
			percent = (totalEmployed / (double)studentDegreeList.size()) * 100;
		}
		NumberFormat formatter = new DecimalFormat("#0.0");
		String label = formatter.format(percent) + "% of students employed on graduation " 
		+ year + " " + quarter;
		JLabel lblStats = new JLabel(label);
		lblStats.setFont(new Font("Lucida Grande", Font.BOLD, 16));		
		
		tblGradStudents = new JTable(gradData, STUDENT_DATA_BY_DATE);
		scrollPaneGradInfo = new JScrollPane(tblGradStudents);
		
		pnlGraduationResults.add(lblStats);
		pnlGraduationResults.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlGraduationResults.add(scrollPaneGradInfo);
		
		return pnlGraduationResults;
	}
	

	
	private int getGraduationData(List<StudentDegree> studentDegreeList) throws SQLException  {
		gradData = new Object[studentDegreeList.size()][STUDENT_DATA_BY_DATE.length];
		int totalEmployed = 0;
		for (int i = 0; i < studentDegreeList.size(); i++)
		{
			String uwNetid = studentDegreeList.get(i).getUwnetId();
			Student student = StudentDB.getSudentById(uwNetid);
			gradData[i][0] = student.getFirstName();
			gradData[i][1] = student.getLastName();
			gradData[i][2] = uwNetid;
			String degreeId = studentDegreeList.get(i).getDegreeId();
			Degree degree = DegreeDB.getDegree(degreeId);
			gradData[i][3] = degree.getProgram();
			List<StudentEmployment> employment 
			= StudentEmploymentDB.getStudentEmploymentsOfUWNetID(uwNetid);
			if (employment.size() != 0) {
				totalEmployed++;
			}
			String employer = "";
			String position = "";
			for (int j = 0; j < employment.size(); j++) {
				employer += (", " + employment.get(j).getEmployer());
				position += (", " + employment.get(j).getPosition());
			}
			gradData [i][4] = employer;
			gradData [i][5] = position;
		}
		return totalEmployed;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnSkill)
		{
			pnlContent.removeAll();
			pnlContent.add(createSkillSearchPanel());
			pnlContent.revalidate();
			this.repaint();
		} 
		else if (e.getSource() == btnGrad)
		{
			pnlContent.removeAll();
			pnlContent.add(createGraduationSearchPanel());
			pnlContent.revalidate();
			this.repaint();
		}
		else if (e.getSource() == ckbNoIntern)
		{
			if (ckbNoIntern.isSelected() && ckbIntern.isSelected())
			{
				ckbIntern.setSelected(false);
			}
		}
		else if (e.getSource() == ckbIntern)
		{
			if (ckbIntern.isSelected() && ckbNoIntern.isSelected())
			{
				ckbNoIntern.setSelected(false);
			}
		}
		else if (e.getSource() == ckbNoTransfer)
		{
			if (ckbNoTransfer.isSelected() && ckbTransfer.isSelected())
			{
				ckbTransfer.setSelected(false);
			}
		}
		else if (e.getSource() == ckbTransfer)
		{
			if (ckbTransfer.isSelected() && ckbNoTransfer.isSelected())
			{
				ckbNoTransfer.setSelected(false);
			}
		}
		else if (e.getSource() == btnSkillSubmit)
		{
			pnlContent.removeAll();
			pnlContent.add(getSkillSearchReport());
			pnlContent.revalidate();
			this.repaint();
		}
		
		else if (e.getSource() == btnGetStudentData)
		{
			String uwNetID = (String) tblStudents.getValueAt(tblStudents.getSelectedRow(), 2);
		} else if (e.getSource() == btnGraduationSubmit) {
			String name = txfYearName.getText().trim();
			if (name.isEmpty() || !name.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null, "Please Enter a Year!");
				txfYearName.setText("");
				lstTerms.clearSelection();
				return;
			} else {
				pnlContent.removeAll(); 
				try {
					pnlContent.add(getGraduationSearchReport());
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Report Generation Failed");
					return;
				}
				pnlContent.revalidate();
				this.repaint();
			}
		}
	}

	@Override
	public void tableChanged(TableModelEvent e)
	{
		//Table is not editable so fresh if anyone tries
		//to edit.
		btnSkillSubmit.doClick();
	}
	
}
