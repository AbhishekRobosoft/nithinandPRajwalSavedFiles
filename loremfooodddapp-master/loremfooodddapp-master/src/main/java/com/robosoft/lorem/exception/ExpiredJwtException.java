package com.robosoft.lorem.exception;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;

public class ExpiredJwtException extends ClaimJwtException
{
    public ExpiredJwtException(Header header, Claims claims, String message)
    {
        super(header, claims, message);
    }

    public ExpiredJwtException(Header header, Claims claims, String message, Throwable cause)
    {
        super(header, claims, message, cause);
    }
}