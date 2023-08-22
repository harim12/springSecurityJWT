package com.RimHASSANI.demo.springsecurityjwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("colis")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ColisList extends DemandeSpecific{


    @Column(name = "colis_list_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer colisListId;
    private String colisName;

    @OneToMany(mappedBy = "colisList", cascade = CascadeType.ALL)
    private List<Colis> colisItems = new ArrayList<>();
}
