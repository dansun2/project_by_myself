package com.metabirth.service;

import com.metabirth.dao.InstructorCourseDao;
import com.metabirth.exception.DataAccessException;
import com.metabirth.exception.InvalidInputException;
import com.metabirth.model.Course;
import com.metabirth.model.Instructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InstructorCourseService {
    private static final Logger log = LoggerFactory.getLogger(InstructorCourseService.class);
    private final InstructorCourseDao instructorCourseDao;
    private final Connection connection;

    public InstructorCourseService(Connection connection) {
        this.instructorCourseDao = new InstructorCourseDao(connection);
        this.connection = connection;
    }

    // 강의를 맡고 있지 않은 강사 조회
    public List<Instructor> getInstructorsWithoutCourses() throws DataAccessException {
        try {
            List<Instructor> instructors = instructorCourseDao.getInstructorsWithoutCourses();
            if (instructors.isEmpty()) {
                return null;
            }
            return instructors;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }
    }

    // 강사가 맡고 있는 강의 전체 조회
    public List<Course> getAllCoursesByInstructor(Integer instructorId) throws DataAccessException {
        if (instructorId == null) {
            throw new InvalidInputException("강사ID 입력값 없음");
        }
        try {
            List<Course> courses = instructorCourseDao.getAllCoursesByInstructor(instructorId);
            if (courses.isEmpty()) {
                return null;
            }
            return courses;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }
    }

    // 강의에 강사 배정
    public boolean assignCourseToInstructor(Integer instructorId, Integer courseId) throws DataAccessException {
        if (instructorId == null) {
            throw new InvalidInputException("강사ID가 입력되지 않았습니다.");
        }
        if (courseId == null) {
            throw new InvalidInputException("강의ID가 입력되지 않았습니다.");
        }
        try {
            return instructorCourseDao.assignCourseToInstructor(instructorId, courseId);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }
    }

    // 강의에 배정된 강사를 다른 강사로 변경
    public boolean reassignCourseToAnotherInstructor(Integer courseId, Integer instructorId) throws DataAccessException {
        if (instructorId == null) {
            throw new InvalidInputException("강사ID가 입력되지 않았습니다.");
        }
        if (courseId == null) {
            throw new InvalidInputException("강의ID가 입력되지 않았습니다.");
        }
        try {
            return instructorCourseDao.reassignCourseToAnotherInstructor(courseId, instructorId);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }
    }

    // 강의에 배정된 강사 취소
    public boolean unassignCourseFromInstructor(Integer courseId) throws DataAccessException {
        if (courseId == null) {
            throw new InvalidInputException("강의ID가 입력되지 않았습니다.");
        }
        try {
            return instructorCourseDao.unassignCourseFromInstructor(courseId);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }
    }
}
