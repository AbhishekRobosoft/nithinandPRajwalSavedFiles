package com.robosoft.lorem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.robosoft.lorem.routeResponse.Location;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/*
{
"cartId":"",
"userId":"",
"restaurantId":"",
"itemsIncart":[{
"dishId":"",
"dishCount":"",
"customizationInfo":"",
"addOnCount":"",
"itemCount":""
},],
"cookingInstruction":"",
"toPay":"",
"scheduleDate":"",
"scheduleTime":"",
}
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartModel {


    private Integer cartId;
    private Integer userId;
    private Integer restaurantId;
    private List<ItemModel> itemsIncart;
    private String cookingInstruction;
    private Double toPay;
    private Date scheduleDate;
    private Time scheduleTime;

    private Integer countOfItems;

    private String restaurantAddress;

    private String restaurantName;

    private Long deliveryDuration;

    private Location location;



    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }







    public List<ItemModel> getItemsIncart() {
        return itemsIncart;
    }

    public void setItemsIncart(List<ItemModel> itemsIncart) {
        this.itemsIncart = itemsIncart;
    }

    public String getCookingInstruction() {
        return cookingInstruction;
    }

    public void setCookingInstruction(String cookingInstruction) {
        this.cookingInstruction = cookingInstruction;
    }



    public Date getScheduleDate() {
        return scheduleDate;
    }


    //format  yyyy-[m]m-[d]d
    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = Date.valueOf(scheduleDate);
    }

    public Time getScheduleTime() {
        return scheduleTime;
    }


    // format hh:mm:ss
    public void setScheduleTime(String scheduleTime) {

        String[] timing = scheduleTime.split(":");

        if(timing.length==2)
            scheduleTime=scheduleTime+":00";


        this.scheduleTime = Time.valueOf(scheduleTime);
    }




    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Double getToPay() {
        return toPay;
    }

    public void setToPay(Double toPay) {
        this.toPay = toPay;
    }

    public Integer getCountOfItems() {
        return countOfItems;
    }

    public void setCountOfItems(Integer countOfItems) {
        this.countOfItems = countOfItems;
    }

    public Long getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(Long deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
