package model;

/**
 * This class represents a student's skill name.
 * 
 * @author Jieun Lee
 * @version 11-19-2016
 */
public class StudentSkill {

	/** A StudentDegree Id */
	private String myId;

	/** A student's UW net Id */
	private String myUwnetId;

	/** A Skill Id */
	private String mySkillId;

	/**
	 * Constructs a StudentSkill with given student's Uw net Id and skill id.
	 * 
	 * @param theUwnetId
	 * @param theSkillId
	 */
	public StudentSkill(final String theUwnetId, final String theSkillId) {
		setUwnetId(theUwnetId);
		setSkillId(theSkillId);
	}

	/**
	 * @return the myId
	 */
	public String getId() {
		return myId;
	}

	/**
	 * @return the myUwnetId
	 */
	public String getUwnetId() {
		return myUwnetId;
	}

	/**
	 * @return the mySkillId
	 */
	public String getSkillId() {
		return mySkillId;
	}

	/**
	 * @param theId
	 *            the myId to set
	 */
	public void setId(final String theId) {
		if (theId == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		this.myId = theId;
	}

	/**
	 * @param theUwnetId
	 *            the myUwnetId to set
	 */
	public void setUwnetId(final String theUwnetId) {
		if (theUwnetId == null) {
			throw new IllegalArgumentException("UW net id cannot be null");
		}
		this.myUwnetId = theUwnetId;
	}

	/**
	 * @param theSkillId
	 *            the mySkillId to set
	 */
	public void setSkillId(final String theSkillId) {
		if (theSkillId == null) {
			throw new IllegalArgumentException("Skill ID cannot be null");
		}
		this.mySkillId = theSkillId;
	}

}
