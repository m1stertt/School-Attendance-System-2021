package dal;

import be.Course;
import be.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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


                new Student("Peter", (r.nextInt(100) / totalCourses) * 100),
                new Student("Ole", (r.nextInt(100) / totalCourses) * 100),
                new Student("Allan", (r.nextInt(100) / totalCourses) * 100),
                new Student("Jesper", (r.nextInt(100) / totalCourses) * 100),
                new Student("Casper", (r.nextInt(100) / totalCourses) * 100),
                new Student("Nikolaj", (r.nextInt(100) / totalCourses) * 100),
                new Student("Clark", (r.nextInt(100) / totalCourses) * 100),
                new Student("Bent", (r.nextInt(100) / totalCourses) * 100)

        );
        System.out.println(r.nextInt(100));
        return studentData;
    }

    public List<Course> getAllCourses(){
        ObservableList<Course> classData = FXCollections.observableArrayList(
                new Course("SCO2.B.21", 75, 25),
                new Course("SDE2.B.21", 85, 15)
        );
        return classData;
    }
}
