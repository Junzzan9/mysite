package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestBookVo;

public class GuestBookDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "guestbook1";
	private String pw = "guestbook1";

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

	public int postAdd(GuestBookVo gvo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " INSERT INTO guestbook1 ";
			query += " VALUES (seq_no.nextval,?, ?, ?, sysdate) ";
			// System.out.println(query);

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, gvo.getName()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, gvo.getPw()); // ?(물음표) 중 2번째, 순서중요
			pstmt.setString(3, gvo.getContent()); // ?(물음표) 중 3번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	public int postDelete(String dId,String dPw) {
		int count = 0;
		getConnection();
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";  
	        query += " delete from guestbook1 "; 
	        query += " where no = ? ";
	        query += " and password = ? ";
		    pstmt = conn.prepareStatement(query); 
		    pstmt.setString(1,dId);
		    pstmt.setString(2,dPw);
		    System.out.println(query);


			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	public List<GuestBookVo> getPostList() {
		return getPostList("");
	}

	public List<GuestBookVo> getPostList(String keword) {
		List<GuestBookVo> postList = new ArrayList<GuestBookVo>();

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  no, ";
			query += "         name, ";
			query += "         password, ";
			query += "         content, ";
			query += "         reg_date ";
			query += " from guestbook1 ";

			if (keword != "" || keword == null) {
				query += " where name like ? ";
				query += " or no like ? ";
				query += " or password like  ? ";
				query += " or content like ? ";
				query += " or reg_date like ? ";
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기

				pstmt.setString(1, '%' + keword + '%'); // ?(물음표) 중 1번째, 순서중요
				pstmt.setString(2, '%' + keword + '%'); // ?(물음표) 중 2번째, 순서중요
				pstmt.setString(3, '%' + keword + '%'); // ?(물음표) 중 3번째, 순서중요
				pstmt.setString(4, '%' + keword + '%'); // ?(물음표) 중 4번째, 순서중요
				pstmt.setString(5, '%' + keword + '%'); // ?(물음표) 중 5번째, 순서중요
			} else {
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			}

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String pw = rs.getString("password");
				String content = rs.getString("content");
				String date = rs.getString("reg_date");

				GuestBookVo gvo = new GuestBookVo(no, name, pw, content, date);
				postList.add(gvo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return postList;

	}
	public GuestBookVo getPost(int guestbookNo) {
		getConnection();
		GuestBookVo guestbookVo = null;
		
		try {
			String query = "";
			query += " select  no, ";
			query += "         name, ";
			query += "         password, ";
			query += "         content, ";
			query += "         reg_date ";
			query += " from  guestbook1 ";
			query += " where  no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestbookNo);
		
			rs = pstmt.executeQuery();
			
			
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				guestbookVo = new GuestBookVo(no, name, password, content, regDate);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return guestbookVo;
	}
}