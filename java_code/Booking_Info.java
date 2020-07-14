package team12;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * Store Booking informations.
 * @author 박수빈
 *
 */
public class Booking_Info {
	
	//restaurant ID 검색을 위한 레스토랑 이름
	String restaurant_name;
	//booking ID를 위한 year을 제외한 month, day
	String date_for_bookingID;

	// Booking Table Info.
	int booking_ID;
	int restaurant_ID;
	int customer_ID;
	Time time_of_event;
	Date date_of_event;

	// Booked Customer Table Info.
	int customerID;
	int customer_category;
	String phone_number;

	// For Connection.
	Statement stmt;
	ResultSet rs;


	
	/**
	 * 레스토랑 예약 시 알맞은 Booking_ID 및 Customer_ID 부여
	 */
	public void Create_IDs() {

		try {

			// 쿼리 결과를 받아온다.
			stmt = DB_Connection.stmt;
			rs = stmt.executeQuery("select * from DBCOURSE_Restaurants use index(restaurant_id_index)");
			while (rs.next()) {
				String Name = rs.getString(2);
				if (Name.equals(restaurant_name))
					restaurant_ID = rs.getInt(1);
				
			}
			
			// 쿼리 결과를 받아온다.
			rs = stmt.executeQuery("select count(*) from DBCOURSE_Bookings where restaurant_ID =" + restaurant_ID);
			//booking_ID 설정
			while (rs.next()) {
				String b_id = Integer.toString(rs.getInt(1) + 1);
				booking_ID = Integer.parseInt(b_id+date_for_bookingID + restaurant_ID);
			}

			
			// 쿼리 결과를 받아온다.
			rs = stmt.executeQuery("select count(*) from DBCOURSE_Bookings use index(bookings_index)");
			//customer_ID 설정
			while (rs.next()) {
				String c_id = Integer.toString(rs.getInt(1) + 1);
				int len = phone_number.length();
				try {
				customer_ID = Integer.parseInt(c_id+Character.toString(phone_number.charAt(len - 4)) + Character.toString(phone_number.charAt(len - 3))
						+ Character.toString(phone_number.charAt(len - 2)) + Character.toString(phone_number.charAt(len - 1)));
				}
				catch(Exception e ) {
					JOptionPane.showMessageDialog(null, "올바른 전화번호를 입력하세요.");
				}
			}

			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * initialize Booking related info
	 */
	public void Clear_B() {


		restaurant_name = null;
		date_for_bookingID = null;

		// Booking Table Info.
		booking_ID = 0;
		restaurant_ID = 0;
		customer_ID = 0;
		time_of_event = null;
		date_of_event = null;

		// Customer Table Info.
		customerID = 0;
		customer_category = 0;
		phone_number = null;
		
		// For Connection.
		stmt = null;
		rs = null;

	}

}