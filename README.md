# LMS Console — Learning Management System (Java OOP + PostgreSQL)

## 📌 About the project
This project is a beginner-friendly **Learning Management System (LMS)** built as a **console application**.  
It stores **users** and **courses**, supports **login**, and implements a real business operation: **enrolling a student into a course**.

**Goal:** demonstrate clean **OOP structure**, **PostgreSQL + JDBC** connection, and business rules (not only CRUD).

---

## 🧩 Project structure
- **`entity`** — data classes (User, Course, Enrollment)
- **`repository`** — SQL/JDBC database operations
- **`service`** — business logic and validations
- **`controller`** — connects UI and services
- **`ui`** — console menus and input/output
- **`util`** — helpers (e.g., `DatabaseManager`)

---

## 🧾 How to apply SQL (pgAdmin 4)
SQL scripts are in `sql/`:
- `sql/schema.sql` — creates tables
- `sql/seed.sql` — inserts demo data

Steps:
1. Create database (example: `lms_db`)
2. Open **Query Tool** in pgAdmin 4
3. Run `schema.sql`, then run `seed.sql`

---

## ▶️ How to run
1. Open the project in **IntelliJ IDEA**
2. Set DB credentials (recommended):
   create `src/main/resources/application.properties`

db.url=  
db.user=  
db.password=


3. Run the `Main` class  
   If connection is correct, the app prints: **DB OK**

---

## 🔐 Demo logins (from seed.sql)
- **Teacher:** `teacher1 / 1234`
- **Student:** `stud1 / 1111`
- **Student:** `stud2 / 2222`

---

## 👥 Team
**CodeSpark0** — Kuttybayev Alikhan
