package team12;
/** ����ϴµ� �ʿ��� �͵��� ���Ե� ���̺귯�� ���� */
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

/** ���غ� */
public class Modify_second_page extends JPanel implements ActionListener {

	/** �ʿ� ������, ���빰, ���� ���� */
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

	JButton Okay = new JButton("Ȯ��");
	/** ��ư�� ������ ���ֱ� ���� Insets ���� */
	Insets insets = new Insets(0, 0, 0, 0);
	
	/** DB���� ���� */
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt1;
	PreparedStatement pstmt2;
	
	/** ������� �������� �޾ƿ��� Ŭ���� Restaurant_Info ���� */
	Restaurant_Info res;

	ArrayList<String> Menu_info1 = new ArrayList<String>();
	ArrayList<String> Menu_info2 = new ArrayList<String>();

	int select_R_ID;

	String ErrorMessage;
	boolean isError = false;
	boolean rollback = false;

	/** Panel ���� */

	Modify_second_page() {
		/** �Ͼ� ��� */
		this.setBackground(Color.white);
		/** ���� ��ġ�� ��ġ */
		this.setLayout(null); 

		/** Ȩ ��ư ���� */
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. Ȩ ��������.");
		/** ��ư ��輱 ���ֱ� */
		HOME.setBorderPainted(false);
		HOME.addActionListener(this);
		/** Ȯ�� ��ư ���� */
		Okay.setBounds(430, 530, 40, 30);
		Okay.setMargin(insets);
		Okay.setBackground(Color.WHITE);
		Okay.addActionListener(this);

		menu1.setBounds(34, logo_height / 4 * 3 - 15, 20, 15);
		menu1.setBackground(Color.white);
		menu1.setToolTipText("�ۼ��� �޴��� �����ϰų� �޴��� ������� �� �Է��ϰ� ���� �����ø� �������ּ���");
		menu1.addActionListener(this);
		menu2.setBounds(34, logo_height / 4 * 3 + 200, 20, 15);
		menu2.setBackground(Color.white);
		menu2.setToolTipText("�ۼ��� �޴��� �����ϰų� �޴��� ������� �� �Է��ϰ� ���� �����ø� �������ּ���");
		menu2.addActionListener(this);

		/** 1���޴� ���� ���빰�� ���� ��ġ �� ũ��, ���� �Ӽ� ���� */
		F_name1.setBounds(225, logo_height / 4 * 3 - 15, 220, 20);
		F_name1.setToolTipText("��ǥ �޴� �̸� �Է¶��Դϴ�. (�ظ޴� ��ü ��������)");
		F_price1.setBounds(245, logo_height / 4 * 3 + 25, 200, 20);
		F_price1.setToolTipText("�޴� ���� �Է¶��Դϴ�.");
		F_main1.setBounds(245, logo_height / 4 * 3 + 65, 200, 20);
		F_main1.setToolTipText("�� ��� �Է¶��Դϴ�.");
		F_sub1.setBounds(245, logo_height / 4 * 3 + 105, 200, 20);
		F_sub1.setToolTipText("�� ��� �Է¶��Դϴ�.");
		/** Vegetarian ���� ��ư ���� */
		Y1.setBounds(240, logo_height / 4 * 3 + 148, 20, 15);
		Y1.setBackground(Color.white);
		Y1.setToolTipText("��� ���� ���� ���ö��Դϴ�.");
		N1.setBounds(310, logo_height / 4 * 3 + 148, 20, 15);
		N1.setBackground(Color.white);
		N1.setToolTipText("��� ���� ���� ���ö��Դϴ�.");
		/** Y, N �� �ϳ��� ���� */
		group1.add(Y1);
		group1.add(N1);

		/** 2�� ���� ���빰�� ���� ��ġ �� ũ��, ���� �Ӽ� ���� */
		F_name2.setBounds(225, logo_height / 4 * 3 + 200, 220, 20);
		F_name2.setToolTipText("��ǥ �޴� �̸� �Է¶��Դϴ�. (�ظ޴� ��ü ��������)");
		F_price2.setBounds(245, logo_height / 4 * 3 + 240, 200, 20);
		F_price2.setToolTipText("�޴� ���� �Է¶��Դϴ�.");
		F_main2.setBounds(245, logo_height / 4 * 3 + 280, 200, 20);
		F_main2.setToolTipText("�� ��� �Է¶��Դϴ�.");
		F_sub2.setBounds(245, logo_height / 4 * 3 + 320, 200, 20);
		F_sub2.setToolTipText("�� ��� �Է¶��Դϴ�.");
		/** Vegetarian ���� ��ư ���� */
		Y2.setBounds(240, logo_height / 4 * 3 + 363, 20, 15);
		Y2.setBackground(Color.white);
		Y2.setToolTipText("��� ���� ���� ���ö��Դϴ�.");
		N2.setBounds(310, logo_height / 4 * 3 + 363, 20, 15);
		N2.setBackground(Color.white);
		N2.setToolTipText("��� ���� ���� ���ö��Դϴ�.");
		/** Y, N �� �ϳ��� ���ð��� */
		group2.add(Y2);
		group2.add(N2);

		/** ���빰�� ȭ�鿡 ��� */
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

	/** Panel�� �׸��� */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		/** ���, �ϴ� �κ� */
		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);

		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Modify restaurant Menu.", logo_width / 2 + 75, logo_height / 4 - 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 15));
		g.drawString("�޴��� �����ϼ���.", logo_width / 5 * 4 + 50, logo_height / 5 * 2 - 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+�� �ʼ� �Է¶��Դϴ�.)", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

