package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class represents an employemnt information, employer, job position,
 * starting date, salary, and skills used at workplace, for a student. The
 * system allows add a degree.
 * 
 * @author Jieun Lee
 * @version 11-19-2016
 */
public class StudentEmployment {
	
	public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/** A StudentEmployment Id */
	private String myId;
	
	/** A student's UW net Id */
	private String myUwnetId;
	
	/** An employment Id */
	private String myEmployer;
	
	/** A job position */
	private String myPosition;
	
	/** A salary */
	private double mySalary;
	
	/** A start date */
	private LocalDate myStartDate;
	
	/** A comment */
	private String myComment;
	

	/* Constructors */
	
	/**
	 * Constructs a StudentEmployment with the given employment data.
	 * It is used when employment information is available.
	 * 
	 * @param theUwnetId
	 * @param theEmployer
	 * @param thePosition
	 * @param theSalary
	 * @param theStartDate
	 */
	public StudentEmployment(final String theUwnetId, final String theEmployer, final String thePosition, final double theSalary, final LocalDate theStartDate) {
		setUwnetId(theUwnetId);
		setEmployer(theEmployer);
		setPosition(thePosition);
		setSalary(theSalary);
		setStartDate(theStartDate);
	}
	


	/**
	 * Constructs a StudentEmployment with the given comment.
	 * It is used when no employment information is available.
	 * 
	 * @param comment
	 */
	public StudentEmployment(final String theUwnetid, final String theComment) {
		setComment(theComment);
	}

	
	/* Getter */

	/**
	 * Get a StudentEmployment Id.
	 * @return the Id
	 */
	public String getId() {
		return myId;
	}



	/**
	 * Get a job position.
	 * @return the Position
	 */
	public String getPosition() {
		return myPosition;
	}


	/**
	 * Get a salary.
	 * @return the Salary
	 */
	public double getSalary() {
		return mySalary;
	}


	/**
	 * Get a start date. (Format: 'yyyy-mm-dd')
	 * @return the StartDate
	 */
	public LocalDate getStartDate() {
		return myStartDate;
	}

	/**
	 * Get a comment.
	 * @return the Comment
	 */
	public String getComment() {
		return myComment;
	}
	
	/**
	 * @return the myUwnetId
	 */
	public String getUwnetId() {
		return myUwnetId;
	}

	/**
	 * @return the myEmployer
	 */
	public String getEmployer() {
		return myEmployer;
	}

	
	

	/* Setter */

	/**
	 * Set StudentEmployment id with given Id.
	 * @param theId the Id to set
	 */
	public void setId(String theId) {
		this.myId = theId;
	}

	/**
	 * Set job position with given.
	 * @param thePosition the myPosition to set
	 */
	public void setPosition(final String thePosition) {
		this.myPosition = thePosition;
	}


	/**
	 * Set salary with given.
	 * @param theSalary the mySalary to set
	 */
	public void setSalary(final double theSalary) {
		if (theSalary < 0) {
			throw new IllegalArgumentException("The salary cannot be negative");
		}
		this.mySalary = theSalary;
	}


	/**
	 * Set start date with given date.
	 * @param theStartDate the myStartDate to set
	 */
	public void setStartDate(final LocalDate theStartDate) {
		this.myStartDate = theStartDate;
	}


	/**
	 * Set a comment with given.
	 * @param theComment the myComment to set
	 */
	public void setComment(final String theComment) {
		this.myComment = theComment;
	}
	
	/**
	 * @param theUwnetId the myUwnetId to set
	 */
	public void setUwnetId(String theUwnetId) {
		this.myUwnetId = theUwnetId;
	}

	/**
	 * @param theEmploymentId the myEmploymentId to set
	 */
	public void setEmployer(String theEmployer) {
		this.myEmployer = theEmployer;
	}


}
