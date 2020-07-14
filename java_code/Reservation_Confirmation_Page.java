package team12;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @author 이한나.
 * 예약 확인 페이지.
 */
public class Reservation_Confirmation_Page extends JPanel implements ActionListener {

	// 필요 아이콘, 내용물, 변수 선언
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
	JButton NEXT = new JButton(next);
	JButton Enter = new JButton();
	JButton B_temp;
	JButton[] Header = new JButton[4];
	JTextField Search = new JTextField();
	JPanel search_table0 = new JPanel();
	JPanel search_table1 = new JPanel();
	JPanel search_table2 = new JPanel();
	JScrollPane SearchPane = new JScrollPane(search_table0);
	String header[] = { "Booking ID", "Restaurant", "Reserv_Day", "Reserv_Time" };
	String search_phone_num;
	ArrayList<String> search_info = new ArrayList<>(); // 검색 결과 정보를 담을 변수 선언

	Statement stmt;
	ResultSet rs;
	int exist_result = 0; // 결과가 없을 경우를 대비해 만든 변수.

	/**
	 * 예약 확인 panel 설정.
	 * 하얀 배경에 절대 위치를 통해 버튼을 올렸다.
	 * 검색 버튼, 홈 버튼, 다음 페이지 버튼, 검색란, 결과란이 있다.
	 */
	Reservation_Confirmation_Page() {

		this.setBackground(Color.white); // 하얀 배경
		this.setLayout(null); // 절대 위치로 배치

		// 검색란 설정
		Search.setBounds(105, logo_height / 4 * 3, 200, 30);
		Search.setToolTipText("000-0000-0000 형식으로 전화번호를 입력하세요.");
		Search.setHorizontalAlignment(JTextField.CENTER);
		// 검색 버튼 설정
		Enter.setBounds(315, logo_height / 4 * 3, 80, 30);
		Enter.setText("Search");
		Enter.setBackground(Color.WHITE);
		Enter.setToolTipText("Search. 검색.");
		Enter.addActionListener(this);
		// 홈 버튼 설정
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. 홈 페이지로. (Color Change)");
		HOME.setBorderPainted(false); // 버튼 경계선 없애기
		HOME.addActionListener(this);
		// 다음 페이지로 가는 버튼 설정
		NEXT.setBounds(436, logo_height * 3 - 60, next_width / 8, next_height / 8);
		NEXT.setBackground(Color.WHITE);
		NEXT.setToolTipText("Next. 다음 페이지로.");
		NEXT.setBorderPainted(false); // 버튼 경계선 없애기
		NEXT.addActionListener(this);
		// 결과란 설정
		search_table1.setLayout(new GridLayout(1, 4));
		for (int i = 0; i < 4; i++) {
			Header[i] = new JButton(header[i]);
			Header[i].setBackground(new Color(224, 247, 239));
			search_table1.add(Header[i]);
		}
		Header[0].setToolTipText("예약번호");
		Header[1].setToolTipText("레스토랑 이름");
		Header[2].setToolTipText("예약 날짜");
		Header[3].setToolTipText("예약 시간");
		search_table2.setLayout(null);
		search_table2.setBackground(Color.WHITE);
		search_table0.setLayout(new BorderLayout());
		search_table0.add(search_table1, BorderLayout.NORTH);
		search_table0.add(search_table2, BorderLayout.CENTER);
		SearchPane.setBounds(30, logo_height + 30, 439, 250);
		SearchPane.setToolTipText("검색 결과란입니다.");

		// panel에 내용물 붙임
		this.add(HOME);
		this.add(NEXT);
		this.add(Search);
		this.add(Enter);
		this.add(SearchPane);

	}

