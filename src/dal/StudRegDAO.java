package dal;

import be.Course;
import be.Student;
import bll.LoginSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.sql.Date;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class StudRegDAO {

    private DBConnector dataSource;

    /**
     * This method is used to get all students related to a course.
     * @param courseId id of the course.
     * @return a list of all students
     */
    public List<Student> getAllStudents(int courseId) {
        List<Student> students = new ArrayList<>();
        dataSource = new DBConnector();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT u.Id, u.Username, u.FirstName, u.LastName FROM [User] AS u WHERE u.Role='Student'";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int studentId = rs.getInt("Id");
                    double studentAbsence = getStudentAttendanceDaysInSemesterCourse(courseId,studentId);
                students.add(new Student(studentId, rs.getString("FirstName"), rs.getString("LastName"), studentAbsence));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return FXCollections.observableArrayList(students);
    }

    /**
     * Method to get all courses.
     * @return a list of all courses registered in database.
     */
    public List<Course> getAllCourses() {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        dataSource = new DBConnector();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT C.Id, C.[Name], C.StartDate, C.EndDate " +
                    "FROM Courses AS C;";
            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("Id");
                String name = rs.getString("Name");
                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("EndDate");
                courses.add(new Course(id, name, startDate, endDate));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courses;
    }
    /**
     * Method to get all courses related to a student.
     * @return a list of all courses related to a student in the database.
     */
    public List<Course> getAllStudentCourses(int studentID) {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        dataSource = new DBConnector();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT C.Id, C.[Name], C.StartDate, C.EndDate " +
                    "FROM Courses AS C JOIN StudentCourses as SC ON C.Id = SC.CourseID AND SC.StudentID = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,studentID);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("Id");
                String name = rs.getString("Name");
                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("EndDate");
                courses.add(new Course(id, name, startDate, endDate));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courses;
    }

    public void registerAttendance(int courseId) {
        Calendar cal = Calendar.getInstance();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
        dataSource = new DBConnector();
        try (Connection con = dataSource.getConnection()) {

            String sql = "INSERT INTO StudentLessonAttendance (StudentId, CourseId, RegisterTime, [Status]) " +
                    "VALUES (?, ?, ?, ?);";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, LoginSession.getUserId());
            pstmt.setInt(2, courseId);
            pstmt.setTimestamp(3, timestamp);
            pstmt.setBoolean(4, true);
            pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public Integer getStudentAttendanceDaysInSemesterCourse(int courseId, int studentId) {
        dataSource = new DBConnector();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT COUNT(*) AS TotalCourseAttendanceDays " +
                    "FROM StudentLessonAttendance AS SLA " +
                    "WHERE SLA.CourseId = ? " +
                    "AND SLA.StudentId = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, courseId);
            pstmt.setInt(2,studentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("TotalCourseAttendanceDays");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * This method returns a list of course names to populate the student registration
     * window with a list of all courses to attend to on a day.
     * @param day a day in the format 1-7.
     * @return a list of all course names on a day.
     */
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

    /**
     * This method is used to check if
     * student register in the correct timeframe of the lesson.
     *
     * @param day to check for.
     * @return a HashMap containing a String key with the name of the lesson
     * each key is related to a list containing the start and end time of the lesson.
     */
    public HashMap<String, ArrayList<LocalTime>> getCourseTime(Integer day) {
        HashMap<String, ArrayList<LocalTime>> courseTimes = new HashMap<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT c.Name, CDOW.StartTime, CDOW.EndTime " +
                    "FROM Courses AS C " +
                    "INNER JOIN CoursesDayOfWeek AS CDOW ON c.Id = CDOW.CourseId " +
                    "WHERE CDOW.Weekday = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, day);
            ResultSet rs = pstmt.executeQuery();
            DateFormat df = new SimpleDateFormat("H:mm");
            while (rs.next()) {
                LocalTime startTime = rs.getObject("StartTime", LocalTime.class);
                LocalTime endTime = rs.getObject("EndTime", LocalTime.class);
                String course = rs.getString("Name");
                ArrayList<LocalTime> startAndEndTimes = new ArrayList();
                startAndEndTimes.add(startTime);
                startAndEndTimes.add(endTime);
                courseTimes.put(course, startAndEndTimes);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseTimes;
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

    /**
     * This database call method calculates how many course days there are in
     * a semester course up until the current date.
     *
     * @param courseId courseId of the course to calculate the days from.
     * @return An integer with the total course days for a semester.
     */
    public Integer getCourseDaysInSemesterCourseUntilNow(int courseId) {
        HashMap<String, Date> startAndEndDate = getCourseStartAndEndDate(courseId);
        ArrayList<Integer> courseWeekDays = getCourseLessonDays(courseId);
        int totalLessons = 0;
        dataSource = new DBConnector();
        try (Connection con = dataSource.getConnection()) {
            String sql = "declare @from datetime= ? " +
                    "declare @to datetime  = ? " +
                    "select" +
                    " datediff(day, -7, @to)/7-datediff(day, -6, @from)/7 AS MON," +
                    " datediff(day, -6, @to)/7-datediff(day, -5, @from)/7 AS TUE," +
                    " datediff(day, -5, @to)/7-datediff(day, -4, @from)/7 AS WED," +
                    " datediff(day, -4, @to)/7-datediff(day, -3, @from)/7 AS THU," +
                    " datediff(day, -3, @to)/7-datediff(day, -2, @from)/7 AS FRI," +
                    " datediff(day, -2, @to)/7-datediff(day, -1, @from)/7 AS SAT," +
                    " datediff(day, -1, @to)/7-datediff(day, 0, @from)/7 AS SUN";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDate(1, startAndEndDate.get("startDate"));
            pstmt.setDate(2, new Date(new java.util.Date().getTime()));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                for (int i : courseWeekDays) {
                    switch (i) {
                        case 1:
                            totalLessons = totalLessons + rs.getInt("MON");
                            break;
                        case 2:
                            totalLessons = totalLessons + rs.getInt("TUE");
                            break;
                        case 3:
                            totalLessons = totalLessons + rs.getInt("WED");
                            break;
                        case 4:
                            totalLessons = totalLessons + rs.getInt("THU");
                            break;
                        case 5:
                            totalLessons = totalLessons + rs.getInt("FRI");
                            break;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return totalLessons;
    }

    /**
     * Helper method for the getCourseDaysInSemesterCourse to
     * get the start and end date for a specific course.
     *
     * @param id Name of the course to get the dates from.
     * @return Start and end date of the course.
     */
    private HashMap<String, Date> getCourseStartAndEndDate(int id) {
        HashMap<String, Date> startAndEndDates = new HashMap<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT c.Name, c.StartDate, c.EndDate " +
                    "FROM Courses AS c " +
                    "WHERE c.Id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("EndDate");
                startAndEndDates.put("startDate", startDate);
                startAndEndDates.put("endDate", endDate);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return startAndEndDates;
    }

    /**
     * Helper method for the getCourseDaysInSemesterCourse to
     * get the days in which a course has lessons.
     *
     * @param id Name of the course to get days from.
     * @return An ArrayList of Integers which hold the dates
     * 1 is equal to monday, 2 is equal to tuesday etc.
     */
    private ArrayList<Integer> getCourseLessonDays(int id) {
        ArrayList<Integer> courseWeekDays = new ArrayList<>();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT CDOW.Weekday " +
                    "FROM CoursesDayOfWeek AS CDOW " +
                    "INNER JOIN Courses AS C ON CDOW.CourseId = c.Id " +
                    "WHERE C.Id = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer weekDay = rs.getInt("WeekDay");
                courseWeekDays.add(weekDay);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return courseWeekDays;
    }

    public boolean checkLogin(String username, String password, String Role) {
        dataSource = new DBConnector();
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT U.Id , U.Username, U.Password " +
                    "FROM [USER] AS U " +
                    "WHERE U.Username = ? " +
                    "AND U.Password = ? " +
                    "AND U.Role = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, Role);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LoginSession.setUserId(rs.getInt("Id"));
                LoginSession.setUserName(rs.getString("Username"));
                LoginSession.setIsLoggedIn(true);
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}