package com.metabirth.model;

import java.time.LocalDateTime;

public class Instructor {
    private Integer instructorId;
    private String instructorName;
    private String instructorEmail;
    private String instructorPassword;
    private String instructorPhone;
    private boolean instructorStatus;
    private LocalDateTime instructorCreatedAt;
    private LocalDateTime instructorUpdatedAt;
    private LocalDateTime instructorDeletedAt;

    public Instructor() {
    }

    // 정보를 조회할 때 비밀번호가 출력되면 안되기 때문에 시그니처가 다른 생성자를 하나 더 만듦
    public Instructor(Integer instructorId, String instructorName, String instructorEmail, String instructorPhone, boolean instructorStatus, LocalDateTime instructorCreatedAt, LocalDateTime instructorUpdatedAt, LocalDateTime instructorDeletedAt) {
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
        this.instructorStatus = instructorStatus;
        this.instructorCreatedAt = instructorCreatedAt;
        this.instructorUpdatedAt = instructorUpdatedAt;
        this.instructorDeletedAt = instructorDeletedAt;
    }
    
    public Instructor(Integer instructorId, String instructorName, String instructorEmail, String instructorPassword, String instructorPhone, boolean instructorStatus, LocalDateTime instructorCreatedAt, LocalDateTime instructorUpdatedAt, LocalDateTime instructorDeletedAt) {
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
        return  "강사ID=" + instructorId +
                ", 강사이름='" + instructorName + '\'' +
                ", 강사이메일='" + instructorEmail + '\'' +
                ", 강사휴대폰번호='" + instructorPhone + '\'' +
                ", 강사상태=" + instructorStatus +
                ", 강사정보등록일=" + instructorCreatedAt +
                (instructorUpdatedAt != null ? ", 강사정보수정일=" + instructorUpdatedAt : " ") +
                (instructorDeletedAt != null ? ", 강사정보삭제일=" + instructorDeletedAt : " ");
    }
}
