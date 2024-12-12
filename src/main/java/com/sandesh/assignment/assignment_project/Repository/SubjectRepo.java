package com.sandesh.assignment.assignment_project.Repository;

import com.sandesh.assignment.assignment_project.Model.Student;
import com.sandesh.assignment.assignment_project.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long> {
}
