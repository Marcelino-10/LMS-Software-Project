package com.example.lms.controller;

import com.example.lms.model.course_related.assignment_related.Assignment;
import com.example.lms.dto.SubmissionResponse;
import com.example.lms.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping("/course/{id}/assignment/add")
    public Assignment addAssignment(@RequestBody Assignment assignment, @PathVariable int id) {
        return assignmentService.addAssignment(id, assignment);
    }

    @PostMapping("/assignment/{id}/submit")
    public SubmissionResponse submitAssignment(@PathVariable("id") int assignmentId, @RequestParam("file")MultipartFile file) throws IOException {
        return assignmentService.submitAssignment(202, assignmentId, file);
    }

    @GetMapping("/assignment/{id}/submissions")
    public List<SubmissionResponse> getSubmissions(@PathVariable("id") int id) {
        return assignmentService.getSubmissions(id);
    }

    @PatchMapping("/submission/{id}/grade")
    public SubmissionResponse gradeSubmission(@PathVariable("id") int submissionId, @RequestBody int grade) {
        return assignmentService.gradeSubmission(submissionId, grade);
    }

}
