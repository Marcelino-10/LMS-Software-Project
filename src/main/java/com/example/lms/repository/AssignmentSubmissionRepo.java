package com.example.lms.repository;

import com.example.lms.model.course_related.assignment_related.AssignmentSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentSubmissionRepo extends JpaRepository<AssignmentSubmission, Integer> {
}
