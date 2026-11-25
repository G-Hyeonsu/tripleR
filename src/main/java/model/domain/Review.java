package model.domain;

import java.time.LocalDate;

public class Review {
    private int reservationId;   
    private Integer rating;   
    private LocalDate deadline;  
    private String reviewUrl;   
    private String submitted;   
    private String reviewStatus;  // reviewStatus: P(작성대기) S(작성완료) E(기한만료)

    public Review() {
    }

    public Review(int reservationId, Integer rating, LocalDate deadline, String reviewUrl, String submitted, String reviewStatus) {
        this.reservationId = reservationId;
        this.rating = rating;
        this.deadline = deadline;
        this.reviewUrl = reviewUrl;
        this.submitted = submitted;
        this.reviewStatus = reviewStatus;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    @Override
    public String toString() {
        return "Review{reservationId=" + reservationId
                + ", rating=" + rating
                + ", deadline=" + deadline
                + ", reviewUrl='" + reviewUrl + "'"
                + ", submitted='" + submitted + "'"
                + ", reviewStatus='" + reviewStatus + "'"
                + "}";
    }
}
