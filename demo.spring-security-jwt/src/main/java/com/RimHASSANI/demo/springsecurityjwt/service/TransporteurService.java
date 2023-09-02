package com.RimHASSANI.demo.springsecurityjwt.service;

import com.RimHASSANI.demo.springsecurityjwt.model.Transporteur;
import com.RimHASSANI.demo.springsecurityjwt.model.TransporteurInfo;
import com.RimHASSANI.demo.springsecurityjwt.model.TransporteurPersonalInfo;
import com.RimHASSANI.demo.springsecurityjwt.repository.TransporteurRepository;
import com.RimHASSANI.demo.springsecurityjwt.repository.UserRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TransporteurService  implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransporteurRepository transporteurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        return transporteurRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("transporteur is not valid"));
    }

    public TransporteurInfo getTransporteurInfoByEmail(String email) {
        Tuple tuple = transporteurRepository.getTransporteurInfoByEmail(email);

        if (tuple != null) {
            String firstName = tuple.get("first_name", String.class);
            String lastName = tuple.get("last_name", String.class);
            String carType = tuple.get("car_type", String.class);

            return new TransporteurInfo(firstName, lastName, carType);
        }

        return null;
    }

    public TransporteurPersonalInfo getTransporteurPersonalInfoByEmail(String email) {
        Tuple tuple = transporteurRepository.getTransporteurPersonalInfoByEmail(email);

        if (tuple != null) {
            String firstName = tuple.get("first_name", String.class);
            String lastName = tuple.get("last_name", String.class);
            String  imageUrl  = tuple.get("image_url",String.class);
            String city = tuple.get("city",String.class);

            return new TransporteurPersonalInfo(firstName, lastName,imageUrl,city,email);
        }

        return null;
    }
}
