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


	@Override
	public String toString() {
		return "강사ID= " + instructorId +
				", 강의ID= " + courseId;
	}
}
