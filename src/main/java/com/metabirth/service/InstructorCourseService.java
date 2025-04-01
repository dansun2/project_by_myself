package com.metabirth.service;

import com.metabirth.dao.InstructorCourseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class InstructorCourseService {
    private static final Logger log = LoggerFactory.getLogger(InstructorCourseService.class);
    private final InstructorCourseDao instructorCourseDao;
    private final Connection connection;

    public InstructorCourseService(Connection connection) {
        this.instructorCourseDao = new InstructorCourseDao(connection);
        this.connection = connection;
    }

    // 강의에 강사 배정
    public boolean assignCourseToInstructor(int instructorId, int courseId) throws SQLException {
        boolean result = instructorCourseDao.assignCourseToInstructor(instructorId, courseId);
        return result;
    }
}
