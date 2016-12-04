package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.StudentSkill;

public class StudentSkillDB {

	private static Connection myConnection;
	private static List<StudentSkill> myStudentSkillList;
	
	// getskills
	public static List<StudentSkill> getStudentSkills() throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentSkill;";

		myStudentSkillList = new ArrayList<StudentSkill>();
		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("studentSkillId");
				String uwId = rs.getString("uwnetId");
				String skillId = rs.getString("skillId");

				StudentSkill studentSkill = new StudentSkill(uwId, skillId);
				studentSkill.setId(id);

				myStudentSkillList.add(studentSkill);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return myStudentSkillList;
	}
	
	
	// get skill by stu-skill id
	public static StudentSkill getStudentSkills(final String theId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentSkill WHERE studentSkillId = '" + theId + "'";

		myStudentSkillList = new ArrayList<StudentSkill>();
		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("studentSkillId");
				String uwId = rs.getString("uwnetId");
				String skillId = rs.getString("skillId");


				StudentSkill studentSkill = new StudentSkill(uwId, skillId);
				studentSkill.setId(id);

				return studentSkill;
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
	
	public static List<StudentSkill> getStudentSKillsOfUWNetID(final String theUwnetId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentSkill WHERE uwnetId = '" + theUwnetId + "'";

		List<StudentSkill> filteredList = new ArrayList<StudentSkill>();
		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("studentSkillId");
				String uwId = rs.getString("uwnetId");
				String skillId = rs.getString("skillId");

				StudentSkill studentSkill = new StudentSkill(uwId, skillId);
				studentSkill.setId(id);
				
				filteredList.add(studentSkill);
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
	
	
	
	// add skill
	public static boolean addStudentSkill(StudentSkill theSkill) {
		String sql = "insert into StudentSkill(uwnetId, skillId) values "
				+ "(?, ?); ";

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
			preparedStatement.setString(1, theSkill.getUwnetId());
			preparedStatement.setString(2, theSkill.getSkillId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	
}
