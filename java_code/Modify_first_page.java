package team12;
/** ����ϴµ� �ʿ��� �͵��� ���Ե� ���̺귯�� ���� */
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
 * @author ���غ�
 *  */
public class Modify_first_page extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	/** �ʿ� ������, ���빰, ���� ���� */
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
	JButton Okay = new JButton("Ȯ��");

	/** DB���� ����*/
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
	/** ��ư�� ������ ���ֱ� ���� Insets ���� */
	Insets insets = new Insets(0, 0, 0, 0);

	boolean rollback = false;

	/** Panel ���� */
	Modify_first_page() {
		/** �Ͼ� ��� */
		this.setBackground(Color.white);
		/** ���� ��ġ�� ��ġ */
		this.setLayout(null);

		/** ���� ���빰�� ���� ��ġ �� ũ��, ���� �Ӽ� ���� */
		R_name.setBounds(190, logo_height / 4 * 3 - 15, 250, 20);
		R_name.setToolTipText("������� �̸� �Է¶��Դϴ�.");
		R_type.setBounds(190, logo_height / 4 * 3 + 25, 250, 20);
		R_type.setToolTipText("(ex : �ѽ�, �Ͻ�, �ƽþ�����, ...)");
		R_info.setBounds(190, logo_height / 4 * 3 + 65, 250, 20);
		R_info.setToolTipText("���� �Ұ� �Է¶��Դϴ�.");
		R_phone.setBounds(190, logo_height / 4 * 3 + 185, 250, 20);
		R_phone.setToolTipText("��ȭ��ȣ �Է¶��Դϴ�. ( ' - ' ����)");
		R_address.setBounds(190, logo_height / 4 * 3 + 225, 250, 20);
		R_address.setToolTipText("�ּ� �Է¶��Դϴ�.");
		R_website.setBounds(190, logo_height / 4 * 3 + 265, 250, 20);
		R_website.setToolTipText("������Ʈ �ּ� �Է¶��Դϴ�.");
		Open_hour.setBounds(190, logo_height / 4 * 3 + 105, 40, 20);
		Open_hour.setBackground(Color.white);
		Open_hour.setToolTipText("���� �ð� �Է¶��Դϴ�.");
		Open_minute.setBounds(250, logo_height / 4 * 3 + 105, 40, 20);
		Open_minute.setBackground(Color.white);
		Open_minute.setToolTipText("���� �ð� �Է¶��Դϴ�.");
		Close_hour.setBounds(190, logo_height / 4 * 3 + 145, 40, 20);
		Close_hour.setBackground(Color.white);
		Close_hour.setToolTipText("Ŭ���� �ð� �Է¶��Դϴ�.");
		Close_minute.setBounds(250, logo_height / 4 * 3 + 145, 40, 20);
		Close_minute.setBackground(Color.white);
		Close_minute.setToolTipText("Ŭ���� �ð� �Է¶��Դϴ�.");
		/** ���� ��ġ �� ũ�� ���� */
		Mon.setBounds(185, logo_height / 4 * 3 + 305, 20, 15);
		Tue.setBounds(255, logo_height / 4 * 3 + 305, 20, 15);
		Wed.setBounds(325, logo_height / 4 * 3 + 305, 20, 15);
		Thu.setBounds(395, logo_height / 4 * 3 + 305, 20, 15);
		Fri.setBounds(185, logo_height / 4 * 3 + 330, 20, 15);
		Sat.setBounds(255, logo_height / 4 * 3 + 330, 20, 15);
		Sun.setBounds(325, logo_height / 4 * 3 + 330, 20, 15);
		/** ��� �� �Ͼ����� ���� */
		Mon.setBackground(Color.white);
		Tue.setBackground(Color.white);
		Wed.setBackground(Color.white);
		Thu.setBackground(Color.white);
		Fri.setBackground(Color.white);
		Sat.setBackground(Color.white);
		Sun.setBackground(Color.white);
		/** TpplTipText���� */
		Mon.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Tue.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Wed.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Thu.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Fri.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Sat.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Sun.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		/** Holiday_Closed ���� ��ư ���� */
		Y.setBounds(185, logo_height / 4 * 3 + 363, 20, 15);
		Y.setBackground(Color.white);
		Y.setToolTipText("������ ���� ���� ���ö��Դϴ�.");
		N.setBounds(255, logo_height / 4 * 3 + 363, 20, 15);
		N.setBackground(Color.white);
		N.setToolTipText("������ ���� ���� ���ö��Դϴ�.");
		/** Y, N �� �ϳ��� ���� ���� */
		group.add(Y);
		group.add(N);
		/** â �̵� ��ư ���� */
		menu.setBounds(370, 530, 40, 30);
		/** ��ư�� ���� ���ֱ� */
		menu.setMargin(insets);
		menu.setBackground(Color.WHITE);
		Okay.setBounds(430, 530, 40, 30);
		/** ��ư�� ���� ���ֱ� */
		Okay.setMargin(insets);
		Okay.setBackground(Color.WHITE);
		/** Ȩ ��ư ���� */
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. Ȩ ��������.");
		/** ��ư ��輱 ���ֱ� */
		HOME.setBorderPainted(false);

		/** ���빰�� ȭ�鿡 ��� */
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
		/** ��ư�� �׼� �ֱ� */
		menu.addActionListener(this);
		HOME.addActionListener(this);
		Okay.addActionListener(this);

	}

	/** Panel�� �׸��� */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		/** ���, �ϴ� �κ� */

		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Modify restaurant information.", logo_width / 2 + 45, logo_height / 4 - 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 15));
		g.drawString("������� ������ �����ϼ���.", logo_width / 5 * 4 + 25, logo_height / 5 * 2 - 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+�� �ʼ� �Է¶��Դϴ�.)", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

		/** �ߴ� �κ� */
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

		/** �� ���� �κ� */
		g.setColor(new Color(243, 128, 137));
		g.drawString("+", 40, logo_height / 4 * 3);
		g.drawString("+", 40, logo_height / 4 * 3 + 120);
		g.drawString("+", 40, logo_height / 4 * 3 + 160);
		g.drawString("+", 40, logo_height / 4 * 3 + 375);
		g.drawString("+", 40, logo_height / 4 * 3 + 240);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.drawString(" +", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

	}

	// ��ư ���� ���� �Լ�
	public void actionPerformed(ActionEvent e) {
		/** HOME ��ư�� Ŭ���� �� ù ȭ������ ���ư� */
		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		/** DB�� �ִ� ������ �޾ƿ� �� ������ ������ �Ŀ� menu��ư�� Ŭ���� �� �Էµ� ������ DB�� ������Ʈ �� */ 
		if (e.getSource() == menu) {
			/** DBCOURSE_RESTAURANTS���̺�� DBCOURSE_HOLIDAY���̺��� �ش��ϴ� ���� �޾ƿ���*/
			getUpdateInfo();
			getUpdateHoliday();
			/**������ ���� ��� �����޼��� ��� */
			if (rollback == true) {
				JOptionPane.showConfirmDialog(null, "�ٽ� �õ��� �ֽʽÿ�");
			}/** ������ ���� �ʴ� ��� ������ ������ DB�� ������Ʈ �� �� ���� �޴� ������������ �Ѿ*/ 
			else {
				UpdateRestaurant(res.R_ID);
				UpdateAccess(res.R_ID);
				UpdateHoliday(res.R_ID);
				Main_Frame.R_card.next(Main_Frame.R_frame.getContentPane());
			}/** ���� �������� �ش��ϴ� ������ �̸� �޾ƿ�*/
			Main_Frame.modify_second_page.getInfo(res.R_ID);
			Main_Frame.modify_second_page.setMenu();
		}
		/** ������� ������ �����ϰ� ���� ��� �ٷ� okay��ư ������ menu��ư ������ ���� �ٸ���
		 * �� ������������ �ʿ��� �͵��� ���� ���� �׸��� ������� ����Ʈ �������� ���ư� */
		if (e.getSource() == Okay) {

			getUpdateInfo();
			getUpdateHoliday();

			if (rollback == true) {
				JOptionPane.showConfirmDialog(null, "�ٽ� �õ��� �ֽʽÿ�");
			} else {
				UpdateRestaurant(res.R_ID);
				UpdateAccess(res.R_ID);
				UpdateHoliday(res.R_ID);
			}
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");
		}
	}
	/** ������� ����Ʈ ���������� ������� ���̵� �޾ƿ��� �Լ� */
	public void create_R_ID() {
		select_R_ID = Main_Frame.list_page.select_R_ID;
		res.R_ID = select_R_ID;
	}
	/** DB�� �ִ� ������� ��������  ���̵� �˻��ؼ� �޾ƿ��� �Լ�. �ѹ��� ���� ���� ���ο� view�� ���� ���*/
	public void getInfo(int id) {
		try {
			/** �ʱ�ȭ */
			restaurant_info.clear();

			/** ���� ����� �޾ƿ� */
			stmt = DB_Connection.stmt;
			String sqlstr = "select * from DBCOURSE_RESTAURANT_INFO_VIEW where Restaurant_ID=" + id;
			rs = stmt.executeQuery(sqlstr);

			/** �˻� ����� ArrayList�� restaurant_info ������ ���� */
			while (rs.next()) {
				/** R_ID(1), R_Name(2), R_Type(3), R_Info(4), R_Open(5), R_Close(6), R_Phone(7),
				* R_Address(8), R_Website(9) ������� �������� view���� �޾ƿ��� ���� */
				restaurant_info.add(rs.getString(1));
				restaurant_info.add(rs.getString(2));
				restaurant_info.add(rs.getString(3));
				restaurant_info.add(rs.getString(4));
				/** open�ð��� close�ð��� ���°� HH:MM:SS�̱� ������ �տ� HH�� �޾ƿ��� ���� �޾ƿ� String�� �ɰ�*/
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
	/** DBCOURSE_HOLIDAY�� �ִ� ������� ���Ͽ� ���� ������ ����������̵�� �˻��ؼ� �޾ƿ��� �Լ� */
	public void getHoliday(int id) {
		try {
			/** �ʱ�ȭ */
			holiday.clear();
			ArrayList holi = new ArrayList<>();
			/** ���� ����� �޾ƿ� */
			stmt = DB_Connection.stmt;

			String sqlstr = "select * from  DBCOURSE_Holiday where Restaurant_ID=" + id;
			rs = stmt.executeQuery(sqlstr);

			/** �˻� ����� ArrayList ������ ���� */
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
	/** �޾ƿ� �����带 ��ġ�� �°� �ؽ�Ʈ�ʵ忡 �Է��ϴ� �޼ҵ�*/
	public void SetText(ArrayList<String> info) {

		R_name.setText(info.get(1));
		R_type.setText(info.get(2));
		R_info.setText(info.get(3));
		R_phone.setText(info.get(6));
		R_address.setText(info.get(7));
		R_website.setText(info.get(8));
		/** open�ð��� close�ð��� ���°� HH:MM:SS�̱� ������ �ɰ��� ���*/
		Open_hour.setSelectedItem(info.get(4).substring(0, 2));
		Open_minute.setSelectedItem(info.get(4).substring(3, 5));
		Close_hour.setSelectedItem(info.get(5).substring(0, 2));
		Close_minute.setSelectedItem(info.get(5).substring(3, 5));
	}
	/** DBCOURSE_HOLIDAY���� �޾ƿ� ������ �����ִ� ���� �����ϴ� �Լ�*/
	public void SetCheckBox(ArrayList holiday) {

		/** ArrayList holiday�� ������ �Լ��� �� �ش��ϴ� ������ �����ϸ� ��ư�� ���õ� ���·� ��ȯ*/
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

	/** ������� ������ �������� ��� ������ ������ ArrayList�� �޾ƿ��� �Լ�*/
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
	
	/** ���� ������ �������� ��� ������ ������ ArrayList�� �޾ƿ��� �Լ�*/
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
	/**������ ������ DB�� �ִ� DBCOURSE_RESTAURANTS���̺�
	 *  ������� ���̵� �˻��ؼ� �ش��ϴ� ���̵� ������ ������Ʈ�ϴ� �Լ�*/
	public void UpdateRestaurant(int id) {
		try {
			// �ʱ�ȭ

			conn = DB_Connection.conn;
			// ���� ����� �޾ƿ´�.
			pstmt = null;
			rollback = false;

			String sqlstr = "update DBCOURSE_Restaurants set Restaurant_name=?, Restaurant_Type=?, Restaurant_Info=?, Opening_hours=?, Closing_hours=? where Restaurant_ID="
					+ id;

			pstmt = conn.prepareStatement(sqlstr);

			/** DBCOURSE_RESTAURANTS���� �ش��ϴ� ���̵� ���� tuple�� ������Ʈ */
			pstmt.setString(1, restaurant_info.get(0));
			pstmt.setString(2, restaurant_info.get(1));
			pstmt.setString(3, restaurant_info.get(2));
			pstmt.setString(4, restaurant_info.get(6));
			pstmt.setString(5, restaurant_info.get(7));

			int result = pstmt.executeUpdate();

			if (pstmt != null)
				pstmt.close();

			if (result > 0) {
				System.out.println("����");
				DB_Connection.conn.commit();
			} else {
				DB_Connection.conn.rollback();
				System.out.println("����");
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
	/**������ ������ DB�� �ִ� DBCOURSE_ACCESS_ROUTE ���̺�
	 *  ������� ���̵� �˻��ؼ� �ش��ϴ� ���̵� ������ ������Ʈ�ϴ� �Լ�*/
	public void UpdateAccess(int id) {
		try {
			// �ʱ�ȭ

			conn = DB_Connection.conn;
			// ���� ����� �޾ƿ´�.

			pstmt = null;
			rollback = false;

			String sqlstr = "update DBCOURSE_Access_Route set Phone_Number=?, Address=?, Website_Address=? where Restaurant_ID="
					+ id;

			pstmt = conn.prepareStatement(sqlstr);
			
			/** ������� ���̵� �ش��ϴ� DBCOURSE_ACCESS_ROUTE tuple ������Ʈ*/
			pstmt.setString(1, restaurant_info.get(3));
			pstmt.setString(2, restaurant_info.get(4));
			pstmt.setString(3, restaurant_info.get(5));

			int result = pstmt.executeUpdate();

			if (pstmt != null)
				pstmt.close();

			if (result > 0) {
				System.out.println("����");
				DB_Connection.conn.commit();
			} else {
				DB_Connection.conn.rollback();
				System.out.println("����");
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
	/**������ ������ DB�� �ִ� DBCOURSE_HOLIDAY���̺�
	 *  ������� ���̵� �˻��ؼ� �ش��ϴ� ���̵� ������ ������Ʈ�ϴ� �Լ�*/
	public void UpdateHoliday(int id) {
		try {
			/** �ʱ�ȭ */

			conn = DB_Connection.conn;
			
			/** ���� ����� �޾ƿ� */
			Statement stmt02 = conn.createStatement();
			Statement stmt03 = conn.createStatement();
			pstmt = null;
			rollback = false;

			/** R_ID�� ���̺� �� ������� �ִ��� Ȯ��*/
			System.out.println(checkRID());
			/** mysql �󿡼� �ش��ϴ� �ܷ�Ű�� ����� ��� ���̺��� ������ �����Ǳ� ������ �װ� �����ϱ�
			 * ���� ������ �ַ�Ű R_ID�� �������� �� drop�ؼ� �ַ�Ű�� ����*/
			stmt02.executeUpdate("ALTER TABLE DBCOURSE_holiday drop foreign key R_ID");
			/**holiday�� ������Ʈ �ϱ� ���ؼ� ���� ����������̵� �ش��ϴ� holiday�� ���� ����*/
			String sqlstr = "delete from DBCOURSE_Holiday where Restaurant_ID = ?";
			pstmt = conn.prepareStatement(sqlstr);
			pstmt.setString(1, String.valueOf(id));
			pstmt.executeUpdate();

			/**���� ���� �������� �𸣱� ������ ���³��� �޾ƿ� ArrayList�� size�� ���缭 ������� DBCOURSE_HOLIDAY�� �ش��ϴ� ����������̵��
			 * ������ ���� ������ �Բ� ������Ʈ*/
			if (cHoliday.size() != 0) {
				for (int i = 0; i < cHoliday.size(); i++) {
					pstmt1 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Holiday values(?,?,?)");
					pstmt1.setInt(1, id);
					pstmt1.setString(2, cHoliday.get(i));
					/**�����Ͽ� ���� ���� �Է�*/
					pstmt1.setString(3, holi_close);
					pstmt1.executeUpdate();
				}
			}/**���࿡ ���� ���� ���� ��� �˻����ǿ� �ִ� ����������̵� �ش��ϴ� holiday�� None�� �־ ������Ʈ*/
			else {
				pstmt1 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Holiday values(?,?,?)");
				pstmt1.setInt(1, id);
				pstmt1.setString(2, "None");
				pstmt1.setString(3, holi_close);
				pstmt1.executeUpdate();
			}
			/** holiday�� �����ߴ� �ַ�Ű�� �ٽ� ����־���*/
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
	/** DBCOURSE_HOLIDAY���̺� R_ID�� �ִ��� üũ�ϱ� ���� �Լ�*/
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