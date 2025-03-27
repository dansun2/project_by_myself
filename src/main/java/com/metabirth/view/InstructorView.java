package com.metabirth.view;

import com.metabirth.model.Instructor;
import com.metabirth.service.InstructorService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class InstructorView {
    private final InstructorService instructorService;
    private final Scanner scanner;

    public InstructorView(Connection connection) {
        this.instructorService = new InstructorService(connection);
        this.scanner = new Scanner(System.in);
    }

    public void showInstructorMenu() {
        while (true) {
            System.out.println("\n===== 강사 관리 시스템 =====");
            System.out.println("1. 강사 정보 등록");
            System.out.println("2. 강사 정보 수정");
            System.out.println("3. 강사 전체 조회");
            System.out.println("4. 강사 상세 조회");
            System.out.println("5. 강사 정보 삭제");
            System.out.println("0. 뒤로가기");
            System.out.print("선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
//                case 1 -> registerInstructor();
//                case 2 -> updateInstructor();
                case 3 -> getAllInstructors();
//                case 4 -> deleteInstructor();
                case 0 -> {
                    return;
                }
                default -> System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    private void getAllInstructors() {
        try {
            List<Instructor> instructors = instructorService.getAllInstructors();

            if (instructors.isEmpty()) {
                System.out.println("등록된 강사가 없습니다.");
            } else {
                System.out.println("\n===== 전체 강사 목록 =====");
                instructors.forEach(instructor -> System.out.println(instructor));
            }
        } catch (SQLException e) {
            System.out.println("강사 목록을 조회하는 중 오류가 발생했습니다.");
        }
    }


}
