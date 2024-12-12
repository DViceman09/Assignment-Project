package com.sandesh.assignment.assignment_project.Controller;

import com.sandesh.assignment.assignment_project.Model.Subject;
import com.sandesh.assignment.assignment_project.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/subject")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        return ResponseEntity.ok(subjectService.createSubject(subject));
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }
}
