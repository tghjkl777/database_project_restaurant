package team12;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

/**
 * @author ���ѳ�.
 * Additional_Restaurants_Second_Page�� �����ϴ� JPanel.
 * �޴� ���ݿ� ���� ������ �޾� database�� insert�Ѵ�.
 */
public class Additional_Restaurants_Second_Page extends JPanel implements ActionListener {

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
	// ������� ���� ���� ���� ��ü ����
	Restaurant_Info restaurant = Additional_Restaurants_First_Page.restaurant;
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
	 * Additional_Restaurants_Second_Page JPanel ����.
	 * �Ͼ� ��濡 ���� ��ġ�� ���� ��ư�� �÷ȴ�.
	 * Ȩ ��ư, ���� �������� ���� ��ư, �޴� ������ �Է��ϴ� �Է¶����� �ִ�.
	 * �޴��� �ִ� 2������ �Է°����ϸ�, �Է����� ���� ���� üũ�� ���� block��Ű�� �ȴ�.
	 * �ʼ� �Է¶��� �ݵ�� �Է��ؾ� �Ѵ�.
	 * */
	Additional_Restaurants_Second_Page() {

		this.setBackground(Color.white); // �Ͼ� ���
		this.setLayout(null); // ���� ��ġ�� ��ġ

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
		// �޴� �߰� ��ư
		menu1.setBounds(34, logo_height / 4 * 3 - 15, 20, 15);
		menu1.setBackground(Color.white);
		menu1.setToolTipText("�� �޴����� �߰��Է��Ͻðڽ��ϱ�?");
		menu1.addActionListener(this);
		menu2.setBounds(34, logo_height / 4 * 3 + 200, 20, 15);
		menu2.setBackground(Color.white);
		menu2.setToolTipText("�� �޴����� �߰��Է��Ͻðڽ��ϱ�?");
		menu2.addActionListener(this);

		// 1�� ���� ���빰�� ���� ��ġ �� ũ��, ���� �Ӽ� ����
		F_name1.setBounds(225, logo_height / 4 * 3 - 15, 220, 20);
		F_name1.setToolTipText("��ǥ �޴� �̸� �Է¶��Դϴ�. (�ظ޴� ��ü ��������)");
		F_price1.setBounds(245, logo_height / 4 * 3 + 25, 200, 20);
		F_price1.setToolTipText("�޴� ���� �Է¶��Դϴ�.");
		F_main1.setBounds(245, logo_height / 4 * 3 + 65, 200, 20);
		F_main1.setToolTipText("�� ��� �Է¶��Դϴ�.");
		F_sub1.setBounds(245, logo_height / 4 * 3 + 105, 200, 20);
		F_sub1.setToolTipText("�� ��� �Է¶��Դϴ�.");
		// Vegetarian ���� ��ư ����
		Y1.setBounds(240, logo_height / 4 * 3 + 148, 20, 15);
		Y1.setBackground(Color.white);
		Y1.setToolTipText("��� ���� ���� ���ö��Դϴ�.");
		N1.setBounds(310, logo_height / 4 * 3 + 148, 20, 15);
		N1.setBackground(Color.white);
		N1.setToolTipText("��� ���� ���� ���ö��Դϴ�.");
		// Y, N �� �ϳ��� ���ð���
		group1.add(Y1);
		group1.add(N1);
		// ��Ȱ�� ����
		F_name1.setEnabled(false);
		F_price1.setEnabled(false);
		F_main1.setEnabled(false);
		F_sub1.setEnabled(false);
		Y1.setEnabled(false);
		N1.setEnabled(false);

		// 2�� ���� ���빰�� ���� ��ġ �� ũ��, ���� �Ӽ� ����
		F_name2.setBounds(225, logo_height / 4 * 3 + 200, 220, 20);
		F_name2.setToolTipText("��ǥ �޴� �̸� �Է¶��Դϴ�. (�ظ޴� ��ü ��������)");
		F_price2.setBounds(245, logo_height / 4 * 3 + 240, 200, 20);
		F_price2.setToolTipText("�޴� ���� �Է¶��Դϴ�.");
		F_main2.setBounds(245, logo_height / 4 * 3 + 280, 200, 20);
		F_main2.setToolTipText("�� ��� �Է¶��Դϴ�.");
		F_sub2.setBounds(245, logo_height / 4 * 3 + 320, 200, 20);
		F_sub2.setToolTipText("�� ��� �Է¶��Դϴ�.");
		// Vegetarian ���� ��ư ����
		Y2.setBounds(240, logo_height / 4 * 3 + 363, 20, 15);
		Y2.setBackground(Color.white);
		Y2.setToolTipText("��� ���� ���� ���ö��Դϴ�.");
		N2.setBounds(310, logo_height / 4 * 3 + 363, 20, 15);
		N2.setBackground(Color.white);
		N2.setToolTipText("��� ���� ���� ���ö��Դϴ�.");
		// Y, N �� �ϳ��� ���ð���
		group2.add(Y2);
		group2.add(N2);
		// ��Ȱ�� ����
		F_name2.setEnabled(false);
		F_price2.setEnabled(false);
		F_main2.setEnabled(false);
		F_sub2.setEnabled(false);
		Y2.setEnabled(false);
		N2.setEnabled(false);

		// ���빰�� ȭ�鿡 ���
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

		// �� ���� �κ�
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

	/**
	 * ��ư ���� ���� �Լ�.
	 * + NEXT��ư�� ������ �޴� ������ database�� insert�� ��, 
	 * �˾�â�� ���� �޴� �Է� ���¸� �˷��ְ� Restaurant_List_page�� �̵��Ѵ�.
	 * �ʼ� �Է�â�� ������� ��, ���ݿ� ���ڰ� �ƴ� ���ڿ��� ���� �� �� ���� �߻� �� �˾�â���� ��� ������ ����.
	 * â �̵� �Ŀ��� clearPage() �Լ��� ���� â�� �ʱ�ȭ��Ų��.
	 * + HOME��ư�� ������ HOME_Page�� �̵��Ѵ�.
	 * + menu1, menu2�� �����ϸ� �ش� �޴��� blockó�� �Ǿ� �޴� �Է� X�� ������ �����Ѵ�.
	 * */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == NEXT) {

			// ����ڰ� �Է��� ������ Restaurant_Info��ü�� �ϴ� �����Ѵ�
			getInfo();

			// ���� �Է¿� ������ ���ٸ� �����ͺ��̽��� �� ������ �����ϰ� �������� �̵���Ų��.
			if (!isError) {

				// �����ͺ��̽��� ���� ����
				storeInfo();

				if (rollback == true) {
					clearPage();
					JOptionPane.showMessageDialog(null, "�ٽ� �õ��� �ֽʽÿ�.");
				} else {

					// �ƹ��͵� ���õ��� �ʾ��� �� �޴� ������ ���� ���θ� ����.
					if (!menu1.isSelected() && !menu2.isSelected()) {

						int confirm = JOptionPane.showConfirmDialog(null, "�޴� �������� �����Ͻðڽ��ϱ�?", "Do you want to omit?",
								JOptionPane.YES_NO_OPTION);

						if (confirm == JOptionPane.YES_OPTION) {
							clearPage();
							Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");
						}

					} else {
						// ���õ� �޴��� ���� �� �߰��� �޴��� �˷���.
						String Message = null;
						if (menu1.isSelected() && menu2.isSelected())
							Message = "�޴�1, �޴�2�� �߰� �ԷµǾ����ϴ�.";
						else if (menu1.isSelected() && !menu2.isSelected())
							Message = "�޴�1�� �߰� �ԷµǾ����ϴ�.";
						else if (!menu1.isSelected() && menu2.isSelected())
							Message = "�޴�2�� �߰� �ԷµǾ����ϴ�.";
						JOptionPane.showMessageDialog(null, Message);
						Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");
						clearPage();
					}
				}
			}
			// ���� �Է¿� ������ �ִٸ� error�߻� ������ �˷��ش�.
			else
				JOptionPane.showMessageDialog(null, ErrorMessage);

		}

		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");

