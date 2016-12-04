package model;

/**
 * Skill Class.
 * 
 * @author jeho1994
 *
 */
public class Skill {
	/** The skill id. */
	private String myId;
	/** The skill name. */
	private String mySkillName;
	
	/**
	 * Skill Class constructor.
	 * 
	 * @param skillName
	 */
	public Skill(String skillName) {
		mySkillName = skillName;
	}
	
	/**
	 * Gets the skill name.
	 * 
	 * @return mySkillName.
	 */
	public String getSkillName() {
		return mySkillName;
	}
	
	/**
	 * Sets the skill name.
	 * 
	 * @param name
	 */
	public void setSkillName(String name) {
		mySkillName = name;
	}
	
	/**
	 * Sets the skill id.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		myId = id;
	}
	
	/**
	 * Gets the skill id.
	 * 
	 * @return myId
	 */
	public String getId() {
		return myId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mySkillName;
	}
	
}
