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

    // 리뷰어 회원 등록
    public int insertReviewer(Reviewer reviewer) throws SQLException {
        int result = 0;
        String sql = "INSERT INTO reviewer "
                + "(reviewer_id, password, reviewer_name, phone, "
                + "email, birth, reviewer_score, blog_url) "
                + "VALUES (?, ?, ?, ?, ?, ?, 0, ?)";

        Object[] param = new Object[] {
                reviewer.getReviewerId(),
                reviewer.getPassword(),
                reviewer.getReviewerName(),
                reviewer.getPhone(),
                reviewer.getEmail(),
                reviewer.getBirth(),
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

    // 리뷰어 회원 정보 수정
    // : reviewer_id는 PK이므로 수정 불가
    // : reviewer_score는 리뷰어 본인이 수정 불가. 식당 측에서 리뷰어 점수 등록하면 시스템 상에서 업데이트 해줌.
    public int updateReviewer(Reviewer reviewer) throws SQLException {
        int result = 0;
        String sql = "UPDATE reviewer "
                + "SET password=?, reviewer_name=?, phone=?, "
                + "email=?, birth=?, blog_url=? "
                + "WHERE reviewer_id=?";

        Object[] param = new Object[] {
                reviewer.getPassword(),
                reviewer.getReviewerName(),
                reviewer.getPhone(),
                reviewer.getEmail(),
                reviewer.getBirth(),
                reviewer.getBlogUrl(),
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

    // 리뷰어 회원 탈퇴
    // : ★ 리뷰어가 탈퇴 시 해당 리뷰어의 reviewer_id를 FK로 참조하는 Reservation을 함께 삭제할지(ON DELETE CASCADE),
    //     FK만 NULL로 남겨둘지(ON DELETE SET NULL) 정해야함!
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

    // 모든 리뷰어 회원 조회
    // : password 제외한 정보 조회
	public List<Reviewer> getReviewerList() {
	
	    String sql = "SELECT reviewer_id, reviewer_name, phone, "
	            + "email, birth, reviewer_score, blog_url "
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
	
	
	// ID로 특정 리뷰어 1명 조회
	// : password 제외한 정보 조회
	public Reviewer findReviewerById(String reviewerId) {
	
	    String sql = "SELECT reviewer_id, reviewer_name, phone, "
	            + "email, birth, reviewer_score, blog_url "
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
	
	
	// 로그인 인증
	// : 성공 시 Reviewer, 실패 시 null 반환
	// : password는 WHERE 조건으로만 사용하고 결과로는 가져오지 않음
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
	
	// ID 중복 체크
	// : 존재하면 true, 없으면 false 반환
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
	    reviewer.setReviewerScore(rs.getInt("reviewer_score"));
	    reviewer.setBlogUrl(rs.getString("blog_url"));
	    
	    return reviewer;
   }
}