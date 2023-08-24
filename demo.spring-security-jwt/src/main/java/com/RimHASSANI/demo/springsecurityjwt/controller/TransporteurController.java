package com.RimHASSANI.demo.springsecurityjwt.controller;

import com.RimHASSANI.demo.springsecurityjwt.model.Transporteur;
import com.RimHASSANI.demo.springsecurityjwt.model.TransporteurInfo;
import com.RimHASSANI.demo.springsecurityjwt.service.TransporteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}