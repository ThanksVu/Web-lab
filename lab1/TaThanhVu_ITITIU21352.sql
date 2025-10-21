
-- TaThanhVu_ITITIU21352.sql
-- Database creation script based on the provided ERD (Student–Course–Lecturer–Department)
-- MySQL-compatible

-- 1) Safety cleanup (optional for repeatable runs)
DROP DATABASE IF EXISTS university_lab1;
CREATE DATABASE university_lab1 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE university_lab1;

-- 2) Tables
CREATE TABLE Department (
    department_id   INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Lecturer (
    lecturer_id   INT AUTO_INCREMENT PRIMARY KEY,
    lecturer_name VARCHAR(100) NOT NULL,
    department_id INT NOT NULL,
    CONSTRAINT fk_lecturer_department
      FOREIGN KEY (department_id) REFERENCES Department(department_id)
      ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Course (
    course_id   INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(120) NOT NULL UNIQUE
);

CREATE TABLE Student (
    student_id   INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(120) NOT NULL,
    dob          DATE,
    major        VARCHAR(120)
);

-- Relationship: Student registers Course
CREATE TABLE Register (
    student_id INT NOT NULL,
    course_id  INT NOT NULL,
    reg_date   DATE DEFAULT (CURRENT_DATE),
    PRIMARY KEY (student_id, course_id),
    CONSTRAINT fk_register_student
      FOREIGN KEY (student_id) REFERENCES Student(student_id)
      ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_register_course
      FOREIGN KEY (course_id) REFERENCES Course(course_id)
      ON UPDATE CASCADE ON DELETE CASCADE
);

-- Relationship: Lecturer teaches Course
CREATE TABLE Teach (
    lecturer_id INT NOT NULL,
    course_id   INT NOT NULL,
    PRIMARY KEY (lecturer_id, course_id),
    CONSTRAINT fk_teach_lecturer
      FOREIGN KEY (lecturer_id) REFERENCES Lecturer(lecturer_id)
      ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_teach_course
      FOREIGN KEY (course_id) REFERENCES Course(course_id)
      ON UPDATE CASCADE ON DELETE CASCADE
);

-- 3) Sample data (small seed so export contains both schema + data)
INSERT INTO Department (department_name) VALUES
('Computer Science'),
('Information Systems'),
('Electrical Engineering');

INSERT INTO Lecturer (lecturer_name, department_id) VALUES
('Dr. Tran Minh Quan', 1),
('Ms. Le Thi Thu', 2),
('Assoc. Prof. Nguyen Van An', 1);

INSERT INTO Course (course_name) VALUES
('Database Systems'),
('Data Structures'),
('Computer Networks');

INSERT INTO Student (student_name, dob, major) VALUES
('Ta Thanh Vu',         '2003-10-05', 'Information Technology'),
('Nguyen Thanh Khiem',  '2005-06-11', 'Computer Science'),
('Pham Gia Bao',        '2004-12-03', 'Information Systems'),
('Le Minh Chau',        '2003-04-18', 'Computer Networks');

-- Lecturer–Course (Teach)
INSERT INTO Teach (lecturer_id, course_id) VALUES
(1, 1), -- Dr. Quan teaches Database Systems
(3, 2), -- Assoc. Prof. An teaches Data Structures
(2, 3); -- Ms. Thu teaches Computer Networks

-- Student–Course (Register)
INSERT INTO Register (student_id, course_id, reg_date) VALUES
(1, 1, '2025-09-15'),
(1, 3, '2025-09-16'),
(2, 1, '2025-09-15'),
(2, 2, '2025-09-17'),
(3, 1, '2025-09-18'),
(4, 3, '2025-09-19');
