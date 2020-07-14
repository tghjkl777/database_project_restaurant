package team12;
/** 사용하는데 필요한 것들이 포함된 라이브러리 포함 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.util.Vector;

/** 
 * @author 이해빈
 *  */
public class Modify_first_page extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/** 필요 아이콘, 내용물, 변수 선언 */
	ImageIcon logo_icon = Main_Frame.home_page.logo_icon;

	Image logo_img = logo_icon.getImage();

	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;

	JButton HOME = new JButton(logo_icon);

	String[] hour = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "21", "22", "23" };
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
	JButton menu = new JButton("Menu");
	JButton Okay = new JButton("확인");

	/** DB관련 변수*/
	Connection conn;
	Statement stmt;
	Statement stmt2;
	ResultSet rs;
	PreparedStatement pstmt;
	PreparedStatement pstmt1;
	PreparedStatement pstmt_holiday;

	Restaurant_Info res = new Restaurant_Info();

	ArrayList<String> restaurant_info = new ArrayList<>();
	ArrayList holiday = new ArrayList<>();
	ArrayList<String> cHoliday = new ArrayList<>();
	String holi_close;

	int select_R_ID;
	/** 버튼의 여백을 업애기 위한 Insets 설정 */
	Insets insets = new Insets(0, 0, 0, 0);

	boolean rollback = false;

	/** Panel 설정 */
	Modify_first_page() {
		/** 하얀 배경 */
		this.setBackground(Color.white);
		/** 절대 위치로 배치 */
		this.setLayout(null);

		/** 각종 내용물들 절대 위치 및 크기, 세부 속성 설정 */
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
		/** 절대 위치 및 크기 설정 */
		Mon.setBounds(185, logo_height / 4 * 3 + 305, 20, 15);
		Tue.setBounds(255, logo_height / 4 * 3 + 305, 20, 15);
		Wed.setBounds(325, logo_height / 4 * 3 + 305, 20, 15);
		Thu.setBounds(395, logo_height / 4 * 3 + 305, 20, 15);
		Fri.setBounds(185, logo_height / 4 * 3 + 330, 20, 15);
		Sat.setBounds(255, logo_height / 4 * 3 + 330, 20, 15);
		Sun.setBounds(325, logo_height / 4 * 3 + 330, 20, 15);
		/** 배경 색 하양으로 설정 */
		Mon.setBackground(Color.white);
		Tue.setBackground(Color.white);
		Wed.setBackground(Color.white);
		Thu.setBackground(Color.white);
		Fri.setBackground(Color.white);
		Sat.setBackground(Color.white);
		Sun.setBackground(Color.white);
		/** TpplTipText설정 */
		Mon.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Tue.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Wed.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Thu.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Fri.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Sat.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		Sun.setToolTipText("쉬는 날이 없는 경우 비워두십시오.");
		/** Holiday_Closed 설정 버튼 생성 */
		Y.setBounds(185, logo_height / 4 * 3 + 363, 20, 15);
		Y.setBackground(Color.white);
		Y.setToolTipText("공휴일 휴점 여부 선택란입니다.");
		N.setBounds(255, logo_height / 4 * 3 + 363, 20, 15);
		N.setBackground(Color.white);
		N.setToolTipText("공휴일 휴점 여부 선택란입니다.");
		/** Y, N 중 하나만 선택 가능 */
		group.add(Y);
		group.add(N);
		/** 창 이동 버튼 설정 */
		menu.setBounds(370, 530, 40, 30);
		/** 버튼의 여백 없애기 */
		menu.setMargin(insets);
		menu.setBackground(Color.WHITE);
		Okay.setBounds(430, 530, 40, 30);
		/** 버튼의 여백 없애기 */
		Okay.setMargin(insets);
		Okay.setBackground(Color.WHITE);
		/** 홈 버튼 설정 */
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. 홈 페이지로.");
		/** 버튼 경계선 없애기 */
		HOME.setBorderPainted(false);

		/** 내용물을 화면에 담기 */
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
		this.add(menu);
		this.add(Okay);
		/** 버튼에 액션 주기 */
		menu.addActionListener(this);
		HOME.addActionListener(this);
		Okay.addActionListener(this);

	}

	/** Panel에 그리기 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		/** 상단, 하단 부분 */

		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Modify restaurant information.", logo_width / 2 + 45, logo_height / 4 - 10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		g.drawString("레스토랑 정보를 수정하세요.", logo_width / 5 * 4 + 25, logo_height / 5 * 2 - 10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+는 필수 입력란입니다.)", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

		/** 중단 부분 */
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

		/** 색 강조 부분 */
		g.setColor(new Color(243, 128, 137));
		g.drawString("+", 40, logo_height / 4 * 3);
		g.drawString("+", 40, logo_height / 4 * 3 + 120);
		g.drawString("+", 40, logo_height / 4 * 3 + 160);
		g.drawString("+", 40, logo_height / 4 * 3 + 375);
		g.drawString("+", 40, logo_height / 4 * 3 + 240);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		g.drawString(" +", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

	}

	// 버튼 조작 관련 함수
	public void actionPerformed(ActionEvent e) {
		/** HOME 버튼을 클릭할 시 첫 화면으로 돌아감 */
		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		/** DB상에 있는 정보를 받아온 후 정보를 수정한 후에 menu버튼을 클릭할 시 입력된 정보를 DB에 업데이트 함 */ 
		if (e.getSource() == menu) {
			/** DBCOURSE_RESTAURANTS테이블과 DBCOURSE_HOLIDAY테이블에서 해당하는 정보 받아오기*/
			getUpdateInfo();
			getUpdateHoliday();
			/**에러가 나는 경우 에러메세지 출력 */
			if (rollback == true) {
				JOptionPane.showConfirmDialog(null, "다시 시도해 주십시오");
			}/** 에러가 나지 않는 경우 수정한 정보를 DB상에 업데이트 한 후 다음 메뉴 수정페이지로 넘어감*/ 
			else {
				UpdateRestaurant(res.R_ID);
				UpdateAccess(res.R_ID);
				UpdateHoliday(res.R_ID);
				Main_Frame.R_card.next(Main_Frame.R_frame.getContentPane());
			}/** 다음 페이지에 해당하는 정보를 미리 받아옴*/
			Main_Frame.modify_second_page.getInfo(res.R_ID);
			Main_Frame.modify_second_page.setMenu();
		}
		/** 레스토랑 정보만 수정하고 싶을 경우 바로 okay버튼 누르면 menu버튼 눌렀을 때와 다르게
		 * 그 다음페이지에 필요한 것들을 빼고 실행 그리고 레스토랑 리스트 페이지로 돌아감 */
		if (e.getSource() == Okay) {

			getUpdateInfo();
			getUpdateHoliday();

			if (rollback == true) {
				JOptionPane.showConfirmDialog(null, "다시 시도해 주십시오");
			} else {
				UpdateRestaurant(res.R_ID);
				UpdateAccess(res.R_ID);
				UpdateHoliday(res.R_ID);
			}
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");
		}
	}
	/** 레스토랑 리스트 페이지에서 레스토랑 아이디를 받아오는 함수 */
	public void create_R_ID() {
		select_R_ID = Main_Frame.list_page.select_R_ID;
		res.R_ID = select_R_ID;
	}
	/** DB상에 있는 레스토랑 정보들을  아이디를 검색해서 받아오는 함수. 한번에 띄우기 위해 새로운 view를 만들어서 사용*/
	public void getInfo(int id) {
		try {
			/** 초기화 */
			restaurant_info.clear();

			/** 쿼리 결과를 받아옴 */
			stmt = DB_Connection.stmt;
			String sqlstr = "select * from DBCOURSE_RESTAURANT_INFO_VIEW where Restaurant_ID=" + id;
			rs = stmt.executeQuery(sqlstr);

			/** 검색 결과를 ArrayList인 restaurant_info 변수에 저장 */
			while (rs.next()) {
				/** R_ID(1), R_Name(2), R_Type(3), R_Info(4), R_Open(5), R_Close(6), R_Phone(7),
				* R_Address(8), R_Website(9) 레스토랑 정보들을 view에서 받아오는 순서 */
				restaurant_info.add(rs.getString(1));
				restaurant_info.add(rs.getString(2));
				restaurant_info.add(rs.getString(3));
				restaurant_info.add(rs.getString(4));
				/** open시간과 close시간은 형태가 HH:MM:SS이기 때문에 앞에 HH만 받아오기 위해 받아온 String을 쪼갬*/
				String str_op = rs.getString(5);
				restaurant_info.add(str_op.substring(0, str_op.length() - 3));
				String str_cl = rs.getString(6);
				restaurant_info.add(str_cl.substring(0, str_cl.length() - 3));
				restaurant_info.add(rs.getString(7));
				restaurant_info.add(rs.getString(8));
				restaurant_info.add(rs.getString(9));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	/** DBCOURSE_HOLIDAY에 있는 레스토랑 휴일에 관한 정보를 레스토랑아이디로 검색해서 받아오는 함수 */
	public void getHoliday(int id) {
		try {
			/** 초기화 */
			holiday.clear();
			ArrayList holi = new ArrayList<>();
			/** 쿼리 결과를 받아옴 */
			stmt = DB_Connection.stmt;

			String sqlstr = "select * from  DBCOURSE_Holiday where Restaurant_ID=" + id;
			rs = stmt.executeQuery(sqlstr);

			/** 검색 결과를 ArrayList 변수에 저장 */
			while (rs.next()) {
				int r_id = rs.getInt(1);
				String cl_day = rs.getString(2);
				String hc = rs.getString(3);

				holiday.add(r_id);
				holiday.add(cl_day);
				holiday.add(hc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/** 받아온 정보드를 위치에 맞게 텍스트필드에 입력하는 메소드*/
	public void SetText(ArrayList<String> info) {

		R_name.setText(info.get(1));
		R_type.setText(info.get(2));
		R_info.setText(info.get(3));
		R_phone.setText(info.get(6));
		R_address.setText(info.get(7));
		R_website.setText(info.get(8));
		/** open시간과 close시간은 형태가 HH:MM:SS이기 때문에 쪼개서 사용*/
		Open_hour.setSelectedItem(info.get(4).substring(0, 2));
		Open_minute.setSelectedItem(info.get(4).substring(3, 5));
		Close_hour.setSelectedItem(info.get(5).substring(0, 2));
		Close_minute.setSelectedItem(info.get(5).substring(3, 5));
	}
	/** DBCOURSE_HOLIDAY에서 받아온 휴일을 보여주는 것을 설정하는 함수*/
	public void SetCheckBox(ArrayList holiday) {

		/** ArrayList holiday에 저장한 함수들 중 해당하는 정보를 포함하면 버튼을 선택된 상태로 변환*/
		if (holiday.contains("Mon")) {
			Mon.setSelected(true);
		}
		if (holiday.contains("Tue")) {
			Tue.setSelected(true);
		}
		if (holiday.contains("Wed")) {
			Wed.setSelected(true);
		}
		if (holiday.contains("Thu")) {
			Thu.setSelected(true);
		}
		if (holiday.contains("Fri")) {
			Fri.setSelected(true);
		}
		if (holiday.contains("Sat")) {
			Sat.setSelected(true);
		}
		if (holiday.contains("Sun")) {
			Sun.setSelected(true);
		}
		if (holiday.contains("Y")) {
			Y.setSelected(true);
		}
		if (holiday.contains("N")) {
			N.setSelected(true);
		}

	}

	/** 레스토랑 정보를 수정했을 경우 수정된 정보를 ArrayList에 받아오는 함수*/
	public void getUpdateInfo() {
		restaurant_info.clear();
		restaurant_info.add(R_name.getText());
		restaurant_info.add(R_type.getText());
		restaurant_info.add(R_info.getText());
		restaurant_info.add(R_phone.getText());
		restaurant_info.add(R_address.getText());
		restaurant_info.add(R_website.getText());
		restaurant_info.add(Open_hour.getSelectedItem().toString() + ":" + Open_minute.getSelectedItem().toString());
		restaurant_info.add(Close_hour.getSelectedItem().toString() + ":" + Close_minute.getSelectedItem().toString());
	}
	
	/** 휴일 정보를 수정했을 경우 수정된 정보를 ArrayList에 받아오는 함수*/
	public void getUpdateHoliday() {
		cHoliday.clear();
		if (Mon.isSelected())
			cHoliday.add("Mon");
		if (Tue.isSelected())
			cHoliday.add("Tue");
		if (Wed.isSelected())
			cHoliday.add("Wed");
		if (Thu.isSelected())
			cHoliday.add("Thu");
		if (Fri.isSelected())
			cHoliday.add("Fri");
		if (Sat.isSelected())
			cHoliday.add("Sat");
		if (Sun.isSelected())
			cHoliday.add("Sun");
		if (Y.isSelected())
			holi_close = "Y";
		if (N.isSelected())
			holi_close = "N";

	}
	/**수정된 정보를 DB상에 있는 DBCOURSE_RESTAURANTS테이블에
	 *  레스토랑 아이디를 검색해서 해당하는 아이디에 정보를 업데이트하는 함수*/
	public void UpdateRestaurant(int id) {
		try {
			// 초기화

			conn = DB_Connection.conn;
			// 쿼리 결과를 받아온다.
			pstmt = null;
			rollback = false;

			String sqlstr = "update DBCOURSE_Restaurants set Restaurant_name=?, Restaurant_Type=?, Restaurant_Info=?, Opening_hours=?, Closing_hours=? where Restaurant_ID="
					+ id;

			pstmt = conn.prepareStatement(sqlstr);

			/** DBCOURSE_RESTAURANTS에서 해당하는 아이디를 가진 tuple을 업데이트 */
			pstmt.setString(1, restaurant_info.get(0));
			pstmt.setString(2, restaurant_info.get(1));
			pstmt.setString(3, restaurant_info.get(2));
			pstmt.setString(4, restaurant_info.get(6));
			pstmt.setString(5, restaurant_info.get(7));

			int result = pstmt.executeUpdate();

			if (pstmt != null)
				pstmt.close();

			if (result > 0) {
				System.out.println("성공");
				DB_Connection.conn.commit();
			} else {
				DB_Connection.conn.rollback();
				System.out.println("실패");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
			try {
				DB_Connection.conn.rollback();
				System.out.println("Modify_First_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
	}
	/**수정된 정보를 DB상에 있는 DBCOURSE_ACCESS_ROUTE 테이블에
	 *  레스토랑 아이디를 검색해서 해당하는 아이디에 정보를 업데이트하는 함수*/
	public void UpdateAccess(int id) {
		try {
			// 초기화

			conn = DB_Connection.conn;
			// 쿼리 결과를 받아온다.

			pstmt = null;
			rollback = false;

			String sqlstr = "update DBCOURSE_Access_Route set Phone_Number=?, Address=?, Website_Address=? where Restaurant_ID="
					+ id;

			pstmt = conn.prepareStatement(sqlstr);
			
			/** 레스토랑 아이디에 해당하는 DBCOURSE_ACCESS_ROUTE tuple 업데이트*/
			pstmt.setString(1, restaurant_info.get(3));
			pstmt.setString(2, restaurant_info.get(4));
			pstmt.setString(3, restaurant_info.get(5));

			int result = pstmt.executeUpdate();

			if (pstmt != null)
				pstmt.close();

			if (result > 0) {
				System.out.println("성공");
				DB_Connection.conn.commit();
			} else {
				DB_Connection.conn.rollback();
				System.out.println("실패");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			}
		} catch (SQLException e) {

			e.printStackTrace();
			try {
				DB_Connection.conn.rollback();
				System.out.println("Modify_First_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
	}
	/**수정된 정보를 DB상에 있는 DBCOURSE_HOLIDAY테이블에
	 *  레스토랑 아이디를 검색해서 해당하는 아이디에 정보를 업데이트하는 함수*/
	public void UpdateHoliday(int id) {
		try {
			/** 초기화 */

			conn = DB_Connection.conn;
			
			/** 쿼리 결과를 받아옴 */
			Statement stmt02 = conn.createStatement();
			Statement stmt03 = conn.createStatement();
			pstmt = null;
			rollback = false;

			/** R_ID가 테이블 상에 만들어져 있는지 확인*/
			System.out.println(checkRID());
			/** mysql 상에서 해당하는 외래키와 연결된 모든 테이블에서 정보가 삭제되기 때문에 그걸 방지하기
			 * 위해 별도로 왜래키 R_ID를 설정해준 후 drop해서 왜래키를 없앰*/
			stmt02.executeUpdate("ALTER TABLE DBCOURSE_holiday drop foreign key R_ID");
			/**holiday를 업데이트 하기 위해서 먼저 레스토랑아이디에 해당하는 holiday를 전부 삭제*/
			String sqlstr = "delete from DBCOURSE_Holiday where Restaurant_ID = ?";
			pstmt = conn.prepareStatement(sqlstr);
			pstmt.setString(1, String.valueOf(id));
			pstmt.executeUpdate();

			/**쉬는 날이 몇일인지 모르기 때문에 쉬는날을 받아온 ArrayList의 size에 맞춰서 순서대로 DBCOURSE_HOLIDAY에 해당하는 레스토랑아이디와
			 * 공휴일 유점 유무를 함께 업데이트*/
			if (cHoliday.size() != 0) {
				for (int i = 0; i < cHoliday.size(); i++) {
					pstmt1 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Holiday values(?,?,?)");
					pstmt1.setInt(1, id);
					pstmt1.setString(2, cHoliday.get(i));
					/**공휴일에 쉬는 여부 입력*/
					pstmt1.setString(3, holi_close);
					pstmt1.executeUpdate();
				}
			}/**만약에 쉬는 날이 없을 경우 검색조건에 있는 레스토랑아이디에 해당하는 holiday에 None을 넣어서 업데이트*/
			else {
				pstmt1 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Holiday values(?,?,?)");
				pstmt1.setInt(1, id);
				pstmt1.setString(2, "None");
				pstmt1.setString(3, holi_close);
				pstmt1.executeUpdate();
			}
			/** holiday에 삭제했던 왜래키를 다시 집어넣어줌*/
			stmt03.executeUpdate("ALTER TABLE DBCOURSE_holiday add constraint R_ID FOREIGN KEY"
					+ "(Restaurant_ID) REFERENCES DBCOURSE_Restaurants(Restaurant_ID) ON DELETE CASCADE");

			if (pstmt != null)
				pstmt.close();

			DB_Connection.conn.commit();

		} catch (SQLException e) {

			e.printStackTrace();
			try {
				DB_Connection.conn.rollback();
				System.out.println("Modify_First_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}
	}
	/** DBCOURSE_HOLIDAY테이블에 R_ID가 있는지 체크하기 위한 함수*/
	public boolean checkRID() {
		try {
			Statement stmt01 = conn.createStatement();
			stmt01.executeUpdate("ALTER TABLE DBCOURSE_holiday add constraint R_ID FOREIGN KEY"
					+ "(Restaurant_ID) REFERENCES DBCOURSE_Restaurants(Restaurant_ID) ON DELETE CASCADE");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}