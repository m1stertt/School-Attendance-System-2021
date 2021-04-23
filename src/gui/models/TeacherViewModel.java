package gui.models;

import be.Course;
import be.Student;
import bll.StudRegManager;
import dal.StudRegDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;

public class TeacherViewModel {

    private StudRegManager studRegManager = StudRegManager.createStudRegManager();
    private ObservableList<Student> allStudents = FXCollections.observableArrayList();

    public HashMap<String, Double> getCourseAbsenceDate(int courseId) {
        return studRegManager.getCourseAbsenceDate(courseId);
    }

    public ObservableList<Student> getAllStudents(int id) {
        allStudents.setAll(studRegManager.getAllStudentsCalculatedAbsence(id));
        return allStudents;
    }

    public List<Course> getAllCourses() {
        return studRegManager.getAllCourses();
    }

}
