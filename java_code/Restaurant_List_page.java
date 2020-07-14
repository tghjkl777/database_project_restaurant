package team12;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * @author 박지영.
 * Restaurant_List_page를 구성하는 JPanel.
 * 레스토랑 리스트들을 화면에 보여주고 이에 대한 수정 추가 삭제를 해준다.
 */
public class Restaurant_List_page extends JPanel implements ActionListener {
	static String Name;
	// 아이콘 사진 선언
	ImageIcon logo_icon = Main_Frame.home_page.logo_icon;
	Image logo_img = logo_icon.getImage();
	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;

	// 테이블 필요 요소 선언
	DefaultTableModel model;
	JTable table;
	JScrollPane scrollPane;

	// DB내용 불러오기 위한 필요벡터 선언
	Vector data; // 한줄 데이터
	Vector title; // 위에 타이틀
	Vector result; // 전체 결과 데이터

	// DB연동, 쿼리 위한 필요 선언
	Connection conn;
	Statement stmt; // 데이타 연동후 바로 조회하는 변수
	PreparedStatement pstRemove; // 삭제 쿼리 실행하는 객체변수
	PreparedStatement pstUpdate;// 수정 쿼리 실행하는
	ResultSet rs;
	int exist_result = 0;

	// 필요 버튼 선언
	JButton add_B, remove_B, fix_B;
	JButton HOME = new JButton(logo_icon);
	
	// 수정 버튼 시 R_ID값이 넘어가도록 변수에 select_R_ID값 담아두기.
	static int select_R_ID;
	
	
	/** 
	 * Restaurant_List_page JPanel 설정.
	 * 하얀 배경에 절대 위치를 통해 버튼을 올렸다.
	 * 홈 버튼, 추가 삭제  수정 버튼, 레스토랑 리스트가 뜨는 테이블이 있다.
	 * */
	Restaurant_List_page() {
		// TODO Auto-generated constructor stub

		this.setBackground(Color.white); // 하얀 배경
		this.setLayout(null); // 절대 위치로 배치

		// 홈버튼 설정&붙이기
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. 홈 페이지로.");
		HOME.setBorderPainted(false); // 버튼 경계선 없애기
		HOME.addActionListener(this);
		this.add(HOME);

		data = new Vector<>();
		title = new Vector<>();

		// 테이플 타이틀 이름 선언
		title.add("Restaurant_ID");
		title.add("Restaurant_Name");

		model = new DefaultTableModel();
		result = List_Search();
		model.setDataVector(result, title);

		// 테이블 설정&붙이기
		table = new JTable(model);
		table.setForeground(new Color(0,70,42));
		table.setGridColor(Color.WHITE);
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.setRowHeight(40);
		
		//데이터들을 셀 가운데로 정렬되게
		Center();

		// 테이블 타이들 크키,색깔들 설정
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(224, 247, 239));
		header.setForeground(new Color(0, 70, 42));
		header.setFont(new Font("Calibri", Font.PLAIN, 18));

		// 스크롤 판에 붙이기
		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBounds(20, logo_width / 2 + 30, 460, 350);
		this.add(scrollPane);

		// 버튼 이름 설정
		remove_B = new JButton("삭제");
		fix_B = new JButton("수정");

		// 추가 버튼 설정&붙이기
		add_B = new JButton("추가");
		add_B.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		add_B.setBackground(new Color(0, 70, 42));
		add_B.setForeground(Color.WHITE);
		add_B.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add_B.setBounds(200, logo_width / 2 + 405, 80, 30);
		this.add(add_B);

		// 삭제 버튼 설정&붙이기
		remove_B.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		remove_B.setBackground(new Color(0, 70, 42));
		remove_B.setForeground(Color.WHITE);
		remove_B.setCursor(new Cursor(Cursor.HAND_CURSOR));
		remove_B.setBounds(300, logo_width / 2 + 405, 80, 30);
		this.add(remove_B);

		// 수정 버튼 설정&붙이기
		fix_B.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		fix_B.setBackground(new Color(0, 70, 42));
		fix_B.setForeground(Color.WHITE);
		fix_B.setCursor(new Cursor(Cursor.HAND_CURSOR));
		fix_B.setBounds(400, logo_width / 2 + 405, 80, 30);
		this.add(fix_B);

		// OptionPane 디자인 설정
		UIManager UI = new UIManager();
		UI.put("OptionPane.background", Color.WHITE);
		UIManager.put("Panel.background", Color.WHITE);
		
