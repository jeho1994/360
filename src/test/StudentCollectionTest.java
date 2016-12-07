package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import model.Student;
import model.StudentCollection;

public class StudentCollectionTest
{
	@Test
	public void testAddStudent()
	{
		Student test = new Student("John", "Doe", "jdoe");
		assertTrue(StudentCollection.add(test));
	}
	
	@Test
	public void testCountGraduation()
	{
		assertTrue(StudentCollection.countGraduation(2016) >= 0);
	}
	
	@Test public void testCountGraduationForSize()
	{
		assertTrue(StudentCollection.countGraduation(2016) == 
				StudentCollection.getStudentsByGraduation(2016).size());
	}
	
	@Test
	public void testCountGraduationByYearAndTerm()
	{
		assertTrue(StudentCollection.countGraduation(2016, "Fall") >= 0);
	}
	
	@Test
	public void testGetStudents()
	{
		assertNotNull(StudentCollection.getStudents());
	}
	
	@Test
	public void testGetStudentByID()
	{
		Student student = StudentCollection.getStudentById("jieun212");
		assertTrue(student.getFirstName().equals("Jieun"));
	}
	
	@Test
	public void testSearch()
	{
		List<Student> students = StudentCollection.search("Jieun");
		boolean studentFound = false;
		for (Student s : students)
		{
			if (s.getFirstName().equals("Jieun"))
			{
				studentFound = true;
			}
		}
		assertTrue(studentFound);
	}
	
	@Test
	public void testUpdateEmail()
	{
		Student student = new Student("Jake", "Snake", "jsnake");
		StudentCollection.add(student);
		StudentCollection.updateEmail(student, "jsnake@yahoo.com");
		List<Student> students = StudentCollection.getStudents();
		Student jake = null;
		for (Student s : students)
		{
			if (s.getEmail().equals("jsnake@yahoo.com"))
			{
				jake = s;
			}
		}
		assertTrue(jake.getEmail().equals("jsnake@yahoo.com"));
	}
	
	@Test
	public void testHasInternship()
	{
		assertTrue(StudentCollection.hasInternship("jieun212"));
	}
	
	@Test
	public void testHasEmployment()
	{
		assertTrue("True", StudentCollection.hasEmployment("jieun212"));
	}
	
	@Test 
	public void testIsInProgram()
	{
		assertTrue(StudentCollection.isInProgram("jieun212", "2"));
	}
	
	@Test
	public void testHasSkill()
	{
		assertTrue(StudentCollection.hasSkill("jieun212", "2"));
	}
	
	@Test
	public void testHasTransferred()
	{
		assertTrue(StudentCollection.hasTransferred("tvriper"));
		assertFalse(StudentCollection.hasTransferred("jieun212"));
	}
}
