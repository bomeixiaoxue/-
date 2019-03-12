package com.jd.userFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NorthPanel extends JPanel implements MouseListener{
	ImageIcon imageIcon;
	JLabel label;
	JButton button;
	JLabel minLabel,closeLabel;
	JFrame frame;
	public NorthPanel(JFrame frame){
		this.frame = frame;
		setLayout(null);
		setBounds(0, 0, 1400, 160);
		imageIcon = new ImageIcon("src/image/jiarujd.jpg");
		//自定义图片大小
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(1400,150,Image.SCALE_DEFAULT));
		label = new JLabel();
		label.setIcon(imageIcon);
		label.setBounds(0, 0, 1400, 150);
		
		//最小化标签
		minLabel = new JLabel("-");
		minLabel.setFont(new Font("黑体", Font.BOLD, 35));
		minLabel.setForeground(Color.white);
		minLabel.setBounds(1305, 0, 50, 40);
		
		//关闭标签
		closeLabel = new JLabel("×");
		closeLabel.setFont(new Font("黑体", Font.BOLD, 30));
		closeLabel.setForeground(Color.white);
		closeLabel.setBounds(1350, 0, 50, 40);

		this.add(minLabel);
		this.add(closeLabel);
		this.add(label);
		
		minLabel.addMouseListener(this);
		closeLabel.addMouseListener(this);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		JLabel label = (JLabel) e.getSource();
		if(label == closeLabel){
			System.exit(0);
		}else{
			frame.setExtendedState(JFrame.ICONIFIED);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		JLabel label = (JLabel) e.getSource();
		if(label == closeLabel){
			label.setForeground(Color.red);
		}else{
			label.setForeground(new Color(25,181,254));
		}
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		JLabel label = (JLabel) e.getSource();
		label.setForeground(Color.white);
	}
	
}
