package com.robosoft.lorem.response;
import lombok.Data;
import java.util.List;

@Data
public class OrderDetails
{
    private int orderId;
    private int cartId;
    private List<DishInfo> dishInfoList;
    private int restaurantId;
    private String restaurantName;
    private AmountDetails amountDetails;
    private String deliveryAddress;
    private String scheduleDate;
    private String scheduleTime;
}
