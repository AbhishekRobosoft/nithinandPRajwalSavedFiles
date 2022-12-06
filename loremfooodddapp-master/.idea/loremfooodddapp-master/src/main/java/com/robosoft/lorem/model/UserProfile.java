package com.robosoft.lorem.model;


import lombok.Data;

@Data
public class UserProfile {

    private int userId;

    private String firstName;

    private String lastName;

    private String profilePicURL;

    private String email;

    private int creditScore;

    private String mobileNumber;

    private boolean isMobileVerified;

}
