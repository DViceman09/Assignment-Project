package com.sandesh.assignment.assignment_project.Repository;

import com.sandesh.assignment.assignment_project.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
}
