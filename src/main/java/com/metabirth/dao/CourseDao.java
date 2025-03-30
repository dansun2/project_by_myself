package com.metabirth.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.metabirth.model.Course;
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

	public boolean addCourse(Course course) {
		String query = QueryUtil.getQuery("addCourse");

		// Statement.RETURN_GENERATED_KEYS는 데이터가 생성된 후 자동생성된 PK값을 가져오는 옵션.
		// 값을 꺼내오려면 ResultSet타입으로 ps.getGeneratedKeys()를 가져와야함.
		try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, course.getCourseName());
			ps.setString(2, course.getCourseTime());
			ps.setInt(3, course.getCourseCapacity());
			ps.setDouble(4,course.getCoursePrice());
			ps.setBoolean(5,course.isCourseStatus());

			int rows = ps.executeUpdate(); // executeUpdate는 결과로 나온 행의 수를 반환
			return rows > 0; // db에 등록이 되었으면 true
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Course findByCourseId(int courseId) {
		Course course = null; // 조회한 강사의 정보를 담을 객체 생성
		String query = QueryUtil.getQuery("findByCourseId");

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, courseId);
			ResultSet rs = ps.executeQuery();// 쿼리를 날려서 결과값을 rs에 담음

			if(rs.next()) {
				course = new Course(
					rs.getInt("course_id"),
					rs.getString("course_name"),
					rs.getString("course_time"),
					rs.getInt("capacity"),
					rs.getDouble("price"),
					rs.getBoolean("status"),
					rs.getTimestamp("created_at").toLocalDateTime(),
					rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null,
					rs.getTimestamp("deleted_at") != null ? rs.getTimestamp("deleted_at").toLocalDateTime() : null
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}

	public boolean deleteByCourseId(int courseId) {
		String query = QueryUtil.getQuery("deleteByCourseId");

		try(PreparedStatement deletePs = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			deletePs.setBoolean(1, false);
			deletePs.setInt(2, courseId);
			int rows = deletePs.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean updateCourseName(int courseId, String input) {
		String query = QueryUtil.getQuery("updateCourseName");
		try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, input);
			ps.setInt(2,courseId);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean updateCourseTime(int courseId, String input) {
		String query = QueryUtil.getQuery("updateCourseTime");
		try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, input);
			ps.setInt(2,courseId);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean updateCourseCapacity(int courseId, String input) {
		String query = QueryUtil.getQuery("updateCourseCapacity");
		try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, Integer.parseInt(input));
			ps.setInt(2,courseId);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean updateCoursePrice(int courseId, String input) {
		String query = QueryUtil.getQuery("updateCoursePrice");
		try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setDouble(1, Double.parseDouble(input));
			ps.setInt(2,courseId);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
