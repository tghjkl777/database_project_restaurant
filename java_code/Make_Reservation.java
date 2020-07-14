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
 * ������� ������ ���� ������
 * @author �ڼ���
 *
 */
public class Make_Reservation extends JPanel implements ActionListener{
	
	//�ʿ� ������, ���빰, ���� ����
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
	JButton Okay=new JButton("Ȯ��");
	static Booking_Info booking = new Booking_Info();
	// For Connection.
	PreparedStatement pstmt;
	// ���� ���� ����
	String ErrorMessage;
	boolean isError = false;
	boolean rollback =false;
	
	Insets insets = new Insets(0,0,0,0);
	
	//Panel ����
	Make_Reservation(){
		
		this.setBackground(Color.white);	//�Ͼ� ���
		this.setLayout(null);	//���� ��ġ�� ��ġ
		
		//���� ���빰�� ���� ��ġ �� ũ��, ���� �Ӽ� ����
		//������� �̸� �Է�
		restaurant_name.setBounds(190, logo_height/4*3-15, 250, 20);
		restaurant_name.setToolTipText("������� �̸� �Է¶��Դϴ�.");
		//���� ��¥ �Է�
		date_of_event_Y.setBounds(190, logo_height/4*3+55, 50, 20);
		date_of_event_Y.setToolTipText("���� �⵵ �Է¶��Դϴ�");
		date_of_event_M.setBounds(275, logo_height/4*3+55, 50, 20);
		date_of_event_M.setToolTipText("���� �� �Է¶��Դϴ�");
		date_of_event_D.setBounds(360, logo_height/4*3+55, 50, 20);
		date_of_event_D.setToolTipText("���� �� �Է¶��Դϴ�");
		//���� �ð� ���� 
		reservation_hour.setBounds(190, logo_height/4*3+125, 40, 20);
		reservation_hour.setBackground(Color.white);
		reservation_hour.setToolTipText("���� �ð� �Է¶��Դϴ�.");
		reservation_minute.setBounds(250, logo_height/4*3+125, 40, 20);
		reservation_minute.setBackground(Color.white);
		reservation_minute.setToolTipText("���� �ð� �Է¶��Դϴ�.");
		//��ȭ��ȣ ����
		customer_phone.setBounds(190, logo_height/4*3+195, 250, 20);
		customer_phone.setToolTipText("��ȭ��ȣ �Է¶��Դϴ�.");
		//���� �Է�
		customer_age.setBounds(190, logo_height/4*3+265, 250, 20);
		customer_age.setToolTipText("���� �̸� �Է¶��Դϴ�.");
		//���� ����
		female.setBounds(185, logo_height/4*3+335, 20, 15);
		female.setBackground(Color.white);
		female.setToolTipText("���� ���� ���ö��Դϴ�.");
		male.setBounds(300, logo_height/4*3+335, 20, 15);
		male.setBackground(Color.white);
		male.setToolTipText("���� ���� ���ö��Դϴ�.");
		group.add(female);
		group.add(male);
		
		//Ȩ ��ư ����
		HOME.setBounds(5, 5, logo_width/2, logo_height/2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. Ȩ ��������.");
		HOME.setBorderPainted(false);	//��ư ��輱 ���ֱ�
		HOME.addActionListener(this);
		//���� Ȯ�� ����
		Okay.setBounds(430, 530, 40, 30);
		Okay.setMargin(insets);
		Okay.setBackground(Color.WHITE);
		Okay.addActionListener(this);
		
		//���빰�� ȭ�鿡 ���
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
			
	//Panel�� �׸���
	public void paintComponent(Graphics g) {
				
		super.paintComponent(g);
				
		//��, �Ʒ� �κ�
		g.setColor(new Color(0,70,42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Make an Reservation.", logo_width/2+15, logo_height/4-10);
		g.setFont(new Font("���� ���", Font.PLAIN, 15));
		g.drawString("������� ������ �����ϼ���.", logo_width/5*4+25, logo_height/5*2-10);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+�� �ʼ� �Է¶��Դϴ�.)", logo_width/5*4+53, logo_height/5*2+15);
		
		//�߰� �κ�
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
	
		
		//�� ���� �κ�
		g.setColor(new Color(243,128,137));
		g.drawString("+", 40, logo_height/4*3);
		g.drawString("+", 40, logo_height/4*3+70);
		g.drawString("+", 40, logo_height/4*3+140);
		g.drawString("+", 40, logo_height/4*3+210);
		
	}
	

	//��ư ���� ���� �Լ�
	public void actionPerformed(ActionEvent e) {

		// okay��ư ���
		if (e.getSource() == Okay) {
			
			// ����ڰ� �Է��� ������ Booking_Info��ü�� �ϴ� �����Ѵ�
			getInfo();

			// ���� �Է¿� ������ ���ٸ� �����ͺ��̽��� �� ������ ����
			if (!isError) {
				
				// �����ͺ��̽��� ���� ����
				storeInfo();
				
				// â �ʱ�ȭ
				clearPage();
				
				if(rollback ==false) JOptionPane.showMessageDialog(null, "����Ϸ�");
				
				Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
				
				
			}
			// ���� �Է¿� ������ �ִٸ� error�߻� ������ �˷��ش�.
			else
				JOptionPane.showMessageDialog(null, ErrorMessage);

		}

		// home��ư ���
		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");

	}
	
	/**
	 * �Է¹��� ���� ������ Booking_Info ��ü�� �����ϴ� �Լ�
	 */
	public void getInfo() {

		try {

			// �ʱ�ȭ
			isError = false;
			ErrorMessage = null;
			booking.Clear_B();

			// �̸��� �ʼ� ���� -> ����ó�� �ʿ�
			if (restaurant_name.getText().isEmpty())
				throw new Exception("Restaurant Name�� �ʼ� �Է¶��Դϴ�.");
			booking.restaurant_name = restaurant_name.getText();
			
			// ���೯¥�� �ʼ� ���� -> ����ó�� �ʿ�
			if (date_of_event_Y.getText().isEmpty()||date_of_event_M.getText().isEmpty()||date_of_event_D.getText().isEmpty())
				throw new Exception("Date Of Event�� �ʼ� �Է¶��Դϴ�.");
			String date=date_of_event_Y.getText()+"."+date_of_event_M.getText()+"."+date_of_event_D.getText();
			booking.date_for_bookingID=date_of_event_M.getText()+date_of_event_D.getText();
			DateFormat df= new SimpleDateFormat("yyyy.MM.dd"); 

			try {
				java.util.Date utilDate=df.parse(date);
				booking.date_of_event = new java.sql.Date(utilDate.getTime());
			} catch(ParseException e) {
				e.printStackTrace();
			}
			
			// 00:00:00 ���·� ����ð� �ٲپ� ����
			String time = hour[reservation_hour.getSelectedIndex()] + ":" + minute[reservation_minute.getSelectedIndex()] + ":00";
			booking.time_of_event=Time.valueOf(time);
			
			// ��ȭ��ȣ�� �ʼ� ���� -> ����ó�� �ʿ�
			if(customer_phone.getText().isEmpty())
				throw new Exception("Phone Number�� �ʼ��Է¶��Դϴ�");
			booking.phone_number = customer_phone.getText();

			
			//���� Ȯ��
			if(!customer_age.getText().isEmpty() && checkString(customer_age.getText())) {
				age =  Integer.parseInt(customer_age.getText());
				}
			if(!customer_age.getText().isEmpty() && !checkString(customer_age.getText())) 
				throw new Exception("age ���� �������¸� �Է��ϼ���.");
						
			//���̶� ���� ���� ī�װ� ����
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
	 * ���ڿ��� ���ڷ� �̷���� �ִ��� Ȯ��
	 * @param str age textfield���� ���� string 
	 * @return boolean ���ڸ� true�� return �ƴϸ� false�� return
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
	 * Booking���� �������� �����ͺ��̽��� ����
	 */
	public void storeInfo() {

		try {
			
			//�ʱ�ȭ
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
	 * ������ Ŭ�����κ��� �޾ƿ� ������� �̸��� ������� �Է¶��� ����
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