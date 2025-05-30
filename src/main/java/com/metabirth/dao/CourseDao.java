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

	// 모든 강의 정보 조회
	public List<Course> getAllCourses() throws SQLException { // 예외를 나를 호출하는 service로 던짐

		List<Course> courses = new ArrayList<>();
		String query = QueryUtil.getQuery("getAllCourses"); // XML에서 쿼리 로드

		// 여기에서의 try는 try-catch가 아니고 PreparedStatement나 ResultSet 이 자원을 자동으로 닫아주기 위함
		try (PreparedStatement ps = connection.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

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

	// 강의등록
	public boolean addCourse(Course course) throws SQLException {
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
		}
	}

	// ID로 강의 조회
	public Course findByCourseId(int courseId) throws SQLException {
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
			return course;
		}
	}

	// 강의삭제(상태만 false로 변경)
	public boolean deleteByCourseId(int courseId) throws SQLException {
		String query = QueryUtil.getQuery("deleteByCourseId");

		try(PreparedStatement deletePs = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			deletePs.setBoolean(1, false);
			deletePs.setInt(2, courseId);
			int rows = deletePs.executeUpdate();
			return rows > 0;
		}
	}

	// 강의명 변경
	public boolean updateCourseName(int courseId, String input) throws SQLException {
		String query = QueryUtil.getQuery("updateCourseName");
		try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, input);
			ps.setInt(2,courseId);
			int row = ps.executeUpdate();
			return row > 0;
		}
	}

	// 강의시간 변경
	public boolean updateCourseTime(int courseId, String input) throws SQLException {
		String query = QueryUtil.getQuery("updateCourseTime");
		try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, input);
			ps.setInt(2,courseId);
			int row = ps.executeUpdate();
			return row > 0;
		}
	}

	// 강의 수용인원 변경
	public boolean updateCourseCapacity(int courseId, String input) throws SQLException {
		String query = QueryUtil.getQuery("updateCourseCapacity");
		try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, Integer.parseInt(input));
			ps.setInt(2,courseId);
			int row = ps.executeUpdate();
			return row > 0;
		}
	}

	// 강의가격 변경
	public boolean updateCoursePrice(int courseId, String input) throws SQLException {
		String query = QueryUtil.getQuery("updateCoursePrice");
		try(PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			ps.setDouble(1, Double.parseDouble(input));
			ps.setInt(2,courseId);
			int row = ps.executeUpdate();
			return row > 0;
		} 
	}
}
