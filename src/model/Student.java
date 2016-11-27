package model;

/**
 * The Student class represents a unique University of Washington Tacoma
 * Institute of Technology student.
 * 
 * @author Thomas Van Riper November 19, 2016
 */
public class Student {
	
	/** A minimum length */
	private static final int STUDENT_NUM_MIN_LENGTH = 7;

	/** A student's degree */
	private StudentDegree degree;
	
	/** A student's employment */
	private StudentEmployment employment;
	
	/** A student's internship */
	// private StudentInternship intern;

	private String email;
	private String firstName;
	private String lastName;
	private int studentNumber;
	private String uwNetID;

	/**
	 * Constructs a new student with first name, last name, student number, and
	 * a UW Net ID.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param studentNumber
	 * @param uwNetID
	 */
	public Student(String firstName, String lastName, int studentNumber, String uwNetID) {
		if (firstName == null || lastName == null || uwNetID == null) {
			throw new NullPointerException("Value cannot be null.");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		setStudentNumber(studentNumber);
		this.uwNetID = uwNetID;
	}

	/**
	 * Constructs a new student with first name, last name, student number, and
	 * a UW Net ID, and an e-mail.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param studentNumber
	 * @param uwNetID
	 */
	public Student(String firstName, String lastName, String email, int studentNumber, String uwNetID) {
		this(firstName, lastName, studentNumber, uwNetID);
		setEmail(email);
	}

	/**
	 * Constructs a new student with a first name, last name, student number, UW
	 * Net ID, and degree information.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param studentNumber
	 * @param uwNetID
	 * @param degree
	 */
	public Student(String firstName, String lastName, int studentNumber, String uwNetID, StudentDegree degree) {
		this(firstName, lastName, studentNumber, uwNetID);
		this.degree = degree;
	}

	/**
	 * Constructs a student with a first name, last name, student number, UW Net
	 * ID, e-mail, and degree information.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param studentNumber
	 * @param uwNetID
	 * @param degree
	 */
	public Student(String firstName, String lastName, String email, int studentNumber, String uwNetID,
			StudentDegree degree) {
		this(firstName, lastName, email, studentNumber, uwNetID);
		this.degree = degree;
	}

	/**
	 * Returns the student's degree.
	 * 
	 * @return degree
	 */
	public StudentDegree getDegree() {
		return degree;
	}

	/**
	 * Sets the student's degree information.
	 * 
	 * @param degree
	 */
	public void setDegree(StudentDegree degree) {
		this.degree = degree;
	}

	/**
	 * Returns the student's employment information.
	 * 
	 * @return
	 */
	public StudentEmployment getEmployment() {
		return employment;
	}

	/**
	 * Set's the student's employment information.
	 * 
	 * @param employment
	 */
	public void setEmployment(StudentEmployment employment) {
		this.employment = employment;
	}

	/**
	 * TODO - after getting StudentInternship class Returns the student's
	 * internship information.
	 * 
	 * @return intern
	 */
	/*
	 * public StudentInternship getInternship() { return intern; }
	 */

	/**
	 * Sets the student's internship information.
	 * 
	 * @param intern
	 */
	/*
	 * public void setInternship(StudentInternship intern) { this.intern =
	 * intern; }
	 */

	/**
	 * Returns student's email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the student's e-mail.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		if (!email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException("Invalid e-mail address.");
		}

		this.email = email;
	}

	/**
	 * Returns student's first name.
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the student's first name.
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns student's last name.
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the student's last name.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the student's student ID number.
	 * 
	 * @return studentNumber
	 */
	public int getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(int studentNumber) {
		if (Integer.toString(studentNumber).length() != STUDENT_NUM_MIN_LENGTH) {
			throw new IllegalArgumentException("Student ID numbers must be seven digits.");
		}

		this.studentNumber = studentNumber;
	}

	/**
	 * Returns student's UW Net ID.
	 * 
	 * @return uwNetID
	 */
	public String getUWNetID() {
		return uwNetID;
	}

	/**
	 * Sets the student's UW Net ID.
	 * 
	 * @param uwNetID
	 */
	public void setUWNetID(String uwNetID) {
		this.uwNetID = uwNetID;
	}
}
