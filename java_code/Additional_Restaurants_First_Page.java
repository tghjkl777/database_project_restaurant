package team12;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

/**
 * @author ���ѳ�.
 * Additional_Restaurants_First_Page�� �����ϴ� JPanel.
 * ������� ���ݿ� ���� ������ �޾� database�� insert�Ѵ�.
 */
public class Additional_Restaurants_First_Page extends JPanel implements ActionListener {

	// �ʿ� ������, ���빰, ���� ����
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
	String[] hour = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
			"18", "19", "20", "21", "22", "23" };
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
	// ������� ���� ���� ���� ��ü ����
	static Restaurant_Info restaurant = new Restaurant_Info();
	// For Connection.
	PreparedStatement pstmt1;
	PreparedStatement pstmt2;
	PreparedStatement pstmt3;
	PreparedStatement pstmt4;
	// ���� ���� ����
	String ErrorMessage;
	boolean isError = false;
	boolean rollback = false;

	/** 
	 * HOME_Page JPanel ����.
	 * �Ͼ� ��濡 ���� ��ġ�� ���� ��ư�� �÷ȴ�.
	 * Ȩ ��ư, ���� �������� ���� ��ư, ������� ������ �Է��ϴ� �Է¶����� �ִ�.
	 * �ʼ� �Է¶��� �ݵ�� �Է��ؾ� �Ѵ�.
	 * */
	Additional_Restaurants_First_Page() {

		this.setBackground(Color.white); // �Ͼ� ���
		this.setLayout(null); // ���� ��ġ�� ��ġ

		// ���� ���빰�� ���� ��ġ �� ũ��, ���� �Ӽ� ����
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
		// ���� ��ġ �� ũ�� ����
		Mon.setBounds(185, logo_height / 4 * 3 + 305, 20, 15);
		Tue.setBounds(255, logo_height / 4 * 3 + 305, 20, 15);
		Wed.setBounds(325, logo_height / 4 * 3 + 305, 20, 15);
		Thu.setBounds(395, logo_height / 4 * 3 + 305, 20, 15);
		Fri.setBounds(185, logo_height / 4 * 3 + 330, 20, 15);
		Sat.setBounds(255, logo_height / 4 * 3 + 330, 20, 15);
		Sun.setBounds(325, logo_height / 4 * 3 + 330, 20, 15);
		// ��� �� �Ͼ����� ����
		Mon.setBackground(Color.white);
		Tue.setBackground(Color.white);
		Wed.setBackground(Color.white);
		Thu.setBackground(Color.white);
		Fri.setBackground(Color.white);
		Sat.setBackground(Color.white);
		Sun.setBackground(Color.white);
		// ToolTipText ����
		Mon.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Tue.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Wed.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Thu.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Fri.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Sat.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		Sun.setToolTipText("���� ���� ���� ��� ����νʽÿ�.");
		// Holiday_Closed ���� ��ư ����
		Y.setBounds(185, logo_height / 4 * 3 + 363, 20, 15);
		Y.setBackground(Color.white);
		Y.setToolTipText("������ ���� ���� ���ö��Դϴ�.");
		N.setBounds(255, logo_height / 4 * 3 + 363, 20, 15);
		N.setBackground(Color.white);
		N.setToolTipText("������ ���� ���� ���ö��Դϴ�.");
		// Y, N �� �ϳ��� ���ð���
		group.add(Y);
		group.add(N);
		// Ȩ ��ư ����
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. Ȩ ��������.");
		HOME.setBorderPainted(false); // ��ư ��輱 ���ֱ�
		HOME.addActionListener(this);
		// ���� �������� ���� ��ư ����
		NEXT.setBounds(436, logo_height * 3 - 60, next_width / 8, next_height / 8);
		NEXT.setBackground(Color.WHITE);
		NEXT.setToolTipText("Next. ���� ��������.");
		NEXT.setBorderPainted(false); // ��ư ��輱 ���ֱ�
		NEXT.addActionListener(this);

		// ���빰�� ȭ�鿡 ���
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
		this.add(NEXT);

	}

	/**
	 * Panel�� �׸���.
	 * �۾��� �׸����� �׸���.
	 * �ʼ� �Է¶��� �� ������ ���ִ�.
	 * */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// ��, �Ʒ� �κ�
		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);
		g.drawImage(next_img, getWidth() - next_width / 2, getHeight() - logo_height / 2, next_width / 2,
				next_height / 2, this);
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Enter additional restaurant information.", logo_width / 2 + 15, logo_height / 4 - 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 15));
		g.drawString("������� ������ �Է��ϼ���.", logo_width / 5 * 4 + 25, logo_height / 5 * 2 - 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+�� �ʼ� �Է¶��Դϴ�.)", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

		// �߰� �κ�
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

		// �� ���� �κ�
		g.setColor(new Color(243, 128, 137));
		g.drawString("+", 40, logo_height / 4 * 3);
		g.drawString("+", 40, logo_height / 4 * 3 + 120);
		g.drawString("+", 40, logo_height / 4 * 3 + 160);
		g.drawString("+", 40, logo_height / 4 * 3 + 375);
		g.drawString("+", 40, logo_height / 4 * 3 + 240);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.drawString(" +", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

	}

	/**
	 * ��ư ���� ���� �Լ�.
	 * + NEXT��ư�� ������ ������� ������ database�� insert�� ��, 
	 * ���� �������� �̵��� ��ǥ �޴� �Է� ���� ���ο� ���� �������� �̵��Ѵ�.
	 * �޴� �Է� �� Additional_Restaurants_Second_Page�� �̵��Ѵ�.
	 * �޴� �Է� ���� �� Restaurant_List_page�� �̵��Ѵ�.
	 * �ʼ� �Է�â�� ������� �� �� ���� �߻� �� �˾�â���� ��� ������ ����.
	 * â �̵� �Ŀ��� clearPage() �Լ��� ���� â�� �ʱ�ȭ��Ų��. 
	 * + HOME��ư�� ������ HOME_Page�� �̵��Ѵ�.
	 * */
	public void actionPerformed(ActionEvent e) {

		// next��ư ���
		if (e.getSource() == NEXT) {

			// ����ڰ� �Է��� ������ Restaurant_Info��ü�� �ϴ� �����Ѵ�
			getInfo();

			// ���� �Է¿� ������ ���ٸ� �����ͺ��̽��� �� ������ �����ϰ�
			// ��ǥ �޴� �Է� ���� ���ο� ���� �������� �̵���Ų��
			if (!isError) {

				// �����ͺ��̽��� ���� ����
				storeInfo();

				if (rollback == true) {
					clearPage();
					JOptionPane.showMessageDialog(null, "�ٽ� �õ��� �ֽʽÿ�.");
				} else {
					// ��ǥ �޴� �Է� ���� ���� ����
					int confirm = JOptionPane.showConfirmDialog(null,
							"                  ��ǥ �޴��� �Է��Ͻðڽ��ϱ�?\n  (��ǥ�޴��� �ִ� 2������ �Է��Ͻ� �� �ֽ��ϴ�.)",
							" Enter the menu info?", JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {

						Main_Frame.R_card.next(Main_Frame.R_frame.getContentPane());

					} else if (confirm == JOptionPane.NO_OPTION) {

						Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");

					}

					// â �ʱ�ȭ
					clearPage();

					// listâ ���ΰ�ħ
					Main_Frame.list_page.result = Main_Frame.list_page.List_Search();
					Main_Frame.list_page.model.setDataVector(Main_Frame.list_page.result, Main_Frame.list_page.title);

					Main_Frame.list_page.Center();
				}
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
	 * �Է¹��� ������� ������ Restaurant_Info ��ü�� �����ϴ� �Լ�
	 * ����ó�� �ʿ� (�ʼ� ������ ����ִ����� ����).
	 * ����ó���� throw new�� �̿��� errorMessage�� �ѱ�� ������� �����Ѵ�.
	 * ���⼭�� errorMessage�� ��ư��ɿ��� �˾�â�� ���� ��� ������ ���� �� ���ȴ�.
	 */
	public void getInfo() {

		try {

			// �ʱ�ȭ
			isError = false;
			ErrorMessage = null;
			restaurant.Clear_R();
			// ������� ���̵� ����
			restaurant.Create_R();

			// �̸��� �ʼ� ���� -> ����ó�� �ʿ�
			if (R_name.getText().isEmpty())
				throw new Exception("Restaurant Name�� �ʼ� �Է¶��Դϴ�.");
			restaurant.R_Name = R_name.getText();
			restaurant.R_Type = R_type.getText();
			restaurant.R_Info = R_info.getText();
			// 00:00:00 ���·� �ٲٱ�
			String R_open = hour[Open_hour.getSelectedIndex()] + ":" + minute[Open_minute.getSelectedIndex()] + ":00";
			String R_close = hour[Close_hour.getSelectedIndex()] + ":" + minute[Close_minute.getSelectedIndex()]
					+ ":00";
			restaurant.R_Open = R_open;
			restaurant.R_Close = R_close;
			restaurant.R_Phone = R_phone.getText();
			// �ּҴ� �ʼ� ���� -> ����ó�� �ʿ�
			if (R_address.getText().isEmpty())
				throw new Exception("Address�� �ʼ� �Է¶��Դϴ�.");
			restaurant.R_Address = R_address.getText();
			restaurant.R_Website = R_website.getText();
			// �ƹ��͵� ���� �� �� �ÿ��� "None"
			if (Mon.isSelected())
				restaurant.R_Closed_Day.add("Mon");
			if (Tue.isSelected())
				restaurant.R_Closed_Day.add("Tue");
			if (Wed.isSelected())
				restaurant.R_Closed_Day.add("Wed");
			if (Thu.isSelected())
				restaurant.R_Closed_Day.add("Thu");
			if (Fri.isSelected())
				restaurant.R_Closed_Day.add("Fri");
			if (Sat.isSelected())
				restaurant.R_Closed_Day.add("Sat");
			if (Sun.isSelected())
				restaurant.R_Closed_Day.add("Sun");
			if (restaurant.R_Closed_Day.isEmpty())
				restaurant.R_Closed_Day.add("None");
			// ������ ���� ���δ� �ʼ� ���ö� -> ����ó�� �ʿ�
			if (Y.isSelected())
				restaurant.R_Holiday_Closed = "Y";
			else if (N.isSelected())
				restaurant.R_Holiday_Closed = "N";
			else
				throw new Exception("Holiday Closed�� �ʼ� ���ö��Դϴ�.");

		} catch (Exception e) {

			isError = true;
			ErrorMessage = e.getMessage();

		}

	}

	/**
	 *  getInfo�� �������� �����ͺ��̽��� �����ϴ� �Լ�.
	 *  prepareStatement�� Ȱ���� ������ insert�Ѵ�.
	 *  ���ܹ߻� �Ǵ� ���� ���� �� rollback()�Ѵ�.
	 */
	public void storeInfo() {

		try {
			// �ʱ�ȭ
			pstmt1 = null;
			pstmt2 = null;
			pstmt3 = null;
			rollback = false;

			int result1, result2, result3 = 1, result4, temp;
			result1 = result2 = result4 = temp = 0;

			// Restaurants Table
			pstmt1 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Restaurants values(?,?,?,?,?,?)");
			pstmt1.setInt(1, restaurant.R_ID);
			pstmt1.setString(2, restaurant.R_Name);
			pstmt1.setString(3, restaurant.R_Type);
			pstmt1.setString(4, restaurant.R_Info);
			pstmt1.setString(5, restaurant.R_Open);
			pstmt1.setString(6, restaurant.R_Close);
			result1 = pstmt1.executeUpdate();

			// Access_Route Table
			pstmt2 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Access_Route values(?,?,?,?)");
			pstmt2.setInt(1, restaurant.R_ID);
			pstmt2.setString(2, restaurant.R_Phone);
			pstmt2.setString(3, restaurant.R_Address);
			pstmt2.setString(4, restaurant.R_Website);
			result2 = pstmt2.executeUpdate();

			// Holiday Table
			for (int i = 0; i < restaurant.R_Closed_Day.size(); i++) {
				pstmt3 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Holiday values(?,?,?)");
				pstmt3.setInt(1, restaurant.R_ID);
				pstmt3.setString(2, restaurant.R_Closed_Day.get(i));
				pstmt3.setString(3, restaurant.R_Holiday_Closed);
				temp = pstmt3.executeUpdate();
				result3 = result3 * temp;
			}

			// Evaluation Table
			pstmt4 = DB_Connection.conn.prepareStatement("insert into DBCOURSE_Evaluation values(?,0,0)");
			pstmt4.setInt(1, restaurant.R_ID);
			result4 = pstmt4.executeUpdate();

			// �ڿ��ݳ�
			if (pstmt1 != null)
				pstmt1.close();
			if (pstmt2 != null)
				pstmt2.close();
			if (pstmt3 != null)
				pstmt3.close();
			if (pstmt4 != null)
				pstmt4.close();

			// �� ���� �������� �ÿ��� commit
			if (result1 * result2 * result3 * result4 > 0)
				DB_Connection.conn.commit();
			else {
				DB_Connection.conn.rollback();
				System.out.println("Additional_Restaurants_Frist_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			// ���� �߻��� �ѹ�
			try {
				DB_Connection.conn.rollback();
				System.out.println("Additional_Restaurants_Frist_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 *  â �ʱ�ȭ �Լ�
	 */
	public void clearPage() {

		R_name.setText(null);
		R_type.setText(null);
		R_info.setText(null);
		R_phone.setText(null);
		R_address.setText(null);
		R_website.setText(null);
		Open_hour.setSelectedIndex(0);
		Open_minute.setSelectedIndex(0);
		Close_hour.setSelectedIndex(0);
		Close_minute.setSelectedIndex(0);
		Mon.setSelected(false);
		Tue.setSelected(false);
		Wed.setSelected(false);
		Thu.setSelected(false);
		Fri.setSelected(false);
		Sat.setSelected(false);
		Sun.setSelected(false);
		group.clearSelection();

	}

}