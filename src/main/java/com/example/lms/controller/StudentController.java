package com.example.lms.controller;

import com.example.lms.model.user_related.Admin;
import com.example.lms.model.user_related.Student;
import com.example.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
    }

    @PatchMapping("/update/{id}")
    public void updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        studentService.updateStudent(id, student);
    }
    @GetMapping("/get/{id}")
    public Student getStudent(@PathVariable Integer id) {
        return studentService.getStudent(id);
    }


    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

}