package com.metabirth.dao;

import com.metabirth.model.Instructor;
import com.metabirth.util.QueryUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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




}
