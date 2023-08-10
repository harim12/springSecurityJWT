package com.RimHASSANI.demo.springsecurityjwt.model;

import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationDTO {
    private String username;
    private String password;
}
