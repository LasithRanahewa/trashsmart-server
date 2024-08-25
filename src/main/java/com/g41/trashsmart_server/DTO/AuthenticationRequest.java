package com.g41.trashsmart_server.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    private String email;
    private String password;
//    private String role;
}

