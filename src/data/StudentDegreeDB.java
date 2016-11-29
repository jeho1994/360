package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.StudentDegree;

public class StudentDegreeDB {
	
	private Connection myConnection;
	private List<StudentDegree> myStudentDegreeList;
	
	// get all student-degree
	public List<StudentDegree> getStudentDegrees() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentDegree;";

		myStudentDegreeList = new ArrayList<StudentDegree>();
		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("studentDegreeId");
				String uwId = rs.getString("uwnetId");
				String degreeId = rs.getString("degreeId");
				String term = rs.getString("graduation_term");
				String year = rs.getString("graduation_year");
				double gpa = rs.getDouble("gpa");
				String transfer = rs.getString("transferCollege");

				StudentDegree studentDegree = null;
				
				if (transfer == null) {
					studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa);
					studentDegree.setId(id);
				} else {
					studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa, transfer);
					studentDegree.setId(id);
				}
				myStudentDegreeList.add(studentDegree);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return myStudentDegreeList;
	}
	
	
	// get student-degree
	public StudentDegree getStudentDegree(String theId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentDegree WHERE studentDegreeId = " + theId + ";";

		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("studentDegreeId");
				String uwId = rs.getString("uwnetId");
				String degreeId = rs.getString("degreeId");
				String term = rs.getString("graduation_term");
				String year = rs.getString("graduation_year");
				double gpa = rs.getDouble("gpa");
				String transfer = rs.getString("transferCollege");

				StudentDegree studentDegree = null;
				
				if (transfer == null) {
					studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa);
					studentDegree.setId(id);
				} else {
					studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa, transfer);
					studentDegree.setId(id);
				}
				return studentDegree;
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
	
	
	// add student-degree
	public String addStudentDegree(StudentDegree theDegree) {
		String sql = "insert into StudentDegree(studentDegreeId, uwnetId, degreeId, graduation_term, graduation_year, gpa, transferCollege) values "
				+ "(?, ?, ?, ?, ?, ?, ?); ";

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
			preparedStatement.setString(1, theDegree.getId());
			preparedStatement.setString(1, theDegree.getUwnetId());
			preparedStatement.setString(1, theDegree.getDegreeId());
			preparedStatement.setString(2, theDegree.getGraduationTerm());
			preparedStatement.setString(3, theDegree.getGraduationYear());
			preparedStatement.setDouble(4, theDegree.getGPA());
			preparedStatement.setString(5, theDegree.getTransferCollege());
	
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding StudentDegree: " + e.getMessage();
		}
		return "Added StudentDegree Successfully";
	}
	
	
	// edit student-degree
	public String updateStudentDegree(StudentDegree theDegree, String columnName, Object data) {
		
		String id = theDegree.getId();
		String sql = "UPDATE StudentDegree SET `" + columnName
				+ "` = ?  WHERE studentDegreeId = ? ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = myConnection.prepareStatement(sql);
			if (data instanceof String)
				preparedStatement.setString(1, (String) data); 
			else if (data instanceof Double)
				preparedStatement.setDouble(1, (Double) data);
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return "Error updating StudentDegree: " + e.getMessage();
		}
		return "Updated StudentDegree Successfully";
	
	}
}