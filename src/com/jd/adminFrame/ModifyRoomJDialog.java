package com.jd.adminFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.jd.sql.Sunsql;

//�޸ķ�����Ϣ�Ի���
public class ModifyRoomJDialog extends JDialog{
	JLabel roomLabel,priceLabel,priceValueLabel,stateLabel,phoneLabel,addressLabel,roomType;
	JComboBox<String> addressComboBox,roomTypeComboBox,stateComboBox;
	JButton finishButton;
	Vector<String> vector,priceV;
	Vector<Integer> rtypeidV;
	public ModifyRoomJDialog(JFrame frame,String title,boolean bool,Vector<String> v){
		super(frame, title, bool);
		vector = v;
		priceV = new Vector<>();
		rtypeidV = new Vector<>();
		setLayout(null);
		roomLabel = new JLabel("�����: " + vector.get(0));
		priceLabel = new JLabel("�۸�: ");
		priceValueLabel = new JLabel(vector.get(1));
		roomType = new JLabel("��������: ");
		phoneLabel = new JLabel("�绰: " + vector.get(5));
		addressLabel = new JLabel("λ��: ");
		stateLabel = new JLabel("״̬: ");
		
		roomTypeComboBox = new JComboBox<>();
		addressComboBox = new JComboBox<>();
		stateComboBox = new JComboBox<>();
		
		finishButton = new JButton("���");
		
		//�������
		setCom();
		//���������б�
		setComboBox();
		
		/*
		 * �¼�
		 */
		//�������������б��¼�
		roomTypeComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				int index = roomTypeComboBox.getSelectedIndex();
				priceValueLabel.setText(priceV.get(index) + ".0000");
			}
		});
		//��ɰ�ť�¼�
		finishButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO �Զ����ɵķ������
				String rid = vector.get(0);
				int rtypeid = rtypeidV.get(roomTypeComboBox.getSelectedIndex());
				String sql = "update dbo.room set rprice='"+ priceValueLabel.getText() 
				+"',raddress='" + addressComboBox.getSelectedItem().toString()+"',"
				+ "rtypeid='"+ rtypeid +"', rstate='"+ stateComboBox.getSelectedItem().toString() 
				+"' where rid='"+ vector.get(0) +"'";
				Sunsql.upData(sql);
				dispose();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO �Զ����ɵķ������
				JButton button = (JButton) e.getSource();
				button.setBackground(new Color(191,85,236));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO �Զ����ɵķ������
				JButton button = (JButton) e.getSource();
				button.setBackground(new Color(154,18,179));
			}
		});
		
		
		add(roomLabel);
		add(priceLabel);
		add(priceValueLabel);
		add(stateLabel);
		add(stateComboBox);
		add(phoneLabel);
		add(addressLabel);
		add(roomType);
		add(addressComboBox);
		add(roomTypeComboBox);
		add(finishButton);
		
		frameUtil.InitFrame.initFrame(this, 630, 500);
	}
	//���������б�
	public void setComboBox(){
		Connection connection = Sunsql.getConnection();
		String sql = "select rtypeid,roomtype,price from roomtype";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				rtypeidV.addElement(resultSet.getInt(1));
				roomTypeComboBox.addItem(resultSet.getString(2));
				priceV.addElement(resultSet.getString(3));
			}
			resultSet.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		
		addressComboBox.addItem("һ¥");
		addressComboBox.addItem("��¥");
		addressComboBox.addItem("��¥");
		addressComboBox.addItem("��¥");
		addressComboBox.addItem("��¥");
		addressComboBox.addItem("��¥");
		addressComboBox.addItem("��¥");
		addressComboBox.addItem("��¥");
		
		stateComboBox.addItem("����");
		stateComboBox.addItem("������");
		stateComboBox.addItem("ά����");
		
		addressComboBox.setSelectedItem(vector.get(2));
		roomTypeComboBox.setSelectedItem(vector.get(3));
		stateComboBox.setSelectedItem(vector.get(4));
	}
	
	//�������
	public void setCom(){
		Font font = new Font("����", Font.BOLD, 20);
		roomLabel.setFont(font);
		priceLabel.setFont(font);
		priceValueLabel.setFont(font);
		roomType.setFont(font);
		phoneLabel.setFont(font);
		addressLabel.setFont(font);
		stateLabel.setFont(font);
		stateComboBox.setFont(font);
		addressComboBox.setFont(font);
		roomTypeComboBox.setFont(font);
		finishButton.setFont(font);
		
		roomLabel.setBounds(50, 50, 200, 30);
		priceLabel.setBounds(370, 50, 100, 30);
		priceValueLabel.setBounds(435, 50, 200, 30);
		roomType.setBounds(30, 150, 150, 30);
		roomTypeComboBox.setBounds(130, 150, 200, 30);
		phoneLabel.setBounds(370, 150, 200, 30);
		addressLabel.setBounds(70, 250, 150, 30);
		addressComboBox.setBounds(130, 250, 200, 30);
		stateLabel.setBounds(370, 250, 150, 30);
		stateComboBox.setBounds(430, 250, 150, 30);
		finishButton.setBounds(225, 350, 150, 30);
		finishButton.setBackground(new Color(154,18,179));
		finishButton.setBorderPainted(false);
		finishButton.setFocusPainted(false);
		finishButton.setForeground(Color.white);
	}
	
	
}
