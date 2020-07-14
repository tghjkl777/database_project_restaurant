package team12;
/** 사용하는데 필요한 것들이 포함된 라이브러리 포함 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;

/** 이해빈 */
public class Modify_second_page extends JPanel implements ActionListener {

	/** 필요 아이콘, 내용물, 변수 선언 */
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
	ButtonGroup group1 = new ButtonGroup();
	JRadioButton Y1 = new JRadioButton();
	JRadioButton N1 = new JRadioButton();
	JTextField F_name1 = new JTextField();
	JTextField F_price1 = new JTextField();
	JTextField F_main1 = new JTextField();
	JTextField F_sub1 = new JTextField();
	ButtonGroup group2 = new ButtonGroup();
	JRadioButton Y2 = new JRadioButton();
	JRadioButton N2 = new JRadioButton();
	JTextField F_name2 = new JTextField();
	JTextField F_price2 = new JTextField();
	JTextField F_main2 = new JTextField();
	JTextField F_sub2 = new JTextField();
	JCheckBox menu1 = new JCheckBox();
	JCheckBox menu2 = new JCheckBox();

	JButton Okay = new JButton("확인");
	/** 버튼에 여백을 없애기 위한 Insets 설정 */
	Insets insets = new Insets(0, 0, 0, 0);
	
	/** DB관련 변수 */
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt1;
	PreparedStatement pstmt2;
	
	/** 레스토랑 정보들을 받아오는 클래스 Restaurant_Info 선언 */
	Restaurant_Info res;

	ArrayList<String> Menu_info1 = new ArrayList<String>();
	ArrayList<String> Menu_info2 = new ArrayList<String>();

	int select_R_ID;

	String ErrorMessage;
	boolean isError = false;
	boolean rollback = false;

	/** Panel 설정 */

