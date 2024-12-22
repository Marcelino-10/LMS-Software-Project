package com.example.lms.controller;

import com.example.lms.dto.QuizData;
import com.example.lms.dto.QuizSubmissionData;
import com.example.lms.model.course_related.quiz_related.QuizSubmission;
import com.example.lms.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/course/{id}/quiz/add")
    public QuizData addQuiz(@PathVariable("id") int id, @RequestBody QuizData quizData) {
        return quizService.addQuiz(id, quizData);
    }

    @PostMapping("/quiz/{id}/submit")
    public QuizSubmissionData submitQuiz(@PathVariable("id") int id, @RequestBody List<String> answers) {
        return quizService.submitQuiz(202, id, answers);
    }

    @GetMapping("/quiz/{id}/submissions")
    public List<QuizSubmission> getSubmissions(@PathVariable("id") int id) {
        return quizService.getSubmissions(id);
    }

}
