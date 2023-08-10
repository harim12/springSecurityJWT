package com.RimHASSANI.demo.springsecurityjwt.controller;

import com.RimHASSANI.demo.springsecurityjwt.model.*;
import com.RimHASSANI.demo.springsecurityjwt.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register/user")
    public ApplicationUser registerUser(@RequestBody RegistrationUserDTO body){
        return authenticationService.registerUser(
                body.getFirstName(),
                body.getLastName(),
                body.getEmail(),
                body.getPassword()
        );
    }

    @PostMapping("/login/user")
    public LoginResponseUserDTO loginUser(@RequestBody RegistrationUserDTO body){
        return authenticationService.loginUser(
                body.getEmail(),
                body.getPassword());
    }

    @PostMapping("/register/transporteur")
    public Transporteur registerTransporteur(@RequestBody RegistrationTransporteurDTO body){
        return authenticationService.registerTransporteur(
                body.getFirstName(),
                body.getLastName(),
                body.getPhoneNumber(),
                body.getEmail(),
                body.getPassword()
        );
    }




}
