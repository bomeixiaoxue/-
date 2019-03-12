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

//��̬���������
public class RoomStateSouthPanel extends JPanel implements FocusListener{
	JPanel leftPanel,rightPanel;
	static JPanel centerPane;
	JButton button,roomButton,commitButton,delButton,finishButton;
	JLabel leftLabel;
	Vector<String> vector;
	Vector<String> noRoom;
	Color color;
	static boolean flag = false;	//��ɾ���µ������۰�ť���ʶ����
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
		leftLabel = new JLabel(" �µ���");
		commitButton = new JButton("��  ��");
		delButton = new JButton("ɾ  ��");
		roomButton = new JButton("ɾ��");
		
		leftPanel.setPreferredSize(new Dimension(150, 150));
		rightPanel.setPreferredSize(new Dimension(200, 150));
		
		leftPanel.setLayout(new BorderLayout());
		rightPanel.setLayout(null);
		leftLabel.setFont(new Font("����", Font.BOLD, 30));
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
		//��ɾ����ť����¼�
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
		//���µ���ť����¼�
		commitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				if(!vector.isEmpty()){
					String string = getNoRoom();
					if(!string.equals("")){
						JOptionPane.showMessageDialog(null, string + "�����ڸ�ʱ�β���Ԥ��..", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					}else {
						orderJDialog = new OrderJDialog(frame,"����",true,vector);
					}
				}else{
					JOptionPane.showMessageDialog(null, "��Ԥ���������µ�...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
				//��ɾ���µ������۰�ť���ʶ����
				RoomStateSouthPanel.flag = false;
			}
		});
		
	}
	//��ȡ����Ԥ������
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
	//���ݲ���Ԥ������
	public void setRoom(Vector<String> noRoom){
		this.noRoom = noRoom;
	}
	//���м�������Ԥ���ķ����
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
		// TODO �Զ����ɵķ������
		JButton button = (JButton)e.getSource();
		color = button.getBackground();
		button.setBackground(new Color(247,202,24));
		roomButton = button;
		//��ɾ���µ������۰�ť���ʶ����
		flag = true;
	}
	@Override
	public void focusLost(FocusEvent e) {
		// TODO �Զ����ɵķ������
		JButton button = (JButton)e.getSource();
		button.setBackground(color);
	}
}
