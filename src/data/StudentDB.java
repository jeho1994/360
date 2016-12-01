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
	private Connection connection;
	private List<Student> studentList;
	
	/**
	 * Updates the database with a new student.
	 * @param student
	 * @return True if the student is added, false otherwise.
	 */
	public boolean add(Student student)
	{
		String sql = "INSERT INTO Student(id, first, middle, last, uwNetID, email)" + 
				" VALUES(?, ?, ?, ?, ?, ?);";
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
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, student.getStudentNumber());
			preparedStatement.setString(2, student.getFirstName());
			preparedStatement.setString(3, student.getMiddleName());
			preparedStatement.setString(4, student.getLastName());
			preparedStatement.setString(5, student.getUWNetID());
			preparedStatement.setString(6, student.getEmail());
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
	public List<Student> getStudents() throws SQLException
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
				int studentNumber = rs.getInt("id");
				String firstName = rs.getString("first");
				String middleName = rs.getString("middle");
				String lastName = rs.getString("last");
				String uwNetID = rs.getString("uwNetID");
				String email = rs.getString("email");
				Student student = null;
				if (email != null)
				{
					student = new Student(firstName, middleName, lastName, studentNumber,
							uwNetID);
				}
				else
				{
					student = new Student(firstName, lastName, email, studentNumber,
							uwNetID);
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
	public List<Student> getStudents(String name) throws SQLException
	{
		List<Student> filterList = new ArrayList<Student>();
		if (studentList == null)
		{
			getStudents();
		}
		
		name = name.toLowerCase();
		for (Student student : studentList)
		{
			if (name.contains(student.getFirstName().toLowerCase()) ||
					name.contains(student.getLastName().toLowerCase()))
			{
				filterList.add(student);
			}
		}
		
		return filterList;
	}
	
	
	/**
	 * Returns a student that matches the parameter.
	 * @param id
	 * @return A student.
	 * @throws SQLException
	 */
	public Student getStudentByID(int id) throws SQLException
	{
		if (studentList == null)
		{
			getStudents();
		}
		
		for (Student student : studentList)
		{
			if (student.getStudentNumber() == id)
			{
				return student;
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
	public boolean updateEmail(Student student, String email)
	{
		int id = student.getStudentNumber();
		String sql = "UPDATE Student SET email = '" + email + "' WHERE id = " + id;
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
