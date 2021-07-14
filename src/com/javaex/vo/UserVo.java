package com.javaex.vo;

public class UserVo {
	private String uId;
	private String pw;
	private String name;
	private String gender;

	public UserVo() {

	}

	public UserVo(String uId, String pw, String name, String gender) {
		this.uId = uId;
		this.pw = pw;
		this.name = name;
		this.gender = gender;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "UserVo [uId=" + uId + ", pw=" + pw + ", name=" + name + ", gender=" + gender + "]";
	}

}
