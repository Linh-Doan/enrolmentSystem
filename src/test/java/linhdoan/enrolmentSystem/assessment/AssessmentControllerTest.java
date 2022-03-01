package linhdoan.enrolmentSystem.assessment;

import linhdoan.enrolmentSystem.offering.Offering;
import linhdoan.enrolmentSystem.unit.Unit;
import linhdoan.enrolmentSystem.util.Util;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AssessmentController.class)
public class AssessmentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AssessmentService assessmentService;

    private Assessment assessment;
    private String badRequestMessage;

    @Before
    public void setUp() {
        assessment = new Assessment();
        assessment.setAssessmentId(1);
        badRequestMessage = "message";
    }
    @Test

    public void givenValidOfferingId_whenGetAssessments_thenReturnJsonArray() throws Exception {
        Integer validOfferingId = 12;
        given(assessmentService.getAssessments(validOfferingId)).willReturn(List.of(assessment));
        mvc.perform(get("/offering/{offeringId}/assessment", validOfferingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].assessmentId").value(assessment.getAssessmentId()));
    }

    @Test
    public void givenInvalidOfferingId_whenGetAssessments_thenReturnBadRequest() throws Exception {
        Integer invalidOfferingId = 12;
        given(assessmentService.getAssessments(invalidOfferingId)).willThrow(new IllegalStateException(badRequestMessage));
        mvc.perform(get("/offering/{offeringId}/assessment", invalidOfferingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

    @Test
    public void whenPostValidNewAssessment_thenReturnNewAssessment() throws Exception {
        mvc.perform(post("/offering/{offeringId}/assessment", assessment.getAssessmentId())
                .content(Util.asJsonString(assessment))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.assessmentId").value(assessment.getAssessmentId()));
    }

    @Test
    public void whenPostInvalidNewAssessment_thenReturnBadRequest() throws Exception {
        doThrow(new IllegalStateException(badRequestMessage)).when(assessmentService).addAssessment(isA(Assessment.class), isA(Integer.class));
        mvc.perform(post("/offering/{offeringId}/assessment", 100)
                .content(Util.asJsonString(assessment))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }
}
