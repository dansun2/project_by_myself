package com.metabirth.view;

import com.metabirth.exception.DataAccessException;
import com.metabirth.model.Instructor;
import com.metabirth.service.InstructorService;
import com.metabirth.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
            System.out.println("6. 삭제된 강사 확인");
            System.out.println("0. 뒤로가기");
            System.out.print("선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
                case 1 -> registerInstructor();
                case 2 -> updateByInstructorId();
                case 3 -> getAllInstructors();
                case 4 -> findByInstructorId();
                case 5 -> deleteByInstructorId();
                case 6 -> getAllDeletedInstructors();
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
        } catch (DataAccessException e) {
            System.out.println("강사 목록을 조회하는 중 오류가 발생했습니다.");
        }
    }

    private void getAllDeletedInstructors() {
        try {
            List<Instructor> instructors = instructorService.getAllDeletedInstructors();

            if (instructors.isEmpty()) {
                System.out.println("삭제된 강사가 없습니다.");
            } else {
                System.out.println("\n===== 삭제된 강사 목록 =====");
                instructors.forEach(instructor -> System.out.println(instructor));
            }
        } catch (DataAccessException e) {
            System.out.println("강사 목록을 조회하는 중 오류가 발생했습니다.");
        }
    }

    private void findByInstructorId() {
        System.out.print("검색할 강사 ID 번호를 입력하세요 : ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();

        try {
            Instructor instructor = instructorService.findByInstructorId(instructorId);
            if (instructor != null) {
                System.out.println("\n===== 조회된 강사 =====");
                System.out.println(instructor);
            }
        } catch (DataAccessException e) {
            System.out.println("강사를 조회하는 중 오류가 발생했습니다.");
        }
    }

    private void updateByInstructorId() {
        System.out.print("수정할 강사 ID 번호를 입력하세요 : ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();

        try {
            Instructor instructor = instructorService.findByInstructorId(instructorId);
            if (instructor != null) {
                System.out.println("\n===== 수정할 강사 정보 =====");
                System.out.println(instructor);
            }
        } catch (DataAccessException e) {
            System.out.println("강사를 조회하는중 중 오류가 발생했습니다.");
        }

        System.out.println("1. 강사이름");
        System.out.println("2. 강사휴대폰번호");
        System.out.println("3. 강사이메일");
        System.out.print("수정을 원하시는 카테고리 번호를 선택해주세요 : ");
        int choice = scanner.nextInt(); // 수정할 정보 선택
        scanner.nextLine();
        String input = ""; // 수정받을 입력값

        try {
            switch (choice) {
                case 1:
                    System.out.print("수정할 이름을 입력해주세요 : ");
                    input = scanner.nextLine();
                    break;
                case 2:
                    System.out.print("수정할 휴대폰번호를 입력해주세요 : ");
                    input = scanner.nextLine();
                    break;
                case 3:
                    System.out.print("수정할 이메일을 입력해주세요 : ");
                    input = scanner.nextLine();
                    break;
            }
            Instructor result = instructorService.updateByInstructorId(choice, instructorId, input);
            System.out.println("\n===== 수정된 강사 정보 =====");
            System.out.println(result);
        } catch (DataAccessException e) {
            System.out.println("강사를 수정하는중 중 오류가 발생했습니다.");
        }
    }

    private void registerInstructor() {
        System.out.print("등록할 강사의 이름을 입력하세요 : ");
        String name = scanner.nextLine();
        while (name.isBlank()) {
            System.out.print("강사명이 입력되지 않았습니다. 다시 입력해주세요 : ");
            name = scanner.nextLine();
        }

        System.out.print("등록할 강사의 비밀번호를 입력하세요 : ");
        String password = scanner.nextLine();
        while (password.isBlank()) {
            System.out.print("비밀번호가 입력되지 않았습니다. 다시 입력해주세요 : ");
            password = scanner.nextLine();
        }

        System.out.print("등록할 강사의 이메일을 입력하세요 : ");
        String email = scanner.nextLine();
        while (email.isBlank()) {
            System.out.print("이메일이 입력되지 않았습니다. 다시 입력해주세요 : ");
            email = scanner.nextLine();
        }

        System.out.print("등록할 강사의 전화번호를 입력하세요 : ");
        String phone = scanner.nextLine();
        while (phone.isBlank()) {
            System.out.print("전화번호가 입력되지 않았습니다. 다시 입력해주세요 : ");
            phone = scanner.nextLine();
        }

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
        } catch (DataAccessException e) {
            System.out.println("강사를 등록하는중 중 오류가 발생했습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("이미 존재하는 이메일입니다. 다시 시도해주세요.");
        }
    }
    
    private void deleteByInstructorId() {
        System.out.print("삭제할 강사 ID 번호를 입력하세요 : ");
        int instructorId = scanner.nextInt();
        scanner.nextLine();

        // 삭제할 강사 번호를 조회해서 정보를 한 번 띄워줌
        try {
            Instructor instructor = instructorService.findByInstructorId(instructorId);
            if (instructor != null) {
                System.out.println("\n===== 삭제할 강사 정보 =====");
                System.out.println(instructor);
            }
        } catch (DataAccessException e) {
            System.out.println("강사를 조회하는중 중 오류가 발생했습니다.");
        }

        try {
            while (true) {
                System.out.println("정말 해당 강사 정보를 삭제하시겠습니까?");
                System.out.println("1. 삭제");
                System.out.println("2. 취소");
                int num = scanner.nextInt();
                scanner.nextLine();

                if (num == 1) {
                    boolean result = instructorService.deleteByInstructorId(instructorId);
                    if (result) {
                        System.out.println("강사 정보 삭제를 완료했습니다.");
                    } else {
                        System.out.println("강사 정보 삭제에 실패했습니다.");
                    }
                    break;
                } else if (num == 2) {
                    System.out.println("강사 정보 삭제를 취소합니다.");
                    break;
                } else {
                    System.out.println("1 또는 2를 입력하세요.");
                }
            }
        } catch (DataAccessException e) {
            System.out.println("강사를 수정하는중 중 오류가 발생했습니다.");
        }
    }
}
