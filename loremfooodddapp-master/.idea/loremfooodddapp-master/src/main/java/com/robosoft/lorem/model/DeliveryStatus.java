package com.robosoft.lorem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatus {

    private int orderId;
    private String orderStatus;
    private String timingDetails;
}
