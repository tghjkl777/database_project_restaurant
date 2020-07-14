package team12;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;

/**
 * ������ ��������� ���並 �ۼ��ϴ� Ŭ����
 * @author �����
 *
 */
public class Review extends JPanel implements ActionListener {
	// �ʿ� ������, ���빰, ���� ����
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
	JButton SAVE = new JButton();
	ButtonGroup group1 = new ButtonGroup();
	JRadioButton S1 = new JRadioButton();
	JRadioButton S2 = new JRadioButton();
	JTextField Restaurant_name = new JTextField();
	JTextField ID = new JTextField();
	JTextField age = new JTextField();
	JTextField Review = new JTextField();
	JTextField Star = new JTextField();
	String review;
	String restaurant_name;

	Connection conn;
	Statement stmt;
	PreparedStatement pstUpdate;
	PreparedStatement pstRemove;
	ResultSet rs;
	ResultSet rs1;

	public Review() {

		this.setBackground(Color.white); // �Ͼ� ���
		this.setLayout(null); // ���� ��ġ�� ��ġ

		// ������� �̸� ����
		Restaurant_name.setBounds(240, logo_height / 4 * 3 + 30, 200, 30);
		Restaurant_name.setToolTipText("�����Ͻ� ������� �̸��Դϴ�");
		Restaurant_name.setHorizontalAlignment(JTextField.CENTER);
		Restaurant_name.setEditable(false);

		// Ȩ ��ư ����
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. Ȩ ��������. (Color Change)");
		HOME.setBorderPainted(false); // ��ư ��輱 ���ֱ�
		HOME.addActionListener(this);

		ID.setBounds(240, logo_height / 4 * 3 + 85, 200, 20);
		ID.setToolTipText("���̵� �Է¶��Դϴ�.");
		age.setBounds(240, logo_height / 4 * 3 + 135, 200, 20);
		age.setToolTipText("���� �Է¶��Դϴ�.");
		Star.setBounds(240, logo_height / 4 * 3 + 236, 50, 20);
		Star.setToolTipText("���� �Է¶��Դϴ�.(1��~5��)");
		Review.setBounds(240, logo_height / 4 * 3 + 286, 200, 50);
		Review.setToolTipText("������ �Է¶��Դϴ�.");

		S1.setBounds(240, logo_height / 4 * 3 + 187, 20, 15);
		S1.setBackground(Color.white);
		S1.setToolTipText("���� ���ö��Դϴ�.");

		S2.setBounds(340, logo_height / 4 * 3 + 187, 20, 15);
		S2.setBackground(Color.white);
		S2.setToolTipText("���� ���ö��Դϴ�.");

		group1.add(S1);
		group1.add(S2);

		// ���� ��ư
		SAVE.setBounds(160, logo_height * 3 - 80, 150, 30);
		SAVE.setText("SAVE");
		SAVE.setBackground(Color.WHITE);
		SAVE.setToolTipText("Save. ����.");
		SAVE.addActionListener(this);

		this.add(Restaurant_name);
		this.add(HOME);
		this.add(SAVE);
		this.add(ID);
		this.add(age);
		this.add(Review);
		this.add(S1);
		this.add(S2);
		this.add(Star);

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		// ��, �Ʒ� �κ�
		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Write a Review.", logo_width / 2 + 107, logo_height / 4 + 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 15));
		g.drawString("���並 �ۼ����ּ���.", logo_width / 5 * 4 + 40, logo_height / 5 * 2 + 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+�� �ʼ� �Է¶��Դϴ�.)", logo_width / 5 * 4 + 43, logo_height / 5 * 2 + 35);

		// �߰� �κ�
		g.setColor(new Color(0, 49, 39));
		g.setFont(new Font("Calibri", Font.PLAIN, 18));
		g.drawString("Restaurant name ", 50, logo_height / 4 * 3 + 50);
		g.setFont(new Font("Calibri", Font.PLAIN, 15));
		g.drawString("+   ID ", 80, logo_height / 4 * 3 + 100);
		g.drawString("+   Age", 80, logo_height / 4 * 3 + 150);
		g.drawString("+   Gender", 80, logo_height / 4 * 3 + 200);
		g.drawString("+   Star", 80, logo_height / 4 * 3 + 250);
		g.drawString("+   Review", 80, logo_height / 4 * 3 + 300);
		g.drawString("Female", 265, logo_height / 4 * 3 + 200);
		g.drawString("Male", 365, logo_height / 4 * 3 + 200);
		g.drawString("(One to Five)", 300, logo_height / 4 * 3 + 250);

		g.setColor(new Color(243, 128, 137));
		g.drawString("+", 80, logo_height / 4 * 3 + 100);
		g.drawString("+", 80, logo_height / 4 * 3 + 150);
		g.drawString("+", 80, logo_height / 4 * 3 + 200);
		g.drawString("+", 80, logo_height / 4 * 3 + 250);
		g.drawString("+", 80, logo_height / 4 * 3 + 300);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.drawString(" +", logo_width / 5 * 4 + 43, logo_height / 5 * 2 + 35);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");

		// ���� ����
		if (e.getSource() == SAVE) {
			// ���� �ʵ� �� �޾ƿ���
			String restaurant_name = Restaurant_name.getText();
			String id = null;
			String Age = null;
			String review = null;
			String star = null;
			int age_i;
			int age_category = 0;
			double star_d = 0.0;

			// �ʼ��Է��� �ʵ忡 ���Ͽ� ����ó��
			if (ID.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "���̵�� �ʼ��Է� �Դϴ�!");
			} else
				id = ID.getText();

			if (age.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "���̴� �ʼ��Է� �Դϴ�!");
			} else
				Age = age.getText();
			if (Review.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "����� �ʼ��Է� �Դϴ�!");
			} else
				review = Review.getText();
			if (Star.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "������ �ʼ��Է� �Դϴ�!");
			} else
				star = Star.getText();
			if ((!ID.getText().isEmpty()) && (!age.getText().isEmpty()) && (!Review.getText().isEmpty())
					&& (!Star.getText().isEmpty())) {
				try {
					age_i = Integer.parseInt(Age);
					age_category = 0;
					star_d = Double.parseDouble(star);

					// ���̿� ������ ī�װ� ����
					if (10 <= age_i && age_i < 20 && S1.isSelected())
						age_category = 11;
					else if (10 <= age_i && age_i < 20 && S2.isSelected())
						age_category = 12;
					else if (20 <= age_i && age_i < 30 && S1.isSelected())
						age_category = 21;
					else if (20 <= age_i && age_i < 30 && S2.isSelected())
						age_category = 22;
					else if (30 <= age_i && age_i < 40 && S1.isSelected())
						age_category = 31;
					else if (30 <= age_i && age_i < 40 && S2.isSelected())
						age_category = 32;
					else if (40 <= age_i && age_i < 50 && S1.isSelected())
						age_category = 41;
					else if (40 <= age_i && age_i < 50 && S2.isSelected())
						age_category = 42;
					else if (50 <= age_i && age_i < 60 && S1.isSelected())
						age_category = 51;
					else if (50 <= age_i && age_i < 60 && S2.isSelected())
						age_category = 52;
					else if (60 <= age_i && age_i < 70 && S1.isSelected())
						age_category = 61;
					else if (60 <= age_i && age_i < 70 && S2.isSelected())
						age_category = 62;
					else if (70 <= age_i && age_i < 80 && S1.isSelected())
						age_category = 71;
					else if (70 <= age_i && age_i < 80 && S2.isSelected())
						age_category = 72;
					else
						age_category = 0;

				} catch (Exception e2) {
				}

				try {
					// Review���̺� insert
					stmt = DB_Connection.stmt;
					rs1 = stmt.executeQuery("select Review_ID from DBCOURSE_Reviews");
					while (rs1.next()) {
						String Id = rs1.getString(1);
						// id�� primary key�̹Ƿ� �ߺ�����
						if (id.equals(Id)) {
							clearPage();
							// �ߺ��Ǵ°�� �˾�â���� ��� ��, �ٽ� �Է¹���
							JOptionPane.showMessageDialog(null, "�ߺ��� ���̵��Դϴ�.");
							id = null;
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				// ���̵� ������ ��
				if (!id.equals(null)) {
					insert_review(id, restaurant_name, age_category, star_d, review);
					Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
				}

			}
		}

	}
	/**
	 * �ۼ��� ���並 ��������� �򰡿� �ݿ��ϴ� �Լ�
	 * @param id ���並 �ۼ��� ��������� ���̵�
	 * @param star �ۼ����� ����
	 */
	public void update_ev(int id,double star) {
		try {
			conn = DB_Connection.conn;
			stmt = DB_Connection.stmt;
			conn.setAutoCommit(false);
			String input="select Star_avg,Number_Of_Reviews from DBCOURSE_Evaluation where Restaurant_ID="+id;
			rs=stmt.executeQuery(input);
			
			while(rs.next()){
				double star_a=Double.parseDouble(rs.getString(1));
				int num=Integer.parseInt(rs.getString(2));
				double cal=((star_a*num)+star)/(num+1);
				pstRemove = conn.prepareStatement("delete from DBCOURSE_Evaluation where Restaurant_ID = ?");
				pstRemove.setInt(1,id);
				pstRemove.executeUpdate();
				
				pstUpdate=conn.prepareStatement("insert into DBCOURSE_Evaluation values(?,?,?)");
				pstUpdate.setInt(1, id);
				pstUpdate.setDouble(2, cal);
				pstUpdate.setInt(3, num+1);
				pstUpdate.executeUpdate();
				conn.commit();
			}
		} catch (SQLException e3) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * �ۼ��� ���並 DB�� �����ϴ� �޼ҵ�
	 * @param id ���並 �ۼ��� ��������� ���̵�
	 * @param restaurant_name ���並 �ۼ��� ��������� �̸�
	 * @param age_category ���� �ۼ����� ���̹���
	 * @param star ���� �ۼ����� ������
	 * @param review �ۼ����� ������
	 */
	public void insert_review(String id, String restaurant_name, int age_category, double star, String review) {
		try {
			conn = DB_Connection.conn;
			stmt = DB_Connection.stmt;
			conn.setAutoCommit(false);

			rs = stmt.executeQuery("select Restaurant_ID,Restaurant_Name from DBCOURSE_Restaurants");

			while (rs.next()) {
				String ID = rs.getString(1);
				String Name = rs.getString(2);
				int ID_i = Integer.parseInt(ID);

				if (Name.equals(restaurant_name)) {
					pstUpdate = conn.prepareStatement("insert into DBCOURSE_Reviews values(?,?,?,?,?)");
					pstUpdate.setString(1, id);
					pstUpdate.setInt(2, ID_i);
					pstUpdate.setInt(3, age_category);
					pstUpdate.setDouble(4, star);
					pstUpdate.setString(5, review);
					pstUpdate.executeUpdate();
					conn.commit();
					update_ev(ID_i,star);

				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * ���̵� �׸��� �ʱ�ȭ��Ű�� �޼ҵ�(�ߺ��� ���̵��ϰ�� ����ϱ� ����)
	 */
	public void clearPage() {
		ID.setText(null);
	}

	/**
	 * ������ Ŭ�����κ��� ������� �̸��� �޾ƿ��� �޼ҵ�
	 * @param name ���並 �������ϴ� ������� �̸�
	 */
	public void getInfo(String name) {
		restaurant_name = name;
		ID.setText(null);
		age.setText(null);
		Star.setText(null);
		Review.setText(null);
		S1.setSelected(false);
		S2.setSelected(false);
		Restaurant_name.setText(restaurant_name);
	}
}