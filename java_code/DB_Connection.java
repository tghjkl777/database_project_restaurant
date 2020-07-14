package team12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ���ѳ�.
 * Mysql���� ������ ����ϴ� Ŭ����.
 */
class DB_Connection {

	/** �ٸ� Ŭ���������� ������ ����� �� �ֵ��� ���� ������ ����. */
	static Statement stmt;
	/** �ٸ� Ŭ���������� ������ ����� �� �ֵ��� ���� ������ ����. */
	static Connection conn;

	/**
	 * DB ���� �Լ�.
	 * 
	 * jdbc�� �̿��� mysql���� ������ �δ´�. Ʈ������� �������� �����Ѵ�. ���ῡ �����ϸ� ���� ���� ������ ����Ѵ�.
	 */
	public void connectDB() {

		/** databaseURL, username, password / �ʿ��� ���� ���� */
		String databaseURL = "jdbc:mysql://localhost:3306/team12?useSSL=false&serverTimezone=Asia/Seoul&useUnicode=true&characterEncoding=euckr";
		String user = "team12";
		String password = "team12";

		try {

			// JDBC ����̺� �ε�
			Class.forName("com.mysql.cj.jdbc.Driver");
			// DB ����
			conn = DriverManager.getConnection(databaseURL, user, password);
			// Statement ����
			stmt = conn.createStatement();

			// ���� ���� �� ���� ���� ���� ���
			if (conn != null)
				System.out.println("Connected to the database");

			conn.setAutoCommit(false); // Ʈ����� ��������

		} catch (ClassNotFoundException ex) {

			System.out.println("Could not find database driver class");
			ex.printStackTrace();

		} catch (SQLException ex) {

			System.out.println("An error occurred. Maybe user/password is invalid");
			ex.printStackTrace();

		}
	}

	/**
	 * DB ���� ���� �Լ�.
	 * 
	 * Statement, Connection �ڿ��� �ݳ��ϰ� ���� ������ ����Ѵ�.
	 */
	public void closeDB() {

		if (conn != null) {

			try {

				// Statement, Connection �ڿ� �ݳ�
				stmt.close();
				conn.close();

				// ���� ���� ���
				System.out.println("Closed Connection");

			} catch (SQLException ex) {

				ex.printStackTrace();

			}

		}

	}

}