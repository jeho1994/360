package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.StudentEmployment;

public class StudentEmploymentDB {
	
	private static Connection myConnection;
	private static List<StudentEmployment> myStudentEmploymentList;
	
	// get all student-employments
	public static List<StudentEmployment> getStudentEmployments() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentEmployment;";

		myStudentEmploymentList = new ArrayList<StudentEmployment>();
		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("studentEmployId");
				String uwnetId = rs.getString("uwnetid");
				String employer = rs.getString("employer");
				String position = rs.getString("position");
				double salary = rs.getDouble("salary");
				Date date = rs.getDate("dateFrom");
				String comment = rs.getString("comment");

				StudentEmployment studentEmployment = new StudentEmployment(uwnetId, employer,  position, salary, date, comment);

				studentEmployment.setId(id);
				myStudentEmploymentList.add(studentEmployment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return myStudentEmploymentList;
	}
	
	public static List<StudentEmployment> getStudentEmploymentsOfUWNetID(final String theUwnetId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentEmployment WHERE uwnetId = '" + theUwnetId + "'";
		List<StudentEmployment> filteredList = new ArrayList<StudentEmployment>();
		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("studentEmployId");
				String uwnetId = rs.getString("uwnetid");
				String employer = rs.getString("employer");
				String position = rs.getString("position");
				double salary = rs.getDouble("salary");
				Date date = rs.getDate("dateFrom");
				String comment = rs.getString("comment");

				StudentEmployment studentEmployment = new StudentEmployment(uwnetId, employer,  position, salary, date, comment);

				studentEmployment.setId(id);
				filteredList.add(studentEmployment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return filteredList;
	}
	
	
	// get stu-emp(stuemp id)
	public static StudentEmployment getStudentEmployment(String theId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentEmployment WHERE studentEmploymentId = " + theId + ";";

		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("studentEmployId");
				String uwnetId = rs.getString("uwnetid");
				String employer = rs.getString("employer");
				String position = rs.getString("position");
				double salary = rs.getDouble("salary");
				Date date = rs.getDate("dateFrom");
				String comment = rs.getString("comment");

				StudentEmployment studentEmployment = new StudentEmployment(uwnetId, employer,  position, salary, date, comment);

				studentEmployment.setId(id);
				return studentEmployment;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return null;
	}
	
	
	
	
	// add
	public static boolean add(StudentEmployment theEmployemnt) {
		String sql = "insert into StudentEmployment(uwnetid, employer, position, salary, dateFrom, comment) values "
				+ "(?, ?, ?, ?, ?, ?); ";

		if (myConnection == null) {
			try {
				myConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = myConnection.prepareStatement(sql);
			preparedStatement.setString(1, theEmployemnt.getUwnetId());
			preparedStatement.setString(2, theEmployemnt.getEmployer());
			preparedStatement.setString(3, theEmployemnt.getPosition());
			preparedStatement.setDouble(4, theEmployemnt.getSalary());
			preparedStatement.setDate(5, theEmployemnt.getStartDate());
			preparedStatement.setString(6, theEmployemnt.getComment());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// edit
	public static String update(StudentEmployment theEmployment, String columnName, Object data) {
		
		String id = theEmployment.getId();
		String sql = "UPDATE StudentEmployment SET `" + columnName
				+ "` = ?  WHERE studentEmploymentId = ? ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = myConnection.prepareStatement(sql);
			if (data instanceof String) {
				preparedStatement.setString(1, (String) data);
			} else if (data instanceof Double) {
				preparedStatement.setDouble(1, (Double) data);
			} else if (data instanceof Date) { // for Date type
				preparedStatement.setDate(1, (Date) data);
			} else {
				System.out.println(data.getClass().getName() + "");
			}
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return "Error updating StudentEmployment: " + e.getMessage();
		}
		return "Updated StudentEmployment Successfully";
	
	}
	

}