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

//订单面板
public class OrderPanel extends JPanel{
	JPanel northPanel,centerPanel;
	JButton allOrderButton,searchButton,delButton;
	JComboBox<String> comboBox;
	JTextField seachTextField;
	JTable table;
	DefaultTableModel model;
	Font font;
	public OrderPanel(JFrame frame){
		setName("订单");
		setLayout(new BorderLayout());
		font = new Font("宋体", Font.BOLD, 20);
		/*
		 * 上面面板
		 * 
		 */
		northPanel = new JPanel();
		northPanel.setLayout(null);
		northPanel.setBackground(new Color(44,62,80));
		northPanel.setPreferredSize(new Dimension(1150, 70));
		
		allOrderButton = new JButton("全部订单");
		comboBox = new JComboBox<>();
		seachTextField = new JTextField("订单号/房间号");
		searchButton = new JButton("搜索");
		delButton = new JButton("删除");
		
		//设置组件
		setCom();
		
		northPanel.add(allOrderButton);
		northPanel.add(comboBox);
		northPanel.add(seachTextField);
		northPanel.add(searchButton);
		northPanel.add(delButton);
		
		
		/*
		 * 中间显示订单数据面板
		 */
		centerPanel = new JPanel();
		table = new JTable();
		model = new MyTableModel();
		model.addColumn("序号号");
		model.addColumn("订单号");
		model.addColumn("房间编号");
		model.addColumn("数量");
		model.addColumn("房间类型");
		model.addColumn("入住时间");
		model.addColumn("离店时间");
		model.addColumn("价格");
		model.addColumn("订单日期");
		model.addColumn("状态");
		//默认显示全部订单
		setTable("select * from orderNum where userid='" + LoginMainClass.USER + "'");
		
		
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(model);
		
		
		//设置表格大小
		table.setRowHeight(28);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//设置列宽
		table.getColumnModel().getColumn(0).setPreferredWidth(5);
		table.getColumnModel().getColumn(3).setPreferredWidth(5);
		table.getColumnModel().getColumn(7).setPreferredWidth(20);
		table.getColumnModel().getColumn(9).setPreferredWidth(20);
		//设置表格里面的文字居中显示
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		/*
		 * 事件处理
		 */
		//全部订单按钮
		allOrderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				setTable("select * from orderNum where userid='" + LoginMainClass.USER + "'");
				allOrderButton.setBackground(new Color(211,84,0));
				comboBox.setBackground(Color.white);
				
				seachTextField.setText("订单号/房间号");
			}
		});
		//搜索按钮
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(!seachTextField.getText().trim().equals("") && !seachTextField.getText().trim().equals("订单号/房间号")){
					searchOrder();
				}
			}
		});
		//搜索框事件
		seachTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根
				if(seachTextField.getText().trim().equals("订单号/房间号")){
					seachTextField.setText("");
				}
			}
		});
		seachTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根
				super.keyPressed(e);
				if(!seachTextField.getText().trim().equals("") 
						&& !seachTextField.getText().trim().equals("订单号/房间号") 
						&& e.getKeyCode() == KeyEvent.VK_ENTER){
					searchOrder();
				}
			}
		});
		//删除按钮
		delButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				int row = table.getSelectedRow();
				String orderNum = null;
				if(row >= 0){
					orderNum = (String) table.getValueAt(row, 1);
					int value = JOptionPane.showConfirmDialog(frame, "是否删除订单"+orderNum, "提示", JOptionPane.YES_NO_CANCEL_OPTION);
					if(value == 0){
						model.removeRow(row);
						//执行对数据库进行删除记录操作
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
				// TODO 自动生成的方法存根
				delButton.setBackground(Color.RED);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				delButton.setBackground(Color.white);
			}
			
		});
		//时间下拉列表
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				comboBox.setBackground(new Color(211,84,0));
				allOrderButton.setBackground(Color.white);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.getInstance();
				
				int choice = comboBox.getSelectedIndex();
				String sql = null,date = null;
				switch (choice) {
				
					case 0:							//近三个月的订单
						calendar.add(Calendar.MONTH, -3);
						date = format.format(calendar.getTime());
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and soledate>='" + date + "'";
						break;
					case 1:							//今年内的订单
						date = String.valueOf(calendar.get(Calendar.YEAR));
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and YEAR(soledate)='" + date + "'";
						break;
					case 2:							//前第一年的订单
						date = String.valueOf(calendar.get(Calendar.YEAR)-1);
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and YEAR(soledate)='" + date + "'";
						break;
					case 3:							//前第两年的订单
						date = String.valueOf(calendar.get(Calendar.YEAR)-2);
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and YEAR(soledate)='" + date + "'";
						break;
					case 4:							//两年以前的全部订单	
						date = String.valueOf(calendar.get(Calendar.YEAR)-2);
						sql = "select * from orderNum where userid='" 
								+ LoginMainClass.USER + "' and YEAR(soledate)<'" + date + "'";
						break;
					default:
						break;
				}
				setTable(sql);
				seachTextField.setText("订单号/房间号");
			}
		});
		centerPanel.add(scrollPane);
		add(northPanel,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);
	}
	//搜索订单
	public void searchOrder(){
		String content = seachTextField.getText().trim();
		if(!content.equals("") && !content.equals("订单号/房间号")){
			String sql = "select * from orderNum where (soleid like '%" 
					+ content + "%' or rid like '%" 
					+ content + "%') and userid='" + LoginMainClass.USER + "'";
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
	//设置组件
	public void setCom(){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		comboBox.addItem("近三个月的订单");
		comboBox.addItem("今年内的订单");
		comboBox.addItem(String.valueOf(year-1) + "年订单");
		comboBox.addItem(String.valueOf(year-2) + "年订单");
		comboBox.addItem(String.valueOf(year-2) + "年以前的订单");
		
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
