package com.jd.userFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jd.login.LoginMainClass;

//左边按钮面板
public class LeftPanel extends JPanel{
	JPanel abovePanel,belowPanel;
	JLabel userLabel1,userLabel2,dateLabel1,dateLabel2;
	JButton roomButton,orderButton,kongButton;
	Font font;
	Color color;
	public LeftPanel(){
		
		this.setLayout(new GridLayout(2, 1));
		font = new Font("宋体", Font.BOLD, 25);
		
		abovePanel = new JPanel();
		belowPanel = new JPanel();
		
		abovePanel.setLayout(new GridLayout(3, 1));
		belowPanel.setLayout(null);
		
		roomButton = new JButton("房态");
		orderButton = new JButton("订单");
		kongButton = new JButton("个人资料");
		
		userLabel1 = new JLabel("当前操作用户：");
		userLabel2 = new JLabel(LoginMainClass.USER);
		dateLabel1 = new JLabel("当前日期：");
		dateLabel2 = new JLabel();
		//设置按钮和标签
		setButtonAndLabel();
		
		
		abovePanel.add(roomButton);
		abovePanel.add(orderButton);
		abovePanel.add(kongButton);
		belowPanel.add(userLabel1);
		belowPanel.add(userLabel2);
		belowPanel.add(dateLabel1);
		belowPanel.add(dateLabel2);
		add(abovePanel);
		add(belowPanel);
		
	}
	public void setButtonAndLabel(){
		roomButton.setFont(font);						//设置样式
		orderButton.setFont(font);
		kongButton.setFont(font);
		
		roomButton.setBorderPainted(false);				//去掉按钮外边框
		orderButton.setBorderPainted(false);
		kongButton.setBorderPainted(false);
		
		roomButton.setFocusPainted(false);				//去掉按钮内部文字边线
		orderButton.setFocusPainted(false);
		kongButton.setFocusPainted(false);
		
		roomButton.setForeground(Color.white);			//去掉按钮内部文字颜色
		orderButton.setForeground(Color.white);
		kongButton.setForeground(Color.white);
		
		roomButton.setBackground(new Color(44,62,80));	//设置背景颜色
		orderButton.setBackground(new Color(25,181,254));
		kongButton.setBackground(new Color(25,181,254));
		
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
		JButton[] buttons = new JButton[3];
		buttons[0] = roomButton;
		buttons[1] = orderButton;
		buttons[2] = kongButton;
		return buttons;
	}
}
