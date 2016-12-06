package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.DataConnection;
import model.StudentInternship;


/**
 * StudentDB represents the database of student Internships.
 * @author Louis Yang.

 */
public class StudentInternshipDB
{
	private static Connection connection;
	private static List<StudentInternship> internshipList;
	
	/**
	 * Updates the database with a new internship.
	 * @param StudentInternship
	 * @return True if the student is added, false otherwise.
	 */
	public static boolean add(StudentInternship internship)
	{
		String sql = "INSERT INTO StudentInternship(dateFrom, dateTo"
				+ ", position, uwnetid, employer)" + 
				" VALUES(?, ?, ?, ?, ?);";
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
			preparedStatement.setDate(1, internship.getStartDate());
			preparedStatement.setDate(2, internship.getEndDate());
			preparedStatement.setString(3, internship.getPosition());
			preparedStatement.setString(4, internship.getUWId());
			preparedStatement.setString(5, internship.getEmployer());
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
	 * Returns a list of internships.
	 * @return A list of internships.
	 * @throws SQLException
	 */
	public static List<StudentInternship> getInternships() throws SQLException
	{
		if (connection == null)
		{
			connection = DataConnection.getConnection();
		}
		
		Statement statement = null;
		String query = "SELECT * FROM StudentInternship";
		internshipList = new ArrayList<StudentInternship>();
		try
		{
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next())
			{
				int internshipId = rs.getInt("StudentInternId");
				Date startDate = rs.getDate("dateFrom");
				Date endDate = rs.getDate("dateTo");
				String position = rs.getString("position");
				String uwnetid = rs.getString("uwnetid");
				String employer = rs.getString("employer");
				
				StudentInternship internship = new StudentInternship(startDate, endDate, position, 
							uwnetid, employer);
				internship.setId(Integer.toString(internshipId));
				internshipList.add(internship);
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
		
		return internshipList;
	}
	
	public static List<StudentInternship> getInternshipsOfUWNetID(final String theUwnetId) throws SQLException {
		if (connection == null)
		{
			connection = DataConnection.getConnection();
		}
		
		Statement statement = null;
		String query = "SELECT * FROM StudentInternship WHERE uwnetid = '" +theUwnetId + "'";
		List<StudentInternship> filteredList  = new ArrayList<StudentInternship>();
		try
		{
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next())
			{
				int internshipId = rs.getInt("StudentInternId");
				Date startDate = rs.getDate("dateFrom");
				Date endDate = rs.getDate("dateTo");
				String position = rs.getString("position");
				String uwnetid = rs.getString("uwnetid");
				String employer = rs.getString("employer");
				
				StudentInternship internship = new StudentInternship(startDate, endDate, position, 
							uwnetid, employer);
				internship.setId(Integer.toString(internshipId));
				filteredList.add(internship);
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
		
		return filteredList;
	}
	
	
	/**
	 * Returns a list of internships that match the parameter.
	 * @param name
	 * @return A list of internships.
	 * @throws SQLException
	 */
	public static List<StudentInternship> getInternship(String name) throws SQLException
	{
		List<StudentInternship> filterList = new ArrayList<StudentInternship>();
		if (internshipList == null)
		{
			getInternships();
		}
		
		name = name.toLowerCase();
		for (StudentInternship internship : internshipList)
		{
			if (name.contains(internship.getPosition().toLowerCase())) {
				filterList.add(internship);
			}
		}
		
		return filterList;
	}
	
	
	/**
	 * Returns an internship that matches the parameter.
	 * @param id
	 * @return An internship.
	 * @throws SQLException
	 */
	public static StudentInternship getStudentByID(String id) throws SQLException
	{
		if (internshipList == null)
		{
			getInternships();
		}
		
		for (StudentInternship internship : internshipList)
		{
			if (internship.getId() == id)
			{
				return internship;
			}
		}
		
		return null;
	}
	
	
	
	/**
	 * Updates the student internship information.
	 * 
	 * @param internship
	 * @param columnName
	 * @param data
	 * @return
	 */
	public static String updateStudentInternship(StudentInternship internship, String columnName, Object data) {

		String id = internship.getId();
		String sql = "UPDATE StudentInternship SET `" + columnName
				+ "` = ?  WHERE StudentInternId = ? ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data); 
			else if (data instanceof Date)
				preparedStatement.setDate(1, (Date) data);
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return "Error updating StudentInternship: " + e.getMessage();
		}
		return "Updated StudentInternship Successfully";

	}
}
