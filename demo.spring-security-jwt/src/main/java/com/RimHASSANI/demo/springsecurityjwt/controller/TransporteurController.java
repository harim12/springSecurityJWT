package com.RimHASSANI.demo.springsecurityjwt.controller;

import com.RimHASSANI.demo.springsecurityjwt.model.DemandeEntity;
import com.RimHASSANI.demo.springsecurityjwt.model.Transporteur;
import com.RimHASSANI.demo.springsecurityjwt.model.TransporteurInfo;
import com.RimHASSANI.demo.springsecurityjwt.model.TransporteurPersonalInfo;
import com.RimHASSANI.demo.springsecurityjwt.service.TransporteurService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("transporteur")
@CrossOrigin(origins = "http://localhost:4200")
public class TransporteurController {
    @Autowired
    private TransporteurService transporteurService;

    @GetMapping("/get/{email}/info")
    public ResponseEntity<TransporteurInfo> getTransporteurInfo(@PathVariable String email) {
        TransporteurInfo transporteurInfo = transporteurService.getTransporteurInfoByEmail(email);
        if (transporteurInfo != null) {
            return ResponseEntity.ok(transporteurInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/{email}/personal-info")
    public ResponseEntity<TransporteurPersonalInfo> getTransporteurPersonalInfo(@PathVariable String email) {
        TransporteurPersonalInfo transporteurPersonalInfo = transporteurService.getTransporteurPersonalInfoByEmail(email);
        if (transporteurPersonalInfo != null) {
            return ResponseEntity.ok(transporteurPersonalInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/update")
    public TransporteurPersonalInfo addTeste(@RequestParam("testeEntity") String testDataJson,
                                  @RequestParam(value = "image", required = false) MultipartFile image){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            TransporteurPersonalInfo transporteurPersonalInfo = objectMapper.readValue(testDataJson, TransporteurPersonalInfo.class);
            String imagePath = "C:\\Users\\hassa\\OneDrive\\Documents\\GitHub\\TransfertFacile\\front\\src\\assets\\assetsBack" + image.getOriginalFilename();
            Files.copy(image.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);

            transporteurPersonalInfo.setImageUrl(imagePath);

            return transporteurService.updateTransporteurInfo(transporteurPersonalInfo,imagePath);

        } catch (Exception e) {
            return null;
        }
    }

}
