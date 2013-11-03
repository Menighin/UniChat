package com.anonymous.classes;

public class User {
	private int userID;
	private String username;
	private int courseID;
	private String course;
	private String sex;
	private int university;
	
	public User(int userID, String username, int courseID, String course,
			String sex, int university) {
		super();
		this.userID = userID;
		this.username = username;
		this.courseID = courseID;
		this.course = course;
		this.sex = sex;
		this.university = university;
	}
	
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public int getUniversity() {
		return university;
	}

	public void setUniversity(int university) {
		this.university = university;
	}

	public User () {
		this.username = this.course = this.sex = "";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username
				+ ", courseID=" + courseID + ", course=" + course + ", sex="
				+ sex + ", university=" + university + "]";
	}

	
	
}