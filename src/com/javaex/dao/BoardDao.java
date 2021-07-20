package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public int boardInsert(BoardVo bVo) {
		int count = 0;
		getConnection();

		try {

			String query = "";
			query += " INSERT INTO board ";
			query += " VALUES (seq_board_no.nextval,?, ?, 0, sysdate,?) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getuNo());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	public List<BoardVo> getBoardList() {
		return getBoardList("");
	}
	
	public List<BoardVo> getBoardList(String keyword) {

		getConnection();

		List<BoardVo> bList = new ArrayList<BoardVo>();
		try {
			String query = " ";

			query += " select bo.no as no, ";
			query += " bo.title as title, ";
			query += " us.name as name, ";
			query += " bo.user_no as uNo, ";
			query += " bo.hit as hit, ";
			query += " to_char(bo.reg_date,'yy-mm-dd') as rdate ";
			query += " from  users us ,  board bo ";
			query += " where us.no = bo.user_no ";

			if (keyword != "" || keyword == null) {
				
				
				query += " and bo.title like ? ";
				query += " order by bo.no desc ";
				
				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1, '%' + keyword + '%');
			}
			else {
				query += " order by bo.no desc ";
				pstmt = conn.prepareStatement(query);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String date = rs.getString("rdate");
				int uNo = rs.getInt("uNo");
				String name = rs.getString("name");

				BoardVo bVo = new BoardVo(no, title, hit, date, uNo, name);
				bList.add(bVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return bList;
	}

	public BoardVo getBoard(int no) {

		BoardVo bVo = null;
		getConnection();

		try {

			String query = "";
			query += " select bo.no, ";
			query += " us.name, bo.hit,  ";
			query += " bo.reg_date, bo.title, ";
			query += " bo.content, bo.user_no ";
			query += " from board bo,users us ";
			query += " where bo.user_no = us.no ";
			query += " and bo.no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int bno = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String date = rs.getString("reg_date");
				int uNo = rs.getInt("user_no");
				String name = rs.getString("name");

				bVo = new BoardVo(bno, title, content, hit, date, uNo, name);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return bVo;

	}

	public int boardUpdate(BoardVo bVo) {
		getConnection();
		int count = 0;
		try {
			String query = "";
			query += " update board ";
			query += " set title = ?, ";
			query += " content = ? ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	public int hitup(int no) {
		getConnection();
		int count = 0;
		try {
			String query = "";
			query += " update board ";
			query += " set hit = hit + 1 ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	

}
