package com.robosoft.lorem.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Menu
{
    private String  dishPhoto;
    public Menu(String dishPhoto)
    {
        this.dishPhoto=dishPhoto;
    }
}
