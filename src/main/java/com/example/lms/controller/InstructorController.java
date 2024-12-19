package com.example.lms.controller;

import com.example.lms.model.user_related.Instructor;
import com.example.lms.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping("/add/instructor")
    public Instructor addInstructor(@RequestBody Instructor instructor){
        return instructorService.addInstructor(instructor);
    }

    @PostMapping("/delete/instructor")
    public void deleteInstructor(@RequestBody Integer id) {
        instructorService.deleteInstructor(id);
    }


    @GetMapping("/show/instructors")
    public List<Instructor> getInstructors(){
        return instructorService.getAll();
    }
}
