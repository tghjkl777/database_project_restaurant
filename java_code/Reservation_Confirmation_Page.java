package team12;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @author ���ѳ�.
 * ���� Ȯ�� ������.
 */
public class Reservation_Confirmation_Page extends JPanel implements ActionListener {

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
	JButton NEXT = new JButton(next);
	JButton Enter = new JButton();
	JButton B_temp;
	JButton[] Header = new JButton[4];
	JTextField Search = new JTextField();
	JPanel search_table0 = new JPanel();
	JPanel search_table1 = new JPanel();
	JPanel search_table2 = new JPanel();
	JScrollPane SearchPane = new JScrollPane(search_table0);
	String header[] = { "Booking ID", "Restaurant", "Reserv_Day", "Reserv_Time" };
	String search_phone_num;
	ArrayList<String> search_info = new ArrayList<>(); // �˻� ��� ������ ���� ���� ����

	Statement stmt;
	ResultSet rs;
	int exist_result = 0; // ����� ���� ��츦 ����� ���� ����.

	/**
	 * ���� Ȯ�� panel ����.
	 * �Ͼ� ��濡 ���� ��ġ�� ���� ��ư�� �÷ȴ�.
	 * �˻� ��ư, Ȩ ��ư, ���� ������ ��ư, �˻���, ������� �ִ�.
	 */
	Reservation_Confirmation_Page() {

		this.setBackground(Color.white); // �Ͼ� ���
		this.setLayout(null); // ���� ��ġ�� ��ġ

		// �˻��� ����
		Search.setBounds(105, logo_height / 4 * 3, 200, 30);
		Search.setToolTipText("000-0000-0000 �������� ��ȭ��ȣ�� �Է��ϼ���.");
		Search.setHorizontalAlignment(JTextField.CENTER);
		// �˻� ��ư ����
		Enter.setBounds(315, logo_height / 4 * 3, 80, 30);
		Enter.setText("Search");
		Enter.setBackground(Color.WHITE);
		Enter.setToolTipText("Search. �˻�.");
		Enter.addActionListener(this);
		// Ȩ ��ư ����
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. Ȩ ��������. (Color Change)");
		HOME.setBorderPainted(false); // ��ư ��輱 ���ֱ�
		HOME.addActionListener(this);
		// ���� �������� ���� ��ư ����
		NEXT.setBounds(436, logo_height * 3 - 60, next_width / 8, next_height / 8);
		NEXT.setBackground(Color.WHITE);
		NEXT.setToolTipText("Next. ���� ��������.");
		NEXT.setBorderPainted(false); // ��ư ��輱 ���ֱ�
		NEXT.addActionListener(this);
		// ����� ����
		search_table1.setLayout(new GridLayout(1, 4));
		for (int i = 0; i < 4; i++) {
			Header[i] = new JButton(header[i]);
			Header[i].setBackground(new Color(224, 247, 239));
			search_table1.add(Header[i]);
		}
		Header[0].setToolTipText("�����ȣ");
		Header[1].setToolTipText("������� �̸�");
		Header[2].setToolTipText("���� ��¥");
		Header[3].setToolTipText("���� �ð�");
		search_table2.setLayout(null);
		search_table2.setBackground(Color.WHITE);
		search_table0.setLayout(new BorderLayout());
		search_table0.add(search_table1, BorderLayout.NORTH);
		search_table0.add(search_table2, BorderLayout.CENTER);
		SearchPane.setBounds(30, logo_height + 30, 439, 250);
		SearchPane.setToolTipText("�˻� ������Դϴ�.");

		// panel�� ���빰 ����
		this.add(HOME);
		this.add(NEXT);
		this.add(Search);
		this.add(Enter);
		this.add(SearchPane);

	}

