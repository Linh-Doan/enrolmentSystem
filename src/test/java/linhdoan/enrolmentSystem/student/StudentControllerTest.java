package linhdoan.enrolmentSystem.student;

import linhdoan.enrolmentSystem.assessment.Assessment;
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

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    private Student ben;
    private Student george;
    private String badRequestMessage;

    @Before
    public void setUp() {
        ben = new Student("Ben", "Smith", "ben.smith@gmail.com", LocalDate.of(1998, Month.JANUARY, 3));
        ben.setStudentId(1);
        george = new Student("George", "Thompson", "george.thompson@gmail.com", LocalDate.of(1998, Month.JULY, 1));
        badRequestMessage = "message";
    }

    @Test
    public void givenStudents_whenGetStudent_thenReturnJsonArray() throws Exception {

        List<Student> allStudents = List.of(ben);
        given(studentService.getStudents()).willReturn(allStudents);
        mvc.perform(get("/student")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].studentId").exists())
                .andExpect(jsonPath("$[0].firstName").value(ben.getFirstName()));
    }

    @Test
    public void givenValidId_whenGetStudentById_thenReturnStudent() throws Exception {
        given(studentService.getStudentById(ben.getStudentId())).willReturn(ben);
        mvc.perform(get("/student/{id}", ben.getStudentId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(ben.getStudentId()));
    }

    @Test
    public void givenInvalidId_whenGetStudentById_returnBadRequest() throws Exception {
        Integer randomInvalidId = 100;
        given(studentService.getStudentById(randomInvalidId)).willThrow(new IllegalStateException(badRequestMessage));
        mvc.perform(get("/student/{id}", randomInvalidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

    @Test
    public void whenPostValidNewStudent_thenReturnNewStudent() throws Exception {
        mvc.perform(post("/student")
                .content(Util.asJsonString(ben))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentId").value(ben.getStudentId()));
    }

    @Test
    public void whenPostInvalidNewStudent_thenReturnBadRequest() throws Exception {
        doThrow(new IllegalStateException(badRequestMessage)).when(studentService).addNewStudent(isA(Student.class));
        mvc.perform(post("/student")
                .content(Util.asJsonString(ben))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

    @Test
    public void givenValidStudentAndOfferingId_whenDeleteEnrolment_returnOk() throws Exception {
        mvc.perform(delete("/student/{studentId}/offeringId/{offeringId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidStudentOrOfferingId_whenDeleteEnrolment_returnBadRequest() throws Exception {
        doThrow(new IllegalStateException(badRequestMessage)).when(studentService).deleteEnrolment(isA(Integer.class), isA(Integer.class));
        mvc.perform(delete("/student/{studentId}/offeringId/{offeringId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

    @Test
    public void givenValidStudentAndOfferingId_whenAddEnrolment_returnOk() throws Exception {
        mvc.perform(put("/student/{studentId}/offeringId/{offeringId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidStudentOrOfferingId_whenAddEnrolment_returnBadRequest() throws Exception {
        doThrow(new IllegalStateException(badRequestMessage)).when(studentService).addEnrolment(isA(Integer.class), isA(Integer.class));
        mvc.perform(put("/student/{studentId}/offeringId/{offeringId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

    @Test
    public void givenValidId_whenGetStudentSchedule_thenReturnJSONArray() throws Exception {
        Assessment assessment = new Assessment();
        List<Assessment> assessments = List.of(assessment);
        given(studentService.getSchedule(ben.getStudentId())).willReturn(assessments);
        mvc.perform(get("/student/{studentId}/schedule", ben.getStudentId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void givenInvalidId_whenGetStudentSchedule_thenReturnBadRequest() throws Exception {
        Integer randomInvalidId = 100;
        given(studentService.getSchedule(randomInvalidId)).willThrow(new IllegalStateException(badRequestMessage));
        mvc.perform(get("/student/{studentId}/schedule", randomInvalidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(badRequestMessage));
    }

}
