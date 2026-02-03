package services;

import entity.Course;
import entity.CourseDetailsDTO;
import repository.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseService {

    private final CourseRepository courseRepo;

    public CourseService(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }

    public void addCourse(Course course) {
        try {
            courseRepo.addCourse(course);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add course", e);
        }
    }

//Тут Лямбда -фильтрует курсы, где есть свободные места(Ищет курсы)
    public List<Course> getCoursesWithOpenSpots() {
        return courseRepo.findAll().stream()
                .filter(c -> c.getCapacity() > 0)
                .collect(Collectors.toList());}

//Это  Категорий + Лямбды - Фильтрация по строке
    public List<Course> filterByCategory(String category) {
        return courseRepo.findAll().stream()
                .filter(c -> c.getCategory().equalsIgnoreCase(category)) // Лямбда
                .collect(Collectors.toList());
    }
//JOIN Получает полные данные c DTO.
    public CourseDetailsDTO getFullCourseDescription(Long courseId) {
        return courseRepo.getCourseDetailsWithTeacher(courseId)
                .orElseThrow(() -> new RuntimeException("Course details not found"));
    }

    public void updateCourse(Course course) {
        try {
            Optional<Course> existing = courseRepo.findById(course.getId());
            if (existing.isEmpty()) {
                throw new RuntimeException("Course not found with id: " + course.getId());
            }
            courseRepo.updateCourse(course);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update course with id: " + course.getId(), e);
        }
    }

    public void deleteCourse(Long courseId) {
        try {
            Optional<Course> existing = courseRepo.findById(courseId);
            if (existing.isEmpty()) {
                throw new RuntimeException("Course not found with id: " + courseId);
            }
            courseRepo.deleteById(courseId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete course with id: " + courseId, e);
        }
    }

    public Course getCourseById(Long courseId) {
        try {
            return courseRepo.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
        } catch (Exception e) {
            throw new RuntimeException("Failed to get course with id: " + courseId, e);
        }
    }

    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public List<Course> getCoursesByTeacher(Long teacherId) {
        try {
            return courseRepo.findByTeacherId(teacherId);
        } catch(Exception e) {
            throw new RuntimeException("Failed to get course with teacher_id: " + teacherId, e);
        }
    }

}
