package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class NewUser
{
    private String emailId;
    private int emailOtp;
    private String otpExpireTime;
    private boolean emailVerified;
    private String password;
}
