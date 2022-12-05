package com.robosoft.lorem.controller;
import com.robosoft.lorem.model.*;
import com.robosoft.lorem.response.BrandList;
import com.robosoft.lorem.routeResponse.Location;
import com.robosoft.lorem.service.DAOService;
import com.robosoft.lorem.service.EmailService;
import com.robosoft.lorem.service.SmsService;
import com.robosoft.lorem.service.UserService;
import com.robosoft.lorem.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin(
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        origins ={"http://localhost:4200","http://localhost:3000"})
@RequestMapping(value="/Lorem",produces = "application/json")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    //for using user details services
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserService userService;

    @Autowired
    EmailService emailService;
    @Autowired
    SmsService smsService;
    @Autowired
    DAOService daoService;

    @Autowired
    JdbcTemplate jdbcTemplate;





    //Abhishek

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody JWTRequest jwtRequest) throws Exception
    {

        try
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getEmailId(),
                            jwtRequest.getPassword()
                    )
            );
        }
        catch (BadCredentialsException e)
        {
            return  new ResponseEntity<>("Invalid credentials",HttpStatus.EXPECTATION_FAILED);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmailId());

        String firstName=jdbcTemplate.queryForObject("select firstName from user where emailId=?",String.class, new Object[]{jwtRequest.getEmailId()});

        final String token = jwtUtility.generateToken(userDetails);

        return  new ResponseEntity<>(new JWTResponse(token, firstName),HttpStatus.OK);

    }



    @GetMapping("/viewPopularBrands/{pageNo}/{limit}")
    public ResponseEntity<?>listPopularBrands(@PathVariable int pageNo, @PathVariable int limit)
    {
        try
        {
            Map<Integer, List<BrandList>> brandLists=userService.viewPopularBrands(pageNo, limit);
            if(brandLists.size()==0)
            {
                return new ResponseEntity<>("No Popular brands to show",HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(brandLists,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return new ResponseEntity<>("No Popular brands to show",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/viewAllBrands/{pageNo}/{limit}")
    public ResponseEntity<?> viewAllPopularBrands(@PathVariable int pageNo, @PathVariable int limit)
    {
        try
        {
            Map<Integer, List<BrandList>> brandLists=userService.viewAllBrands(pageNo,limit);
            if(brandLists.size()==0)
            {
                return new ResponseEntity<>("No Popular brands to show",HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(brandLists,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>("No Popular brands to show",HttpStatus.BAD_REQUEST);
        }
    }

    // changed from get to post mapping
    @GetMapping("/getReviews")
    public ResponseEntity<?> getReviews(@RequestParam int restaurantId, @RequestParam int pageNo, @RequestParam int limit)
    {
        try
        {
            Restaurant restaurant=new Restaurant();
            restaurant.setRestaurantId(restaurantId);

            Map<Integer,Object> reviewPageResponseList= userService.viewReviews(restaurant, pageNo, limit);
            if(reviewPageResponseList.size()==0)
            {
                return new ResponseEntity<>("No Reviews to this restaurant",HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reviewPageResponseList, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("No Reviews To this restaurant",HttpStatus.NO_CONTENT);
        }
    }



    //Akrithi
    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody User user)
    {
        ResponseEntity<?> reg_user = userService.createAccount(user);
        return reg_user;
    }
    @PutMapping("/emails2fa")
    public ResponseEntity<Object> send2faCodeInEmail(@RequestBody NewUser newuser) throws MessagingException {
        //2fa code generation
        Integer tfaCode =Integer.valueOf(new Random().nextInt(9999)+1000);

        boolean email_check= daoService.update2FAProperties(newuser.getEmailId(),tfaCode);

        if(email_check)
        {
            try
            {
                boolean emailSent= emailService.sendEmail(newuser.getEmailId(),tfaCode);
                if(emailSent)
                {
                    return new ResponseEntity<>("OTP sent to your email",HttpStatus.OK);
                }
            }
            catch (Exception e)
            {
                return  new ResponseEntity<>("OTP could not be sent",HttpStatus.EXPECTATION_FAILED);
            }
        }
        return  new ResponseEntity<>("Account already exists",HttpStatus.EXPECTATION_FAILED);
    }

    @PutMapping("/verifyEMail2fa")
    public ResponseEntity<Object> verifyEmail(@RequestBody NewUser newUser)
    {
        //front-end will provide the email id
        boolean isValid= daoService.checkEmailCode(newUser.getEmailId(),newUser.getEmailOtp());
        if(isValid)
        {
            return  new ResponseEntity<>("OTP verified",HttpStatus.OK);
        }
        return  new ResponseEntity<>("Invalid OTP",HttpStatus.CONFLICT);
    }


    @PutMapping("/verifyMobile2fa")
    public ResponseEntity<?> verifySms(@RequestParam("mobileNo") String mobileNo,@RequestParam("otpNumber") int otpNumber)
    {
        boolean isValid= daoService.checkSmsCode(mobileNo,otpNumber);
        if(isValid)
        {
            return  new ResponseEntity<>("OTP verified",HttpStatus.OK);
        }
        return  new ResponseEntity<>("Invalid OTP",HttpStatus.CONFLICT);
    }


    // different from emails2fa as this method doesn't create new a/c if email ID doesn't exist
    @PutMapping("/forgotPassword/email")
    public ResponseEntity<String> sendEmail2FaToResetPassword(@RequestBody NewUser newuser) throws MessagingException {
        Integer tfaCode =Integer.valueOf(new Random().nextInt(9999)+1000);

        boolean result= daoService.forgotPasswordEmail(newuser.getEmailId(), tfaCode);

        if(result)
        {
            try
            {
                boolean emailSent= emailService.sendEmail(newuser.getEmailId(),tfaCode);
                if(emailSent)
                {
                    return new ResponseEntity<>("OTP sent to your email",HttpStatus.OK);
                }
            }
            catch (Exception e)
            {
                return  new ResponseEntity<>("OTP could not be sent to your mail",HttpStatus.EXPECTATION_FAILED);
            }
        }
        //if no account found
        return  new ResponseEntity<>("No account with this email",HttpStatus.EXPECTATION_FAILED);
    }


    // since separate fields are required for updating passwords w.r.t email and number
    @PutMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody User user)
    {
        //front end will check for otp verification and redirect to password resetting
        boolean resetPassword= daoService.resetPassword(user);
        if(resetPassword)
        {
            return  new ResponseEntity<>("Password reset successful",HttpStatus.OK);
        }
        return  new ResponseEntity<>("Could not reset password",HttpStatus.NOT_MODIFIED);
    }


    @PutMapping("/mobiles2fa")
    public ResponseEntity<String> resetPasswordThroughSms(@RequestBody User user) throws MessagingException {
        Integer tfaCode =Integer.valueOf(new Random().nextInt(9999)+1000);

        boolean result= daoService.forgotPasswordSms(user.getMobileNo(), tfaCode);

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
        return  new ResponseEntity<>("mobile number is not verified",HttpStatus.EXPECTATION_FAILED);
    }

    //changed from request param to path variable
    @GetMapping("/viewDetails")
    public ResponseEntity<?> viewDetails(@RequestParam String offerId, @ModelAttribute Location location)
    {
        ResponseEntity<?> offerObj= userService.viewOfferDetails(offerId,location);
        return offerObj;
    }

    //extra api
    @PutMapping("/mobileNumber")
    public ResponseEntity<Object> updateMobileNumber(@RequestBody User user)
    {
        boolean updated= daoService.updatingNumber(user);
        if(updated)
        {
            return  new ResponseEntity<>("updated successfully",HttpStatus.OK);
        }
        return  new ResponseEntity<>("you Cannot update the same number",HttpStatus.NOT_MODIFIED);
    }
    //call the above 'verify' api to verify new number



    @GetMapping("/viewBrandOffers")
    public ResponseEntity<?> viewBrandOffers(@RequestParam("brandID") int brandID, @RequestParam("page") int page,@RequestParam("limit") long limit)
    {
        Map<Integer,List<Offer>> offerList= userService.viewBrandOffers(brandID,page,limit);
        if(offerList==null)
        {
            return  new ResponseEntity<>("No offers ahead for this brand",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(offerList, HttpStatus.OK);
    }


    //changed from request param to path variable
    @GetMapping("/restaurant/best/offers")
    public ResponseEntity<?> getRestaurantOffers(@RequestParam("page") int page, @RequestParam("restaurantId") int restaurantId,@RequestParam("limit") long limit)
    {

        Map<Integer,List<Offer>> offers= userService.viewBestOfferOfRestaurant(page,restaurantId,limit);

        if(offers==null)
        {
            return  new ResponseEntity<>("No offers ahead",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(offers,HttpStatus.OK);
    }

    @GetMapping("/restaurant/all/offers")
    public ResponseEntity<?> getRestaurantAllOffers(@RequestParam int page, @RequestParam int restaurantId,@RequestParam("limit") long limit)
    {

        Map<Integer,List<Offer>> offers= userService.viewAllOffersOfRestaurant(page,restaurantId,limit);

        if(offers==null)
        {
            return  new ResponseEntity<>("No offers ahead",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(offers,HttpStatus.OK);
    }

    @GetMapping("/viewBestOffers")
    public ResponseEntity<?> viewBestOffers(@RequestParam("page") int page,@RequestParam("limit") long limit)
    {
        Map<Integer,List<Offer>> offerList= userService.viewBestOffers(page,limit);
        if(offerList==null)
        {
            return  new ResponseEntity<>("No offers ahead",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(offerList, HttpStatus.OK);
    }


    @GetMapping("/view/gallery")
    public ResponseEntity<?> displayGallery(@RequestParam("restaurantId") int restaurantId, @RequestParam("page") int page)
    {
        Map<Integer,List<Gallery>> gallery = userService.Gallery(restaurantId,page);

        if(gallery==null)
        {
            return new ResponseEntity<>("No photos here", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gallery, HttpStatus.OK);
    }



    //nithin

    @GetMapping("/viewBrand")
    public ResponseEntity<?> viewBrand(@RequestParam int brandId) throws IOException {
        try {
            BrandDesc brand = userService.viewBrand(brandId);
            if (brand == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(brand));
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("brand not found",HttpStatus.NOT_FOUND);

        }

    }

    //    DISPLAY MENU ITEMS
    @GetMapping("/displayMenu")
    public ResponseEntity<?> displayMenu(@RequestParam int restaurantId) {
        try {
            List<MenuItem> menuItems = userService.DisplayMenu(restaurantId);
            if (menuItems.size() <= 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(menuItems));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("There is no dish available",HttpStatus.NOT_FOUND);


        }
    }

    @GetMapping("/displayMenuItems")
    public ResponseEntity<?> displayMenuItems(@RequestParam int restaurantId) {
        try {
            MenuItems menuItems = userService.DisplayMenuItems(restaurantId);
            if (menuItems == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(menuItems));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("sorry no restaurant found",HttpStatus.NOT_FOUND);

        }
    }


    //    SEARCH ITEM BY USING DISH NAME
    @GetMapping("/searchItem")
    public ResponseEntity<?> searchItem(@RequestParam int restaurantId, @RequestParam String dishName) {
        try {
            MenuItems menuItems = userService.searchItem(restaurantId, dishName);
            if (menuItems == null) {
                return new ResponseEntity<>("There is no dish available",HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.of(Optional.of(menuItems));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("There is no restaurant",HttpStatus.NOT_FOUND);
        }
    }

    //    VIEW RESTAURANT
    //changed to request param
    @GetMapping("/viewRestaurant")
    public ResponseEntity<?> viewRestaurant(@RequestParam int restaurantId, @ModelAttribute Location start) {
        try {
            RestaurantDetails restaurantDetails = userService.viewRestaurant(restaurantId, start);
            if (restaurantDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(restaurantDetails));
        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<>("restaurant not found",HttpStatus.NOT_FOUND);
        }

    }

    //    OVERVIEW OF RESTAURANT
    @GetMapping("/overView")
    public ResponseEntity<?> overView(@RequestParam int restaurantId) {
        try {
            OverviewDetails overviewDeatails = userService.overview(restaurantId);
            if (overviewDeatails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(overviewDeatails));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("restaurant not found",HttpStatus.NOT_FOUND);

        }
    }



    //       OPENING INFORMATION FOR CURRENT DATE
    @GetMapping("/opening")
    public ResponseEntity<?> opening(@RequestParam int restaurantId) {
        try {
            OpeningDetails openingDetails = userService.opening(restaurantId);
            if (openingDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(openingDetails));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("There is no opening details for restaurant",HttpStatus.NOT_FOUND);
        }
    }


    //    OPENING INFORMATION FOR NEXT 7 DAYS FROM CURRENT DATE
    @GetMapping("/openingsFor7Days")
    public ResponseEntity<?> openingsFor7Days(@RequestParam int restaurantId) {
        try {
            List<OpeningDetails> openingDetails = userService.openingsFor7Days(restaurantId);
            if (openingDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(openingDetails));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("There is no opening details for restaurant",HttpStatus.NOT_FOUND);

        }
    }

    //SEARCH RESTAURANT BY USING DISH TYPE
    @GetMapping("/Searching")
    public ResponseEntity<?> search(@RequestParam int restaurantId, @RequestParam String dishType) {
        try {
            List<MenuDetails> menuDetails = userService.menuDetails(restaurantId, dishType, "");
            if (menuDetails.size() <= 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(menuDetails));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("There is no dish available for restaurant",HttpStatus.NOT_FOUND);
        }
    }


    //Prajwal
    //search with filter
    //changed to get
    @GetMapping("/Search")
    public ResponseEntity<?> searchRestaurants(@ModelAttribute SearchFilter searchFilter) {
        RestaurantSearchResult restaurantSearchResult = userService.searchRestaurant(searchFilter);
        if (searchFilter.getPageNumber()==1)
        {
            if(restaurantSearchResult.getTotalRocordsCount()==0)
                return new ResponseEntity<>("No Restaurants matching the search query found..",HttpStatus.NO_CONTENT);
        }

        if(restaurantSearchResult.getPerPageRecordsCount()==0)
            return new ResponseEntity<>("No Content in this page..",HttpStatus.NO_CONTENT);


        return new ResponseEntity<>(restaurantSearchResult,HttpStatus.OK);
    }


    //get nearby brands
    //changed to get
    @GetMapping("/Brands")
    public ResponseEntity<?> getNearByBrands(@ModelAttribute Location address, @RequestParam int pageNumber, @RequestParam int limit){
        NearByBrandsSearchResult nearByBrandsSearchResult = userService.getNearbyBrands(address,pageNumber,limit);

        if(pageNumber==1 || pageNumber==0){
            if(nearByBrandsSearchResult.getTotalResultsCount()==0)
                return new ResponseEntity<String>("No Brands Nearby..",HttpStatus.NO_CONTENT);
        }

        if(nearByBrandsSearchResult.getPageResultsCount()<1)
            return new ResponseEntity<String>("No Contents in the requested page..",HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(nearByBrandsSearchResult);
    }
}
