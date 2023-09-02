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

    @Autowired
    WebSocketService webSocketService;


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

    public TransporteurPersonalInfo updateTransporteurInfo(TransporteurPersonalInfo transporteurPersonalInfo,String imagePath) {
        Transporteur existingTransporteur = transporteurRepository.findByEmail(transporteurPersonalInfo.getEmail()).get();
        if (existingTransporteur != null) {
            // Update the fields that you want to allow changing
            existingTransporteur.setFirstName(transporteurPersonalInfo.getFirstName());
            existingTransporteur.setLastName(transporteurPersonalInfo.getLastName());
            existingTransporteur.setCity(transporteurPersonalInfo.getCity());
            existingTransporteur.setImageUrl(imagePath);
            // You can similarly update other fields as needed

            // Save the updated transporteur information back to the database
            transporteurRepository.save(existingTransporteur);

            TransporteurPersonalInfo updatedPersonalInfo = new TransporteurPersonalInfo();
            updatedPersonalInfo.setFirstName(existingTransporteur.getFirstName());
            updatedPersonalInfo.setLastName(existingTransporteur.getLastName());
            updatedPersonalInfo.setCity(existingTransporteur.getCity());
            updatedPersonalInfo.setEmail(existingTransporteur.getEmail());
            updatedPersonalInfo.setImageUrl(existingTransporteur.getImageUrl());

            notifyFrontend();
            return updatedPersonalInfo;
        } else {
            // Handle the case where the transporteur with the provided email does not exist
            return null;
        }

    }

    protected String getEntityTopic(){
        return "update-transporteur";
    }
    public void notifyFrontend(){
        final String entityTopic = getEntityTopic();
        if(entityTopic==null){

            return;
        }
        webSocketService.sendMessage(entityTopic);

    }

}
