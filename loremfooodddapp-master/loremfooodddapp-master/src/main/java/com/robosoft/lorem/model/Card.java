package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Card
{
    private String cardNo;
    private String cardName;
    private String expiryDate;
    private String cvv;
    private int userId;
    private boolean cardType;
}
