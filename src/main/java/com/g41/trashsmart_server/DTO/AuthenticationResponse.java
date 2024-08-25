package com.g41.trashsmart_server.DTO;

import lombok.Getter;

@Getter
public class AuthenticationResponse {
    private String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
}
