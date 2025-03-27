package com.metabirth.service;

import com.metabirth.dao.InstructorDao;
import com.metabirth.model.Instructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

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

    public Instructor findByInstructorId(int instructorId) {
        Instructor instructor = instructorDao.findByInstructorId(instructorId);

        if(instructor == null) {
            System.out.println("해당 ID의 강사가 없습니다.");
            return null;
        }
        return instructor;
    }

    public boolean deleteByInstructorId(int instructorId) {
        return instructorDao.deleteByInstructorId(instructorId); // view에서 입력받은 id 가져와서 일단 조회
    }

    public boolean registerInstructor(Instructor instructor) throws SQLException {
        
        List<Instructor> existingInstructor = getAllInstructors(); // 강사 정보 전체를 가져와서 list에 담음
        for (Instructor i : existingInstructor) {
            if (i.getInstructorEmail().equals(instructor.getInstructorEmail())) { // 전체 강사중에 중복되는 email있는지 확인
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            }
        }
        return instructorDao.registerInstructor(instructor);
    }
}
