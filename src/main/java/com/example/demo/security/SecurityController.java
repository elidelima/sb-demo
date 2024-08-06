package com.example.demo.security;

import com.example.demo.exceptions.CustomBaseException;
import com.example.demo.exceptions.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SecurityController {

    //BASIC AUTH
    @GetMapping("/open")
    public String open() {
        return "No login required";
    }

    @GetMapping("/closed")
    public String closed() {
        return "Login required";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/special")
    public String special() {
        return "SPECIAL";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/basic")
    public String basic() {
        return "BASIC";
    }

    //JWT

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest request) {

        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            );

            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = JWTUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(new JWTResponse(jwtToken));
        } catch (AuthenticationException e) {
            System.out.println("Authentication Exception " + e.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/create-new-user")
    public ResponseEntity createNewUser(@RequestBody LoginRequest request) {
        Optional<CustomUser> customUserOptional = customUserRepository.findById(request.getUsername());
        if(customUserOptional.isPresent()) {
            throw new CustomBaseException(HttpStatus.BAD_REQUEST, new SimpleResponse("Username already exists"));
        }

        CustomUser user = new CustomUser();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        customUserRepository.save(user);
        return ResponseEntity.ok("user created successfully");
    }


    // PS all this logic should not be in the controller layer

}
