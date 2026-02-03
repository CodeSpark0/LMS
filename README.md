# LMS Console — Learning Management System (Java OOP + PostgreSQL)

## 📌 About the project
This project is a beginner-friendly **Learning Management System (LMS)** built as a **console application**.  
It stores **users** and **courses**, supports **login**, and implements a real business operation: **enrolling a student into a course**.

**Goal:** demonstrate clean **OOP structure**, **PostgreSQL + JDBC** connection, and business rules (not only CRUD).

## 🔐 Demo logins (from seed.sql)
- **Teacher:** `teacher1 / 1234`
- **Student:** `stud1 / 1111`
- **Student:** `stud2 / 2222`

---

## 📂 Project Structure
```
📂 src
├── 📂 controllers       
│   ├── AuthController.java
│   ├── StudentController.java
│   ├── TeacherController.java
│
├── 📂 entity           
│   ├── Course.java
│   ├── Enrollment.java
│   ├── Grade.java
│   ├── Role.java
│   ├── Student.java
│   ├── Teacher.java
│   ├── User.java
│   
├── 📂 repository       
│   ├── CourseRepository.java
│   ├── EnrollmentRepository.java
│   ├── GradeRepository.java
│   ├── StudentRepository.java
│   ├── TeacherRepository.java
│  
├── 📂 services
│   ├── AuthResult.java
│   ├── AuthService.java
│   ├── CourseService.java
│   ├── EnrollmentService.java
│   ├── StudentService.java
│   ├── TeacherService.java
│  
├── 📂 util
│   ├── DatabaseManager.java
│   ├── Session.java
│ 
├── Main.java 
│ 
├─📂 resources         
│  ├── application.properties
            
├── README.md               
```

## 📌 Contributors
📌 **Team Members**:
- [Alikhan](https://github.com/CodeSpark0)
- [Alua](https://github.com/poterpish)
- [Nurasyl](https://github.com/Nurasyl-MN)
