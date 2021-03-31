package dal;

import be.Course;
import be.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Static MockDAO.
 */

public class StudRegDAO {

    private DBConnector dataSource;


    public ObservableList<Student> getAllStudents(){
        Random r = new Random();
        double totalCourses = 100;

        ObservableList<Student> studentData = FXCollections.observableArrayList(

//                new Student("Peter", "Hansen", r.nextInt(10) / totalCourses),
//                new Student("Ole", "Petersen", r.nextInt(10) / totalCourses),
//                new Student("Allan", "Olsen", r.nextInt(10) / totalCourses),
//                new Student("Jesper", "Allansen", r.nextInt(10) / totalCourses),
//                new Student("Casper", "Jespersen", r.nextInt(10) / totalCourses),
//                new Student("Nikolaj", "Caspersen", r.nextInt(10) / totalCourses),
//                new Student("Clark", "Nikolajsen", r.nextInt(10) / totalCourses)
        );
        return studentData;
    }


    public List<Course> getAllCourses(){
        ObservableList<Course> classData = FXCollections.observableArrayList(
                new Course("SCO2.B.21", 75, 25),
                new Course("SDE2.B.21", 85, 15)
        );
        return classData;
    }

    public List<String> getCoursesStringForDay(Integer day) {
        List<String> courses = new ArrayList<String>();
        dataSource = new DBConnector();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT c.Name, CDOW.Weekday, CDOW.StartTime, CDOW.EndTime\n" +
                    "FROM Courses AS C\n" +
                    "INNER JOIN CoursesDayOfWeek AS CDOW ON c.Id = CDOW.CourseId\n" +
                    "WHERE CDOW.Weekday = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, day);
            ResultSet rs = pstmt.executeQuery();
            DateFormat df = new SimpleDateFormat("H:mm");

            while (rs.next()) {
                Date from = new Date(rs.getTime("StartTime").getTime());
                Date to = new Date(rs.getTime("EndTime").getTime());
                String course = rs.getString("Name") + ": " + df.format(from) + " - " + df.format(to);
                courses.add(course);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courses;
    }


    public XYChart.Series getSummarizedStudentWeekDayData() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Student Attendance");
        Random r = new Random();
        double totalCourses = 100;
        series.getData().add(new XYChart.Data<>("Monday", (r.nextInt(10) / totalCourses) * 100));
        series.getData().add(new XYChart.Data<>("Tuesday", (r.nextInt(10) / totalCourses) * 100));
        series.getData().add(new XYChart.Data<>("Wednesday", (r.nextInt(10) / totalCourses) * 100));
        series.getData().add(new XYChart.Data<>("Thursday", (r.nextInt(10) / totalCourses) * 100));
        series.getData().add(new XYChart.Data<>("Friday", (r.nextInt(10) / totalCourses) * 100));
        return series;
    }
}
