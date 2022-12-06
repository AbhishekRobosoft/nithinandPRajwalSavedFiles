package com.robosoft.lorem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartsResponseModel {

    private Long totalResultCount;
    private Integer perPageCount;
    private List<CartModel> carts;
}
