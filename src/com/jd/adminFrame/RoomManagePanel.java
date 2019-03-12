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

import com.jd.sql.Sunsql;

import frameUtil.MyTableModel;

//����������
public class RoomManagePanel extends JPanel implements MouseListener{
	JPanel northPanel,centerPanel;
	JTextField searchField;
	JButton searchButton,addRoom;
	Color color;
	JScrollPane scrollPane;
	JTable table;
	DefaultTableModel tableModel;
	JPopupMenu popupMenu;
	JMenuItem modifyMenuItem,delMenuItem;
	JFrame frame;
	public RoomManagePanel(JFrame f){
		frame = f;
		
		setLayout(new BorderLayout());
		color = new Color(33,130,76);
		northPanel = new JPanel();
		centerPanel = new JPanel();
		searchField = new JTextField(15);
		searchButton = new JButton("����");
		addRoom = new JButton("��ӷ���");
		
		northPanel.setLayout(null);
		centerPanel.setLayout(new BorderLayout());
		northPanel.setPreferredSize(new Dimension(650, 40));
		//�������
		setCom();
		

		tableModel = new MyTableModel();
		tableModel.addColumn("���");
		tableModel.addColumn("�����");
		tableModel.addColumn("�۸�");
		tableModel.addColumn("λ��");
		tableModel.addColumn("����");
		tableModel.addColumn("״̬");
		tableModel.addColumn("����绰");
		//Ĭ����ʾȫ����¼
		setTable("select room.*,roomtype.roomtype from room,roomtype "
				+ "where room.rtypeid=roomtype.rtypeid");
		
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
		modifyMenuItem = new JMenuItem("�޸�");
		delMenuItem = new JMenuItem("ɾ��");
		popupMenu.add(modifyMenuItem);
		popupMenu.add(delMenuItem);
		
		
		
		
		//������ť�¼�
		searchButton.addMouseListener(this);
		//��ӷ��䰴ť�¼�
		addRoom.addMouseListener(this);
		//����������¼�
		searchField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO �Զ����ɵķ������
				if(searchField.getText().trim().equals("�����/��������")){
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
					searchRoom();
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
		//�޸ķ����¼�
		modifyMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				int row = table.getSelectedRow();
				int column = tableModel.getColumnCount();
				if(row >= 0){
					Vector<String> vector = new Vector<>();
					for(int i = 1; i < column; i++){
						vector.add((String) tableModel.getValueAt(row, i));
					}
					new ModifyRoomJDialog(frame,"�޸ķ�����Ϣ",true,vector);
					setTable("select room.*,roomtype.roomtype from room,roomtype "
							+ "where room.rtypeid=roomtype.rtypeid");
				}
				
			}
		});
		
		//ɾ�������¼�
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
						//ִ�ж����ݿ����ɾ������
						String sql = "delete from room where rid='" + string + "'";
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
		
		
		northPanel.add(searchField);
		northPanel.add(searchButton);
		northPanel.add(addRoom);
		centerPanel.add(scrollPane,BorderLayout.CENTER);
		add(northPanel,BorderLayout.NORTH);
		add(centerPanel,BorderLayout.CENTER);
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
				rowData.add(resultSet.getString(7));
				rowData.add(resultSet.getString(5));
				rowData.add(resultSet.getString(6));
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
		searchField.setBounds(40, 5, 225, 30);
		searchButton.setBounds(270, 3, 100, 34);
		addRoom.setBounds(500, 3, 150, 34);
		searchButton.setFocusPainted(false);
		addRoom.setFocusPainted(false);
		searchButton.setBackground(color);
		addRoom.setBackground(color);
		searchField.setFont(new Font("����", Font.BOLD, 18));
		searchButton.setFont(new Font("����", Font.BOLD, 18));
		addRoom.setFont(new Font("����", Font.BOLD, 18));
		searchButton.setForeground(Color.white);
		addRoom.setForeground(Color.white);
		searchButton.setBorderPainted(false);
		addRoom.setBorderPainted(false);
	}
	//��������
	public void searchRoom() {
		// TODO �Զ����ɵķ������
		String content = searchField.getText().trim();
		if(!content.equals("") && !content.equals("�����/��������")){
			String sql = "select room.*,roomtype.roomtype from room,roomtype "
					+ "where room.rtypeid=roomtype.rtypeid"
					+ " and (rid like '%" + content +"%' "
					+ " or roomtype.roomtype like '%" + content +"%')";
			setTable(sql);
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JButton button = (JButton)e.getSource();
		if(button.getText().equals("����")){
			searchRoom();
		}else if(button.getText().equals("��ӷ���")){
			new AddRoomJDialog(frame,"��ӷ���",true);
			//ˢ�±��
			setTable("select room.*,roomtype.roomtype from room,roomtype "
					+ "where room.rtypeid=roomtype.rtypeid");
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
