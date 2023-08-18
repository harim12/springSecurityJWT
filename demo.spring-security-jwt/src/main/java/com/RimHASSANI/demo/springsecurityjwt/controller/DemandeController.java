package com.RimHASSANI.demo.springsecurityjwt.controller;

import com.RimHASSANI.demo.springsecurityjwt.model.DemandeEntity;
import com.RimHASSANI.demo.springsecurityjwt.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/add")
    public DemandeEntity addDemande(@RequestBody DemandeEntity demandeEntity){
        return demandeService.addDemande(demandeEntity);
    }

    @GetMapping("/get")
    public List<DemandeEntity> getDemandes(){
        //I need to get demandes only in the same city
        return demandeService.getDemandes();
    }
}
