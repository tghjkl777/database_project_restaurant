package team12;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * @author ������.
 * Restaurant_List_page�� �����ϴ� JPanel.
 * ������� ����Ʈ���� ȭ�鿡 �����ְ� �̿� ���� ���� �߰� ������ ���ش�.
 */
public class Restaurant_List_page extends JPanel implements ActionListener {
	static String Name;
	// ������ ���� ����
	ImageIcon logo_icon = Main_Frame.home_page.logo_icon;
	Image logo_img = logo_icon.getImage();
	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;

	// ���̺� �ʿ� ��� ����
	DefaultTableModel model;
	JTable table;
	JScrollPane scrollPane;

	// DB���� �ҷ����� ���� �ʿ交�� ����
	Vector data; // ���� ������
	Vector title; // ���� Ÿ��Ʋ
	Vector result; // ��ü ��� ������

	// DB����, ���� ���� �ʿ� ����
	Connection conn;
	Statement stmt; // ����Ÿ ������ �ٷ� ��ȸ�ϴ� ����
	PreparedStatement pstRemove; // ���� ���� �����ϴ� ��ü����
	PreparedStatement pstUpdate;// ���� ���� �����ϴ�
	ResultSet rs;
	int exist_result = 0;

	// �ʿ� ��ư ����
	JButton add_B, remove_B, fix_B;
	JButton HOME = new JButton(logo_icon);
	
	// ���� ��ư �� R_ID���� �Ѿ���� ������ select_R_ID�� ��Ƶα�.
	static int select_R_ID;
	
	
	/** 
	 * Restaurant_List_page JPanel ����.
	 * �Ͼ� ��濡 ���� ��ġ�� ���� ��ư�� �÷ȴ�.
	 * Ȩ ��ư, �߰� ����  ���� ��ư, ������� ����Ʈ�� �ߴ� ���̺��� �ִ�.
	 * */
	Restaurant_List_page() {
		// TODO Auto-generated constructor stub

		this.setBackground(Color.white); // �Ͼ� ���
		this.setLayout(null); // ���� ��ġ�� ��ġ

		// Ȩ��ư ����&���̱�
		HOME.setBounds(5, 5, logo_width / 2, logo_height / 2);
		HOME.setBackground(Color.WHITE);
		HOME.setToolTipText("HOME. Ȩ ��������.");
		HOME.setBorderPainted(false); // ��ư ��輱 ���ֱ�
		HOME.addActionListener(this);
		this.add(HOME);

		data = new Vector<>();
		title = new Vector<>();

		// ������ Ÿ��Ʋ �̸� ����
		title.add("Restaurant_ID");
		title.add("Restaurant_Name");

		model = new DefaultTableModel();
		result = List_Search();
		model.setDataVector(result, title);

		// ���̺� ����&���̱�
		table = new JTable(model);
		table.setForeground(new Color(0,70,42));
		table.setGridColor(Color.WHITE);
		table.setFont(new Font("���� ���", Font.PLAIN, 15));
		table.setRowHeight(40);
		
		//�����͵��� �� ����� ���ĵǰ�
		Center();

		// ���̺� Ÿ�̵� ũŰ,����� ����
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(224, 247, 239));
		header.setForeground(new Color(0, 70, 42));
		header.setFont(new Font("Calibri", Font.PLAIN, 18));

		// ��ũ�� �ǿ� ���̱�
		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBounds(20, logo_width / 2 + 30, 460, 350);
		this.add(scrollPane);

		// ��ư �̸� ����
		remove_B = new JButton("����");
		fix_B = new JButton("����");

		// �߰� ��ư ����&���̱�
		add_B = new JButton("�߰�");
		add_B.setFont(new Font("���� ���", Font.PLAIN, 15));
		add_B.setBackground(new Color(0, 70, 42));
		add_B.setForeground(Color.WHITE);
		add_B.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add_B.setBounds(200, logo_width / 2 + 405, 80, 30);
		this.add(add_B);

		// ���� ��ư ����&���̱�
		remove_B.setFont(new Font("���� ���", Font.PLAIN, 15));
		remove_B.setBackground(new Color(0, 70, 42));
		remove_B.setForeground(Color.WHITE);
		remove_B.setCursor(new Cursor(Cursor.HAND_CURSOR));
		remove_B.setBounds(300, logo_width / 2 + 405, 80, 30);
		this.add(remove_B);

		// ���� ��ư ����&���̱�
		fix_B.setFont(new Font("���� ���", Font.PLAIN, 15));
		fix_B.setBackground(new Color(0, 70, 42));
		fix_B.setForeground(Color.WHITE);
		fix_B.setCursor(new Cursor(Cursor.HAND_CURSOR));
		fix_B.setBounds(400, logo_width / 2 + 405, 80, 30);
		this.add(fix_B);

		// OptionPane ������ ����
		UIManager UI = new UIManager();
		UI.put("OptionPane.background", Color.WHITE);
		UIManager.put("Panel.background", Color.WHITE);
		
