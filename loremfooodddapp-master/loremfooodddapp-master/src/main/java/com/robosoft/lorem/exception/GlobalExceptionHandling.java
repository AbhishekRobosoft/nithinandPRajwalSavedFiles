package com.robosoft.lorem.exception;
import com.robosoft.lorem.model.Offer;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling
{
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<String> handleJWTToken(com.robosoft.lorem.exception.ExpiredJwtException e)
    {
        return new ResponseEntity<String>("Session has been Expired, Please login again", HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleErrors(Exception e)
    {
        e.printStackTrace();
        return new ResponseEntity<String>("Some error occurred...", HttpStatus.EXPECTATION_FAILED);

    }


    @ExceptionHandler(value= EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException e)
    {
        e.printStackTrace();
        return new ResponseEntity<String>("NO content found...", HttpStatus.NO_CONTENT);

    }
    @ExceptionHandler(value= CartDeletedException.class)
    public ResponseEntity<String> handleCartDeletedExceptionException(CartDeletedException e)
    {
        e.printStackTrace();
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);

    }

    @ExceptionHandler(value= FirstNameOrPasswordNullException.class)
    public ResponseEntity<String> handleFirstNameNullException(FirstNameOrPasswordNullException e)
    {
        e.printStackTrace();
        return new ResponseEntity<String>("Enter valid mandatory fields  ", HttpStatus.OK);

    }

    @ExceptionHandler(value = RestaurantIsNotOpenException.class)
    public ResponseEntity<String> handleRestaurantNotOpenException(RestaurantIsNotOpenException restaurantIsNotOpenException){
        restaurantIsNotOpenException.printStackTrace();
        return new ResponseEntity<String>(restaurantIsNotOpenException.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }



    @ExceptionHandler(value = InvalidScheduledDateTimeException.class)
    public ResponseEntity<String> handleInvalidScheduledDateAndTimeException(InvalidScheduledDateTimeException invalidScheduledDateTimeException){
        invalidScheduledDateTimeException.printStackTrace();
        return new ResponseEntity<String>(invalidScheduledDateTimeException.getMessage(),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = InvalidDateTimeException.class)
    public ResponseEntity<String> handleInvalidDateTimeException(InvalidDateTimeException invalidDateTimeException){
        invalidDateTimeException.printStackTrace();
        return new ResponseEntity<String>(invalidDateTimeException.getMessage(),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = OfferExpiredException.class)
    public ResponseEntity<String> handleInvalidOfferException(OfferExpiredException offerExpiredException){
        offerExpiredException.printStackTrace();
        return new ResponseEntity<String>("Sorry, Offer expired..", HttpStatus.EXPECTATION_FAILED);
    }


    @ExceptionHandler(value = InvalidIdException.class)
    public ResponseEntity<String> handleInvalidIdException(InvalidIdException invalidIdException){
        invalidIdException.printStackTrace();
        return new ResponseEntity<String>(invalidIdException.getMessage(),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value= InvalidOrderStatusException.class)
    public ResponseEntity<String> handleInvalidOrderStatusException(InvalidOrderStatusException invalidOrderStatusException){
        invalidOrderStatusException.printStackTrace();

        return new ResponseEntity<String>(invalidOrderStatusException.getMessage(),HttpStatus.BAD_REQUEST);
    }




}


