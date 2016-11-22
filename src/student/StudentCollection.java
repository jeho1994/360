package student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.StudentDB;

/**
 * StudentCollection represents a list of students. Includes methods
 * to add and search the list of students.
 * @author Thomas Van Riper
 * November 21, 2016
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
	public static int getNumOfStudentsEmployed()
	{
		int count = 0;
		List<Student> studentList = getStudents();
		for (Student student : studentList)
		{
			if (student.getEmployment != null)
			{
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Returns a list of all students in the database.
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
	
	/**
	 * Returns a list of students that match the parameter.
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
}
