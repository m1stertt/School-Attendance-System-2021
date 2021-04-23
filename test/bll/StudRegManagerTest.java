package bll;

import be.Attendance;
import be.Course;
import be.Student;
import dal.StudRegDAO;
import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudRegManagerTest {

    static StudRegManager studentRegManager;

    @BeforeAll
    static void init() {
        studentRegManager = new StudRegManager(new StudRegTestDAO());
    }

    @Test
    void getAllStudentsCalculatedAbsence() {
        double expectedResult = 50;
        List<Student> allStudents = studentRegManager.getAllStudentsCalculatedAbsence(1);
        allStudents.forEach(student -> Assertions.assertEquals(expectedResult, student.getAbsence()));
    }

    @Test
    void getAllCourses() {
        List<Course> allCoursesActual = studentRegManager.getAllCourses();
        List<Course> allCoursesExpected = new ArrayList<>();
        Date time = Calendar.getInstance().getTime();
        allCoursesExpected.add(new Course(1, "DBOS2.AB.21", time, time));
        allCoursesExpected.add(new Course(2, "ITO2.AB.21", time, time));
        allCoursesExpected.add(new Course(3, "SCO2.B.21", time, time));
        allCoursesExpected.add(new Course(4, "SDE2.B.21", time, time));

        for (int i = 0; i < allCoursesActual.size(); i++) {
            Assertions.assertEquals(allCoursesExpected.get(i).getCourseName(), allCoursesActual.get(i).getCourseName());
        }
    }

    @Test
    void getCoursesStringForDay() {
        List<String> allCoursesForTest = new ArrayList<>();
        allCoursesForTest.add("DBOS2.AB.21");
        allCoursesForTest.add("ITO2.AB.21");
        allCoursesForTest.add("SCO2.B.21");
        allCoursesForTest.add("SDE2.B.21");

        List<String> actualCoursesString = studentRegManager.getCoursesStringForDay(LocalDate.now());
        for (int i = 0; i < allCoursesForTest.size(); i++) {
            Assertions.assertEquals(allCoursesForTest.get(i), actualCoursesString.get(i));
        }
    }


    @Test
    void getCourseAbsenceDate() {
        double expectedClassAverageStudentAttendance = 10;
        double expectedAllLessonsInCourse = 20;

        HashMap<String, Double> actualAbsenceDate = studentRegManager.getCourseAbsenceDate(2);
        Assertions.assertEquals(actualAbsenceDate.get("classAverageStudentAttendance"), expectedClassAverageStudentAttendance);
        Assertions.assertEquals(actualAbsenceDate.get("allLessonsInCourse"), expectedAllLessonsInCourse);


    }

    @Test
    void getCourseName() {
        String actualCourseName = studentRegManager.getCourseName(1);
        Assertions.assertEquals("DBOS2.AB.21", actualCourseName);
    }

    @Test
    void getWeekdayAttendanceData() {
        HashMap<String, Integer> actualData = studentRegManager.getWeekdayAttendanceData(2);

        Assertions.assertEquals(actualData.get("MONDAY"), 0);
        Assertions.assertEquals(actualData.get("TUESDAY"), 0);
        Assertions.assertEquals(actualData.get("WEDNESDAY"), 0);
        Assertions.assertEquals(actualData.get("THURSDAY"), 0);
        Assertions.assertEquals(actualData.get("FRIDAY"), 4);

    }

    @Test
    void createWeekDaySeries() {
        int expectedLessonDaysUntilNow = 16;


        expectedLessonDaysUntilNow = expectedLessonDaysUntilNow / 5;
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Student Attendance");


        XYChart.Series actualData = studentRegManager.createWeekDaySeries(1);
        System.out.println(actualData.getData().get(0).getClass().get);





    }

    @Test
    void getAbsenceData() {
        double absenceData = studentRegManager.getAbsenceData(2);
        Assertions.assertEquals(0.5, absenceData);
    }

//    @Test
//    void getAbsenceDataNoCourses() {
//        StudRegManager studentRegManager = new StudRegManager(new StudRegTestDAO());
//        double absenceData = studentRegManager.getAbsenceData(3);
//        Assertions.assertEquals(0, absenceData);
//    }
}