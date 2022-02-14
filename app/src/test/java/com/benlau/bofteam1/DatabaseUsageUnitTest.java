package com.benlau.bofteam1;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.benlau.bofteam1.db.AppDatabase;
import com.benlau.bofteam1.db.Course;
import com.benlau.bofteam1.db.CourseDao;
import com.benlau.bofteam1.db.Person;
import com.benlau.bofteam1.db.PersonDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseUsageUnitTest {
    private PersonDao personDao;
    private CourseDao courseDao;
    private AppDatabase db;

    /**
     * Creating an empty test Database db
     */
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        personDao = db.personsDao();
        courseDao = db.coursesDao();

    }

    /**
     * Closes the test Database when done testing
     * @throws IOException
     */
    @After
    public void closeDb() throws IOException {
        db.close();
    }

    /**
     * Testing insertion of Persons into Database
     * @throws Exception
     */
    @Test
    public void testInsertPerson() throws Exception {
        //create Persons
        Person person1 = new Person("Mark", "https://www.recipetineats.com/wp-content/uploads/2019/04/Beef-Pho_6.jpg", String.valueOf(3));
        Person person2 = new Person("Pho", "https://media-cdn.tripadvisor.com/media/photo-s/14/71/0d/f6/pho-dac-biet-photo-by.jpg", String.valueOf(2));
        Person person3 = new Person("Dummy", "Dummy", String.valueOf(0));
        //insert Persons into DataBase
        personDao.insert(person1);
        personDao.insert(person2);
        personDao.insert(person3);
        //get list of Persons from DataBase
        List<Person> personList = personDao.getAllPeople();
        assertEquals(personList.size(), 3);
    }

    /**
     * Testing insertion of Courses into Database
     * @throws Exception
     */
    @Test
    public void testInsertCourse() throws Exception {
        //create Courses for first Person
        Course course1 = new Course(2, 1, "2022", "WI", "CSE", "110" );
        Course course2 = new Course(3, 1, "2021", "FA", "MUS", "19");
        //create Courses for second Person
        Course course3 = new Course(4, 2, "2020", "SP", "CSE", "12" );
        Course course4 = new Course(5, 2, "2021", "FA", "MUS", "19");
        Course course5 = new Course(6, 2, "2019", "FA", "MATH", "20C");
        courseDao.insert(course1);
        courseDao.insert(course2);
        courseDao.insert(course3);
        courseDao.insert(course4);
        courseDao.insert(course5);
        //getting List of Courses for First Person
        List<Course> courseList1 = courseDao.getCoursesForPerson(1);
        assertEquals(courseList1.size(), 2);
        //getting List of Courses for Second Person
        List<Course> courseList2 = courseDao.getCoursesForPerson(2);
        assertEquals(courseList2.size(), 3);
    }

    /**
     * Based on the success of the two above courses, now to join them together and verify
     * accessibility of the each person's fields and their Course List as well as the Courses' fields
     */
    @Test
    public void integrationOfPersonsAndCourses() {
        //create new Person and Course and insert into Database
        Person person1 = new Person("Mark", "https://www.recipetineats.com/wp-content/uploads/2019/04/Beef-Pho_6.jpg", String.valueOf(3));
        Course course1 = new Course(1, 1, "2022", "WI", "CSE", "110" );
        personDao.insert(person1);
        courseDao.insert(course1);

        //get List of Persons and Courses
        List<Person> personList = personDao.getAllPeople();
        List<Course> courseList = courseDao.getCoursesForPerson(1);

        //Check Person Name Field
        assertEquals(personList.get(0).getPersonName(), "Mark");

        //Check Person photoURL Field
        assertEquals(personList.get(0).getPhotoUrl(), "https://www.recipetineats.com/wp-content/uploads/2019/04/Beef-Pho_6.jpg");

        //Check Person ID Field
        assertEquals(personList.get(0).getPersonId(), 1);

        //Check Course Fields for the aforementioned Person
        assertEquals(courseList.get(0).getFullCourse(), "2022 WI CSE 110");


    }

    /**
     * Based on the success of the test above, now calculating the common courses
     *
     */
    @Test
    public void testCommonCoursesBetweenPersons() {
        //creating 4 Persons
        Person person1 = new Person("Mark", "https://www.recipetineats.com/wp-content/uploads/2019/04/Beef-Pho_6.jpg", String.valueOf(0));
        Person person2 = new Person("Pho", "https://media-cdn.tripadvisor.com/media/photo-s/14/71/0d/f6/pho-dac-biet-photo-by.jpg", String.valueOf(0));
        Person person3 = new Person("Dummy1", "Dummy1", String.valueOf(0));
        Person person4 = new Person("Dummy2", "Dummy2", String.valueOf(0));
        personDao.insert(person1);
        personDao.insert(person2);
        personDao.insert(person3);
        personDao.insert(person4);

        //creating 2 Courses for Person1 (User by which to compare to with everyone else for common courses)
        Course course1 = new Course(4, 1, "2022", "WI", "CSE", "110" );
        Course course2 = new Course(5, 1, "2021", "FA", "MUS", "19");
        courseDao.insert(course1);
        courseDao.insert(course2);

        //creating 1 Course for Person2 (1 in common with Person1)
        Course course3 = new Course(6, 2, "2022", "WI", "CSE", "110" );
        courseDao.insert(course3);

        //creating 3 Courses for Person3 (2 in common with Person1)
        Course course4 = new Course(7, 3, "2022", "WI", "CSE", "110" );
        Course course5 = new Course(8, 3, "2019", "FA", "CSE", "8A" );
        Course course6 = new Course(9, 3, "2021", "FA", "MUS", "19");
        courseDao.insert(course4);
        courseDao.insert(course5);
        courseDao.insert(course6);

        //creating 2 Courses for Person4 (0 in common with Person2
        Course course7 = new Course(10, 4, "2019", "FA", "CSE", "8A" );
        Course course8 = new Course(11, 4, "2020", "SP", "MATH", "20D" );
        courseDao.insert(course7);
        courseDao.insert(course8);

        //getting List of Courses for First Person
        List<Course> courseList1 = courseDao.getCoursesForPerson(1);

        //getting List of Courses for Second Person
        List<Course> courseList2 = courseDao.getCoursesForPerson(2);

        //getting List of Courses for Third Person
        List<Course> courseList3 = courseDao.getCoursesForPerson(3);

        //getting List of Courses for Fourth Person
        List<Course> courseList4 = courseDao.getCoursesForPerson(4);

        //checking number of common Courses between Person1 (user) and Person2
        assertEquals(numberOfCommonCourses(courseList1, courseList2), 1);

        //checking number of common Courses between Person1 (user) and Person3
        assertEquals(numberOfCommonCourses(courseList1, courseList3), 2);

        //checking number of common Courses between Person1 (user) and Person4
        assertEquals(numberOfCommonCourses(courseList1, courseList4), 0);

        //checking the actual list of common Courses between Person1(user) and Person3 (2 in common)
        ArrayList commonList13 = listOfCommonCourses(courseList1, courseList3);
        assertEquals(commonList13.get(0), "2022 WI CSE 110");
        assertEquals(commonList13.get(1), "2021 FA MUS 19");

        //checking the actual list of common Courses between Person1(user) and Person4 (0 in common)
        ArrayList commonList14 = listOfCommonCourses(courseList1, courseList4);
        assertEquals(commonList14.size(), 0);
    }

    public int numberOfCommonCourses(List<Course> list1, List<Course> list2) {
        int inCommon = 0;
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).getFullCourse().equals(list2.get(j).getFullCourse())) {
                    inCommon++;
                }
            }
        }
        return inCommon;
    }

    public ArrayList<String> listOfCommonCourses(List<Course> list1, List<Course> list2) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < list1.size(); i++) {
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).getFullCourse().equals(list2.get(j).getFullCourse())) {
                    list.add(list1.get(i).getFullCourse());
                }
            }
        }
        return list;
    }



}
