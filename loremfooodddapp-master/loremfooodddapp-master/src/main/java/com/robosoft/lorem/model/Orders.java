package com.robosoft.lorem.model;
import com.robosoft.lorem.routeResponse.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Orders
{
    private int orderId;
    private String orderStatus;
    private String orderType;
    private int userId;
    private String contactName;
    private String contactNo;
    private int addressId;
    private int cartId;
    private int restaurantId;
    private String deliveryInstructions;
    private List<Card> cardList;
    private String addressDesc;
    private Date scheduleDate;
    private Time scheduleTime;
    Location location;



}
