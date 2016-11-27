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
	
	/** A employer name */
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
	 * Construts a StudentEmployemnt with given employer, job position, salary, start date
	 * It is used when employment information is available.
	 * 
	 * @param theEmployer
	 * @param thePosition
	 * @param theSalary
	 * @param theStartDate
	 * @param theSkills
	 */
	public StudentEmployment(final String theEmployer, final String thePosition, final double theSalary, final LocalDate theStartDate) {
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
	public StudentEmployment(final String theComment) {
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
	 * Get a name of employer.
	 * @return the Employer
	 */
	public String getEmployer() {
		return myEmployer;
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

	/* Setter */

	/**
	 * Set StudentEmployment id with given Id.
	 * @param theId the Id to set
	 */
	public void setId(String theId) {
		this.myId = theId;
	}


	/**
	 * Set employer name with given name.
	 * @param theEmployer the myEmployer to set
	 */
	public void setEmployer(final String theEmployer) {
		this.myEmployer = theEmployer;
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

}
