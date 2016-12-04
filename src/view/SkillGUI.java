package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import component.HintTextField;
import data.SkillDB;
import model.Skill;

/**
 * SkillGUI lists all the skill sets of students. 
 * @author Louis Yang.
 * December 4th, 2016
 *
 */
public class SkillGUI extends JPanel implements ActionListener, TableModelListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 406659207743283297L;

	/**HintTextField fields.*/
	private HintTextField txfSkillName;
	
	/**JButton fields.*/
	private JButton btnAdd, btnAddSkill, btnList;
	
	/**JLabel fields.*/
	private JLabel lblWarning;
	
	/**JPanel fields.*/
	private JPanel pnlAdd, pnlButtons, pnlContent, pnlList;
	
	/**A scroll pane to list the skills.*/
	private JScrollPane scrollPane;
	
	/**A table to list the skill sets.*/
	private JTable tblList;
	
	/**
	 * Constructs the program GUI by calling methods to create
	 * components.
	 */
	public SkillGUI() {
		setLayout(new BorderLayout());
		createComponents();
		setVisible(true);
		setSize(500, 700);
	}
	
	/**
	 * Adds a new skill to the existing skill set.
	 */
	private void addSkill() {
		String name = txfSkillName.getText().trim();
		if (name.isEmpty()) {
			lblWarning.setText("Please enter a skill name.");
			return;
		}
		
		Skill skill = new Skill(name);
		SkillDB.addSkill(skill);
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
	 * Creates a panel to add a new skill.
	 * @return pnlAdd
	 */
	private JPanel createAddPanel() {
		pnlAdd = new JPanel();

				
		JPanel pnlInfo = new JPanel();
		txfSkillName = new HintTextField("Skill Name");
		txfSkillName.setColumns(20);
		pnlInfo.add(txfSkillName);
		
		JPanel pnlButton = new JPanel();
		btnAddSkill = new JButton("Add");
		btnAddSkill.addActionListener(this);
		pnlButton.add(btnAddSkill);
		
		pnlAdd.add(pnlInfo);
		pnlAdd.add(pnlButton);
		
		return pnlAdd;
	}

	/**
	 * Creates a button panel to navigate to view or add a skill.
	 * @return pnlButtons
	 */
	private JPanel createButtonPanel() {
		pnlButtons = new JPanel();
		
		btnList = new JButton("Skill List");
		btnList.addActionListener(this);
		
		btnAdd = new JButton("Add a Skill");
		btnAdd.addActionListener(this);
		
		pnlButtons.add(btnList);
		pnlButtons.add(btnAdd);
		
		return pnlButtons;
	}
	
	/**
	 * Creates a panel that lists all skills.
	 * @return pnlList
	 */
	private JPanel createListPanel() {
		pnlList = new JPanel();
		
		String[] columnNames = {"Skill"};
		tblList = new JTable(getData(), columnNames);
		tblList.getModel().addTableModelListener(this);
		scrollPane = new JScrollPane(tblList);
		pnlList.add(scrollPane);
		
		return pnlList;
	}
	
	/**
	 * Retrieves the skill data from the Skill database.
	 */
	private Object[][] getData() {
		List<Skill> skills = null;
		try {
			skills = SkillDB.getSkills();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Object[][] data = new Object[skills.size()][1];
		if (skills != null) {
			for (int i = 0; i < skills.size(); i++) {
				data[i][0] = skills.get(i).getSkillName();
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
		} else if (e.getSource() == btnAddSkill) {
			addSkill();
		}
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		//The programs are not editable, so refresh if anyone tries to
		//edit the list. Don't give them false hope.
		btnList.doClick();
	}
}
