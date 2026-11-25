package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.util.JDBCUtil;
import model.domain.Reviewer;

public class ReviewerDAO {
    private JDBCUtil jdbcUtil = null;

    public ReviewerDAO() {
        jdbcUtil = new JDBCUtil();  
    }

    // 체험단 회원 등록
    public int insertReviewer(Reviewer reviewer) throws SQLException {
        int result = 0;
        String sql = "INSERT INTO reviewer "
                + "(reviewer_id, password, reviewer_name, phone, "
                + "email, birth, reviewer_score, blog_url) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] param = new Object[] {
                reviewer.getReviewerId(),
                reviewer.getPassword(),
                reviewer.getReviewerName(),
                reviewer.getPhone(),
                reviewer.getEmail(),
                reviewer.getBirth(),
                reviewer.getReviewerScore(),
                reviewer.getBlogUrl()
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

    // 체험단 회원 정보 수정
    public int updateReviewer(Reviewer reviewer) throws SQLException {
        int result = 0;
        String sql = "UPDATE reviewer "
                + "SET password=?, reviewer_name=?, phone=?, "
                + "email=?, birth=?, reviewer_score=? "
                + "WHERE reviewer_id=?";

        Object[] param = new Object[] {
                reviewer.getPassword(),
                reviewer.getReviewerName(),
                reviewer.getPhone(),
                reviewer.getEmail(),
                reviewer.getBirth(),
                reviewer.getReviewerScore(),
                reviewer.getReviewerId()
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

    // 체험단 회원 탈퇴
    public int deleteReviewer(String reviewerId) throws SQLException {
        String sql = "DELETE FROM reviewer WHERE reviewer_id=?";
        int result = 0;

        jdbcUtil.setSqlAndParameters(sql, new Object[] { reviewerId });
        
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

    // 모든 체험단 회원 조회
	public List<Reviewer> getReviewerList() {
	
	    String sql = "SELECT reviewer_id, reviewer_name, phone, "
	            + "email, birth, reviewer_score "
	            + "FROM reviewer";
	
	    jdbcUtil.setSqlAndParameters(sql, null);
	
	    try {
	        ResultSet rs = jdbcUtil.executeQuery();
	        List<Reviewer> reviewerList = new ArrayList<>();
	
	        while (rs.next()) {
	            Reviewer reviewer = mapRowToReviewer(rs);
	            reviewerList.add(reviewer);
	        }
	
	        return reviewerList;
	
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        jdbcUtil.close();
	    }
	
	    return null;
	}
	
	
	// 특정 회원 ID로 회원 조회
	public Reviewer findReviewerById(String reviewerId) {
	
	    String sql = "SELECT reviewer_id, reviewer_name, phone, "
	            + "email, birth, reviewer_score "
	            + "FROM reviewer WHERE reviewer_id=?";
	
	    jdbcUtil.setSqlAndParameters(sql, new Object[] { reviewerId });
	
	    try {
	        ResultSet rs = jdbcUtil.executeQuery();
	
	        if (rs.next()) {
	            return mapRowToReviewer(rs);
	        }
	
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        jdbcUtil.close();
	    }
	
	    return null;
	}
	
	
	// 로그인 인증 (성공 시 Reviewer, 실패 시 null 반환)
	// password는 WHERE 조건으로만 사용하고 결과로는 가져오지 않음
	public Reviewer login(String reviewerId, String password) {
	
	    String sql = "SELECT reviewer_id, reviewer_name, phone, email, "
	            + "birth, reviewer_score, blog_url "
	            + "FROM reviewer "
	            + "WHERE reviewer_id = ? AND password = ?";
	
	    Object[] param = new Object[] { reviewerId, password };
	
	    jdbcUtil.setSqlAndParameters(sql, param);
	
	    try {
	        ResultSet rs = jdbcUtil.executeQuery();
	
	        if (rs.next()) {
	
	            Reviewer reviewer = new Reviewer();
	
	            reviewer.setReviewerId(rs.getString("reviewer_id"));
	            reviewer.setReviewerName(rs.getString("reviewer_name"));
	            reviewer.setPhone(rs.getString("phone"));
	            reviewer.setEmail(rs.getString("email"));
	            reviewer.setBirth(rs.getObject("birth", LocalDate.class));
	            int score = rs.getInt("reviewer_score");
	            reviewer.setReviewerScore(rs.wasNull() ? null : score);
	            reviewer.setBlogUrl(rs.getString("blog_url"));
	
	            return reviewer;
	        }
	
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        jdbcUtil.close();
	    }
	
	    return null;
	}
	
	// ID 중복 체크 (존재하면 true, 없으면 false 반환)
	public boolean existsById(String reviewerId) {
	
	    String sql = "SELECT COUNT(*) AS count "
	            + "FROM reviewer "
	            + "WHERE reviewer_id = ?";
	
	    Object[] param = new Object[] { reviewerId };
	
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
	
   private Reviewer mapRowToReviewer(ResultSet rs) throws SQLException {
	    Reviewer reviewer = new Reviewer();
	
	    reviewer.setReviewerId(rs.getString("reviewer_id"));
	    reviewer.setReviewerName(rs.getString("reviewer_name"));
	    reviewer.setPhone(rs.getString("phone"));
	    reviewer.setEmail(rs.getString("email"));
	    reviewer.setBirth(rs.getObject("birth", LocalDate.class));
	    int score = rs.getInt("reviewer_score");
	    reviewer.setReviewerScore(rs.wasNull() ? null : score);
	
	    return reviewer;
   }
}