package com.robosoft.lorem.response;
import lombok.Data;

@Data
public class AmountDetails
{
    private float totalAmount;
    private float AmountPaid;
    private float taxAmount;
    private float discount;
    private String paymentType;
}
