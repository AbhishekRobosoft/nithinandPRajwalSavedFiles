package com.robosoft.lorem.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

//Akrithi

@Service
public class SmsServiceImpl implements SmsService
{
    private static final String ACCOUNT_SID="ACd6a7bd40544e6d130c3443f89b82518e";
    private static final String AUTH_ID="313b442dca3368ea3af6e37c4d558b54";
    static
    {
        Twilio.init(ACCOUNT_SID,AUTH_ID);
    }
    public boolean sendSms(String mobileNumber, int tfaCode)
    {
        Message.creator(new PhoneNumber(mobileNumber), new PhoneNumber("+13609970496"),
                "Your Two Factor Authentication code is:"+tfaCode).create();
        return true;
    }
}
