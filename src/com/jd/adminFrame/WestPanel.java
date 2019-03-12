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

//��߲˵�(�������ͣ���������û������˷�)ѡ��ť���
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
		
		roomTypeButton = new JButton("��������");
		roomButton = new JButton("�������");
		userButton = new JButton("�û�����");
		checkOutButton = new JButton("�˷�");
		
		userLabel1 = new JLabel("��ǰ�����û���");
		userLabel2 = new JLabel(LoginMainClass.USER);
		dateLabel1 = new JLabel("��ǰ���ڣ�");
		dateLabel2 = new JLabel();
		//���ð�ť�ͱ�ǩ
		setButtonAndLabel();
		
		//���������Ӱ�ť
		abovePanel.add(roomTypeButton);
		abovePanel.add(roomButton);
		abovePanel.add(userButton);
		abovePanel.add(checkOutButton);
		//�������ӱ�ǩ
		belowPanel.add(userLabel1);
		belowPanel.add(userLabel2);
		belowPanel.add(dateLabel1);
		belowPanel.add(dateLabel2);

		add(abovePanel);
		add(belowPanel);
		setPreferredSize(new Dimension(150, 650));
	}
	//���ð�ť�ͱ�ǩ
	public void setButtonAndLabel(){
		font = new Font("����", Font.BOLD, 25);
		
		roomTypeButton.setFont(font);						//������ʽ
		roomButton.setFont(font);
		userButton.setFont(font);
		checkOutButton.setFont(font);
		
		roomTypeButton.setBorderPainted(false);				//ȥ����ť��߿�
		roomButton.setBorderPainted(false);
		userButton.setBorderPainted(false);
		checkOutButton.setBorderPainted(false);
		
		roomTypeButton.setFocusPainted(false);				//ȥ����ť�ڲ����ֱ���
		roomButton.setFocusPainted(false);
		userButton.setFocusPainted(false);
		checkOutButton.setFocusPainted(false);
		
		roomTypeButton.setForeground(Color.white);			//ȥ����ť�ڲ�������ɫ
		roomButton.setForeground(Color.white);
		userButton.setForeground(Color.white);
		checkOutButton.setForeground(Color.white);
		
		roomTypeButton.setBackground(new Color(44,62,80));	//���ñ�����ɫ
		roomButton.setBackground(new Color(25,181,254));
		userButton.setBackground(new Color(25,181,254));
		checkOutButton.setBackground(new Color(25,181,254));
		
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
			JButton[] buttons = new JButton[4];
			buttons[0] = roomTypeButton;
			buttons[1] = roomButton;
			buttons[2] = userButton;
			buttons[3] = checkOutButton;
			return buttons;
		}
}
