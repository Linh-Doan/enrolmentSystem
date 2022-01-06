package linhdoan.enrolmentSystem.assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("offering/{offeringId}/assessment")
public class AssessmentController {
    private AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping
    public ResponseEntity getAssessments(@PathVariable("offeringId") Integer offeringId) {
        List<Assessment> assessments = null;
        try {
            assessments = assessmentService.getAssessments(offeringId);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(assessments);
    }

    @PostMapping
    public ResponseEntity addAssessment(@RequestBody Assessment assessment, @PathVariable("offeringId") Integer offeringId) {
        try {
            assessmentService.addAssessment(assessment, offeringId);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(assessment);
    }
}
