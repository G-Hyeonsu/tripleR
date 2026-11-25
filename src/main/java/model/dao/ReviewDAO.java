package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.util.JDBCUtil;
import model.domain.Review;

public class ReviewDAO {
    private JDBCUtil jdbcUtil = null;

    public ReviewDAO() {
        jdbcUtil = new JDBCUtil();
    }

    public int insertReview(Review review) throws SQLException {
        int result = 0;
        String sql = "INSERT INTO review "
                + "(reservation_id, rating, deadline, review_url, submitted, review_status) "
                + "VALUES (?, NULL, ?, NULL, 'N', 'P')";

        Object[] param = new Object[] {
                review.getReservationId(),
                toSqlDate(review.getDeadline())
        };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    }

    // 리뷰 마감 기한일 수정 (식당 측에서 실행)
    public int updateDeadline(Review review) throws SQLException {
        int result = 0;
        String sql = "UPDATE review "
                + "SET deadline = ? "
                + "WHERE reservation_id = ?";

        Object[] param = new Object[] {
            toSqlDate(review.getDeadline()),
            review.getReservationId()
        };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    } 

    // 리뷰 링크와 별점 등록 (리뷰어 측에서 실행) 
    public int submitReview(int reservationId, String reviewUrl, int rating) throws SQLException {
        int result = 0;
        String sql = "UPDATE review "
                + "SET review_url=?, rating=?, submitted='Y', review_status = CASE WHEN TRUNC(SYSDATE) <= TRUNC(deadline) "
                + "THEN 'S' ELSE 'E' END "
                + "WHERE reservation_id=? AND submitted='N'";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { reviewUrl, rating, reservationId });

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    }

    // 리뷰 링크 수정 (리뷰어 측에서 실행)
    public int updateUrl(Review review) throws SQLException {
        int result = 0;
        String sql = "UPDATE review "
                + "SET review_url = ? "
                + "WHERE reservation_id = ? ";

        Object[] param = new Object[] {
                review.getReviewUrl(),
                review.getReservationId()
        };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    }

    // 리뷰 삭제
    public int deleteReview(int reservationId) throws SQLException {
        int result = 0;
        String sql = "DELETE FROM review WHERE reservation_id=?";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { reservationId });

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    }

    // 식당이 리뷰어 점수 +1
    public int addReviewerScore(int reservationId, String restaurantId) throws SQLException {
        return giveReviewerScore(reservationId, restaurantId, 1, "'S'");
    }

    // 식당이 리뷰어 점수 -1 
    public int decreaseReviewerScore(int reservationId, String restaurantId) throws SQLException {
        return giveReviewerScore(reservationId, restaurantId, -1, "'E'");
    }

    // reviewerscore 기록 + reviewer.reviewer_score 누적을 하나의 트랜잭션으로 처리
    // 조건(이 식당의 예약, 리뷰 상태 일치, 예약당 1번)을 만족하지 않으면 0 반환
    private int giveReviewerScore(int reservationId, String restaurantId, int score, String statusList) {
        String sql = "BEGIN "
                + "INSERT INTO reviewerscore (reservation_id, score, reason, created_date) "
                + "SELECT rs.reservation_id, ?, NULL, SYSDATE "
                + "FROM reservation rs JOIN review rv ON rs.reservation_id = rv.reservation_id "
                + "WHERE rs.reservation_id = ? AND rs.restaurant_id = ? "
                + "AND rv.review_status IN (" + statusList + "); "
                + "IF SQL%ROWCOUNT = 0 THEN "
                + "RAISE_APPLICATION_ERROR(-20001, '점수 부여 조건 불충족'); "
                + "END IF; "
                + "UPDATE reviewer SET reviewer_score = NVL(reviewer_score, 0) + ? "
                + "WHERE reviewer_id = (SELECT reviewer_id FROM reservation WHERE reservation_id = ?); "
                + "END;";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { score, reservationId, restaurantId, score, reservationId });

        try {
            jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            return 1;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return 0;
    }

    // 모든 리뷰 리스트 반환
    public List<Review> getReviewList() {
        String sql = "SELECT * FROM review";

        jdbcUtil.setSqlAndParameters(sql, null);

        try {
            ResultSet rs = jdbcUtil.executeQuery();

            List<Review> reviewList = new ArrayList<>();

            while (rs.next()) {
                Review review = mapRowToReview(rs);
                reviewList.add(review);
            }
            return reviewList;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // 리뷰어의 id로 리뷰를 불러오기
    public List<Review> getReviewByReviewerId(String reviewer_id) {
        String sql =  "SELECT rv.* FROM review rv "
                + "JOIN reservation rs ON rv.reservation_id = rs.reservation_id "
                + "WHERE rs.reviewer_id = ?";
        jdbcUtil.setSqlAndParameters(sql, new Object[] { reviewer_id });

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Review> reviewList = new ArrayList<>();

            while (rs.next()) {
                Review review = mapRowToReview(rs);
                reviewList.add(review);

            }
            return reviewList;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // 식당별 리뷰 리스트 불러오기
    public List<Review> getReviewListByRestaurant(String restaurantId) {
        String sql = "SELECT rv.* FROM review rv "
                + "JOIN reservation rs ON rv.reservation_id = rs.reservation_id "
                + "WHERE rs.restaurant_id=? ";

        jdbcUtil.setSqlAndParameters(sql, new Object[] { restaurantId });

        try {
            ResultSet rs = jdbcUtil.executeQuery();

            List<Review> reviewList = new ArrayList<>();

            while (rs.next()) {
                Review review = mapRowToReview(rs);
                reviewList.add(review);
            }

            return reviewList;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    private java.sql.Date toSqlDate(LocalDate date) {
        return (date == null) ? null : java.sql.Date.valueOf(date);
    }

    private Review mapRowToReview(ResultSet rs) throws SQLException {
        Review review = new Review();

        review.setReservationId(rs.getInt("reservation_id"));
        review.setReviewUrl(rs.getString("review_url"));
        review.setDeadline(rs.getObject("deadline", LocalDate.class));
        int rating = rs.getInt("rating");
        review.setRating(rs.wasNull() ? null : rating);  
        review.setSubmitted(rs.getString("submitted"));
        review.setReviewStatus(rs.getString("review_status"));
        return review;
    }
}