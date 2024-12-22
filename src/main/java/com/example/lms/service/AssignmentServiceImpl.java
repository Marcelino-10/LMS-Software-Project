package com.example.lms.service;

import com.example.lms.model.course_related.FileEntity;
import com.example.lms.model.course_related.assignment_related.Assignment;
import com.example.lms.model.course_related.Course;
import com.example.lms.model.course_related.assignment_related.AssignmentSubmission;
import com.example.lms.dto.SubmissionResponse;
import com.example.lms.model.user_related.Instructor;
import com.example.lms.model.user_related.Student;
import com.example.lms.repository.AssignmentRepo;
import com.example.lms.repository.AssignmentSubmissionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService{
    private final AssignmentRepo assignmentRepo;

    private final CourseService courseService;

    private final FileService fileService;

    private final StudentService studentService;

    private final AssignmentSubmissionRepo assignmentSubmissionRepo;

    private final NotificationService notificationService;

    @Override
    public Assignment addAssignment(int courseId, Assignment assignment) {
        Course course = courseService.getCourse(courseId);
        if (course == null)
            return null;

        assignment.setCourse(course);
        assignmentRepo.save(assignment);

        course.getAssignments().add(assignment);
        courseService.saveCourse(course);
        return assignment;
    }

    @Override
    public SubmissionResponse submitAssignment(int studentId, int assignmentId, MultipartFile file) throws IOException {

        Student student = studentService.getStudent(studentId);

        if (student == null)
            return null;

        Assignment assignment = assignmentRepo.findById(assignmentId).orElse(null);

        if (assignment == null)
            return null;

        FileEntity submittedFileEntity = fileService.saveFile(file);

        AssignmentSubmission assignmentSubmission = new AssignmentSubmission();

        assignmentSubmission.setStudent(student);
        assignmentSubmission.setAssignment(assignment);
        assignmentSubmission.setFileEntity(submittedFileEntity);

        assignmentSubmissionRepo.save(assignmentSubmission);

        return new SubmissionResponse(assignmentSubmission.getId(), studentId, 0, false, submittedFileEntity);
    }

    @Override
    public List<SubmissionResponse> getSubmissions(int id) {
        Assignment assignment = assignmentRepo.findById(id).orElse(null);

        if (assignment == null)
            return null;

        List<SubmissionResponse> ret = new ArrayList<>();

        for (AssignmentSubmission submission : assignment.getSubmissions()) {
            ret.add(new SubmissionResponse(submission));
        }

        return ret;
    }

    @Override
    public SubmissionResponse gradeSubmission(int submissionId, int grade) {
        AssignmentSubmission as = assignmentSubmissionRepo.findById(submissionId).orElse(null);
        if (as == null)
            return null;

        if (as.isMarked())
            return null;

        if (grade > as.getAssignment().getGrade())
            return null;

        as.setMarked(true);
        as.setGrade(grade);

        assignmentSubmissionRepo.save(as);

        Student student = as.getStudent();

        notificationService.sendNotification(student, "You have scored " +  grade + " in the Assignment: " + as.getAssignment().getTitle());


        return new SubmissionResponse(as);
    }
}
