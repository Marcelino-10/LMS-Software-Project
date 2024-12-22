package com.example.lms.model.user_related;

import com.example.lms.model.course_related.Lesson;
import com.example.lms.model.course_related.assignment_related.AssignmentSubmission;
import com.example.lms.model.course_related.quiz_related.QuizSubmission;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Student extends User{
    @JsonBackReference("assignment-submissions")
    @OneToMany(mappedBy = "student")
    private List<AssignmentSubmission> submissions = new ArrayList<>();

    @JsonBackReference("quiz-submissions")
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizSubmission> quizSubmissions = new ArrayList<>();

    @ManyToMany(mappedBy = "attendance")
    @JsonIgnore
    private List<Lesson> attendedLessons = new ArrayList<>();

    @JsonGetter("attended_lessons")
    public List<Integer> getAttendedLessonIds() {
        List<Integer> lessonIds = new ArrayList<>();
        for (Lesson lesson : attendedLessons) {
            lessonIds.add(lesson.getId());
        }
        return lessonIds;
    }

}
