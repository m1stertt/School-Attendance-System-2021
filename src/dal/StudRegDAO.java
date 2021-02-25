package dal;

import be.Course;
import be.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
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

                new Student("Peter", "Hansen", r.nextInt(10) / totalCourses),
                new Student("Ole", "Petersen", r.nextInt(10) / totalCourses),
                new Student("Allan", "Olsen", r.nextInt(10) / totalCourses),
                new Student("Jesper", "Allansen", r.nextInt(10) / totalCourses),
                new Student("Casper", "Jespersen", r.nextInt(10) / totalCourses),
                new Student("Nikolaj", "Caspersen", r.nextInt(10) / totalCourses),
                new Student("Clark", "Nikolajsen", r.nextInt(10) / totalCourses)
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

    public List<String> getCoursesStringForDay(Integer day){
        List<String> courses=new ArrayList<String>();

        switch(day){
            case 1: //Monday
                courses.add("SCO 10.30-13.15");
                break;
            case 2: //Tuesday
                courses.add("SDE 8.45-13.15");
                break;
            case 3: //Wednesday
            case 5: //Friday
                courses.add("SCO 8.45-11.15");
                break;
            case 4: //Thursday
                courses.add("DBOS 8.45-12.00");
                courses.add("ITO 12.30-15.45");
                break;
        }
        return courses;
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
