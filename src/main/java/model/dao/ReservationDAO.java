package model.dao;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.util.JDBCUtil;
import model.domain.Reservation;

public class ReservationDAO {
	private JDBCUtil jdbcUtil = null;	
		
	public ReservationDAO() {				
		jdbcUtil = new JDBCUtil();		
	}

	// 예약 등록
	// : reservation_id는 시퀀스로 등록
	// : reservation_status는 기본 값 ‘R’로 등록
	public int insertReservation(Reservation reservation) {
		String sql = "INSERT INTO reservation (reservation_id, restaurant_id, reviewer_id, reservation_date, reservation_status) "
				     + "VALUES (seq_reservation.NEXTVAL, ?, ?, ?, 'R')";
		Object[] param =  new Object[] {
				reservation.getRestaurantId(),
				reservation.getReviewerId(), 
				reservation.getReservationDate() };
		
		jdbcUtil.setSqlAndParameters(sql, param);
		
		try {
			int result = jdbcUtil.executeUpdate(); 
			jdbcUtil.commit();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
    	return 0;
    } 
	
	// 리뷰어 아이디로 특정 리뷰어의 예약 내역 조회
	public List<Reservation> findReservationByReviewerId(String reviewerId) {
		String sql =  "SELECT * FROM reservation WHERE reviewer_id = ?";
		jdbcUtil.setSqlAndParameters(sql,  new Object[] {reviewerId});
		List<Reservation> list = new ArrayList<Reservation>();
		try {
			 ResultSet rs = jdbcUtil.executeQuery();
			 
			 while (rs.next()) {
	             Reservation reservation = new Reservation();

	             reservation.setReservationId(rs.getInt("reservation_id"));
	             reservation.setReservationDate(rs.getObject("reservation_date", LocalDateTime.class));
	             reservation.setReservationStatus(rs.getString("reservation_status"));
	             reservation.setReviewerId(rs.getString("reviewer_id"));
	             reservation.setRestaurantId(rs.getString("restaurant_id"));

	             list.add(reservation);
	        }

	        return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
    	return list;
    } 
	
	// 식당 아이디로 특정 식당의 예약 내역 조회
	public List<Reservation> findReservationByRestaurantId(String restaurantId) {
		String sql =  "SELECT * FROM reservation WHERE restaurant_id = ?";
		jdbcUtil.setSqlAndParameters(sql,  new Object[] {restaurantId});
		List<Reservation> list = new ArrayList<Reservation>();
		try {
			 ResultSet rs = jdbcUtil.executeQuery();
			 
			 while (rs.next()) {
	             Reservation reservation = new Reservation();

	             reservation.setReservationId(rs.getInt("reservation_id"));
	             reservation.setReservationDate(rs.getObject("reservation_date", LocalDateTime.class));
	             reservation.setReservationStatus(rs.getString("reservation_status"));
	             reservation.setReviewerId(rs.getString("reviewer_id"));
	             reservation.setRestaurantId(rs.getString("restaurant_id"));

	             list.add(reservation);
	        }

	        return list;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
    	return list;
    }
	
	// 예약 날짜 수정
	public int updateReservationDate(int reservationId, LocalDateTime reservationDate) {
		String sql = "UPDATE reservation SET reservation_date = ? WHERE reservation_id = ?";
		Object[] param = new Object[] { reservationDate, reservationId };
		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			int result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return 0;
	}
	
	// 예약 상태 수정
	public int updateReservationStatus(int reservationId, String reservationStatus) {
		String sql = "UPDATE reservation SET reservation_status = ? WHERE reservation_id = ?";
		Object[] param = new Object[] { reservationStatus, reservationId };
		jdbcUtil.setSqlAndParameters(sql, param);

		try {
			int result = jdbcUtil.executeUpdate();
			jdbcUtil.commit();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();
		}
		return 0;
	}
	
	// 예약 삭제
	// : 예약 삭제 시 해당 예약의 reservation_id를 PK로 참조하는 Review와 ReviewerScore를 함께 삭제해야함(ON DELETE CASCADE)
	public int deleteReservation(String reservationId) {
		String sql =  "DELETE FROM reservation WHERE reservation_id = ? ";   
		jdbcUtil.setSqlAndParameters(sql,  new Object[] {reservationId});
		
		try {
			int result = jdbcUtil.executeUpdate(); 
			jdbcUtil.commit();
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		
		}
    	return 0;
    }
}