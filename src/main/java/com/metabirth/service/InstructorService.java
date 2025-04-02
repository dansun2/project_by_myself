package com.metabirth.service;

import com.metabirth.dao.InstructorDao;
import com.metabirth.exception.DataAccessException;
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

    public List<Instructor> getAllInstructors() throws DataAccessException {
        try {
            List<Instructor> instructors = instructorDao.getAllInstructors();
            if(instructors == null) {
                return null;
            }
            return instructors;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }
    }

    public List<Instructor> getAllDeletedInstructors() throws DataAccessException {
        try {
            List<Instructor> instructors = instructorDao.getAllDeletedInstructors();
            if(instructors == null) {
                return null;
            }
            return instructors;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }
    }

    public Instructor findByInstructorId(Integer instructorId) throws DataAccessException {
        if (instructorId == null) {
            System.out.println("강사ID가 입력되지 않았습니다.");
            return null;
        }
        try {
            Instructor instructor = instructorDao.findByInstructorId(instructorId);
            if(instructor == null) {
                return null;
            }
            return instructor;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }
    }

    public boolean registerInstructor(Instructor instructor) throws DataAccessException {
        if (instructor == null) {
            System.out.println("등록할 강사의 정보가 입력되지 않았습니다.");
            return false;
        }
        try {
            List<Instructor> existingInstructor = getAllInstructors(); // 강사 정보 전체를 가져와서 list에 담음
            for (Instructor i : existingInstructor) {
                if (i.getInstructorEmail().equals(instructor.getInstructorEmail())) { // 전체 강사중에 중복되는 email있는지 확인
                    throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
                }
            }
            return instructorDao.registerInstructor(instructor);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }
    }

    public Instructor updateByInstructorId(int choice, Integer instructorId, String input) throws DataAccessException{
        if (instructorId == null) {
            System.out.println("강사ID가 입력되지 않았습니다.");
            return null;
        }
        if (input == null || input.equals("")) {
            System.out.println("수정할 값이 입력되지 않았습니다.");
            return null;
        }
        // 1번이면 이름 수정, 2번이면 휴대폰수정, 3번이면 이메일수정
        Instructor instructor = null;

        try {
            switch (choice) {
                case 1 -> { // 이름 수정 (중복처리 필요 x)
                    boolean result = instructorDao.updateInstructorName(instructorId, input);

                    if (result) {
                        System.out.println("수정이 완료되었습니다.");
                        instructor = instructorDao.findByInstructorId(instructorId);
                        return instructor;
                    }
                }
                case 2 -> { // 휴대폰 수정 (중복처리 필요x)
                    boolean result = instructorDao.updateInstructorPhone(instructorId, input);

                    if (result) {
                        System.out.println("수정이 완료되었습니다.");
                        instructor = instructorDao.findByInstructorId(instructorId);
                        return instructor;
                    }
                }
                case 3 -> {// 이메일수정 (중복처리 필요)
                    List<Instructor> existingInstructor = getAllInstructors(); // 강사정보를 가져와서 넣을 변수

                    for (Instructor i : existingInstructor) {
                        if (i.getInstructorEmail().equals(input)) {
                            throw new IllegalArgumentException("이미 존재하는 이메일입니다."); // 익셉션 날리지 말고 다시 입력받을 수 있게 하자
                        }
                    }
                    boolean result = instructorDao.updateInstructorEmail(instructorId, input);

                    if (result) {
                        System.out.println("수정이 완료되었습니다.");
                        instructor = instructorDao.findByInstructorId(instructorId);
                        return instructor;
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }


        if(instructor == null) {
            System.out.println("해당 ID의 강사가 없습니다.");
            return null;
        }
        return instructor;
    }

    public boolean deleteByInstructorId(Integer instructorId) throws DataAccessException {
        if (instructorId == null) {
            System.out.println("강사ID가 입력되지 않았습니다.");
            return false;
        }
        try {
            return instructorDao.deleteByInstructorId(instructorId);
        }catch (SQLException e) {
            log.error(e.getMessage());
            throw new DataAccessException("DB와의 연결 과정에서 오류가 발생");
        }

    }
}
