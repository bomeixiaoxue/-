package com.jd.adminFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.jd.sql.Sunsql;

//�޸ķ�����Ϣ�Ի���
public class AddRoomJDialog extends JDialog{
	JLabel roomLabel,priceLabel,priceValueLabel,stateLabel,phoneLabel,addressLabel,roomType;
	JComboBox<String> addressComboBox,roomTypeComboBox,stateComboBox;
	JTextField roomTextField;
	JButton finishButton;
	Vector<String> priceV;
	Vector<Integer> rtypeidV;
	public AddRoomJDialog(JFrame frame,String title,boolean bool){
		super(frame, title, bool);
		priceV = new Vector<>();
		rtypeidV = new Vector<>();
		setLayout(null);
		roomLabel = new JLabel("�����: ");
		priceLabel = new JLabel("�۸�: ");
		priceValueLabel = new JLabel();
		roomType = new JLabel("��������: ");
		phoneLabel = new JLabel("�绰: ");
		addressLabel = new JLabel("λ��: ");
		stateLabel = new JLabel("״̬: ");
		
		roomTypeComboBox = new JComboBox<>();
		addressComboBox = new JComboBox<>();
		stateComboBox = new JComboBox<>();
		roomTextField = new JTextField(15);
		finishButton = new JButton("���");
		
		//�������
		setCom();
		//���������б�
		setComboBox();
		priceValueLabel.setText(priceV.get(0) + ".0000");
		/*
		 * �¼�
		 */
		//
		roomTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO �Զ����ɵķ������
				phoneLabel.setText("�绰: " + roomTextField.getText().trim());
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO �Զ����ɵķ������
				
			}
		});
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
				String rid = roomTextField.getText().trim();
				if(!rid.equals("")){
					String sql = "select rid from room where rid='" + rid + "'";
					Connection connection = Sunsql.getConnection();
					try {
						Statement statement = connection.createStatement();
						ResultSet resultSet = statement.executeQuery(sql);
						if(resultSet.next()){
							JOptionPane.showMessageDialog(frame, "�÷����Ѵ���..", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						}else{
							sql = "insert into room values('"
									+ roomTextField.getText().trim() + "','" 
									+ priceValueLabel.getText().trim() + "','" 
									+ addressComboBox.getSelectedItem().toString() + "','" 
									+ rtypeidV.get(roomTypeComboBox.getSelectedIndex()) + "','" 
									+ stateComboBox.getSelectedItem().toString() + "','" 
									+ roomTextField.getText().trim() + "')";
							Sunsql.upData(sql);
							dispose();
						}
						resultSet.close();
						statement.close();
						
					} catch (SQLException exception) {
						// TODO �Զ����ɵ� catch ��
						exception.printStackTrace();
					}
					Sunsql.closeConnection();
					
				}
				
				
				
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
		add(roomTextField);
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
		
	}
	
	//�������
	public void setCom(){
		Font font = new Font("����", Font.BOLD, 20);
		roomLabel.setFont(font);
		roomTextField.setFont(font);
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
		
		roomLabel.setBounds(50, 50, 150, 30);
		roomTextField.setBounds(130, 50, 200, 30);
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
		priceValueLabel.setForeground(new Color(154,18,179));
		finishButton.setBorderPainted(false);
		finishButton.setFocusPainted(false);
		finishButton.setForeground(Color.white);
	}
	
	
}
