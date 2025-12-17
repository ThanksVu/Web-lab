package com.student.dao;

import com.student.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/student_management?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    // Load MySQL driver 
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Driver loaded");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Map ResultSet -> 1 Student
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

    // get all student
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id DESC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                students.add(mapRowToStudent(rs));
            }

            System.out.println("DEBUG getAllStudents: " + students.size() + " rows");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // find keyword (code / name / email)
    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents();
        }

        List<Student> students = new ArrayList<>();
        String sql = """
                SELECT * FROM students
                WHERE student_code LIKE ? OR full_name LIKE ? OR email LIKE ?
                ORDER BY id DESC
                """;

        String like = "%" + keyword.trim() + "%";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, like);
            pstmt.setString(2, like);
            pstmt.setString(3, like);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRowToStudent(rs));
                }
            }

            System.out.println("DEBUG searchStudents('" + keyword + "'): "
                    + students.size() + " rows");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // filter major
    public List<Student> filterStudentsByMajor(String major) {
        if (major == null || major.trim().isEmpty()) {
            return getAllStudents();
        }

        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE major = ? ORDER BY id DESC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, major);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRowToStudent(rs));
                }
            }

            System.out.println("DEBUG filterStudentsByMajor('" + major + "'): "
                    + students.size() + " rows");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // take 1 student by id
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToStudent(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // add student
    public void insertStudent(Student s) {
        String sql = "INSERT INTO students(student_code, full_name, email, major) " +
                     "VALUES (?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, s.getStudentCode());
            pstmt.setString(2, s.getFullName());
            pstmt.setString(3, s.getEmail());
            pstmt.setString(4, s.getMajor());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // update student
    public void updateStudent(Student s) {
        String sql = "UPDATE students SET student_code=?, full_name=?, email=?, major=? " +
                     "WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

    // delete student
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
