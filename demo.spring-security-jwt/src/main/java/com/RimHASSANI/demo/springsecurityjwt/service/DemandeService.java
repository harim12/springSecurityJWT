package com.RimHASSANI.demo.springsecurityjwt.service;

import com.RimHASSANI.demo.springsecurityjwt.model.*;
import com.RimHASSANI.demo.springsecurityjwt.repository.DemandeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired WebSocketService webSocketService;
    public DemandeEntity addDemande(DemandeEntity demandeEntity) {

        DemandeSpecific specificDemande = demandeEntity.getSpecificDemande();
       if (specificDemande != null) {
            // Create an instance of the specific demande (e.g., CarDemande or MeubleDemande)
            if (specificDemande instanceof VoitureDemande) {
                VoitureDemande carDemande = (VoitureDemande) specificDemande;

            } else if (specificDemande instanceof MeubleDemande) {
                MeubleDemande meubleDemande = (MeubleDemande) specificDemande;
                // Set attributes specific to MeubleDemande
            } else if(specificDemande instanceof MotoDemande){
                MotoDemande motoDemande = (MotoDemande) specificDemande;
            } else if(specificDemande instanceof ColisList){
                ColisList colisList = (ColisList) specificDemande;
            }

            specificDemande.setDemandeEntity(demandeEntity);
        }
        DemandeEntity demande  = demandeRepository.save(demandeEntity);
        notifyFrontend();

        return demande ;
    }

    public List<DemandeEntity> getDemandes() {
        return demandeRepository.findAll();
    }

    protected String getEntityTopic(){
        return "add-demande";
    }
    public void notifyFrontend(){
        final String entityTopic = getEntityTopic();
        if(entityTopic==null){

            return;
        }
        webSocketService.sendMessage(entityTopic);

    }

    public DemandeEntity getDemandeById(Integer id) {
        Optional<DemandeEntity> demandeOptional = demandeRepository.findById(id);
        return demandeOptional.orElse(null);
    }
}
