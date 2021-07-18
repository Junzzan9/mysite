package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드

	// 생성자

	// method g/s

	// method general
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

	public int userInsert(UserVo uVo) {

		int count = -1;
		getConnection();

		try {
			String query = "";
			query += " insert into users ";
			query += " values ( seq_user_no.nextval, ? ,? ,? ,? ) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uVo.getuId());
			pstmt.setString(2, uVo.getPw());
			pstmt.setString(3, uVo.getName());
			pstmt.setString(4, uVo.getGender());

			count = pstmt.executeUpdate();
			// 4.결과처리

			System.out.println(count + "건 저장되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

	public UserVo getUser(String uId, String pw) {

		getConnection();

		UserVo uVo = new UserVo();

		try {
			String query = "";
			query += " select no,name,id from users ";
			query += " where id = ? and passward = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uId);
			pstmt.setString(2, pw);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String id = rs.getString("id");

				uVo.setNo(no);
				uVo.setName(name);
				uVo.setuId(id);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return uVo;
	}

	public UserVo getUser(int no) {

		UserVo uVo = null;

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select no, id, passward, name, gender ";
			query += " from users ";
			query += " where no = ? ";

			System.out.println(query);

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int uNo = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("passward");
				String name = rs.getString("name");
				String gender = rs.getString("gender");

				uVo = new UserVo(uNo, id, password, name, gender);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return uVo;

	}

	public int userModify(int no, String pw, String name, String gender) {

		getConnection();

		int count = -1;

		try {
			String query = "";
			query += " update users set ";
			query += " passward = ?, name = ?,gender = ? ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pw);
			pstmt.setString(2, name);
			pstmt.setString(3, gender);
			pstmt.setInt(4, no);

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}

}
