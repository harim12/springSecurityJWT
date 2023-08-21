package com.RimHASSANI.demo.springsecurityjwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("meuble")
@Getter
@Setter


public class MeubleDemande extends DemandeSpecific {
    // Attributs spécifiques aux meubles...

    // Getters and setters
    @JsonProperty("meubleType")
    @Column(name = "meuble_type")
    private String meubleType;


}
