package main.sqlite;

import main.classes.*;
import org.sqlite.SQLiteConfig;

import static java.lang.Math.toIntExact;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class DatabaseCommunicaton {

    public static final String DB_URL = "jdbc:sqlite:database.db";

    private Connection connectToDatabase () throws SQLException {
        Connection connection;
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        // Turning on foreign key constraints https://stackoverflow.com/a/13377015/10299831
        connection = DriverManager.getConnection(DB_URL,config.toProperties());
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
                " lecturer_name VARCHAR(20) NOT NULL, " +
                " lecturer_last_name VARCHAR(20) NOT NULL," +
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

        //Creating the vocations course requirement entries
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
            long vocationId = rs.getLong("vocation_id");
            String vocationName = rs.getString("vocation_name");
            HashMap<Course, Integer> courseRequirements = queryVocationsCourseRequirements(toIntExact(vocationId));
            Vocation v = new Vocation(vocationId,vocationName,courseRequirements);
            vocationsList.add(v);
        }
        connection.close();
        return vocationsList;
    }

    public Vocation querySingleVocationWithIntegerId(int vocationId) throws SQLException {
        Connection connection = connectToDatabase();
        String query = "SELECT * FROM vocations WHERE vocation_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, vocationId);
        ResultSet rs = preparedStatement.executeQuery();
        rs.getLong("vocation_id");
        String vocationName = rs.getString("vocation_name");
        HashMap<Course, Integer> courseRequirements = queryVocationsCourseRequirements(toIntExact(vocationId));
        connection.close();
        return new Vocation(vocationId, vocationName, courseRequirements);
    }

    /* performs a check checking whether or not the provided vocation('s id) is a
     * foreign key of a class group. If a class group with the provided vocation exists,
     * returns true. If the vocation has no class groups associated with it, returns false.
     */
    public boolean checkVocationConstraint(Integer id) throws SQLException {
        Connection connection = connectToDatabase();
        String vocationConstraintCheck = "SELECT EXISTS(SELECT 1 FROM class_groups WHERE vocation_id = ? );";
        PreparedStatement vocationConstraintCheckPreparedStatement = connection.prepareStatement(vocationConstraintCheck);
        vocationConstraintCheckPreparedStatement.setInt(1, id);
        ResultSet rs = vocationConstraintCheckPreparedStatement.executeQuery();
        int check = rs.getInt(1);
        connection.close();
        if (check == 0) {
            return false;
        } else {
            System.out.println(check + " : rs.getInt");
            return true;
        }

    }

    public void deleteVocation(Integer id) throws SQLException {

        Connection connection = connectToDatabase();
        String vocationCourseRequirementsDeleteSql = "DELETE FROM vocation_course_requirements WHERE vocation_id = ?";
        PreparedStatement vcrdSql = connection.prepareStatement(vocationCourseRequirementsDeleteSql);
        vcrdSql.setLong(1, id);
        vcrdSql.executeUpdate();
        String vocationDeleteSql = "DELETE FROM vocations WHERE vocation_id = ?";
        PreparedStatement vdSql = connection.prepareStatement(vocationDeleteSql);
        vdSql.setLong(1, id);
        vdSql.executeUpdate();
        connection.close();
    }

    public void createVocationsCourseRequirementsTable() throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS vocation_course_requirements " +
                "(vocation_id INTEGER NOT NULL, " +
                "course_id INTEGER NOT NULL, " +
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

    public void createClassGroupsTable() throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "CREATE TABLE IF NOT EXISTS class_groups " +
                "(class_group_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "class_group_identification VARCHAR(10) NOT NULL, " +
                "vocation_id INTEGER NOT NULL, " +
                "number_of_students INTEGER NOT NULL, " +
                "FOREIGN KEY(vocation_id) REFERENCES vocations(vocation_id));";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public void addClassGroup (String classGroupIdentification, int vocationId, int numberOfStudents) throws SQLException {
        Connection connection = connectToDatabase();
        String sql = "INSERT INTO class_groups (class_group_identification, vocation_id, number_of_students) VALUES " +
                "('" + classGroupIdentification + "', '" + vocationId + "', '" + numberOfStudents + "');";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }

    public ArrayList<ClassGroup> queryClassGroups () throws SQLException {
        ArrayList<ClassGroup> classGroupsList = new ArrayList<>();
        Connection connection = connectToDatabase();
        String query = "SELECT * FROM class_groups";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            long classGroupId = rs.getLong("class_group_id");
            int numberOfStudents = rs.getInt("number_of_students");
            String classGroupIdentification = rs.getString("class_group_identification");
            int vocationId = rs.getInt("vocation_id");
            Vocation vocation = querySingleVocationWithIntegerId(vocationId);
            classGroupsList.add(new ClassGroup(classGroupId, numberOfStudents, classGroupIdentification, vocation));
        }
        connection.close();
        return classGroupsList;
    }

    public void deleteClassGroup(int classGroupId) throws SQLException {
        Connection connection = connectToDatabase();
        String classGroupDeleteSql = "DELETE FROM class_groups WHERE class_group_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(classGroupDeleteSql);
        preparedStatement.setInt(1, classGroupId);
        preparedStatement.executeUpdate();
        connection.close();
    }

}
