package team12;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * @author 이한나.
 * cardLayout을 통해 여러 패널들의 이동을 구현하는 JFrame.
 * WindowListener를 통해 창을 닫을 시,
 * DB_Connection 클래스의 closeDB() 함수를 통해 database와의 연결을 종료한다.
 * 
 */
public class Main_Frame extends JFrame implements WindowListener {
	
	// 필요한 변수 선언
	static DB_Connection CONNECTION;
	static Main_Frame R_frame;
	static CardLayout R_card = new CardLayout(0, 0);
	static Restaurant_List_page list_page;
	static HOME_Page home_page;
	static Modify_first_page modify_first_page;
	static Modify_second_page modify_second_page;
	static Review review;
	static Restaurant_Information restaurant_information;
	static Search_Menu_Page search_menu_page;
	static Make_Reservation make_reservation;
	static Search_Restaurant_Page search_restaurant_page;

	/** 
	 *  Frame 설정.
	 *  cardLayout을 이용한다.
	 *  cardLayout을 통해 사용한 panel들을 추가한다.
	 *  
	 *  */
	Main_Frame() {

		// 기본 설정
		setSize(500, 620);
		setTitle("Additional Restaurant Insertion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(R_card);
		// frame이 화면 중앙에 오도록 배치
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

		// 카드레이아웃에 각 판넬 추가
		home_page = new HOME_Page();
		home_page.add(new Label("home page"));
		Additional_Restaurants_First_Page insert_restaurant_first_page = new Additional_Restaurants_First_Page();
		insert_restaurant_first_page.add(new Label("insert restaurant first page"));
		Additional_Restaurants_Second_Page insert_restaurant_second_page = new Additional_Restaurants_Second_Page();
		insert_restaurant_second_page.add(new Label("insert restaurant second page"));
		Reservation_Confirmation_Page reserv_confirm_page = new Reservation_Confirmation_Page();
		reserv_confirm_page.add(new Label("reservation confirmation page"));
		list_page = new Restaurant_List_page();
		list_page.add(new Label("list page"));
		Select_Mode_page select_mode_page = new Select_Mode_page();
		select_mode_page.add(new Label("select mode page"));
		modify_first_page = new Modify_first_page();
		modify_first_page.add(new Label("modify first page"));
		modify_second_page = new Modify_second_page();
		modify_second_page.add(new Label("modify second page"));
		restaurant_information = new Restaurant_Information();
		restaurant_information.add(new Label("restaurant information page"));
		search_menu_page = new Search_Menu_Page();
		search_menu_page.add(new Label("search menu page"));
		review = new Review();
		review.add(new Label("review page"));
		search_restaurant_page = new Search_Restaurant_Page();
		search_restaurant_page.add(new Label("search restaurant page"));
		make_reservation = new Make_Reservation();
		make_reservation.add(new Label("make reservation"));
		Select_Mode_User select_mode_user = new Select_Mode_User();
		select_mode_user.add(new Label("select_mode_user"));

		add(home_page, "0");
		add(select_mode_page, "1");
		add(review, "12");
		add(select_mode_user, "11");
		add(restaurant_information, "13");
		add(search_menu_page, "14");
		add(search_restaurant_page, "15");
		add(make_reservation, "16");
		add(reserv_confirm_page, "19");
		add(list_page, "20");
		add(insert_restaurant_first_page, "21");
		add(insert_restaurant_second_page, "22");
		add(modify_first_page, "23");
		add(modify_second_page, "24");

		this.addWindowListener((WindowListener) this);

		// Frame 속성 설정
		setResizable(false); // 크기 고정
		setVisible(true); // 보이게 설정
	}

	/**
	 *  메인 함수,
	 *  DB_Connection 클래스의 connectDB() 함수를 통해
	 *  Mysql과의 연결을 맺고 Main_Frame을 생성해 창을 띄운다.
	 * */
	public static void main(String[] args) {

		// DB연결
		CONNECTION = new DB_Connection();
		CONNECTION.connectDB();

		R_frame = new Main_Frame();

	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	/** 창을 닫으면 DB_Connection 클래스의 closeDB() 함수를 통해 mysql과의 연결을 종료한다.*/
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		CONNECTION.closeDB();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

}
