package com.metabirth.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.metabirth.service.InstructorCourseService;

public class InstructorCourseView {
	private final InstructorCourseService instructorCourseService;
	private final Scanner scanner;

	public InstructorCourseView(Connection connection) {
		this.instructorCourseService = new InstructorCourseService(connection);
		this.scanner = new Scanner(System.in);
	}

	public void showInstructorCourseMenu() throws SQLException {
		while (true) {
			System.out.println("\n===== 강사-수업 관리 시스템 =====");
			System.out.println("1. 강사에게 수업 배정");
			System.out.println("2. 강사가 맡고 있는 수업 조회");
			System.out.println("3. 강사가 맡고 있는 수업 배정 취소");
			System.out.println("4. 강사가 맡고 있는 수업을 다른 강사에게 배정");
			System.out.println("5. 배정된 수업이 없는 강사 조회");
			System.out.println("6. 강의에 담당 강사 배정");
			System.out.println("7. 강의의 담당 강사 조회");
			System.out.println("8. 배정된 강사가 없는 수업 조회");
			System.out.println("0. 뒤로가기");
			System.out.print("선택하세요: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // 개행 문자 처리

			switch (choice) {
				case 1 -> assignCourseToInstructor();


				case 0 -> {
					return;
				}
				default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
			}
		}
	}

	public void assignCourseToInstructor() throws SQLException {
		System.out.print("수업을 맡길 강사 ID를 입력하세요 : ");
		int instructorId = scanner.nextInt();
		scanner.nextLine();

		System.out.print("해당 강사에게 배정할 수업 ID를 입력하세요 : ");
		int courseId = scanner.nextInt();
		scanner.nextLine();

		instructorCourseService.assignCourseToInstructor(instructorId, courseId);
	}

}
