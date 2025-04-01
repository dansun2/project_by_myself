package com.metabirth.dao;

import com.metabirth.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InstructorCourseDao {
    private final Connection connection;

    public InstructorCourseDao(Connection connection) {
        this.connection = connection;
    }

    public boolean assignCourseToInstructor(int instructorId, int courseId) throws SQLException {
        String query = QueryUtil.getQuery("assignCourseToInstructor");

        // 링크테이블에 각각의 id를 넣어줌
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, instructorId);
            ps.setInt(2, courseId);

            int rows = ps.executeUpdate();
            return rows > 0; // db에 등록이 되었으면 true
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
