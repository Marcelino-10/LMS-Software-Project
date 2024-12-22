package com.example.lms.service;

import com.example.lms.controller.CourseData;
import com.example.lms.model.course_related.Course;
import com.example.lms.model.course_related.FileEntity;
import com.example.lms.model.user_related.Instructor;
import com.example.lms.model.user_related.Student;
import com.example.lms.repository.CourseRepo;
import com.example.lms.repository.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepo courseRepo;

    private final InstructorService instructorService;

    private final FileService fileService;

    private final FileRepo fileRepo;

    private final StudentService studentService;

    private final NotificationService notificationService;

    @Override
    public Course addCourse(CourseData course) {
        Instructor instructor = instructorService.getInstructor(course.getInstructorId());
        Course newCourse = new Course(course, instructor);
        return courseRepo.save(newCourse);
    }

    @Override
    public Course getCourse(int id) {
        return courseRepo.findById(id).orElse(null);
    }

    @Override
    public void saveCourse(Course course) {
        courseRepo.save(course);
    }

    @Override
    public void deleteCourse(Integer id) {

    }

    @Override
    public void updateCourse(CourseData course) {

    }

    @Override
    public FileEntity uploadFile(int courseId, MultipartFile file) throws IOException {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new NoSuchElementException("Course not found with id " + courseId));

        FileEntity fileEntity = fileService.saveFile(file);

        course.getFiles().add(fileEntity);
        fileEntity.setCourse(course);
        fileRepo.save(fileEntity);
        courseRepo.save(course);

        return fileEntity;
    }

    public Course getCourseById(int id) {
        return courseRepo.findById(id).orElse(null);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    @Override
    public String enrollInCourse(int studentId, int courseId) {
        Student student = studentService.getStudent(studentId);
        if (student == null)
            return null;
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getStudents().contains(student)) {
            return "Student is already enrolled in this course.";
        }
        course.getStudents().add(student);
        courseRepo.save(course);

        notificationService.sendNotification(student, "You have successfully enrolled in the course: " + course.getTitle());

        Instructor instructor = course.getInstructor();
        notificationService.sendNotification(instructor, "Student " + student.getName() + " has enrolled in your course: " + course.getTitle());

        return "Student successfully enrolled in the course.";
    }

}