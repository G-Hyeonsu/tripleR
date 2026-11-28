package model.domain;

import java.time.LocalDate;

public class ReviewerScore {
    private int reservationId; 
    private Integer score;  
    private String reason;  
    private LocalDate createdDate;  

    public ReviewerScore() {
    }

    public ReviewerScore(int reservationId, Integer score, String reason, LocalDate createdDate) {
        this.reservationId = reservationId;
        this.score = score;
        this.reason = reason;
        this.createdDate = createdDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "ReviewerScore{reservationId=" + reservationId
                + ", score=" + score
                + ", reason='" + reason + "'"
                + ", createdDate=" + createdDate
                + "}";
    }
}
