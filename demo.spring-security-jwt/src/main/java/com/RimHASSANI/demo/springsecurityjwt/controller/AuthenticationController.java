package com.RimHASSANI.demo.springsecurityjwt.controller;

import com.RimHASSANI.demo.springsecurityjwt.model.*;
import com.RimHASSANI.demo.springsecurityjwt.service.AuthenticationService;
import com.RimHASSANI.demo.springsecurityjwt.service.AuthentificationTransporteurService;
import com.RimHASSANI.demo.springsecurityjwt.service.AuthentificationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthentificationUserService authentificationUserService;

    @Autowired
    private AuthentificationTransporteurService authentificationTransporteurService;
    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationUserDTO request) {
        try {
            ApplicationUser user = authentificationUserService.registerUser(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPassword()
            );
            // Return a successful response with the created user
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            // Handle the case when the email already exists
            String errorMessage = "Email already exists";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @PostMapping("/login/user")
    public LoginResponseUserDTO loginUser(@RequestBody RegistrationUserDTO body){
        return authentificationUserService.loginUser(
                body.getEmail(),
                body.getPassword());
    }

    @PostMapping("/register/transporteur")
    public ResponseEntity<?> registerTransporteur(@RequestBody RegistrationTransporteurDTO body) {
        try {
            Transporteur transporteur = authentificationTransporteurService.registerTransporteur(
                    body.getFirstName(),
                    body.getLastName(),
                    body.getPhoneNumber(),
                    body.getEmail(),
                    body.getPassword()
            );
            // Return a successful response with the created transporteur
            return ResponseEntity.ok(transporteur);
        } catch (RuntimeException e) {
            // Handle the case when the email already exists
            String errorMessage = "Email already exists";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }


    @PostMapping("/login/transporteur")
    public LoginResponseTransporteurDTO loginTransporteur(@RequestBody RegistrationUserDTO body){
        return authentificationTransporteurService.loginTransporteur(
                body.getEmail(),
                body.getPassword());
    }






}
