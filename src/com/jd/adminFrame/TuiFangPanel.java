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

//退房面板
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
		searchButton = new JButton("搜索");
		searchField.setText("订单号/用户账号");
		searchField.setBounds(40, 5, 225, 30);
		searchField.setFont(new Font("宋体", Font.BOLD, 16));
		searchButton.setBounds(270, 3, 100, 34);
		searchButton.setBackground(color);
		searchButton.setForeground(Color.white);
		searchButton.setBorderPainted(false);
		searchButton.setFocusable(false);
		searchButton.setFont(new Font("宋体", Font.BOLD, 18));
		
		
		table = new JTable();
		model = new MyTableModel();
		model.addColumn("序号号");
		model.addColumn("订单号");
		model.addColumn("用户账号");
		model.addColumn("姓名");
		model.addColumn("房间编号");
		model.addColumn("数量");
		model.addColumn("房间类型");
		model.addColumn("入住时间");
		model.addColumn("离店时间");
		model.addColumn("价格");
		model.addColumn("订单日期");
		model.addColumn("状态");
		//默认显示全部订单
		setTable("select * from orderNum order by soleid desc");
		
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(model);
		
		//设置表格大小
		table.setRowHeight(28);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//设置列宽
		table.getColumnModel().getColumn(0).setPreferredWidth(5);
		table.getColumnModel().getColumn(2).setPreferredWidth(5);
		table.getColumnModel().getColumn(3).setPreferredWidth(5);
		table.getColumnModel().getColumn(5).setPreferredWidth(20);
		table.getColumnModel().getColumn(9).setPreferredWidth(20);
		table.getColumnModel().getColumn(11).setPreferredWidth(10);
		//设置表格里面的文字居中显示
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		//右键弹出菜单
		popupMenu = new JPopupMenu();
		tuiFangMenuItem = new JMenuItem("退房");
		popupMenu.add(tuiFangMenuItem);
		
		//搜索框添加事件
		searchField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根
				if(searchField.getText().trim().equals("订单号/用户账号")){
					searchField.setText("");
				}
				
			}
		});
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根
				super.keyPressed(e);
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					searchOrder();
				}
			}
			
		});
		//搜索按钮事件
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				searchOrder();
			}
		});
		//给表格添加鼠标右键事件
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				super.mouseClicked(e);
				if(e.getButton() == MouseEvent.BUTTON3){
					popupMenu.show(table, e.getX(), e.getY());
				}
			}
		});
		//退房事件
		tuiFangMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int row = table.getSelectedRow();
				if(row >= 0){
					String sql = "update orderNum set state='结算离店' where soleid='"
							+ table.getValueAt(row, 1)+"'";
					Sunsql.upData(sql);
					//刷新表格
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
	//搜索订单
	public void searchOrder(){
		String content = searchField.getText().trim();
		if(!content.equals("") && !content.equals("订单号/用户账号")){
			String sql = "select * from orderNum where (soleid like '%"
					+ content +"%' or userid like '%" + content + "%')";
			setTable(sql);
		}
	}
	//实现数据库查找操作，并把结果集数据填入Jtable里
		public void setTable(String sql){
			clean();		 //清除面板的表格
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
				result.close();// 关闭结果集对象
				statement.close();// 关闭连接状态对象
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			Sunsql.closeConnection();
			
		}
		//清空数据表
		public void clean(){
			int row = model.getRowCount()-1;
			while(row >= 0){
				model.removeRow(row);
				row--;
			}
		}
}
