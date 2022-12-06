package com.robosoft.lorem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserEditFields
{
    private int userId;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private MultipartFile profilePic;
    private String profileUrl;
}
