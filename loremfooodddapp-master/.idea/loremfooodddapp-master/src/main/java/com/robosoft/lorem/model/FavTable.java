package com.robosoft.lorem.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class FavTable
{
    private int userId;
    private int brandId;
}
