package com.robosoft.lorem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestaurantSearchResult {

    private Long totalRocordsCount;
    private Integer perPageRecordsCount;
    private List<RestaurantSearchModel> pageResults;

}
