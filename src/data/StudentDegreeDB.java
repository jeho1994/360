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
	
	private static Connection myConnection;
	private static List<StudentDegree> myStudentDegreeList;
	
	// get all student-degree
	public static List<StudentDegree> getStudentDegrees() throws SQLException {
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

				StudentDegree studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa, transfer);
				studentDegree.setId(id);
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
	public static StudentDegree getStudentDegree(String theId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentDegree WHERE studentDegreeId '" + theId + "'";

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

				StudentDegree studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa, transfer);
				studentDegree.setId(id);
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
	
	public static StudentDegree getStudentDegreeID(String theUwnetid, String theDegreeId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentDegree WHERE uwnetId = '" + theUwnetid + "' "
				+ "AND degreeId = '" + theDegreeId + "'";

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

				StudentDegree studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa, transfer);
				studentDegree.setId(id);
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
	
	public static List<StudentDegree> getStudentDegreeOfUWNetID(String theUwnetId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentDegree WHERE uwnetid = '" + theUwnetId + "'";

		List<StudentDegree> filteredList = new ArrayList<StudentDegree>();
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
				
				StudentDegree studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa, transfer);
				studentDegree.setId(id);
				filteredList.add(studentDegree);
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
	
	public static StudentDegree getStudentDegreeOfDegreeID(String theDegreeId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentDegree WHERE degreeId = '" + theDegreeId + "'";

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
				
				StudentDegree studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa, transfer);
				studentDegree.setId(id);
				
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
	public static boolean addStudentDegree(StudentDegree theDegree) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		String sql = "insert into StudentDegree(uwnetId, degreeId, graduation_term, graduation_year, gpa, transferCollege) values "
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
			preparedStatement.setString(1, theDegree.getUwnetId());
			preparedStatement.setString(2, theDegree.getDegreeId());
			preparedStatement.setString(3, theDegree.getGraduationTerm());
			preparedStatement.setString(4, theDegree.getGraduationYear());
			preparedStatement.setDouble(5, theDegree.getGPA());
			preparedStatement.setString(6, theDegree.getTransferCollege());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	// edit student-degree
	public static boolean updateStudentDegree(StudentDegree theDegree, String columnName, Object data) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		String id = theDegree.getId();
		String sql = "UPDATE StudentDegree SET `" + columnName
				+ "` = ?  WHERE studentDegreeId = ? ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = myConnection.prepareStatement(sql);
			if (data instanceof String) {
				preparedStatement.setString(1, (String) data);
			} else if (data instanceof Double) {
				preparedStatement.setDouble(1, (Double) data);
			} else {
				System.out.println(data.getClass().getName() + "");
			}
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
		return true;
	
	}
	
	public static List<StudentDegree> getStudentDegreeByYear(int annual, String quarter) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query;
		if (quarter == null) {
			query = "SELECT * " + "FROM StudentDegree WHERE graduation_year = " + annual + ";";
		} else  {
			query = "SELECT * " + "FROM StudentDegree WHERE graduation_year = " 
					+ annual + " AND graduation_term = '" + quarter + "';";
		}
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

				StudentDegree studentDegree = new StudentDegree(uwId, degreeId, term, year, gpa, transfer);
				studentDegree.setId(id);
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
	
	public static List<StudentDegree> getStudentDegreeByYear(int annual) throws SQLException {
		return getStudentDegreeByYear(annual, null);
	}
	
	public static boolean hasTransferred(final String theUWnetId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT COUNT(studentDegreeId) AS transferred" 
				+ " FROM StudentDegree WHERE uwnetId = '" + theUWnetId + "' "
				+ "AND transferCollege IS NOT NULL";

		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String result_str = rs.getString("transferred");
				int result = Integer.parseInt(result_str);
				if (result > 0) {
					return true;
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return false;
	}
	
	public static boolean isInProgram(final String theUWnetId, final String theDegreeId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT COUNT(studentDegreeId) AS inProgram" 
				+ " FROM StudentDegree WHERE uwnetId = '" + theUWnetId + "' "
				+ "AND degreeId = '" + theDegreeId + "'";

		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String result_str = rs.getString("inProgram");
				int result = Integer.parseInt(result_str);
				if (result > 0) {
					return true;
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return false;
	}

}
