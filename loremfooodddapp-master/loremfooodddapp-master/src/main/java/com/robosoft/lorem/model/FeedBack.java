package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class FeedBack
{
    private int userId;
    private String role;
    private String name;
    private String entityName;
    private String contactEmailId;
    private String contactMobileNumber;
    private String EntityArea;
    private String EntityCity;
    private String categoryType;
    private String message;
}
