package com.robosoft.lorem.model;

import lombok.Data;

import java.util.List;

@Data
public class AddressDesc {

    private int count;
    private List<AddressDetails> addressDetailsList;

}
