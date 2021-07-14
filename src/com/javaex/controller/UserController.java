package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("[UserController accessed]");

		String action = request.getParameter("action");
		System.out.println("current action="+action);
		
		if ("jform".equals(action)) {   //회원가입 폼
			System.out.println("[UserController-joinForm]");
			// 포워드
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		}
		else if ("join".equals(action)) {
			System.out.println("[UserController-join]");
			//파라미터 꺼내기
			String uId = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name =  request.getParameter("name");
			String gender =  request.getParameter("gender");
			
			System.out.println(uId+" "+pw+" "+name+" "+gender);
			//vo에 파라미터값 대입
			UserVo uVo = new UserVo(uId,pw,name,gender);
			System.out.println(uVo);
			//vo db에 추가
			UserDao uDao = new UserDao();
			uDao.userInsert(uVo);
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		}
		else if ("loginForm".equals(action)) {
			System.out.println("[UserController-loginForm]");
			//로그인폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
