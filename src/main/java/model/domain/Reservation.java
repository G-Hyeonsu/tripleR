package model.domain;

import java.time.LocalDateTime;

public class Reservation {
    private int reservationId;   
    private String restaurantId;  
    private String reviewerId;   
    private LocalDateTime reservationDate;  
    private String reservationStatus;  // reservationStatus: R(예약요청) A(승인) D(거절) V(방문완료) C(취소)

    public Reservation() {
    }

    public Reservation(int reservationId, String restaurantId, String reviewerId, LocalDateTime reservationDate, String reservationStatus) {
        this.reservationId = reservationId;
        this.restaurantId = restaurantId;
        this.reviewerId = reviewerId;
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public String toString() {
        return "Reservation{reservationId=" + reservationId
                + ", restaurantId='" + restaurantId + "'"
                + ", reviewerId='" + reviewerId + "'"
                + ", reservationDate=" + reservationDate
                + ", reservationStatus='" + reservationStatus + "'"
                + "}";
    }
}