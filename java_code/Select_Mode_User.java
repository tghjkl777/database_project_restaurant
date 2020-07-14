package team12;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * for users to select ������� �˻�/�޴� �˻�/����Ȯ�� 
 * @author �ڼ���
 *
 */
public class Select_Mode_User extends JPanel implements ActionListener {

	// �ʿ� ������, ���빰, ���� ����
	ImageIcon logo_icon = Main_Frame.home_page.logo_icon;
	ImageIcon readme = Main_Frame.home_page.readme;

	ImageIcon restaurant_basic = new ImageIcon("images/Search_R.jpg");
	ImageIcon restaurant_on = new ImageIcon("images/Search_R_on.jpg");

	ImageIcon menu_basic = new ImageIcon("images/Search_M.jpg");
	ImageIcon menu_on = new ImageIcon("images/Search_M_on.jpg");

	ImageIcon reservation_basic = new ImageIcon("images/Confirm_Reservation.jpg");
	ImageIcon reservation_on = new ImageIcon("images/Confirm_Reservation_on.jpg");
	Image logo_img = logo_icon.getImage();

	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;

	JButton HOME = new JButton(logo_icon);
	JButton Restaurant = new JButton(restaurant_basic);
	JButton Menu = new JButton(menu_basic);
	JButton Reservation = new JButton(reservation_basic);

	boolean ColorChange = true;
	boolean ReadMe = false;

	Select_Mode_User() {

		this.setBackground(Color.white); // �Ͼ� ���
		this.setLayout(null); // ���� ��ġ�� ��ġ

		// Ȩ ��ư ����
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. Ȩ ��������.");
		HOME.setBorderPainted(false); // ��ư ��輱 ���ֱ�
		HOME.addActionListener(this);

		Restaurant.setBounds(0, 140, 500, 100);
		Restaurant.setBackground(Color.WHITE);
		Restaurant.setToolTipText("������� �˻�");
		Restaurant.setRolloverIcon(restaurant_on);
		Restaurant.setPressedIcon(restaurant_on);
		Restaurant.setBorderPainted(false);
		Restaurant.addActionListener(this);

		Menu.setBounds(0, 280, 500, 100);
		Menu.setBackground(Color.WHITE);
		Menu.setRolloverIcon(menu_on);
		Menu.setPressedIcon(menu_on);
		Menu.setToolTipText("�޴� �˻�");
		Menu.setBorderPainted(false);
		Menu.addActionListener(this);

		Reservation.setBounds(0, 420, 500, 100);
		Reservation.setBackground(Color.WHITE);
		Reservation.setRolloverIcon(reservation_on);
		Reservation.setPressedIcon(reservation_on);
		Reservation.setToolTipText("���� Ȯ��");
		Reservation.setBorderPainted(false);
		Reservation.addActionListener(this);

		this.add(HOME);

		this.add(Restaurant);
		this.add(Menu);
		this.add(Reservation);

	}

	// Panel�� �׸���
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 35));
		g.drawString("Select a user service.", logo_width / 2 + 20, logo_height / 4 + 15);
		g.setColor(new Color(243, 128, 137));
		g.setFont(new Font("���� ���", Font.BOLD, 20));
		g.drawString("���ϴ� ���񽺸� �����ϼ���.", logo_width / 5 * 4 - 22, logo_height / 5 * 2 + 17);

	}

	// ��ư ���� ���� �Լ�
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Restaurant) {
			Main_Frame.search_restaurant_page.List_Search();
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "15");
			ReadMe = false;
		}

		if (e.getSource() == Menu) {
			Main_Frame.search_menu_page.List_Search();
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "14");
			ReadMe = false;
		}

		if (e.getSource() == Reservation) {
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "19");
			ReadMe = false;
		}

		if (e.getSource() == HOME) {
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		}

	}

}