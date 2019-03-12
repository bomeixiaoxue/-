package com.jd.userFrame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jd.login.LoginMainClass;

import frameUtil.DragPicListener;
import frameUtil.InitFrame;

//用户窗体
public class UserFrame extends JFrame implements ActionListener{
	NorthPanel northPanel;
	LeftPanel leftPanel;
	static RoomStatePanel roomStatePanel;
	OrderPanel orderPanel;
	PersonInformationPanel personInformationPanel;
	JButton roomButton,orderButton,kongButton;
	JButton[] buttons = new JButton[3];
	JPanel centerPanel;
	CardLayout card;
	public UserFrame(){
		setTitle("假日酒店管理系统");
		this.setLayout(new BorderLayout());
		
		northPanel = new NorthPanel(this);
		leftPanel = new LeftPanel();
		centerPanel = new JPanel();
		roomStatePanel = new RoomStatePanel(this);
		orderPanel = new OrderPanel(this);
		personInformationPanel = new PersonInformationPanel(this);
		
		northPanel.setPreferredSize(new Dimension(1300, 150));
		leftPanel.setPreferredSize(new Dimension(150, 650));
		
		card = new CardLayout();
		centerPanel.setLayout(card);
		centerPanel.add(roomStatePanel,"房态");
		centerPanel.add(orderPanel,"订单");
		centerPanel.add(personInformationPanel,"个人资料");
		
		getContentPane().add(northPanel, BorderLayout.NORTH);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		//获取左面板按钮
		buttons = leftPanel.getButton();
		roomButton = buttons[0];
		orderButton = buttons[1];
		kongButton = buttons[2];
		roomButton.addActionListener(this);
		orderButton.addActionListener(this);
		kongButton.addActionListener(this);
		
		roomStatePanel.validate();
		orderPanel.validate();
		centerPanel.validate();
		this.setResizable(false);
		this.setUndecorated(true);
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
		if(button.getText().trim().equals("房态")){
			
			roomStatePanel.setMenu();
			for(int i = 0; i < roomStatePanel.buttons.size(); i++){
				if(roomStatePanel.buttons.get(i).getText().equals(RoomStatePanel.actionButton.getText())){
					roomStatePanel.buttons.get(i).setBackground(new Color(211,84,0));
				}
					
			}
			card.show(centerPanel, "房态");

			orderButton.setBackground(new Color(25,181,254));
			kongButton.setBackground(new Color(25,181,254));
		}else if(button.getText().trim().equals("订单")){
			orderPanel.seachTextField.setText("订单号/房间号");
			card.show(centerPanel, "订单");
			//初始化订单面板
			orderPanel.setTable("select * from orderNum where userid='" + LoginMainClass.USER + "'");
			orderPanel.allOrderButton.setBackground(new Color(211,84,0));
			orderPanel.comboBox.setBackground(Color.white);
			roomButton.setBackground(new Color(25,181,254));
			kongButton.setBackground(new Color(25,181,254));
		}else if(button.getText().trim().equals("个人资料")){
			card.show(centerPanel, "个人资料");
			roomButton.setBackground(new Color(25,181,254));
			orderButton.setBackground(new Color(25,181,254));
		}
		centerPanel.validate();
		//对删除下单区房价按钮其标识作用
		RoomStateSouthPanel.flag = false;
	}
}
