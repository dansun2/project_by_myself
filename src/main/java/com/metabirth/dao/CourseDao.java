package com.metabirth.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.metabirth.model.Course;
import com.metabirth.model.Instructor;
import com.metabirth.util.QueryUtil;

public class CourseDao {
	private final Connection connection;

	public CourseDao(Connection connection) {
		this.connection = connection;
	}

	public List<Course> getAllCourses() {

		List<Course> courses = new ArrayList<>();
		String query = QueryUtil.getQuery("getAllCourses"); // XML에서 쿼리 로드

		try (Statement stmt = connection.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
}
