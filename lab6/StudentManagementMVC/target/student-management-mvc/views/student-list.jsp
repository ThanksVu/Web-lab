<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student List - MVC</title>
    <style>
        /* --- CSS giữ nguyên như bạn --- */
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: #ffffff;
            border-radius: 10px;
            padding: 25px 30px 30px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
        }
        h1 { font-size: 26px; margin-bottom: 5px; color: #333;
             display: flex; align-items: center; gap: 10px; }
        h1 span.icon { font-size: 30px; }
        .subtitle { font-size: 14px; color: #666; margin-bottom: 25px; }
        .message { padding: 10px 15px; border-radius: 5px; margin-bottom: 15px;
                   font-size: 14px; font-weight: 500; }
        .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error   { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .top-bar { display: flex; justify-content: space-between; align-items: center;
                   gap: 10px; margin-bottom: 20px; flex-wrap: wrap; }
        .search-box { flex: 1; min-width: 260px; }
        .search-box form { display: flex; gap: 10px; }
        .search-input { flex: 1; padding: 10px 12px; border-radius: 5px;
                        border: 1px solid #ccc; font-size: 14px; }
        .search-info { margin-top: 8px; color: #555; font-style: italic; }
        .btn { display: inline-block; padding: 8px 14px; border-radius: 4px;
               border: none; cursor: pointer; font-size: 14px;
               text-decoration: none; text-align: center; }
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        .btn-primary:hover { opacity: 0.9; }
        .btn-secondary { background-color: #4a5568; color: #fff; }
        .btn-secondary:hover { background-color: #2d3748; }
        .btn-danger { background-color: #e53e3e; color: white; }
        .btn-danger:hover { background-color: #c53030; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        thead {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        th, td { padding: 10px 12px; text-align: left; font-size: 14px; }
        tbody tr:nth-child(even) { background-color: #f7fafc; }
        tbody tr:nth-child(odd)  { background-color: #edf2f7; }
        tbody tr:hover { background-color: #e2e8f0; }
        .actions { display: flex; gap: 8px; }
        .empty-state { text-align: center; padding: 40px 0; color: #555; }
        .empty-state-icon { font-size: 50px; margin-bottom: 10px; }
        .major-inline-filter-form {
            margin-top: 6px; display: flex; align-items: center;
            gap: 4px; flex-wrap: nowrap;
        }
        .major-inline-select {
            padding: 3px 6px; border-radius: 3px;
            border: 1px solid #e2e8f0; font-size: 11px;
        }
        .btn-filter-small { padding: 4px 8px; font-size: 11px; border-radius: 3px; }
        .clear-filter-link { font-size: 11px; color: #e2e8f0; text-decoration: underline; }
        .clear-filter-link:hover { color: #ffffff; }
    </style>
</head>
<body>
<div class="container">
    <h1>
        <span class="icon">📚</span>
        Student Management System
    </h1>
    <p class="subtitle">MVC Pattern with Jakarta EE &amp; JSTL</p>

    <!-- Success Message -->
    <c:if test="${not empty param.message}">
        <div class="message success">
            ✅ ${param.message}
        </div>
    </c:if>

    <!-- Error Message -->
    <c:if test="${not empty param.error}">
        <div class="message error">
            ❌ ${param.error}
        </div>
    </c:if>

    <!-- Search + Add New Student -->
    <div class="top-bar">
        <div class="search-box">
            <form action="student" method="get">
                <input type="hidden" name="action" value="search" />
                <input
                    type="text"
                    name="keyword"
                    class="search-input"
                    placeholder="🔍 Search by code, name or email..."
                    value="${keyword}" />
                <button type="submit" class="btn btn-secondary">Search</button>

                <c:if test="${not empty keyword}">
                    <a href="student?action=list" class="btn btn-secondary">
                        Show All
                    </a>
                </c:if>
            </form>

            <c:if test="${not empty keyword}">
                <div class="search-info">
                    Search results for: <strong>${keyword}</strong>
                </div>
            </c:if>
        </div>

        <div>
            <a href="student?action=new" class="btn btn-primary">
                ➕ Add New Student
            </a>
        </div>
    </div>

    <!-- Student Table -->
    <c:choose>
        <c:when test="${not empty students}">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Student Code</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>
                        Major
                        <form action="student" method="get" class="major-inline-filter-form">
                            <input type="hidden" name="action" value="filter" />
                            <select name="major" class="major-inline-select">
                                <option value="">All Majors</option>
                                <option value="Computer Science" ${selectedMajor == 'Computer Science' ? 'selected' : ''}>Computer Science</option>
                                <option value="Information Technology" ${selectedMajor == 'Information Technology' ? 'selected' : ''}>Information Technology</option>
                                <option value="Software Engineering" ${selectedMajor == 'Software Engineering' ? 'selected' : ''}>Software Engineering</option>
                                <option value="Business Administration" ${selectedMajor == 'Business Administration' ? 'selected' : ''}>Business Administration</option>
                            </select>
                            <button type="submit" class="btn btn-secondary btn-filter-small">
                                Filter
                            </button>
                            <c:if test="${not empty selectedMajor}">
                                <a href="student?action=list" class="clear-filter-link">
                                    Clear
                                </a>
                            </c:if>
                        </form>
                    </th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>${student.id}</td>
                        <td><strong>${student.studentCode}</strong></td>
                        <td>${student.fullName}</td>
                        <td>${student.email}</td>
                        <td>${student.major}</td>
                        <td>
                            <div class="actions">
                                <a href="student?action=edit&id=${student.id}" class="btn btn-secondary">
                                    ✏️ Edit
                                </a>
                                <a href="student?action=delete&id=${student.id}"
                                   class="btn btn-danger"
                                   onclick="return confirm('Are you sure you want to delete this student?');">
                                    🗑️ Delete
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <div class="empty-state-icon">📭</div>
                <h3>No students found</h3>
                <p>Start by adding a new student</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
