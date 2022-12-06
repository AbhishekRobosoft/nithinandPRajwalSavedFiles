package com.robosoft.lorem.exception;

public class RestaurantIsNotOpenException extends RuntimeException{
    public RestaurantIsNotOpenException(String message) {
        super(message);
    }
}
