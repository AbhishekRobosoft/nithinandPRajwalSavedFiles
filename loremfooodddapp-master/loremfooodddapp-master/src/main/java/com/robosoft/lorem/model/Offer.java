package com.robosoft.lorem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Offer
{
    private String offerId;
    private float discount;
    private int maxCashBack;
    private Date validUpto;
    private int validPerMonth;
    //store url in db
    private String photo;
    private String description;
    private int brandId;
    private boolean codEnabled;
    private float superCashPercent;
    private int maxSuperCash;

    private float discountedAmount;

    public Offer(String offerId, float discount, int maxCashBack, Date validUpto, int validPerMonth, String photo, String description, int brandId, boolean codEnabled, float superCashPercent, int maxSuperCash) {
        this.offerId = offerId;
        this.discount = discount;
        this.maxCashBack = maxCashBack;
        this.validUpto = validUpto;
        this.validPerMonth = validPerMonth;
        this.photo = photo;
        this.description = description;
        this.brandId = brandId;
        this.codEnabled = codEnabled;
        this.superCashPercent = superCashPercent;
        this.maxSuperCash = maxSuperCash;
    }

    //private String photo_url;

}
