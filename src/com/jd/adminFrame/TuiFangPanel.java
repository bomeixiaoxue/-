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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
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

//�˷����
public class TuiFangPanel extends JPanel{
	JPanel northPanel,centerPanel;
	JTextField searchField;
	JButton searchButton;
	Color color;
	JTable table;
	MyTableModel model;
	JPopupMenu popupMenu;
	JMenuItem tuiFangMenuItem;
	public TuiFangPanel(JFrame frame){
		
		setLayout(new BorderLayout());
		northPanel = new JPanel();
		centerPanel = new JPanel();
		color = new Color(33,130,76);
		
		northPanel.setLayout(null);
		centerPanel.setLayout(new BorderLayout());
		northPanel.setPreferredSize(new Dimension(650, 40));
		
		searchField = new JTextField(15);
		searchButton = new JButton("����");
		searchField.setText("������/�û��˺�");
		searchField.setBounds(40, 5, 225, 30);
		searchField.setFont(new Font("����", Font.BOLD, 16));
		searchButton.setBounds(270, 3, 100, 34);
		searchButton.setBackground(color);
		searchButton.setForeground(Color.white);
		searchButton.setBorderPainted(false);
		searchButton.setFocusable(false);
		searchButton.setFont(new Font("����", Font.BOLD, 18));
		
		
		table = new JTable();
		model = new MyTableModel();
		model.addColumn("��ź�");
		model.addColumn("������");
		model.addColumn("�û��˺�");
		model.addColumn("����");
		model.addColumn("������");
		model.addColumn("����");
		model.addColumn("��������");
		model.addColumn("��סʱ��");
		model.addColumn("���ʱ��");
		model.addColumn("�۸�");
		model.addColumn("��������");
		model.addColumn("״̬");
		//Ĭ����ʾȫ������
		setTable("select * from orderNum order by soleid desc");
		
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(model);
		
		//���ñ���С
		table.setRowHeight(28);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//�����п�
		table.getColumnModel().getColumn(0).setPreferredWidth(5);
		table.getColumnModel().getColumn(2).setPreferredWidth(5);
		table.getColumnModel().getColumn(3).setPreferredWidth(5);
		table.getColumnModel().getColumn(5).setPreferredWidth(20);
		table.getColumnModel().getColumn(9).setPreferredWidth(20);
		table.getColumnModel().getColumn(11).setPreferredWidth(10);
		//���ñ����������־�����ʾ
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		//�Ҽ������˵�
		popupMenu = new JPopupMenu();
		tuiFangMenuItem = new JMenuItem("�˷�");
		popupMenu.add(tuiFangMenuItem);
		
		//����������¼�
		searchField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO �Զ����ɵķ������
				if(searchField.getText().trim().equals("������/�û��˺�")){
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
					searchOrder();
				}
			}
			
		});
		//������ť�¼�
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				searchOrder();
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
		//�˷��¼�
		tuiFangMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				int row = table.getSelectedRow();
				if(row >= 0){
					String sql = "update orderNum set state='�������' where soleid='"
							+ table.getValueAt(row, 1)+"'";
					Sunsql.upData(sql);
					//ˢ�±��
					setTable("select * from orderNum order by soleid desc");
				}
			}
		});
		northPanel.add(searchField);
		northPanel.add(searchButton);
		centerPanel.add(scrollPane,BorderLayout.CENTER);
		add(northPanel,BorderLayout.NORTH);
		add(centerPanel,BorderLayout.CENTER);
		
	}
	//��������
	public void searchOrder(){
		String content = searchField.getText().trim();
		if(!content.equals("") && !content.equals("������/�û��˺�")){
			String sql = "select * from orderNum where (soleid like '%"
					+ content +"%' or userid like '%" + content + "%')";
			setTable(sql);
		}
	}
	//ʵ�����ݿ���Ҳ��������ѽ������������Jtable��
		public void setTable(String sql){
			clean();		 //������ı��
			Vector<String> rowData;
			Connection connection = Sunsql.getConnection();
			int i = 1;
			try {
				Statement  statement = connection.createStatement();
				ResultSet result = statement.executeQuery(sql);
				while(result.next()){
					rowData = new Vector<>();
					rowData.add(String.valueOf(i));
					rowData.add(result.getString(1));
					rowData.add(result.getString(2));
					rowData.add(result.getString(3));
					rowData.add(result.getString(4));
					rowData.add(result.getString(5));
					rowData.add(result.getString(6));
					rowData.add(result.getString(7));
					rowData.add(result.getString(8));
					rowData.add(result.getString(9));
					rowData.add(result.getString(10));
					rowData.add(result.getString(11));
					model.addRow(rowData);
					i++;
				}
				result.close();// �رս��������
				statement.close();// �ر�����״̬����
			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			Sunsql.closeConnection();
			
		}
		//������ݱ�
		public void clean(){
			int row = model.getRowCount()-1;
			while(row >= 0){
				model.removeRow(row);
				row--;
			}
		}
}
