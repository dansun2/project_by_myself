package com.metabirth.dao;

import com.metabirth.model.Instructor;
import com.metabirth.util.QueryUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// db와 상호작용하는 클래스. connection을 주입받아서 재사용
// 강사정보를 가져오고 저장하는 기능
public class InstructorDao {
    private final Connection connection;

    public InstructorDao(Connection connection) {
        this.connection = connection;
    }

    // 모든 강사정보 조회(활성화 상태의 강사만)
    public List<Instructor> getAllInstructors() throws SQLException {
        
        List<Instructor> instructors = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllInstructors"); // XML에서 쿼리 로드

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                instructors.add(new Instructor(
                        rs.getInt("instructor_id"),
                        rs.getString("instructor_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getBoolean("status"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null,
                        rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null
                ));
            }
            return instructors;
        }
    }

    // 삭제된(비활성화) 강사 조회
    public List<Instructor> getAllDeletedInstructors() throws SQLException {

        List<Instructor> instructors = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllDeletedInstructors"); // XML에서 쿼리 로드

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                instructors.add(new Instructor(
                    rs.getInt("instructor_id"),
                    rs.getString("instructor_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getBoolean("status"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null,
                    rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null
                ));
            }
            return instructors;
        }
    }

    // 강사ID로 조회
    public Instructor findByInstructorId(int instructorId) throws SQLException {
        Instructor instructor = null; // 조회한 강사의 정보를 담을 객체 생성
        String query = QueryUtil.getQuery("findByInstructorId");

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, instructorId);
            ResultSet rs = ps.executeQuery();// 쿼리를 날려서 결과값을 rs에 담음

            if(rs.next()) {
                instructor = new Instructor(
                        rs.getInt("instructor_id"),
                        rs.getString("instructor_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getBoolean("status"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null,
                        rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null
                );
            }
            return instructor;
        }
    }

    // 강사 등록
    public boolean registerInstructor(Instructor instructor) throws SQLException {
        String query = QueryUtil.getQuery("registerInstructor");

        // Statement.RETURN_GENERATED_KEYS는 데이터가 생성된 후 자동생성된 PK값을 가져오는 옵션.
        // 값을 꺼내오려면 ResultSet타입으로 ps.getGeneratedKeys()를 가져와야함.
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, instructor.getInstructorName());
            ps.setString(2,instructor.getInstructorEmail());
            ps.setString(3,instructor.getInstructorPassword());
            ps.setString(4,instructor.getInstructorPhone());
            ps.setBoolean(5,instructor.isInstructorStatus());

            int rows = ps.executeUpdate(); // executeUpdate는 결과로 나온 행의 수를 반환
            return rows > 0; // db에 등록이 되었으면 true
        }
    }

    // 강사 이메일 수정
    public boolean updateInstructorEmail(int instructorId, String input) throws SQLException {
        String query = QueryUtil.getQuery("updateInstructorEmail");
        System.out.println(query);
        System.out.println(input);
        System.out.println(instructorId);
        try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, input);
            ps.setInt(2,instructorId);
            int row = ps.executeUpdate();
            return row > 0;
        }
    }

    // 강사 휴대폰번호 수정
    public boolean updateInstructorPhone(int instructorId, String input) throws SQLException {
        String query = QueryUtil.getQuery("updateInstructorPhone");
        try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, input);
            ps.setInt(2,instructorId);
            int row = ps.executeUpdate();
            return row > 0;
        }
    }

    // 강사이름 수정
    public boolean updateInstructorName(int instructorId, String input) throws SQLException {
        String query = QueryUtil.getQuery("updateInstructorName");
        try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, input);
            ps.setInt(2,instructorId);
            int row = ps.executeUpdate();
            return row > 0;
        }
    }

    // 강사 삭제(비활성화)
    public boolean deleteByInstructorId(int instructorId) throws SQLException {
        String query = QueryUtil.getQuery("deleteByInstructorId");

        try(PreparedStatement deletePs = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            deletePs.setBoolean(1, false);
            deletePs.setInt(2, instructorId);
            int rows = deletePs.executeUpdate();
            return rows > 0;
        }
    }
}
