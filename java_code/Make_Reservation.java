package team12;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.swing.*;

/**
 * 레스토랑 예약을 위한 페이지
 * @author 박수빈
 *
 */
public class Make_Reservation extends JPanel implements ActionListener{
	
	//필요 아이콘, 내용물, 변수 선언
	ImageIcon logo_icon = new ImageIcon("images/logo.jpg");
	ImageIcon next_icon = new ImageIcon("images/NextUI.jpg");
	Image logo_img = logo_icon.getImage();
	int logo_width = logo_img.getWidth(this)*2;
	int logo_height = logo_img.getHeight(this)*2;
	JButton HOME = new JButton(logo_icon);
	String[] hour = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
	String[] minute = {"00", "10", "20", "30", "40", "50"};
	JTextField restaurant_name = new JTextField();
	JTextField date_of_event_Y = new JTextField();
	JTextField date_of_event_M = new JTextField();
	JTextField date_of_event_D = new JTextField();
	JComboBox reservation_hour = new JComboBox(hour);
	JComboBox reservation_minute = new JComboBox(minute);
	JTextField customer_phone = new JTextField();
	JTextField customer_age = new JTextField();
	int age;
	ButtonGroup group = new ButtonGroup();
	JRadioButton female = new JRadioButton();
	JRadioButton male = new JRadioButton();
	JButton Okay=new JButton("확인");
	static Booking_Info booking = new Booking_Info();
	// For Connection.
	PreparedStatement pstmt;
	// 에러 관련 변수
	String ErrorMessage;
	boolean isError = false;
	boolean rollback =false;
	
	Insets insets = new Insets(0,0,0,0);
	
