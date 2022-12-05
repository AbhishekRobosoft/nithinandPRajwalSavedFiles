package com.robosoft.lorem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class OrderResponseModel {
    private Integer totalRecordsCount;
    private Integer totalRecordsInPage;
    private List<OrderModel> orders;
}
