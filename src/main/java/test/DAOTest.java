package test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.dao.ReservationDAO;
import model.dao.RestaurantDAO;
import model.dao.ReviewDAO;
import model.dao.ReviewerDAO;
import model.domain.Reservation;
import model.domain.Restaurant;
import model.domain.Review;
import model.domain.Reviewer;

public class DAOTest {

	private static RestaurantDAO restaurantDao = new RestaurantDAO();
	private static ReviewerDAO reviewerDao = new ReviewerDAO();
	private static ReservationDAO reservationDao = new ReservationDAO();
	private static ReviewDAO reviewDao = new ReviewDAO();

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		// 1. 전체 식당 목록 출력 (DB 연결 확인)
		System.out.println("===== 전체 식당 목록 =====");
		List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();
		if (restaurantList == null) {
			System.out.println("식당 목록 조회 실패 (DB 연결 또는 dbinfo.properties 확인)");
			scanner.close();
			return;
		}
		Iterator<Restaurant> restIter = restaurantList.iterator();
		while (restIter.hasNext()) {
			System.out.println(restIter.next().toString());
		}
		System.out.println();

		// 2. 식당 로그인
		System.out.print("식당 ID와 비밀번호를 입력하시오: ");
		String restaurantId = scanner.next();
		String restaurantPw = scanner.next();
		System.out.println();

		Restaurant restaurant = restaurantDao.login(restaurantId, restaurantPw);
		if (restaurant == null) {
			System.out.println("식당 로그인 실패");
			scanner.close();
			return;
		}
		System.out.println("- 로그인한 식당:");
		System.out.println(restaurant.toString());
		System.out.println();

		// 3. 리뷰어 로그인
		System.out.print("리뷰어 ID와 비밀번호를 입력하시오: ");
		String reviewerId = scanner.next();
		String reviewerPw = scanner.next();
		System.out.println();

		Reviewer reviewer = reviewerDao.login(reviewerId, reviewerPw);
		if (reviewer == null) {
			System.out.println("리뷰어 로그인 실패");
			scanner.close();
			return;
		}
		System.out.println("- 로그인한 리뷰어:");
		System.out.println(reviewer.toString());
		System.out.println();

		// 4. 리뷰어의 예약 목록 출력
		System.out.println("===== " + reviewerId + "의 예약 목록 =====");
		printReservations(reservationDao.checkReservation(reviewerId));

		// 5. 리뷰어의 리뷰 목록 출력
		System.out.println("===== " + reviewerId + "의 리뷰 목록 =====");
		printReviews(reviewDao.getReviewByReviewerId(reviewerId));

		// 6. 식당의 리뷰 목록 출력
		System.out.println("===== " + restaurantId + "의 리뷰 목록 =====");
		printReviews(reviewDao.getReviewListByRestaurant(restaurantId));

		// 7. 새 예약 등록 (로그인한 리뷰어 → 로그인한 식당)
		System.out.print("새 예약 날짜(yyyy-MM-dd)와 시간(HH:mm)을 입력하시오: ");
		LocalDate date = LocalDate.parse(scanner.next());
		LocalTime time = LocalTime.parse(scanner.next());
		System.out.println();

		Reservation newReservation = new Reservation();
		newReservation.setRestaurantId(restaurantId);
		newReservation.setReviewerId(reviewerId);
		newReservation.setReservationDate(LocalDateTime.of(date, time));
		newReservation.setReservationStatus("R");

		int result = reservationDao.insertReservation(newReservation);
		System.out.println("- 예약 등록 결과: " + (result == 1 ? "성공" : "실패"));
		System.out.println();

		// 8. 등록 후 예약 목록 다시 출력
		System.out.println("===== 등록 후 " + reviewerId + "의 예약 목록 =====");
		printReservations(reservationDao.checkReservation(reviewerId));

		scanner.close();
	}

	private static void printReservations(List<Reservation> list) {
		if (list == null || list.isEmpty()) {
			System.out.println("(없음)");
		} else {
			Iterator<Reservation> iter = list.iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next().toString());
			}
		}
		System.out.println();
	}

	private static void printReviews(List<Review> list) {
		if (list == null || list.isEmpty()) {
			System.out.println("(없음)");
		} else {
			Iterator<Review> iter = list.iterator();
			while (iter.hasNext()) {
				System.out.println(iter.next().toString());
			}
		}
		System.out.println();
	}
}