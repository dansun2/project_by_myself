package com.metabirth.service;

import com.metabirth.dao.InstructorCourseDao;
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

    // 강의에 강사 배정
    // 만약 강사가 false 상태면 강의를 배정할 수 없음
    public boolean assignCourseToInstructor(Integer instructorId, Integer courseId) throws SQLException {
        if (instructorId == null) {
            System.out.println("강사ID가 입력되지 않았습니다.");
            return false;
        }
        if (courseId == null) {
            System.out.println("강의ID가 입력되지 않았습니다.");
            return false;
        }
        boolean result = instructorCourseDao.assignCourseToInstructor(instructorId, courseId);
        return result;
    }

    // 강사가 맡고 있는 강의 전체 조회
    public List<Course> getAllCoursesByInstructor(Integer instructorId) throws SQLException {
        if (instructorId == null) {
            System.out.println("강사ID가 입력되지 않았습니다.");
            return null;
        }
        List<Course> courses = instructorCourseDao.getAllCoursesByInstructor(instructorId);
        return courses;
    }

    // 강의에 배정된 강사 취소
    public boolean unassignCourseFromInstructor(Integer courseId) throws SQLException {
        if (courseId == null) {
            System.out.println("강의ID가 입력되지 않았습니다.");
            return false;
        }
        boolean result = instructorCourseDao.unassignCourseFromInstructor(courseId);
        return result;
    }

    // 강의에 배정된 강사를 다른 강사로 변경
    public boolean reassignCourseToAnotherInstructor(Integer courseId, Integer instructorId) throws SQLException {
        if (instructorId == null) {
            System.out.println("강사ID가 입력되지 않았습니다.");
            return false;
        }
        if (courseId == null) {
            System.out.println("강의ID가 입력되지 않았습니다.");
            return false;
        }
        // courseId와 instructorId 값이 있는지 확인
        boolean result = instructorCourseDao.reassignCourseToAnotherInstructor(courseId, instructorId);
        return result;
    }

    // 강의를 맡고 있지 않은 강사 조회
    public List<Instructor> getInstructorsWithoutCourses() throws SQLException {
        List<Instructor> instructors = instructorCourseDao.getInstructorsWithoutCourses();
        return instructors;
    }
}
