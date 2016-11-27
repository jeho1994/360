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
	
	/** A graduation term */
	private String myTerm;
	
	/** A graduation year */
	private String myYear;
	
	/** A GPA */
	private double myGpa;
	
	/** A name of transfer college */
	private String myTransfer;
	

	public StudentDegree(String theTerm, String theYear, double theGpa) {
		setGraduationTerm(theTerm);
		setGraduationYear(theYear);
		setGPA(theGpa);
	}
	
	public StudentDegree(String theTerm, String theYear, double theGpa, String theTransfer) {
		setGraduationTerm(theTerm);
		setGraduationYear(theYear);
		setGPA(theGpa);
		setTransferCollege(theTransfer);
	}
	
	
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
	
	public void setGraduationYear(String theYear) {
		if (theYear.length() != 4 || Integer.valueOf(theYear) < 1000 || Integer.valueOf(theYear) >9999) {
			throw new IllegalArgumentException("Invalid year. Year shoud be between 1000 and 9999");
		}
		myYear = theYear;
	}
	
	public void setGPA(double theGpa) {
		if (theGpa < 0.0 || theGpa > 4.0) {
			throw new IllegalArgumentException("Invalid GPA. GPA should be between 0.0 and 4.0");
		}
		myGpa = theGpa;
	}
	
	public void setTransferCollege(String theCollegeName) {
		myTransfer = theCollegeName;
	}


	public String getId() {
		return myId;
	}
	

	public String getGraduationTerm() {
		return myTerm;
	}
	
	public String getGraduationYear() {
		return myYear;
	}
	
	public double getGPA() {
		return myGpa;
	}
	
	public String getTransferCollege() {
		return myTransfer;
	}
	
	
}
