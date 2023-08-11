package com.RimHASSANI.demo.springsecurityjwt.service;

import com.RimHASSANI.demo.springsecurityjwt.model.ApplicationUser;
import com.RimHASSANI.demo.springsecurityjwt.model.LoginResponseUserDTO;
import com.RimHASSANI.demo.springsecurityjwt.model.Role;
import com.RimHASSANI.demo.springsecurityjwt.repository.RoleRepository;
import com.RimHASSANI.demo.springsecurityjwt.repository.TransporteurRepository;
import com.RimHASSANI.demo.springsecurityjwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
@Transactional
public class AuthentificationUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransporteurRepository transporteurRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @Qualifier("userAuthenticationManager")
    private final AuthenticationManager userAuthenticationManager;
    @Autowired
    public AuthentificationUserService(
            @Qualifier("userAuthenticationManager") AuthenticationManager userAuthenticationManager) {
        this.userAuthenticationManager = userAuthenticationManager;

    }

    public ApplicationUser registerUser(String firstName, String lastName, String email, String password){

        try {
            String encodedPassword = passwordEncoder.encode(password);
            Role userRole = roleRepository.findByAuthority("USER").get();

            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);

            return userRepository.save(new ApplicationUser(0, firstName, lastName, email, encodedPassword, authorities));
        } catch (DataIntegrityViolationException e) {
            // Handle the case when the email already exists
            throw new RuntimeException("Email already exists");
        }
    }

    public LoginResponseUserDTO loginUser(String email, String password){

        try{
            Authentication auth = this.userAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseUserDTO(userRepository.findByEmail(email).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseUserDTO(null, "");
        }
    }

}
