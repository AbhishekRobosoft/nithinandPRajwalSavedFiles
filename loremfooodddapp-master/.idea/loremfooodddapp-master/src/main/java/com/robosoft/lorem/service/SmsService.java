package com.robosoft.lorem.service;

public interface SmsService {
    boolean sendSms(String mobileNo, int tfaCode);
}
