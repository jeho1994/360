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
		new Student(null, "Doe", 1111111, "jdoe");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithEmail()
	{
		new Student("John", "Doe", "jdoe2uw.net", 1111111, "jdoe");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithStudentNumber()
	{
		new Student("John", "Doe", 123456, "jdoe");
	}
}
