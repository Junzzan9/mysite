package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if("list".equals(action)||null == action) {
			System.out.println("board accessed");
			BoardDao bDao = new BoardDao();
			List<BoardVo> bList = bDao.getBoardList();
			
			request.setAttribute("bList", bList);
			//System.out.println(bList);
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
		else if("read".equals(action)) {
			System.out.println("read accessed");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bDao = new BoardDao();
			
			BoardVo bVo = bDao.getBoard(no);
			
			request.setAttribute("bVo", bVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
