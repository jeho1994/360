package test;

import org.junit.Test;

import model.Student;

/**
 * Tests the Student class.
 * @author Thomas Van Riper
 * November 19, 2016
 */
public class StudentTest
{
	@Test(expected = NullPointerException.class)
	public void testConstructor()
	{
		new Student(null, "Michael", "Doe", "jdoe");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetFirstName()
	{
		Student test = new Student("John", "Michael", "Doe", "jdoe");
		test.setFirstName("      ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetEmail()
	{
		Student test = new Student("John", "Doe", "jdoe");
		test.setEmail("jdoegmail.com");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetMiddleName()
	{
		new Student("John", null, "Doe", "jdoe");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setLastName()
	{
		Student test = new Student("John", "Doe", "jdoe");
		test.setLastName("     ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetUWNetID()
	{
		Student test = new Student("John", "Doe", "jdoe");
		test.setUWNetID("j");
	}
}
