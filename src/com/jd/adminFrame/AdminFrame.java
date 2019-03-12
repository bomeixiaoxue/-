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

//����Ա���
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
		setTitle("���վƵ����ϵͳ");
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
		centerPanel.add(roomTypePanel,"��������");
		centerPanel.add(roomManagePanel,"�������");
		centerPanel.add(userManagePanel,"�û�����");
		centerPanel.add(tuiFangPanel,"�˷�");
		
		
//		�������ͣ���������û������˷�
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
		// TODO �Զ����ɵķ������
		JButton button = (JButton) e.getSource();
		button.setBackground(new Color(44,62,80));
		if(button.getText().trim().equals("��������")){
			roomTypePanel.searchField.setText("�������ͱ��/��������");
			roomTypePanel.setTable("select * from roomtype");
			
			card.show(centerPanel, "��������");
			
			roomButton.setBackground(new Color(25,181,254));
			userButton.setBackground(new Color(25,181,254));
			checkOutButton.setBackground(new Color(25,181,254));
		}else if(button.getText().trim().equals("�������")){
			roomManagePanel.searchField.setText("�����/��������");
			roomManagePanel.setTable("select room.*,roomtype.roomtype from room,roomtype "
					+ "where room.rtypeid=roomtype.rtypeid");
			
			card.show(centerPanel, "�������");
			
			roomTypeButton.setBackground(new Color(25,181,254));
			userButton.setBackground(new Color(25,181,254));
			checkOutButton.setBackground(new Color(25,181,254));
			
		}else if(button.getText().trim().equals("�û�����")){
			userManagePanel.searchField.setText("�˺�/����");
			userManagePanel.setTable("select * from userInformation");
			
			card.show(centerPanel, "�û�����");
			roomTypeButton.setBackground(new Color(25,181,254));
			roomButton.setBackground(new Color(25,181,254));
			checkOutButton.setBackground(new Color(25,181,254));
			
		}else if(button.getText().trim().equals("�˷�")){
			tuiFangPanel.searchField.setText("������/�û��˺�");
			//Ĭ����ʾȫ������
			tuiFangPanel.setTable("select * from orderNum order by soleid desc");
			card.show(centerPanel, "�˷�");
			roomTypeButton.setBackground(new Color(25,181,254));
			roomButton.setBackground(new Color(25,181,254));
			userButton.setBackground(new Color(25,181,254));
		}
	}
	
}
