package team12;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

/**
 * Show Restaurant informations.
 * @author 김민지
 *
 */

public class Restaurant_Information extends JPanel implements ActionListener {

	ImageIcon logo_icon = new ImageIcon("images/logo.jpg");
	ImageIcon next_icon = new ImageIcon("images/NextUI.jpg");
	ImageIcon next = new ImageIcon("images/Next.jpg");
	Image logo_img = logo_icon.getImage();
	Image next_img = next_icon.getImage();
	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;
	int next_width = next_img.getWidth(this);
	int next_height = next_img.getHeight(this);
	JButton HOME = new JButton(logo_icon);
	// 데이터베이스에서 불러올 정보들에 대한 필드
	int ID;
	String R_name;
	String R_type;
	String R_info;
	String open;
	String close;
	String Star_avg;
	String Num_review;
	String[] menu = new String[2];
	String[] price = new String[2];
	String phone_num, address, web;
	JButton Reservation_B = new JButton();
	JButton Review_B = new JButton();

	Connection conn;
	Statement stmt;
	ResultSet rs, rs1, rs2, rs3;
	int exist_result = 0;
	String restaurant_name;

	Restaurant_Information() {

		this.setBackground(Color.white); // 하얀 배경
		this.setLayout(null); // 절대 위치로 배치
		// 홈 버튼 설정
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. 홈 페이지로. (Color Change)");
		HOME.setBorderPainted(false); // 버튼 경계선 없애기
		HOME.addActionListener(this);
		// 예약하기 버튼
		Reservation_B.setBounds(70, logo_height * 3 - 110, 150, 30);
		Reservation_B.setText("Reservation");
		Reservation_B.setBackground(Color.WHITE);
		Reservation_B.setToolTipText("예약하기");
		Reservation_B.addActionListener(this);
		// 리뷰쓰기 버튼
		Review_B.setBounds(270, logo_height * 3 - 110, 150, 30);
		Review_B.setText("Review");
		Review_B.setBackground(Color.WHITE);
		Review_B.setToolTipText("리뷰쓰기");
		Review_B.addActionListener(this);

		this.add(HOME);
		this.add(Reservation_B);
		this.add(Review_B);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		info_Search(restaurant_name);
		g.drawRect(105, 10, 360, 100);
		g.setColor(new Color(0, 49, 39));
		g.setFont(new Font("굴림", Font.PLAIN, 25));
		g.drawString(R_name, 130, 48);
		g.setFont(new Font("굴림", Font.PLAIN, 15));
		g.drawString(R_type, 300, 50);
		g.setColor(new Color(243, 128, 137));
		g.setFont(new Font("굴림", Font.PLAIN, 12));
		g.drawString("별점", 400, 50);
		if (Star_avg == "0")
			g.drawString("없음", 440, 50);
		else
			g.drawString(Star_avg, 430, 50);
		g.drawString("리뷰", 400, 85);
		g.drawString(Num_review + " 개", 430, 85);
		g.setColor(new Color(0, 49, 39));
		g.setFont(new Font("굴림", Font.ITALIC, 13));
		g.drawString(R_info, 130, 85);
		g.drawString("Opening Hour : " + open, 100, 150);
		g.drawString("Closing Hour : " + close, 100, 180);
		g.drawString("<대표 메뉴>", 100, 230);
		if (menu[0] != null) {
			g.drawString(menu[0], 100, 260);
			if (price[0] != null) {
				g.drawString(price[0], 250, 260);
			}
		}
		if (menu[1] != null) {
			g.drawString(menu[1], 100, 290);
			if (price[1] != null) {
				g.drawString(price[1], 250, 290);
			}
		}
		g.drawString("주소 : " + address, 100, 350);
		g.drawString("전화번호 : " + phone_num, 100, 400);
		g.drawString("웹사이트 : " + web, 100, 420);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");

		if (e.getSource() == Reservation_B) {
			Main_Frame.make_reservation.get_Info(R_name);
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "16");
		}
		if (e.getSource() == Review_B) {
			Main_Frame.review.getInfo(R_name);
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "12");
		}
	}

	/**
	 * 화면에 출력하기 위해 필요한 정보들을 불러옴
	 * @param name 레스토랑의 이름
	 */
	public void info_Search(String name) {

		try {
			stmt = DB_Connection.stmt;
			String insert = ("select * from DBCOURSE_Restaurants where Restaurant_Name='" + name + "'");
			rs = stmt.executeQuery(insert);

			// 레스토랑 테이블에 대한 정보
			while (rs.next()) {
				ID = rs.getInt(1);
				R_name = rs.getString(2);
				R_type = rs.getString(3);
				R_info = rs.getString(4);
				open = rs.getString(5);
				close = rs.getString(6);
			}

			// access테이블에 대한 정보
			String insert1 = ("select Phone_Number,Address,Website_Address from DBCOURSE_Access_Route "
					+ "where Restaurant_ID=" + ID);
			rs1 = stmt.executeQuery(insert1);
			while (rs1.next()) {
				phone_num = rs1.getString(1);
				address = rs1.getString(2);
				web = rs1.getString(3);
				if (web == null) {
					web = "정보 없음";
				}
			}

			// 음식테이블에 대한 정보
			String insert2 = ("select Representative_Food,Price from DBCOURSE_Menu where Restaurant_ID=" + ID);
			rs2 = stmt.executeQuery(insert2);
			int i = 0;
			while (rs2.next()) {
				menu[i] = rs2.getString(1);
				price[i] = rs2.getString(2);
				i++;

			}

			// 평점, 리뷰개수에 대한 정보
			rs3 = stmt.executeQuery(
					"select Star_avg, Number_Of_Reviews from DBCOURSE_Evaluation where Restaurant_ID=" + ID);
			while (rs3.next()) {
				Star_avg = rs3.getString(1);
				Num_review = rs3.getString(2);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 앞의 클래스로부터 정보를 받아오는 메소드
	 * @param name 정보를 띄울 레스토랑 이름
	 */
	public void getInfo(String name) {
		restaurant_name = name;
	}
}