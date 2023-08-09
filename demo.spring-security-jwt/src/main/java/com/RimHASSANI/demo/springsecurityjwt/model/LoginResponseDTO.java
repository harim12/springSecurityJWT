package com.RimHASSANI.demo.springsecurityjwt.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class LoginResponseDTO {
    private ApplicationUser user;
    private String jwt;

    public LoginResponseDTO(ApplicationUser user,String jwt){
         this.user = user;
         this.jwt = jwt;

    }
}