		if (e.getSource() == menu1) {

			if (menu1.isSelected()) {
				// Ȱ�� ����
				F_name1.setEnabled(true);
				F_price1.setEnabled(true);
				F_main1.setEnabled(true);
				F_sub1.setEnabled(true);
				Y1.setEnabled(true);
				N1.setEnabled(true);
			} else {
				// ��Ȱ�� ����
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
				// Ȱ�� ����
				F_name2.setEnabled(true);
				F_price2.setEnabled(true);
				F_main2.setEnabled(true);
				F_sub2.setEnabled(true);
				Y2.setEnabled(true);
				N2.setEnabled(true);
			} else {
				// ��Ȱ�� ����
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
	 * �Է¹��� ������� ������ Restaurant_Info ��ü�� �����ϴ� �Լ�
	 * ����ó�� �ʿ� (���� �̸�, �����, ��� ����, ���� ������������).
	 * ����ó���� throw new�� �̿��� errorMessage�� �ѱ�� ������� �����Ѵ�.
	 * ���⼭�� errorMessage�� ��ư��ɿ��� �˾�â�� ���� ��� ������ ���� �� ���ȴ�.
	 */
	public void getInfo() {

		try {

			// �ʱ�ȭ
			isError = false;
			ErrorMessage = null;
			restaurant.Clear_M();
			// �޴� ���̵� ����
			restaurant.Create_M();

			// ����ó�� �ʿ� -> ���� �̸�, �����, ��� ����, ���� ������������
			if (menu1.isSelected()) {
				if (F_name1.getText().isEmpty())
					throw new Exception("Representative Food1�� �ʼ� �Է¶��Դϴ�. (�޴� �̸��� �Է��ϼ���.)");
				restaurant.M_Food1 = F_name1.getText();
				if (!F_price1.getText().isEmpty() && !checkString(F_price1.getText()))
					throw new Exception("Price�� ���ڿ��� �մϴ�.");
				else if (!F_price1.getText().isEmpty() && checkString(F_price1.getText()))
					restaurant.M_Price1 = Integer.parseInt(F_price1.getText());
				if (F_main1.getText().isEmpty())
					throw new Exception("Main ingredient�� �ʼ� �Է¶��Դϴ�.");
				restaurant.M_main1 = F_main1.getText();
				restaurant.M_sub1 = F_sub1.getText();
				if (Y1.isSelected())
					restaurant.M_vege1 = "Y";
				else if (N1.isSelected())
					restaurant.M_vege1 = "N";
				else
					throw new Exception("Vegetarian�� �ʼ� ���ö��Դϴ�.");
			}

			if (menu2.isSelected()) {
				if (F_name2.getText().isEmpty())
					throw new Exception("Representative Food2�� �ʼ� �Է¶��Դϴ�. (�޴� �̸��� �Է��ϼ���.)");
				restaurant.M_Food2 = F_name2.getText();
				if (!F_price2.getText().isEmpty() && !checkString(F_price2.getText()))
					throw new Exception("Price�� ���ڿ��� �մϴ�.");
				else if (!F_price2.getText().isEmpty() && checkString(F_price2.getText()))
					restaurant.M_Price2 = Integer.parseInt(F_price2.getText());
				if (F_main2.getText().isEmpty())
					throw new Exception("Main ingredient�� �ʼ� �Է¶��Դϴ�.");
				restaurant.M_main2 = F_main2.getText();
				restaurant.M_sub2 = F_sub2.getText();
				if (Y2.isSelected())
					restaurant.M_vege2 = "Y";
				else if (N2.isSelected())
					restaurant.M_vege2 = "N";
				else
					throw new Exception("Vegetarian�� �ʼ� ���ö��Դϴ�.");
			}

		} catch (Exception e) {

			isError = true;
			ErrorMessage = e.getMessage();

		}

	}

	/**
	 *  ���ڿ��� ���ڷ� �̷���� �ִ��� Ȯ��
	 * @param str �������� Ȯ���ϰ� ���� ���ڿ�
	 * @return ���ڸ� true, ���ڰ� �ƴ� ���ڿ��̸� false�� return�Ѵ�.
	 *  �Լ��� Integer.parseInt(str) �ڵ� ���� ��,
	 *  ������ �߻��ϸ� catch���� ���� return���� false�� ���ϴ� ������� �����Ѵ�.
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
				System.out.println("Additional_Restaurants_Second_Page - rollback");
				JOptionPane.showMessageDialog(null, "RollBack");
				rollback = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			// ���� �߻��� �ѹ�
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
	 *  â �ʱ�ȭ �Լ�
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