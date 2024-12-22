package com.example.lms.service;

import com.example.lms.model.course_related.Course;
import com.example.lms.model.course_related.Lesson;
import com.example.lms.model.user_related.Student;
import com.example.lms.repository.CourseRepo;
import com.example.lms.repository.LessonRepo;
import com.example.lms.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private static final Logger log = LoggerFactory.getLogger(LessonServiceImpl.class);

    private final LessonRepo lessonRepo;
    private final CourseRepo courseRepo;
    private final StudentRepo studentRepo;
    private final NotificationService notificationService;

    public Lesson addLesson(Lesson lesson, int courseId) {
        Course course = courseRepo.findById(courseId).orElse(null);
        if (course == null) {
            throw new IllegalArgumentException("Course Not Found");
        }

        lesson.setCourse(course);
        lesson.setOtp(generateOtp());

        lessonRepo.save(lesson);

        course.getLessons().add(lesson);
        courseRepo.save(course);

        for (Student student : course.getStudents()) {
            notificationService.sendNotification(student, "A new lesson has been uploaded to Course: " + course.getTitle());
        }

        return lesson;
    }

    public List<Lesson> getLessonsForCourse(int courseId) {
        return lessonRepo.findByCourseId(courseId);
    }

    private int generateOtp() {
        return (int) (Math.random() * 900000) + 100000;
    }

    @Override
    public String attendLesson(int lessonId, int studentId, int otp) {
        Lesson lesson = lessonRepo.findById(lessonId).orElse(null);
        if (lesson == null) {
            return "Lesson not found.";
        }

        if (lesson.getOtp() != otp) {
            return "Incorrect OTP. Please try again.";
        }

        Student student = studentRepo.findById(studentId).orElse(null);
        if (student == null) {
            return "Student not found.";
        }

        Course course = lesson.getCourse();
        if (course == null) {
            return "The lesson is not associated with a course.";
        }

        // Check if the student is enrolled in the course
        if (!course.getStudents().contains(student)) {
            return "Student is not enrolled in the course for this lesson. Please enroll first.";
        }

        if (lesson.getAttendance().contains(student)) {
            return "Student has already attended this lesson.";
        }

        lesson.getAttendance().add(student);
        lessonRepo.save(lesson);

        return "Attendance recorded successfully for lesson ID: " + lessonId;
    }

}
