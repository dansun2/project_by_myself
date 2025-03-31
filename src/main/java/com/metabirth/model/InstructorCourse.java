package com.metabirth.model;

public class InstructorCourse {
	private Integer instructorId;
	private Integer courseId;

	public InstructorCourse(Integer instructorId, Integer courseId) {
		this.instructorId = instructorId;
		this.courseId = courseId;
	}

	public Integer getInstructorId() {
		return instructorId;
	}

	public Integer getCourseId() {
		return courseId;
	}
}