	Modify_second_page() {
		/** 하얀 배경 */
		this.setBackground(Color.white);
		/** 절대 위치로 배치 */
		this.setLayout(null); 

		/** 홈 버튼 설정 */
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. 홈 페이지로.");
		/** 버튼 경계선 없애기 */
		HOME.setBorderPainted(false);
		HOME.addActionListener(this);
		/** 확인 버튼 설정 */
		Okay.setBounds(430, 530, 40, 30);
		Okay.setMargin(insets);
		Okay.setBackground(Color.WHITE);
		Okay.addActionListener(this);

		menu1.setBounds(34, logo_height / 4 * 3 - 15, 20, 15);
		menu1.setBackground(Color.white);
		menu1.setToolTipText("작성된 메뉴를 삭제하거나 메뉴가 비어있을 때 입력하고 싶지 않으시면 선택해주세요");
		menu1.addActionListener(this);
		menu2.setBounds(34, logo_height / 4 * 3 + 200, 20, 15);
		menu2.setBackground(Color.white);
		menu2.setToolTipText("작성된 메뉴를 삭제하거나 메뉴가 비어있을 때 입력하고 싶지 않으시면 선택해주세요");
		menu2.addActionListener(this);

		/** 1번메뉴 각종 내용물들 절대 위치 및 크기, 세부 속성 설정 */
		F_name1.setBounds(225, logo_height / 4 * 3 - 15, 220, 20);
		F_name1.setToolTipText("대표 메뉴 이름 입력란입니다. (※메뉴 자체 생략가능)");
		F_price1.setBounds(245, logo_height / 4 * 3 + 25, 200, 20);
		F_price1.setToolTipText("메뉴 가격 입력란입니다.");
		F_main1.setBounds(245, logo_height / 4 * 3 + 65, 200, 20);
		F_main1.setToolTipText("주 재료 입력란입니다.");
		F_sub1.setBounds(245, logo_height / 4 * 3 + 105, 200, 20);
		F_sub1.setToolTipText("부 재료 입력란입니다.");
		/** Vegetarian 설정 버튼 생성 */
		Y1.setBounds(240, logo_height / 4 * 3 + 148, 20, 15);
		Y1.setBackground(Color.white);
		Y1.setToolTipText("비건 음식 여부 선택란입니다.");
		N1.setBounds(310, logo_height / 4 * 3 + 148, 20, 15);
		N1.setBackground(Color.white);
		N1.setToolTipText("비건 음식 여부 선택란입니다.");
		/** Y, N 중 하나만 선택 */
		group1.add(Y1);
		group1.add(N1);

		/** 2번 각종 내용물들 절대 위치 및 크기, 세부 속성 설정 */
		F_name2.setBounds(225, logo_height / 4 * 3 + 200, 220, 20);
		F_name2.setToolTipText("대표 메뉴 이름 입력란입니다. (※메뉴 자체 생략가능)");
		F_price2.setBounds(245, logo_height / 4 * 3 + 240, 200, 20);
		F_price2.setToolTipText("메뉴 가격 입력란입니다.");
		F_main2.setBounds(245, logo_height / 4 * 3 + 280, 200, 20);
		F_main2.setToolTipText("주 재료 입력란입니다.");
		F_sub2.setBounds(245, logo_height / 4 * 3 + 320, 200, 20);
		F_sub2.setToolTipText("부 재료 입력란입니다.");
		/** Vegetarian 설정 버튼 생성 */
		Y2.setBounds(240, logo_height / 4 * 3 + 363, 20, 15);
		Y2.setBackground(Color.white);
		Y2.setToolTipText("비건 음식 여부 선택란입니다.");
		N2.setBounds(310, logo_height / 4 * 3 + 363, 20, 15);
		N2.setBackground(Color.white);
		N2.setToolTipText("비건 음식 여부 선택란입니다.");
		/** Y, N 중 하나만 선택가능 */
		group2.add(Y2);
		group2.add(N2);

		/** 내용물들 화면에 담기 */
		this.add(F_name1);
		this.add(F_price1);
		this.add(F_main1);
		this.add(F_sub1);
		this.add(Y1);
		this.add(N1);
		this.add(F_name2);
		this.add(F_price2);
		this.add(F_main2);
		this.add(F_sub2);
		this.add(Y2);
		this.add(N2);
		this.add(HOME);
		this.add(Okay);
		this.add(menu1);
		this.add(menu2);

	}

