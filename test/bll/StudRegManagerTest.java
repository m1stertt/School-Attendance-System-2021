package bll;

import be.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class StudRegManagerTest {

    @Test
    void getCourseAbsenceDate() {

    }

    @Test
    void getWeekdayAttendanceData() {

    }

    @Test
    void createWeekDaySeries() {
    }

    @Test
    void getAbsenceData() {
        double expectedSum = 0.5;
        double sum = 0;
        StudRegTestDAO studRegTestDAO = new StudRegTestDAO();
        List<Course> courses = studRegTestDAO.getAllStudentCourses();

        for (Course ignored : courses) {
            sum += (double) studRegTestDAO.getStudentAttendanceDaysInSemesterCourse() / studRegTestDAO.getCourseDaysInSemesterCourseUntilNow();
        }
        double d = sum / courses.size();
        Assertions.assertEquals(expectedSum, d);
    }
}