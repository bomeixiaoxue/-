package com.jd.userFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

//单个房间面板
public class SamllerRoomPanel extends JPanel implements MouseListener{
	Color color;
	JLabel roomLabel,priceLabel;
	public SamllerRoomPanel(){
		setPreferredSize(new Dimension(120,120));
		setLayout(null);
		setBackground(new Color(191,191,191));
		roomLabel = new JLabel();
		priceLabel = new JLabel();
		
		roomLabel.setBounds(0, 10, 120, 30);
		priceLabel.setBounds(0, 40, 120, 30);
		add(roomLabel);
		add(priceLabel);
		
		
		addMouseListener(this);
	}
	//设置标签
	public void setLabel(String roomNum,String price){
		roomLabel.setText(" 房号: " + roomNum);
		priceLabel.setText(" 价格: " + price + " 元");
	}
	
	
	/*
	*鼠标事件
	*/
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		
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
		JPanel panel = (JPanel) e.getSource();
		color = panel.getBackground();
		panel.setBackground(new Color(134,226,213));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		JPanel panel = (JPanel) e.getSource();
		panel.setBackground(color);
	}
	
}
