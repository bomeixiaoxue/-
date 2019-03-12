package com.jd.adminFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jd.login.LoginMainClass;

//左边菜单(房间类型，房间管理，用户管理，退房)选择按钮面板
public class WestPanel extends JPanel{
	JPanel abovePanel,belowPanel;
	JButton roomTypeButton,roomButton,userButton,checkOutButton;
	JLabel userLabel1,userLabel2,dateLabel1,dateLabel2;
	Font font;
	Color color;
	
	
	public WestPanel(){
		setLayout(new GridLayout(2, 1));
		
		abovePanel = new JPanel();
		belowPanel = new JPanel();
		
		abovePanel.setLayout(new GridLayout(4, 1));
		belowPanel.setLayout(null);
		
		roomTypeButton = new JButton("房间类型");
		roomButton = new JButton("房间管理");
		userButton = new JButton("用户管理");
		checkOutButton = new JButton("退房");
		
		userLabel1 = new JLabel("当前操作用户：");
		userLabel2 = new JLabel(LoginMainClass.USER);
		dateLabel1 = new JLabel("当前日期：");
		dateLabel2 = new JLabel();
		//设置按钮和标签
		setButtonAndLabel();
		
		//上面面板添加按钮
		abovePanel.add(roomTypeButton);
		abovePanel.add(roomButton);
		abovePanel.add(userButton);
		abovePanel.add(checkOutButton);
		//下面板添加标签
		belowPanel.add(userLabel1);
		belowPanel.add(userLabel2);
		belowPanel.add(dateLabel1);
		belowPanel.add(dateLabel2);

		add(abovePanel);
		add(belowPanel);
		setPreferredSize(new Dimension(150, 650));
	}
	//设置按钮和标签
	public void setButtonAndLabel(){
		font = new Font("宋体", Font.BOLD, 25);
		
		roomTypeButton.setFont(font);						//设置样式
		roomButton.setFont(font);
		userButton.setFont(font);
		checkOutButton.setFont(font);
		
		roomTypeButton.setBorderPainted(false);				//去掉按钮外边框
		roomButton.setBorderPainted(false);
		userButton.setBorderPainted(false);
		checkOutButton.setBorderPainted(false);
		
		roomTypeButton.setFocusPainted(false);				//去掉按钮内部文字边线
		roomButton.setFocusPainted(false);
		userButton.setFocusPainted(false);
		checkOutButton.setFocusPainted(false);
		
		roomTypeButton.setForeground(Color.white);			//去掉按钮内部文字颜色
		roomButton.setForeground(Color.white);
		userButton.setForeground(Color.white);
		checkOutButton.setForeground(Color.white);
		
		roomTypeButton.setBackground(new Color(44,62,80));	//设置背景颜色
		roomButton.setBackground(new Color(25,181,254));
		userButton.setBackground(new Color(25,181,254));
		checkOutButton.setBackground(new Color(25,181,254));
		
		userLabel1.setBounds( 10,  50,  150,  30);
		userLabel2.setBounds( 50,  90,  150,  30);
		dateLabel1.setBounds( 10,  170,  150,  30);
		dateLabel2.setBounds( 50,  210,  150,  30);
		//设置当前日期
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dateLabel2.setText(format.format(Calendar.getInstance().getTime()));
	}
	//获取按钮
		public JButton[] getButton(){
			JButton[] buttons = new JButton[4];
			buttons[0] = roomTypeButton;
			buttons[1] = roomButton;
			buttons[2] = userButton;
			buttons[3] = checkOutButton;
			return buttons;
		}
}
