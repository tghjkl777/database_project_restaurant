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
 * @author ���ѳ�.
 * ������ ���� ����� ��� �� �ϳ��� �����ϴ� ������.
 */
public class Select_Mode_page extends JPanel implements ActionListener {

	// �ʿ� ������, ���빰, ���� ����
	ImageIcon logo_icon = Main_Frame.home_page.logo_icon;
	ImageIcon readme = Main_Frame.home_page.readme;
	ImageIcon manager_basic = new ImageIcon("images/Manager_basic.jpg");
	ImageIcon manager_on = new ImageIcon("images/Manager_on.jpg");
	ImageIcon user_basic = new ImageIcon("images/User_basic.jpg");
	ImageIcon user_on = new ImageIcon("images/User_on.jpg");
	ImageIcon readme_manager = new ImageIcon("images/MODE_README1.jpg");
	ImageIcon readme_user = new ImageIcon("images/MODE_README2.jpg");
	Image logo_img = logo_icon.getImage();
	Image readme_manager_img = readme_manager.getImage();
	Image readme_user_img = readme_user.getImage();
	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;
	int readme_info_width = readme_manager_img.getWidth(this);
	int readme_info_height = readme_manager_img.getHeight(this);
	JButton HOME = new JButton(logo_icon);
	JButton README = new JButton(readme);
	JButton Manager = new JButton(manager_basic);
	JButton User = new JButton(user_basic);
	boolean ColorChange = true;
	boolean ReadMe = false;
	
	/**
	 * Select_Mode_page JPanel ����.
	 * �Ͼ� ��濡 ���� ��ġ�� ���� ��ư�� �÷ȴ�.
	 * Ȩ ��ư, ���� ��ư, ������ ��ư, ����� ��ư�� �ִ�.
	 */
	Select_Mode_page() {

		this.setBackground(Color.white); // �Ͼ� ���
		this.setLayout(null); // ���� ��ġ�� ��ġ

		// Ȩ ��ư ����
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. Ȩ ��������.");
		HOME.setBorderPainted(false); // ��ư ��輱 ���ֱ�
		HOME.addActionListener(this);
		// ���� ��ư ����
		README.setBounds(436, 30, 30, 30);
		README.setBackground(Color.WHITE);
		README.setToolTipText("���� Ȯ��");
		README.addActionListener(this);
		// ������ ��ư ����
		Manager.setBounds(120, 180, 250, 100);
		Manager.setBackground(Color.WHITE);
		Manager.setToolTipText("������ ���");
		Manager.setRolloverIcon(manager_on);
		Manager.setPressedIcon(manager_on);
		Manager.setBorderPainted(false);
		Manager.addActionListener(this);
		// ����� ��ư ����
		User.setBounds(120, 350, 250, 100);
		User.setBackground(Color.WHITE);
		User.setRolloverIcon(user_on);
		User.setPressedIcon(user_on);
		User.setToolTipText("����� ���");
		User.setBorderPainted(false);
		User.addActionListener(this);

		this.add(HOME);
		this.add(README);
		this.add(Manager);
		this.add(User);

	}

	/**
	 * Panel�� �׸���.
	 * �۾��� �׸����� �׸���.
	 * */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (ReadMe == true) {
			g.drawImage(readme_manager_img, 80, 290, readme_info_width, readme_info_height, this);
			g.drawImage(readme_user_img, 80, 470, readme_info_width, readme_info_height, this);

		}
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 35));
		g.drawString("Select a mode.", logo_width / 2 + 20, logo_height / 4 + 15);
		g.setColor(new Color(243, 128, 137));
		g.setFont(new Font("���� ���", Font.BOLD, 20));
		g.drawString("��带 �����ϼ���.", logo_width / 5 * 4 - 22, logo_height / 5 * 2 + 17);

	}

	/**
	 * ��ư ���� ���� �Լ�.
	 * + Manager��ư�� ������ �����ڸ�忡 �ش��ϴ� ������(Restaurant_List_page)�� �̵��Ѵ�.
	 * + User��ư�� ������ ����ڸ�忡 �ش��ϴ� ������(Select_Mode_User)�� �̵��Ѵ�.
	 * + HOME��ư�� ������ HOME_Page�� �̵��Ѵ�.
	 * + README��ư�� ������ ������ ��Ÿ����.
	 * */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Manager) {
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "20");
			ReadMe = false;
		}

		if (e.getSource() == User) {
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "11");
			ReadMe = false;
		}

		if (e.getSource() == HOME) {
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		}

		if (e.getSource() == README) {
			if (ReadMe == true)
				ReadMe = false;
			else
				ReadMe = true;
			repaint();
		}

	}

}