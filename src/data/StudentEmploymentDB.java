package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.StudentEmployment;

public class StudentEmploymentDB {
	
	private Connection myConnection;
	private List<StudentEmployment> myStudentEmploymentList;
	
	// get all student-employments
	public List<StudentEmployment> getStudentEmployments() throws SQLException {
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
				String employmentId = rs.getString("employId"); //TODO - employer instead of Id
				String position = rs.getString("position");
				double salary = rs.getDouble("salary");
				String date_str = rs.getString("dateFrom");
				LocalDate date = LocalDate.parse(date_str, StudentEmployment.DATE_FORMAT);
				String comment = rs.getString("comment");

				StudentEmployment studentEmployment = null;
				
				if (employmentId == null) {
					studentEmployment = new StudentEmployment(uwnetId, comment);
					studentEmployment.setId(id);
				} else {
					studentEmployment = new StudentEmployment(uwnetId, employmentId,  position, salary, date);
					studentEmployment.setId(id);
				}
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
	
	
	
	// get stu-emp(stuemp id)
	public StudentEmployment getStudentEmployment(String theId) throws SQLException {
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
				String employmentId = rs.getString("employId");
				String position = rs.getString("position");
				double salary = rs.getDouble("salary");
				String date_str = rs.getString("dateFrom");
				LocalDate date = LocalDate.parse(date_str, StudentEmployment.DATE_FORMAT);
				String comment = rs.getString("comment");

				StudentEmployment studentEmployment = null;
				
				if (employmentId == null) {
					studentEmployment = new StudentEmployment(uwnetId, comment);
					studentEmployment.setId(id);
				} else {
					studentEmployment = new StudentEmployment(uwnetId, employmentId,  position, salary, date);
					studentEmployment.setId(id);
				}
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
	public String addStudentEmployment(StudentEmployment theEmployemnt) {
		String allsql = "insert into StudentEmployment(studentEmployId, uwnetid, employId, position, salary, dateFrom) values "
				+ "(?, ?, ?, ?, ?, ?); ";
		String commentsql = "insert into StudentEmployment(studentEmployId, uwnetid, comment) values "
				+ "(?, ?, ?); ";

		if (myConnection == null) {
			try {
				myConnection = DataConnection.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		PreparedStatement preparedStatement = null;
		try {
			
			if (theEmployemnt.getEmploymentId() != null) {
				preparedStatement = myConnection.prepareStatement(allsql);
				preparedStatement.setString(1, theEmployemnt.getId());
				preparedStatement.setString(2, theEmployemnt.getUwnetId());
				preparedStatement.setString(3, theEmployemnt.getEmploymentId());
				preparedStatement.setString(4, theEmployemnt.getPosition());
				preparedStatement.setDouble(5, theEmployemnt.getSalary());
				preparedStatement.setString(6, theEmployemnt.getStartDate().format(StudentEmployment.DATE_FORMAT));
				
			} else {
				preparedStatement = myConnection.prepareStatement(commentsql);
				preparedStatement.setString(1, theEmployemnt.getId());
				preparedStatement.setString(2, theEmployemnt.getUwnetId());
				preparedStatement.setString(3, theEmployemnt.getComment());
			}

	
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding StudentEmployment: " + e.getMessage();
		}
		return "Added StudentEmployment Successfully";
	}
	
	// edit
	public String updateStudentEmployment(StudentEmployment theEmployment, String columnName, Object data) {
		
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
			} else if (data instanceof LocalDate) { // for Date type
				preparedStatement.setDate(1, Date.valueOf((LocalDate) data));
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