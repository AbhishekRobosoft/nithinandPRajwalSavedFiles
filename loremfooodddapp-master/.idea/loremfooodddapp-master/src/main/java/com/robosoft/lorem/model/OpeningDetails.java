package com.robosoft.lorem.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class OpeningDetails {
    private Date dateOf;
    private Time openingTime;
    private Time closingTime;
    private String reason;
    private boolean opened;

}
