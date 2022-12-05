package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTResponse
{
    private String JWTToken;
    private String userName;
}
