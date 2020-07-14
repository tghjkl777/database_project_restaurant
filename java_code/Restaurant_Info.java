package team12;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/** Store restaurant informations. */
public class Restaurant_Info {

	/** Restaurants Table Info. */
	int R_ID = -1;
	String R_Name;
	String R_Type;
	String R_Info;
	String R_Open;
	String R_Close;

	/** Access_Route Table Info. */
	String R_Phone;
	String R_Address;
	String R_Website;

	/** Holiday Table Info. */
	ArrayList<String> R_Closed_Day = new ArrayList<>();
	String R_Holiday_Closed;

	/** Menu Table Info. */
	int M_ID1 = -1;
	String M_Food1;
	int M_Price1 = -1;
	int M_ID2 = -1;
	String M_Food2;
	int M_Price2 = -1;

	/** Ingredients Table Info. */
	String M_main1;
	String M_sub1;
	String M_vege1;
	String M_main2;
	String M_sub2;
	String M_vege2;

	/** For Connection. */
	Statement stmt;
	ResultSet rs;

	/** 새로운 레스토랑 생성시 알맞은 R_ID 부여 */
	public void Create_R() {

		try {

			/** 쿼리 결과를 받아온다. */
			stmt = DB_Connection.stmt;
			rs = stmt.executeQuery("select max(Restaurant_ID) from DBCOURSE_Restaurants");

			while (rs.next()) {
				/** Restaurant_ID : 순서대로 1, 2, 3 ... 으로 정해지므로 R_ID는 DB의 레스토랑 개수 + 1을 하면 된다. */
				R_ID = rs.getInt(1) + 1;
			}
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/** 새로운 메뉴 추가 시 알맞은 M_ID 부여 */
	public void Create_M() {

		String m1 = String.valueOf(R_ID) + "01";
		String m2 = String.valueOf(R_ID) + "02";
		M_ID1 = Integer.parseInt(m1);
		M_ID2 = Integer.parseInt(m2);

	}

	/** initial R */
	public void Clear_R() {

		/** Restaurants Table Info. */
		R_ID = -1;
		R_Name = null;
		R_Type = null;
		R_Info = null;
		R_Open = null;
		R_Close = null;

		/** Access_Route Table Info. */
		R_Phone = null;
		R_Address = null;
		R_Website = null;

		/** Holiday Table Info. */
		R_Closed_Day.clear();
		R_Holiday_Closed = null;

		/** For Connection. */
		stmt = null;
		rs = null;

	}

	/** initial M */
	public void Clear_M() {

		/** Menu Table Info. */
		M_ID1 = -1;
		M_Food1 = null;
		M_Price1 = -1;
		M_ID2 = -1;
		M_Food2 = null;
		M_Price2 = -1;

		/** Ingredients Table Info. */
		M_main1 = null;
		M_sub1 = null;
		M_vege1 = null;
		M_main2 = null;
		M_sub2 = null;
		M_vege2 = null;

		/** For Connection. */
		stmt = null;
		rs = null;

	}
}