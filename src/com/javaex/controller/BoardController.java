package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String keyword =request.getParameter("keyword");
		if ("list".equals(action) || null == action) {
			System.out.println("board accessed");
			BoardDao bDao = new BoardDao();
			List<BoardVo> bList ;
			
			if(keyword != null) {
				bList = bDao.getBoardList(keyword);
			} else {
				bList = bDao.getBoardList();
			}
			
			request.setAttribute("bList", bList);
			// System.out.println(bList);
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		} else if ("read".equals(action)) {
			System.out.println("read accessed");

			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao bDao = new BoardDao();

			BoardVo bVo = bDao.getBoard(no);
			bDao.hitup(no);
			
			request.setAttribute("bVo", bVo);

			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		} else if ("mform".equals(action)) {
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bDao =new BoardDao();
			BoardVo bVo = bDao.getBoard(no);
			
			request.setAttribute("mVo", bVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}

		else if ("modify".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardDao bDao = new BoardDao();
			BoardVo bVo = new BoardVo(title,content,no);
			
			bDao.boardUpdate(bVo);
			
			WebUtil.redirect(request, response, "/mysite/board");
			
		}
		
		else if ("wform".equals(action)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}
		
		else if ("insert".equals(action)) {
			
			HttpSession session = request.getSession();
			
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardVo bVo = new BoardVo(title, no, content);
			BoardDao bDao = new BoardDao();
			
			bDao.boardInsert(bVo);
			
			WebUtil.redirect(request, response, "/mysite/board");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
