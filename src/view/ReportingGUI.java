package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

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
	
	/**JPanel fields.*/
	private JPanel pnlButtons, pnlContent, pnlSkillSearch;
	
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
		
		return pnlSkillSearch;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
