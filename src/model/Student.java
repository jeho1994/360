package model;

/**
 * The Student class represents a unique University of Washington Tacoma
 * Institute of Technology student.
 * 
 * @author Thomas Van Riper November 19, 2016
 */
public class Student {
	private StudentDegree myDegree;
	private StudentEmployment myEmployment;
	private StudentInternship myInternship;
	
	private String myEmail;
	private String myFirstName;
	private String myMiddleName;
	private String myLastName;
	private String myUWNetID;

	/**
	 * Constructs a new student with first name, last name, and a UW NetID.
	 * @param theFirstName
	 * @param theLastName
	 * @param theUWNetID
	 */
	public Student(String theFirstName, String theLastName, String theUWNetID)
	{
		if (theFirstName == null || theLastName == null || theUWNetID == null)
		{
			throw new NullPointerException("Value cannot be null.");
		}
		
		setFirstName(theFirstName);
		setLastName(theLastName);
		myUWNetID = theUWNetID;
	}
	
	/**
	 * Constructs a new student with first name, middle name, last name, and
	 * a UW Net ID.
	 * 
	 * @param myFirstName
	 * @param myMiddleName
	 * @param myLastName
	 * @param myUWNetID
	 */
	public Student(String theFirstName, String theMiddleName, String theLastName, String theUWNetID) 
	{
		this(theFirstName, theLastName, theUWNetID);
		setMiddleName(theMiddleName);
	}

	/**
	 * Returns the student's degree.
	 * @return myDegree
	 */
	public StudentDegree getDegree()
	{
		return myDegree;
	}
	
	/**
	 * Sets the student's degree.
	 * @param theDegree
	 */
	public void setDegree(StudentDegree theDegree)
	{
		myDegree = theDegree;
	}
	
	/**
	 * Returns the student's employment.
	 * @return myEmployment
	 */
	public StudentEmployment getEmployment()
	{
		return myEmployment;
	}
	
	/**
	 * Sets the student's employment.
	 * @param theEmployment
	 */
	public void setEmployment(StudentEmployment theEmployment)
	{
		myEmployment = theEmployment;
	}
	
	/**
	 * Returns the student's internship.
	 * @return
	 */
	public StudentInternship getInternship()
	{
		return myInternship;
	}
	
	/**
	 * Sets the student's internship.
	 * @param theInternship
	 */
	public void setInternship(StudentInternship theInternship)
	{
		myInternship = theInternship;
	}
	
	/**
	 * Returns student's email.
	 * 
	 * @return email
	 */
	public String getEmail() 
	{
		return myEmail;
	}

	/**
	 * Sets the student's e-mail.
	 * 
	 * @param theEmail
	 */
	public void setEmail(String theEmail) 
	{
		if (!theEmail.contains("@") || !theEmail.contains(".")) 
		{
			throw new IllegalArgumentException("Invalid e-mail address.");
		}

		this.myEmail = theEmail.trim();
	}

	/**
	 * Returns student's first name.
	 * 
	 * @return myFirstName
	 */
	public String getFirstName() 
	{
		return myFirstName;
	}

	/**
	 * Sets the student's first name.
	 * 
	 * @param theFirstName
	 */
	public void setFirstName(String theFirstName) 
	{
		theFirstName = theFirstName.trim();
		validateParameters(theFirstName);
		this.myFirstName = theFirstName;
	}
	
	/**
	 * Returns student's middle name.
	 * @return myMiddleName
	 */
	public String getMiddleName()
	{
		return myMiddleName;
	}
	
	/**
	 * Sets the student's middle name.
	 * @param theMiddleName
	 */
	public void setMiddleName(String theMiddleName)
	{
		theMiddleName = theMiddleName.trim();
		if (theMiddleName.isEmpty())
		{
			this.myMiddleName = theMiddleName;
		}
		else
		{
			validateParameters(theMiddleName);
			this.myMiddleName = theMiddleName;
		}
	}

	/**
	 * Returns student's last name.
	 * 
	 * @return myLastName
	 */
	public String getLastName() 
	{
		return myLastName;
	}

	/**
	 * Sets the student's last name.
	 * 
	 * @param theLastName
	 */
	public void setLastName(String theLastName) 
	{
		theLastName = theLastName.trim();
		validateParameters(theLastName);
		this.myLastName = theLastName;
	}

	/**
	 * Returns student's UW Net ID.
	 * 
	 * @return myUWNetID
	 */
	public String getUWNetID() 
	{
		return myUWNetID;
	}

	/**
	 * Sets the student's UW Net ID.
	 * 
	 * @param theUWNetID
	 */
	public void setUWNetID(String theUWNetID) 
	{
		theUWNetID = theUWNetID.trim();
		validateParameters(theUWNetID);
		this.myUWNetID = theUWNetID;
	}
	
	/**
	 * Checks if parameter values are less than two characters long.
	 * @param theParameter
	 */
	private void validateParameters(String theParameter)
	{
		if (theParameter.length() < 2)
		{
			throw new IllegalArgumentException("Invalid value.");
		}
	}
}
