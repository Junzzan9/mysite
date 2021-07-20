package com.javaex.vo;

public class BoardVo {
	private int no;
	private String title;
	private String content;
	private int hit;
	private String date;
	private int uNo;
	private String name;

	// 게시판 list vo
	public BoardVo(int no, String title, int hit, String date, int uNo, String name) {
		super();
		this.no = no;
		this.title = title;
		this.hit = hit;
		this.date = date;
		this.uNo = uNo;
		this.name = name;
	}
	
	
	
	public BoardVo(int no, String title, String content, int hit, String date, int uNo, String name) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.date = date;
		this.uNo = uNo;
		this.name = name;
	}



	// 게시판 read vo
	public BoardVo(String title, String content, int hit, String date, int uNo, String name) {
		super();
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.date = date;
		this.uNo = uNo;
		this.name = name;
	}
	
	// 게시판 modify vo
	public BoardVo(String title, String content, int No) {
		this.title = title;
		this.content = content;
		this.no = No;
	
	}
	
	


	public BoardVo(String title, int uNo, String content) {
		super();
		this.title = title;
		this.content = content;
		this.uNo = uNo;
	}



	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getuNo() {
		return uNo;
	}

	public void setuNo(int uNo) {
		this.uNo = uNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", date=" + date
				+ ", uNo=" + uNo + "]";
	}

}
