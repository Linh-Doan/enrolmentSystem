package linhdoan.enrolmentSystem.assessment;

import linhdoan.enrolmentSystem.offering.Offering;
import linhdoan.enrolmentSystem.offering.OfferingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class AssessmentServiceTest {
    @InjectMocks
    private AssessmentService assessmentService;
    @Mock
    private AssessmentRepository assessmentRepository;
    @Mock
    private OfferingRepository offeringRepository;

    private Offering offering;
    private Assessment assessment;
    @Before
    public void setUp() {
        offering = new Offering();
        offering.setOfferingId(1);
        assessment = new Assessment(offering, LocalDate.of(2022, Month.JANUARY, 1), 0.6f);
    }

    @Test
    public void givenValidOfferingId_whenGetAssessments_returnJsonArray() throws Exception {
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.of(offering));
        given(assessmentRepository.findAssessmentByOfferingOfferingId(offering.getOfferingId())).willReturn(List.of(assessment));
        List<Assessment> assessmentsFound = assessmentService.getAssessments(offering.getOfferingId());
        assertEquals(assessmentsFound.size(), 1);
        assertEquals(assessmentsFound.get(0), assessment);
    }

    @Test
    public void givenInvalidOfferingId_whenGetAssessments_throwException() throws Exception {
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            assessmentService.getAssessments(offering.getOfferingId());
        });
    }

    @Test
    public void givenInvalidOfferingId_whenAddAssessments_throwException() throws Exception {
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            assessmentService.addAssessment(assessment, offering.getOfferingId());
        });
    }

    @Test
    public void givenExistingOfferingId_whenAddAssessments_throwException() throws Exception {
        offering.addAssessment(assessment);
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.of(offering));
        assertThrows(IllegalStateException.class, () -> {
            assessmentService.addAssessment(assessment, offering.getOfferingId());
        });
    }

    @Test
    public void givenOfferingIdAndAssessment_whenAddAssessmentsAndSumGreaterThan1_throwException() throws Exception {
        Assessment assessment1 = new Assessment();
        assessment1.setAssessmentDate(LocalDate.of(2022, Month.FEBRUARY, 1));
        assessment1.setAssessmentWeight(0.5f);
        offering.addAssessment(assessment1);
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.of(offering));
        assertThrows(IllegalStateException.class, () -> {
            assessmentService.addAssessment(assessment, offering.getOfferingId());
        });
    }

    @Test
    public void givenValidOfferingIdAndAssessment_whenAddAssessments_throwException() throws Exception {
        given(offeringRepository.findById(offering.getOfferingId())).willReturn(Optional.of(offering));
        assessment.setAssessmentId(1);
        Integer addedAssessmentId = assessmentService.addAssessment(assessment, offering.getOfferingId());
        assertEquals(addedAssessmentId, assessment.getAssessmentId());
        verify(assessmentRepository, times(1)).save(any(Assessment.class));
    }
}
