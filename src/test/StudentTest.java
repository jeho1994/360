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
		new Student(null, "Doe", "jdoe");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetFirstName()
	{
		Student test = new Student("John", "Michael", "Doe", "jdoe");
		test.setFirstName("      ");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetMiddleName()
	{
		new Student("John", null, "Doe", "jdoe");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetEmail()
	{
		Student test = new Student("John", "Michael", "Doe", "jdoe");
		test.setEmail("jdoe@yahoocom");
	}
	
	@Test(expected = NullPointerException.class)
	public void testSetLastName()
	{
		new Student("John", null, "jdoe");
	}
	
	@Test(expected = NullPointerException.class)
	public void testSetUWNetID()
	{
		new Student("John", "Doe", null);
	}
}
