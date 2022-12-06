package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JWTRequest
{
    private String emailId;
    private String password;
}
