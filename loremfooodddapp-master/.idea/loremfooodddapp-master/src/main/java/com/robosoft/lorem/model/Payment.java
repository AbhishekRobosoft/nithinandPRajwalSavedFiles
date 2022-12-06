package com.robosoft.lorem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Payment
{
    private String paymentType;
    private int paymentId;
    private int userId;
    private int orderId;
    private float amount;
    private float discount;
    private float taxAmount;
    private float grandTotal;
    private String promoCode;
    private String cvv;
    private String cardNo;
    private String paymentStatus;
    private String orderStatus;
    private int addressId;
    private Date scheduleDate;
    private Time scheduleTime;

//    public Date getScheduleDate()
//    {
//        return scheduleDate;
//    }
//
//
//    //format  yyyy-[m]m-[d]d
//    public void setScheduleDate(String scheduleDate)
//    {
//        this.scheduleDate = Date.valueOf(scheduleDate);
//    }
//
//    public Time getScheduleTime()
//    {
//        return scheduleTime;
//    }
//
//
//    // format hh:mm:ss
//    public void setScheduleTime(String scheduleTime)
//    {
//        this.scheduleTime = Time.valueOf(scheduleTime);
//    }
}
