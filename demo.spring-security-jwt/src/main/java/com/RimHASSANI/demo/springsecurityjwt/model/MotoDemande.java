package com.RimHASSANI.demo.springsecurityjwt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("moto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MotoDemande extends DemandeSpecific{
    @JsonProperty("motoType")
    @Column(name = "moto_type")
    private String motoType;
}
