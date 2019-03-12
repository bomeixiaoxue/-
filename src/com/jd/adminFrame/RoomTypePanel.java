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


//房间类型管理面板
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
		searchButton = new JButton("搜索");
		addRoomType = new JButton("添加房间类型");
		
		northPanel.setLayout(null);
		northPanel.setPreferredSize(new Dimension(650, 40));
		centerPanel.setLayout(new BorderLayout());
		
		//设置组件
		setCom();
		tableModel = new MyTableModel();
		tableModel.addColumn("序号");
		tableModel.addColumn("房间类型编号");
		tableModel.addColumn("房间类型");
		tableModel.addColumn("价格");
		//默认显示全部记录
		setTable("select * from roomtype");
		
		table = new JTable(tableModel);
		//设置表格行高
		table.setRowHeight(28);
		//设置表头高度
		table.getTableHeader().setPreferredSize(new Dimension(1150, 35));
		//设置表格单选
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//表头不能移动
		table.getTableHeader().setReorderingAllowed(false);
		scrollPane = new JScrollPane(table);
		
		//设置表格里面的文字居中显示
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
		
		//右键弹出菜单
		popupMenu = new JPopupMenu();
		delMenuItem = new JMenuItem("删除");
		popupMenu.add(delMenuItem);
		
		
		northPanel.add(searchField);
		northPanel.add(searchButton);
		northPanel.add(addRoomType);
		centerPanel.add(scrollPane,BorderLayout.CENTER);
		add(northPanel,BorderLayout.NORTH);
		add(centerPanel,BorderLayout.CENTER);
		
		/*
		 * 事件
		 */
		//按钮添加事件
		searchButton.addMouseListener(this);
		addRoomType.addMouseListener(this);
		//搜索框添加事件
		searchField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根
				if(searchField.getText().trim().equals("房间类型编号/房间类型")){
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
					searchRoomType();
				}
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
		//删除房间类型
		delMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int row = table.getSelectedRow();
				String string = null;
				if(row >= 0){
					string = (String) table.getValueAt(row, 1);
					int value = JOptionPane.showConfirmDialog(frame, "提示", "是否确认删除",JOptionPane.YES_NO_OPTION);
					if(value == 0){
						tableModel.removeRow(row);
//						执行对数据库进行删除操作
						String sql = "delete from roomtype where rtypeid='" + Integer.parseInt(string) + "'";
						Sunsql.upData(sql);
						sql = "delete from room where rtypeid='" + String .valueOf(row+1) + "'";
						Sunsql.upData(sql);
						//设置下表格序号
						int rowCount = tableModel.getRowCount();
						for(int i = 0; i < rowCount; i++){
							tableModel.setValueAt(i+1, i, 0);
						}
					}
				}
			}
		});
	}
	
	//实现数据库查找操作，并把结果集数据填入Jtable里
	public void setTable(String sql){
		clean();		 //清除面板的表格
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
			
			resultSet.close();// 关闭结果集对象
			statement.close();// 关闭连接状态对象
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	//清空数据表
	public void clean(){
		int row = tableModel.getRowCount()-1;
		while(row >= 0){
			tableModel.removeRow(row);
			row--;
		}
	}
	
	//设置组件
	public void setCom(){
		searchField.setText("房间类型编号/房间类型");
		searchField.setBounds(40, 5, 225, 30);
		searchButton.setBounds(270, 3, 100, 34);
		addRoomType.setBounds(500, 3, 150, 34);
		searchButton.setFocusPainted(false);
		addRoomType.setFocusPainted(false);
		searchButton.setBackground(color);
		addRoomType.setBackground(color);
		searchField.setFont(new Font("宋体", Font.BOLD, 16));
		searchButton.setFont(new Font("宋体", Font.BOLD, 18));
		addRoomType.setFont(new Font("宋体", Font.BOLD, 18));
		searchButton.setForeground(Color.white);
		addRoomType.setForeground(Color.white);
		searchButton.setBorderPainted(false);
		addRoomType.setBorderPainted(false);
		
	}
	//搜索房间
	public void searchRoomType() {
		// TODO 自动生成的方法存根
		String content = searchField.getText().trim();
		if(!content.equals("") && !content.equals("房间类型编号/房间类型")){
			String sql = "select * from roomtype where (rtypeid like '%" 
					+ content + "%' or roomtype like '%" + content + "%')";
			setTable(sql);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton)e.getSource();
		if(button.getText().equals("搜索")){
			searchRoomType();
		}else if(button.getText().equals("添加房间类型")){
			new AddRoomTypeJDialog(frame, "添加房间类型", true, tableModel.getRowCount());
			//刷新表格
			setTable("select * from roomtype");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton)e.getSource();
		button.setBackground(new Color(102,204,153));
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton)e.getSource();
		button.setBackground(color);
	}
	
	
}
