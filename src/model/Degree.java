package model;


/**
 * Degree Class
 * 
 * @author Louis Yang
 *
 */
public class Degree {
	/** Degree id. */
	private String myId;
	/** Program name. */
	private String myProgram;
	/** Degree level. */
	private String myLevel;
	
	/**
	 * The Degree Constructor
	 * 
	 * @param program
	 * @param level
	 */
	public Degree(String program, String level) {
		myProgram = program;
		myLevel = level;
	}
	
	/**
	 * Gets the program name.
	 * 
	 * @return myProgram
	 */
	public String getProgram() {
		return myProgram;
	}
	
	/**
	 * Sets the program name.
	 * 
	 * @param program
	 */
	public void setProgram(String program) {
		myProgram = program;
	}
	
	/**
	 * Gets the Degree level.
	 * 
	 * @return myLevel
	 */
	public String getLevel() {
		return myLevel;
	}
	
	/**
	 * Sets the Degree level.
	 * 
	 * @param level
	 */
	public void setLevel(String level) {
		myLevel = level;
	}
	
	/**
	 * Sets the Degree Id.
	 * 
	 * @param id
	 */
	public void setId (String id) {
		myId = id;
	}
	
	/**
	 * Gets the Degree Id.
	 * 
	 * @return myId.
	 */
	public String getId () {
		return myId;
	}
}
