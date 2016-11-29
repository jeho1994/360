package model;

/**
 * This class represents a graduation term and year, GPA, and transfer college, for a student.
 * The system allows add a degree.
 * 
 * @author Jieun Lee
 * @version 11-19-2016
 */
public class StudentDegree {
	
	/** A set of terms */
	public static String[] TERMSET = {"WINTER", "SPRING", "SUMMER", "FALL"};
	
	
	
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
	 * Constructs a StudentDegree with given obtained degree information.
	 * It is used for a student who is not a transferred student.
	 * 
	 * @param theUwnetId
	 * @param theDegreeId
	 * @param theTerm
	 * @param theYear
	 * @param theGpa
	 */
	public StudentDegree(final String theUwnetId, final String theDegreeId, String theTerm, String theYear, double theGpa) {
		setGraduationTerm(theTerm);
		setGraduationYear(theYear);
		setGPA(theGpa);
	}
	
	/**
	 * Constructs a StudentDegree with given obtained degree information and transfer college name.
	 * It is used for a student who is a transferred student.
	 * @param theUwnetId
	 * @param theDegreeId
	 * @param theTerm
	 * @param theYear
	 * @param theGpa
	 * @param theTransfer
	 */
	public StudentDegree(final String theUwnetId, final String theDegreeId, String theTerm, String theYear, double theGpa, String theTransfer) {
		setGraduationTerm(theTerm);
		setGraduationYear(theYear);
		setGPA(theGpa);
		setTransferCollege(theTransfer);
	}
	
	
	/*
	 * Setter
	 */
	
	/**
	 * 
	 * @param theId
	 */
	public void setId(String theId) {
		myId = theId;
	}
	
	public boolean setGraduationTerm(String theTerm) {
		for (String term: TERMSET) {
			if (term.equalsIgnoreCase(theTerm)) {
				myTerm = term;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param theYear
	 */
	public void setGraduationYear(String theYear) {
		if (theYear.length() != 4 || Integer.valueOf(theYear) < 1000 || Integer.valueOf(theYear) >9999) {
			throw new IllegalArgumentException("Invalid year. Year shoud be between 1000 and 9999");
		}
		myYear = theYear;
	}
	
	/**
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
	 * @param theUwnetId the myUwnetId to set
	 */
	public void setUwnetId(String theUwnetId) {
		this.myUwnetId = theUwnetId;
	}

	/**
	 * @param theDegreeId the myDegreeId to set
	 */
	public void setDegreeId(String theDegreeId) {
		this.myDegreeId = theDegreeId;
	}
	
	
	
	/*
	 * Getter
	 */
	
	/**
	 * 
	 * @return
	 */
	public String getId() {
		return myId;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getGraduationTerm() {
		return myTerm;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getGraduationYear() {
		return myYear;
	}
	
	public double getGPA() {
		return myGpa;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTransferCollege() {
		return myTransfer;
	}

	/**
	 * @return the myUwnetId
	 */
	public String getUwnetId() {
		return myUwnetId;
	}

	/**
	 * @return the myDegreeId
	 */
	public String getDegreeId() {
		return myDegreeId;
	}
	
}
