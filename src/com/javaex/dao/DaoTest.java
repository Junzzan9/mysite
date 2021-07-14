package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {

		UserDao uDao = new UserDao();
		UserVo uVo = new UserVo("junzzan9","1234","오준식","male");
		uDao.userInsert(uVo);
		
		
	}

}
