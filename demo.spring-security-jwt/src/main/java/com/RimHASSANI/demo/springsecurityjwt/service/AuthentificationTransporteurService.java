package com.RimHASSANI.demo.springsecurityjwt.service;

import com.RimHASSANI.demo.springsecurityjwt.model.LoginResponseTransporteurDTO;
import com.RimHASSANI.demo.springsecurityjwt.model.Role;
import com.RimHASSANI.demo.springsecurityjwt.model.Transporteur;
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
public class AuthentificationTransporteurService {

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

    @Qualifier("transporteurAuthenticationManager")
    private final AuthenticationManager transporteurAuthenticationManager;

    @Autowired
    public AuthentificationTransporteurService(

            @Qualifier("transporteurAuthenticationManager") AuthenticationManager transporteurAuthenticationManager) {

        this.transporteurAuthenticationManager = transporteurAuthenticationManager;
    }

    public Transporteur registerTransporteur(String firstName, String lastName, Integer phoneNumber, String email, String password) {
        try {
            String encodedPassword = passwordEncoder.encode(password);
            Role userRole = roleRepository.findByAuthority("TRANSPORTEUR").get();

            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);

            return transporteurRepository.save(new Transporteur(0, firstName, lastName, phoneNumber, email, encodedPassword, authorities));
        } catch (DataIntegrityViolationException e) {
            // Handle the case when the email already exists
            throw new RuntimeException("Email already exists");
        }
    }

    public LoginResponseTransporteurDTO loginTransporteur(String email, String password){

        try{
            Authentication auth = this.transporteurAuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseTransporteurDTO(transporteurRepository.findByEmail(email).get(), token);

        } catch(AuthenticationException e){
            return new LoginResponseTransporteurDTO(null, "");
        }
    }
}
