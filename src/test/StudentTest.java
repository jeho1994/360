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
	public void testConstructorWithEmail()
	{
		new Student("John", "Michael", "Doe", "jdoe2uw.net", "jdoe");
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetFirstName()
	{
		Student test = new Student("John", "Michael", "Doe", "jdoe");
		test.setFirstName("      ");
	}
}
