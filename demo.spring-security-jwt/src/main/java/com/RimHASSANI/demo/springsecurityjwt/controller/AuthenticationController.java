package com.RimHASSANI.demo.springsecurityjwt.controller;

import com.RimHASSANI.demo.springsecurityjwt.event.RegistrationCompleteEvent;
import com.RimHASSANI.demo.springsecurityjwt.model.*;
import com.RimHASSANI.demo.springsecurityjwt.service.AuthenticationService;
import com.RimHASSANI.demo.springsecurityjwt.service.AuthentificationTransporteurService;
import com.RimHASSANI.demo.springsecurityjwt.service.AuthentificationUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthentificationUserService authentificationUserService;

    @Autowired
    private AuthentificationTransporteurService authentificationTransporteurService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationUserDTO registrationDTO, final HttpServletRequest request) {
        try {
            ApplicationUser user = authentificationUserService.registerUser(
                    registrationDTO.getFirstName(),
                    registrationDTO.getLastName(),
                    registrationDTO.getEmail(),
                    registrationDTO.getPassword()
            );
            publisher.publishEvent(new RegistrationCompleteEvent(
                    user,
                    applicationUrl(request)
            ));

            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            // Handle the case when the email already exists
            String errorMessage = "Email already exists";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = authentificationUserService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")) {
            return "User Verified Successfully";
        }
        return "Bad User";
    }


    @GetMapping("/resendVerifyToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken,
                                          HttpServletRequest request) {
        VerificationToken verificationToken
                = authentificationUserService.generateNewVerificationToken(oldToken);
        ApplicationUser user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);
        return "Verification Link Sent";
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

    private void resendVerificationTokenMail(ApplicationUser user, String applicationUrl, VerificationToken verificationToken) {
        String url =
                applicationUrl
                        + "/verifyRegistration?token="
                        + verificationToken.getToken();

        //sendVerificationEmail()
        log.info("Click the link to verify your account: {}",
                url);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                "/auth"+
                request.getContextPath();
    }





}
