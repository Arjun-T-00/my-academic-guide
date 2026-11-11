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

### 📚 My PDFs
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
- MySQL Server 8.0+
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

### 3. Set up the MySQL database

The project includes a database setup script:

`database/schema.sql`

To create the database and required tables:

1. Open MySQL Workbench.
2. Open the `database/schema.sql` file from the cloned project.
3. Execute the entire SQL script.

The script automatically creates the `academic_guide` database and the following five tables:

- `students`
- `notes`
- `tasks`
- `subjects`
- `pdfs`

### 4. Configure database credentials

The application uses an environment variable for the MySQL password.

On Windows PowerShell, execute:

```powershell
setx DB_PASSWORD "YOUR_MYSQL_PASSWORD"
```

Replace `YOUR_MYSQL_PASSWORD` with your own local MySQL password.

Restart your terminal or VS Code after setting the variable.

The database configuration is located at:

`src/main/resources/application.properties`

### 5. Run the application

On Windows, execute:

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
├── database/
│   └── schema.sql
│
├── .mvn/
│
├── src/
│   ├── main/
│   │   ├── java/com/academicguide/
│   │   │   ├── AcademicGuideApplication.java
│   │   │   ├── HomeController.java
│   │   │   ├── LoginController.java
│   │   │   ├── RegistrationController.java
│   │   │   ├── Student.java
│   │   │   ├── Note.java
│   │   │   ├── Task.java
│   │   │   ├── Subject.java
│   │   │   └── repositories and services
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       └── application.properties
│   │
│   └── test/
│
├── uploads/             (local PDF storage; not tracked by Git)
├── .gitignore
├── README.md
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
