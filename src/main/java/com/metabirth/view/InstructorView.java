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
                case 1 -> registerInstructor();
//                case 2 -> updateInstructor();
                case 3 -> getAllInstructors();
                case 4 -> findByInstructorId();
                case 5 -> deleteByInstructorId();
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

    private void findByInstructorId() {
        System.out.print("검색할 강사 ID 번호를 입력하세요 : ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();

        Instructor instructor = instructorService.findByInstructorId(instructorId);
    }

    private void registerInstructor() {
        System.out.print("등록할 강사의 이름을 입력하세요 : ");
        String name = scanner.nextLine();

        System.out.print("등록할 강사의 비밀번호를 입력하세요 : ");
        String password = scanner.nextLine();

        System.out.print("등록할 강사의 이메일을 입력하세요 : ");
        String email = scanner.nextLine();

        System.out.print("등록할 강사의 전화번호를 입력하세요 : ");
        String phone = scanner.nextLine();

        // 처음 등록할때는 활성화(true)상태로 등록함
        // 수정일, 삭제일 null값 처리 어떻게 할건지 고민
        Instructor instructor = new Instructor(1,name,email,password,phone,true, LocalDateTime.now(),null,null);

        try {
            boolean result = instructorService.registerInstructor(instructor);
            if (result) {
                System.out.println("강사 정보가 등록되었습니다.");
            } else {
                System.out.println("강사 정보 등록에 실패했습니다.");
            }
        } catch (SQLException e) {
            System.out.println("강사 정보 등록 중 오류가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    private void deleteByInstructorId() {
        System.out.print("삭제할 강사 ID 번호를 입력하세요 : ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();

        boolean result = instructorService.deleteByInstructorId(instructorId);
        if (result) {
            System.out.println("강사 정보 삭제를 완료했습니다.");
        } else {
            System.out.println("강사 정보 삭제에 실패했습니다.");
        }
    }

}
