package com.robosoft.lorem.response;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ReviewPageResponse
{
    private int userId;
    private String firstName;
    private String lastName;
    private String profilePic;
    private int reviewCount;
    private int ratingCount;
    private int reviewId;
    private int averageRating;
    private String description;
    private List<String>photo;
    private Date date;
    private int likeCount;
}
