package com.metabirth.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.metabirth.dao.CourseDao;
import com.metabirth.model.Course;

public class CourseService {
	private static final Logger log = LoggerFactory.getLogger(CourseService.class);
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
		if (course == null) {
			System.out.println("등록할 강의정보가 입력되지 않았습니다.");
			return false;
		}
		return courseDao.addCourse(course);
	}
	
	public Course findByCourseId(Integer courseId) {
		if (courseId == null ){
			System.out.println("강의ID가 입력되지 않았습니다.");
			return null;
		}
		Course course = courseDao.findByCourseId(courseId);

		if(course == null) {
			System.out.println("해당 ID의 강의가 없습니다.");
			return null;
		}
		return course;
	}

	public boolean deleteByCourseId(Integer courseId) {
		if (courseId == null ){
			System.out.println("강의ID가 입력되지 않았습니다.");
			return false;
		}
		return courseDao.deleteByCourseId(courseId); // view에서 입력받은 id 가져와서 일단 조회
	}

	public Course updateByCourseId(int choice, Integer courseId, String input) {
		if (courseId == null ){
			System.out.println("강의ID가 입력되지 않았습니다.");
			return null;
		}
		if (input == null || input.equals("")) {
			System.out.println("수정할 강의 정보가 입력되지 않았습니다.");
			return null;
		}

		// 1번이면 이름 수정, 2번이면 휴대폰수정, 3번이면 이메일수정
		Course course = null;
		switch (choice) {
			case 1 -> { // 강의명 수정 (중복처리 필요 x)
				boolean result = courseDao.updateCourseName(courseId, input);
				if (result) {
					System.out.println("수정이 완료되었습니다.");
					course = courseDao.findByCourseId(courseId);
					return course;
				}
			}
			case 2 -> { // 강의시간 수정
				boolean result = courseDao.updateCourseTime(courseId, input);
				if (result) {
					System.out.println("수정이 완료되었습니다.");
					course = courseDao.findByCourseId(courseId);
					return course;
				}
			}
			case 3 -> {// 수용인원 수정
				boolean result = courseDao.updateCourseCapacity(courseId, input);
				if (result) {
					System.out.println("수정이 완료되었습니다.");
					course = courseDao.findByCourseId(courseId);
					return course;
				}
			}
			case 4 -> { // 강의가격 수정
				boolean result = courseDao.updateCoursePrice(courseId, input);
				if (result) {
					System.out.println("수정이 완료되었습니다.");
					course = courseDao.findByCourseId(courseId);
					return course;
				}
			}
		}
		if(course == null) {
			System.out.println("해당 ID의 강사가 없습니다.");
			return null;
		}
		return course;
	}

}
