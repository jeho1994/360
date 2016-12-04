package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import data.DegreeDB;
import model.Degree;
import model.Student;
import model.StudentCollection;

/**
 * ReportingGUI displays the various options for running reports on student
 * data.
 * @author Thomas Van Riper
 * December 2nd, 2016
 *
 */
public class ReportingGUI extends JPanel implements ActionListener
{
	/**
	 * An auto generated serial version UID.
	 */
	private static final long serialVersionUID = -412717764910629392L;
	
	/**JButton fields.*/
	private JButton btnGrad, btnSkill;
	
	/**JCheckBox fields.*/
	private JCheckBox ckbIntern, ckbNoIntern, ckbTransfer, ckbNoTransfer;
	
	/**JList fields.*/
	private JList<Object> lstPrograms;
	
	/**JPanel fields.*/
	private JPanel pnlButtons, pnlContent, pnlSkillSearch;
	
	/**JScrollPane to scroll through offered programs.*/
	private JScrollPane scrollPane;
	
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
		
		JPanel pnlIntern = new JPanel();
		pnlIntern.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlIntern.setLayout(new GridLayout(3, 0));
		ckbNoIntern = new JCheckBox("Did not complete an internship");
		ckbNoIntern.addActionListener(this);
		ckbIntern = new JCheckBox("Completed an internship");
		ckbIntern.addActionListener(this);
		JPanel pnlInternLabel = new JPanel();
		pnlInternLabel.add(new JLabel("Internship"));
		pnlIntern.add(pnlInternLabel);
		pnlIntern.add(ckbNoIntern);
		pnlIntern.add(ckbIntern);
		
		JPanel pnlTransfer = new JPanel();
		pnlTransfer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlTransfer.setLayout(new GridLayout(3, 0));
		ckbNoTransfer = new JCheckBox("Did not transfer");
		ckbNoTransfer.addActionListener(this);
		ckbTransfer = new JCheckBox("Transferred");
		ckbTransfer.addActionListener(this);
		JPanel pnlTransferLabel = new JPanel();
		pnlTransferLabel.add(new JLabel("Transfer Student"));
		pnlTransfer.add(pnlTransferLabel);
		pnlTransfer.add(ckbNoTransfer);
		pnlTransfer.add(ckbTransfer);
		
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
		
		pnlSkillSearch.add(pnlIntern);
		pnlSkillSearch.add(pnlTransfer);
		pnlSkillSearch.add(scrollPane);
		
		return pnlSkillSearch;
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
		
	}
}
