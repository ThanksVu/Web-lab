package com.student.controller;

import com.student.dao.StudentDAO;
import com.student.model.Student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/student")
public class StudentController extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "insert":
                insertStudent(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "update":
                updateStudent(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            case "search":
                searchStudents(request, response);
                break;
            case "filter":           
                filterByMajor(request, response);
                break;
            case "list":
            default:
                listStudents(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> students = studentDAO.getAllStudents();
        request.setAttribute("students", students);
        request.setAttribute("selectedMajor", null);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/views/student-list.jsp");
        dispatcher.forward(request, response);
    }

    // Hiển thị form thêm mới
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/views/student-form.jsp");
        dispatcher.forward(request, response);
    }

    // showEditForm
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentDAO.getStudentById(id);
        request.setAttribute("student", existingStudent);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/views/student-form.jsp");
        dispatcher.forward(request, response);
    }

    // insertStudent
    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Student s = buildStudentFromRequest(request);
        studentDAO.insertStudent(s);
        response.sendRedirect("student"); 
    }

    // updateStuden
    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Student s = buildStudentFromRequest(request);
        s.setId(Integer.parseInt(request.getParameter("id")));

        studentDAO.updateStudent(s);
        response.sendRedirect("student");
    }

    // deleteStuden
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        studentDAO.deleteStudent(id);
        response.sendRedirect("student");
    }

    // search
    private void searchStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        if (keyword != null) keyword = keyword.trim();

        List<Student> students = studentDAO.searchStudents(keyword);
        request.setAttribute("students", students);
        request.setAttribute("keyword", keyword);

        request.setAttribute("selectedMajor", null);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/views/student-list.jsp");
        dispatcher.forward(request, response);
    }

    // filter 
    private void filterByMajor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String major = request.getParameter("major");
        if (major != null) major = major.trim();

        List<Student> students = studentDAO.filterStudentsByMajor(major);
        request.setAttribute("students", students);

        request.setAttribute("selectedMajor", major);

        request.setAttribute("keyword", null);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/views/student-list.jsp");
        dispatcher.forward(request, response);
    }

    // read form
    private Student buildStudentFromRequest(HttpServletRequest request) {
        String code = request.getParameter("studentCode");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String major = request.getParameter("major");

        Student s = new Student();
        s.setStudentCode(code);
        s.setFullName(fullName);
        s.setEmail(email);
        s.setMajor(major);

        return s;
    }
}
