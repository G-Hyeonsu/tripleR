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

	//예약 등록
	public int insertReservation(Reservation reservation) {
		String sql = "INSERT INTO RESERVATION (RESERVATION_ID, RESERVATION_DATE, " + 
				"RESERVATION_STATUS, REVIEWER_ID, RESTAURANT_ID) VALUES (seq_reservation.NEXTVAL, ?, ?, ?, ?)";
		Object[] param =  new Object[] {
				reservation.getReservationDate(),
				reservation.getReservationStatus(), 
				reservation.getReviewerId(), 
				reservation.getRestaurantId()
			};   
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
	
	// 예약 확인
	public List<Reservation> checkReservation(String reviewerId) {
		String sql =  "SELECT * FROM Reservation WHERE REVIEWER_ID = ?";
		jdbcUtil.setSqlAndParameters(sql,  new Object[] {reviewerId});
		List<Reservation> list = new ArrayList<Reservation>();
		try {
			 ResultSet rs = jdbcUtil.executeQuery();
			 
			 while (rs.next()) {
	             Reservation reservation = new Reservation();

	             reservation.setReservationId(rs.getInt("RESERVATION_ID"));
	             reservation.setReservationDate(rs.getObject("RESERVATION_DATE", LocalDateTime.class));
	             reservation.setReservationStatus(rs.getString("RESERVATION_STATUS"));
	             reservation.setReviewerId(rs.getString("REVIEWER_ID"));
	             reservation.setRestaurantId(rs.getString("RESTAURANT_ID"));

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
	
	// 예약 수정
	public int modifyReservation(Reservation reservation) {
		String sql = "UPDATE RESERVATION SET RESERVATION_DATE = ?, RESERVATION_STATUS = ?, "+
				"REVIEWER_ID = ?, RESTAURANT_ID = ? WHERE RESERVATION_ID = ?";
		Object[] param =  new Object[] {
				reservation.getReservationDate(), 
				reservation.getReservationStatus(),
				reservation.getReviewerId(), 
				reservation.getRestaurantId(), 
				reservation.getReservationId()
				};   
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
	
	// 예약 취소 (예약 삭제 아님!)
	public int deleteReservation(int reservationId) {
	    String sql = "UPDATE RESERVATION "
	               + "SET RESERVATION_STATUS = ? "
	               + "WHERE RESERVATION_ID = ?";

	    Object[] param = new Object[] {
	        "C",
	        reservationId
	    };

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
	
	// 삭제
	public int deleteReservation(String reservationId) {
		String sql =  "DELETE FROM Reservation WHERE RESERVATION_ID = ? ";   
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