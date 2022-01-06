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
        List<Assessment> assessments = offering.getAssessments();
        float totalPercentage = 0;
        for (Assessment a : assessments) {
            if (a.getAssessmentDate().equals(assessment.getAssessmentDate())) {
                throw new IllegalStateException("Assessments of the same offering must be on different dates");
            }
            totalPercentage += a.getAssessmentWeight();
        }
        if (totalPercentage + assessment.getAssessmentWeight() > 1) {
            throw new IllegalStateException(String.format("Total weight of assessments must be less than 1\n%.2f%% left", (1-totalPercentage)));
        }
        assessment.setAssessmentWeight((float) (Math.round(assessment.getAssessmentWeight()*100)*1.0/100));
        offering.addAssessment(assessment);
        assessmentRepository.save(assessment);
        return assessment.getAssessmentId();
    }

    public List<Assessment> getAssessments(Integer offeringId) {
        Offering offering = offeringRepository.findById(offeringId).orElseThrow(() ->new IllegalStateException("Invalid offering id" + offeringId));
        return assessmentRepository.findAssessmentByOfferingOfferingId(offeringId);
    }
}
