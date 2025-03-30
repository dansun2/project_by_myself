package com.metabirth.model;

import java.time.LocalDateTime;

public class Course {
    private Integer courseId;
    private String courseName;
    private String courseTime;
    private Integer courseCapacity;
    private double coursePrice;
    private boolean courseStatus;
    private LocalDateTime courseCreatedAt;
    private LocalDateTime courseUpdatedAt;
    private LocalDateTime courseDeletedAt;

    public Course(Integer courseId, String courseName, String courseTime, Integer courseCapacity, double coursePrice, boolean courseStatus, LocalDateTime courseCreatedAt, LocalDateTime courseUpdatedAt, LocalDateTime courseDeletedAt) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseTime = courseTime;
        this.courseCapacity = courseCapacity;
        this.coursePrice = coursePrice;
        this.courseStatus = courseStatus;
        this.courseCreatedAt = courseCreatedAt;
        this.courseUpdatedAt = courseUpdatedAt;
        this.courseDeletedAt = courseDeletedAt;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public Integer getCourseCapacity() {
        return courseCapacity;
    }

    public void setCourseCapacity(Integer courseCapacity) {
        this.courseCapacity = courseCapacity;
    }

    public double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public boolean isCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(boolean courseStatus) {
        this.courseStatus = courseStatus;
    }

    public LocalDateTime getCourseCreatedAt() {
        return courseCreatedAt;
    }

    public void setCourseCreatedAt(LocalDateTime courseCreatedAt) {
        this.courseCreatedAt = courseCreatedAt;
    }

    public LocalDateTime getCourseUpdatedAt() {
        return courseUpdatedAt;
    }

    public void setCourseUpdatedAt(LocalDateTime courseUpdatedAt) {
        this.courseUpdatedAt = courseUpdatedAt;
    }

    public LocalDateTime getCourseDeletedAt() {
        return courseDeletedAt;
    }

    public void setCourseDeletedAt(LocalDateTime courseDeletedAt) {
        this.courseDeletedAt = courseDeletedAt;
    }

}
