package com.sandesh.assignment.assignment_project.Service;

import com.sandesh.assignment.assignment_project.Model.Subject;
import com.sandesh.assignment.assignment_project.Repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepo subjectRepo;

    public Subject createSubject(Subject subject) {
        return subjectRepo.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepo.findAll();
    }
}
