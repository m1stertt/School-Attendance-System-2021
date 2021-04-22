package bll;

import be.Attendance;
import be.Course;
import be.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudRegManagerTest {

    @Test
    void getCourseAbsenceDate() {

            StudRegManager StudentRegManager = new StudRegManager();

            //make sure student counter works
            // the list has 5 students
            // 1: 0   2: 1   3: 1  4: 0  5: 1
            // totalStudentAttendanceDays = 2
            // class average = 2 / 5 = 0.4 -> 40%
            //expected result is a hasmap with value of "classAverageStudentAttendance", we should get 0.4

            List<String> students = new List<String>;
              students.add("1");
              students.add("0");
              students.add("0");
              students.add("0");
              students.add("1");
              //some mock data?


            double expectedOutput = 0.4;  //
            String resultTAG = "classAverageStudentAttendance";

            HashMap<String, Double> theResult = new HashMap<String, Double>;

            theResult = getCourseAbsenceDate(); //calling the method with a course id with students we just made

            double actualNumber = theResult.get(resultTAG);

            assertEquals(expectedOutput, actualNumber);

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
        assertEquals(expectedSum, d);
    }
}