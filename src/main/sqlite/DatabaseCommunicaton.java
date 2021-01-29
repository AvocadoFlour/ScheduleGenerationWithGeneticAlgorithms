package main.sqlite;

import main.classes.LectureHall;
import main.classes.Lecturer;

import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<Lecturer> deleteLecturer (Integer id) throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "DELETE FROM lecturers WHERE lecturer_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        return queryLecturers();
    }

    public void createCoursesTable() {
    }

    public void addCourse () {
    }

    public void queryCourses () {
    }

    public void createClassGroupsTable() {
    }

    public void addClassGroups () {
    }

    public void queryClassGroup () {
    }

    public void createLecturesTable() {
    }

    public void addLecture () {
    }

    public void queryLectures () {
    }

    public void createVocationsTable() {
    }

    public void addVocation () {
    }

    public void queryVocations () {
    }

}
