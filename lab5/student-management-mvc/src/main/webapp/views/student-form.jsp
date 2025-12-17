<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${student != null}">Edit Student</c:when>
            <c:otherwise>Add New Student</c:otherwise>
        </c:choose>
    </title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            background-color: #f5f7fb;
            color: #111827;
        }

        .container {
            max-width: 600px;
            margin: 40px auto;
            padding: 24px;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
        }

        h1 {
            font-size: 22px;
            margin-bottom: 18px;
            color: #1d3557;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            font-weight: 600;
            color: #374151;
        }

        input[type="text"],
        input[type="email"],
        select {
            width: 100%;
            padding: 9px 10px;
            border-radius: 8px;
            border: 1px solid #d1d5db;
            font-size: 14px;
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        select:focus {
            outline: none;
            border-color: #2563eb;
            box-shadow: 0 0 0 1px #2563eb33;
        }

        .error {
            color: #b91c1c;
            font-size: 13px;
            display: block;
            margin-top: 4px;
        }

        .form-actions {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }

        .btn {
            padding: 9px 16px;
            border-radius: 999px;
            border: none;
            cursor: pointer;
            font-size: 14px;
            text-decoration: none;
            text-align: center;
        }

        .btn-primary {
            background: #2563eb;
            color: #fff;
        }

        .btn-primary:hover {
            background: #1d4ed8;
        }

        .btn-secondary {
            background: #e5e7eb;
            color: #111827;
        }

        .btn-secondary:hover {
            background: #d1d5db;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>
        <c:choose>
            <c:when test="${student != null}">Edit Student</c:when>
            <c:otherwise>Add New Student</c:otherwise>
        </c:choose>
    </h1>

    <form action="student" method="post">
        <c:choose>
            <c:when test="${student != null}">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="${student.id}">
            </c:when>
            <c:otherwise>
                <input type="hidden" name="action" value="insert">
            </c:otherwise>
        </c:choose>

        <div class="form-group">
            <label for="studentCode">Student Code:</label>
            <input type="text" id="studentCode" name="studentCode"
                   value="${student.studentCode}">
            <c:if test="${not empty errorCode}">
                <span class="error">${errorCode}</span>
            </c:if>
        </div>

        <div class="form-group">
            <label for="fullName">Full Name:</label>
            <input type="text" id="fullName" name="fullName"
                   value="${student.fullName}">
            <c:if test="${not empty errorName}">
                <span class="error">${errorName}</span>
            </c:if>
        </div>

        <div class="form-group">
            <label for="email">Email (optional):</label>
            <input type="email" id="email" name="email"
                   value="${student.email}">
            <c:if test="${not empty errorEmail}">
                <span class="error">${errorEmail}</span>
            </c:if>
        </div>

        <div class="form-group">
            <label for="major">Major:</label>
            <select id="major" name="major">
                <option value="">-- Select Major --</option>
                <option value="Computer Science" ${student.major == 'Computer Science' ? 'selected' : ''}>
                    Computer Science
                </option>
                <option value="Information Technology" ${student.major == 'Information Technology' ? 'selected' : ''}>
                    Information Technology
                </option>
                <option value="Software Engineering" ${student.major == 'Software Engineering' ? 'selected' : ''}>
                    Software Engineering
                </option>
                <option value="Business Administration" ${student.major == 'Business Administration' ? 'selected' : ''}>
                    Business Administration
                </option>
            </select>
            <c:if test="${not empty errorMajor}">
                <span class="error">${errorMajor}</span>
            </c:if>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                <c:choose>
                    <c:when test="${student != null}">
                        💾 Update
                    </c:when>
                    <c:otherwise>
                        ➕ Add Student
                    </c:otherwise>
                </c:choose>
            </button>
            <a href="student?action=list" class="btn btn-secondary">
                ❌ Cancel
            </a>
        </div>
    </form>
</div>
</body>
</html>
