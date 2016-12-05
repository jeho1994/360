package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import data.DegreeDB;
import data.SkillDB;
import model.Degree;
import model.Skill;
import model.Student;
import model.StudentCollection;
import model.StudentDegree;
import model.StudentSkill;

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
	
	/**JButton fields.*/
	private JButton btnGrad, btnGetStudentData, btnSkill, btnSkillSubmit;
	
	/**JCheckBox fields.*/
	private JCheckBox ckbIntern, ckbNoIntern, ckbTransfer, ckbNoTransfer;
	
	/**JList fields.*/
	private JList<Object> lstPrograms, lstSkills;
	
	/**JPanel fields.*/
	private JPanel pnlButtons, pnlContent, pnlSkillSearch, pnlResult;
	
	/**JScrollPane to scroll through offered programs and skills.*/
	private JScrollPane scrollPane, scrollPaneSkills, scrollPaneStudents;
	
	/**JTable fields.*/
	private JTable tblStudents;
	
	/**A 2D array to format a JTable of student data.*/
	private Object[][] studentData;
	
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
		if (ckbNoIntern.isSelected())
		{
			for (Student student : studentList)
			{
				if (!student.getInternship().isEmpty())
				{
					removalList.add(student);
				}
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
		scrollPaneStudents = new JScrollPane(tblStudents);
		
		btnGetStudentData = new JButton("View Student");
		btnGetStudentData.addActionListener(this);
		btnGetStudentData.setAlignmentX(CENTER_ALIGNMENT);
		
		pnlResult.add(lblStats);
		pnlResult.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlResult.add(scrollPaneStudents);
		pnlResult.add(Box.createRigidArea(new Dimension(0, 10)));
		pnlResult.add(btnGetStudentData);
		
		return pnlResult;
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
