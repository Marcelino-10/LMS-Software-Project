package com.example.lms.service.impl;

import com.example.lms.model.user_related.Student;
import com.example.lms.dto.UserData;
import com.example.lms.repository.StudentRepo;
import com.example.lms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepo;

    @Override
    public Student getStudent(int id) {
        return studentRepo.findById(id).orElse(null);
    }

    @Override
    public UserData addStudent(Student student) {
        return new UserData(studentRepo.save(student));
    }
}