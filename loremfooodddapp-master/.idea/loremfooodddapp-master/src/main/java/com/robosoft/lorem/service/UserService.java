package com.robosoft.lorem.service;

import com.robosoft.lorem.entity.OpeningInfo;
import com.robosoft.lorem.model.*;
import com.robosoft.lorem.response.BrandList;
import com.robosoft.lorem.response.OrderDetails;
import com.robosoft.lorem.routeResponse.Location;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<?> addToFavourite(FavTable favTable);


    Map<Integer, List<BrandList>> viewPopularBrands(int pageNo, int limit);

    Map<Integer, List<BrandList>> viewAllBrands(int pageNo, int limit);

    ResponseEntity<?> addReview(ReviewInfo reviewInfo);

    Map<Integer, Object> viewReviews(Restaurant restaurant, int pageNo, int limit);

    OrderDetails getOrderDetails(Orders orders);

    boolean addCard(Card card);

    Map<Integer, List<Card>> viewCards(int pageNo, int limit);

    boolean editCard(Card card);

    String makeCardPrimary(Card card);

    boolean deleteCard(Card card);

    String makePayment(Payment payment);

    String checkOrderDetials(Payment payment, int cartId);

    boolean giveFeedback(FeedBack feedBack);

    RestaurantSearchResult searchRestaurant(SearchFilter searchFilter);

    NearByBrandsSearchResult getNearbyBrands(Location address, int pageNumber, long limit);

    int getUserIdFromEmail();


    CartModel saveOrUpdateCart(CartModel cartModel);

    boolean likeAreview(int userIdFromEmail, int reviewId);

    UserProfile getUserProfile(int userIdFromEmail);

    OrderResponseModel getMyOrdersByStatus(String orderStatus, int userIdFromEmail, int pageNumber, long limit);

    CartsResponseModel getMyCarts(int userIdFromEmail, int pageNumber, long limit);

    boolean removeCart(int userIdFromEmail, int cartId);

    CartModel getCartById(CartModel cartModel);

    List<MenuDetails> menuDetails(int restaurantId, String dishType, String dishName);

    List<MenuItem> DisplayMenu(int restaurantId);

    MenuItems DisplayMenuItems(int restaurantId);

    MenuItems searchItem(int restaurantId, String dishName);

    RestaurantDetails viewRestaurant(int restaurantId, Location start);

    OverviewDetails overview(int restaurantId);

    boolean addOpeningInfo(OpeningInfo openingInfo) throws IOException;

    OpeningDetails opening(int restaurantId);

    List<OpeningDetails> openingsFor7Days(int restaurantId);

    ResponseEntity<?> addAddress(Address address) throws IOException;

    boolean editAddress(Address address, int addressId, int userIdFromEmail);

    boolean deleteAddress(int addressId, int userIdFromEmail);

    boolean setPrimaryAddress(int addressId, int userIdFromEmail);

    List<AddressDetails> displayAddress(int userIdFromEmail);

    AddressDesc displayAddresses(int userIdFromEmail);

    OrderInfo chooseAddress(Orders orders, int userIdFromEmail, Location location,String offerId) throws IOException;

    boolean cancelOrder(Orders orders) throws IOException;

    BrandDesc viewBrand(int brandId) throws IOException;

    ResponseEntity<?> createAccount(User user);

    ResponseEntity<?> editProfile(UserEditFields user);

    int referAFriend();

    Map<Integer, String> onClickShareApp();

    Map<Integer, List<Gallery>> Gallery(int restaurantId, int page);

    Map<Integer, List<Offer>> viewBestOffers(int page,long limit);


    ResponseEntity<?> viewOfferDetails(String offerId,Location location);

    Map<Integer, List<Offer>> viewBrandOffers(int brandID, int page,long limit);

    Map<Integer, List<Offer>> viewBestOfferOfRestaurant(int page, int restaurantId,long limit);

    Map<Integer, List<Offer>> viewAllOffersOfRestaurant(int page, int restaurantId,long limit);

    ResponseEntity<?> redeem(int claimedCreditScore);

    ResponseEntity<?> applyOffer(String offerId,int cartId);

    Boolean updateOrderStatus(Orders orders);

    List<DeliveryStatus> getMyDeliveryStatus(int orderId, int userId);
}
