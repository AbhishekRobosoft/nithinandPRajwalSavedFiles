package com.robosoft.lorem.controller;
import com.cloudinary.utils.ObjectUtils;
import com.robosoft.lorem.model.*;
import com.robosoft.lorem.response.OrderDetails;
import com.robosoft.lorem.service.CloundinaryConfig;
import com.robosoft.lorem.service.DAOService;
import com.robosoft.lorem.service.SmsService;
import com.robosoft.lorem.service.UserService;
import com.robosoft.lorem.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;


@RestController
@CrossOrigin(
        allowedHeaders = {"Authorization", "Origin"},
//        exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        origins ={"http://localhost:4200","http://localhost:3000"})
@RequestMapping(value = "/User", produces = "application/json")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    //for using user services
    @Autowired
    private UserService userService;


    //for using user details services
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    CloundinaryConfig cloudinary;

    @Autowired
    SmsService smsService;

    @Autowired
    DAOService daoService;


    //Abhishek

    @PutMapping("/likeBrand")
    public ResponseEntity<?> addToFav(@RequestBody FavTable favTable) {
        return userService.addToFavourite(favTable);
    }


    @PostMapping("/review")
    public ResponseEntity<?> addReview(@ModelAttribute ReviewInfo reviewInfo) {
        try {
            List<String> photoLinks = new ArrayList<String>();
            for (int i = 0; i < reviewInfo.getMultipartFileList().size(); i++) {
                Map uploadResult = cloudinary.upload(reviewInfo.getMultipartFileList().get(i).getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                photoLinks.add(uploadResult.get("url").toString());
            }
            reviewInfo.setPhotoLinks(photoLinks);
            return userService.addReview(reviewInfo);
        } catch (Exception e) {
            return userService.addReview(reviewInfo);
        }
    }


    // changed to post mapping
    @PostMapping("/getOrderDetails")
    public ResponseEntity<?> getOrderDetails(@RequestBody Orders orders)
    {
        OrderDetails orderDetails = userService.getOrderDetails(orders);
        if (orderDetails != null)
        {
            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("No Details Found", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/addCard")
    public ResponseEntity<String> addCard(@RequestBody Card card) {
        try {
            boolean message = userService.addCard(card);
            if (message == true) {
                return new ResponseEntity("Card added successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity("Failed to add card", HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            return new ResponseEntity("Failed to add card", HttpStatus.BAD_GATEWAY);
        }

    }

    @GetMapping("/viewCards/{pageNo}/{limit}")
    public ResponseEntity<?> viewCards(@PathVariable int pageNo, @PathVariable int limit ) {
        try {
            Map<Integer, List<Card>> cardLists = userService.viewCards(pageNo, limit);
            if (cardLists.size() == 0) {
                return new ResponseEntity<>("No Cards Saved Please Add Some Cards", HttpStatus.EXPECTATION_FAILED);
            }
            return new ResponseEntity<>(cardLists, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("No Cards Saved Please Add Some Cards", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/makeCardPrimary")
    public ResponseEntity<String> makeCardPrimary(@RequestBody Card card) {
        try {
            this.userService.makeCardPrimary(card);
            return ResponseEntity.ok().body(card.getCardNo() + " Selected as primary");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong");
        }
    }

    @PutMapping("/deleteCard")
    public ResponseEntity<String> deleteCard(@RequestBody Card card)
    {
        try
        {
            this.userService.deleteCard(card);
            return ResponseEntity.ok().body(card.getCardNo() + " Removed successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong");
        }
    }

    @PostMapping("/check-order-details")
    public ResponseEntity<?> checkOrderDetails(@ModelAttribute Payment payment, @RequestParam int cartId)
    {
        if(!userService.checkOrderDetials(payment, cartId))
        {
            return new ResponseEntity<>(false,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/makePayment")
    public ResponseEntity<String> makePayment(@RequestBody Payment payment) {
        try {
            String message = userService.makePayment(payment);
            return new ResponseEntity(message, HttpStatus.OK);
        } catch (Exception e) {
            String message = userService.makePayment(payment);
            return new ResponseEntity(message, HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/giveFeedback")
    public ResponseEntity<String> giveFeedback(@RequestBody FeedBack feedBack) {
        try {
            if (userService.giveFeedback(feedBack) == true) {
                return new ResponseEntity("Thank you for your feedback", HttpStatus.OK);
            } else {
                return new ResponseEntity("Something went wrong", HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            return new ResponseEntity("Something went wrong", HttpStatus.BAD_GATEWAY);
        }

    }

    //Prajwal

    //create and update cart
    @PostMapping("/Cart")
    public ResponseEntity<?> createOrUpdateCart(@RequestBody CartModel cartModel) {
        cartModel.setUserId(userService.getUserIdFromEmail());
        CartModel returnedCartModel = userService.saveOrUpdateCart(cartModel);

        if (returnedCartModel == null)
            return new ResponseEntity<>("Cart Not created or Updated...", HttpStatus.EXPECTATION_FAILED);

        return ResponseEntity.ok(returnedCartModel);

    }


    //like and unlike review
    @PostMapping("/Review/Like")
    public ResponseEntity<?> likeAReview(@RequestBody int reviewId) {
        if (userService.likeAreview(userService.getUserIdFromEmail(), reviewId))
            return new ResponseEntity<>("Liked Review Successfully...", HttpStatus.OK);

        return new ResponseEntity<>("UnLiked Review Successfully...", HttpStatus.EXPECTATION_FAILED);
    }


    //get My Profile
    @GetMapping("/Profile")
    public ResponseEntity<?> getUserProfile() {
        UserProfile userProfile = userService.getUserProfile(userService.getUserIdFromEmail());

        if (userProfile != null)
            return new ResponseEntity<>(userProfile, HttpStatus.OK);

        return new ResponseEntity<>(userProfile, HttpStatus.NO_CONTENT);
    }


    //get my orders based on status
    @GetMapping("/Orders")
    public ResponseEntity<?> getMyOrdersByStatus(@RequestParam String orderStatus, @RequestParam int pageNumber, @RequestParam int limit) {

        OrderResponseModel orderResponseModel = userService.getMyOrdersByStatus(orderStatus, userService.getUserIdFromEmail(), pageNumber, limit);

        if (orderResponseModel == null)
            return new ResponseEntity<>("No Content in current page..", HttpStatus.NO_CONTENT);

        if (pageNumber == 1) {
            if (orderResponseModel.getTotalRecordsCount() == 0)
                return new ResponseEntity<>("No Orders Found under this user id..", HttpStatus.NO_CONTENT);
        }

        if (orderResponseModel.getTotalRecordsInPage() == 0)
            return new ResponseEntity<>("No Content in current page..", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(orderResponseModel, HttpStatus.OK);
    }


    //get my carts
    @GetMapping("/Carts")
    public ResponseEntity<?> getMyCarts(@RequestParam int pageNumber, @RequestParam int limit) {
        CartsResponseModel cartsResponseModel = userService.getMyCarts(userService.getUserIdFromEmail(), pageNumber, limit);

        if (pageNumber == 1) {
            if (cartsResponseModel.getTotalResultCount() == 0)
                return new ResponseEntity<>("No Carts found under this user id..", HttpStatus.NO_CONTENT);
        }

        if (cartsResponseModel.getPerPageCount() == 0)
            return new ResponseEntity<>("No Content in current page..", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(cartsResponseModel, HttpStatus.OK);
    }


    //remove a cart
    @DeleteMapping("/Cart")
    public HttpStatus deleteMyCart(@RequestBody CartModel cart) {
        if (userService.removeCart(userService.getUserIdFromEmail(), cart.getCartId()))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }


    //get cart by cart id and user id
    @PostMapping("/MyCart")
    public ResponseEntity<?> getCartById(@RequestBody CartModel cartModel) {
        cartModel.setUserId(userService.getUserIdFromEmail());
        CartModel returningCartModel = userService.getCartById(cartModel);

        if (returningCartModel == null)
            return new ResponseEntity<String>("No Cart matching given details found..", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(returningCartModel, HttpStatus.OK);
    }

    //nithin


    //adding new address
    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddress(@RequestBody Address address) throws Exception {
        try {
            return userService.addAddress(address);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("failed to add the address", HttpStatus.EXPECTATION_FAILED);
        }

    }

    //editing address by user
    @PostMapping("/editAddress/{addressId}")
    public ResponseEntity<String> editAddress(@RequestBody Address address, @PathVariable int addressId) throws Exception {
        if (userService.editAddress(address, addressId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("Address updated successfully");
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Task failed,pls provide correct information");

    }

    //    deleting the address
    @DeleteMapping("/deleteAddress")
    public ResponseEntity<String> deleteAddress(@RequestParam int addressId) throws Exception {
        if (userService.deleteAddress(addressId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("Address deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("address deletion failed");

    }

    // setting primary address by user
    @PutMapping("/setPrimaryAddress")
    public ResponseEntity<String> setPrimaryAddress(@RequestParam int addressId) throws Exception {
        if (userService.setPrimaryAddress(addressId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("primary address set successfully");
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("failed to set primary address");

    }

    @GetMapping("/displayAddresses")
    public ResponseEntity<AddressDesc> displayAddresses() {
        AddressDesc addressDesc = userService.displayAddresses(userService.getUserIdFromEmail());
        if (addressDesc == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(addressDesc));
    }

    @PostMapping("/choosePayment/{offerId}")
    public ResponseEntity<?> chooseAddress(@RequestBody Orders orders,@PathVariable String offerId) throws Exception {
        OrderInfo orderInfo = userService.chooseAddress(orders, userService.getUserIdFromEmail(), orders.getLocation(),offerId);
        if (orderInfo == null) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("failed");
        }
        return ResponseEntity.of(Optional.of(orderInfo));

    }

    @PutMapping("/cancelOrder")
    public ResponseEntity<?> cancelOrder(@RequestBody Orders orders) throws IOException {
        if (userService.cancelOrder(orders)) {
            return ResponseEntity.status(HttpStatus.OK).body("order canceled");
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("failed to cancel order");
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateOrderStatus(@RequestBody Orders orders) {
        if (userService.updateOrderStatus(orders)) {
            return ResponseEntity.status(HttpStatus.OK).body("" + orders.getOrderStatus());
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Task failed");
    }


//Akrithi

    @PutMapping("/editProfile")
    public ResponseEntity<?> editProfile(@ModelAttribute UserEditFields user)
    {
        try
        {
            Map uploadResult2 = cloudinary.upload(user.getProfilePic().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String photo_url = uploadResult2.get("url").toString();
            user.setProfileUrl(photo_url);
        }
        catch (Exception e)
        {
            user.setProfileUrl(null);
        }
        return userService.editProfile(user);

    }

    @GetMapping("/refer")
    public ResponseEntity<Integer> referAFriend() {
        try
        {
            int reg_user = userService.referAFriend();
            return new ResponseEntity<Integer>(reg_user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // if required
    @GetMapping("/share/referAFriend")
    public ResponseEntity<Map<Integer, String>> shareReference() {
        try {
            Map<Integer, String> reg_user = userService.onClickShareApp();
            return new ResponseEntity<Map<Integer, String>>(reg_user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/redeem")
    public ResponseEntity<?> redeemItNow(@RequestParam int claimedCreditScore) {
        ResponseEntity<?> claimed = userService.redeem(claimedCreditScore);
        return claimed;
    }

    @PutMapping("/verifyMobileNumber")
    public ResponseEntity<String> resetPasswordThroughSms(@RequestBody User user) throws MessagingException
    {
        Integer tfaCode =Integer.valueOf(new Random().nextInt(9999)+1000);
        boolean result= daoService.verifyMobileRequest(user.getMobileNo(), tfaCode);
        if(result)
        {
            try
            {
                boolean smsSent= smsService.sendSms(user.getMobileNo(),tfaCode);
                if(smsSent)
                {
                    return new ResponseEntity<>("OTP sent to your mobile",HttpStatus.OK);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return  new ResponseEntity<>("OTP could not be sent to your number",HttpStatus.EXPECTATION_FAILED);
            }
        }
        //if no mobile number found
        return  new ResponseEntity<>("mobile number is already verified",HttpStatus.EXPECTATION_FAILED);
    }

    // changed to path variable
    @GetMapping("/apply")
    public ResponseEntity<?> applyOffer(@RequestParam String offerId, @RequestParam int cartId) {
        ResponseEntity<?> applied = userService.applyOffer(offerId,cartId);
        return applied;
    }

    @GetMapping("/OrderStatus")
    public ResponseEntity<?> getMyOrderStatus(@RequestParam int orderId){
        List<DeliveryStatus> returnedDeliveryStatus = userService.getMyDeliveryStatus(orderId, userService.getUserIdFromEmail());

        if(returnedDeliveryStatus==null)
            return new ResponseEntity<String>("Delivery status not found..",HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(returnedDeliveryStatus);
    }


}
