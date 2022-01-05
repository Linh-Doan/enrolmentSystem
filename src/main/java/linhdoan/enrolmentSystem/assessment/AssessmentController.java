package linhdoan.enrolmentSystem.assessment;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Assessment> getAssessments(@PathVariable("offeringId") Integer offeringId) {
        return assessmentService.getAssessments(offeringId);
    }

    @PostMapping(path = "new")
    public Integer addAssessment(@RequestBody Assessment assessment, @PathVariable("offeringId") Integer offeringId) {
        return assessmentService.addAssessment(assessment, offeringId);
    }
}
