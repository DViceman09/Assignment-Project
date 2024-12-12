package com.sandesh.assignment.assignment_project.Service;

import com.sandesh.assignment.assignment_project.Configurations.JwtProvider;
import com.sandesh.assignment.assignment_project.Model.Student;
import com.sandesh.assignment.assignment_project.Model.Subject;
import com.sandesh.assignment.assignment_project.Repository.StudentRepo;
import com.sandesh.assignment.assignment_project.Repository.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentRepo userRepo;

    @Autowired
    private JwtProvider jwtProvider;

    public Student createStudent(Student student) {
        return studentRepo.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Student findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        Student user = userRepo.findByEmail(email);
        return user;
    }

    public Student findUserByEmail(String email) throws Exception {
        Student user = userRepo.findByEmail(email);
        if(user == null) {
            throw new Exception("User not found");
        }
        return user;
    }

}
