package linhdoan.enrolmentSystem.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.isA;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UnitController.class)
class UnitControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UnitService unitService;

    private Unit unit1;

    private Unit unit2;

    private String badRequestMessage;

    @BeforeEach
    void setUp() {
        unit1 = new Unit("FIT1001", "Intro");
        unit2 = new Unit("FIT2001", "Intermediate");
        badRequestMessage = "message";
    }

    @Test
    public void givenUnits_whenGetUnits_thenReturnJsonArray() throws Exception {
        List<Unit> allUnits = List.of(unit1, unit2);
        given(unitService.getAllUnits()).willReturn(allUnits);
        mvc.perform(get("/unit")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].unitCode").value(unit1.getUnitCode()))
                .andExpect(jsonPath("$[0].unitName").value(unit1.getUnitName()));
    }

    @Test
    public void givenValidId_whenGetUnitById_thenReturnUnit() throws Exception {
        given(unitService.getUnitByUnitCode(unit2.getUnitCode())).willReturn(unit2);
        mvc.perform(get("/unit/{id}", unit2.getUnitCode())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.unitCode").value(unit2.getUnitCode()));
    }

    @Test
    public void givenInvalidId_whenGetUnitById_returnBadRequest() throws Exception {
        String randomInvalidId = "blah";
        given(unitService.getUnitByUnitCode(randomInvalidId)).willThrow(new IllegalStateException(badRequestMessage));
        mvc.perform(get("/unit/{id}", randomInvalidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

    @Test
    public void whenPostValidNewUnit_thenReturnNewUnit() throws Exception {
        mvc.perform(post("/unit")
                .content(asJsonString(unit1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.unitCode").exists());
    }

    @Test
    public void whenPostInvalidNewUnit_thenReturnBadRequest() throws Exception {
        doThrow(new IllegalStateException(badRequestMessage)).when(unitService).addNewUnit(isA(Unit.class));
        mvc.perform(post("/unit")
                .content(asJsonString(unit1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
