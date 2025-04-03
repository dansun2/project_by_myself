package com.metabirth.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.metabirth.exception.DataAccessException;
import com.metabirth.exception.DataNotFoundException;
import com.metabirth.exception.InvalidInputException;
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

	// 강의 전체 조회
	public List<Course> getAllCourses() throws DataAccessException {
		try {
			List<Course> courses = courseDao.getAllCourses();
			if (courses == null) {
				return null;
			}
			return courses;
		} catch (SQLException e) { // DAO 에서 문제가 생겼을 때
			log.error(e.getMessage());
			throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
		}
	}

	// 강의 등록부분. 이름이 동일한 강의도 등록이 가능함
	public boolean addCourse(Course course) throws DataAccessException {
		try {
			if (course == null) {
				throw new InvalidInputException("등록할 강의정보가 존재하지 않음");
			}
			return courseDao.addCourse(course);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
		}
	}

	public Course findByCourseId(Integer courseId) throws DataAccessException {
		if (courseId == null || courseId <= 0) {
			throw new InvalidInputException("강의ID가 입력되지 않았습니다.");
		}
		try {
			Course course = courseDao.findByCourseId(courseId);
			if(course == null) {
				return null;
			}
			return course;
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
		}
	}

	public boolean deleteByCourseId(Integer courseId) throws DataAccessException {
		if (courseId == null ){
			System.out.println("강의ID가 입력되지 않았습니다.");
			return false;
		}
		try {
            return courseDao.deleteByCourseId(courseId);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
		}
	}

	public Course updateByCourseId(int choice, Integer courseId, String input) throws DataAccessException{
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
		try {
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
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
		}
		return course;
	}

}
