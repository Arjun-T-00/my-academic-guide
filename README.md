# My Academic Guide 🎓

A web-based academic management application built using **Java, Spring Boot, Thymeleaf, and MySQL**.

My Academic Guide helps students organize their academic activities in one place, manage personal notes, track tasks, and store study materials.

## ✨ Features

### 🔐 Student Authentication
- Student registration and login.
- Secure password hashing using PBKDF2.
- Separate accounts for individual students.

### 📝 My Notes
- Create personal notes.
- Edit and delete existing notes.
- Notes are associated with individual student accounts.

### ✅ To-Do List
- Add tasks with titles and descriptions.
- Edit and delete tasks.
- Mark tasks as completed or undo completion.
- Manage tasks independently for each student.

### 📚 PDF Library
- Create custom subjects.
- Upload PDF study materials.
- Organize PDFs using custom titles.
- Open, edit, and delete uploaded PDFs.

### 🌐 Study Resources
- Quick access to the REC Database website for additional academic resources.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Application framework |
| Thymeleaf | Server-side HTML rendering |
| HTML | Web page structure |
| CSS | Styling and design |
| JavaScript | Frontend interactions |
| MySQL | Database |
| JDBC | Database connectivity |
| Maven | Project and dependency management |

## ⚙️ Requirements

Before running the project, make sure you have:

- Java Development Kit (JDK)
- MySQL Server
- MySQL Workbench (optional, for database management)
- Git

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Arjun-T-00/my-academic-guide.git
```

### 2. Open the project

```bash
cd my-academic-guide
```

### 3. Create the database

Open MySQL Workbench and execute:

```sql
CREATE DATABASE academic_guide;
```

### 4. Configure the database password

The application uses an environment variable for the MySQL password.

On Windows PowerShell:

```powershell
setx DB_PASSWORD "YOUR_MYSQL_PASSWORD"
```

Replace `YOUR_MYSQL_PASSWORD` with your local MySQL password.

Restart your terminal or VS Code after setting the variable.

The database configuration is in:

`src/main/resources/application.properties`

### 5. Run the application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Open your browser and visit:

```text
http://localhost:8080
```

## 📁 Project Structure

```text
my-academic-guide/
│
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/academicguide/
│   │   │   ├── controllers
│   │   │   ├── repositories
│   │   │   ├── models
│   │   │   └── services
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## 🔒 Security and Privacy

- Passwords are hashed before storage.
- Database credentials are configured through environment variables.
- Uploaded PDF files are excluded from Git using `.gitignore`.
- Student data is separated by account.

## 👨‍💻 Developer

**Arjun T.**

Computer Science and Engineering

Rajalakshmi Engineering College

---

*Built as a personal academic management project to make student life more organized and productive.*