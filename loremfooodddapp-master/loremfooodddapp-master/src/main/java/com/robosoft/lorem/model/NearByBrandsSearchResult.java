package com.robosoft.lorem.model;

import lombok.Data;

import java.util.List;

@Data
public class NearByBrandsSearchResult {

    private Long totalResultsCount;
    private Integer pageResultsCount;
    private List<BrandSearchModel> nearByBrands;
}
