package com.robosoft.lorem.entity;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.Time;


@AllArgsConstructor
@RequiredArgsConstructor
public class OpeningInfo {

    private int restaurantId;
    private Date dateOf;
    private Time openingTime;
    private Time closingTime;
    private String reason;
    private boolean opened;

    public Time getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public Time getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Time closingTime) {
        this.closingTime = closingTime;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Date getDateOf() {
        return dateOf;
    }

    public void setDateOf(String dateOf) {
        this.dateOf = Date.valueOf(dateOf);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
}