	/** Panel에 그리기 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		/** 상단, 하단 부분 */
		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);

		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Modify restaurant Menu.", logo_width / 2 + 75, logo_height / 4 - 10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		g.drawString("메뉴를 수정하세요.", logo_width / 5 * 4 + 50, logo_height / 5 * 2 - 10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+는 필수 입력란입니다.)", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

		/** 중단 부분 */
		g.setColor(new Color(0, 49, 39));
		g.setFont(new Font("Calibri", Font.PLAIN, 15));

		g.drawString("+   Representative Food 1", 40, logo_height / 4 * 3 - 2);
		g.drawString("+   Price", 60, logo_height / 4 * 3 + 38);
		g.drawString("+   Main Ingredient", 60, logo_height / 4 * 3 + 78);
		g.drawString("+   Sub Ingredient", 60, logo_height / 4 * 3 + 118);
		g.drawString("+   Vegetarian", 60, logo_height / 4 * 3 + 158);
		g.drawString("Yes", 270, logo_height / 4 * 3 + 159);
		g.drawString("No", 340, logo_height / 4 * 3 + 159);

		g.drawString("+   Representative Food 2", 40, logo_height / 4 * 3 + 213);
		g.drawString("+   Price", 60, logo_height / 4 * 3 + 253);
		g.drawString("+   Main Ingredient", 60, logo_height / 4 * 3 + 293);
		g.drawString("+   Sub Ingredient", 60, logo_height / 4 * 3 + 333);
		g.drawString("+   Vegetarian", 60, logo_height / 4 * 3 + 373);
		g.drawString("Yes", 270, logo_height / 4 * 3 + 374);
		g.drawString("No", 340, logo_height / 4 * 3 + 374);

		/** 색 강조 부분 */
		g.setColor(new Color(243, 128, 137));
		g.drawString("+", 40, logo_height / 4 * 3 - 2);
		g.drawString("+", 60, logo_height / 4 * 3 + 78);
		g.drawString("+", 60, logo_height / 4 * 3 + 158);
		g.drawString("+", 40, logo_height / 4 * 3 + 213);
		g.drawString("+", 60, logo_height / 4 * 3 + 293);
		g.drawString("+", 60, logo_height / 4 * 3 + 373);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		g.drawString(" +", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

	}

	/** 전 페이지로부터 레스토랑 아이디를 받아옴 */
	public void create_R_ID() {
		select_R_ID = Main_Frame.list_page.select_R_ID;
		res.R_ID = select_R_ID;
		res.Create_M();
	}

	/** 버튼 액션 관련 함수 오버라이딩 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Okay) {
			/** DB에서 Menu관련 정보 받아오기 */
			getUpdateMenuInfo();
			if (!isError) {
				if (rollback == true) {/** 에러가 날 경우 에러메세지 출력하고 다시 시도 */
					clearPage();
					JOptionPane.showMessageDialog(null, "다시 시도해 주십시오.");
				} else {/** 에러가 나지 않을 경우 메뉴와 재료를 업데이트하고 리스트페이지로 돌아감 */
					UpdateIngredient(Main_Frame.modify_first_page.select_R_ID);
					UpdateMenu(Main_Frame.modify_first_page.select_R_ID);
					Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");
					clearPage();
				}
			} else
				JOptionPane.showMessageDialog(null, ErrorMessage);
		}
		/** Home버튼 누를 시 첫페이지로 돌아가기 */
		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		/** menu1버튼 관련 함수 */
		if (e.getSource() == menu1) {

			if (!menu1.isSelected()) {
				/** 버튼이 선택되어 있지 않을 때 활성 상태 */
				F_name1.setEnabled(true);
				F_price1.setEnabled(true);
				F_main1.setEnabled(true);
				F_sub1.setEnabled(true);
				Y1.setEnabled(true);
				N1.setEnabled(true);
			} else {
				/** 버튼이 선택되어 있을 때 비활성 상태 */
				F_name1.setEnabled(false);
				F_price1.setEnabled(false);
				F_main1.setEnabled(false);
				F_sub1.setEnabled(false);
				Y1.setEnabled(false);
				N1.setEnabled(false);
			}

		}
		/** menu2버튼 관련 함수 */
		if (e.getSource() == menu2) {

			if (!menu2.isSelected()) {
				/** 버튼이 선택되어 있지 않을 때 활성 상태 */
				F_name2.setEnabled(true);
				F_price2.setEnabled(true);
				F_main2.setEnabled(true);
				F_sub2.setEnabled(true);
				Y2.setEnabled(true);
				N2.setEnabled(true);
			} else {
				/** 버튼이 선택되어 있을 때 비활성 상태 */
				F_name2.setEnabled(false);
				F_price2.setEnabled(false);
				F_main2.setEnabled(false);
				F_sub2.setEnabled(false);
				Y2.setEnabled(false);
				N2.setEnabled(false);
			}

		}

	}

	/** DB에서 검색한 레스토랑 아이디에 해당하는 메뉴정보들을 받아오는 함수  한꺼번에 보기 위해 새로운 뷰를 생성*/
	public void getInfo(int id) {

		try {
			/** 초기화 */
			Menu_info1.clear();
			Menu_info2.clear();

			/** 쿼리 결과를 받아옴 */

			stmt = DB_Connection.stmt;

			String sqlstr = "select * from DBCOURSE_MENU_INFO_VIEW where Restaurant_ID=" + id;
			rs = stmt.executeQuery(sqlstr);

			/** 검색 결과 저장 */
			while (rs.next()) {
				/** 메뉴 1번 */
				if (rs.getString(1).equals(id + "01")) {
					Menu_info1.add(rs.getString(3));
					Menu_info1.add(rs.getString(4));
					Menu_info1.add(rs.getString(5));
					Menu_info1.add(rs.getString(6));
					Menu_info1.add(rs.getString(7));
				}
				/** 메뉴 2번 */
				if (rs.getString(1).equals(id + "02")) {
					Menu_info2.add(rs.getString(3));
					Menu_info2.add(rs.getString(4));
					Menu_info2.add(rs.getString(5));
					Menu_info2.add(rs.getString(6));
					Menu_info2.add(rs.getString(7));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** 받아온 정보를 창에 불러오기 */
	public void setMenu() {

		String vege1;
		String vege2;
		/** 1번 메뉴가 있을 때 받아온 정보 띄우기 */
		if (Menu_info1.get(0) != null) {
			F_name1.setText(Menu_info1.get(0));
			F_price1.setText(Menu_info1.get(1));
			F_main1.setText(Menu_info1.get(2));
			F_sub1.setText(Menu_info1.get(3));
			vege1 = Menu_info1.get(4);

			if (vege1.isEmpty())
				group1.clearSelection();
			else if (vege1.equals("Y"))
				Y1.setSelected(true);
			else if (vege1.equals("N"))
				N1.setSelected(true);
		}
		/** 2번 메뉴가 있을 때 받아온 정보 띄우기 */
		if (Menu_info2.get(0) != null) {
			F_name2.setText(Menu_info2.get(0));
			F_price2.setText(Menu_info2.get(1));
			F_main2.setText(Menu_info2.get(2));
			F_sub2.setText(Menu_info2.get(3));
			vege2 = Menu_info2.get(4);

			if (vege2.isEmpty())
				group2.clearSelection();
			else if (vege2.equals("Y"))
				Y2.setSelected(true);
			else if (vege2.equals("N"))
				N2.setSelected(true);
		}
	}

	/** 수정된 메뉴 정보를 저장하는 함수 */
	public void getUpdateMenuInfo() {

		/** 초기화 */
		isError = false;
		ErrorMessage = null;
		Menu_info1.clear();
		Menu_info2.clear();

		try {

			/** 1번 메뉴 필수로 입력해야 하는 창이 비어있거나 형태를 잘못입력한 경우 예외메세지를 출력하고 바르게 입력된 경우
			 * ArrayList 변수에 저장 */
			if (!menu1.isSelected()) {

				if (F_name1.getText().isEmpty())
					throw new Exception("Representative Food1은 필수 입력란입니다. (메뉴 이름을 입력하세요.)");
				Menu_info1.add(F_name1.getText());
				if (!F_price1.getText().isEmpty() && !checkString(F_price1.getText()))
					throw new Exception("Price는 숫자여야 합니다.");
				else if (!F_price1.getText().isEmpty() && checkString(F_price1.getText()))
					Menu_info1.add(F_price1.getText());
				else
					Menu_info1.add(null);
				if (F_main1.getText().isEmpty())
					throw new Exception("Main ingredient는 필수 입력란입니다.");
				Menu_info1.add(F_main1.getText());
				Menu_info1.add(F_sub1.getText());
				if (Y1.isSelected())
					Menu_info1.add("Y");
				else if (N1.isSelected())
					Menu_info1.add("N");
				else
					throw new Exception("Vegetarian은 필수 선택란입니다.");
			}

			/** 2번 메뉴 필수로 입력해야 하는 창이 비어있거나 형태를 잘못입력한 경우 예외메세지를 출력하고 바르게 입력된 경우
			 * ArrayList 변수에 저장 */
			if (!menu2.isSelected()) {

				if (F_name2.getText().isEmpty())
					throw new Exception("Representative Food2는 필수 입력란입니다. (메뉴 이름을 입력하세요.)");
				Menu_info2.add(F_name2.getText());
				if (!F_price2.getText().isEmpty() && !checkString(F_price2.getText()))
					throw new Exception("Price는 숫자여야 합니다.");
				else if (!F_price2.getText().isEmpty() && checkString(F_price2.getText()))
					Menu_info2.add(F_price2.getText());
				else
					Menu_info2.add(null);
				if (F_main2.getText().isEmpty())
					throw new Exception("Main ingredient는 필수 입력란입니다.");
				Menu_info2.add(F_main2.getText());
				Menu_info2.add(F_sub2.getText());
				if (Y2.isSelected())
					Menu_info2.add("Y");
				else if (N2.isSelected())
					Menu_info2.add("N");
				else
					throw new Exception("Vegetarian은 필수 선택란입니다.");

			}

		} catch (Exception e) {

			isError = true;
			ErrorMessage = e.getMessage();

		}
	}

	/** DB상에 메뉴를 업데이트하는 함수 */
	public void UpdateMenu(int id) {
		try {
			/** 초기화 */
			pstmt1 = null;
			pstmt2 = null;
			rollback = false;

			/** 쿼리 결과를 받아옴 */

			String sqlstr1;
			String sqlstr2;

			/** 1번메뉴 업데이트 */
			if (!menu1.isSelected()) {
				sqlstr1 = "update DBCOURSE_Menu set Representative_Food=?, Price=? where Restaurant_ID=" + id
						+ " and Menu_ID=" + id + "01";

				pstmt1 = DB_Connection.conn.prepareStatement(sqlstr1);

				/** ?에 들어갈 변수를 지정 */
				pstmt1.setString(1, Menu_info1.get(0));

				try {
					pstmt1.setInt(2, Integer.parseInt(Menu_info1.get(1)));
				} catch (NumberFormatException e) {
					pstmt1.setString(2, null);
				}
			} else {
				sqlstr1 = "update DBCOURSE_Menu set Representative_Food = null, Price = null where Restaurant_ID=" + id
						+ " and Menu_ID=" + id + "01";

				pstmt1 = DB_Connection.conn.prepareStatement(sqlstr1);
			}

			/** 2번 메뉴 업데이트 */
			if (!menu2.isSelected()) {
				sqlstr2 = "update DBCOURSE_Menu set Representative_Food=?, Price=? where Restaurant_ID=" + id
						+ " and Menu_ID=" + id + "02";

				pstmt2 = DB_Connection.conn.prepareStatement(sqlstr2);

				pstmt2.setString(1, Menu_info2.get(0));
				try {
					pstmt2.setInt(2, Integer.parseInt(Menu_info2.get(1)));
				} catch (NumberFormatException ex) {
					pstmt2.setString(2, null);
				}
			} else {
				sqlstr2 = "update DBCOURSE_Menu set Representative_Food = null, Price = null where Restaurant_ID=" + id
						+ " and Menu_ID=" + id + "02";

				pstmt2 = DB_Connection.conn.prepareStatement(sqlstr2);
			}

			int result1 = pstmt1.executeUpdate();
			int result2 = pstmt2.executeUpdate();
			if (result1 * result2 > 0)
				DB_Connection.conn.commit();
			else {
				DB_Connection.conn.rollback();
				System.out.println("Modify_Second_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DB_Connection.conn.rollback();
				System.out.println("Modify_Second_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
	/** DB상에 재료 정보 업데이트 */
	public void UpdateIngredient(int id) {
		try {

			pstmt1 = null;
			pstmt2 = null;
			rollback = false;
			/** 쿼리 결과를 받아옴 */
			String sqlstr1;
			String sqlstr2;

			if (!menu1.isSelected()) {/** 메뉴가 입력되었을 경우 */
				sqlstr1 = "update DBCOURSE_Ingredients set Food_Name=?, main_ingredient=?, sub_ingredient=?, Vegetarian_YN=? where Menu_ID="
						+ id + "01";

				pstmt1 = DB_Connection.conn.prepareStatement(sqlstr1);

				/** 조건에 맞는 tuple에 정보 업데이트 */
				pstmt1.setString(1, Menu_info1.get(0));
				pstmt1.setString(2, Menu_info1.get(2));
				pstmt1.setString(3, Menu_info1.get(3));

				if (Menu_info1.get(4).isEmpty())
					pstmt1.setString(4, null);
				else
					pstmt1.setString(4, Menu_info1.get(4).substring(0, 1));

			} else {/** 메뉴가 입력되지 않았을 경우 레스토랑 아이디를 제외한 나머지 전부 null로 입력 */
				sqlstr1 = "update DBCOURSE_Ingredients set Food_Name = null, main_ingredient = null, sub_ingredient = null, Vegetarian_YN = null where Menu_ID="
						+ id + "01";

				pstmt1 = DB_Connection.conn.prepareStatement(sqlstr1);
			}

			if (!menu2.isSelected()) {/** 메뉴가 입력되었을 경우 */

				sqlstr2 = "update DBCOURSE_Ingredients set Food_Name=?, main_ingredient=?, sub_ingredient=?, Vegetarian_YN=? where Menu_ID="
						+ id + "02";
				pstmt2 = DB_Connection.conn.prepareStatement(sqlstr2);
				/** 조건에 맞는 tuple에 정보 업데이트 */
				pstmt2.setString(1, Menu_info2.get(0));
				pstmt2.setString(2, Menu_info2.get(2));
				pstmt2.setString(3, Menu_info2.get(3));

				if (Menu_info2.get(4).isEmpty())
					pstmt2.setString(4, null);
				else
					pstmt2.setString(4, Menu_info2.get(4).substring(0, 1));

			} else {/** 메뉴가 입력되지 않았을 경우 레스토랑 아이디를 제외한 나머지 전부 null로 입력 */
				sqlstr2 = "update DBCOURSE_Ingredients set Food_Name = null, main_ingredient = null, sub_ingredient = null, Vegetarian_YN = null where Menu_ID="
						+ id + "02";

				pstmt2 = DB_Connection.conn.prepareStatement(sqlstr2);
			}

			int result1 = pstmt1.executeUpdate();
			int result2 = pstmt2.executeUpdate();
			if (result1 * result2 > 0)
				DB_Connection.conn.commit();
			else {
				DB_Connection.conn.rollback();
				System.out.println("Modify_Second_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DB_Connection.conn.rollback();
				System.out.println("Modify_Second_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}

	/** 문자열이 숫자로 이루어져 있는지 판별하는 함수 */
	@SuppressWarnings("finally")
	public boolean checkString(String str) {

		boolean isNum = false;
		int i;

		try {
			isNum = true;
			i = Integer.parseInt(str);
		} catch (Exception e) {
			isNum = false;
		} finally {
			return isNum;
		}
	}

	/** 창 초기화 함수 */
	public void clearPage() {
		Menu_info1.clear();
		Menu_info2.clear();
		F_name1.setText(null);
		F_price1.setText(null);
		F_main1.setText(null);
		F_sub1.setText(null);
		F_name2.setText(null);
		F_price2.setText(null);
		F_main2.setText(null);
		F_sub2.setText(null);
		group1.clearSelection();
		group2.clearSelection();
		menu1.setSelected(false);
		F_name1.setEnabled(true);
		F_price1.setEnabled(true);
		F_main1.setEnabled(true);
		F_sub1.setEnabled(true);
		Y1.setEnabled(true);
		N1.setEnabled(true);
		menu2.setSelected(false);
		F_name2.setEnabled(true);
		F_price2.setEnabled(true);
		F_main2.setEnabled(true);
		F_sub2.setEnabled(true);
		Y2.setEnabled(true);
		N2.setEnabled(true);
	}

}