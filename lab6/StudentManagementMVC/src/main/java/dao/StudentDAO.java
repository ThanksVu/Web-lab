package dao;

import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM students";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM students WHERE id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO students(student_code, full_name, email, major) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE =
            "UPDATE students SET student_code=?, full_name=?, email=?, major=? WHERE id=?";
    private static final String SQL_DELETE =
            "DELETE FROM students WHERE id=?";
    private static final String SQL_COUNT_ALL =
            "SELECT COUNT(*) AS total FROM students";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");   
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }

    public List<Student> getAllStudents() {
    List<Student> list = new ArrayList<>();

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_ALL);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            list.add(mapRowToStudent(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}


    public Student getStudentById(int id) {
        Student s = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    s = mapRowToStudent(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }

    public void insertStudent(Student s) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_INSERT)) {

            pstmt.setString(1, s.getStudentCode());
            pstmt.setString(2, s.getFullName());
            pstmt.setString(3, s.getEmail());
            pstmt.setString(4, s.getMajor());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student s) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_UPDATE)) {

            pstmt.setString(1, s.getStudentCode());
            pstmt.setString(2, s.getFullName());
            pstmt.setString(3, s.getEmail());
            pstmt.setString(4, s.getMajor());
            pstmt.setInt(5, s.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_DELETE)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTotalStudents() {
        int total = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL_COUNT_ALL);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setStudentCode(rs.getString("student_code"));
        s.setFullName(rs.getString("full_name"));
        s.setEmail(rs.getString("email"));
        s.setMajor(rs.getString("major"));
        s.setCreatedAt(rs.getTimestamp("created_at"));
        return s;
    }
}
