package linhdoan.enrolmentSystem.offering;

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
@WebMvcTest(OfferingController.class)
public class OfferingControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OfferingService offeringService;

    private Offering offering;
    private String badRequestMessage;

    @Before
    public void setUp() {
        Unit unit = new Unit("FIT1001", "Intro");
        offering = new Offering(unit, 2022, '1');
        offering.setOfferingId(1);
        badRequestMessage = "message";
    }

    @Test
    public void givenOfferings_whenGetAllOfferings_thenReturnJsonArray() throws Exception {
        List<Offering> allOfferings = List.of(offering);
        given(offeringService.getAllOfferings()).willReturn(allOfferings);
        mvc.perform(get("/offering")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].offeringId").value(offering.getOfferingId()));
    }

    @Test
    public void givenValidUnitCode_whenGetOfferings_thenReturnJsonArray() throws Exception {
        String validUnitCode = offering.getUnit().getUnitCode();
        given(offeringService.getOfferings(validUnitCode)).willReturn(List.of(offering));
        mvc.perform(get("/offering/{unitCode}", validUnitCode)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].offeringId").value(offering.getOfferingId()));
    }

    @Test
    public void givenInvalidUnitCode_whenGetOfferings_thenBadRequest() throws Exception {
        String randomInvalidId = "blah";
        given(offeringService.getOfferings(randomInvalidId)).willThrow(new IllegalStateException(badRequestMessage));
        mvc.perform(get("/offering/{unitCode}", randomInvalidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

    @Test
    public void whenPostValidNewOffering_thenReturnNewOffering() throws Exception {
        mvc.perform(post("/offering/{unitCode}", "FIT1001")
                .content(Util.asJsonString(offering))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.unit").exists());
    }

    @Test
    public void whenPostInvalidNewUnit_thenReturnBadRequest() throws Exception {
        doThrow(new IllegalStateException(badRequestMessage)).when(offeringService).addOffering(isA(Offering.class), isA(String.class));
        mvc.perform(post("/offering/{unitCode}", "badCode")
                .content(Util.asJsonString(offering))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

    @Test
    public void givenValidId_whenGetOfferingById_thenReturnOffering() throws Exception {
        given(offeringService.getOfferingById(offering.getOfferingId())).willReturn(offering);
        mvc.perform(get("/offering/id/{offeringId}", offering.getOfferingId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offeringId").value(offering.getOfferingId()));
    }

    @Test
    public void givenInvalidId_whenGetOfferingById_thenReturnBadRequest() throws Exception {
        Integer randomInvalidId = 100;
        given(offeringService.getOfferingById(randomInvalidId)).willThrow(new IllegalStateException(badRequestMessage));
        mvc.perform(get("/offering/id/{offeringId}", randomInvalidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }
}
