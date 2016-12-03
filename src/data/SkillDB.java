package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Skill;

/**
 * SkillDB represents the database of Skills.
 * @author Louis Yang.

 */
public class SkillDB
{
	private static Connection connection;
	private static List<Skill> skillList;
	
	/**
	 * Updates the database with a new internship.
	 * @param StudentInternship
	 * @return True if the student is added, false otherwise.
	 */
	public static boolean addSkill(Skill skill)
	{
		String sql = "INSERT INTO Skill(skillname)" + " VALUES(?);";
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
			preparedStatement.setString(1, skill.getSkillName());
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
	 * Returns a list of skills.
	 * @return A list of skills.
	 * @throws SQLException
	 */
	public static List<Skill> getSkills() throws SQLException
	{
		if (connection == null)
		{
			connection = DataConnection.getConnection();
		}
		
		Statement statement = null;
		String query = "SELECT * FROM Skill";
		skillList = new ArrayList<Skill>();
		try
		{
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next())
			{
				int skillId = rs.getInt("sillId");
				String name = rs.getString("skillName");
				
				Skill skill = new Skill(name);
				skill.setId(Integer.toString(skillId));
				skillList.add(skill);
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
		
		return skillList;
	}
	
	/**
	 * Returns a list of skills that match the parameter.
	 * @param name
	 * @return A list of skills.
	 * @throws SQLException
	 */
	public static List<Skill> getSkill(String name) throws SQLException
	{
		List<Skill> filterList = new ArrayList<Skill>();
		if (skillList == null)
		{
			getSkills();
		}
		
		name = name.toLowerCase();
		for (Skill skill : skillList)
		{
			if (name.contains(skill.getSkillName().toLowerCase())) {
				filterList.add(skill);
			}
		}
		
		return filterList;
	}
	
	
	/**
	 * Returns an skill that matches the parameter.
	 * @param id
	 * @return A skill.
	 * @throws SQLException
	 */
	public static Skill getSkillByID(String id) throws SQLException
	{
		if (skillList == null)
		{
			getSkills();
		}
		
		for (Skill skill : skillList)
		{
			if (skill.getId() == id)
			{
				return skill;
			}
		}
		
		return null;
	}
	
	/**
	 * Updates the skills in the Skill DB. 
	 * 
	 * @param skill
	 * @param columnName
	 * @param data
	 * @return
	 */
	public static String updateSkills(Skill skill, String columnName, Object data) {

		String id = skill.getId();
		String sql = "UPDATE Skill SET `" + columnName
				+ "` = ?  WHERE sillId = ? ";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, (String) data); 
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			return "Error updating Skill: " + e.getMessage();
		}
		return "Updated Skill Successfully";

	}
}
