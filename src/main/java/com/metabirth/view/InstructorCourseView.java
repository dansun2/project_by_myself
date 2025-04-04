package com.metabirth.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.metabirth.exception.DataAccessException;
import com.metabirth.exception.InvalidInputException;
import com.metabirth.model.Course;
import com.metabirth.model.Instructor;
import com.metabirth.model.InstructorCourse;
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
			System.out.println("0. 뒤로가기");
			System.out.print("선택하세요: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // 개행 문자 처리

			switch (choice) {
				case 1 -> assignCourseToInstructor();
				case 2 -> getAllCoursesByInstructor();
				case 3 -> unassignCourseFromInstructor();
				case 4 -> reassignCourseToAnotherInstructor();
				case 5 -> getInstructorsWithoutCourses();
				case 0 -> {
					return;
				}
				default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
			}
		}
	}

	// 강의를 맡고 있지 않은 강사 전제 조회
	public void getInstructorsWithoutCourses() {
		try {
			List<Instructor> instructors = instructorCourseService.getInstructorsWithoutCourses();
			if (instructors.isEmpty()) {
				System.out.println("배정된 수업이 없는 강사가 없습니다.");
			} else {
				System.out.println("\n===== 배정된 수업이 없는 강사 목록 =====");
				instructors.forEach(instructorCourse -> System.out.println(instructorCourse));
			}
		} catch (DataAccessException e) {
			System.out.println("배정된 강의가 없는 강사를 조회 중 오류가 발생했습니다.");
		}
	}

	// 강사가 담당하고 있는 강의 전체 조회(ID로 상세조회하는 기능과 비슷)
	public void getAllCoursesByInstructor() {
		System.out.print("수업을 조회할 강사 ID를 입력하세요 : ");
		int instructorId = scanner.nextInt();
		scanner.nextLine();
		while (instructorId <= 0) {
			System.out.print("강사ID는 0보다 작거나 같을 수 없습니다. 다시 입력해주세요 : ");
			instructorId = scanner.nextInt();
			scanner.nextLine();
		}

		try {
			List<Course> courses = instructorCourseService.getAllCoursesByInstructor(instructorId);

			if (courses.isEmpty()) {
				System.out.println("해당 강사에게 배정된 강의가 없습니다.");
			} else {
				System.out.println("\n===== 배정된 강의 목록 =====");
				courses.forEach(course -> System.out.println(course));
			}
		} catch (DataAccessException e) {
			System.out.println("강의 목록을 조회하는 중 오류가 발생했습니다.");
		} catch (InvalidInputException e) {
			System.out.println("강사 ID를 입력받지 못했습니다.");
		}
	}

	// 강사에게 강의 배정
	public void assignCourseToInstructor() {
		System.out.print("수업을 맡길 강사 ID를 입력하세요 : ");
		int instructorId = scanner.nextInt();
		scanner.nextLine();
		while (instructorId <= 0) {
			System.out.print("강사ID는 0보다 작거나 같을 수 없습니다. 다시 입력해주세요 : ");
			instructorId = scanner.nextInt();
			scanner.nextLine();
		}

		System.out.print("해당 강사에게 배정할 수업 ID를 입력하세요 : ");
		int courseId = scanner.nextInt();
		scanner.nextLine();
		while (courseId <= 0) {
			System.out.print("강의ID는 0보다 작거나 같을 수 없습니다. 다시 입력해주세요 : ");
			courseId = scanner.nextInt();
			scanner.nextLine();
		}

		try {
			boolean result = instructorCourseService.assignCourseToInstructor(instructorId, courseId);
			if (result) {
				System.out.println("해당 강사에게 강의가 배정되었습니다.");
			} else {
				System.out.println("강의 배정에 실패했습니다.");
			}
		} catch (DataAccessException e){
			System.out.println("강의를 배정하는 중 오류가 발생했습니다.");
		} catch (InvalidInputException e) {
			System.out.println("강의ID 혹은 강사ID가 입력되지 않았습니다.");
		}
	}

	// 강의의 담당 강사를 변경
	public void reassignCourseToAnotherInstructor() {
		System.out.print("담당 강사를 변경할 강의 ID를 입력하세요 : ");
		int courseId = scanner.nextInt();
		scanner.nextLine();
		while (courseId <= 0) {
			System.out.print("강의ID는 0보다 작거나 같을 수 없습니다. 다시 입력해주세요 : ");
			courseId = scanner.nextInt();
			scanner.nextLine();
		}

		System.out.print("해당 강의를 재배정하려는 강사 ID를 입력하세요 : ");
		int instructorId = scanner.nextInt();
		scanner.nextLine();
		while (instructorId <= 0) {
			System.out.print("강사ID는 0보다 작거나 같을 수 없습니다. 다시 입력해주세요 : ");
			instructorId = scanner.nextInt();
			scanner.nextLine();
		}

		try {
			boolean result = instructorCourseService.reassignCourseToAnotherInstructor(courseId, instructorId);
			if (result) {
				System.out.println("강사 변경이 완료되었습니다.");
			} else {
				System.out.println("강사 변경에 실패했습니다.");
			}
		} catch (DataAccessException e) {
			System.out.println("담당 강사를 변경하는 중 오류가 발생했습니다.");
		} catch (InvalidInputException e) {
			System.out.println("강의ID 혹은 강사ID가 입력되지 않았습니다.");
		}
	}

	// 강의에 강사 배정을 취소는 기능
	public void unassignCourseFromInstructor() {
		System.out.print("강사 배정을 취소할 강의 ID를 입력하세요 : ");
		int courseId = scanner.nextInt();
		scanner.nextLine();
		while (courseId <= 0) {
			System.out.print("강의ID는 0보다 작거나 같을 수 없습니다. 다시 입력해주세요 : ");
			courseId = scanner.nextInt();
			scanner.nextLine();
		}

		System.out.println("정말 담당 강사 배정을 취소하시겠습니까?");
		System.out.println("1. 강사 배정 취소");
		System.out.println("2. 취소하지 않고 돌아가기");
		int num = scanner.nextInt();
		scanner.nextLine();

		try {
			switch (num) {
				case 1 -> { // 취소한다고 하면
					boolean result = instructorCourseService.unassignCourseFromInstructor(courseId);
					if (result) {
						System.out.println("해당 강의에 강사 배정이 취소되었습니다.");
					} else {
						System.out.println("해당 수업의 강사 배정 취소에 실패하였습니다.");
					}
				}
				case 2 -> {
					System.out.println("강사 배정을 취소하지 않고 이전 상태를 유지합니다.");
					return;
				}
				default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
			}
		} catch (DataAccessException e) {
			System.out.println("강사 배정을 취소하던 중 오류가 발생했습니다.");
		} catch (InvalidInputException e) {
			System.out.println("강의ID가 입력되지 않았습니다.");
		}
	}
}
