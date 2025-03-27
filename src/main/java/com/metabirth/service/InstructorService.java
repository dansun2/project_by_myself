package com.metabirth.service;

import com.metabirth.dao.InstructorDao;
import com.metabirth.model.Instructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

// 비즈니스 로직을 처리하고 dao로 넘김
public class InstructorService {

    private static final Logger log = LoggerFactory.getLogger(InstructorService.class);
    private final InstructorDao instructorDao;
    private final Connection connection;

    public InstructorService(Connection connection) {
        this.instructorDao = new InstructorDao(connection);
        this.connection = connection;
    }

    public List<Instructor> getAllInstructors() throws SQLException {

        List<Instructor> instructors = instructorDao.getAllInstructors();

        if(instructors == null) {
            log.error("조회한 사용자의 정보가 없거나 DB와 연결하는 과정에서 오류가 발생했습니다.");
            return null;
        }

        return instructors;
    }




}
