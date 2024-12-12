package com.sandesh.assignment.assignment_project.Service;

import com.sandesh.assignment.assignment_project.Model.Student;
import com.sandesh.assignment.assignment_project.Model.USER_ROLE;
import com.sandesh.assignment.assignment_project.Repository.StudentRepo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentDetailsService implements UserDetailsService {

    private final StudentRepo studentRepo;

    public StudentDetailsService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student user = studentRepo.findByEmail(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("username not found exception " +username);
        }

        USER_ROLE role = user.getRole();
        if(role == null)
        {
            role = USER_ROLE.ROLE_STUDENT;
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.toString()));
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}