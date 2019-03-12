package com.jd.adminFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jd.userFrame.NorthPanel;

import frameUtil.DragPicListener;
import frameUtil.InitFrame;

//管理员面板
public class AdminFrame extends JFrame implements ActionListener{
	JButton roomTypeButton,roomButton,userButton,checkOutButton;
	JButton[] buttons = new JButton[4];
	NorthPanel northPanel;
	WestPanel westPanel;
	JPanel centerPanel;
	RoomTypePanel roomTypePanel;
	RoomManagePanel roomManagePanel;
	UserManagePanel userManagePanel;
	TuiFangPanel tuiFangPanel;
	CardLayout card;
	public AdminFrame(){
		setTitle("假日酒店管理系统");
		setLayout(new BorderLayout());
		northPanel = new NorthPanel(this);
		northPanel.setPreferredSize(new Dimension(1300, 150));
		westPanel = new WestPanel();
		centerPanel = new JPanel();
		
		roomTypePanel = new RoomTypePanel(this);
		roomManagePanel = new RoomManagePanel(this);
		userManagePanel = new UserManagePanel(this);
		tuiFangPanel = new TuiFangPanel(this);
		
		card = new CardLayout();
		centerPanel.setLayout(card);
		centerPanel.add(roomTypePanel,"房间类型");
		centerPanel.add(roomManagePanel,"房间管理");
		centerPanel.add(userManagePanel,"用户管理");
		centerPanel.add(tuiFangPanel,"退房");
		
		
//		房间类型，房间管理，用户管理，退房
		buttons = westPanel.getButton();
		roomTypeButton = buttons[0];
		roomButton = buttons[1];
		userButton = buttons[2];
		checkOutButton = buttons[3];
		roomTypeButton.addActionListener(this);
		roomButton.addActionListener(this);
		userButton.addActionListener(this);
		this.setUndecorated(true);
		checkOutButton.addActionListener(this);
		
		add(northPanel,BorderLayout.NORTH);
		add(westPanel,BorderLayout.WEST);
		add(centerPanel,BorderLayout.CENTER);
		InitFrame.initFrame(this, 1400, 800);
		
		DragPicListener dragPicListener = new DragPicListener(this);
		this.addMouseListener(dragPicListener);
		this.addMouseMotionListener(dragPicListener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton) e.getSource();
		button.setBackground(new Color(44,62,80));
		if(button.getText().trim().equals("房间类型")){
			roomTypePanel.searchField.setText("房间类型编号/房间类型");
			roomTypePanel.setTable("select * from roomtype");
			
			card.show(centerPanel, "房间类型");
			
			roomButton.setBackground(new Color(25,181,254));
			userButton.setBackground(new Color(25,181,254));
			checkOutButton.setBackground(new Color(25,181,254));
		}else if(button.getText().trim().equals("房间管理")){
			roomManagePanel.searchField.setText("房间号/房间类型");
			roomManagePanel.setTable("select room.*,roomtype.roomtype from room,roomtype "
					+ "where room.rtypeid=roomtype.rtypeid");
			
			card.show(centerPanel, "房间管理");
			
			roomTypeButton.setBackground(new Color(25,181,254));
			userButton.setBackground(new Color(25,181,254));
			checkOutButton.setBackground(new Color(25,181,254));
			
		}else if(button.getText().trim().equals("用户管理")){
			userManagePanel.searchField.setText("账号/姓名");
			userManagePanel.setTable("select * from userInformation");
			
			card.show(centerPanel, "用户管理");
			roomTypeButton.setBackground(new Color(25,181,254));
			roomButton.setBackground(new Color(25,181,254));
			checkOutButton.setBackground(new Color(25,181,254));
			
		}else if(button.getText().trim().equals("退房")){
			tuiFangPanel.searchField.setText("订单号/用户账号");
			//默认显示全部订单
			tuiFangPanel.setTable("select * from orderNum order by soleid desc");
			card.show(centerPanel, "退房");
			roomTypeButton.setBackground(new Color(25,181,254));
			roomButton.setBackground(new Color(25,181,254));
			userButton.setBackground(new Color(25,181,254));
		}
	}
	
}
