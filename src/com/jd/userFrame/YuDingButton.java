package com.jd.userFrame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

//���������Ԥ����ť
public class YuDingButton extends JButton implements MouseListener{
	Color color = null;;
	String roomID = null;
	public YuDingButton(String name){
		super(name);
		
		addMouseListener(this);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������
		//�����������Ԥ���ķ����
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JButton button = (JButton)e.getSource();
		color = button.getBackground();
		button.setBackground(new Color(134,226,213));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JButton button = (JButton)e.getSource();
		button.setBackground(color);
	}
	//��¼�����
	public void setRoomID(String roomID){
		this.roomID = roomID;
	}
	//��ȡ�����
	public String getRoomID(){
		return roomID;
	}
}
