package model.domain;

import java.time.LocalDate;

public class Reviewer {
    private String reviewerId;   
    private String password;
    private String reviewerName;
    private String phone; 
    private String email;   
    private LocalDate birth;  
    private Integer reviewerScore;  
    private String blogUrl;  

    public Reviewer() {
    }

    public Reviewer(String reviewerId, String password, String reviewerName, String phone, String email, LocalDate birth, Integer reviewerScore, String blogUrl) {
        this.reviewerId = reviewerId;
        this.password = password;
        this.reviewerName = reviewerName;
        this.phone = phone;
        this.email = email;
        this.birth = birth;
        this.reviewerScore = reviewerScore;
        this.blogUrl = blogUrl;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
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

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Integer getReviewerScore() {
        return reviewerScore;
    }

    public void setReviewerScore(Integer reviewerScore) {
        this.reviewerScore = reviewerScore;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    @Override
    public String toString() {
        return "Reviewer{reviewerId='" + reviewerId + "'"
                + ", password='" + password + "'"
                + ", reviewerName='" + reviewerName + "'"
                + ", phone='" + phone + "'"
                + ", email='" + email + "'"
                + ", birth=" + birth
                + ", reviewerScore=" + reviewerScore
                + ", blogUrl='" + blogUrl + "'"
                + "}";
    }
}
