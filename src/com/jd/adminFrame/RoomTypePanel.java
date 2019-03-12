package com.jd.adminFrame;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.directory.ModificationItem;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.jd.login.LoginMainClass;
import com.jd.sql.Sunsql;

import frameUtil.MyTableModel;


//�������͹������
public class RoomTypePanel extends JPanel implements MouseListener{
	JPanel northPanel,centerPanel;
	JTextField searchField;
	JButton searchButton,addRoomType;
	Color color;
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel tableModel;
	JPopupMenu popupMenu;
	JMenuItem delMenuItem;
	JFrame frame;
	public RoomTypePanel(JFrame frame){
		this.frame = frame;
		setLayout(new BorderLayout());
		color = new Color(33,130,76);
		northPanel = new JPanel();
		centerPanel = new JPanel();
		searchField = new JTextField(15);
		searchButton = new JButton("����");
		addRoomType = new JButton("��ӷ�������");
		
		northPanel.setLayout(null);
		northPanel.setPreferredSize(new Dimension(650, 40));
		centerPanel.setLayout(new BorderLayout());
		
		//�������
		setCom();
		tableModel = new MyTableModel();
		tableModel.addColumn("���");
		tableModel.addColumn("�������ͱ��");
		tableModel.addColumn("��������");
		tableModel.addColumn("�۸�");
		//Ĭ����ʾȫ����¼
		setTable("select * from roomtype");
		
		table = new JTable(tableModel);
		//���ñ���и�
		table.setRowHeight(28);
		//���ñ�ͷ�߶�
		table.getTableHeader().setPreferredSize(new Dimension(1150, 35));
		//���ñ��ѡ
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//��ͷ�����ƶ�
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane = new JScrollPane(table);
		
		//���ñ����������־�����ʾ
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
		
		//�Ҽ������˵�
		popupMenu = new JPopupMenu();
		delMenuItem = new JMenuItem("ɾ��");
		popupMenu.add(delMenuItem);
		
		
		northPanel.add(searchField);
		northPanel.add(searchButton);
		northPanel.add(addRoomType);
		centerPanel.add(scrollPane,BorderLayout.CENTER);
		add(northPanel,BorderLayout.NORTH);
		add(centerPanel,BorderLayout.CENTER);
		
		/*
		 * �¼�
		 */
		//��ť����¼�
		searchButton.addMouseListener(this);
		addRoomType.addMouseListener(this);
		//����������¼�
		searchField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO �Զ����ɵķ������
				if(searchField.getText().trim().equals("�������ͱ��/��������")){
					searchField.setText("");
				}
				
			}
		});
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO �Զ����ɵķ������
				super.keyPressed(e);
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					searchRoomType();
				}
			}
			
		});
		
		//������������Ҽ��¼�
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO �Զ����ɵķ������
				super.mouseClicked(e);
				if(e.getButton() == MouseEvent.BUTTON3){
					popupMenu.show(table, e.getX(), e.getY());
				}
			}
		});
		//ɾ����������
		delMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				int row = table.getSelectedRow();
				String string = null;
				if(row >= 0){
					string = (String) table.getValueAt(row, 1);
					int value = JOptionPane.showConfirmDialog(frame, "��ʾ", "�Ƿ�ȷ��ɾ��",JOptionPane.YES_NO_OPTION);
					if(value == 0){
						tableModel.removeRow(row);
//						ִ�ж����ݿ����ɾ������
						String sql = "delete from roomtype where rtypeid='" + Integer.parseInt(string) + "'";
						Sunsql.upData(sql);
						sql = "delete from room where rtypeid='" + String .valueOf(row+1) + "'";
						Sunsql.upData(sql);
						//�����±�����
						int rowCount = tableModel.getRowCount();
						for(int i = 0; i < rowCount; i++){
							tableModel.setValueAt(i+1, i, 0);
						}
					}
				}
			}
		});
	}
	
	//ʵ�����ݿ���Ҳ��������ѽ������������Jtable��
	public void setTable(String sql){
		clean();		 //������ı��
		Vector<String> rowData;
		Connection connection = Sunsql.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			int i = 1;
			while(resultSet.next()){
				rowData = new Vector<>();
				rowData.add(String.valueOf(i));
				rowData.add(resultSet.getString(1));
				rowData.add(resultSet.getString(2));
				rowData.add(resultSet.getString(3));
				tableModel.addRow(rowData);
				i++;
			}
			
			resultSet.close();// �رս��������
			statement.close();// �ر�����״̬����
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}
	//������ݱ�
	public void clean(){
		int row = tableModel.getRowCount()-1;
		while(row >= 0){
			tableModel.removeRow(row);
			row--;
		}
	}
	
	//�������
	public void setCom(){
		searchField.setText("�������ͱ��/��������");
		searchField.setBounds(40, 5, 225, 30);
		searchButton.setBounds(270, 3, 100, 34);
		addRoomType.setBounds(500, 3, 150, 34);
		searchButton.setFocusPainted(false);
		addRoomType.setFocusPainted(false);
		searchButton.setBackground(color);
		addRoomType.setBackground(color);
		searchField.setFont(new Font("����", Font.BOLD, 16));
		searchButton.setFont(new Font("����", Font.BOLD, 18));
		addRoomType.setFont(new Font("����", Font.BOLD, 18));
		searchButton.setForeground(Color.white);
		addRoomType.setForeground(Color.white);
		searchButton.setBorderPainted(false);
		addRoomType.setBorderPainted(false);
		
	}
	//��������
	public void searchRoomType() {
		// TODO �Զ����ɵķ������
		String content = searchField.getText().trim();
		if(!content.equals("") && !content.equals("�������ͱ��/��������")){
			String sql = "select * from roomtype where (rtypeid like '%" 
					+ content + "%' or roomtype like '%" + content + "%')";
			setTable(sql);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JButton button = (JButton)e.getSource();
		if(button.getText().equals("����")){
			searchRoomType();
		}else if(button.getText().equals("��ӷ�������")){
			new AddRoomTypeJDialog(frame, "��ӷ�������", true, tableModel.getRowCount());
			//ˢ�±��
			setTable("select * from roomtype");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JButton button = (JButton)e.getSource();
		button.setBackground(new Color(102,204,153));
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JButton button = (JButton)e.getSource();
		button.setBackground(color);
	}
	
	
}