		/** �ߴ� �κ� */
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

		/** �� ���� �κ� */
		g.setColor(new Color(243, 128, 137));
		g.drawString("+", 40, logo_height / 4 * 3 - 2);
		g.drawString("+", 60, logo_height / 4 * 3 + 78);
		g.drawString("+", 60, logo_height / 4 * 3 + 158);
		g.drawString("+", 40, logo_height / 4 * 3 + 213);
		g.drawString("+", 60, logo_height / 4 * 3 + 293);
		g.drawString("+", 60, logo_height / 4 * 3 + 373);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.drawString(" +", logo_width / 5 * 4 + 53, logo_height / 5 * 2 + 15);

	}

	/** �� �������κ��� ������� ���̵� �޾ƿ� */
	public void create_R_ID() {
		select_R_ID = Main_Frame.list_page.select_R_ID;
		res.R_ID = select_R_ID;
		res.Create_M();
	}

	/** ��ư �׼� ���� �Լ� �������̵� */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Okay) {
			/** DB���� Menu���� ���� �޾ƿ��� */
			getUpdateMenuInfo();
			if (!isError) {
				if (rollback == true) {/** ������ �� ��� �����޼��� ����ϰ� �ٽ� �õ� */
					clearPage();
					JOptionPane.showMessageDialog(null, "�ٽ� �õ��� �ֽʽÿ�.");
				} else {/** ������ ���� ���� ��� �޴��� ��Ḧ ������Ʈ�ϰ� ����Ʈ�������� ���ư� */
					UpdateIngredient(Main_Frame.modify_first_page.select_R_ID);
					UpdateMenu(Main_Frame.modify_first_page.select_R_ID);
					Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");
					clearPage();
				}
			} else
				JOptionPane.showMessageDialog(null, ErrorMessage);
		}
		/** Home��ư ���� �� ù�������� ���ư��� */
		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		/** menu1��ư ���� �Լ� */
		if (e.getSource() == menu1) {

			if (!menu1.isSelected()) {
				/** ��ư�� ���õǾ� ���� ���� �� Ȱ�� ���� */
				F_name1.setEnabled(true);
				F_price1.setEnabled(true);
				F_main1.setEnabled(true);
				F_sub1.setEnabled(true);
				Y1.setEnabled(true);
				N1.setEnabled(true);
			} else {
				/** ��ư�� ���õǾ� ���� �� ��Ȱ�� ���� */
				F_name1.setEnabled(false);
				F_price1.setEnabled(false);
				F_main1.setEnabled(false);
				F_sub1.setEnabled(false);
				Y1.setEnabled(false);
				N1.setEnabled(false);
			}

		}
		/** menu2��ư ���� �Լ� */
		if (e.getSource() == menu2) {

			if (!menu2.isSelected()) {
				/** ��ư�� ���õǾ� ���� ���� �� Ȱ�� ���� */
				F_name2.setEnabled(true);
				F_price2.setEnabled(true);
				F_main2.setEnabled(true);
				F_sub2.setEnabled(true);
				Y2.setEnabled(true);
				N2.setEnabled(true);
			} else {
				/** ��ư�� ���õǾ� ���� �� ��Ȱ�� ���� */
				F_name2.setEnabled(false);
				F_price2.setEnabled(false);
				F_main2.setEnabled(false);
				F_sub2.setEnabled(false);
				Y2.setEnabled(false);
				N2.setEnabled(false);
			}

		}

	}

	/** DB���� �˻��� ������� ���̵� �ش��ϴ� �޴��������� �޾ƿ��� �Լ�  �Ѳ����� ���� ���� ���ο� �並 ����*/
	public void getInfo(int id) {

		try {
			/** �ʱ�ȭ */
			Menu_info1.clear();
			Menu_info2.clear();

			/** ���� ����� �޾ƿ� */

			stmt = DB_Connection.stmt;

			String sqlstr = "select * from DBCOURSE_MENU_INFO_VIEW where Restaurant_ID=" + id;
			rs = stmt.executeQuery(sqlstr);

			/** �˻� ��� ���� */
			while (rs.next()) {
				/** �޴� 1�� */
				if (rs.getString(1).equals(id + "01")) {
					Menu_info1.add(rs.getString(3));
					Menu_info1.add(rs.getString(4));
					Menu_info1.add(rs.getString(5));
					Menu_info1.add(rs.getString(6));
					Menu_info1.add(rs.getString(7));
				}
				/** �޴� 2�� */
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

	/** �޾ƿ� ������ â�� �ҷ����� */
	public void setMenu() {

		String vege1;
		String vege2;
		/** 1�� �޴��� ���� �� �޾ƿ� ���� ���� */
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
		/** 2�� �޴��� ���� �� �޾ƿ� ���� ���� */
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

	/** ������ �޴� ������ �����ϴ� �Լ� */
	public void getUpdateMenuInfo() {

		/** �ʱ�ȭ */
		isError = false;
		ErrorMessage = null;
		Menu_info1.clear();
		Menu_info2.clear();

		try {

			/** 1�� �޴� �ʼ��� �Է��ؾ� �ϴ� â�� ����ְų� ���¸� �߸��Է��� ��� ���ܸ޼����� ����ϰ� �ٸ��� �Էµ� ���
			 * ArrayList ������ ���� */
			if (!menu1.isSelected()) {

				if (F_name1.getText().isEmpty())
					throw new Exception("Representative Food1�� �ʼ� �Է¶��Դϴ�. (�޴� �̸��� �Է��ϼ���.)");
				Menu_info1.add(F_name1.getText());
				if (!F_price1.getText().isEmpty() && !checkString(F_price1.getText()))
					throw new Exception("Price�� ���ڿ��� �մϴ�.");
				else if (!F_price1.getText().isEmpty() && checkString(F_price1.getText()))
					Menu_info1.add(F_price1.getText());
				else
					Menu_info1.add(null);
				if (F_main1.getText().isEmpty())
					throw new Exception("Main ingredient�� �ʼ� �Է¶��Դϴ�.");
				Menu_info1.add(F_main1.getText());
				Menu_info1.add(F_sub1.getText());
				if (Y1.isSelected())
					Menu_info1.add("Y");
				else if (N1.isSelected())
					Menu_info1.add("N");
				else
					throw new Exception("Vegetarian�� �ʼ� ���ö��Դϴ�.");
			}

			/** 2�� �޴� �ʼ��� �Է��ؾ� �ϴ� â�� ����ְų� ���¸� �߸��Է��� ��� ���ܸ޼����� ����ϰ� �ٸ��� �Էµ� ���
			 * ArrayList ������ ���� */
			if (!menu2.isSelected()) {

				if (F_name2.getText().isEmpty())
					throw new Exception("Representative Food2�� �ʼ� �Է¶��Դϴ�. (�޴� �̸��� �Է��ϼ���.)");
				Menu_info2.add(F_name2.getText());
				if (!F_price2.getText().isEmpty() && !checkString(F_price2.getText()))
					throw new Exception("Price�� ���ڿ��� �մϴ�.");
				else if (!F_price2.getText().isEmpty() && checkString(F_price2.getText()))
					Menu_info2.add(F_price2.getText());
				else
					Menu_info2.add(null);
				if (F_main2.getText().isEmpty())
					throw new Exception("Main ingredient�� �ʼ� �Է¶��Դϴ�.");
				Menu_info2.add(F_main2.getText());
				Menu_info2.add(F_sub2.getText());
				if (Y2.isSelected())
					Menu_info2.add("Y");
				else if (N2.isSelected())
					Menu_info2.add("N");
				else
					throw new Exception("Vegetarian�� �ʼ� ���ö��Դϴ�.");

			}

		} catch (Exception e) {

			isError = true;
			ErrorMessage = e.getMessage();

		}
	}

	/** DB�� �޴��� ������Ʈ�ϴ� �Լ� */
	public void UpdateMenu(int id) {
		try {
			/** �ʱ�ȭ */
			pstmt1 = null;
			pstmt2 = null;
			rollback = false;

			/** ���� ����� �޾ƿ� */

			String sqlstr1;
			String sqlstr2;

			/** 1���޴� ������Ʈ */
			if (!menu1.isSelected()) {
				sqlstr1 = "update DBCOURSE_Menu set Representative_Food=?, Price=? where Restaurant_ID=" + id
						+ " and Menu_ID=" + id + "01";

				pstmt1 = DB_Connection.conn.prepareStatement(sqlstr1);

				/** ?�� �� ������ ���� */
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

			/** 2�� �޴� ������Ʈ */
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
	/** DB�� ��� ���� ������Ʈ */
	public void UpdateIngredient(int id) {
		try {

			pstmt1 = null;
			pstmt2 = null;
			rollback = false;
			/** ���� ����� �޾ƿ� */
			String sqlstr1;
			String sqlstr2;

			if (!menu1.isSelected()) {/** �޴��� �ԷµǾ��� ��� */
				sqlstr1 = "update DBCOURSE_Ingredients set Food_Name=?, main_ingredient=?, sub_ingredient=?, Vegetarian_YN=? where Menu_ID="
						+ id + "01";

				pstmt1 = DB_Connection.conn.prepareStatement(sqlstr1);

				/** ���ǿ� �´� tuple�� ���� ������Ʈ */
				pstmt1.setString(1, Menu_info1.get(0));
				pstmt1.setString(2, Menu_info1.get(2));
				pstmt1.setString(3, Menu_info1.get(3));

				if (Menu_info1.get(4).isEmpty())
					pstmt1.setString(4, null);
				else
					pstmt1.setString(4, Menu_info1.get(4).substring(0, 1));

			} else {/** �޴��� �Էµ��� �ʾ��� ��� ������� ���̵� ������ ������ ���� null�� �Է� */
				sqlstr1 = "update DBCOURSE_Ingredients set Food_Name = null, main_ingredient = null, sub_ingredient = null, Vegetarian_YN = null where Menu_ID="
						+ id + "01";

				pstmt1 = DB_Connection.conn.prepareStatement(sqlstr1);
			}

			if (!menu2.isSelected()) {/** �޴��� �ԷµǾ��� ��� */

				sqlstr2 = "update DBCOURSE_Ingredients set Food_Name=?, main_ingredient=?, sub_ingredient=?, Vegetarian_YN=? where Menu_ID="
						+ id + "02";
				pstmt2 = DB_Connection.conn.prepareStatement(sqlstr2);
				/** ���ǿ� �´� tuple�� ���� ������Ʈ */
				pstmt2.setString(1, Menu_info2.get(0));
				pstmt2.setString(2, Menu_info2.get(2));
				pstmt2.setString(3, Menu_info2.get(3));

				if (Menu_info2.get(4).isEmpty())
					pstmt2.setString(4, null);
				else
					pstmt2.setString(4, Menu_info2.get(4).substring(0, 1));

			} else {/** �޴��� �Էµ��� �ʾ��� ��� ������� ���̵� ������ ������ ���� null�� �Է� */
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

	/** ���ڿ��� ���ڷ� �̷���� �ִ��� �Ǻ��ϴ� �Լ� */
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

	/** â �ʱ�ȭ �Լ� */
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