		// 버튼들 액션리스너 설정
		add_B.addActionListener(this);
		remove_B.addActionListener(this);
		fix_B.addActionListener(this);
		HOME.addActionListener(this);

	}
	/**
	 * 출력되는 DB데이터들 테이블에 가운데 정렬로 나오게 하는 함수
	 */
	public void Center()
	{
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setCellRenderer(center);
		tcm.getColumn(1).setCellRenderer(center);
	}

	/**
	 * Panel에 그리기.
	 * 글씨와 그림들을 그린다.
	 * */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Restaurant List", logo_width / 2 + 114, logo_height / 4 - 5);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		g.drawString("현재 레스토랑 목록입니다.", logo_width / 5 * 4 + 29, logo_height / 5 * 2 - 5);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+편집을 원하면 목록에서 선택 후 해당 버튼을 눌러주세요.)", logo_width / 2 + 20, logo_height / 5 * 2 + 20);

	}

	
	/**
	 * 버튼 조작 관련 함수.
	 * 추가버튼을 누르면 레스토랑 추가를 위한 페이지로넘어간다.
	 * 테이블에서 레스토랑을 선택하고 삭제버튼을 누르면  확인창을 띄우고 해당 레스토랑을 삭제한다.
	 * 레스토랑을 테이브에서 선택하고 수정버튼을 누르면 해당 레스토랑 수정 페이지로 넘어간다.
	  * */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		//추가 버튼 기능
		if (e.getSource() == add_B) {

			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "21");

		}
		//삭제 버튼 기능
		if (e.getSource() == remove_B) {
			int confirm = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION && table.getSelectedRow() != -1) {

				int index = table.getSelectedRow(); // 테이블에서 선택택 줄의 값을 가지고 옴
				Vector in = (Vector) data.get(index); // 선택된 줄의 값들을 벡터 하나에 받아온다.

				int ID = (int) in.get(0);

				Remove(ID);

				result = List_Search();
				model.setDataVector(result, title);
				
				Center();

			}
			// 선택된 테이블 row가 없을 시 선택된 값이 없다고 알림.
			else if (confirm == JOptionPane.YES_OPTION && table.getSelectedRow() == -1) {

				JOptionPane.showMessageDialog(null, "선택된 값이 없습니다.");

			}

		}

		// 수정 버튼 기능
		if (e.getSource() == fix_B) {

			// 선택된 테이블 row가 없을 시 선택된 값이 없다고 알림.
			if (table.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(null, "선택된 값이 없습니다.");
			// 있을 경우 수정 페이지로 넘어감.
			else {
				int index = table.getSelectedRow(); // 테이블에서 선택택 줄의 값을 가지고 옴
				Vector in = (Vector) data.get(index); // 선택된 줄의 값들을 벡터 하나에 받아온다.
				select_R_ID = (int) in.get(0);
				Main_Frame.modify_first_page.create_R_ID();
				Main_Frame.modify_first_page.getInfo(select_R_ID);
				Main_Frame.modify_first_page.getHoliday(select_R_ID);
				Main_Frame.modify_first_page.SetText(Main_Frame.modify_first_page.restaurant_info);
				Main_Frame.modify_first_page.SetCheckBox(Main_Frame.modify_first_page.holiday);
				Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "23");
			}
		}

		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
	}
	/**
	 *  데이터 리스트들 불러오는 함수
	 *  데이터베이스에 들어있는 정보를 벡터로 받아온다.
	 *  예외발생 또는 실행 실패 시 롤백 한다.
	 */
	Vector List_Search() {

		try {

			data.clear();
			stmt = DB_Connection.stmt;

			rs = stmt.executeQuery("select Restaurant_ID,Restaurant_Name  from DBCOURSE_Restaurants ");

			while (rs.next()) {

				Vector in = new Vector<String>();

				int ID = rs.getInt(1);

				String Name = rs.getString(2);

				in.add(ID);
				in.add(Name);

				data.add(in);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	
	/**
	 *  데이터 삭제 함수
	 *  해당 레스토랑을 삭제해준다. 
	 */
	void Remove(int ID) {

		try {
			conn = DB_Connection.conn;
			conn.setAutoCommit(false);//트랜잭션 설정

			pstRemove = conn.prepareStatement("delete from DBCOURSE_Restaurants where Restaurant_ID = ?");
			pstRemove.setInt(1, ID);
			pstRemove.executeUpdate();

			pstRemove = conn
					.prepareStatement("delete from DBCOURSE_Ingredients where menu_ID between ?*100 and ?*100+10");
			pstRemove.setInt(1, ID);
			pstRemove.setInt(2, ID);
			pstRemove.executeUpdate();
			conn.commit();//위 두쿼리가 성공했을때만 commit 된다.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();// 쿼리 실패시 롤백 된다.
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}


}