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

	private Connection myConnection;
	private List<StudentSkill> myStudentSkillList;
	
	// getskills
	public List<StudentSkill> getStudentSkills() throws SQLException {
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
				String id = rs.getString("studentDegreeId");
				String uwId = rs.getString("uwnetId");
				String skillId = rs.getString("degreeId");

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
	public StudentSkill getStudentSkills(final String theId) throws SQLException {
		if (myConnection == null) {
			myConnection = DataConnection.getConnection();
		}
		Statement stmt = null;
		String query = "SELECT * " + "FROM StudentSkill WHERE studentSkillId = " + theId + ";";

		myStudentSkillList = new ArrayList<StudentSkill>();
		try {
			stmt = myConnection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("studentDegreeId");
				String uwId = rs.getString("uwnetId");
				String skillId = rs.getString("degreeId");

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
	
	
	
	// add skill
	public String addStudentSkill(StudentSkill theSkill) {
		String sql = "insert into StudentSkill(studentSkillId, uwnetId, skillId) values "
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
			preparedStatement = myConnection.prepareStatement(sql);
			preparedStatement.setString(1, theSkill.getId());
			preparedStatement.setString(1, theSkill.getUwnetId());
			preparedStatement.setString(1, theSkill.getSkillId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error adding StudentSkill: " + e.getMessage();
		}
		return "Added StudentSkill Successfully";
	}
	
	
	
	
}
