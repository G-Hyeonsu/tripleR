package model.dao;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.util.JDBCUtil;
import model.domain.Restaurant;

public class RestaurantDAO {
    private JDBCUtil jdbcUtil = null;

    public RestaurantDAO() {
        jdbcUtil = new JDBCUtil();
    }

    // 식당 회원 등록
    // : default_review_date기본 값 NULL로 등록
    public int insertRestaurant(Restaurant restaurant) {
        int result = 0;
        String sql = "INSERT INTO restaurant "
                + "(restaurant_id, password, restaurant_name, owner_name, phone, email, address, default_review_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, NULL)";

        Object[] param = new Object[] {
            restaurant.getRestaurantId(),
            restaurant.getPassword(),
            restaurant.getRestaurantName(),
            restaurant.getOwnerName(),
            restaurant.getPhone(),
            restaurant.getEmail(),
            restaurant.getAddress() };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    }

    // 식당 회원 정보 수정
    // : restaurant_id는 PK이므로 수정 불가
    public int updateRestaurant(Restaurant restaurant) {
        int result = 0;
        String sql = "UPDATE restaurant "
                + "SET restaurant_name = ?, password = ?, owner_name = ?, phone = ?, email = ?, address = ?, default_review_date = ? "
                + "WHERE restaurant_id = ?";

        Object[] param = new Object[] {
            restaurant.getRestaurantName(),
            restaurant.getPassword(),
            restaurant.getOwnerName(),
            restaurant.getPhone(),
            restaurant.getEmail(),
            restaurant.getAddress(),
            restaurant.getDefaultReviewDate(),
            restaurant.getRestaurantId() };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    }

    // 식당 회원 탈퇴
    // : ★ 식당 탈퇴 시 해당 식당의 restaurant_id를 FK로 참조하는 Reservation을 함께 삭제할지(ON DELETE CASCADE),
    //   FK만 NULL로 남겨둘지(ON DELETE SET NULL) 정해야함!
    public int deleteRestaurant(String restaurantId) {
        int result = 0;
        String sql = "DELETE FROM restaurant "
                + "WHERE restaurant_id = ?";

        Object[] param = new Object[] { restaurantId };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            result = jdbcUtil.executeUpdate();
            jdbcUtil.commit();
            return result;
        } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return result;
    }
    
    // 식당 전체 조회
    // : password 제외한 정보 조회
    public List<Restaurant> getRestaurantList() {
        String sql = "SELECT restaurant_id, restaurant_name, owner_name, phone, email, address, default_review_date "
                + "FROM restaurant";

        jdbcUtil.setSqlAndParameters(sql, null);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Restaurant> list = new ArrayList<Restaurant>();
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getString("restaurant_id"));
                restaurant.setRestaurantName(rs.getString("restaurant_name"));
                restaurant.setOwnerName(rs.getString("owner_name"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setEmail(rs.getString("email"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setDefaultReviewDate(rs.getObject("default_review_date", LocalDate.class));
                list.add(restaurant);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // ID로 특정 식당 한 곳 조회
    // : password 제외한 정보 조회
    public Restaurant findRestaurantById(String restaurantId) {
        String sql = "SELECT restaurant_id, restaurant_name, owner_name, phone, email, address, default_review_date "
                + "FROM restaurant "
                + "WHERE restaurant_id = ?";

        Object[] param = new Object[] { restaurantId };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getString("restaurant_id"));
                restaurant.setRestaurantName(rs.getString("restaurant_name"));
                restaurant.setOwnerName(rs.getString("owner_name"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setEmail(rs.getString("email"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setDefaultReviewDate(rs.getObject("default_review_date", LocalDate.class));
                return restaurant;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    

    // 식당명으로 식당 검색
    // : 식당명 부분 일치 시에도 반환
    public List<Restaurant> findRestaurantByName(String keyword) {
        String sql = "SELECT restaurant_id, restaurant_name, owner_name, phone, email, address, default_review_date "
                + "FROM restaurant "
                + "WHERE restaurant_name LIKE ?";

        Object[] param = new Object[] { "%" + keyword + "%" };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            List<Restaurant> list = new ArrayList<Restaurant>();
            while (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getString("restaurant_id"));
                restaurant.setRestaurantName(rs.getString("restaurant_name"));
                restaurant.setOwnerName(rs.getString("owner_name"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setEmail(rs.getString("email"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setDefaultReviewDate(rs.getObject("default_review_date", LocalDate.class));
                list.add(restaurant);
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // 로그인 인증 
    // : 성공 시 Restaurant, 실패 시 null 반환
    // : password는 WHERE 조건으로만 사용하고 결과로는 가져오지 않음
    public Restaurant login(String restaurantId, String password) {
        String sql = "SELECT restaurant_id, restaurant_name, owner_name, phone, email, address, default_review_date "
                + "FROM restaurant "
                + "WHERE restaurant_id = ? AND password = ?";

        Object[] param = new Object[] { restaurantId, password };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(rs.getString("restaurant_id"));
                restaurant.setRestaurantName(rs.getString("restaurant_name"));
                restaurant.setOwnerName(rs.getString("owner_name"));
                restaurant.setPhone(rs.getString("phone"));
                restaurant.setEmail(rs.getString("email"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setDefaultReviewDate(rs.getObject("default_review_date", LocalDate.class));
                return restaurant;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return null;
    }

    // ID 중복 체크 
    // : 존재하면 true, 없으면 false 반환
    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) AS count "
                + "FROM restaurant "
                + "WHERE restaurant_id = ?";

        Object[] param = new Object[] { id };

        jdbcUtil.setSqlAndParameters(sql, param);

        try {
            ResultSet rs = jdbcUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close();
        }
        return false;
    }
}