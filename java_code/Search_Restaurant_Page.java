package team12;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.event.*;

/**
 * ������� �˻� ������
 * @author �ڼ���
 *
 */
public class Search_Restaurant_Page extends JPanel implements ActionListener {

	// �ʿ� ������, ���빰, ���� ����
	ImageIcon logo_icon = new ImageIcon("images/logo.jpg");
	ImageIcon next_icon = new ImageIcon("images/NextUI.jpg");
	Image logo_img = logo_icon.getImage();
	Image next_img = next_icon.getImage();
	int logo_width = logo_img.getWidth(this) * 2;
	int logo_height = logo_img.getHeight(this) * 2;
	int next_width = next_img.getWidth(this);
	int next_height = next_img.getHeight(this);
	JButton HOME = new JButton(logo_icon);
	JButton Enter = new JButton();
	JButton Select = new JButton();
	JTextField Search = new JTextField();
	String search_restaurant_name;
	static String select_R;
	// DB���� �ҷ����� ���� �ʿ交�� ����
	Vector data = new Vector<>(); // ���� ������
	Vector title = new Vector<>(); // ���� Ÿ��Ʋ
	Vector result; // ��ü ��� ������
	DefaultTableModel model=new DefaultTableModel();
	JTable table;
	JScrollPane scrollPane;
	// For Connection.
	Statement stmt = DB_Connection.stmt;
	ResultSet rs;

	int exist_result = 0; // ����� ���� ��츦 ����� ���� ����.

	// ������� �˻� panel ����
	Search_Restaurant_Page() {

		result = List_Search();

		title.add("Restaurant_Name");
		title.add("Restaurant_Type");
		model.setDataVector(result, title);

		table = new JTable(model);
		table.setForeground(new Color(0, 70, 42));
		table.setGridColor(Color.WHITE);
		table.setFont(new Font("���� ���", Font.PLAIN, 15));
		table.setRowHeight(40); // ���̺� �� ���� ����

		Center();// ���̺� ���� ��� ���� ���� ����

		JTableHeader header2 = table.getTableHeader();

		ColumnHeaderToolTips tips = new ColumnHeaderToolTips();

		TableColumn col1 = table.getColumnModel().getColumn(0);
		tips.setToolTip(col1, "������� �̸�");
		TableColumn col2 = table.getColumnModel().getColumn(1);
		tips.setToolTip(col2, "������� Ÿ��");

		header2.addMouseMotionListener(tips);

		header2.setBackground(new Color(224, 247, 239));
		header2.setForeground(new Color(0, 70, 42));
		header2.setFont(new Font("Calibri", Font.PLAIN, 20));

		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBounds(20, logo_width / 2 + 120, 460, 270);

		this.setBackground(Color.white); // �Ͼ� ���
		this.setLayout(null); // ���� ��ġ�� ��ġ

		// �˻��� ����
		Search.setBounds(105, logo_height / 4 * 3, 200, 30);
		Search.setToolTipText("������� �̸��� �Է��ϼ���.");
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

		Select.setBounds(200, logo_height * 3 - 80, next_width / 2, next_height / 8);
		Select.setBackground(Color.WHITE);
		Select.setToolTipText("Select. ����.");
		Select.setText("Select");
		Select.addActionListener(this);

		// panel�� ���빰 ����
		this.add(HOME);
		this.add(Search);
		this.add(Enter);
		this.add(Select);
		this.add(scrollPane);
		
		UIManager UI = new UIManager();
		UI.put("OptionPane.background", Color.WHITE);
		UIManager.put("Panel.background", Color.WHITE);

	}

	/**
	 * ��µǴ� DB�����͵� ���̺� ��� ���ķ� ������ ��
	 */
	public void Center() {
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setCellRenderer(center);
		tcm.getColumn(1).setCellRenderer(center);
	}

	// Panel�� �׸���
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// ��, �Ʒ� �κ�
		g.drawImage(logo_img, 0, 0, logo_width / 2, logo_height / 2, this);
		g.setColor(new Color(0, 70, 42));
		g.setFont(new Font("Calibri", Font.PLAIN, 20));
		g.drawString("Search Restaurant.", logo_width / 2 + 55, logo_height / 4 + 10);
		g.setFont(new Font("���� ���", Font.PLAIN, 15));
		g.drawString("��������� �˻��ϼ���.", logo_width / 5 * 4 + 18, logo_height / 5 * 2 + 10);
		
