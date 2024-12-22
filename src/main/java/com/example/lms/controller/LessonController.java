package com.example.lms.controller;

import com.example.lms.dto.AttendanceData;
import com.example.lms.model.course_related.Lesson;
import com.example.lms.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/lesson")
@RestController
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/{courseId}/add")
    public Lesson addLessonToCourse(@RequestBody Lesson lesson, @PathVariable int courseId) {
        return lessonService.addLesson(lesson, courseId);
    }

    @GetMapping("/{courseId}/show_lessons")
    public List<Lesson> getLessonsForCourse(@PathVariable int courseId) {
        return lessonService.getLessonsForCourse(courseId);
    }

    @PostMapping("/{lessonId}/attend")
    public String attendLesson(
            @PathVariable int lessonId,
            @RequestBody AttendanceData attendanceData) {
        return lessonService.attendLesson(
                lessonId,
                attendanceData.getStudentId(),
                attendanceData.getOtp()
        );
    }

}
