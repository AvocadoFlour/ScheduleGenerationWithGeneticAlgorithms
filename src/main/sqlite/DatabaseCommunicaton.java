package main.sqlite;

import main.classes.Course;
import main.classes.LectureHall;
import main.classes.Lecturer;
import main.classes.Vocation;

import javax.swing.*;

import static java.lang.Math.toIntExact;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseCommunicaton {

    private Connection connectToDatabase () throws SQLException {
        String jdbcUrl = "jdbc:sqlite:database.db";
        Connection connection;
        connection = DriverManager.getConnection(jdbcUrl);
        return connection;
    }

    public void createLectureHallsTable () throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS lecture_halls " +
                "(hall_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " hall_code VARCHAR(20) UNIQUE NOT NULL, " +
                " hall_capacity INTEGER NOT NULL);";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public void addLectureHall(String hallCode, int hallCapacity) throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "INSERT INTO lecture_halls (hall_code, hall_capacity) VALUES ('" + hallCode + "','" + hallCapacity + "');";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public ArrayList<LectureHall> queryLectureHalls () throws SQLException {
        ArrayList<LectureHall> lectureHallsList = new ArrayList<>();
        Connection connection = connectToDatabase();
        String query = "SELECT * FROM lecture_halls";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            int hallId = rs.getInt("hall_id");
            String hallCode = rs.getString("hall_code");
            int hallCapacity = rs.getInt("hall_capacity");
            LectureHall lh = new LectureHall(hallId, hallCode, hallCapacity);
            lectureHallsList.add(lh);
        }
        connection.close();
        return lectureHallsList;
    }

    public ArrayList<LectureHall> deleteLectureHall(Integer id) throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "DELETE FROM lecture_halls WHERE hall_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        return queryLectureHalls();
    }

    public void createLecturersTable () throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS lecturers " +
                "(lecturer_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " lecturer_name VARCHAR(20) UNIQUE NOT NULL, " +
                " lecturer_last_name VARCHAR(20) UNIQUE NOT NULL," +
                " lecturer_qualifications INTEGER NOT NULL);";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public void addLecturer (String name, String lastName, Integer qualifications) throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "INSERT INTO lecturers (lecturer_name, lecturer_last_name, lecturer_qualifications) " +
                "VALUES ('" + name + "','" + lastName + "','" + qualifications + "');";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public ArrayList<Lecturer> queryLecturers () throws SQLException {
        ArrayList<Lecturer> lecturersList = new ArrayList<>();
        Connection connection = connectToDatabase();
        String query = "SELECT * FROM lecturers";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            int lecturerId = rs.getInt("lecturer_id");
            String name = rs.getString("lecturer_name");
            String lastName = rs.getString("lecturer_last_name");
            Integer qualifications = rs.getInt("lecturer_qualifications");
            Lecturer l = new Lecturer(lecturerId, name, lastName, Lecturer.convertToIntegerArray(qualifications));
            lecturersList.add(l);
        }
        connection.close();
        return lecturersList;
    }

    public void deleteLecturer (Integer id) throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "DELETE FROM lecturers WHERE lecturer_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void createCoursesTable() throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS courses " +
                "(course_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " course_name VARCHAR(20) UNIQUE NOT NULL, " +
                " course_qualification INTEGER NOT NULL);";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public void addCourse (String courseName, int courseQualification) throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "INSERT INTO courses (course_name, course_qualification) " +
                "VALUES ('" + courseName + "','" + courseQualification + "');";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public ArrayList<Course> queryCourses () throws SQLException {
        ArrayList<Course> coursesList = new ArrayList<>();
        Connection connection = connectToDatabase();
        String querry = "SELECT * FROM courses";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(querry);
        while (rs.next()) {
            Long courseId = rs.getLong("course_id");
            String courseName = rs.getString("course_name");
            int courseQualification = rs.getInt("course_qualification");
            Course c = new Course(courseId, courseName, courseQualification);
            coursesList.add(c);
        }
        connection.close();
        return coursesList;
    }

    public Course queryCourse (Long id) throws SQLException {
        Course course = null;
        Connection connection = connectToDatabase();
        String query = "SELECT * FROM courses WHERE course_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Long courseId = rs.getLong("course_id");
            String courseName = rs.getString("course_name");
            int courseQualification = rs.getInt("course_qualification");
            course = new Course(courseId,courseName,courseQualification);
        }
        return course;
    }

    public void deleteCourse(Long id) throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "DELETE FROM courses WHERE course_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    public void createVocationsTable() throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS vocations " +
                "(vocation_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " vocation_name VARCHAR(30) NOT NULL);";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }
    public void addVocation (String vocationName, HashMap<Course, Integer> courseRequirements) throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "INSERT INTO vocations (vocation_name) " +
                "VALUES ('" + vocationName + "');";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

        //Getting the just created Vocation's table id
        ResultSet rs = statement.getGeneratedKeys();
        int vocationId = Integer.parseInt(rs.getString(1));
        String sqlBuilder = "INSERT INTO vocation_course_requirements (vocation_id, course_id, hours_required) VALUES";
        StringBuilder sb = new StringBuilder();
        sb.append(sqlBuilder);
        for(Map.Entry<Course, Integer> e : courseRequirements.entrySet()) {
            sb.append("('" + vocationId + "', '" +e.getKey().getId() + "', '" + e.getValue() + "'),");
        }
        sb.setLength(sb.length() - 1);
        sb.append(";");

        System.out.println(sb.toString());
        statement.executeUpdate(sb.toString());

    }

    public ArrayList<Vocation> queryVocations() throws SQLException {
        ArrayList<Vocation> vocationsList = new ArrayList<>();
        Connection connection = connectToDatabase();
        String query = "SELECT * FROM vocations";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            Long vocationId = rs.getLong("vocation_id");
            String vocationName = rs.getString("vocation_name");
            HashMap<Course, Integer> courseRequirements = queryVocationsCourseRequirements(toIntExact(vocationId));
            Vocation v = new Vocation(vocationId,vocationName,courseRequirements);
            vocationsList.add(v);
        }
        connection.close();
        return vocationsList;
    }

    public void deleteVocation(Integer id) throws SQLException {
        Connection connection = connectToDatabase();
        String vocationCourseRequirementsDeleteSql = "DELETE FROM vocation_course_requirements WHERE vocation_id = ?";
        PreparedStatement vcrdSql = connection.prepareStatement(vocationCourseRequirementsDeleteSql);
        vcrdSql.setLong(1, id);
        vcrdSql.executeUpdate();
        String vocationDeletesql = "DELETE FROM vocations WHERE vocation_id = ?";
        PreparedStatement vdSql = connection.prepareStatement(vocationDeletesql);
        vdSql.setLong(1, id);
        vdSql.executeUpdate();
        connection.close();
    }

    public void createVocationsCourseRequirementsTable() throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS vocation_course_requirements " +
                "(vocation_id INTEGER, " +
                "course_id INTEGER, " +
                "hours_required INTEGER NOT NULL, " +
                "FOREIGN KEY(vocation_id) REFERENCES vocations(vocation_id), " +
                "FOREIGN KEY(course_id) REFERENCES courses(course_id));";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    public HashMap<Course, Integer> queryVocationsCourseRequirements(Integer vocationId) throws SQLException {
        Connection connection = connectToDatabase();
        String query = "SELECT * FROM vocation_course_requirements WHERE vocation_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, vocationId);
        ResultSet rs = preparedStatement.executeQuery();
        HashMap<Course, Integer> courseRequirements = new HashMap<>();
        while (rs.next()) {
            long courseId = rs.getLong("course_id");
            Course course = queryCourse(courseId);
            int hoursRequired = rs.getInt("hours_required");
            courseRequirements.put(course, hoursRequired);
        }
        connection.close();
        return courseRequirements;
    }

    public void addVocationCourseRequirement() throws SQLException {

    }

    public void createClassGroupsTable() {
    }

    public void addClassGroups () {
    }

    public void queryClassGroup () {
    }

}
