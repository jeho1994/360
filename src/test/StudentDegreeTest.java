/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.StudentDegree;

/**
 * The StudentDegreeTest is a unit test for StudentDegree class and covers all the methods in
 * StudentDegree class.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 12-06-2016
 */
public class StudentDegreeTest {
	
    /**
     * The tolerance for double type.
     */
    private static final double TOLERANCE = 0.00000001;
	
    /** A StudentDegree */
	private StudentDegree myStudentDegree;
	
	/** A StudentDegree for testing getter methods */
	private StudentDegree myStudentDegreeGetter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myStudentDegree = new StudentDegree("uwnetidTest", "2", "Winter", "2017", 3.2, "Bellevue College");
		myStudentDegreeGetter = new StudentDegree("uwnetidTest", "2", "Winter", "2017", 3.2, "Bellevue College");
		myStudentDegreeGetter.setId("999");
	}
	
	/*
	 * Tests for constuctors 
	 */

	/**
	 * Test method for {@link model.StudentDegree#StudentDegree(java.lang.String, java.lang.String, java.lang.String, java.lang.String, double)}.
	 */
	@Test
	public void testStudentDegreeConstructor() {
		StudentDegree degree = new StudentDegree("uwnetidConst1", "1", "Spring", "2018", 3.6);
		assertNotNull("Unable to construct StuddentDegree object", degree);
	}

	/**
	 * Test method for {@link model.StudentDegree#StudentDegree(java.lang.String, java.lang.String, java.lang.String, java.lang.String, double, java.lang.String)}.
	 */
	@Test
	public void testStudentDegreeFullConstructor() {
		StudentDegree degree = new StudentDegree("uwnetidConst1", "1", "Spring", "2018", 3.6, "Tacoma Community College");
		assertNotNull("Unable to construct StuddentDegree object", degree);
	}

	
	/*
	 * Tests for setter methods
	 */
	
	/**
	 * Test method for {@link model.StudentDegree#setId(java.lang.String)}.
	 */
	@Test
	public void testSetId() {
		myStudentDegree.setId("1000");
		assertEquals("1000", myStudentDegree.getId());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetIdNull() {
		myStudentDegree.setId(null);
		assertEquals(null, myStudentDegree.getId());
	}

	/**
	 * Test method for {@link model.StudentDegree#setGraduationTerm(java.lang.String)}.
	 */
	@Test
	public void testSetGraduationTerm() {
		myStudentDegree.setGraduationTerm("summer");
		assertEquals("Summer", myStudentDegree.getGraduationTerm());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setGraduationTerm(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetGraduationTermNull() {
		myStudentDegree.setGraduationTerm(null);
		assertEquals(null, myStudentDegree.getGraduationTerm());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setGraduationTerm(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidGraduationTerm() {
		myStudentDegree.setGraduationTerm("term");
		assertEquals("term", myStudentDegree.getGraduationTerm());
	}

	/**
	 * Test method for {@link model.StudentDegree#setGraduationYear(java.lang.String)}.
	 */
	@Test
	public void testSetGraduationYear() {
		myStudentDegree.setGraduationYear("2027");
		assertEquals("2027", myStudentDegree.getGraduationYear());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setGraduationYear(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetGraduationYearNull() {
		myStudentDegree.setGraduationYear(null);
		assertEquals(null, myStudentDegree.getGraduationYear());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setGraduationYear(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetShortGraduationYear() {
		myStudentDegree.setGraduationYear("999");
		assertEquals("999", myStudentDegree.getGraduationYear());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setGraduationYear(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetLongGraduationYear() {
		myStudentDegree.setGraduationYear("10000");
		assertEquals("10000", myStudentDegree.getGraduationYear());
	}

	/**
	 * Test method for {@link model.StudentDegree#setGraduationYear(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetNonNumberedGraduationYear() {
		myStudentDegree.setGraduationYear("year");
		assertEquals("year", myStudentDegree.getGraduationYear());
	}
	/**
	 * Test method for {@link model.StudentDegree#setGPA(double)}.
	 */
	@Test
	public void testSetGPA() {
		myStudentDegree.setGPA(2.9);
		assertEquals(2.9, myStudentDegree.getGPA(), TOLERANCE);
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setGPA(double)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetGPANegative() {
		myStudentDegree.setGPA(-2.9);
		assertEquals(-2.9, myStudentDegree.getGPA(), TOLERANCE);
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setGPA(double)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetGPAOver4() {
		myStudentDegree.setGPA(4.1);
		assertEquals(4.1, myStudentDegree.getGPA(), TOLERANCE);
	}


	/**
	 * Test method for {@link model.StudentDegree#setTransferCollege(java.lang.String)}.
	 */
	@Test
	public void testSetTransferCollege() {
		myStudentDegree.setTransferCollege("Tacoma Community College");
		assertEquals("Tacoma Community College", myStudentDegree.getTransferCollege());
	}

	/**
	 * Test method for {@link model.StudentDegree#setUwnetId(java.lang.String)}.
	 */
	@Test
	public void testSetUwnetId() {
		myStudentDegree.setUwnetId("SetUwnetIdTest");
		assertEquals("SetUwnetIdTest", myStudentDegree.getUwnetId());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setUwnetId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetUwnetIdNull() {
		myStudentDegree.setUwnetId(null);
		assertEquals(null, myStudentDegree.getUwnetId());
	}

	/**
	 * Test method for {@link model.StudentDegree#setDegreeId(java.lang.String)}.
	 */
	@Test
	public void testSetDegreeId() {
		myStudentDegree.setDegreeId("3");
		assertEquals("3", myStudentDegree.getDegreeId());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setDegreeId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetDegreeIdNull() {
		myStudentDegree.setDegreeId(null);
		assertEquals(null, myStudentDegree.getDegreeId());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setDegreeId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetDegreeIdNonNumber() {
		myStudentDegree.setDegreeId("d");
		assertEquals("d", myStudentDegree.getDegreeId());
	}
	
	/**
	 * Test method for {@link model.StudentDegree#setDegreeId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetDegreeIdNegative() {
		myStudentDegree.setDegreeId("-2");
		assertEquals("-2", myStudentDegree.getDegreeId());
	}

	
	
	/*
	 * Tests for getter methods
	 * myStudentDegreeGetter ("uwnetidTest", "2", "Winter", "2017", 3.2, "Bellevue College");
	 * Id("999");
	 */
	
	/**
	 * Test method for {@link model.StudentDegree#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals("999", myStudentDegreeGetter.getId());
	}

	/**
	 * Test method for {@link model.StudentDegree#getGraduationTerm()}.
	 */
	@Test
	public void testGetGraduationTerm() {
		assertEquals("Winter", myStudentDegreeGetter.getGraduationTerm());
	}

	/**
	 * Test method for {@link model.StudentDegree#getGraduationYear()}.
	 */
	@Test
	public void testGetGraduationYear() {
		assertEquals("2017", myStudentDegreeGetter.getGraduationYear());
	}

	/**
	 * Test method for {@link model.StudentDegree#getGPA()}.
	 */
	@Test
	public void testGetGPA() {
		assertEquals(3.2, myStudentDegreeGetter.getGPA(), TOLERANCE);
	}

	/**
	 * Test method for {@link model.StudentDegree#getTransferCollege()}.
	 */
	@Test
	public void testGetTransferCollege() {
		assertEquals("Bellevue College", myStudentDegreeGetter.getTransferCollege());
	}

	/**
	 * Test method for {@link model.StudentDegree#getUwnetId()}.
	 */
	@Test
	public void testGetUwnetId() {
		assertEquals("uwnetidTest", myStudentDegreeGetter.getUwnetId());
	}

	/**
	 * Test method for {@link model.StudentDegree#getDegreeId()}.
	 */
	@Test
	public void testGetDegreeId() {
		assertEquals("2", myStudentDegreeGetter.getDegreeId());
	}

}
