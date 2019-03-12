package com.jd.userFrame;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.jd.login.LoginMainClass;
import com.jd.sql.Sunsql;

import frameUtil.MyTableModel;

//�������
public class OrderPanel extends JPanel{
	JPanel northPanel,centerPanel;
	JButton allOrderButton,searchButton,delButton;
	JComboBox<String> comboBox;
	JTextField seachTextField;
	JTable table;
	DefaultTableModel model;
	Font font;
	public OrderPanel(JFrame frame){
		setName("����");
		setLayout(new BorderLayout());
		font = new Font("����", Font.BOLD, 20);
		/*
		 * �������
		 * 
		 */
		northPanel = new JPanel();
		northPanel.setLayout(null);
		northPanel.setBackground(new Color(44,62,80));
		northPanel.setPreferredSize(new Dimension(1150, 70));
		
		allOrderButton = new JButton("ȫ������");
		comboBox = new JComboBox<>();
		seachTextField = new JTextField("������/�����");
		searchButton = new JButton("����");
		delButton = new JButton("ɾ��");
		
		//�������
		setCom();
		
		northPanel.add(allOrderButton);
		northPanel.add(comboBox);
		northPanel.add(seachTextField);
		northPanel.add(searchButton);
		northPanel.add(delButton);
		
		
		/*
		 * �м���ʾ�����������
		 */
		centerPanel = new JPanel();
		table = new JTable();
		model = new MyTableModel();
		model.addColumn("��ź�");
		model.addColumn("������");
		model.addColumn("������");
		model.addColumn("����");
		model.addColumn("��������");
		model.addColumn("��סʱ��");
		model.addColumn("���ʱ��");
		model.addColumn("�۸�");
		model.addColumn("��������");
		model.addColumn("״̬");
		//Ĭ����ʾȫ������
		setTable("select * from orderNum where userid='" + LoginMainClass.USER + "'");
		
		
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(model);
		
		
		//���ñ���С
		table.setRowHeight(28);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//�����п�
		table.getColumnModel().getColumn(0).setPreferredWidth(5);
		table.getColumnModel().getColumn(3).setPreferredWidth(5);
		table.getColumnModel().getColumn(7).setPreferredWidth(20);
		table.getColumnModel().getColumn(9).setPreferredWidth(20);
		//���ñ����������־�����ʾ
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		/*
		 * �¼�����
		 */
		//ȫ��������ť
		allOrderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				setTable("select * from orderNum where userid='" + LoginMainClass.USER + "'");
				allOrderButton.setBackground(new Color(211,84,0));
				comboBox.setBackground(Color.white);
				
				seachTextField.setText("������/�����");
			}
		});
		//������ť
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if(!seachTextField.getText().trim().equals("") && !seachTextField.getText().trim().equals("������/�����")){
					searchOrder();
				}
			}
		});
		//�������¼�
		seachTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO �Զ����ɵķ������
				if(seachTextField.getText().trim().equals("������/�����")){
					seachTextField.setText("");
				}
			}
		});
		seachTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO �Զ����ɵķ������
				super.keyPressed(e);
				if(!seachTextField.getText().trim().equals("") 
						&& !seachTextField.getText().trim().equals("������/�����") 
						&& e.getKeyCode() == KeyEvent.VK_ENTER){
					searchOrder();
				}
			}
		});
		//ɾ����ť
		delButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO �Զ����ɵķ������
				int row = table.getSelectedRow();
				String orderNum = null;
				if(row >= 0){
					orderNum = (String) table.getValueAt(row, 1);
					int value = JOptionPane.showConfirmDialog(frame, "�Ƿ�ɾ������"+orderNum, "��ʾ", JOptionPane.YES_NO_CANCEL_OPTION);
					if(value == 0){
						model.removeRow(row);
						//ִ�ж����ݿ����ɾ����¼����
						String sql = "delete from orderNum where soleid='" + orderNum + "'";
						Sunsql.upData(sql);
					}
				}
				row = table.getRowCount();
				for(int i = 0; i < row; i++){
					model.setValueAt(i+1, i, 0);
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO �Զ����ɵķ������
				delButton.setBackground(Color.RED);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO �Զ����ɵķ������
				delButton.setBackground(Color.white);
			}
			
		});
		//ʱ�������б�
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				comboBox.setBackground(new Color(211,84,0));
				allOrderButton.setBackground(Color.white);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.getInstance();
				
				int choice = comboBox.getSelectedIndex();
				String sql = null,date = null;
				switch (choice) {
				
					case 0:							//�������µĶ���
						calendar.add(Calendar.MONTH, -3);
						date = format.format(calendar.getTime());
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and soledate>='" + date + "'";
						break;
					case 1:							//�����ڵĶ���
						date = String.valueOf(calendar.get(Calendar.YEAR));
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and YEAR(soledate)='" + date + "'";
						break;
					case 2:							//ǰ��һ��Ķ���
						date = String.valueOf(calendar.get(Calendar.YEAR)-1);
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and YEAR(soledate)='" + date + "'";
						break;
					case 3:							//ǰ������Ķ���
						date = String.valueOf(calendar.get(Calendar.YEAR)-2);
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and YEAR(soledate)='" + date + "'";
						break;
					case 4:							//������ǰ��ȫ������	
						date = String.valueOf(calendar.get(Calendar.YEAR)-2);
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and YEAR(soledate)<'" + date + "'";
						break;
					default:
						break;
				}
				setTable(sql);
				seachTextField.setText("������/�����");
			}
		});
		centerPanel.add(scrollPane);
		add(northPanel,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
	}
	//��������
	public void searchOrder(){
		String content = seachTextField.getText().trim();
		if(!content.equals("") && !content.equals("������/�����")){
			String sql = "select * from orderNum where (soleid like '%" 
					+ content + "%' or rid like '%" 
					+ content + "%') and userid='" + LoginMainClass.USER + "'";
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
	//�������
	public void setCom(){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		comboBox.addItem("�������µĶ���");
		comboBox.addItem("�����ڵĶ���");
		comboBox.addItem(String.valueOf(year-1) + "�궩��");
		comboBox.addItem(String.valueOf(year-2) + "�궩��");
		comboBox.addItem(String.valueOf(year-2) + "����ǰ�Ķ���");
		
		allOrderButton.setBounds(10, 20, 150, 30);
		comboBox.setBounds(180, 20, 200, 30);
		seachTextField.setBounds(550, 20, 200, 30);
		searchButton.setBounds(750, 20, 80, 30);
		delButton.setBounds(900, 20, 80, 30);
		
		allOrderButton.setFont(font);
		comboBox.setFont(font);
		seachTextField.setFont(font);
		searchButton.setFont(font);
		delButton.setFont(font);
		
		allOrderButton.setFocusPainted(false);
		searchButton.setFocusPainted(false);
		delButton.setFocusPainted(false);
		
		searchButton.setBackground(Color.WHITE);
		delButton.setBackground(Color.WHITE);
		allOrderButton.setBackground(new Color(211,84,0));
	}
}
