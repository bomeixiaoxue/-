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

//修改房间信息对话框
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
		roomLabel = new JLabel("房间号: ");
		priceLabel = new JLabel("价格: ");
		priceValueLabel = new JLabel();
		roomType = new JLabel("房间类型: ");
		phoneLabel = new JLabel("电话: ");
		addressLabel = new JLabel("位置: ");
		stateLabel = new JLabel("状态: ");
		
		roomTypeComboBox = new JComboBox<>();
		addressComboBox = new JComboBox<>();
		stateComboBox = new JComboBox<>();
		roomTextField = new JTextField(15);
		finishButton = new JButton("完成");
		
		//设置组件
		setCom();
		//设置下列列表
		setComboBox();
		priceValueLabel.setText(priceV.get(0) + ".0000");
		/*
		 * 事件
		 */
		//
		roomTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO 自动生成的方法存根
				phoneLabel.setText("电话: " + roomTextField.getText().trim());
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO 自动生成的方法存根
				
			}
		});
		//房间类型下列列表事件
		roomTypeComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				int index = roomTypeComboBox.getSelectedIndex();
				priceValueLabel.setText(priceV.get(index) + ".0000");
			}
		});
		//完成按钮事件
		finishButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				String rid = roomTextField.getText().trim();
				if(!rid.equals("")){
					String sql = "select rid from room where rid='" + rid + "'";
					Connection connection = Sunsql.getConnection();
					try {
						Statement statement = connection.createStatement();
						ResultSet resultSet = statement.executeQuery(sql);
						if(resultSet.next()){
							JOptionPane.showMessageDialog(frame, "该房间已存在..", "提示", JOptionPane.INFORMATION_MESSAGE);
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
						// TODO 自动生成的 catch 块
						exception.printStackTrace();
					}
					Sunsql.closeConnection();
					
				}
				
				
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				JButton button = (JButton) e.getSource();
				button.setBackground(new Color(191,85,236));
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
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
	//设置下列列表
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		
		addressComboBox.addItem("一楼");
		addressComboBox.addItem("二楼");
		addressComboBox.addItem("三楼");
		addressComboBox.addItem("四楼");
		addressComboBox.addItem("五楼");
		addressComboBox.addItem("六楼");
		addressComboBox.addItem("七楼");
		addressComboBox.addItem("八楼");
		
		stateComboBox.addItem("可用");
		stateComboBox.addItem("不可用");
		stateComboBox.addItem("维修中");
		
	}
	
	//设置组件
	public void setCom(){
		Font font = new Font("宋体", Font.BOLD, 20);
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
