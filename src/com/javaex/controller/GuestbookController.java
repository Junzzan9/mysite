package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;

@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		System.out.println(action);

		if ("list".equals(action)|| null == action) {

			GuestBookDao gDao = new GuestBookDao();
			List<GuestBookVo> gbList = gDao.getPostList();

			request.setAttribute("gbList", gbList);

			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
			
			
		} else if ("add".equals(action)) {
			String name = request.getParameter("name");
			String pw = request.getParameter("pw");
			String content = request.getParameter("content");

			GuestBookDao gDao = new GuestBookDao();
			GuestBookVo gVo = new GuestBookVo(name, pw, content);

			gDao.postAdd(gVo);
			
			WebUtil.redirect(request, response, "/mysite/guest?action=list");
		}

		else if ("dform".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		}

		else if ("delete".equals(action)) {
			String dId = request.getParameter("dId");
			String dPw = request.getParameter("dPw");

			GuestBookDao guestbookDao = new GuestBookDao();

			guestbookDao.postDelete(dId, dPw);

			WebUtil.redirect(request, response, "/mysite/guest?action=list");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