		//�˻������ ������
		if (exist_result == -1) {
			g.setColor(new Color(243, 128, 137));
			g.setFont(new Font("���� ���", Font.BOLD, 11));
			g.drawString("��� ����. >", logo_width / 2 + 105, logo_height / 4 * 3 + 50);
			g.setFont(new Font("Calibri", Font.BOLD, 13));
			g.drawString("< No results.", logo_width / 2 + 30, logo_height / 4 * 3 + 50);
		} 
		//�� �ۿ�
		else if (exist_result == 0) {
			g.setColor(new Color(243, 128, 137));
			g.setFont(new Font("Calibri", Font.BOLD, 13));
			g.drawString("< Enter Restaurant name. >", logo_width / 2 + 20, logo_height / 4 * 3 + 50);
		}

	}
	// ��ư ���� ���� �Լ�
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == HOME) {
			// ȭ�� �ʱ�ȭ
			Search.setText(null);
			result = List_Search();
			model = new DefaultTableModel();
			model.setDataVector(result, title);
			exist_result = 0;
			Center();
			scrollPane.updateUI();
			repaint();
			// Ȩ ��������
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "0");
		}

		if (e.getSource() == Enter) {
			search_restaurant_name = Search.getText();
			if (search_restaurant_name != null) {
				result = Restaurant_Search(search_restaurant_name);
				model.setDataVector(result, title);
				Center();
				scrollPane.updateUI();
				repaint();
			}
		}
		if (e.getSource() == Select) {
			Vector in = (Vector) data.get(table.getSelectedRow());
			select_R = (String) in.get(0);
			Main_Frame.restaurant_information.getInfo(select_R);
			Main_Frame.R_card.show(Main_Frame.R_frame.getContentPane(), "13");
			Search.setText(null);
			result = List_Search();
			model.setDataVector(result, title);
			Center();
			exist_result = 0;
			scrollPane.updateUI();
			repaint();
		}

	}
	

	/**
	 * ������� �̸� , Ÿ�� ���� ������ ����Ʈ�� �ҷ����� �Լ�
	 * @return Vector restaurant name , type related data 
	 */
	public Vector List_Search() {

		try {
			data.clear();

			rs = stmt.executeQuery("select restaurant_name, restaurant_type from DBCOURSE_Restaurants use index(restaurant_id_index)");

			while (rs.next()) {

				Vector in = new Vector<String>();
				String Name = rs.getString(1);
				String Type = rs.getString(2);

				in.add(Name);
				in.add(Type);

				data.add(in);
			}
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}


	/**
	 * �˻��� ������� �̸� , Ÿ�� ���� ������ ����Ʈ�� �ҷ����� �Լ�
	 * @param name �˻��� ������� �̸�
	 * @return Vector �˻��� restaurant name , type related data 
	 */
	public Vector Restaurant_Search(String name) {

		try {
			data.clear();
			exist_result = -1;

			rs = stmt.executeQuery("select restaurant_name, restaurant_type from DBCOURSE_Restaurants use index(restaurant_id_index)");

			while (rs.next()) {

				Vector in = new Vector<String>();
				String Name = rs.getString(1);
				String Type = rs.getString(2);

				if (Name.equals(name)) {
					in.add(Name);
					in.add(Type);
					data.add(in);
					exist_result = 0;
				}
			}
			
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

}

//table column tooltip ����
class ColumnHeaderToolTips extends MouseMotionAdapter {
	TableColumn curCol;
	Map tips = new HashMap();

	public void setToolTip(TableColumn col, String tooltip) {
		if (tooltip == null) {
			tips.remove(col);
		} else {
			tips.put(col, tooltip);
		}
	}

	public void mouseMoved(MouseEvent evt) {
		JTableHeader header = (JTableHeader) evt.getSource();
		JTable table = header.getTable();
		TableColumnModel colModel = table.getColumnModel();
		int vColIndex = colModel.getColumnIndexAtX(evt.getX());
		TableColumn col = null;
		if (vColIndex >= 0) {
			col = colModel.getColumn(vColIndex);
		}
		if (col != curCol) {
			header.setToolTipText((String) tips.get(col));
			curCol = col;
		}
	}
}