package com.metabirth.dao;

import java.sql.Connection;

public class InstructorCourseDao {
    private final Connection connection;

    public InstructorCourseDao(Connection connection) {
        this.connection = connection;
    }
}
