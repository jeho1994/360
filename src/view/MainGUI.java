package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * The main UI that has tabbed panes for the different parts of the system. It
 * consists of tabs for Student, Program, Skill and Report.
 * 
 * @author Jieun Lee
 * @version 12-06-2016
 */
public class MainGUI extends JFrame {

	/** A Color UW_PURPLE */
	public static final Color UW_PURPLE = new Color(51, 0, 111);
	
	/** A Color UW_GOLD */
	public static final Color UW_GOLD = new Color(232, 211, 162);
	
	/** A Color UW_METALIC_GOLD */
	public static final Color UW_METALIC_GOLD = new Color(145, 123, 76);
	
	/** A Color UW_LIGHT_GRAY */
	public static final Color UW_LIGHT_GRAY = new Color(216, 217, 218);
	
	/** A Color UW_DART_GRAY */
	public static final Color UW_DART_GRAY = new Color(153, 153, 153);
	
	/** A font for table title */
	public static final Font UW_TITLE_FONT = new Font("Lucida Grande", Font.BOLD, 12);
	
	/** A font for panel title */
	public static final Font UW_BIG_FONT = new Font("Lucida Grande", Font.BOLD, 16);

	/** A serial number for StudentGUI() */
	private static final long serialVersionUID = -5345670291679491949L;
	
	/** A ToolKit. */
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();

	/** The Dimension of the screen. */
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

	/**
	 * Run UW IT Student Tracker system.
	 * @param theArgs
	 */
	public static void main(String[] theArgs) {
		new MainGUI();
	}

	/**
	 * Launches the GUI.
	 */
	public MainGUI() {
		super("UWT IT Student Tracker - TCSS 360 GROUP 7");
		setBackground(Color.WHITE);

		crateComponents();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setSize(1248, 720);
		setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2, (int) (SCREEN_SIZE.getHeight() / 4 - getHeight() / 4));
	}

	/**
	 * Create the tabs for each part of the system.
	 */
	private void crateComponents() {
		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent student = makeTextPanel("Student");
		tabbedPane.addTab("Student", student);

		JComponent program = makeTextPanel("Program");
		tabbedPane.addTab("Program", program);

		JComponent skill = makeTextPanel("Skill");
		tabbedPane.addTab("Skill", skill);

		JComponent reporting = makeTextPanel("Reporting");
		tabbedPane.addTab("Reporting", reporting);

		tabbedPane.setSize(500, 1350);

		// add all panels to the frame
		add(createLogoPanel(), BorderLayout.WEST);
		add(tabbedPane, BorderLayout.CENTER);

	}

	/**
	 * Create Logo label on the left side.
	 * @return Image label
	 */
	private JLabel createLogoPanel() {
		final JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);

		final JLabel logoLabel = new JLabel();
		logoLabel.setBackground(Color.WHITE);
		ImageIcon logo = null;
		try {
			logo = new ImageIcon(ImageIO.read(new File("image/logo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logoLabel.setIcon(logo);
		panel.add(logoLabel);

		return logoLabel;
	}

	/**
	 * Create the particular part to add to the tab based on the type.
	 * 
	 * @param type of the system.
	 * @return the panel
	 */
	private JComponent makeTextPanel(String type) {
		final JPanel panel = new JPanel();

		if (type.equalsIgnoreCase("Student")) {
			panel.add(new StudentGUI());
		} else if (type.equalsIgnoreCase("Program")) {
			panel.add(new ProgramGUI());
		} else if (type.equalsIgnoreCase("Skill")) {
			panel.add(new SkillGUI());
		} else {
			panel.add(new ReportingGUI());
		}
		return panel;
	}

}
