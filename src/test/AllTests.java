package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * This test class tests all test cases in test package for UWT IT Student Tracker Application.
 * 
 * @author Jieun Lee
 * @author Thomas Van Riper
 * @author Louis Yang
 * @version 12-06-2016
 */
@RunWith(Suite.class)
@SuiteClasses({ StudentTest.class, StudentDegreeTest.class, StudentEmploymentTest.class, StudentSkillTest.class,
	StudentCollectionTest.class})
public class AllTests 
{
	
	// Thomas: StudentTest.class, StudentCollectionTest.class
	
	// Jieun: StudentDegreeTest.class, StudentEmploymentTest.class, StudentSkillTest.class
	
	// Louis: DegreeTest.class, SkillTest.class, StudentInternship.class

}