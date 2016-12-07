/**
 * 
 */
package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.StudentSkill;

/**
 * The StudentSkillTest is a unit test for StudentSkill class and covers all the methods in
 * StudentSkill class.
 * 
 * @author Jieun Lee (jieun212@uw.edu)
 * @version 12-06-2016
 */
public class StudentSkillTest {
	
	private StudentSkill myStudentSkill;
	private StudentSkill myStudentSkillGetter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myStudentSkill = new StudentSkill("uwneid1234", "1");
		myStudentSkillGetter = new StudentSkill("uwneid1234", "1");
		myStudentSkillGetter.setId("999");
	}

	/**
	 * Test method for {@link model.StudentSkill#StudentSkill(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testStudentSkill() {
		StudentSkill stuskill = new StudentSkill("constuctortest","1");
		assertNotNull("Unable to construct StuddentDegree object", stuskill);
	}

	
	/*
	 * Tests for getter
	 */
	
	/**
	 * Test method for {@link model.StudentSkill#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals("999", myStudentSkillGetter.getId());
	}

	/**
	 * Test method for {@link model.StudentSkill#getUwnetId()}.
	 */
	@Test
	public void testGetUwnetId() {
		assertEquals("uwneid1234", myStudentSkillGetter.getUwnetId());
	}

	/**
	 * Test method for {@link model.StudentSkill#getSkillId()}.
	 */
	@Test
	public void testGetSkillId() {
		assertEquals("1", myStudentSkillGetter.getSkillId());
	}
	
	
	/*
	 * Tests for setter
	 */

	/**
	 * Test method for {@link model.StudentSkill#setId(java.lang.String)}.
	 */
	@Test
	public void testSetId() {
		myStudentSkill.setId("1000");
		assertEquals("1000", myStudentSkill.getId());
	}
	
	/**
	 * Test method for {@link model.StudentSkill#setId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetIdNull() {
		myStudentSkill.setId(null);
		assertEquals(null, myStudentSkill.getId());
	}

	/**
	 * Test method for {@link model.StudentSkill#setUwnetId(java.lang.String)}.
	 */
	@Test
	public void testSetUwnetId() {
		myStudentSkill.setUwnetId("setterTest1234");
		assertEquals("setterTest1234", myStudentSkill.getUwnetId());
	}
	
	/**
	 * Test method for {@link model.StudentSkill#setUwnetId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetUwnetIdNull() {
		myStudentSkill.setUwnetId(null);
		assertEquals(null, myStudentSkill.getUwnetId());
	}

	/**
	 * Test method for {@link model.StudentSkill#setSkillId(java.lang.String)}.
	 */
	@Test
	public void testSetSkillId() {
		myStudentSkill.setSkillId("2");
		assertEquals("2", myStudentSkill.getSkillId());
	}
	
	/**
	 * Test method for {@link model.StudentSkill#setSkillId(java.lang.String)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetSkillIdNull() {
		myStudentSkill.setSkillId(null);
		assertEquals(null, myStudentSkill.getSkillId());
	}

}
