package com.sandesh.assignment.assignment_project.Controller;

import com.sandesh.assignment.assignment_project.Configurations.JwtProvider;
import com.sandesh.assignment.assignment_project.Model.Student;
import com.sandesh.assignment.assignment_project.Model.USER_ROLE;
import com.sandesh.assignment.assignment_project.Repository.StudentRepo;
import com.sandesh.assignment.assignment_project.Request.LoginRequest;
import com.sandesh.assignment.assignment_project.Response.AuthResponse;
import com.sandesh.assignment.assignment_project.Service.StudentDetailsService;
import io.jsonwebtoken.security.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private StudentDetailsService studentDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody Student student) throws Exception {
        Student isEmailExist =  studentRepo.findByEmail(student.getEmail());
        if(isEmailExist != null)
        {
            throw new Exception("Email already in use. Please use a new email id");
        }

        Student User = new Student();
        User.setEmail(student.getEmail());
        User.setName(student.getName());
        User.setRole(student.getRole());
        User.setPassword(passwordEncoder.encode(student.getPassword()));

        Student savedUser = studentRepo.save(User);

        Authentication authentication = new UsernamePasswordAuthenticationToken(student.getEmail(),student.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();

        authResponse.setJwt(jwt);
        authResponse.setMessage("Registration is successful");
        authResponse.setRole(savedUser.getRole());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> Signin(@RequestBody LoginRequest loginRequest)
    {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(email, password);

        Collection<?extends GrantedAuthority> authority = authentication.getAuthorities();

        String role = authority.isEmpty()?null:authority.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successful");
        authResponse.setRole(USER_ROLE.valueOf(role));

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password)
    {
        UserDetails userDetails = studentDetailsService.loadUserByUsername(username);

        if(userDetails == null)
        {
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid password!!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