	//Panel 설정
	Make_Reservation(){
		
		this.setBackground(Color.white);	//하얀 배경
		this.setLayout(null);	//절대 위치로 배치
		
		//각종 내용물들 절대 위치 및 크기, 세부 속성 설정
		//레스토랑 이름 입력
		restaurant_name.setBounds(190, logo_height/4*3-15, 250, 20);
		restaurant_name.setToolTipText("레스토랑 이름 입력란입니다.");
		//예약 날짜 입력
		date_of_event_Y.setBounds(190, logo_height/4*3+55, 50, 20);
		date_of_event_Y.setToolTipText("예약 년도 입력란입니다");
		date_of_event_M.setBounds(275, logo_height/4*3+55, 50, 20);
		date_of_event_M.setToolTipText("예약 달 입력란입니다");
		date_of_event_D.setBounds(360, logo_height/4*3+55, 50, 20);
		date_of_event_D.setToolTipText("예약 날 입력란입니다");
		//예약 시간 설정 
		reservation_hour.setBounds(190, logo_height/4*3+125, 40, 20);
		reservation_hour.setBackground(Color.white);
		reservation_hour.setToolTipText("예약 시간 입력란입니다.");
		reservation_minute.setBounds(250, logo_height/4*3+125, 40, 20);
		reservation_minute.setBackground(Color.white);
		reservation_minute.setToolTipText("예약 시간 입력란입니다.");
		//전화번호 설정
		customer_phone.setBounds(190, logo_height/4*3+195, 250, 20);
		customer_phone.setToolTipText("전화번호 입력란입니다.");
		//나이 입력
		customer_age.setBounds(190, logo_height/4*3+265, 250, 20);
		customer_age.setToolTipText("나이 이름 입력란입니다.");
		//성별 설정
		female.setBounds(185, logo_height/4*3+335, 20, 15);
		female.setBackground(Color.white);
		female.setToolTipText("성별 여부 선택란입니다.");
		male.setBounds(300, logo_height/4*3+335, 20, 15);
		male.setBackground(Color.white);
		male.setToolTipText("성별 여부 선택란입니다.");
		group.add(female);
		group.add(male);
		
		//홈 버튼 설정
		HOME.setBounds(5, 5, logo_width/2, logo_height/2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. 홈 페이지로.");
		HOME.setBorderPainted(false);	//버튼 경계선 없애기
		HOME.addActionListener(this);
		//예약 확인 설정
		Okay.setBounds(430, 530, 40, 30);
		Okay.setMargin(insets);
		Okay.setBackground(Color.WHITE);
		Okay.addActionListener(this);
		
		//내용물들 화면에 담기
		this.add(restaurant_name);
		this.add(reservation_hour);
		this.add(date_of_event_Y);
		this.add(date_of_event_M);
		this.add(date_of_event_D);
		this.add(customer_phone);
		this.add(customer_age);
		this.add(reservation_minute);
		this.add(female);
		this.add(male);
		this.add(HOME);
		this.add(Okay);
		
	}
			
	//Panel에 그리기
	public void paintComponent(Graphics g) {
				
		super.paintComponent(g);
				
		//위, 아래 부분
		g.setColor(new Color(0,70,42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Make an Reservation.", logo_width/2+15, logo_height/4-10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		g.drawString("레스토랑 예약을 진행하세요.", logo_width/5*4+25, logo_height/5*2-10);
		g.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+는 필수 입력란입니다.)", logo_width/5*4+53, logo_height/5*2+15);
		
		//중간 부분
		g.setColor(new Color(0,49,39));
		g.setFont(new Font("Calibri", Font.PLAIN, 15));
		g.drawString("+   Restaurant Name", 40, logo_height/4*3);
		g.drawString("+   Date Of Event", 40, logo_height/4*3+70);
		g.drawString("(Y)", 245, logo_height/4*3+70);
		g.drawString("(M)", 330, logo_height/4*3+70);
		g.drawString("(D)", 415, logo_height/4*3+70);
		g.drawString("+   Time Of Event", 40, logo_height/4*3+140);
		g.drawString(":", 238, logo_height/4*3+140);
		g.drawString("+   Phone Number", 40, logo_height/4*3+210);
		g.drawString("+   Age", 40, logo_height/4*3+280);
		g.drawString("+   Gender", 40, logo_height/4*3+350);
		g.drawString("Female", 215, logo_height/4*3+350);
		g.drawString("Male", 330, logo_height/4*3+350);
	
		
		//색 강조 부분
		g.setColor(new Color(243,128,137));
		g.drawString("+", 40, logo_height/4*3);
		g.drawString("+", 40, logo_height/4*3+70);
		g.drawString("+", 40, logo_height/4*3+140);
		g.drawString("+", 40, logo_height/4*3+210);
		
	}
	

	//버튼 조작 관련 함수
	public void actionPerformed(ActionEvent e) {

		// okay버튼 기능
		if (e.getSource() == Okay) {
			
			// 사용자가 입력한 정보를 Booking_Info객체에 일단 저장한다
			getInfo();

			// 정보 입력에 문제가 없다면 데이터베이스에 그 정보를 저장
			if (!isError) {
				
				// 데이터베이스에 정보 저장
				storeInfo();
				
				// 창 초기화
				clearPage();
				
				if(rollback ==false) JOptionPane.showMessageDialog(null, "예약완료");
				
				Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
				
				
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
	 * 입력받은 예약 정보를 Booking_Info 객체에 저장하는 함수
	 */
	public void getInfo() {

		try {

			// 초기화
			isError = false;
			ErrorMessage = null;
			booking.Clear_B();

			// 이름은 필수 사항 -> 예외처리 필요
			if (restaurant_name.getText().isEmpty())
				throw new Exception("Restaurant Name은 필수 입력란입니다.");
			booking.restaurant_name = restaurant_name.getText();
			
			// 예약날짜는 필수 사항 -> 예외처리 필요
			if (date_of_event_Y.getText().isEmpty()||date_of_event_M.getText().isEmpty()||date_of_event_D.getText().isEmpty())
				throw new Exception("Date Of Event은 필수 입력란입니다.");
			String date=date_of_event_Y.getText()+"."+date_of_event_M.getText()+"."+date_of_event_D.getText();
			booking.date_for_bookingID=date_of_event_M.getText()+date_of_event_D.getText();
			DateFormat df= new SimpleDateFormat("yyyy.MM.dd"); 

			try {
				java.util.Date utilDate=df.parse(date);
				booking.date_of_event = new java.sql.Date(utilDate.getTime());
			} catch(ParseException e) {
				e.printStackTrace();
			}
			
			// 00:00:00 형태로 예약시간 바꾸어 저장
			String time = hour[reservation_hour.getSelectedIndex()] + ":" + minute[reservation_minute.getSelectedIndex()] + ":00";
			booking.time_of_event=Time.valueOf(time);
			
			// 전화번호는 필수 사항 -> 예외처리 필요
			if(customer_phone.getText().isEmpty())
				throw new Exception("Phone Number은 필수입력란입니다");
			booking.phone_number = customer_phone.getText();

			
			//나이 확인
			if(!customer_age.getText().isEmpty() && checkString(customer_age.getText())) {
				age =  Integer.parseInt(customer_age.getText());
				}
			if(!customer_age.getText().isEmpty() && !checkString(customer_age.getText())) 
				throw new Exception("age 란에 숫자형태를 입력하세요.");
						
			//나이랑 성별 보고 카테고리 지정
			int check=0;
			for(int i=10;i<70;i+=10) {
				if (i <= age && age < i+10 && female.isSelected()) {
					booking.customer_category = i+1;
					check=1;
				}
				else if (i <= age && age < i+10 && male.isSelected()) {
					booking.customer_category = i+2;
					check=1;
				}
			}
			if(check==0&&female.isSelected()) booking.customer_category = 71;
			else if(check==0&&male.isSelected()) booking.customer_category = 72;
			
			booking.Create_IDs();
			

		} catch (Exception e) {

			isError = true;
			ErrorMessage = e.getMessage();
		}

	}
	
	/**
	 * 문자열이 숫자로 이루어져 있는지 확인
	 * @param str age textfield란에 쓰인 string 
	 * @return boolean 숫자면 true를 return 아니면 false를 return
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
	 * Booking관련 정보들을 데이터베이스에 저장
	 */
	public void storeInfo() {

		try {
			
			//초기화
			rollback = false;
			pstmt = null;			
			
			//Booked_Customers Table
			pstmt = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Booked_Customers values(?,?,?)");
			pstmt.setInt(1, booking.customer_ID);
			pstmt.setInt(2, booking.customer_category);
			pstmt.setString(3, booking.phone_number);
			pstmt.executeUpdate();			
			
			// Bookings Table
			pstmt = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Bookings values(?,?,?,?,?)");
			pstmt.setInt(1, booking.booking_ID);
			pstmt.setInt(2, booking.restaurant_ID);
			pstmt.setInt(3, booking.customer_ID);
			pstmt.setTime(4, booking.time_of_event);
			pstmt.setDate(5, booking.date_of_event);
			pstmt.executeUpdate();
			DB_Connection.conn.commit();

			pstmt.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DB_Connection.conn.rollback();
				System.out.println("Make Reservation - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * initialize page
	 */
	public void clearPage() {
		
		restaurant_name.setText(null);
		date_of_event_Y.setText(null);
		date_of_event_M.setText(null);
		date_of_event_D.setText(null);
		customer_phone.setText(null);
		customer_age.setText(null);
		reservation_hour.setSelectedIndex(0);
		reservation_minute.setSelectedIndex(0);
		female.setSelected(false);
		male.setSelected(false);
		group.clearSelection();

	}
	
	/**
	 * 이전의 클래스로부터 받아온 레스토랑 이름을 레스토랑 입력란에 세팅
	 * @param name restaurant name
	 */
	public void get_Info(String name) {
		date_of_event_Y.setText(null);
		date_of_event_M.setText(null);
		date_of_event_D.setText(null);
		customer_phone.setText(null);
		customer_age.setText(null);
		reservation_hour.setSelectedIndex(0);
		reservation_minute.setSelectedIndex(0);
		female.setSelected(false);
		male.setSelected(false);
		group.clearSelection();
		restaurant_name.setText(name);
	}
			
}