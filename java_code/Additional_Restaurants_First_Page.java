package team12;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

/**
 * @author 이한나.
 * Additional_Restaurants_First_Page를 구성하는 JPanel.
 * 레스토랑 전반에 대한 정보를 받아 database상에 insert한다.
 */
public class Additional_Restaurants_First_Page extends JPanel implements ActionListener {

	// 필요 아이콘, 내용물, 변수 선언
	ImageIcon logo_icon = Main_Frame.home_page.logo_icon;
	ImageIcon next_icon = Main_Frame.home_page.next_icon;
	ImageIcon next = Main_Frame.home_page.next;
	Image logo_img = logo_icon.getImage();
	Image next_img = next_icon.getImage();
	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;
	int next_width = next_img.getWidth(this);
	int next_height = next_img.getHeight(this);
	JButton HOME = new JButton(logo_icon);
	JButton NEXT = new JButton(next);
	String[] hour = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
			"18", "19", "20", "21", "22", "23" };
	String[] minute = { "00", "10", "20", "30", "40", "50" };
	JComboBox Open_hour = new JComboBox(hour);
	JComboBox Open_minute = new JComboBox(minute);
	JComboBox Close_hour = new JComboBox(hour);
	JComboBox Close_minute = new JComboBox(minute);
	JCheckBox Mon = new JCheckBox();
	JCheckBox Tue = new JCheckBox();
	JCheckBox Wed = new JCheckBox();
	JCheckBox Thu = new JCheckBox();
	JCheckBox Fri = new JCheckBox();
	JCheckBox Sat = new JCheckBox();
	JCheckBox Sun = new JCheckBox();
	ButtonGroup group = new ButtonGroup();
	JRadioButton Y = new JRadioButton();
	JRadioButton N = new JRadioButton();
	JTextField R_name = new JTextField();
	JTextField R_type = new JTextField();
	JTextField R_info = new JTextField();
	JTextField R_phone = new JTextField();
	JTextField R_address = new JTextField();
	JTextField R_website = new JTextField();
	// 레스토랑 정보 저장 관련 객체 생성
	static Restaurant_Info restaurant = new Restaurant_Info();
	// For Connection.
	PreparedStatement pstmt1;
	PreparedStatement pstmt2;
	PreparedStatement pstmt3;
	PreparedStatement pstmt4;
	// 에러 관련 변수
	String ErrorMessage;
	boolean isError = false;
	boolean rollback = false;

	/** 
	 * HOME_Page JPanel 설정.
	 * 하얀 배경에 절대 위치를 통해 버튼을 올렸다.
	 * 홈 버튼, 다음 페이지로 가는 버튼, 레스토랑 정보를 입력하는 입력란들이 있다.
	 * 필수 입력란은 반드시 입력해야 한다.
	 * */
	Additional_Restaurants_First_Page() {

		this.setBackground(Color.white); // 하얀 배경
		this.setLayout(null); // 절대 위치로 배치

		// 각종 내용물들 절대 위치 및 크기, 세부 속성 설정
		R_name.setBounds(190, logo_height / 4 * 3 - 15, 250, 20);
		R_name.setToolTipText("레스토랑 이름 입력란입니다.");
		R_type.setBounds(190, logo_height / 4 * 3 + 25, 250, 20);
		R_type.setToolTipText("(ex : 한식, 일식, 아시아음식, ...)");
		R_info.setBounds(190, logo_height / 4 * 3 + 65, 250, 20);
		R_info.setToolTipText("한줄 소개 입력란입니다.");
		R_phone.setBounds(190, logo_height / 4 * 3 + 185, 250, 20);
		R_phone.setToolTipText("전화번호 입력란입니다. ( ' - ' 포함)");
		R_address.setBounds(190, logo_height / 4 * 3 + 225, 250, 20);
		R_address.setToolTipText("주소 입력란입니다.");
		R_website.setBounds(190, logo_height / 4 * 3 + 265, 250, 20);
		R_website.setToolTipText("웹사이트 주소 입력란입니다.");
		Open_hour.setBounds(190, logo_height / 4 * 3 + 105, 40, 20);
		Open_hour.setBackground(Color.white);
		Open_hour.setToolTipText("오픈 시간 입력란입니다.");
		Open_minute.setBounds(250, logo_height / 4 * 3 + 105, 40, 20);
		Open_minute.setBackground(Color.white);
		Open_minute.setToolTipText("오픈 시간 입력란입니다.");
		Close_hour.setBounds(190, logo_height / 4 * 3 + 145, 40, 20);
		Close_hour.setBackground(Color.white);
		Close_hour.setToolTipText("클로즈 시간 입력란입니다.");
		Close_minute.setBounds(250, logo_height / 4 * 3 + 145, 40, 20);
		Close_minute.setBackground(Color.white);
		Close_minute.setToolTipText("클로즈 시간 입력란입니다.");
		// 절대 위치 및 크기 설정
		Mon.setBounds(185, logo_height / 4 * 3 + 305, 20, 15);
		Tue.setBounds(255, logo_height / 4 * 3 + 305, 20, 15);
		Wed.setBounds(325, logo_height / 4 * 3 + 305, 20, 15);
		Thu.setBounds(395, logo_height / 4 * 3 + 305, 20, 15);
		Fri.setBounds(185, logo_height / 4 * 3 + 330, 20, 15);
		Sat.setBounds(255, logo_height / 4 * 3 + 330, 20, 15);
		Sun.setBounds(325, logo_height / 4 * 3 + 330, 20, 15);
		// 배경 색 하양으로 설정
		Mon.setBackground(Color.white);
		Tue.setBackground(Color.white);
		Wed.setBackground(Color.white);
		Thu.setBackground(Color.white);
		Fri.setBackground(Color.white);
		Sat.setBackground(Color.white);
		Sun.setBackground(Color.white);
		// ToolTipText 설정
		Mon.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Tue.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Wed.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Thu.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Fri.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Sat.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Sun.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		// Holiday_Closed 설정 버튼 생성
		Y.setBounds(185, logo_height / 4 * 3 + 363, 20, 15);
		Y.setBackground(Color.white);
		Y.setToolTipText("공휴일 휴점 여부 선택란입니다.");
		N.setBounds(255, logo_height / 4 * 3 + 363, 20, 15);
		N.setBackground(Color.white);
		N.setToolTipText("공휴일 휴점 여부 선택란입니다.");
		// Y, N 중 하나만 선택가능
		group.add(Y);
		group.add(N);
		// 홈 버튼 설정
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. 홈 페이지로.");
		HOME.setBorderPainted(false); // 버튼 경계선 없애기
		HOME.addActionListener(this);
		// 다음 페이지로 가는 버튼 설정
		NEXT.setBounds(436, logo_height * 3 - 60, next_width / 8, next_height / 8);
		NEXT.setBackground(Color.WHITE);
		NEXT.setToolTipText("Next. 다음 페이지로.");
		NEXT.setBorderPainted(false); // 버튼 경계선 없애기
		NEXT.addActionListener(this);

		// 내용물들 화면에 담기
		this.add(R_name);
		this.add(R_type);
		this.add(R_info);
		this.add(R_phone);
		this.add(R_address);
		this.add(R_website);
		this.add(Open_hour);
		this.add(Open_minute);
		this.add(Close_hour);
		this.add(Close_minute);
		this.add(Mon);
		this.add(Tue);
		this.add(Wed);
		this.add(Thu);
		this.add(Fri);
		this.add(Sat);
		this.add(Sun);
		this.add(Y);
		this.add(N);
		this.add(HOME);
		this.add(NEXT);

	}

	/**
	 * Panel에 그리기.
	 * 글씨와 그림들을 그린다.
	 * 필수 입력란은 색 강조가 들어가있다.
	 * */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// 위, 아래 부분
		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);
		g.drawImage(next_img, getWidth() - next_width / 2, getHeight() - logo_height / 2, next_width / 2,
				next_height / 2, this);
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Enter additional restaurant information.", logo_width / 2 + 15, logo_height / 4 - 10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		g.drawString("레스토랑 정보를 입력하세요.", logo_width / 5 * 4 + 25, logo_height / 5 * 2 - 10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+는 필수 입력란입니다.)", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

		// 중간 부분
		g.setColor(new Color(0, 49, 39));
		g.setFont(new Font("Calibri", Font.PLAIN, 15));
		g.drawString("+   Restaurant Name", 40, logo_height / 4 * 3);
		g.drawString("+   Restaurant Type", 40, logo_height / 4 * 3 + 40);
		g.drawString("+   Restaurant Info", 40, logo_height / 4 * 3 + 80);
		g.drawString("+   Opening hour", 40, logo_height / 4 * 3 + 120);
		g.drawString(":", 238, logo_height / 4 * 3 + 120);
		g.drawString("+   Closing hour", 40, logo_height / 4 * 3 + 160);
		g.drawString(":", 238, logo_height / 4 * 3 + 160);
		g.drawString("+   Phone Number", 40, logo_height / 4 * 3 + 200);
		g.drawString("+   Address", 40, logo_height / 4 * 3 + 240);
		g.drawString("+   Website", 40, logo_height / 4 * 3 + 280);
		g.drawString("+   Closed Day", 40, logo_height / 4 * 3 + 330);
		g.drawString("Mon.", 215, logo_height / 4 * 3 + 318);
		g.drawString("Tue.", 285, logo_height / 4 * 3 + 318);
		g.drawString("Wed.", 355, logo_height / 4 * 3 + 318);
		g.drawString("Thu.", 425, logo_height / 4 * 3 + 318);
		g.drawString("Fri.", 215, logo_height / 4 * 3 + 343);
		g.drawString("Sat.", 285, logo_height / 4 * 3 + 343);
		g.drawString("Sun.", 355, logo_height / 4 * 3 + 343);
		g.drawString("+   Holiday Closed", 40, logo_height / 4 * 3 + 375);
		g.drawString("Yes", 215, logo_height / 4 * 3 + 375);
		g.drawString("No", 285, logo_height / 4 * 3 + 375);

		// 색 강조 부분
		g.setColor(new Color(243, 128, 137));
		g.drawString("+", 40, logo_height / 4 * 3);
		g.drawString("+", 40, logo_height / 4 * 3 + 120);
		g.drawString("+", 40, logo_height / 4 * 3 + 160);
		g.drawString("+", 40, logo_height / 4 * 3 + 375);
		g.drawString("+", 40, logo_height / 4 * 3 + 240);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		g.drawString(" +", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

	}

	/**
	 * 버튼 조작 관련 함수.
	 * + NEXT버튼을 누르면 레스토랑 정보를 database상에 insert한 후, 
	 * 다음 페이지로 이동시 대표 메뉴 입력 선택 여부에 따라 페이지를 이동한다.
	 * 메뉴 입력 시 Additional_Restaurants_Second_Page로 이동한다.
	 * 메뉴 입력 안할 시 Restaurant_List_page로 이동한다.
	 * 필수 입력창이 비어있을 시 등 에러 발생 시 팝업창으로 경고 문구를 띄운다.
	 * 창 이동 후에는 clearPage() 함수를 통해 창을 초기화시킨다. 
	 * + HOME버튼을 누르면 HOME_Page로 이동한다.
	 * */
	public void actionPerformed(ActionEvent e) {

		// next버튼 기능
		if (e.getSource() == NEXT) {

			// 사용자가 입력한 정보를 Restaurant_Info객체에 일단 저장한다
			getInfo();

			// 정보 입력에 문제가 없다면 데이터베이스에 그 정보를 저장하고
			// 대표 메뉴 입력 선택 여부에 따라 페이지를 이동시킨다
			if (!isError) {

				// 데이터베이스에 정보 저장
				storeInfo();

				if (rollback == true) {
					clearPage();
					JOptionPane.showMessageDialog(null, "다시 시도해 주십시오.");
				} else {
					// 대표 메뉴 입력 선택 여부 결정
					int confirm = JOptionPane.showConfirmDialog(null,
							"                  대표 메뉴도 입력하시겠습니까?\n  (대표메뉴는 최대 2개까지 입력하실 수 있습니다.)",
							" Enter the menu info?", JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {

						Main_Frame.R_card.next(Main_Frame.R_frame.getContentPane());

					} else if (confirm == JOptionPane.NO_OPTION) {

						Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");

					}

					// 창 초기화
					clearPage();

					// list창 새로고침
					Main_Frame.list_page.result = Main_Frame.list_page.List_Search();
					Main_Frame.list_page.model.setDataVector(Main_Frame.list_page.result, Main_Frame.list_page.title);

					Main_Frame.list_page.Center();
				}
			}
			// 정보 입력에 문제가 있다면 error발생 이유를 알려준다.
			else
				JOptionPane.showMessageDialog(null, ErrorMessage);

		}

		// home버튼 기능
		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");

	}

	/**
	 * 입력받은 레스토랑 정보를 Restaurant_Info 객체에 저장하는 함수
	 * 예외처리 필요 (필수 사항이 비어있는지의 여부).
	 * 예외처리는 throw new를 이용해 errorMessage를 넘기는 방식으로 진행한다.
	 * 여기서의 errorMessage는 버튼기능에서 팝업창을 통해 경고 문구를 날릴 시 사용된다.
	 */
	public void getInfo() {

		try {

			// 초기화
			isError = false;
			ErrorMessage = null;
			restaurant.Clear_R();
			// 레스토랑 아이디 생성
			restaurant.Create_R();

			// 이름은 필수 사항 -> 예외처리 필요
			if (R_name.getText().isEmpty())
				throw new Exception("Restaurant Name은 필수 입력란입니다.");
			restaurant.R_Name = R_name.getText();
			restaurant.R_Type = R_type.getText();
			restaurant.R_Info = R_info.getText();
			// 00:00:00 형태로 바꾸기
			String R_open = hour[Open_hour.getSelectedIndex()] + ":" + minute[Open_minute.getSelectedIndex()] + ":00";
			String R_close = hour[Close_hour.getSelectedIndex()] + ":" + minute[Close_minute.getSelectedIndex()]
					+ ":00";
			restaurant.R_Open = R_open;
			restaurant.R_Close = R_close;
			restaurant.R_Phone = R_phone.getText();
			// 주소는 필수 사항 -> 예외처리 필요
			if (R_address.getText().isEmpty())
				throw new Exception("Address는 필수 입력란입니다.");
			restaurant.R_Address = R_address.getText();
			restaurant.R_Website = R_website.getText();
			// 아무것도 선택 안 될 시에는 "None"
			if (Mon.isSelected())
				restaurant.R_Closed_Day.add("Mon");
			if (Tue.isSelected())
				restaurant.R_Closed_Day.add("Tue");
			if (Wed.isSelected())
				restaurant.R_Closed_Day.add("Wed");
			if (Thu.isSelected())
				restaurant.R_Closed_Day.add("Thu");
			if (Fri.isSelected())
				restaurant.R_Closed_Day.add("Fri");
			if (Sat.isSelected())
				restaurant.R_Closed_Day.add("Sat");
			if (Sun.isSelected())
				restaurant.R_Closed_Day.add("Sun");
			if (restaurant.R_Closed_Day.isEmpty())
				restaurant.R_Closed_Day.add("None");
			// 공휴일 휴점 여부는 필수 선택란 -> 예외처리 필요
			if (Y.isSelected())
				restaurant.R_Holiday_Closed = "Y";
			else if (N.isSelected())
				restaurant.R_Holiday_Closed = "N";
			else
				throw new Exception("Holiday Closed는 필수 선택란입니다.");

		} catch (Exception e) {

			isError = true;
			ErrorMessage = e.getMessage();

		}

	}

	/**
	 *  getInfo한 정보들을 데이터베이스에 저장하는 함수.
	 *  prepareStatement를 활용해 적절히 insert한다.
	 *  예외발생 또는 실행 실패 시 rollback()한다.
	 */
	public void storeInfo() {

		try {
			// 초기화
			pstmt1 = null;
			pstmt2 = null;
			pstmt3 = null;
			rollback = false;

			int result1, result2, result3 = 1, result4, temp;
			result1 = result2 = result4 = temp = 0;

			// Restaurants Table
			pstmt1 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Restaurants values(?,?,?,?,?,?)");
			pstmt1.setInt(1, restaurant.R_ID);
			pstmt1.setString(2, restaurant.R_Name);
			pstmt1.setString(3, restaurant.R_Type);
			pstmt1.setString(4, restaurant.R_Info);
			pstmt1.setString(5, restaurant.R_Open);
			pstmt1.setString(6, restaurant.R_Close);
			result1 = pstmt1.executeUpdate();

			// Access_Route Table
			pstmt2 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Access_Route values(?,?,?,?)");
			pstmt2.setInt(1, restaurant.R_ID);
			pstmt2.setString(2, restaurant.R_Phone);
			pstmt2.setString(3, restaurant.R_Address);
			pstmt2.setString(4, restaurant.R_Website);
			result2 = pstmt2.executeUpdate();

			// Holiday Table
			for (int i = 0; i < restaurant.R_Closed_Day.size(); i++) {
				pstmt3 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Holiday values(?,?,?)");
				pstmt3.setInt(1, restaurant.R_ID);
				pstmt3.setString(2, restaurant.R_Closed_Day.get(i));
				pstmt3.setString(3, restaurant.R_Holiday_Closed);
				temp = pstmt3.executeUpdate();
				result3 = result3 * temp;
			}

			// Evaluation Table
			pstmt4 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Evaluation values(?,0,0)");
			pstmt4.setInt(1, restaurant.R_ID);
			result4 = pstmt4.executeUpdate();

			// 자원반납
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			if (pstmt3 != null)
				pstmt3.close();
			if (pstmt4 != null)
				pstmt4.close();

			// 세 개다 성공했을 시에만 commit
			if (result1 * result2 * result3 * result4 > 0)
				DB_Connection.conn.commit();
			else {
				DB_Connection.conn.rollback();
				System.out.println("Additional_Restaurants_Frist_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// 예외 발생시 롤백
			try {
				DB_Connection.conn.rollback();
				System.out.println("Additional_Restaurants_Frist_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 *  창 초기화 함수
	 */
	public void clearPage() {

		R_name.setText(null);
		R_type.setText(null);
		R_info.setText(null);
		R_phone.setText(null);
		R_address.setText(null);
		R_website.setText(null);
		Open_hour.setSelectedIndex(0);
		Open_minute.setSelectedIndex(0);
		Close_hour.setSelectedIndex(0);
		Close_minute.setSelectedIndex(0);
		Mon.setSelected(false);
		Tue.setSelected(false);
		Wed.setSelected(false);
		Thu.setSelected(false);
		Fri.setSelected(false);
		Sat.setSelected(false);
		Sun.setSelected(false);
		group.clearSelection();

	}

}