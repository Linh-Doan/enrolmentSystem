package linhdoan.enrolmentSystem.assessment;

import linhdoan.enrolmentSystem.offering.Offering;
import linhdoan.enrolmentSystem.offering.OfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {
    private AssessmentRepository assessmentRepository;
    private OfferingRepository offeringRepository;

    @Autowired
    public AssessmentService(AssessmentRepository assessmentRepository, OfferingRepository offeringRepository) {
        this.assessmentRepository = assessmentRepository;
        this.offeringRepository = offeringRepository;
    }

    public Integer addAssessment(Assessment assessment, Integer offeringId) {
        Offering offering = offeringRepository.findById(offeringId).orElseThrow(() ->new IllegalStateException("Invalid offering id" + offeringId));
        offering.addAssessment(assessment);
        assessmentRepository.save(assessment);
        return assessment.getAssessmentId();
    }

    public List<Assessment> getAssessments(Integer offeringId) {
        Offering offering = offeringRepository.findById(offeringId).orElseThrow(() ->new IllegalStateException("Invalid offering id" + offeringId));
        return assessmentRepository.findAssessmentByOfferingOfferingId(offeringId);
    }
}
