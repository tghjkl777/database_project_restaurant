package team12;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

/**
 * @author 이한나.
 * Additional_Restaurants_Second_Page를 구성하는 JPanel.
 * 메뉴 전반에 대한 정보를 받아 database상에 insert한다.
 */
public class Additional_Restaurants_Second_Page extends JPanel implements ActionListener {

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
	// 레스토랑 정보 저장 관련 객체 생성
	Restaurant_Info restaurant = Additional_Restaurants_First_Page.restaurant;
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
	 * Additional_Restaurants_Second_Page JPanel 설정.
	 * 하얀 배경에 절대 위치를 통해 버튼을 올렸다.
	 * 홈 버튼, 다음 페이지로 가는 버튼, 메뉴 정보를 입력하는 입력란들이 있다.
	 * 메뉴는 최대 2개까지 입력가능하며, 입력하지 않을 곳은 체크를 통해 block시키면 된다.
	 * 필수 입력란은 반드시 입력해야 한다.
	 * */
	Additional_Restaurants_Second_Page() {

		this.setBackground(Color.white); // 하얀 배경
		this.setLayout(null); // 절대 위치로 배치

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
		// 메뉴 추가 버튼
		menu1.setBounds(34, logo_height / 4 * 3 - 15, 20, 15);
		menu1.setBackground(Color.white);
		menu1.setToolTipText("이 메뉴란을 추가입력하시겠습니까?");
		menu1.addActionListener(this);
		menu2.setBounds(34, logo_height / 4 * 3 + 200, 20, 15);
		menu2.setBackground(Color.white);
		menu2.setToolTipText("이 메뉴란을 추가입력하시겠습니까?");
		menu2.addActionListener(this);

		// 1번 각종 내용물들 절대 위치 및 크기, 세부 속성 설정
		F_name1.setBounds(225, logo_height / 4 * 3 - 15, 220, 20);
		F_name1.setToolTipText("대표 메뉴 이름 입력란입니다. (※메뉴 자체 생략가능)");
		F_price1.setBounds(245, logo_height / 4 * 3 + 25, 200, 20);
		F_price1.setToolTipText("메뉴 가격 입력란입니다.");
		F_main1.setBounds(245, logo_height / 4 * 3 + 65, 200, 20);
		F_main1.setToolTipText("주 재료 입력란입니다.");
		F_sub1.setBounds(245, logo_height / 4 * 3 + 105, 200, 20);
		F_sub1.setToolTipText("부 재료 입력란입니다.");
		// Vegetarian 설정 버튼 생성
		Y1.setBounds(240, logo_height / 4 * 3 + 148, 20, 15);
		Y1.setBackground(Color.white);
		Y1.setToolTipText("비건 음식 여부 선택란입니다.");
		N1.setBounds(310, logo_height / 4 * 3 + 148, 20, 15);
		N1.setBackground(Color.white);
		N1.setToolTipText("비건 음식 여부 선택란입니다.");
		// Y, N 중 하나만 선택가능
		group1.add(Y1);
		group1.add(N1);
		// 비활성 상태
		F_name1.setEnabled(false);
		F_price1.setEnabled(false);
		F_main1.setEnabled(false);
		F_sub1.setEnabled(false);
		Y1.setEnabled(false);
		N1.setEnabled(false);

		// 2번 각종 내용물들 절대 위치 및 크기, 세부 속성 설정
		F_name2.setBounds(225, logo_height / 4 * 3 + 200, 220, 20);
		F_name2.setToolTipText("대표 메뉴 이름 입력란입니다. (※메뉴 자체 생략가능)");
		F_price2.setBounds(245, logo_height / 4 * 3 + 240, 200, 20);
		F_price2.setToolTipText("메뉴 가격 입력란입니다.");
		F_main2.setBounds(245, logo_height / 4 * 3 + 280, 200, 20);
		F_main2.setToolTipText("주 재료 입력란입니다.");
		F_sub2.setBounds(245, logo_height / 4 * 3 + 320, 200, 20);
		F_sub2.setToolTipText("부 재료 입력란입니다.");
		// Vegetarian 설정 버튼 생성
		Y2.setBounds(240, logo_height / 4 * 3 + 363, 20, 15);
		Y2.setBackground(Color.white);
		Y2.setToolTipText("비건 음식 여부 선택란입니다.");
		N2.setBounds(310, logo_height / 4 * 3 + 363, 20, 15);
		N2.setBackground(Color.white);
		N2.setToolTipText("비건 음식 여부 선택란입니다.");
		// Y, N 중 하나만 선택가능
		group2.add(Y2);
		group2.add(N2);
		// 비활성 상태
		F_name2.setEnabled(false);
		F_price2.setEnabled(false);
		F_main2.setEnabled(false);
		F_sub2.setEnabled(false);
		Y2.setEnabled(false);
		N2.setEnabled(false);

		// 내용물들 화면에 담기
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
		this.add(NEXT);
		this.add(menu1);
		this.add(menu2);

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

		// 색 강조 부분
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

	/**
	 * 버튼 조작 관련 함수.
	 * + NEXT버튼을 누르면 메뉴 정보를 database상에 insert한 후, 
	 * 팝업창을 통해 메뉴 입력 상태를 알려주고 Restaurant_List_page로 이동한다.
	 * 필수 입력창이 비어있을 시, 가격에 숫자가 아닌 문자열이 있을 시 등 에러 발생 시 팝업창으로 경고 문구를 띄운다.
	 * 창 이동 후에는 clearPage() 함수를 통해 창을 초기화시킨다.
	 * + HOME버튼을 누르면 HOME_Page로 이동한다.
	 * + menu1, menu2를 선택하면 해당 메뉴는 block처리 되어 메뉴 입력 X인 곳으로 간주한다.
	 * */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == NEXT) {

			// 사용자가 입력한 정보를 Restaurant_Info객체에 일단 저장한다
			getInfo();

			// 정보 입력에 문제가 없다면 데이터베이스에 그 정보를 저장하고 페이지를 이동시킨다.
			if (!isError) {

				// 데이터베이스에 정보 저장
				storeInfo();

				if (rollback == true) {
					clearPage();
					JOptionPane.showMessageDialog(null, "다시 시도해 주십시오.");
				} else {

					// 아무것도 선택되지 않았을 시 메뉴 페이지 생략 여부를 물음.
					if (!menu1.isSelected() && !menu2.isSelected()) {

						int confirm = JOptionPane.showConfirmDialog(null, "메뉴 페이지를 생략하시겠습니까?", "Do you want to omit?",
								JOptionPane.YES_NO_OPTION);

						if (confirm == JOptionPane.YES_OPTION) {
							clearPage();
							Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");
						}

					} else {
						// 선택된 메뉴가 있을 시 추가된 메뉴를 알려줌.
						String Message = null;
						if (menu1.isSelected() && menu2.isSelected())
							Message = "메뉴1, 메뉴2가 추가 입력되었습니다.";
						else if (menu1.isSelected() && !menu2.isSelected())
							Message = "메뉴1이 추가 입력되었습니다.";
						else if (!menu1.isSelected() && menu2.isSelected())
							Message = "메뉴2가 추가 입력되었습니다.";
						JOptionPane.showMessageDialog(null, Message);
						Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");
						clearPage();
					}
				}
			}
			// 정보 입력에 문제가 있다면 error발생 이유를 알려준다.
			else
				JOptionPane.showMessageDialog(null, ErrorMessage);

		}

		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");

