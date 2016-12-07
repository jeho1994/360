package model;

/**
 * This class represents degree information, including a graduation term and
 * year, GPA, and transfer college, for a student.
 * 
 * @author Jieun Lee
 * @version 11-19-2016
 */
public class StudentDegree {

	/** A set of terms */
	public static String[] TERMSET = { "Winter", "Spring", "Summer", "Fall" };

	/** A StudentDegree Id */
	private String myId;

	/** A student's UW net Id */
	private String myUwnetId;

	/** A Degree Id */
	private String myDegreeId;

	/** A graduation term */
	private String myTerm;

	/** A graduation year */
	private String myYear;

	/** A GPA */
	private double myGpa;

	/** A name of transfer college */
	private String myTransfer;

	/**
	 * Constructs a StudentDegree with given obtained degree information. It is
	 * used for a student who is not a transferred student.
	 * 
	 * @param theUwnetId
	 * @param theDegreeId
	 * @param theTerm
	 * @param theYear
	 * @param theGpa
	 */
	public StudentDegree(final String theUwnetId, final String theDegreeId, String theTerm, String theYear,
			double theGpa) {
		setUwnetId(theUwnetId);
		setDegreeId(theDegreeId);
		setGraduationTerm(theTerm);
		setGraduationYear(theYear);
		setGPA(theGpa);
	}

	/**
	 * Constructs a StudentDegree with given obtained degree information and
	 * transfer college name. It is used for a student who is a transferred
	 * student.
	 * 
	 * @param theUwnetId
	 * @param theDegreeId
	 * @param theTerm
	 * @param theYear
	 * @param theGpa
	 * @param theTransfer
	 */
	public StudentDegree(final String theUwnetId, final String theDegreeId, String theTerm, String theYear,
			double theGpa, String theTransfer) {
		setUwnetId(theUwnetId);
		setDegreeId(theDegreeId);
		setGraduationTerm(theTerm);
		setGraduationYear(theYear);
		setGPA(theGpa);
		setTransferCollege(theTransfer);
	}

	/*
	 * Setter
	 */

	/**
	 * Set StudentDegree id.
	 * 
	 * @param theId
	 */
	public void setId(String theId) {
		if (theId == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}
		myId = theId;
	}

	/**
	 * Set graduatio term.
	 * 
	 * @param theTerm
	 */
	public void setGraduationTerm(String theTerm) {
		if (theTerm == null) {
			throw new IllegalArgumentException("Id cannot be null");
		}

		boolean isInSet = false;
		String t = null;
		for (String term : TERMSET) {
			if (theTerm.equalsIgnoreCase(term)) {
				isInSet = true;
				t = term;
				break;
			}
		}
		if (isInSet) {
			myTerm = t;
		} else {
			throw new IllegalArgumentException(
					"Invalid terms. Term must be one of following: \"Winter\", \"Spring\", \"Summer\", \"Fall\"");
		}

	}

	/**
	 * Set graduation year.
	 * 
	 * @param theYear
	 */
	public void setGraduationYear(String theYear) {
		if (theYear == null || Integer.parseInt(theYear) < 1000 || Integer.parseInt(theYear) > 9999) {
			throw new IllegalArgumentException("Invalid year. Year shoud be between \"1000\" and \"9999\"");
		}
		myYear = theYear;
	}

	/**
	 * Set GPA.
	 * 
	 * @param theGpa
	 */
	public void setGPA(double theGpa) {
		if (theGpa < 0.0 || theGpa > 4.0) {
			throw new IllegalArgumentException("Invalid GPA. GPA should be between 0.0 and 4.0");
		}
		myGpa = theGpa;
	}

	public void setTransferCollege(String theCollegeName) {
		myTransfer = theCollegeName;
	}

	/**
	 * Set UW net id.
	 * 
	 * @param theUwnetId
	 *            the myUwnetId to set
	 */
	public void setUwnetId(String theUwnetId) {
		if (theUwnetId == null) {
			throw new IllegalArgumentException("UW net id cannot be null");
		}
		this.myUwnetId = theUwnetId;
	}

	/**
	 * Set Degree id.
	 * 
	 * @param theDegreeId
	 *            the myDegreeId to set
	 */
	public void setDegreeId(String theDegreeId) {
		if (theDegreeId == null || Integer.parseInt(theDegreeId) < 0) {
			throw new IllegalArgumentException("Degree id should be non-negative number");
		}
		this.myDegreeId = theDegreeId;
	}

	/*
	 * Getter
	 */

	/**
	 * Get StudentDegree Id.
	 * 
	 * @return StudentDegree Id.
	 */
	public String getId() {
		return myId;
	}

	/**
	 * Get graduation term.
	 * 
	 * @return graduation term.
	 */
	public String getGraduationTerm() {
		return myTerm;
	}

	/**
	 * Get graduation year.
	 * 
	 * @return graduation year.
	 */
	public String getGraduationYear() {
		return myYear;
	}

	/**
	 * Get GPA
	 * 
	 * @return GPA
	 */
	public double getGPA() {
		return myGpa;
	}

	/**
	 * Get transfer college name
	 * 
	 * @return transfer college name
	 */
	public String getTransferCollege() {
		return myTransfer;
	}

	/**
	 * Get UW net id.
	 * 
	 * @return UW net id.
	 */
	public String getUwnetId() {
		return myUwnetId;
	}

	/**
	 * Get Degree id.
	 * 
	 * @return Degree id.
	 */
	public String getDegreeId() {
		return myDegreeId;
	}

}
