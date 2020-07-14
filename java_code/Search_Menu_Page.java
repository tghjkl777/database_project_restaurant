package team12;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
/**
 * 찾고싶은 메뉴를 검색하거나 메뉴 리스트를 확인 할 수 있는 클래스
 * @author 김민지
 * 
 */
public class Search_Menu_Page extends JPanel implements ActionListener {
	ImageIcon logo_icon = new ImageIcon("images/logo.jpg");
	ImageIcon next_icon = new ImageIcon("images/NextUI.jpg");
	Image logo_img = logo_icon.getImage();
	Image next_img = next_icon.getImage();
	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;
	int next_width = next_img.getWidth(this);
	int next_height = next_img.getHeight(this);
	JButton HOME = new JButton(logo_icon);
	JButton Enter = new JButton();
	JButton B_temp;
	JButton Select = new JButton();
	JTextField Search = new JTextField();
	JPanel search_table0 = new JPanel();
	JPanel search_table1 = new JPanel();
	JPanel search_table2 = new JPanel();
	String search_menu;
	// 다음 클래스로 넘겨주기위해 static으로 선언
	static String select_R;
	// 테이블 필요 요소 선언
	DefaultTableModel model = new DefaultTableModel();
	JTable table;
	JScrollPane scrollPane;

	// DB내용 불러오기 위한 필요벡터 선언
	Vector data = new Vector<>(); // 한줄 데이터
	Vector title = new Vector<>(); // 위에 타이틀
	Vector result = new Vector<>(); // 전체 결과 데이터

	Statement stmt; // 데이타 연동후 바로 조회하는 변수
	PreparedStatement pstmRe; // 삭제 후
	ResultSet rs;
	int exist_result = 0;

	// 리스트 확인 panel 설정
	Search_Menu_Page() {
		// TODO Auto-generated constructor stub

		this.setBackground(Color.white); // 하얀 배경
		this.setLayout(null); // 절대 위치로 배치

		// 검색란 설정
		Search.setBounds(105, logo_height / 4 * 3, 200, 30);
		Search.setToolTipText("메뉴 이름을 입력하세요");
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
		// 선택 버튼 설정
		Select.setBounds(200, logo_height * 3 - 80, next_width / 2, next_height / 8);
		Select.setBackground(Color.WHITE);
		Select.setToolTipText("Select. 선택.");
		Select.setText("Select");
		Select.addActionListener(this);

		// 전체 메뉴 목록 받아오기
		result = List_Search();
		title.add("Menu");
		title.add("Restaurant_Name");
		model.setDataVector(result, title);

		// 테이블 설정&붙이기
		table = new JTable(model);
		table.setForeground(new Color(0, 70, 42));
		table.setGridColor(Color.WHITE);
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.setRowHeight(40); // 테이블 셀 높이 설정
		Center();

		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(224, 247, 239));
		header.setForeground(new Color(0, 70, 42));
		header.setFont(new Font("Calibri", Font.PLAIN, 18));

		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBounds(20, logo_width / 2 + 120, 460, 270);

		this.add(scrollPane);
		this.add(HOME);
		this.add(Search);
		this.add(Enter);
		this.add(Select);

		UIManager UI = new UIManager();
		UI.put("OptionPane.background", Color.WHITE);
		UIManager.put("Panel.background", Color.WHITE);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 위, 아래 부분
		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Search Menu.", logo_width / 2 + 85, logo_height / 4 + 10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		g.drawString("메뉴를 검색하세요.", logo_width / 5 * 4 + 18, logo_height / 5 * 2 + 10);

		if (exist_result == -1) {
			g.setColor(new Color(243, 128, 137));
			g.setFont(new Font("맑은 고딕", Font.BOLD, 11));
			g.drawString("결과 없음. >", logo_width / 2 + 105, logo_height / 4 * 3 + 50);
			g.setFont(new Font("Calibri", Font.BOLD, 13));
			g.drawString("< No results.", logo_width / 2 + 30, logo_height / 4 * 3 + 50);
		} else if (exist_result == 0) {
			g.setColor(new Color(243, 128, 137));
			g.setFont(new Font("Calibri", Font.BOLD, 13));
			g.drawString("< Enter menu name. >", logo_width / 2 + 20, logo_height / 4 * 3 + 50);
		}
	}

	/**
	 * 출력되는 DB데이터들 테이블에 가운데 정렬로 나오게 해주는 함수
	 */
	public void Center() {
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setCellRenderer(center);
		tcm.getColumn(1).setCellRenderer(center);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == Enter) {
			search_menu = Search.getText();
			if (search_menu != null) {
				result = Menu_Search(search_menu);
				scrollPane.updateUI();
				repaint();
			}
		}
		if (e.getSource() == HOME) {
			Search.setText(null);
			result = List_Search();
			model = new DefaultTableModel();
			model.setDataVector(result, title);
			exist_result = 0;
			Center();
			scrollPane.updateUI();
			repaint();
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		}

		// 선택 버튼이 눌러졌을 때, 결과 검색
		if (e.getSource() == Select) {
			Vector in = (Vector) data.get(table.getSelectedRow());
			select_R = (String) in.get(1);
			Search.setText(null);
			result = List_Search();
			model = new DefaultTableModel();
			model.setDataVector(result, title);
			exist_result = 0;
			Center();
			scrollPane.updateUI();
			repaint();
			Main_Frame.restaurant_information.getInfo(select_R);
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "13");
		}
	}

	/**
	 * 데이터 리스트들 불러오는 함수
	 * @return Vector 메뉴, 레스토랑 이름
	 */
	public Vector List_Search() {

		try {
			data.clear();
			stmt = DB_Connection.stmt;
			rs = stmt.executeQuery(
					"select Representative_Food,Restaurant_Name from DBCOURSE_Restaurants,DBCOURSE_Menu where DBCOURSE_Menu.Restaurant_ID=DBCOURSE_Restaurants.Restaurant_ID");

			while (rs.next()) {

				if (rs.getString(1) != null) {
					Vector in = new Vector<String>();
					String Menu = rs.getString(1);
					String Name = rs.getString(2);
					in.add(Menu);
					in.add(Name);
					data.add(in);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 메뉴 찾는 함수
	 * @param menu 검색하고 싶은 메뉴이름
	 * @return Vector 검색한 메뉴이름, 그 메뉴를 판매하는 레스토랑 이름
	 */
	public Vector Menu_Search(String menu) {
		try {

			data.clear();
			stmt = DB_Connection.stmt;
			String insert = "select T.Representative_food, R.Restaurant_Name "
					+ "from DBCOURSE_Restaurants as R, DBCOURSE_Menu as T where R.Restaurant_ID = T.Restaurant_ID and "
					+ "T.Representative_food=(select Representative_food from DBCOURSE_Menu  where Representative_food='"
					+ menu + "')";

			rs = stmt.executeQuery(insert);
			while (rs.next()) {

				Vector in = new Vector<String>();

				String Menu = rs.getString(1);
				String Name = rs.getString(2);

				in.add(Menu);
				in.add(Name);
				data.add(in);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}