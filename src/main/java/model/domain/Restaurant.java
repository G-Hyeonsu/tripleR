package model.domain;

import java.time.LocalDate;

public class Restaurant {
    private String restaurantId;   
    private String password;
    private String restaurantName;
    private String ownerName;
    private String phone; 
    private String email;   
    private String address;   
    private LocalDate defaultReviewDate;   

    public Restaurant() {
    }

    public Restaurant(String restaurantId, String password, String restaurantName, String ownerName, String phone, String email, String address, LocalDate defaultReviewDate) {
        this.restaurantId = restaurantId;
        this.password = password;
        this.restaurantName = restaurantName;
        this.ownerName = ownerName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.defaultReviewDate = defaultReviewDate;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDefaultReviewDate() {
        return defaultReviewDate;
    }

    public void setDefaultReviewDate(LocalDate defaultReviewDate) {
        this.defaultReviewDate = defaultReviewDate;
    }

    @Override
    public String toString() {
        return "Restaurant{restaurantId='" + restaurantId + "'"
                + ", password='" + password + "'"
                + ", restaurantName='" + restaurantName + "'"
                + ", ownerName='" + ownerName + "'"
                + ", phone='" + phone + "'"
                + ", email='" + email + "'"
                + ", address='" + address + "'"
                + ", defaultReviewDate=" + defaultReviewDate
                + "}";
    }
}
