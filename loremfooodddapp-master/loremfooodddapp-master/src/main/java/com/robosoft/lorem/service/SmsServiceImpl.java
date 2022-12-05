package com.robosoft.lorem.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

//Akrithi

@Service
public class SmsServiceImpl implements SmsService
{
    private static final String ACCOUNT_SID="AC6a47110f1ac71e02ea318765069d2ec5";
    private static final String AUTH_ID="23c435feb5e283c4ddcf2f31d36a1f7b";
    static
    {
        Twilio.init(ACCOUNT_SID,AUTH_ID);
    }
    public boolean sendSms(String mobileNumber, int tfaCode)
    {
        Message.creator(new PhoneNumber(mobileNumber), new PhoneNumber("+13473450647"),
                "Your Two Factor Authentication code is:"+tfaCode).create();
        return true;
    }
}
