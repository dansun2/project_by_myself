package com.metabirth.model;

import java.time.LocalDateTime;

public class Class {
    private Integer classId;
    private String className;
    private String classTime;
    private Integer classCapacity;
    private double classPrice;
    private boolean classStatus;
    private LocalDateTime classCreatedAt;
    private LocalDateTime classUpdatedAt;
    private LocalDateTime classDeletedAt;

    public Class(Integer classId, String className, String classTime, Integer classCapacity, double classPrice, boolean classStatus, LocalDateTime classCreatedAt, LocalDateTime classUpdatedAt, LocalDateTime classDeletedAt) {
        this.classId = classId;
        this.className = className;
        this.classTime = classTime;
        this.classCapacity = classCapacity;
        this.classPrice = classPrice;
        this.classStatus = classStatus;
        this.classCreatedAt = classCreatedAt;
        this.classUpdatedAt = classUpdatedAt;
        this.classDeletedAt = classDeletedAt;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public Integer getClassCapacity() {
        return classCapacity;
    }

    public void setClassCapacity(Integer classCapacity) {
        this.classCapacity = classCapacity;
    }

    public double getClassPrice() {
        return classPrice;
    }

    public void setClassPrice(double classPrice) {
        this.classPrice = classPrice;
    }

    public boolean isClassStatus() {
        return classStatus;
    }

    public void setClassStatus(boolean classStatus) {
        this.classStatus = classStatus;
    }

    public LocalDateTime getClassCreatedAt() {
        return classCreatedAt;
    }

    public void setClassCreatedAt(LocalDateTime classCreatedAt) {
        this.classCreatedAt = classCreatedAt;
    }

    public LocalDateTime getClassUpdatedAt() {
        return classUpdatedAt;
    }

    public void setClassUpdatedAt(LocalDateTime classUpdatedAt) {
        this.classUpdatedAt = classUpdatedAt;
    }

    public LocalDateTime getClassDeletedAt() {
        return classDeletedAt;
    }

    public void setClassDeletedAt(LocalDateTime classDeletedAt) {
        this.classDeletedAt = classDeletedAt;
    }

}
