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

//��߰�ť���
public class LeftPanel extends JPanel{
	JPanel abovePanel,belowPanel;
	JLabel userLabel1,userLabel2,dateLabel1,dateLabel2;
	JButton roomButton,orderButton,kongButton;
	Font font;
	Color color;
	public LeftPanel(){
		
		this.setLayout(new GridLayout(2, 1));
		font = new Font("����", Font.BOLD, 25);
		
		abovePanel = new JPanel();
		belowPanel = new JPanel();
		
		abovePanel.setLayout(new GridLayout(3, 1));
		belowPanel.setLayout(null);
		
		roomButton = new JButton("��̬");
		orderButton = new JButton("����");
		kongButton = new JButton("��������");
		
		userLabel1 = new JLabel("��ǰ�����û���");
		userLabel2 = new JLabel(LoginMainClass.USER);
		dateLabel1 = new JLabel("��ǰ���ڣ�");
		dateLabel2 = new JLabel();
		//���ð�ť�ͱ�ǩ
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
		roomButton.setFont(font);						//������ʽ
		orderButton.setFont(font);
		kongButton.setFont(font);
		
		roomButton.setBorderPainted(false);				//ȥ����ť��߿�
		orderButton.setBorderPainted(false);
		kongButton.setBorderPainted(false);
		
		roomButton.setFocusPainted(false);				//ȥ����ť�ڲ����ֱ���
		orderButton.setFocusPainted(false);
		kongButton.setFocusPainted(false);
		
		roomButton.setForeground(Color.white);			//ȥ����ť�ڲ�������ɫ
		orderButton.setForeground(Color.white);
		kongButton.setForeground(Color.white);
		
		roomButton.setBackground(new Color(44,62,80));	//���ñ�����ɫ
		orderButton.setBackground(new Color(25,181,254));
		kongButton.setBackground(new Color(25,181,254));
		
		userLabel1.setBounds( 10,  50,  150,  30);
		userLabel2.setBounds( 50,  90,  150,  30);
		dateLabel1.setBounds( 10,  170,  150,  30);
		dateLabel2.setBounds( 50,  210,  150,  30);
		//���õ�ǰ����
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dateLabel2.setText(format.format(Calendar.getInstance().getTime()));
	}
	//��ȡ��ť
	public JButton[] getButton(){
		JButton[] buttons = new JButton[3];
		buttons[0] = roomButton;
		buttons[1] = orderButton;
		buttons[2] = kongButton;
		return buttons;
	}
}
