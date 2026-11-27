package model.service;

import java.sql.SQLException;
import java.util.List;

import model.dao.ReviewerDAO;
import model.domain.Reviewer;

/**
 * 사용자 관리 API를 사용하는 개발자들이 직접 접근하게 되는 클래스.
 * ReviewerDAO와 CommunityDAO를 이용하여 데이터베이스 연동 및 처리 작업이 가능하도록 하며,
 * 데이터베이스의 데이터들을 이용하여 비지니스 로직을 수행하는 역할을 한다.
 * 비지니스 로직이 복잡한 경우에는 비지니스 로직만을 전담하는 클래스를 별도로 둘 수 있다.
 */
public class ReviewerManager {
    private ReviewerDAO ReviewerDAO;
    //private CommunityDAO commDAO;

    private ReviewerManager() {
        try {
            ReviewerDAO = new ReviewerDAO();
            //commDAO = new CommunityDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }           
    }
    
    //리뷰어 생성
    public int create(Reviewer Reviewer) throws SQLException/*, ExistingReviewerException*/ {
//        if (ReviewerDAO.existingReviewer(Reviewer.getReviewerId()) == true) {
//            throw new Exception(Reviewer.getReviewerId() + "는 존재하는 아이디입니다.");
//        }
        return ReviewerDAO.insertReviewer(Reviewer);
    }


    /*
     * public ReviewerDAO getReviewerDAO() { return this.ReviewerDAO; }
     */
}
