package com.jd.userFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//房态面板的下面板
public class RoomStateSouthPanel extends JPanel implements FocusListener{
	JPanel leftPanel,rightPanel;
	static JPanel centerPane;
	JButton button,roomButton,commitButton,delButton,finishButton;
	JLabel leftLabel;
	Vector<String> vector;
	Vector<String> noRoom;
	Color color;
	static boolean flag = false;	//对删除下单区房价按钮其标识作用
	OrderJDialog orderJDialog;
	JFrame frame;
	public RoomStateSouthPanel(JFrame frame){
		this.frame = frame;
		setLayout(new BorderLayout());
		vector = new Vector<String>();
		noRoom = new Vector<String>();
		leftPanel = new JPanel(); 
		centerPane = new JPanel();
		rightPanel = new JPanel();
		leftLabel = new JLabel(" 下单区");
		commitButton = new JButton("下  单");
		delButton = new JButton("删  除");
		roomButton = new JButton("删除");
		
		leftPanel.setPreferredSize(new Dimension(150, 150));
		rightPanel.setPreferredSize(new Dimension(200, 150));
		
		leftPanel.setLayout(new BorderLayout());
		rightPanel.setLayout(null);
		leftLabel.setFont(new Font("宋体", Font.BOLD, 30));
		leftPanel.setBackground(new Color(211,84,0));
		commitButton.setBounds(0, 0, 100, 30);
		commitButton.setBackground(new Color(154,18,179));
		commitButton.setFocusPainted(false);
		commitButton.setForeground(Color.white);
		delButton.setBounds(70, 60, 100, 30);
		delButton.setBackground(Color.RED);
		delButton.setFocusPainted(false);
		delButton.setForeground(Color.white);
		
		leftPanel.add(leftLabel,BorderLayout.CENTER);
		rightPanel.add(commitButton);
		rightPanel.add(delButton);
		add(leftPanel,BorderLayout.WEST);
		add(centerPane,BorderLayout.CENTER);
		add(rightPanel,BorderLayout.EAST);
		centerPane.validate();
		//给删除按钮添加事件
		delButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(roomButton != null && vector.contains(roomButton.getText()) 
						&& flag==true){
					vector.remove(roomButton.getText());
					centerPane.remove(roomButton);
					centerPane.repaint();
				}
			}
		});
		//给下单按钮添加事件
		commitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(!vector.isEmpty()){
					String string = getNoRoom();
					if(!string.equals("")){
						JOptionPane.showMessageDialog(null, string + "房间在该时段不能预订..", "提示", JOptionPane.INFORMATION_MESSAGE);
					}else {
						orderJDialog = new OrderJDialog(frame,"订单",true,vector);
					}
				}else{
					JOptionPane.showMessageDialog(null, "请预定房间再下单...", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
				//对删除下单区房价按钮其标识作用
				RoomStateSouthPanel.flag = false;
			}
		});
		
	}
	//获取不可预订房间
	public String getNoRoom(){
		StringBuffer stringBuffer = new StringBuffer();
		if(!(noRoom.isEmpty() && vector.isEmpty())){
			for(String str: noRoom){
				if(vector.contains(str)){
					stringBuffer.append(str + " ");
				}
			}
		}
		return stringBuffer.toString();
	}
	//传递不可预订房间
	public void setRoom(Vector<String> noRoom){
		this.noRoom = noRoom;
	}
	//给中间面板添加预订的房间号
	public void addRoomID(String RoomID){
		if(!vector.contains(RoomID)){
			button = new JButton(RoomID);
			button.setBackground(new Color(154,18,179));
			button.setFocusPainted(false);
			button.setForeground(Color.white);
			button.addFocusListener(this);
			centerPane.add(button);
			vector.add(RoomID);
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton)e.getSource();
		color = button.getBackground();
		button.setBackground(new Color(247,202,24));
		roomButton = button;
		//对删除下单区房价按钮其标识作用
		flag = true;
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton)e.getSource();
		button.setBackground(color);
	}
}
