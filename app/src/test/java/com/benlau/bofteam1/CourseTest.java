package com.benlau.bofteam1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(RobolectricTestRunner.class)
public class CourseTest {

  @Test
  public void testCourseToString() {
    Course testCourse = new Course("2022", Quarter.FA, "CSE", "199A");
    assertEquals(testCourse.toString(), "2022FA CSE 199A");
    assertEquals(testCourse.getQuarter(), "FA");
  }

  @Test
  public void testCourseEquality() {
    Course testCourse1 = new Course("2022", Quarter.FA, "CSE", "199A");
    Course testCourse2 = new Course("2022", Quarter.FA, "CSE", "199A");
    Course testCourse3 = new Course("2022", Quarter.FA, "CSE", "199");
    Course testCourse4 = new Course("2021", Quarter.FA, "CSE", "199A");
    Course testCourse5 = new Course("2022", Quarter.WI, "CSE", "199A");
    Course testCourse6 = new Course("2022", Quarter.FA, "ECE", "199A");

    assertEquals(testCourse1, testCourse2);
    assertNotEquals(testCourse1, testCourse3);
    assertNotEquals(testCourse1, testCourse4);
    assertNotEquals(testCourse1, testCourse5);
    assertNotEquals(testCourse1, testCourse6);
  }
}
