package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.StudentDB;

/**
 * StudentCollection represents a list of students. Includes methods to add and
 * search the list of students.
 * 
 * @author Thomas Van Riper November 21, 2016
 * Last edited on November 23, 2016
 * Added countGraduation(), getStudentsByGraduation(), and updateEmail()
 */
public class StudentCollection 
{
	private static StudentDB STUDENT_DB;

	/**
	 * Updates the collection with a new student.
	 * @param student
	 * @return True if the student is added, false otherwise.
	 */
	public static boolean add(Student student) 
	{
		if (STUDENT_DB == null) 
		{
			STUDENT_DB = new StudentDB();
		}

		boolean result = STUDENT_DB.add(student);
		return result;
	}

	/**
	 * Returns the number of students currently employed.
	 * @return The number of employed students.
	 */
	public static int countEmployed() 
	{
		int count = 0;
		List<Student> studentList = getStudents();
		for (Student student : studentList)
		{
			if (student.getEmployment() != null) 
			{
				count++;
			}
		}

		return count;
	}
	
	//TODO Remove parameter from getGraduationYear()
	/**
	 * Counts the number of students that graduated in
	 * the year given.
	 * @param year
	 * @return An integer representing the number of students.
	 */
	public static int countGraduation(int year)
	{
		return getStudentsByGraduation(year).size();
	}
	
	//TODO Remove parameter from getGraduationYear()
	/**
	 * Counts the number of students that graduated in the year and
	 * term given.
	 * @param year
	 * @param term
	 * @return An integer representing the number of students. 
	 */
	public static int countGraduation(int year, String term)
	{	
		return getStudentsByGraduation(year, term).size();
	}

	/**
	 * Returns a list of all students in the database.
	 * 
	 * @return A list of students.
	 */
	public static List<Student> getStudents() 
	{
		if (STUDENT_DB == null) 
		{
			STUDENT_DB = new StudentDB();
		}

		try 
		{
			return STUDENT_DB.getStudents();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return null;
	}
	
	//TODO Remove parameter from getGraduationYear()
	/**
	 * Returns a list of students that graduate(d) in the given year.
	 * @param year
	 * @return A list of students.
	 */
	public static List<Student> getStudentsByGraduation(int year)
	{
		List<Student> studentList = getStudents();
		for (Student student : studentList)
		{
			if (Integer.parseInt(student.getDegree().getGraduationYear(0)) != year)
			{
				studentList.remove(student);
			}
		}
		
		return studentList;
	}
	
	//TODO Remove parameter from getGraduationYear()
		/**
		 * Returns a list of students that graduate(d) in the given year
		 * and term.
		 * @param year
		 * @param term
		 * @return A list of students.
		 */
		public static List<Student> getStudentsByGraduation(int year, String term)
		{
			List<Student> studentList = getStudents();
			for (Student student : studentList)
			{
				if (Integer.parseInt(student.getDegree().getGraduationYear(0)) != year &&
						!student.getDegree().getGraduationTerm("").equals(term))
				{
					studentList.remove(student);
				}
			}
			
			return studentList;
		}
		
		/**
		 * Returns a list of students who possess the given skill.
		 * @param skill
		 * @return A list of students.
		 */
		public static List<Student> getStudentsBySkill(String skill)
		{
			List<Student> studentList = getStudents();
			List<Student> filterList = new ArrayList<Student>();
			for (Student student : studentList)
			{
				for (String studentSkill: student.getEmployment().getSkills())
				{
					if (studentSkill.equals(skill))
					{
						filterList.add(student);
					}
				}
			}
			
			return filterList;
		}

	/**
	 * Returns a list of students that match the parameter.
	 * 
	 * @param name
	 * @return A list of students.
	 */
	public static List<Student> search(String name) 
	{
		List<Student> studentList = new ArrayList<Student>();
		if (STUDENT_DB == null) 
		{
			STUDENT_DB = new StudentDB();
		}

		try 
		{
			return STUDENT_DB.getStudents(name);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return studentList;
	}

	/**
	 * Returns a student that matches the parameter.
	 * 
	 * @param id
	 * @return A student.
	 */
	public static Student search(int id) 
	{
		if (STUDENT_DB == null) 
		{
			STUDENT_DB = new StudentDB();
		}

		try 
		{
			return STUDENT_DB.getStudentByID(id);
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Updates a student's e-mail address. No other fields should be updated 
	 * in Student.
	 * @param student
	 * @param email
	 * @return True or false.
	 */
	public static boolean updateEmail(Student student, String email)
	{
		if (STUDENT_DB == null)
		{
			STUDENT_DB = new StudentDB();
		}
		
		boolean result = STUDENT_DB.updateEmail(student, email);
		return result;
	}
}
