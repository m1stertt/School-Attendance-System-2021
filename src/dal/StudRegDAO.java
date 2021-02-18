package dal;

import be.Course;
import be.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Random;

/**
 * Static MockDAO.
 */

public class StudRegDAO {

    public ObservableList<Student> getAllStudents(){
        Random r = new Random();
        double totalCourses = 100;

        ObservableList<Student> studentData = FXCollections.observableArrayList(

                new Student("Peter", "Hansen", (r.nextInt(100) / totalCourses) * 100),
                new Student("Ole", "Petersen", (r.nextInt(100) / totalCourses) * 100),
                new Student("Allan", "Olsen", (r.nextInt(100) / totalCourses) * 100),
                new Student("Jesper", "Allansen", (r.nextInt(100) / totalCourses) * 100),
                new Student("Casper", "Jespersen", (r.nextInt(100) / totalCourses) * 100),
                new Student("Nikolaj", "Caspersen", (r.nextInt(100) / totalCourses) * 100),
                new Student("Clark", "Nikolajsen", (r.nextInt(100) / totalCourses) * 100),
                new Student("Bent", "Clarksen", (r.nextInt(100) / totalCourses) * 100)
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

    public XYChart.Series getSummarizedStudentWeekDayData(){
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
