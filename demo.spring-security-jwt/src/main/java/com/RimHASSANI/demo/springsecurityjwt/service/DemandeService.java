package com.RimHASSANI.demo.springsecurityjwt.service;

import com.RimHASSANI.demo.springsecurityjwt.model.DemandeEntity;
import com.RimHASSANI.demo.springsecurityjwt.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeService {

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired WebSocketService webSocketService;
    public DemandeEntity addDemande(DemandeEntity demandeEntity) {
        notifyFrontend();
        return demandeRepository.save(demandeEntity);
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
}
