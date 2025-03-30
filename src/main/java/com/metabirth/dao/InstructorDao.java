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

    public List<Instructor> getAllInstructors() {
        
        List<Instructor> instructors = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllInstructors"); // XML에서 쿼리 로드

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructors;
    }

    public Instructor findByInstructorId(int instructorId) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructor;
    }

    public boolean updateEmail(int instructorId, String input) {
        String query = QueryUtil.getQuery("updateEmail");
        System.out.println(query);
        System.out.println(input);
        System.out.println(instructorId);
        try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, input);
            ps.setInt(2,instructorId);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updatePhone(int instructorId, String input) {
        String query = QueryUtil.getQuery("updatePhone");
        try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, input);
            ps.setInt(2,instructorId);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateName(int instructorId, String input) {
        String query = QueryUtil.getQuery("updateName");
        try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, input);
            ps.setInt(2,instructorId);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteByInstructorId(int instructorId) {
        Instructor instructor = null;
        String query = QueryUtil.getQuery("deleteByInstructorId");

        try(PreparedStatement deletePs = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            deletePs.setBoolean(1, false);
            deletePs.setInt(2, instructorId);
            int rows = deletePs.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean registerInstructor(Instructor instructor) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
