package com.robosoft.lorem.service;

import com.robosoft.lorem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class DAOServiceImpl implements DAOService
{
    private static final String EMAIL_VERIFICATION="INSERT INTO newuser(emailId,emailOtp,OtpExpireTime) VALUES(?,?,?)";
    private static final String UPDATE_OTP="UPDATE newuser set emailOtp=?,OtpExpireTime=? where emailId=?";
    private static final String UPDATE_EMAIL_VERIFIED="UPDATE newuser set otpVerified=true where emailId=?";
    private static final String UPDATE_NUMBER_VERIFIED="UPDATE mobileotp set otpVerified=true where mobileNo=?";
    private static final String CHECK_EMAIL="select emailId from user where emailId=?";
    private static final String RESET_PASSWORD="UPDATE user set password=? where emailId=?";
    private static final String CHECK_MOBILE="select mobileNo from user where mobileNo=?";
    private static final String RESET_PASSWORD_BY_SMS="UPDATE user set password=? where mobileNo=?";
    private static final String UPDATE_MOBILE_OTP_FORGOT_PASSWORD="UPDATE mobileotp set otpNumber=?,OtpExpireTime=? where mobileNo=?";
    private static final String GET_MOBILE_NO_BY_USERID="select mobileNo from user where userId=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SmsService smsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    //Akrithi

    @Override
    public boolean update2FAProperties(String emailId, int tfaCode)
    {
        try
        {
            String email= jdbcTemplate.queryForObject("select emailId from newuser where emailId=?",new Object[]{emailId},String.class);
            if(email!=null)
            {
                boolean verified=jdbcTemplate.queryForObject("select otpVerified from newuser where emailId=?",new Object[]{emailId},Boolean.class);
                if(!verified)
                {
                    jdbcTemplate.update(UPDATE_OTP,tfaCode,(System.currentTimeMillis()/1000)+120,email);
                    return true;
                }
                return false;
            }
        }

        //allow creating an account only if email is not registered
        catch (DataAccessException e)
        {
            jdbcTemplate.update(EMAIL_VERIFICATION,emailId,tfaCode,(System.currentTimeMillis()/1000)+120);
            return true;
        }
        return false;
    }

    //verifying email otp
    @Override
    public boolean checkEmailCode(String id,int code)
    {
        boolean isValid= jdbcTemplate.queryForObject("select count(*) from newuser where emailOtp=? and emailId=? and OtpExpireTime>=?",new Object[]{code,id,System.currentTimeMillis()/1000},Integer.class)>0;


        if(isValid )
        {
            boolean otpVerified=jdbcTemplate.queryForObject("select otpVerified from newuser where emailId=?",new Object[]{id},boolean.class);
            if(!otpVerified)
            {
                jdbcTemplate.update(UPDATE_EMAIL_VERIFIED,id);
            }
        }
        return isValid;
    }


    //-- verifying mobile otp
    @Override
    public boolean checkSmsCode(String mobileNum,int code)
    {
        boolean isValid= jdbcTemplate.queryForObject("select count(*) from mobileotp where otpNumber=? and mobileNo=? and OtpExpireTime>=?",new Object[]{code,mobileNum,System.currentTimeMillis()/1000},Integer.class)>0;

        if(isValid )
        {
            boolean otpVerified=jdbcTemplate.queryForObject("select otpVerified from mobileotp where mobileNo=?",new Object[]{mobileNum},boolean.class);

            if(!otpVerified)
            {
                jdbcTemplate.update(UPDATE_NUMBER_VERIFIED,mobileNum);
            }
        }
        return isValid;
    }

    @Override
    public boolean  forgotPasswordEmail(String emailId, int tfaCode)
    {
        try
        {
            //email id from user
            String email=jdbcTemplate.queryForObject(CHECK_EMAIL,new Object[]{emailId},String.class);

            if(email!=null)
            {
                jdbcTemplate.update(UPDATE_OTP,tfaCode,(System.currentTimeMillis()/1000)+120,emailId);
                return true;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return false;
    }


    @Override
    public boolean resetPassword(User user)
    {

        String  hashcode= passwordEncoder.encode(user.getPassword());
        if(user.getEmailId()!=null)
        {
            try
            {
                Integer otp = jdbcTemplate.queryForObject("select emailOtp from newuser where emailId=? and emailOtp=?", Integer.class, new Object[]{user.getEmailId(), user.getOtp()});
                if (otp != null) ;
                {
                    jdbcTemplate.update(RESET_PASSWORD, hashcode, user.getEmailId());
                    return true;
                }
            } catch (DataAccessException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        else if(user.getMobileNo()!=null)
        {
            try
            {
                Integer otp = jdbcTemplate.queryForObject("select otpNumber from mobileotp where mobileNo=? and otpNumber=?", Integer.class, new Object[]{user.getMobileNo(), user.getOtp()});
                if (otp != null) ;
                {
                    jdbcTemplate.update(RESET_PASSWORD_BY_SMS, hashcode, user.getMobileNo());
                    return true;
                }
            } catch (DataAccessException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


    @Override
    public boolean  forgotPasswordSms(String mobileNumber, int tfaCode)
    {
        try
        {
            String mobile = jdbcTemplate.queryForObject(CHECK_MOBILE, new Object[]{mobileNumber}, String.class);
            if (mobile != null)
            {
                jdbcTemplate.update(UPDATE_MOBILE_OTP_FORGOT_PASSWORD, tfaCode, (System.currentTimeMillis() / 1000) + 120, mobileNumber);
            }
        }
        catch (DataAccessException e)
        {
            return false; //if no mobile no is found, verify through email.
        }
        return true;
    }

    @Override
    public boolean  verifyMobileRequest(String mobileNumber, int tfaCode)
    {
        try
        {
            jdbcTemplate.update(UPDATE_MOBILE_OTP_FORGOT_PASSWORD, tfaCode, (System.currentTimeMillis() / 1000) + 120, mobileNumber);
        }
        catch (DataAccessException e)
        {
            return false;
        }
        return true;
    }

    // extra
    @Override
    public boolean updatingNumber(User user)
    {
        //old mobile number
        String phone = jdbcTemplate.queryForObject(GET_MOBILE_NO_BY_USERID,new Object[]{user.getUserId()},String.class);

        if(phone.equals(user.getMobileNo()))
        {
            return false;
        }

        //insert mobile number to mobileOtp table
        jdbcTemplate.update("insert into mobileotp(mobileNo) values(?)",user.getMobileNo());

        //update user mobile no
        jdbcTemplate.update("update user set mobileNo=? where userId=?",user.getMobileNo(),user.getUserId());

        //delete old number from mobileOtp
        jdbcTemplate.update("delete from mobileotp where mobileNo=?",phone);

        return true;
    }

}