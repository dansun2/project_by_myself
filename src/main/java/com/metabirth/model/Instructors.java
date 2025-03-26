package com.metabirth.model;

import java.time.LocalDateTime;

public class Instructors {
    private Integer instructorId;
    private String instructorName;
    private String instructorEmail;
    private String instructorPassword;
    private String instructorPhone;
    private boolean instructorStatus;
    private LocalDateTime instructorCreatedAt;
    private LocalDateTime instructorUpdatedAt;
    private LocalDateTime instructorDeletedAt;

    public Instructors(Integer instructorId, String instructorName, String instructorEmail, String instructorPassword, String instructorPhone, boolean instructorStatus, LocalDateTime instructorCreatedAt, LocalDateTime instructorUpdatedAt, LocalDateTime instructorDeletedAt) {
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPassword = instructorPassword;
        this.instructorPhone = instructorPhone;
        this.instructorStatus = instructorStatus;
        this.instructorCreatedAt = instructorCreatedAt;
        this.instructorUpdatedAt = instructorUpdatedAt;
        this.instructorDeletedAt = instructorDeletedAt;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getInstructorPassword() {
        return instructorPassword;
    }

    public void setInstructorPassword(String instructorPassword) {
        this.instructorPassword = instructorPassword;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }

    public boolean isInstructorStatus() {
        return instructorStatus;
    }

    public void setInstructorStatus(boolean instructorStatus) {
        this.instructorStatus = instructorStatus;
    }

    public LocalDateTime getInstructorCreatedAt() {
        return instructorCreatedAt;
    }

    public void setInstructorCreatedAt(LocalDateTime instructorCreatedAt) {
        this.instructorCreatedAt = instructorCreatedAt;
    }

    public LocalDateTime getInstructorUpdatedAt() {
        return instructorUpdatedAt;
    }

    public void setInstructorUpdatedAt(LocalDateTime instructorUpdatedAt) {
        this.instructorUpdatedAt = instructorUpdatedAt;
    }

    public LocalDateTime getInstructorDeletedAt() {
        return instructorDeletedAt;
    }

    public void setInstructorDeletedAt(LocalDateTime instructorDeletedAt) {
        this.instructorDeletedAt = instructorDeletedAt;
    }

    @Override
    public String toString() {
        return "Instructors{" +
                "instructorId=" + instructorId +
                ", instructorName='" + instructorName + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", instructorPassword='" + instructorPassword + '\'' +
                ", instructorPhone='" + instructorPhone + '\'' +
                ", instructorStatus=" + instructorStatus +
                ", instructorCreatedAt=" + instructorCreatedAt +
                ", instructorUpdatedAt=" + instructorUpdatedAt +
                ", instructorDeletedAt=" + instructorDeletedAt +
                '}';
    }
}
