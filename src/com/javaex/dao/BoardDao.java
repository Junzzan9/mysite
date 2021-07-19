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

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " INSERT INTO board ";
			query += " VALUES (seq_board_no.nextval,?, ?, 0, sysdate,?) ";
			// System.out.println(query);

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, bVo.getTitle()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, bVo.getContent()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setInt(3, bVo.getuNo()); // ?(물음표) 중 3번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	public List<BoardVo> getBoardList() {

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
			query += " order by bo.no desc ";

			pstmt = conn.prepareStatement(query);

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
		getConnection();

		BoardVo bVo = null;

		try {
			String query = " ";

			query += " select us.name as name, ";
			query += " bo.hit as hit, ";
			query += " to_char(bo.reg_date,'yy-mm-dd') as rdate, ";
			query += " bo.title as title, ";
			query += " bo.content as content, ";
			query += " bo.user_no as uNo ";
			query += " from  users us ,  board bo ";
			query += " where us.no = bo.user_no ";
			query += " and bo.no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String date = rs.getString("rdate");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int uNo = rs.getInt("uNo");

				bVo = new BoardVo(title, content, hit, date, uNo, name);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return bVo;

	}
}
