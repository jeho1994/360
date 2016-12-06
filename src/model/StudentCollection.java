package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.StudentDB;
import data.StudentDegreeDB;
import data.StudentEmploymentDB;
import data.StudentInternshipDB;
import data.StudentSkillDB;

/**
 * StudentCollection represents a list of students. Includes methods to add and
 * search the list of students.
 * 
 * @author Thomas Van Riper November 21, 2016 Last edited on November 23, 2016
 * @author Jieun Lee December 05, 2016, added methods for ReportingGUI.
 */
public class StudentCollection {
	private static StudentDB STUDENT_DB;
	private static StudentDegreeDB STUDENT_DEGREE_DB;
	private static StudentInternshipDB STUDENT_INTERNSHIP_DB;
	private static StudentEmploymentDB STUDENT_EMPLOYMENT_DB;
	private static StudentSkillDB STUDENT_SKILL_DB;

	/**
	 * Updates the collection with a new student.
	 * 
	 * @param theStudent
	 * @return True if the student is added, false otherwise.
	 */
	public static boolean add(Student theStudent) {
		if (STUDENT_DB == null) {
			STUDENT_DB = new StudentDB();
		}

		@SuppressWarnings("static-access")
		boolean result = STUDENT_DB.add(theStudent);
		return result;
	}

	/**
	 * Counts the number of students that graduated in the year given.
	 * 
	 * @param year
	 * @return An integer representing the number of students.
	 */
	public static int countGraduation(int year) {
		return getStudentsByGraduation(year).size();
	}

	/**
	 * Counts the number of students that graduated in the year and term given.
	 * 
	 * @param year
	 * @param term
	 * @return An integer representing the number of students.
	 */
	public static int countGraduation(int year, String term) {
		return getStudentsByGraduation(year, term).size();
	}

	/**
	 * Returns a list of all students in the database.
	 * 
	 * @return A list of students.
	 */
	@SuppressWarnings("static-access")
	public static List<Student> getStudents() {
		if (STUDENT_DB == null) {
			STUDENT_DB = new StudentDB();
		}

		try {
			return STUDENT_DB.getStudents();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Returns a list of students that graduate(d) in the given year.
	 * 
	 * @param year
	 * @return A list of students.
	 */
	@SuppressWarnings("static-access")
	public static List<StudentDegree> getStudentsByGraduation(int year) {

		if (STUDENT_DEGREE_DB == null) {
			STUDENT_DEGREE_DB = new StudentDegreeDB();
		}
		try {
			return STUDENT_DEGREE_DB.getStudentDegreeByYear(year);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Returns a list of students that graduate(d) in the given year and term.
	 * 
	 * @param year
	 * @param term
	 * @return A list of students.
	 */
	@SuppressWarnings("static-access")
	public static List<StudentDegree> getStudentsByGraduation(int year, String term) {

		if (STUDENT_DEGREE_DB == null) {
			STUDENT_DEGREE_DB = new StudentDegreeDB();
		}
		try {
			return STUDENT_DEGREE_DB.getStudentDegreeByYear(year, term);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Returns a student whose UWnetid is given.
	 * 
	 * @param theUWNetID
	 * @return a student.
	 */
	@SuppressWarnings("static-access")
	public static Student getStudentById(final String theUWNetID) {
		if (STUDENT_DB == null) {
			STUDENT_DB = new StudentDB();
		}
		try {
			return STUDENT_DB.getSudentById(theUWNetID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a list of students that match the parameter.
	 * 
	 * @param name
	 * @return A list of students.
	 */
	@SuppressWarnings("static-access")
	public static List<Student> search(String name) {
		List<Student> studentList = new ArrayList<Student>();
		if (STUDENT_DB == null) {
			STUDENT_DB = new StudentDB();
		}

		try {
			return STUDENT_DB.getStudents(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentList;
	}

	/**
	 * Updates a student's e-mail address. No other fields should be updated in
	 * Student.
	 * 
	 * @param student
	 * @param email
	 * @return True or false.
	 */
	@SuppressWarnings("static-access")
	public static boolean updateEmail(Student student, String email) {
		if (STUDENT_DB == null) {
			STUDENT_DB = new StudentDB();
		}
		boolean result = STUDENT_DB.updateEmail(student, email);
		return result;
	}

	/**
	 * Check if the given student has an intership data in StudentInternship table
	 * 
	 * @param theUWnetId
	 * @return True if the student has at least one internship.
	 */
	@SuppressWarnings("static-access")
	public static boolean hasInternship(final String theUWnetId) {
		if (STUDENT_INTERNSHIP_DB == null) {
			STUDENT_INTERNSHIP_DB = new StudentInternshipDB();
		}
		boolean result = false;

		try {
			List<StudentInternship> internships = STUDENT_INTERNSHIP_DB.getInternshipsOfUWNetID(theUWnetId);
			if (internships != null && internships.size() > 0) {
				result = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Check if the given student has an Employment data (not comment) in StudentEmployment table.
	 * 
	 * @param theUWnetId
	 * @return True if the student has at least one employment information except comment
	 */
	@SuppressWarnings("static-access")
	public static boolean hasEmployment(final String theUWnetId) {
		if (STUDENT_EMPLOYMENT_DB == null) {
			STUDENT_EMPLOYMENT_DB = new StudentEmploymentDB();
		}
		boolean result = false;

		try {
			List<StudentEmployment> emp = STUDENT_EMPLOYMENT_DB.getStudentEmploymentsOfUWNetID(theUWnetId);

			if (emp != null && emp.size() > 0) {
				boolean hasEmp = false;
				for (int i = 0; i < emp.size(); i++) {
					if (emp.get(i).getComment() == null || emp.get(i).getComment().length() > 0) {
						hasEmp = false;
					} else {
						hasEmp = true;
						break;
					}
				}
				result = hasEmp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Check if the given student has transferred from college.
	 * 
	 * @param theUWnetId
	 * @return True if the student is transferred from another college.
	 */
	@SuppressWarnings("static-access")
	public static boolean hasTransferred(final String theUWnetId) {
		if (STUDENT_DEGREE_DB == null) {
			STUDENT_DEGREE_DB = new StudentDegreeDB();
		}
		boolean result = false;
		try {
			result = STUDENT_DEGREE_DB.hasTransferred(theUWnetId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Check if the student is in the given program.
	 * 
	 * @param theUWnetId
	 * @param theDegreeId
	 * @return True if the student is in the given program.
	 */
	@SuppressWarnings("static-access")
	public static boolean isInProgram(final String theUWnetId, final String theDegreeId) {
		if (STUDENT_DEGREE_DB == null) {
			STUDENT_DEGREE_DB = new StudentDegreeDB();
		}
		boolean result = false;
		try {
			result = STUDENT_DEGREE_DB.isInProgram(theUWnetId, theDegreeId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Check if the given student has the given skill
	 * 
	 * @param theUWnetId
	 * @param theSkillId
	 * @return True if the given student has the given skill
	 */
	@SuppressWarnings("static-access")
	public static boolean hasSkill(final String theUWnetId, final String theSkillId) {
		if (STUDENT_SKILL_DB == null) {
			STUDENT_SKILL_DB = new StudentSkillDB();
		}

		boolean result = false;
		try {
			List<StudentSkill> skills = STUDENT_SKILL_DB.getStudentSKillsOfUWNetID(theUWnetId);
			if (skills != null && skills.size() > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
