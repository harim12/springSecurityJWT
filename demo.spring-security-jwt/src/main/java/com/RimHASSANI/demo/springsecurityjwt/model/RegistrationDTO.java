package com.RimHASSANI.demo.springsecurityjwt.model;

import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegistrationDTO {
    private String username;
    private String password;


        public String toString(){
        return "Registration info : name: "+username+"password: "+password;
    }
}
