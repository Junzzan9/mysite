package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 텍스트 인코딩
		request.setCharacterEncoding("UTF-8");

		System.out.println("[UserController accessed]");

		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		System.out.println("current action=" + action);

		if ("jform".equals(action)) { // 회원가입 폼
			System.out.println("[UserController-joinForm]");
			// 포워드

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		} else if ("join".equals(action)) {
			System.out.println("[UserController-join]");
			// 파라미터 꺼내기

			String uId = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			System.out.println(uId + " " + pw + " " + name + " " + gender);
			// vo에 파라미터값 대입
			UserVo uVo = new UserVo(uId, pw, name, gender);
			System.out.println(uVo);
			// vo db에 추가
			UserDao uDao = new UserDao();
			uDao.userInsert(uVo);
			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		} else if ("loginForm".equals(action)) {
			System.out.println("[UserController-loginForm]");
			// 로그인폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		}

		else if ("login".equals(action)) {
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");

			UserDao uDao = new UserDao();
			UserVo uVo = uDao.getUser(id, pw);

			if (uVo.getName() != null) {
				// 로그인성공일때 세션에 저장
				session.setAttribute("authUser", uVo);

				WebUtil.redirect(request, response, "/mysite/main");
			} else {
				System.out.println("로그인 실패");

				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			}

		}

		else if ("logout".equals(action)) {
			// 세션이 가지고있는 authUser값 삭제
			session.removeAttribute("authUser");
			session.invalidate(); // <-- authUser(세션) 메모리삭제

			WebUtil.redirect(request, response, "/mysite/main");

		}

		else if ("modifyForm".equals(action)) {
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//로그인한 사용자 모든정보 DB에서 가져오기 위해 다오열기
			UserDao uDao = new UserDao();
			
			//불러온 모든정보 userVo에 담기
			UserVo uVo = uDao.getUser(no);
			
			request.setAttribute("uVo", uVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		}
		
		else if ("modify".equals(action)) {
			UserVo uVo = (UserVo)session.getAttribute("authUser");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			UserDao uDao = new UserDao();
			
			uDao.userModify(uVo.getNo(), pw, name, gender);
			uVo.setName(name);

			
			WebUtil.redirect(request, response, "/mysite/main");
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