		if (e.getSource() == menu1) {

			if (menu1.isSelected()) {
				// 활성 상태
				F_name1.setEnabled(true);
				F_price1.setEnabled(true);
				F_main1.setEnabled(true);
				F_sub1.setEnabled(true);
				Y1.setEnabled(true);
				N1.setEnabled(true);
			} else {
				// 비활성 상태
				F_name1.setEnabled(false);
				F_price1.setEnabled(false);
				F_main1.setEnabled(false);
				F_sub1.setEnabled(false);
				Y1.setEnabled(false);
				N1.setEnabled(false);
			}

		}

		if (e.getSource() == menu2) {

			if (menu2.isSelected()) {
				// 활성 상태
				F_name2.setEnabled(true);
				F_price2.setEnabled(true);
				F_main2.setEnabled(true);
				F_sub2.setEnabled(true);
				Y2.setEnabled(true);
				N2.setEnabled(true);
			} else {
				// 비활성 상태
				F_name2.setEnabled(false);
				F_price2.setEnabled(false);
				F_main2.setEnabled(false);
				F_sub2.setEnabled(false);
				Y2.setEnabled(false);
				N2.setEnabled(false);
			}

		}

	}

	/**
	 * 입력받은 레스토랑 정보를 Restaurant_Info 객체에 저장하는 함수
	 * 예외처리 필요 (음식 이름, 주재료, 비건 여부, 가격 숫자형태인지).
	 * 예외처리는 throw new를 이용해 errorMessage를 넘기는 방식으로 진행한다.
	 * 여기서의 errorMessage는 버튼기능에서 팝업창을 통해 경고 문구를 날릴 시 사용된다.
	 */
	public void getInfo() {

		try {

			// 초기화
			isError = false;
			ErrorMessage = null;
			restaurant.Clear_M();
			// 메뉴 아이디 생성
			restaurant.Create_M();

			// 예외처리 필요 -> 음식 이름, 주재료, 비건 여부, 가격 숫자형태인지
			if (menu1.isSelected()) {
				if (F_name1.getText().isEmpty())
					throw new Exception("Representative Food1은 필수 입력란입니다. (메뉴 이름을 입력하세요.)");
				restaurant.M_Food1 = F_name1.getText();
				if (!F_price1.getText().isEmpty() && !checkString(F_price1.getText()))
					throw new Exception("Price는 숫자여야 합니다.");
				else if (!F_price1.getText().isEmpty() && checkString(F_price1.getText()))
					restaurant.M_Price1 = Integer.parseInt(F_price1.getText());
				if (F_main1.getText().isEmpty())
					throw new Exception("Main ingredient는 필수 입력란입니다.");
				restaurant.M_main1 = F_main1.getText();
				restaurant.M_sub1 = F_sub1.getText();
				if (Y1.isSelected())
					restaurant.M_vege1 = "Y";
				else if (N1.isSelected())
					restaurant.M_vege1 = "N";
				else
					throw new Exception("Vegetarian은 필수 선택란입니다.");
			}

			if (menu2.isSelected()) {
				if (F_name2.getText().isEmpty())
					throw new Exception("Representative Food2는 필수 입력란입니다. (메뉴 이름을 입력하세요.)");
				restaurant.M_Food2 = F_name2.getText();
				if (!F_price2.getText().isEmpty() && !checkString(F_price2.getText()))
					throw new Exception("Price는 숫자여야 합니다.");
				else if (!F_price2.getText().isEmpty() && checkString(F_price2.getText()))
					restaurant.M_Price2 = Integer.parseInt(F_price2.getText());
				if (F_main2.getText().isEmpty())
					throw new Exception("Main ingredient는 필수 입력란입니다.");
				restaurant.M_main2 = F_main2.getText();
				restaurant.M_sub2 = F_sub2.getText();
				if (Y2.isSelected())
					restaurant.M_vege2 = "Y";
				else if (N2.isSelected())
					restaurant.M_vege2 = "N";
				else
					throw new Exception("Vegetarian은 필수 선택란입니다.");
			}

		} catch (Exception e) {

			isError = true;
			ErrorMessage = e.getMessage();

		}

	}

	/**
	 *  문자열이 숫자로 이루어져 있는지 확인
	 * @param str 숫자인지 확인하고 싶은 문자열
	 * @return 숫자면 true, 숫자가 아닌 문자열이면 false를 return한다.
	 *  함수는 Integer.parseInt(str) 코드 진행 시,
	 *  오류가 발생하면 catch문을 통해 return값이 false로 변하는 방식으로 진행한다.
	 */
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
			pstmt4 = null;
			rollback = false;

			int result1, result2, result3, result4;
			result1 = result2 = result3 = result4 = 0;

			if (menu1.isSelected()) {

				// Ingredients Table Info. (1)
				pstmt1 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_ingredients values(?,?,?,?,?)");
				pstmt1.setInt(1, restaurant.M_ID1);
				pstmt1.setString(2, restaurant.M_Food1);
				pstmt1.setString(3, restaurant.M_main1);
				pstmt1.setString(4, restaurant.M_sub1);
				pstmt1.setString(5, restaurant.M_vege1);
				result1 = pstmt1.executeUpdate();

				// Menu Table Info. (1)
				pstmt2 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_menu values(?,?,?,?)");
				pstmt2.setInt(1, restaurant.R_ID);
				pstmt2.setInt(2, restaurant.M_ID1);
				pstmt2.setString(3, restaurant.M_Food1);
				if (restaurant.M_Price1 == -1)
					pstmt2.setString(4, null);
				else
					pstmt2.setInt(4, restaurant.M_Price1);
				result2 = pstmt2.executeUpdate();
			} else {

				// Ingredients Table Info. (1)
				pstmt1 = DB_Connection.conn
						.prepareStatement("insert into DBCOURSE_ingredients values(?,null,null,null,null)");
				pstmt1.setInt(1, restaurant.M_ID1);
				result1 = pstmt1.executeUpdate();

				// Menu Table Info. (1)
				pstmt2 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_menu values(?,?,null,null)");
				pstmt2.setInt(1, restaurant.R_ID);
				pstmt2.setInt(2, restaurant.M_ID1);
				result2 = pstmt2.executeUpdate();

			}

			if (menu2.isSelected()) {

				// Ingredients Table Info. (2)
				pstmt3 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_ingredients values(?,?,?,?,?)");
				pstmt3.setInt(1, restaurant.M_ID2);
				pstmt3.setString(2, restaurant.M_Food2);
				pstmt3.setString(3, restaurant.M_main2);
				pstmt3.setString(4, restaurant.M_sub2);
				pstmt3.setString(5, restaurant.M_vege2);
				result3 = pstmt3.executeUpdate();

				// Menu Table Info. (2)
				pstmt4 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_menu values(?,?,?,?)");
				pstmt4.setInt(1, restaurant.R_ID);
				pstmt4.setInt(2, restaurant.M_ID2);
				pstmt4.setString(3, restaurant.M_Food2);
				if (restaurant.M_Price2 == -1)
					pstmt4.setString(4, null);
				else
					pstmt4.setInt(4, restaurant.M_Price2);
				result4 = pstmt4.executeUpdate();
			} else {

				// Ingredients Table Info. (1)
				pstmt3 = DB_Connection.conn
						.prepareStatement("insert into DBCOURSE_ingredients values(?,null,null,null,null)");
				pstmt3.setInt(1, restaurant.M_ID2);
				result3 = pstmt3.executeUpdate();

				// Menu Table Info. (1)
				pstmt4 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_menu values(?,?,null,null)");
				pstmt4.setInt(1, restaurant.R_ID);
				pstmt4.setInt(2, restaurant.M_ID2);
				result4 = pstmt4.executeUpdate();

			}

			// 자원반납
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			if (pstmt3 != null)
				pstmt3.close();
			if (pstmt4 != null)
				pstmt4.close();

			// 네 개다 성공했을 시에만 commit
			if (result1 * result2 * result3 * result4 > 0)
				DB_Connection.conn.commit();
			else {
				DB_Connection.conn.rollback();
				System.out.println("Additional_Restaurants_Second_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// 예외 발생시 롤백
			try {
				DB_Connection.conn.rollback();
				System.out.println("Additional_Restaurants_Second_Page - rollback");
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
		F_name1.setEnabled(false);
		F_price1.setEnabled(false);
		F_main1.setEnabled(false);
		F_sub1.setEnabled(false);
		Y1.setEnabled(false);
		N1.setEnabled(false);
		menu2.setSelected(false);
		F_name2.setEnabled(false);
		F_price2.setEnabled(false);
		F_main2.setEnabled(false);
		F_sub2.setEnabled(false);
		Y2.setEnabled(false);
		N2.setEnabled(false);
	}

}