	/**
	 * Panel�� �׸���.
	 * �۾��� �׸����� �׸���.
	 * */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// ��, �Ʒ� �κ�
		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);
		g.drawImage(next_img, getWidth() - next_width / 2, getHeight() - logo_height / 2, next_width / 2,
				next_height / 2, this);
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Confirm your reservation.", logo_width / 2 + 55, logo_height / 4 + 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 15));
		g.drawString("���� ������ Ȯ���ϼ���.", logo_width / 5 * 4 + 18, logo_height / 5 * 2 + 10);
		
		// �˻� ����� ���� ó��
		if (exist_result == -1) {
			g.setColor(new Color(243, 128, 137));
			g.setFont(new Font("���� ���", Font.BOLD, 11));
			g.drawString("��� ����. >", logo_width / 2 + 105, logo_height / 4 * 3 + 50);
			g.setFont(new Font("Calibri", Font.BOLD, 13));
			g.drawString("< No results.", logo_width / 2 + 30, logo_height / 4 * 3 + 50);
		} else if (exist_result == 0) {
			g.setColor(new Color(243, 128, 137));
			g.setFont(new Font("Calibri", Font.BOLD, 13));
			g.drawString("< Enter your phone number. >", logo_width / 2 + 20, logo_height / 4 * 3 + 50);
		}

	}

	/**
	 * ��ư ���� ���� �Լ�.
	 * + Enter��ư�� ������ �˻����� text�� �޾Ƽ� �����ͺ��̽� �󿡼� ������ �˻���, �� ����� �޾ƿ� â�� �Է��Ѵ�.
	 * + HOME��ư�� ������ HOME_Page�� �̵��Ѵ�.
	 * + NEXT��ư�� ������ ���� ������(Select_Mode_User)�� �̵��Ѵ�.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == NEXT) {
			// ȭ�� �ʱ�ȭ
			Search.setText(null);
			search_table2.removeAll();
			exist_result = 0;
			// ���� ��������
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "11");
		}

		if (e.getSource() == HOME) {
			// ȭ�� �ʱ�ȭ
			Search.setText(null);
			search_table2.removeAll();
			exist_result = 0;
			// Ȩ ��������
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		}

		if (e.getSource() == Enter) {

			// �˻� ����� ��ȭ ��ȣ ����
			search_phone_num = Search.getText();

			// �˻����� null�� �ƴ� ��쿡 ��� ����
			if (search_phone_num != null) {

				// �˻� ����� �ʱ�ȭ
				search_table2.removeAll();

				// �˻��Ѵ�
				Reservation_Search();

				// �˻� ����� ����� panel�� �ִ´�.
				int width = 0, height = 0;
				for (int i = 0; i < search_info.size(); i++) {
					B_temp = new JButton(search_info.get(i));
					B_temp.setFont(new Font("���� ���", Font.BOLD, 10));
					B_temp.setToolTipText(search_info.get(i));
					B_temp.setBounds(width, height, 100, 30);
					B_temp.setBackground(Color.WHITE);
					B_temp.setBorderPainted(false);
					search_table2.add(B_temp);
					width = width + 110;
					if ((i + 1) % 4 == 0) {
						width = 0;
						height = height + 30;
					}
				}

			} else
				exist_result = 0;

			// �ٲ� �κ��� â�� ������Ʈ�Ѵ�.
			SearchPane.updateUI();
			repaint();
		}

	}

	/**
	 * ���� Ȯ���� ���� �˻� �Լ�.
	 * �����ͺ��̽��� ������ select�� ���� �˻����� ���� ��ȭ��ȣ�� �ش��ϴ� ������ �ִ��� Ȯ���Ѵ�.
	 */
	public void Reservation_Search() {

		try {
			// �ʱ�ȭ
			exist_result = 0;
			search_info.clear();

			// ���� ����� �޾ƿ´�.
			stmt = DB_Connection.stmt;
			rs = stmt.executeQuery("select * from DBCOURSE_confirm_reservation_view order by Booking_ID");

			// �˻� ����� ArrayList ������ �����Ѵ�.
			while (rs.next()) {
				if (rs.getString("Phone_Number").equals(search_phone_num)) {
					search_info.add(rs.getString(1));
					search_info.add(rs.getString(2));
					search_info.add(rs.getString(3));
					search_info.add(rs.getString(4));
					exist_result = 1;
				}

			}

			// ����� ���� ��� ���� ������ ���� �̸� �˸���. ���� public void paintComponent(Graphics g) �Լ� ����.
			if (exist_result != 1)
				exist_result = -1;

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}