package com.example.lms.model.course_related;

import com.example.lms.controller.CourseData;
import com.example.lms.model.course_related.assignment_related.Assignment;
import com.example.lms.model.user_related.Instructor;
import com.example.lms.model.user_related.Student;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Table (name = "Course")
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    public Course(CourseData courseData, Instructor instructor) {
        this.title = courseData.getTitle();
        this.description = courseData.getDescription();
        this.duration = courseData.getDuration();
        this.instructor = instructor;
    }

//    @OneToMany(mappedBy = "Course", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Student> enrolledStudent = new ArrayList<>();
//
//    @OneToMany(mappedBy = "Course", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Quiz> quizzes = new ArrayList<>();
//
    @OneToMany(mappedBy = "course")
    @JsonManagedReference
    private List<Assignment> assignments = new ArrayList<>();


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FileEntity> files;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    @JsonGetter("Enrollments")
    public List<Integer> Registered_student() {
        List<Integer> lessonIds = new ArrayList<>();
        for (Student student : students) {
            lessonIds.add(student.getId());
        }
        return lessonIds;
    }


//
//    @OneToMany(mappedBy = "Course", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Map<Integer, Lesson> lessons = new HashMap<>();
//
//    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
//    private QuestionsBank questionsBank;
}

