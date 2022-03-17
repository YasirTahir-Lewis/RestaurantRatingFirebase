package edu.lewisu.cs.yasirtahir.restaurantrating;

public class RestaurantRating {
    private String restaurantName;
    private String comments;
    private String cuisineType;
    private int rating;
    private String likedType;

    public RestaurantRating() {
        restaurantName="";
        comments="";
        cuisineType="";
        rating=0;
        likedType="";
    }

    public RestaurantRating(String restaurantName, String comments, String cuisineType, int rating, String likedType) {
        this.restaurantName = restaurantName;
        this.comments = comments;
        this.cuisineType = cuisineType;
        this.rating = rating;
        this.likedType = likedType;
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getLikedType() {
        return likedType;
    }

    public void setLikedType(String likedType) {
        this.likedType = likedType;
    }

    @Override
    public String toString() {
        return "Restaurant name=" + restaurantName + "\n" +
                "Comments=" + comments + "\n" +
                "Cuisine type=" + cuisineType + "\n" +
                "rating=" + rating + "\n" +
                "Liked the most=" + likedType;
    }
}
