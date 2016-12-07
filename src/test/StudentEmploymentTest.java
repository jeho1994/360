/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import model.StudentEmployment;

/**
 * The StudentEmploymentTest is a unit test for StudentEmployment class and covers all the methods in
 * StudentEmployment class.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 12-06-2016
 */
public class StudentEmploymentTest {
	
	
    /**
     * The tolerance for double type.
     */
    private static final double TOLERANCE = 0.00000001;
    
    /** Date formmat */
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/** A java.sql.Date */
	private Date mySqlDate;
	
	/** A StudentEmployment */
	private StudentEmployment myStudentEmployment;
	
	/** A StudentEmployment for testing getter methods */
	private StudentEmployment myStudentEmploymentGetter;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		long date = dateFormat.parse("2016-12-25").getTime();
		mySqlDate = new Date(date);
		myStudentEmployment = new StudentEmployment("uwnetid123", "CompanyName", "Position", 80000, mySqlDate);
		myStudentEmploymentGetter = new StudentEmployment("uwnetid123", "CompanyName", "Position", 80000, mySqlDate);
		myStudentEmploymentGetter.setId("999");
	}
	
	/*
	 * Tests for Constructors
	 */
	
	/**
	 * Test method for {@link model.StudentEmployment#StudentEmployment(java.lang.String, java.lang.String, java.lang.String, double, java.sql.Date, java.lang.String)}.
	 */
	@Test
	public void testStudentFullConstructor() {
		StudentEmployment studentEmployment= new StudentEmployment("uwnetid123", "CompanyName", "Position", 80000, mySqlDate, "Comment");
		assertNotNull("Unable to construct studentEmployment object", studentEmployment);
	}
	
	/**
	 * Test method for {@link model.StudentEmployment#StudentEmployment(java.lang.String, java.lang.String, java.lang.String, double, java.sql.Date)}.
	 */
	@Test
	public void testStudentEmploymentHasEmployment() {
		StudentEmployment studentEmployment= new StudentEmployment("uwnetid123", "CompanyName", "Position", 80000, mySqlDate);
		assertNotNull("Unable to construct studentEmployment object", studentEmployment);
	}
	
	/**
	 * Test method for {@link model.StudentEmployment#StudentEmployment(java.lang.String, java.lang.String, java.lang.String, double, java.sql.Date)}.
	 */
	@Test
	public void testStudentEmploymentNoEmployement() {
		StudentEmployment studentEmployment= new StudentEmployment("uwnetid123", null, null, 0, null, "NoData");
		assertNotNull("Unable to construct studentEmployment object", studentEmployment);
	}
	

	/*
	 * Tests for Getter
	 */

	/**
	 * Test method for {@link model.StudentEmployment#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals("999", myStudentEmploymentGetter.getId());
	}

	/**
	 * Test method for {@link model.StudentEmployment#getPosition()}.
	 */
	@Test
	public void testGetPosition() {
		assertEquals("Position", myStudentEmploymentGetter.getPosition());
	}

	/**
	 * Test method for {@link model.StudentEmployment#getSalary()}.
	 */
	@Test
	public void testGetSalary() {
		assertEquals(80000, myStudentEmploymentGetter.getSalary(), TOLERANCE);
	}

	/**
	 * Test method for {@link model.StudentEmployment#getStartDate()}.
	 */
	@Test
	public void testGetStartDate() {
		assertEquals("2016-12-25", String.valueOf(myStudentEmploymentGetter.getStartDate()));
	}

	/**
	 * Test method for {@link model.StudentEmployment#getComment()}.
	 */
	@Test
	public void testGetComment() {
		myStudentEmploymentGetter.setComment("Getter test");
		assertEquals("Getter test", myStudentEmploymentGetter.getComment());
	}

	/**
	 * Test method for {@link model.StudentEmployment#getUwnetId()}.
	 */
	@Test
	public void testGetUwnetId() {
		assertEquals("uwnetid123", myStudentEmploymentGetter.getUwnetId());
	}

	/**
	 * Test method for {@link model.StudentEmployment#getEmployer()}.
	 */
	@Test
	public void testGetEmployer() {
		assertEquals("CompanyName", myStudentEmploymentGetter.getEmployer());
	}
	
	
	
	/*
	 * Tests for setter
	 */

	/**
	 * Test method for {@link model.StudentEmployment#setId(java.lang.String)}.
	 */
	@Test
	public void testSetId() {
		myStudentEmployment.setId("1000");
		assertEquals("1000", myStudentEmployment.getId());
	}
	
	/**
	 * Test method for {@link model.StudentEmployment#setId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetIdNull() {
		myStudentEmployment.setId(null);
		assertEquals(null, myStudentEmployment.getId());
	}

	/**
	 * Test method for {@link model.StudentEmployment#setPosition(java.lang.String)}.
	 */
	@Test
	public void testSetPosition() {
		myStudentEmployment.setPosition("Setter position");
		assertEquals("Setter position", myStudentEmployment.getPosition());
	}

	/**
	 * Test method for {@link model.StudentEmployment#setSalary(double)}.
	 */
	@Test
	public void testSetSalary() {
		myStudentEmployment.setSalary(100000);
		assertEquals(100000, myStudentEmployment.getSalary(), TOLERANCE);
	}
	
	/**
	 * Test method for {@link model.StudentEmployment#setSalary(double)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetSalaryNegative() {
		myStudentEmployment.setSalary(-100);
		assertEquals(-100, myStudentEmployment.getSalary(), TOLERANCE);
	}

	/**
	 * Test method for {@link model.StudentEmployment#setStartDate(java.sql.Date)}.
	 */
	@Test
	public void testSetStartDate() {
		try {
			long date = dateFormat.parse("2017-01-01").getTime();
			Date sqldate = new Date(date);
			myStudentEmployment.setStartDate(sqldate);
			assertEquals("2017-01-01", String.valueOf(myStudentEmployment.getStartDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link model.StudentEmployment#setComment(java.lang.String)}.
	 */
	@Test
	public void testSetComment() {
		myStudentEmployment.setComment("Set comment teset");
		assertEquals("Set comment teset", myStudentEmployment.getComment());
	}

	/**
	 * Test method for {@link model.StudentEmployment#setUwnetId(java.lang.String)}.
	 */
	@Test
	public void testSetUwnetId() {
		myStudentEmployment.setUwnetId("setuwnetidtest11");
		assertEquals("setuwnetidtest11", myStudentEmployment.getUwnetId());
	}
	
	/**
	 * Test method for {@link model.StudentEmployment#setUwnetId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetUwnetIdNull() {
		myStudentEmployment.setUwnetId(null);
		assertEquals(null, myStudentEmployment.getUwnetId());
	}

	/**
	 * Test method for {@link model.StudentEmployment#setEmployer(java.lang.String)}.
	 */
	@Test
	public void testSetEmployer() {
		myStudentEmployment.setEmployer("Setter company");
		assertEquals("Setter company", myStudentEmployment.getEmployer());
	}

}
