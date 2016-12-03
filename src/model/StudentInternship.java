package model;

import java.sql.Date;

public class StudentInternship {
	
	/** Internship id */
	private String myId;
	/** A start date */
	private Date myStartDate;
	/** An end date */
	private Date myEndDate;
	/** Position Title */
	private String myPosition;
	/** Student's uwnetid */
	private String myUWId ;
	/** Employer Id */
	private String myEmployer;  
	
	
	/**
	 * Constructor for the Student Internship Class.
	 * It is used when internship information is available.
	 * 
	 * @param theStartDate
	 * @param theEndDate
	 * @param thePosition
	 * @param theUwId
	 * @param theEmpId
	 */
	public StudentInternship(Date theStartDate, Date theEndDate, String thePosition,
			String theUwId, String theEmployer) {
		myStartDate = theStartDate;
		myEndDate = theEndDate;
		myPosition = thePosition;
		myUWId = theUwId;
		myEmployer = theEmployer;
	}
	
	/**
	 * Gets the starting date of the internship.
	 * 
	 * @return myStartDate
	 */
	public Date getStartDate() {
		return myStartDate;
	}
	
	/**
	 * Sets the start date of the internship.
	 * 
	 * @param date
	 */
	public void setStartDate(Date date) {
		myStartDate = date;
	}
	
	/**
	 * Gets the end date of the internship.
	 * 
	 * @return myEndDate
	 */
	public Date getEndDate() {
		return myEndDate;
	}
	
	/**
	 * Sets the end date of the internship.
	 * 
	 * @param date
	 */
	public void setEndDate(Date date) {
		myEndDate = date;
	}
	
	/**
	 * Gets the position name of the internship.
	 * 
	 * @return myPosition
	 */
	public String getPosition() {
		return myPosition;
	}
	
	/**
	 * Sets the position name of the internship.
	 * 
	 * @param position the positon of the internship.
	 */
	public void setPosition(String position) {
		myPosition = position;
	}
	
	/**
	 * Gets the student uwId.
	 * 
	 * @return MyUWId
	 */
	public String getUWId() {
		return myUWId;
	}
	
	/**
	 * Sets the uw id associated with the internship.
	 * 
	 * @param uwId
	 */
	public void setUWId(String uwId) {
		myUWId = uwId;
	}
	
	/**
	 * Gets the employer id of the internship.
	 * 
	 * @return myEmpId
	 */
	public String getEmployer() {
		return myEmployer;
	}
	
	/**
	 * Sets the employer id of the internship.
	 * 
	 * @param id
	 */
	public void setEmployer(String employer) {
		myEmployer = employer;
	}
	
	/**
	 * Gets the internship Id.
	 * 
	 * @return myId
	 */
	public String getId() {
		return myId;
	}
	
	/**
	 * Sets the internship Id.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		myId = id;
	}

}
