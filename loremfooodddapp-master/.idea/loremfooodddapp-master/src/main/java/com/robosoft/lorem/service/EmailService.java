package com.robosoft.lorem.service;

import javax.mail.MessagingException;

public interface EmailService {
    boolean sendEmail(String emailId, int tfaCode) throws MessagingException;
}
