package com.RimHASSANI.demo.springsecurityjwt.controller;

import com.RimHASSANI.demo.springsecurityjwt.model.*;
import com.RimHASSANI.demo.springsecurityjwt.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/demande")
@CrossOrigin(origins = "http://localhost:4200")
public class DemandeController {
    @Autowired
    private DemandeService demandeService;

    @GetMapping("/")
    public String getHell(){
        return "hello";
    }

    /*@PostMapping("/add")
    public DemandeEntity addDemande(@RequestBody DemandeEntity demandeEntity){
        return demandeService.addDemande(demandeEntity);
    }*/

    @PostMapping("/add")
    public DemandeEntity addDemande(@RequestBody DemandeEntity demandeEntity) {
        DemandeSpecific specificDemande = demandeEntity.getSpecificDemande();
        if (specificDemande != null) {
            if (specificDemande instanceof MeubleDemande) {
                MeubleDemande meubleDemande = (MeubleDemande) specificDemande;
                meubleDemande.setDemandeEntity(demandeEntity);
            } else if (specificDemande instanceof VoitureDemande) {
                VoitureDemande voitureDemande = (VoitureDemande) specificDemande;
                voitureDemande.setDemandeEntity(demandeEntity);
            } else if(specificDemande instanceof MotoDemande){
                MotoDemande motoDemande = (MotoDemande) specificDemande;
                motoDemande.setDemandeEntity(demandeEntity);
            }
        }
        return demandeService.addDemande(demandeEntity);
    }

    @GetMapping("/get")
    public List<DemandeEntity> getDemandes(){
        //I need to get demandes only in the same city
        return demandeService.getDemandes();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DemandeEntity> getDemandeById(@PathVariable Integer id) {
        DemandeEntity demande = demandeService.getDemandeById(id);

        if (demande != null) {
            return ResponseEntity.ok(demande);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
