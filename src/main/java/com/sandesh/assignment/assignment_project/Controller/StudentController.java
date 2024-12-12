package com.sandesh.assignment.assignment_project.Controller;
import com.sandesh.assignment.assignment_project.Model.Student;
import com.sandesh.assignment.assignment_project.Model.Subject;
import com.sandesh.assignment.assignment_project.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
            return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

}