	/**
	 * Panel에 그리기.
	 * 글씨와 그림들을 그린다.
	 * */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// 위, 아래 부분
		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);
		g.drawImage(next_img, getWidth() - next_width / 2, getHeight() - logo_height / 2, next_width / 2,
				next_height / 2, this);
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Confirm your reservation.", logo_width / 2 + 55, logo_height / 4 + 10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		g.drawString("예약 정보를 확인하세요.", logo_width / 5 * 4 + 18, logo_height / 5 * 2 + 10);
		
		// 검색 결과에 따라 처리
		if (exist_result == -1) {
			g.setColor(new Color(243, 128, 137));
			g.setFont(new Font("맑은 고딕", Font.BOLD, 11));
			g.drawString("결과 없음. >", logo_width / 2 + 105, logo_height / 4 * 3 + 50);
			g.setFont(new Font("Calibri", Font.BOLD, 13));
			g.drawString("< No results.", logo_width / 2 + 30, logo_height / 4 * 3 + 50);
		} else if (exist_result == 0) {
			g.setColor(new Color(243, 128, 137));
			g.setFont(new Font("Calibri", Font.BOLD, 13));
			g.drawString("< Enter your phone number. >", logo_width / 2 + 20, logo_height / 4 * 3 + 50);
		}

	}

	/**
	 * 버튼 조작 관련 함수.
	 * + Enter버튼을 누르면 검색란의 text를 받아서 데이터베이스 상에서 정보를 검색해, 그 결과를 받아와 창에 입력한다.
	 * + HOME버튼을 누르면 HOME_Page로 이동한다.
	 * + NEXT버튼을 누르면 다음 페이지(Select_Mode_User)로 이동한다.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == NEXT) {
			// 화면 초기화
			Search.setText(null);
			search_table2.removeAll();
			exist_result = 0;
			// 다음 페이지로
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "11");
		}

		if (e.getSource() == HOME) {
			// 화면 초기화
			Search.setText(null);
			search_table2.removeAll();
			exist_result = 0;
			// 홈 페이지로
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		}

		if (e.getSource() == Enter) {

			// 검색 대상인 전화 번호 저장
			search_phone_num = Search.getText();

			// 검색란이 null이 아닌 경우에 기능 수행
			if (search_phone_num != null) {

				// 검색 결과란 초기화
				search_table2.removeAll();

				// 검색한다
				Reservation_Search();

				// 검색 결과를 결과란 panel에 넣는다.
				int width = 0, height = 0;
				for (int i = 0; i < search_info.size(); i++) {
					B_temp = new JButton(search_info.get(i));
					B_temp.setFont(new Font("맑은 고딕", Font.BOLD, 10));
					B_temp.setToolTipText(search_info.get(i));
					B_temp.setBounds(width, height, 100, 30);
					B_temp.setBackground(Color.WHITE);
					B_temp.setBorderPainted(false);
					search_table2.add(B_temp);
					width = width + 110;
					if ((i + 1) % 4 == 0) {
						width = 0;
						height = height + 30;
					}
				}

			} else
				exist_result = 0;

			// 바뀐 부분을 창에 업데이트한다.
			SearchPane.updateUI();
			repaint();
		}

	}

	/**
	 * 예약 확인을 위한 검색 함수.
	 * 데이터베이스에 접속해 select를 통해 검색란에 적힌 전화번호에 해당하는 예약이 있는지 확인한다.
	 */
	public void Reservation_Search() {

		try {
			// 초기화
			exist_result = 0;
			search_info.clear();

			// 쿼리 결과를 받아온다.
			stmt = DB_Connection.stmt;
			rs = stmt.executeQuery("select * from DBCOURSE_confirm_reservation_view order by Booking_ID");

			// 검색 결과를 ArrayList 변수에 저장한다.
			while (rs.next()) {
				if (rs.getString("Phone_Number").equals(search_phone_num)) {
					search_info.add(rs.getString(1));
					search_info.add(rs.getString(2));
					search_info.add(rs.getString(3));
					search_info.add(rs.getString(4));
					exist_result = 1;
				}

			}

			// 결과가 없을 경우 변수 조정을 통해 이를 알린다. 위의 public void paintComponent(Graphics g) 함수 참고.
			if (exist_result != 1)
				exist_result = -1;

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}