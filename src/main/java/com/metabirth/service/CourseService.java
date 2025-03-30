package com.metabirth.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.metabirth.dao.CourseDao;
import com.metabirth.model.Course;
import com.metabirth.model.Instructor;

public class CourseService {
	private static final Logger log = LoggerFactory.getLogger(InstructorService.class);
	private final CourseDao courseDao;
	private final Connection connection;

	public CourseService(Connection connection) {
		this.courseDao = new CourseDao(connection);
		this.connection = connection;
	}

	public List<Course> getAllCourses() throws SQLException {

		List<Course> courses = courseDao.getAllCourses();

		if(courses == null) {
			log.error("조회한 강의 정보가 없거나 DB와 연결하는 과정에서 오류가 발생했습니다.");
			return null;
		}

		return courses;
	}

	// 강의 등록부분. 이름이 동일한 강의도 등록이 가능함
	public boolean addCourse(Course course) throws SQLException {
		return courseDao.addCourse(course);
	}
	
	public Course findByCourseId(int courseId) {
		Course course = courseDao.findByCourseId(courseId);

		if(course == null) {
			System.out.println("해당 ID의 강의가 없습니다.");
			return null;
		}
		return course;
	}
}
