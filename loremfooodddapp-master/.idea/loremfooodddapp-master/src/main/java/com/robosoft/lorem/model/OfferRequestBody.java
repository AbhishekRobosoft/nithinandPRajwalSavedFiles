package com.robosoft.lorem.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfferRequestBody
{
    private String offerId;
    private float discount;
    private int maxCashBack;
    private Date validUpto;
    private int validPerMonth;
    //input photo
    private MultipartFile photo;
    //output photo
    private String photo_url;

    private String description;
    private int brandId;
    private boolean codEnabled;
    private float superCashPercent;
    private int maxSuperCash;
}
