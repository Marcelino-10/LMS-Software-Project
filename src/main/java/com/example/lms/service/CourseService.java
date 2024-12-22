package com.example.lms.service;

import com.example.lms.controller.CourseData;
import com.example.lms.model.course_related.Course;
import com.example.lms.model.course_related.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    Course addCourse(CourseData course);

    Course getCourse(int id);

    void saveCourse(Course course);
    
    void deleteCourse(Integer id);

    void updateCourse(CourseData course);

    FileEntity uploadFile(int courseId, MultipartFile file) throws IOException;

    Course getCourseById( int id);

    List<Course> getAllCourses();

    String enrollInCourse(int studentId, int courseId);

}