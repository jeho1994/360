package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Student;

/**
 * StudentDB represents the database of students.
 * @author Thomas Van Riper
 * November 21, 2016
 * Last edited on November 23, 2016
 * Added updateEmail(), 
 */
public class StudentDB
{
	private static Connection connection;
	private static List<Student> studentList;
	
	/**
	 * Updates the database with a new student.
	 * @param student
	 * @return True if the student is added, false otherwise.
	 */
	public static boolean add(Student student)
	{
		String sql = "INSERT INTO Student(first, middle, last, uwNetID, email)" + " VALUES(?, ?, ?, ?, ?);";

		String noMiddlesql = "INSERT INTO Student(first, last, uwNetID, email)" + " VALUES(?, ?, ?, ?)";

		if (connection == null)
		{
			try
			{
				connection = DataConnection.getConnection();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		PreparedStatement preparedStatement = null;
		try
		{
			if (student.getMiddleName() != null) {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, student.getFirstName());
				preparedStatement.setString(2, student.getMiddleName());
				preparedStatement.setString(3, student.getLastName());
				preparedStatement.setString(4, student.getUWNetID());
				preparedStatement.setString(5, student.getEmail());
			} else {
				preparedStatement = connection.prepareStatement(noMiddlesql);
				preparedStatement.setString(1, student.getFirstName());
				preparedStatement.setString(2, student.getLastName());
				preparedStatement.setString(3, student.getUWNetID());
				preparedStatement.setString(4, student.getEmail());
			}
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns a list of students.
	 * @return A list of students.
	 * @throws SQLException
	 */
	public static List<Student> getStudents() throws SQLException
	{
		if (connection == null)
		{
			connection = DataConnection.getConnection();
		}
		
		Statement statement = null;
		String query = "SELECT * FROM Student";
		studentList = new ArrayList<Student>();
		try
		{
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next())
			{
				String firstName = rs.getString("first");
				String middleName = rs.getString("middle");
				String lastName = rs.getString("last");
				String uwNetID = rs.getString("uwNetID");
				String email = rs.getString("email");
				Student student = null;
				if (middleName != null)
				{
					student = new Student(firstName, middleName, lastName, uwNetID);
				}
				else
				{
					student = new Student(firstName, lastName, uwNetID);
				}
				
				if (email != null)
				{
					student.setEmail(email);
				}				

				studentList.add(student);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (statement != null)
			{
				statement.close();
			}
		}
		
		return studentList;
	}
	
	/**
	 * Returns a list of students that match the parameter.
	 * @param name
	 * @return A list of students.
	 * @throws SQLException
	 */
	public static List<Student> getStudents(String searchString) throws SQLException
	{
		List<Student> students = getStudents();
		List<Student> filterList = new ArrayList<Student>();
		
		searchString = searchString.toLowerCase();
		for (Student student : students)
		{
			if (searchString.contains(student.getFirstName().toLowerCase()) ||
					searchString.contains(student.getLastName().toLowerCase()))
			{
				filterList.add(student);
			}
		}
		
		for (Student student : studentList)
		{
			if (searchString.contains(student.getUWNetID()))
			{
				filterList.add(student);
			}
		}
		
		return filterList;
	}
	
	public static Student getSudentById(final String theUWNetID) throws SQLException {
		if (connection == null) {
			connection = DataConnection.getConnection();
		}
		
		Statement statement = null;
		String query = "SELECT * FROM Student WHERE uwNetID = '" + theUWNetID + "'";
		studentList = new ArrayList<Student>();
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next())
			{
				String firstName = rs.getString("first");
				String middleName = rs.getString("middle");
				String lastName = rs.getString("last");
				String uwNetID = rs.getString("uwNetID");
				String email = rs.getString("email");
				Student student = null;
				if (middleName != null) {
					student = new Student(firstName, middleName, lastName, uwNetID);
				} else {
					student = new Student(firstName, lastName, uwNetID);
				}
				if (email != null)
				{
					student.setEmail(email);
				}	
				return student;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return null;
	}
	
	
	
	/**
	 * Updates a student's e-mail address. No other fields should be updated in
	 * Student.
	 * @param student
	 * @param email
	 * @return True or false.
	 */
	public static boolean updateEmail(Student student, String email)
	{
		String id = student.getUWNetID();
		String sql = "UPDATE Student SET email = '" + email + "' WHERE uwNetID = '" + id + "'";
		PreparedStatement preparedStatement = null;
		try
		{
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
