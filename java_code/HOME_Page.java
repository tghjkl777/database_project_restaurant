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
 * HOMEȭ���� �����ϴ� JPanel.
 */
public class HOME_Page extends JPanel implements ActionListener {

	// �ʿ� ������, ���빰, ���� ����
	ImageIcon logo_icon = new ImageIcon("images/logo.jpg");
	ImageIcon next_icon = new ImageIcon("images/NextUI.jpg");
	ImageIcon next = new ImageIcon("images/Next.jpg");
	ImageIcon readme = new ImageIcon("images/README.jpg");
	ImageIcon readme_home = new ImageIcon("images/README_HOME.jpg");
	ImageIcon readme_next = new ImageIcon("images/README_NEXT.jpg");
	ImageIcon readme_mouse = new ImageIcon("images/README_MOUSE.jpg");
	Image logo_img = logo_icon.getImage();
	Image next_img = next_icon.getImage();
	Image readme_home_img = readme_home.getImage();
	Image readme_next_img = readme_next.getImage();
	Image readme_mouse_img = readme_mouse.getImage();
	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;
	int next_width = next_img.getWidth(this);
	int next_height = next_img.getHeight(this);
	int readme_text_width = readme_home_img.getWidth(this);
	int readme_text_height = readme_home_img.getHeight(this);
	int readme_mouse_width = readme_mouse_img.getWidth(this);
	int readme_mouse_height = readme_mouse_img.getHeight(this);
	JButton HOME = new JButton(logo_icon);
	JButton NEXT = new JButton(next);
	JButton README = new JButton(readme);
	boolean ColorChange = true;
	boolean ReadMe = false;

	/** 
	 * HOME_Page JPanel ����.
	 * �Ͼ� ��濡 ���� ��ġ�� ���� ��ư�� �÷ȴ�.
	 * Ȩ ��ư, ���� �������� ���� ��ư, ���� ��ư�� �ִ�.
	 * */
	HOME_Page() {

		this.setBackground(Color.white); // �Ͼ� ���
		this.setLayout(null); // ���� ��ġ�� ��ġ

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
		// ���� ��ư ����
		README.setBounds(436, 30, 30, 30);
		README.setBackground(Color.WHITE);
		README.setToolTipText("���� Ȯ��");
		README.addActionListener(this);

		this.add(HOME);
		this.add(NEXT);
		this.add(README);

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
		if (ReadMe == true) {
			g.drawImage(readme_home_img, 90, 40, readme_text_width, readme_text_height, this);
			g.drawImage(readme_next_img, 65, getHeight() - 48, readme_text_width, readme_text_height, this);
			g.drawImage(readme_mouse_img, 30, 300, readme_mouse_width, readme_mouse_height, this);
		}
		if (ColorChange == true)
			g.setColor(new Color(243, 128, 137));
		else
			g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 35));
		g.drawString("Ewha DB team12", logo_width / 2 + 30, 280);

		// �� ���� �κ�
		if (ColorChange == true)
			g.setColor(new Color(0, 70, 42));
		else
			g.setColor(new Color(243, 128, 137));
		g.drawString("Ewha DB", logo_width / 2 + 30, 280);

	}

	/**
	 * ��ư ���� ���� �Լ�.
	 * + NEXT��ư�� ������ ���� ������(Select_Mode_page)�� �̵��Ѵ�.
	 * + HOME��ư�� ������ ��� ������ ���� ���� �ڹٲ��.
	 * + README��ư�� ������ ������ ��Ÿ����.
	 * */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == NEXT) {
			Main_Frame.R_card.next(Main_Frame.R_frame.getContentPane());
			ReadMe = false;
		}

		if (e.getSource() == HOME) {
			if (ColorChange == true)
				ColorChange = false;
			else
				ColorChange = true;
			repaint();

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