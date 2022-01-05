package linhdoan.enrolmentSystem.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    public Student getStudentById(@PathVariable("studentId") Integer studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping(path = "new")
    public void addNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}/offeringId/{offeringId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId, @PathVariable("offeringId") Integer offeringId) {
        studentService.deleteOfferingInStudent(studentId, offeringId);
    }

    @PutMapping(path = "{studentId}/addEnrolment")
    public void addEnrolment(@PathVariable("studentId") Integer studentId,
                             @RequestParam(required = true) Integer offeringId) {
        studentService.addEnrolment(studentId, offeringId);
    }
}
