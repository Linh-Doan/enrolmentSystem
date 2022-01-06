package linhdoan.enrolmentSystem.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getStudentById(@PathVariable("studentId") Integer studentId) {
        Student student = null;
        try{
            student = studentService.getStudentById(studentId);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Student>(student, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addNewStudent(@RequestBody Student student) {
        try {
            studentService.addNewStudent(student);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{studentId}/offeringId/{offeringId}")
    public ResponseEntity<String> deleteEnrolment(@PathVariable("studentId") Integer studentId, @PathVariable("offeringId") Integer offeringId) {
        try {
            studentService.deleteEnrolment(studentId, offeringId);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Student with id " + studentId + " deleted");
    }

    @PutMapping(path = "{studentId}/offeringId/{offeringId}")
    public ResponseEntity<String> addEnrolment(@PathVariable("studentId") Integer studentId,
                             @PathVariable("offeringId") Integer offeringId) {
        try {
            studentService.addEnrolment(studentId, offeringId);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("Student with id " + studentId + " is now enrolled in offering " + offeringId);
    }
}
