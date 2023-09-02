package com.RimHASSANI.demo.springsecurityjwt.repository;

import com.RimHASSANI.demo.springsecurityjwt.model.Transporteur;
import com.RimHASSANI.demo.springsecurityjwt.model.TransporteurInfo;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransporteurRepository extends JpaRepository<Transporteur,Integer> {
    Optional<Transporteur> findByEmail(String email);

    @Query(value = "SELECT t.first_name, t.last_name, t.car_type FROM transporteurs t WHERE t.email = :email", nativeQuery = true)
    Tuple getTransporteurInfoByEmail(String email);

    @Query(value = "SELECT t.first_name, t.last_name,t.city,t.image_url FROM transporteurs t WHERE t.email = :email", nativeQuery = true)
    Tuple getTransporteurPersonalInfoByEmail(String email);

    @Query(value = "SELECT t.card_expiry, t.ccv_card, t.payment_card_num , t.name_on_card , t.street_address,t.city  FROM transporteurs t WHERE t.email = :email", nativeQuery = true)
    Tuple getTransporteurPaimentInfoByEmail(String email);
}
