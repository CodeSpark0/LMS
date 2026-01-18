# LMS Console — Learning Management System (Java OOP + PostgreSQL)

## 📌 About the project
This project is a beginner-friendly **Learning Management System (LMS)** built as a **console application**.  
The main idea is to manage a small learning process: store users and courses, enroll students into courses, and (in the future) record grades.

Project goal: demonstrate a clean **OOP structure**, working with **PostgreSQL** via **JDBC**, and at least one real business operation (not only CRUD).

---

## ✨ What the system can do
### Roles
- **TEACHER** — manages the learning process (for example, enrolls students into courses)
- **STUDENT** — uses the system to view personal data (can be expanded later)

### Core features
- ✅ **Login** (authentication using data from the database)
- ✅ **Courses** (stored in the database)
- ✅ **Enrollment (business operation)** — enrolling a student into a course with checks:
    - the student exists and has the `STUDENT` role
    - the course exists
    - a student cannot be enrolled twice in the same course
    - enrollment is not allowed if the course is full (capacity limit)

> All data is stored in PostgreSQL, so the results can be seen directly in the database tables.

---

## 🧩 Project structure (how it is organized)
The project is divided into layers to keep responsibilities clear:

- **`entity`** — data classes (User, Course, Enrollment)  
  *Only fields + basic methods (getters/setters, `toString`).*

- **`repository`** — database access (SQL/JDBC)  
  *Executes queries: find, save, count records, etc.*

- **`service`** — business logic and validations  
  *For example: checking capacity and rules before enrollment.*

- **`controller`** — connects UI and services  
  *Handles menu actions and calls the required service methods.*

- **`ui`** — console interface  
  *Displays menus, reads input, prints results/errors.*

- **`util`** — helper classes  
  *For example, a DB connection manager (`DatabaseManager`).*

---

## 🗃️ Database (what is stored)
Data is stored in PostgreSQL tables:
- **`users`** — login, password (learning/demo version), role, full name
- **`courses`** — course title and capacity
- **`enrollments`** — student ↔ course relation + (optional) grade


---

## 🕹️ How to use (console flow)
Example usage:
1. User logs in (login/password)
2. The system detects the role (TEACHER/STUDENT)
3. A role-based menu is shown
4. TEACHER selects an action (e.g., enroll a student)
5. The result is saved to the database and shown in the console

---

## 🛠️ Technologies & languages
- **Java** — main application logic (OOP, console UI)
- **PostgreSQL** — data storage
- **JDBC** — Java ↔ PostgreSQL connection
- **SQL** — table schema and test data scripts

---

## 👥 Team
# **CodeSpark0** (Kuttybayev Alikhan) 
# 

