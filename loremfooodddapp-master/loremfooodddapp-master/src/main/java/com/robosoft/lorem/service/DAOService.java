package com.robosoft.lorem.service;

import com.robosoft.lorem.model.User;

public interface DAOService {
    boolean update2FAProperties(String emailId, int tfaCode);

    boolean checkEmailCode(String emailId, int emailOtp);

    boolean checkSmsCode( String mobileNo, int otpNumber);

    boolean forgotPasswordEmail(String emailId, int tfaCode);

    boolean resetPassword(User user);

    boolean forgotPasswordSms(String mobileNo, int tfaCode);

    boolean  verifyMobileRequest(String mobileNumber, int tfaCode);

    boolean updatingNumber(User user);
}
