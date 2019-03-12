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

//�û�����
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
		setTitle("���վƵ����ϵͳ");
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
		centerPanel.add(roomStatePanel,"��̬");
		centerPanel.add(orderPanel,"����");
		centerPanel.add(personInformationPanel,"��������");
		
		getContentPane().add(northPanel, BorderLayout.NORTH);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		//��ȡ����尴ť
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
		// TODO �Զ����ɵķ������
		JButton button = (JButton) e.getSource();
		button.setBackground(new Color(44,62,80));
		if(button.getText().trim().equals("��̬")){
			
			roomStatePanel.setMenu();
			for(int i = 0; i < roomStatePanel.buttons.size(); i++){
				if(roomStatePanel.buttons.get(i).getText().equals(RoomStatePanel.actionButton.getText())){
					roomStatePanel.buttons.get(i).setBackground(new Color(211,84,0));
				}
					
			}
			card.show(centerPanel, "��̬");

			orderButton.setBackground(new Color(25,181,254));
			kongButton.setBackground(new Color(25,181,254));
		}else if(button.getText().trim().equals("����")){
			orderPanel.seachTextField.setText("������/�����");
			card.show(centerPanel, "����");
			//��ʼ���������
			orderPanel.setTable("select * from orderNum where userid='" + LoginMainClass.USER + "'");
			orderPanel.allOrderButton.setBackground(new Color(211,84,0));
			orderPanel.comboBox.setBackground(Color.white);
			roomButton.setBackground(new Color(25,181,254));
			kongButton.setBackground(new Color(25,181,254));
		}else if(button.getText().trim().equals("��������")){
			card.show(centerPanel, "��������");
			roomButton.setBackground(new Color(25,181,254));
			orderButton.setBackground(new Color(25,181,254));
		}
		centerPanel.validate();
		//��ɾ���µ������۰�ť���ʶ����
		RoomStateSouthPanel.flag = false;
	}
}
