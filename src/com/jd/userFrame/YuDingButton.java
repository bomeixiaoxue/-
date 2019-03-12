package com.jd.userFrame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

//单个房间的预订按钮
public class YuDingButton extends JButton implements MouseListener{
	Color color = null;;
	String roomID = null;
	public YuDingButton(String name){
		super(name);
		
		addMouseListener(this);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		//给开单区添加预订的房间号
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton)e.getSource();
		color = button.getBackground();
		button.setBackground(new Color(134,226,213));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton)e.getSource();
		button.setBackground(color);
	}
	//记录房间号
	public void setRoomID(String roomID){
		this.roomID = roomID;
	}
	//获取房间号
	public String getRoomID(){
		return roomID;
	}
}
