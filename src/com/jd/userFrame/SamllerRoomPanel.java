package com.jd.userFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

//�����������
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
	//���ñ�ǩ
	public void setLabel(String roomNum,String price){
		roomLabel.setText(" ����: " + roomNum);
		priceLabel.setText(" �۸�: " + price + " Ԫ");
	}
	
	
	/*
	*����¼�
	*/
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
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
		JPanel panel = (JPanel) e.getSource();
		color = panel.getBackground();
		panel.setBackground(new Color(134,226,213));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JPanel panel = (JPanel) e.getSource();
		panel.setBackground(color);
	}
	
}
