package team12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 이한나.
 * Mysql과의 연동을 담당하는 클래스.
 */
class DB_Connection {

	/** 다른 클래스에서도 연결을 사용할 수 있도록 전역 변수로 선언. */
	static Statement stmt;
	/** 다른 클래스에서도 연결을 사용할 수 있도록 전역 변수로 선언. */
	static Connection conn;

	/**
	 * DB 연결 함수.
	 * 
	 * jdbc를 이용해 mysql과의 연결을 맺는다. 트랜잭션을 수동으로 설정한다. 연결에 성공하면 연결 성공 문구를 출력한다.
	 */
	public void connectDB() {

		/** databaseURL, username, password / 필요한 변수 선언 */
		String databaseURL = "jdbc:mysql://localhost:3306/team12?useSSL=false&serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=euckr";
		String user = "team12";
		String password = "team12";

		try {

			// JDBC 드라이브 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			// DB 연결
			conn = DriverManager.getConnection(databaseURL, user, password);
			// Statement 생성
			stmt = conn.createStatement();

			// 연결 성공 시 연결 성공 문구 출력
			if (conn != null)
				System.out.println("Connected to the database");

			conn.setAutoCommit(false); // 트랜잭션 수동설정

		} catch (ClassNotFoundException ex) {

			System.out.println("Could not find database driver class");
			ex.printStackTrace();

		} catch (SQLException ex) {

			System.out.println("An error occurred. Maybe user/password is invalid");
			ex.printStackTrace();

		}
	}

	/**
	 * DB 연결 종료 함수.
	 * 
	 * Statement, Connection 자원을 반납하고 종료 문구를 출력한다.
	 */
	public void closeDB() {

		if (conn != null) {

			try {

				// Statement, Connection 자원 반납
				stmt.close();
				conn.close();

				// 종료 문구 출력
				System.out.println("Closed Connection");

			} catch (SQLException ex) {

				ex.printStackTrace();

			}

		}

	}

}