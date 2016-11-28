package student;

/**
 * This class represents a degree information, graduation term and year, GPA, and transfer college, for a student.
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
	
	/** A Degree */
//	private Degree myDegree;	//TODO - after Louis adds Degree class
	
	/** A graduation term */
	private String myTerm;
	
	/** A graduation year */
	private String myYear;
	
	/** A GPA */
	private double myGpa;
	
	/** A name of transfer college */
	private String myTransfer;
	
	//TODO add Degree theDegree for first parameter
	public StudentDegree(String theTerm, String theYear, double theGpa) {
//		myDegree = theDegree;
		setGraduationTerm(theTerm);
		myYear = theYear;
		myGpa = theGpa;
	}
	
	public void setId(String theId) {
		myId = theId;
	}
	
	//TODO - after adding Degree class
//	public void setDegree(Degree theDegree) {
//		myDegree = theDegree;
//	}
	
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
		if (theYear.length() != 4) {
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
	
	
	public String getId(String theId) {
		return myId;
	}
	
	//TODO - after adding Degree class
//	public void setDegree(Degree theDegree) {
//		myDegree = theDegree;
//	}
	
	public String getGraduationTerm(String theTerm) {
		return myTerm.toString();
	}
	
	public String getGraduationYear(int theYear) {
		return myYear;
	}
	
	public double getGPA(double theGpa) {
		return myGpa;
	}
	
	public String getTransferCollege(String theCollegeName) {
		return myTransfer;
	}
	
	
}
