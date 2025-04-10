package com.metabirth.dao;

import com.metabirth.model.Course;
import com.metabirth.model.Instructor;
import com.metabirth.util.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorCourseDao {
    private final Connection connection;
    private PreparedStatement pstmt;
    private String query;

    public InstructorCourseDao(Connection connection) {
        this.connection = connection;
    }

    // 배정된 수업이 없는 강사 조회
    public List<Instructor> getInstructorsWithoutCourses() throws SQLException {
        String query = QueryUtil.getQuery("getInstructorsWithoutCourses");
        List<Instructor> instructors = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = ps.executeQuery();

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

    // 강사가 맡고 있는 강의 전체 조회
    public List<Course> getAllCoursesByInstructor(int instructorId) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = QueryUtil.getQuery("getAllCoursesByInstructor");

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, instructorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getString("course_time"),
                        rs.getInt("capacity"),
                        rs.getDouble("price"),
                        rs.getBoolean("status"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null,
                        rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null
                ));
            }
            return courses;
        }
    }

    // 강사에게 강의 배정
    public boolean assignCourseToInstructor(int instructorId, int courseId) throws SQLException {
        String query = QueryUtil.getQuery("assignCourseToInstructor");

        // 링크테이블에 각각의 id를 넣어줌
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, instructorId);
            ps.setInt(2, courseId);
            ps.setBoolean(3, true); // 수업을 배정할 때 기본 상태는 true

            int rows = ps.executeUpdate();
            return rows > 0; // db에 등록이 되었으면 true
        }
    }

    // 다른 강사에게 강의 배정
    public boolean reassignCourseToAnotherInstructor(int courseId, int instructorId) throws SQLException {
        String query = QueryUtil.getQuery("reassignCourseToAnotherInstructor");
        try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, instructorId);
            ps.setInt(2, courseId);
            int row = ps.executeUpdate();
            return row > 0;
        }
    }

    // 강사에게 배정했던 강의 취소(담당강사가 없는 상태)
    public boolean unassignCourseFromInstructor(int courseId) throws SQLException {
        String query = QueryUtil.getQuery("unassignCourseFromInstructor");
        try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,courseId);
            int row = ps.executeUpdate();
            return row > 0;
        }
    }
}
