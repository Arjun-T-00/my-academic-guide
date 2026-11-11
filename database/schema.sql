
-- My Academic Guide
-- Database setup script
-- MySQL 8.0+

CREATE DATABASE IF NOT EXISTS academic_guide
CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;

USE academic_guide;


-- 1. Students table
CREATE TABLE IF NOT EXISTS students (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY email (email)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;


-- 2. Notes table
CREATE TABLE IF NOT EXISTS notes (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    student_id INT DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_notes_student (student_id),
    CONSTRAINT fk_notes_student
        FOREIGN KEY (student_id)
        REFERENCES students (id)
        ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;


-- 3. Subjects table
CREATE TABLE IF NOT EXISTS subjects (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    student_id INT DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY unique_student_subject (student_id, name),
    CONSTRAINT fk_subjects_student
        FOREIGN KEY (student_id)
        REFERENCES students (id)
        ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;


-- 4. Tasks table
CREATE TABLE IF NOT EXISTS tasks (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed TINYINT(1) DEFAULT '0',
    created TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    student_id INT DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_tasks_student (student_id),
    CONSTRAINT fk_tasks_student
        FOREIGN KEY (student_id)
        REFERENCES students (id)
        ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;


-- 5. PDFs table
CREATE TABLE IF NOT EXISTS pdfs (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    subject_id INT NOT NULL,
    uploaded_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY subject_id (subject_id),
    CONSTRAINT pdfs_ibfk_1
        FOREIGN KEY (subject_id)
        REFERENCES subjects (id)
        ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;
