package com.jd.userFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jd.sql.Sunsql;

//房态面板
public class RoomStatePanel extends JPanel implements ActionListener{
	JPanel northPanel,centerPanel,centerNorthPanel,centerCenterPanel;
	RoomStateSouthPanel southPanel;
	SamllerRoomPanel samllerRoomPanel;
	static JComboBox<String> comboBoxIn,comboBoxOut;
	Vector<JButton> buttons;
	Vector<String> rooms;
	static JButton actionButton;
	ResultSet result = null;
	static int indexIN = 0;   //日期下列列表的下标
	static int indexOut = 0;
	public RoomStatePanel(JFrame frame){
		setLayout(new BorderLayout());
		
		northPanel = new JPanel();
		centerPanel = new JPanel();
		centerNorthPanel = new JPanel();
		centerCenterPanel = new JPanel();
		southPanel = new RoomStateSouthPanel(frame);
		buttons = new Vector<JButton>();
		rooms = new Vector<String>();
//		actionButton = new JButton("单人房");
		JLabel inTime = new JLabel("                  入店时间:");
		JLabel outTime = new JLabel("                 离店时间:");
		comboBoxIn = new JComboBox<>();
		comboBoxOut = new JComboBox<>();
		
		northPanel.setLayout(new GridLayout(1, getRow("roomtype")));
		centerPanel.setLayout(new BorderLayout());
		centerNorthPanel.setLayout(new GridLayout(1, 7));
		centerCenterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		northPanel.setPreferredSize(new Dimension(1150, 50));
		centerNorthPanel.setPreferredSize(new Dimension(1150, 30));
		southPanel.setPreferredSize(new Dimension(1150, 100));
		
		southPanel.setBackground(Color.gray);
		centerNorthPanel.setBackground(Color.gray);
		southPanel.validate();
		
		//初始化下列列表
		frameUtil.Time.setInOutTime(comboBoxIn, comboBoxOut);
		
		JLabel jLabel = new JLabel("    红色不可预订");
		jLabel.setForeground(Color.red);
		centerNorthPanel.add(inTime);
		centerNorthPanel.add(comboBoxIn);
		centerNorthPanel.add(outTime);
		centerNorthPanel.add(comboBoxOut);
		centerNorthPanel.add(new JLabel());
		centerNorthPanel.add(new JLabel());
		centerNorthPanel.add(jLabel);
		
		centerPanel.add(centerNorthPanel,BorderLayout.NORTH);
		centerPanel.add(centerCenterPanel,BorderLayout.CENTER);
		centerPanel.invalidate();
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		//1 创建房间类型选择菜单栏，2 获取不可用房间号集合，3 初始化各个房间面板
		setMenu();
		actionButton = buttons.get(0);
		getNoRoom();
		setSmallerRoom("单人房");
		
		
		comboBoxIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				indexIN = comboBoxIn.getSelectedIndex();
				indexOut = comboBoxOut.getSelectedIndex();
				int count = indexOut-indexIN;
				if(count < 0){
					comboBoxOut.setSelectedIndex(comboBoxIn.getSelectedIndex());
					count = 1;
				}
				//获取不可预订房间号
				getNoRoom();
				//给中间面板添加房间
				setSmallerRoom(actionButton.getText());
				//传递不可预订房间
				southPanel.setRoom(rooms);
				//对删除下单区房价按钮其标识作用
				RoomStateSouthPanel.flag = false;
			}
		});
		comboBoxOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				indexIN = comboBoxIn.getSelectedIndex();
				indexOut = comboBoxOut.getSelectedIndex();
				int count = indexOut-indexIN;
				if(count < 0){
					JOptionPane.showMessageDialog(frame, "离店时间不能小于或等于入店时间", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
					comboBoxOut.setSelectedIndex(comboBoxIn.getSelectedIndex());
					count = 1;
				}
				getNoRoom();
				setSmallerRoom(actionButton.getText());
				southPanel.setRoom(rooms);
				
				//对删除下单区房价按钮其标识作用
				RoomStateSouthPanel.flag = false;
			}
		});
	}
	
	//创建房间类型菜单栏
	public void setMenu(){
		northPanel.removeAll();
		String sql = "select * from roomtype";
		
		Connection connection = Sunsql.getConnection();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()){
				buttons.add(new JButton(result.getString(2)));
				buttons.lastElement().setFocusPainted(false);
				buttons.lastElement().setBackground(new Color(210,215,211));
				northPanel.add(buttons.lastElement());
			}
			
			result.close();			// 关闭结果集对象
			statement.close();		// 关闭连接状态对象
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		buttons.get(0).setBackground(new Color(211,84,0));
		/*
		 * 给选择栏按钮添加事件
		 */
		for(int i = 0; i < buttons.size(); i++){
			buttons.get(i).addActionListener(new  ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
					actionButton = (JButton) e.getSource();
					getNoRoom();
					setSmallerRoom(actionButton.getText());
					
					//对删除下单区房价按钮其标识作用
					RoomStateSouthPanel.flag = false;
					
					//设置按钮选中和不选中的颜色
					actionButton.setBackground(new Color(211,84,0));
					for(JButton button: buttons){
						if(button != actionButton){
							button.setBackground(new Color(210,215,211));
						}
					}
				}
			});
		}
	}
	//返回数据总行数
	public int getRow(String table){
		int row = 0;
		String sql = "select count(*) from " + table;
		Connection connection = Sunsql.getConnection();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			result.next();
			row = result.getInt(1);
			
			result.close();			// 关闭结果集对象
			statement.close();		// 关闭连接状态对象
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		return row;
	}
	
	//给中间面板添加房间
	public void setSmallerRoom(String roomtype){
		centerCenterPanel.removeAll();
		centerCenterPanel.repaint();
		YuDingButton button = null;
		String sql = "select room.rid,room.rprice,room.rstate"
				     + " from room,roomtype "
					 + "where room.rtypeid=roomtype.rtypeid and roomtype.roomtype='" + roomtype +"'";
		Connection connection = Sunsql.getConnection();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()){
				String roomID = result.getString(1);
				String roomPrice = result.getString(2).substring(0,result.getString(2).indexOf('.'));
				String state = result.getString(3);
				samllerRoomPanel = new SamllerRoomPanel();
				samllerRoomPanel.setLabel(roomID, roomPrice);
				button = new YuDingButton("预订");
				button.setBounds(0, 90, 120, 30);
				button.setRoomID(roomID);
				button.setBorderPainted(false);
				button.setFocusPainted(false);
				button.setBackground(new Color(191,191,191));
				button.addActionListener(this);
				//如果房间不可用，就设置背景颜色红色且不添加预订按钮
				if(!rooms.contains(roomID)){
					samllerRoomPanel.add(button);
				}else {
					samllerRoomPanel.setBackground(Color.red);
				}
				if(state.equals("可用")){
					centerCenterPanel.add(samllerRoomPanel);
				}
				centerCenterPanel.validate();
			}
			
			result.close();			// 关闭结果集对象
			statement.close();		// 关闭连接状态对象
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		
	}
	//获取不可预订的房间号集合
	public void getNoRoom() {
		rooms.removeAllElements();
		String inTime = comboBoxIn.getSelectedItem().toString();
		String outTime = comboBoxOut.getSelectedItem().toString();
		
		String number = null;
		String sql = "select rid,number from dbo.orderNum AS O "
				+ " where O.soleid not in (select soleid from dbo.orderNum "
										+ "where outtime<'" + inTime + "' "
										+ "or intime>'" + outTime + "') and state!='结算离店'";
		
		Connection connection = Sunsql.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()){
				String rid = result.getString(1);
				number = result.getString(2).trim();
				if(number.equals("1")){
					rooms.add(rid);
				}else {
					int count = Integer.valueOf(number).intValue();
					String[] strings = new String[count];
					strings = rid.split(",");
					for(int i = 0; i < strings.length; i++){
						rooms.addElement(strings[i]);
					}
				}
			}
			
			result.close();			// 关闭结果集对象
			statement.close();		// 关闭连接状态对象
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Sunsql.closeConnection();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		YuDingButton yuDingButton = (YuDingButton) e.getSource();
		//给开单区添加预订的房间号
		southPanel.addRoomID(yuDingButton.getRoomID());
		//刷新面板
		southPanel.validate();
		
		//对删除下单区房价按钮其标识作用
		RoomStateSouthPanel.flag = false;
	}
}
