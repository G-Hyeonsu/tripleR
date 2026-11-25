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
    public int insertRestaurant(Restaurant restaurant) {
        int result = 0;
        String sql = "INSERT INTO restaurant "
                + "(restaurant_id, password, restaurant_name, owner_name, phone, email, address, default_review_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] param = new Object[] {
            restaurant.getRestaurantId(),
            restaurant.getPassword(),
            restaurant.getRestaurantName(),
            restaurant.getOwnerName(),
            restaurant.getPhone(),
            restaurant.getEmail(),
            restaurant.getAddress(),
            restaurant.getDefaultReviewDate() };

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

    // 식당 정보 수정 (비밀번호 제외)
    public int updateRestaurant(Restaurant restaurant) {
        int result = 0;
        String sql = "UPDATE restaurant "
                + "SET restaurant_name = ?, owner_name = ?, phone = ?, email = ?, address = ? "
                + "WHERE restaurant_id = ?";

        Object[] param = new Object[] {
            restaurant.getRestaurantName(),
            restaurant.getOwnerName(),
            restaurant.getPhone(),
            restaurant.getEmail(),
            restaurant.getAddress(),
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

    // 비밀번호 변경
    public int updateRestaurantPassword(String restaurantId, String newPassword) {
        int result = 0;
        String sql = "UPDATE restaurant "
                + "SET password = ? "
                + "WHERE restaurant_id = ?";

        Object[] param = new Object[] { newPassword, restaurantId };

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

    // 식당 삭제 (탈퇴)
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

    // ID로 식당 한 곳만 검색
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

    // 식당 전체 조회
    public List<Restaurant> getAllRestaurants() {
        String sql = "SELECT restaurant_id, restaurant_name, owner_name, phone, email, address, default_review_date "
                + "FROM restaurant "
                + "ORDER BY restaurant_id";

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

    // 이름으로 식당 검색. 부분 일치 시에도 반환
    public List<Restaurant> searchByName(String keyword) {
        String sql = "SELECT restaurant_id, restaurant_name, owner_name, phone, email, address, default_review_date "
                + "FROM restaurant "
                + "WHERE restaurant_name LIKE ? "
                + "ORDER BY restaurant_name";

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

    // 로그인 인증 (성공 시 Restaurant, 실패 시 null 반환)
    // password는 WHERE 조건으로만 사용하고 결과로는 가져오지 않음
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

    // ID 중복 체크 (존재하면 true, 없으면 false 반환)
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