		// ��ư�� �׼Ǹ����� ����
		add_B.addActionListener(this);
		remove_B.addActionListener(this);
		fix_B.addActionListener(this);
		HOME.addActionListener(this);

	}
	/**
	 * ��µǴ� DB�����͵� ���̺� ��� ���ķ� ������ �ϴ� �Լ�
	 */
	public void Center()
	{
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setCellRenderer(center);
		tcm.getColumn(1).setCellRenderer(center);
	}

	/**
	 * Panel�� �׸���.
	 * �۾��� �׸����� �׸���.
	 * */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Restaurant List", logo_width / 2 + 114, logo_height / 4 - 5);
		g.setFont(new Font("���� ���", Font.PLAIN, 15));
		g.drawString("���� ������� ����Դϴ�.", logo_width / 5 * 4 + 29, logo_height / 5 * 2 - 5);
		g.setFont(new Font("���� ���", Font.PLAIN, 12));
		g.setColor(Color.gray);
		g.drawString("(+������ ���ϸ� ��Ͽ��� ���� �� �ش� ��ư�� �����ּ���.)", logo_width / 2 + 20, logo_height / 5 * 2 + 20);

	}

	
	/**
	 * ��ư ���� ���� �Լ�.
	 * �߰���ư�� ������ ������� �߰��� ���� �������γѾ��.
	 * ���̺��� ��������� �����ϰ� ������ư�� ������  Ȯ��â�� ���� �ش� ��������� �����Ѵ�.
	 * ��������� ���̺꿡�� �����ϰ� ������ư�� ������ �ش� ������� ���� �������� �Ѿ��.
	  * */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		//�߰� ��ư ���
		if (e.getSource() == add_B) {

			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "21");

		}
		//���� ��ư ���
		if (e.getSource() == remove_B) {
			int confirm = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "���� Ȯ��", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION && table.getSelectedRow() != -1) {

				int index = table.getSelectedRow(); // ���̺��� ������ ���� ���� ������ ��
				Vector in = (Vector) data.get(index); // ���õ� ���� ������ ���� �ϳ��� �޾ƿ´�.

				int ID = (int) in.get(0);

				Remove(ID);

				result = List_Search();
				model.setDataVector(result, title);
				
				Center();

			}
			// ���õ� ���̺� row�� ���� �� ���õ� ���� ���ٰ� �˸�.
			else if (confirm == JOptionPane.YES_OPTION && table.getSelectedRow() == -1) {

				JOptionPane.showMessageDialog(null, "���õ� ���� �����ϴ�.");

			}

		}

		// ���� ��ư ���
		if (e.getSource() == fix_B) {

			// ���õ� ���̺� row�� ���� �� ���õ� ���� ���ٰ� �˸�.
			if (table.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(null, "���õ� ���� �����ϴ�.");
			// ���� ��� ���� �������� �Ѿ.
			else {
				int index = table.getSelectedRow(); // ���̺��� ������ ���� ���� ������ ��
				Vector in = (Vector) data.get(index); // ���õ� ���� ������ ���� �ϳ��� �޾ƿ´�.
				select_R_ID = (int) in.get(0);
				Main_Frame.modify_first_page.create_R_ID();
				Main_Frame.modify_first_page.getInfo(select_R_ID);
				Main_Frame.modify_first_page.getHoliday(select_R_ID);
				Main_Frame.modify_first_page.SetText(Main_Frame.modify_first_page.restaurant_info);
				Main_Frame.modify_first_page.SetCheckBox(Main_Frame.modify_first_page.holiday);
				Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "23");
			}
		}

		if (e.getSource() == HOME)
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
	}
	/**
	 *  ������ ����Ʈ�� �ҷ����� �Լ�
	 *  �����ͺ��̽��� ����ִ� ������ ���ͷ� �޾ƿ´�.
	 *  ���ܹ߻� �Ǵ� ���� ���� �� �ѹ� �Ѵ�.
	 */
	Vector List_Search() {

		try {

			data.clear();
			stmt = DB_Connection.stmt;

			rs = stmt.executeQuery("select Restaurant_ID,Restaurant_Name  from DBCOURSE_Restaurants ");

			while (rs.next()) {

				Vector in = new Vector<String>();

				int ID = rs.getInt(1);

				String Name = rs.getString(2);

				in.add(ID);
				in.add(Name);

				data.add(in);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;

	}

	
	/**
	 *  ������ ���� �Լ�
	 *  �ش� ��������� �������ش�. 
	 */
	void Remove(int ID) {

		try {
			conn = DB_Connection.conn;
			conn.setAutoCommit(false);//Ʈ����� ����

			pstRemove = conn.prepareStatement("delete from DBCOURSE_Restaurants where Restaurant_ID = ?");
			pstRemove.setInt(1, ID);
			pstRemove.executeUpdate();

			pstRemove = conn
					.prepareStatement("delete from DBCOURSE_Ingredients where menu_ID between ?*100 and ?*100+10");
			pstRemove.setInt(1, ID);
			pstRemove.setInt(2, ID);
			pstRemove.executeUpdate();
			conn.commit();//�� �������� ������������ commit �ȴ�.

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();// ���� ���н� �ѹ� �ȴ�.
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}


}