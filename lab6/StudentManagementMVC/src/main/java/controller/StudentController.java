package controller;

import dao.StudentDAO;
import model.Student;

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

    // ============ GET ============
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":           
                deleteStudent(request, response);
                break;
            case "list":
            default:
                listStudents(request, response);
                break;
        }
    }

    // ============ POST ============
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "insert":
                insertStudent(request, response);
                break;
            case "update":
                updateStudent(request, response);
                break;
            default:
                response.sendRedirect("student?action=list");
        }
    }

    // ============ LIST ============
    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Student> students = studentDAO.getAllStudents();
        request.setAttribute("students", students);

        // cho JSP đỡ null
        request.setAttribute("keyword", null);
        request.setAttribute("selectedMajor", null);

        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
            request.setAttribute("error", error);
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/views/student-list.jsp");
        dispatcher.forward(request, response);
    }

    // ============ NEW FORM ============
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // không set "student" -> JSP hiểu là Add New
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/views/student-form.jsp");
        dispatcher.forward(request, response);
    }

    // ============ EDIT FORM ============
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("student?action=list");
            return;
        }

        int id = Integer.parseInt(idParam);
        Student existingStudent = studentDAO.getStudentById(id);

        if (existingStudent == null) {
            response.sendRedirect("student?action=list");
            return;
        }

        request.setAttribute("student", existingStudent);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/views/student-form.jsp");
        dispatcher.forward(request, response);
    }

    // ============ INSERT ============
    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Student s = buildStudentFromRequest(request);

        if (!validateStudent(request, s)) {
            request.setAttribute("student", s);
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/views/student-form.jsp");
            dispatcher.forward(request, response);
            return;
        }

        studentDAO.insertStudent(s);
        response.sendRedirect("student?action=list");
    }

    // ============ UPDATE ============
    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("student?action=list");
            return;
        }

        Student s = buildStudentFromRequest(request);
        s.setId(Integer.parseInt(idParam));

        if (!validateStudent(request, s)) {
            request.setAttribute("student", s);
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/views/student-form.jsp");
            dispatcher.forward(request, response);
            return;
        }

        studentDAO.updateStudent(s);
        response.sendRedirect("student?action=list");
    }

    // ============ DELETE ============
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idParam = request.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            studentDAO.deleteStudent(id);
        }
        response.sendRedirect("student?action=list");
    }

    // ============ HELPER METHODS ============
    private Student buildStudentFromRequest(HttpServletRequest request) {
        String code     = request.getParameter("studentCode");
        String fullName = request.getParameter("fullName");
        String email    = request.getParameter("email");
        String major    = request.getParameter("major");

        Student s = new Student();
        s.setStudentCode(code);
        s.setFullName(fullName);
        s.setEmail(email);
        s.setMajor(major);

        return s;
    }

    private boolean validateStudent(HttpServletRequest request, Student s) {
        boolean valid = true;

        // Code
        if (s.getStudentCode() == null || s.getStudentCode().trim().isEmpty()) {
            request.setAttribute("errorCode", "Student code is required");
            valid = false;
        }

        // Name
        if (s.getFullName() == null || s.getFullName().trim().isEmpty()) {
            request.setAttribute("errorName", "Full name is required");
            valid = false;
        }

        // Email 
        if (s.getEmail() != null && !s.getEmail().trim().isEmpty()) {
            String email = s.getEmail().trim();
            if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                request.setAttribute("errorEmail", "Invalid email format");
                valid = false;
            }
        }

        // Major
        if (s.getMajor() == null || s.getMajor().trim().isEmpty()) {
            request.setAttribute("errorMajor", "Major is required");
            valid = false;
        }

        return valid;
    }
}
