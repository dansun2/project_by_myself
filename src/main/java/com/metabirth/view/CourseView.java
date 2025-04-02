package com.metabirth.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import com.metabirth.exception.DataAccessException;
import com.metabirth.exception.DataNotFoundException;
import com.metabirth.exception.InvalidInputException;
import com.metabirth.model.Course;
import com.metabirth.service.CourseService;

public class CourseView {
	private final CourseService courseService;
	private final Scanner scanner;

	public CourseView(Connection connection) {
		this.courseService = new CourseService(connection);
		this.scanner = new Scanner(System.in);
	}

	public void showCourseMenu() {
		while (true) {
			System.out.println("\n===== 수업 관리 시스템 =====");
			System.out.println("1. 강의 등록");
			System.out.println("2. 강의 수정");
			System.out.println("3. 강의 전체 조회");
			System.out.println("4. 강의 상세 조회");
			System.out.println("5. 강의 삭제");
			System.out.println("0. 뒤로가기");
			System.out.print("선택하세요: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // 개행 문자 처리

			switch (choice) {
				case 1 -> addCourse();
				case 2 -> updateByCourseId();
				case 3 -> getAllCourses();
				case 4 -> findByCourseId();
				case 5 -> deleteByCourseId();
				case 0 -> {
					return;
				}
				default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
			}
		}
	}

	private void getAllCourses() {
		try {
			List<Course> courses = courseService.getAllCourses();

			if (courses == null || courses.isEmpty()) {
				System.out.println("등록된 강의가 없습니다.");
			} else {
				System.out.println("\n===== 전체 강의 목록 =====");
				courses.forEach(course -> System.out.println(course));
			}
		} catch (DataAccessException e){
			System.out.println("강의 목록을 조회하는 중 오류가 발생했습니다.");
		}
    }

	private void addCourse() {
		System.out.print("등록할 강의명을 입력하세요 : ");
		String name = scanner.nextLine();
		while (name.isBlank()) {
			System.out.print("강의명이 입력되지 않았습니다. 다시 입력해주세요 : ");
			name = scanner.nextLine();
		}

		System.out.print("등록할 강의의 시간을 입력하세요 : ");
		String time = scanner.nextLine();
		while (time.isBlank()) {
			System.out.print("강의 시간이 입력되지 않았습니다. 다시 입력해주세요 : ");
			time = scanner.nextLine();
		}

		System.out.print("등록할 강의의 수용인원을 입력하세요 : ");
		int capacity = scanner.nextInt();
		scanner.nextLine();
		while (capacity <= 0) {
			System.out.print("강의 수용인원이 0보다 작거나 같습니다. 다시 입력해주세요 : ");
			capacity = scanner.nextInt();
			scanner.nextLine();
		}

		System.out.print("등록할 강의의 가격을 입력하세요 : ");
		double price = scanner.nextInt();
		while (price <= 0) {
			System.out.print("강의 가격이 0보다 작거나 같습니다. 다시 입력해주세요 : ");
			price = scanner.nextInt();
			scanner.nextLine();
		}

		// 처음 등록할때는 활성화(true)상태로 등록함
		// 수정일, 삭제일 null값은 toString 메서드 삼항연산자로 안보이게 해주기
		Course course = new Course(1,name,time,capacity,price,true, LocalDateTime.now(),null,null);

		try {
			boolean result = courseService.addCourse(course);
			if (result) {
				System.out.println("강의 정보가 등록되었습니다.");
			} else {
				System.out.println("강의 정보 등록에 실패했습니다.");
			}
		} catch (DataAccessException e) {
			System.out.println("강의 정보를 등록하는 중에 오류가 발생했습니다.");
		} catch (InvalidInputException e) {
			System.out.println("등록할 강의 정보가 없습니다. 다시 시도해주세요.");
		}
	}

	private void findByCourseId() {
		System.out.print("검색할 강의 ID 번호를 입력하세요 : ");
		int courseId = scanner.nextInt();
		scanner.nextLine();

		// 강의ID가 제대로 입력되지 않았을 때 다시 입력받음
		while (courseId <= 0) {
			System.out.print("강의ID는 0보다 작거나 같을 수 없습니다. 다시 입력해주세요 : ");
			courseId = scanner.nextInt();
			scanner.nextLine();
		}

		try {
			Course course = courseService.findByCourseId(courseId);

			if (course == null) {
				System.out.println("검색한 ID를 가진 강의가 존재하지 않습니다.");
			} else {
				System.out.println("\n===== 조회된 강의 =====");
				System.out.println(course);
			}
		} catch (DataAccessException e) { // if (course == null) 을 service에서 체크해서 throws를 날려주기 때문에 여기서 콘솔찍어줌
			System.out.println("강의를 조회하던 중에 오류가 발생했습니다.");
		}
	}

	private void updateByCourseId() {
		System.out.print("수정할 강의 ID 번호를 입력하세요 : ");
		int courseId = scanner.nextInt();
		scanner.nextLine();

		while (courseId <= 0) {
			System.out.print("강의ID는 0보다 작거나 같을 수 없습니다. 다시 입력해주세요 : ");
			courseId = scanner.nextInt();
			scanner.nextLine();
		}

		try {
			Course course = courseService.findByCourseId(courseId);
			System.out.println("\n===== 수정할 강의 정보 =====");
			System.out.println(course);
		} catch (DataAccessException e) {
			System.out.println("강의를 조회하던 중에 오류가 발생했습니다.");
		}

		System.out.println("1. 강의명");
		System.out.println("2. 강의 시간");
		System.out.println("3. 강의 수용인원");
		System.out.println("4. 강의 가격");
		System.out.print("수정을 원하시는 카테고리 번호를 선택해주세요 : ");
		int choice = scanner.nextInt(); // 수정할 정보 선택
		scanner.nextLine();
		String input = ""; // 수정받을 입력값

		try{
			switch (choice) {
				case 1:
					System.out.print("수정할 강의명을 입력해주세요 : ");
					input = scanner.nextLine();
					break;
				case 2:
					System.out.print("수정할 강의 시간을 입력해주세요 : ");
					input = scanner.nextLine();
					break;
				case 3:
					System.out.print("수정할 강의 수용인원을 입력해주세요 : ");
					input = scanner.nextLine();
					break;
				case 4:
					System.out.print("수정할 강의 가격을 입력해주세요 : ");
					input = scanner.nextLine();
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 선택하세요.");
					return;
			}

			Course course = courseService.updateByCourseId(choice, courseId, input);
			System.out.println("\n===== 수정된 강의 정보 =====");
			System.out.println(course);
		} catch (DataAccessException e) {
			System.out.println("강의를 조회하던 중에 오류가 발생했습니다.");
		}
	}

	private void deleteByCourseId() {
		System.out.print("삭제할 강의 ID 번호를 입력하세요 : ");
		int courseId = scanner.nextInt();
		scanner.nextLine();

		while (courseId <= 0) {
			System.out.print("강의ID는 0보다 작거나 같을 수 없습니다. 다시 입력해주세요 : ");
			courseId = scanner.nextInt();
			scanner.nextLine();
		}

		// 삭제할 강의 번호를 조회해서 정보를 한 번 띄워줌
		try {
			Course course = courseService.findByCourseId(courseId);
			System.out.println("\n===== 삭제할 강의 정보 =====");
			System.out.println(course);
		} catch (DataAccessException e) {
			System.out.println("해당 강의를 가져오던 중 오류가 발생했습니다.");
		}

		while (true) {
			System.out.println("정말 해당 강의 정보를 삭제하시겠습니까?");
			System.out.println("1. 삭제");
			System.out.println("2. 취소");
			int num = scanner.nextInt();
			scanner.nextLine();

			try {
				if (num == 1) {
					boolean result = courseService.deleteByCourseId(courseId);
					if (result) {
						System.out.println("강의 정보 삭제를 완료했습니다.");
					} else {
						System.out.println("강의 정보 삭제에 실패했습니다.");
					}
					break;
				} else if (num == 2) {
					System.out.println("강의 정보 삭제를 취소합니다.");
					break;
				} else {
					System.out.println("1 또는 2를 입력하세요.");
				}
			} catch (DataAccessException e) {
				System.out.println("해당 강의를 가져오던 중 오류가 발생했습니다.");
			}


		}
	}
}
