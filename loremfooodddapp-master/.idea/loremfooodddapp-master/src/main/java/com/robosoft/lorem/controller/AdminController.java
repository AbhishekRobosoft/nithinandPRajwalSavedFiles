package com.robosoft.lorem.controller;
import com.cloudinary.utils.ObjectUtils;
import com.robosoft.lorem.entity.*;
import com.robosoft.lorem.model.Brand;
import com.robosoft.lorem.model.OfferRequestBody;
import com.robosoft.lorem.service.AdminService;
import com.robosoft.lorem.service.CloundinaryConfig;
import com.robosoft.lorem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value="/Admin",produces = "application/json")
public class AdminController
{
    @Autowired
    AdminService adminService;

    @Autowired
    CloundinaryConfig cloudinary;

    @Autowired
    UserService userService;

    //abhishek
    @PostMapping("/addBrand")
    public ResponseEntity<?> addBrand(@ModelAttribute Brand brand) throws IOException
    {
        try
        {
            Map uploadResult = cloudinary.upload(brand.getLogo().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            brand.setLogoLink(uploadResult.get("url").toString());
            Map uploadResult2 = cloudinary.upload(brand.getProfilePic().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            brand.setProfileLink(uploadResult2.get("url").toString());
            boolean check = adminService.addBrand(brand);
            if (check == false)
            {
                return new ResponseEntity<>("Brand cannot be added", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception exception)
        {
            try
            {
                Map uploadResult = cloudinary.upload(brand.getLogo().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                brand.setLogoLink(uploadResult.get("url").toString());
                boolean check = adminService.addBrand(brand);
                if (check == false)
                {
                    return new ResponseEntity<>("Brand cannot be added", HttpStatus.BAD_REQUEST);
                }
            }
            catch (Exception e)
            {
                try
                {
                    Map uploadResult2 = cloudinary.upload(brand.getProfilePic().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                    brand.setProfileLink(uploadResult2.get("url").toString());
                    boolean check = adminService.addBrand(brand);
                    if (check == false) {
                        return new ResponseEntity<>("Brand cannot be added", HttpStatus.BAD_REQUEST);
                    }
                }
                catch (Exception ex)
                {
                    e.printStackTrace();
                    boolean check = adminService.addBrand(brand);
                    if (check == false) {
                        return new ResponseEntity<>("Brand cannot be added", HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }
        return new ResponseEntity<>("Brand added Successfully", HttpStatus.OK);
    }

    //akrithi

    @PostMapping("/addOffers")
    public ResponseEntity<?> addOffers(@ModelAttribute OfferRequestBody offer) throws IOException {

        try
        {
            Map uploadResult2 = cloudinary.upload(offer.getPhoto().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String photo_url = uploadResult2.get("url").toString();
            offer.setPhoto_url(photo_url);
        }
        catch (Exception e)
        {
            offer.setPhoto_url(null);
        }
        return adminService.addOffers(offer);
    }


    //nithin

    //   ADDING OPENING INFORMATION
    @PostMapping("/addOpeningInfo")
    public ResponseEntity<String> addOpeningInfo(@ModelAttribute OpeningInfo openingInfo) throws Exception {
        if (userService.addOpeningInfo(openingInfo)) {
            return ResponseEntity.status(HttpStatus.OK).body("successful");

        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Task failed");

    }

    @PostMapping("/addMenu")
    public ResponseEntity<String> addMenu(@ModelAttribute Menu menu) throws Exception {
        Map uploadResult = cloudinary.upload(menu.getDishPhoto().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
        menu.setDishPhotoLink(uploadResult.get("url").toString());
        if(adminService.addMenu(menu))
            return ResponseEntity.status(HttpStatus.OK).body("Menu added");

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Task failed");

    }

    @PostMapping("/addRestaurant")
    public ResponseEntity<?> addRestaurant(@ModelAttribute Restaurant newrestaurant) throws Exception {

        try {
            Map uploadResult = cloudinary.upload(newrestaurant.getProfilePic().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            newrestaurant.setProfilePicLink(uploadResult.get("url").toString());
        }catch (Exception exception){
            newrestaurant.setProfilePicLink(null);
        }
        if(adminService.addRestaurant(newrestaurant))
            return ResponseEntity.status(HttpStatus.OK).body("Restaurant added");

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Task failed");

    }

    @PostMapping("/addDish")
    public ResponseEntity<String> addDish(@ModelAttribute Dish dish) throws Exception {
        if(adminService.addDish(dish))
        {
            return ResponseEntity.status(HttpStatus.OK).body("Dish added");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task failed");

    }

    @PostMapping("/addon")
    public ResponseEntity<String> addon(@RequestBody Addon addon) throws Exception {
        if(adminService.addon(addon))
        {
            return ResponseEntity.status(HttpStatus.OK).body("Addon added");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task failed");

    }

    //prajwal
    //assign role to a user
    @PatchMapping("/Role/{userId}/{role}")
    public ResponseEntity<?> assignRole(@PathVariable int userId,@PathVariable String role){
        if(adminService.changeRole(userId, role))
            return new ResponseEntity<>("Role Changed Successfully..",HttpStatus.OK);

        return new ResponseEntity<>("Some Error Occurred While Changing Role..",HttpStatus.EXPECTATION_FAILED);
    }

}
