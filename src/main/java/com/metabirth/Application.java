package com.metabirth;

import com.metabirth.config.JDBCConnection;
import com.metabirth.view.InstructorView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== 아카데미 통합 시스템 =====");
            System.out.println("역할을 선택해주세요.\n");
            System.out.println("1. 학생");
            System.out.println("2. 강사");
            System.out.println("3. 관리자");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
//                case 1 -> startRoleManagement(connection);
//                case 2 -> startLessonManagement(connection);
                case 3 -> startAdminManagement(connection);
                case 0 -> {
                    connection.close();
                    System.out.println("🚀 프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("❌ 잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    private static void startAdminManagement(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== 관리자 시스템 =====");
            System.out.println("1. 학생 관리");
            System.out.println("2. 강사 관리");
            System.out.println("3. 수업 관리");
            System.out.println("4. 공지사항 관리");
            System.out.println("0. 종료");
            System.out.print("선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 개행 문자 처리

            switch (choice) {
//                case 1 -> startRoleManagement(connection);
                case 2 -> startInstructorManagement(connection);
                case 3 -> startClassManagement(connection);
                case 0 -> {
                    connection.close();
                    System.out.println("🚀 프로그램을 종료합니다.");
                    return;
                }
                default -> System.out.println("❌ 잘못된 입력입니다. 다시 선택하세요.");
            }
        }


    }

    private static void startInstructorManagement(Connection connection) {
        InstructorView instructorView = new InstructorView(connection);
        instructorView.showMenu();
    }
}
