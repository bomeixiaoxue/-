package com.jd.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jd.sql.Sunsql;

import frameUtil.Encryption;
import frameUtil.InitFrame;

//注册面板
public class EnrollDialog extends JDialog implements KeyListener{
	JLabel userid,password,confirmPassword,name,idCard,phone;
	JTextField userTextField,nameTextField,idCardTextField,phoneTextField;
	JPasswordField passwordField,confirmPasswordField;
	JButton finishButton;
	ResultSet result;
	Font font;
	public EnrollDialog(JFrame frame,String title,boolean mode,String type){
		super(frame,title,mode);
		setLayout(null);
		font = new Font("宋体", Font.BOLD, 25);
		finishButton = new JButton("完成");
		
		userid = new JLabel("账号：");   
		password = new JLabel("密码：");
		confirmPassword = new JLabel("确认密码：");
		name = new JLabel("姓名：");
		idCard = new JLabel("身份证：");
		phone = new JLabel("联系方式：");
		
		userTextField = new JTextField(15);
		nameTextField = new JTextField();
		idCardTextField = new JTextField();
		phoneTextField= new JTextField();
		passwordField = new JPasswordField(15);
		confirmPasswordField = new JPasswordField();
		
		//设置组件
		setCom();
		//添加事件
		userTextField.addKeyListener(this);
		passwordField.addKeyListener(this);
		confirmPasswordField.addKeyListener(this);
		nameTextField.addKeyListener(this);
		idCardTextField.addKeyListener(this);
		phoneTextField.addKeyListener(this);
		
		
		add(userid);
		add(userTextField);
		add(password);
		add(passwordField);
		add(confirmPassword);
		add(confirmPasswordField);
		add(name);
		add(nameTextField);
		add(idCard);
		add(idCardTextField);
		add(phone);
		add(phoneTextField);
		add(finishButton);
		//添加事件
		finishButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String userID = userTextField.getText().trim();
				String name = nameTextField.getText().trim();
				String idc = idCardTextField.getText().trim();
				String phone = phoneTextField.getText().trim();
				String pass = String.valueOf(passwordField.getPassword());
				String cpass = String.valueOf(confirmPasswordField.getPassword());
				
				if(userID.equals("") || name.equals("") || idc.equals("") 
						|| phone.equals("") || pass.equals("") || cpass.equals("")){
					JOptionPane.showMessageDialog(frame, "各个信息均不能为空...", "提示", JOptionPane.INFORMATION_MESSAGE);
				}else{
					String sql = "select * from client where userid='" + userID + "'";
					Connection connection = Sunsql.getConnection();
					try {
						Statement statement = connection.createStatement();
						ResultSet result = statement.executeQuery(sql);
						
						if(result.next()){
							JOptionPane.showMessageDialog(frame, "该用户已存在...", "提示", JOptionPane.INFORMATION_MESSAGE);
						}else {
							if(pass.equals(cpass)){
								sql = "insert into client values('" + userID+ "','" + Encryption.MD5(pass) + "','" + type +"')";
								statement.executeUpdate(sql);
								sql = "insert into userInformation values('" + userID + "','" + name + "','" + idc + "','" + phone + "','" + type +"')";
								statement.executeUpdate(sql);
								JOptionPane.showMessageDialog(frame, "注册成功...", "提示", JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}else{
								JOptionPane.showMessageDialog(frame, "密码与确认密码不同...", "提示", JOptionPane.INFORMATION_MESSAGE);
							}
						}
						result.close();			// 关闭结果集对象
						statement.close();		// 关闭连接状态对象
					} catch (SQLException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					Sunsql.closeConnection();
				}
			}
		});
		
		InitFrame.initFrame(this, 430, 570);
	}
	//设置组件
	public void setCom(){
		userid.setBounds(80, 30, 150, 30);
		userTextField.setBounds(160, 30, 200, 30);
		password.setBounds(80, 90, 150, 30);
		passwordField.setBounds(160, 90, 200, 30);
		confirmPassword.setBounds(30, 150, 150, 30);
		confirmPasswordField.setBounds(160, 150, 200, 30);
		name.setBounds(80, 210, 150, 30);
		nameTextField.setBounds(160, 210, 200, 30);
		idCard.setBounds(50, 270, 150, 30);
		idCardTextField.setBounds(160, 270, 200, 30);
		phone.setBounds(30, 330, 150, 30);
		phoneTextField.setBounds(160, 330, 200, 30);
		finishButton.setBounds(140, 430, 150, 30);
		
		userid.setFont(font);
		userTextField.setFont(font);
		password.setFont(font);
		passwordField.setFont(font);
		confirmPassword.setFont(font);
		confirmPasswordField.setFont(font);
		name.setFont(font);
		nameTextField.setFont(font);
		idCard.setFont(font);
		idCardTextField.setFont(font);
		phone.setFont(font);
		phoneTextField.setFont(font);
		finishButton.setFont(font);
		
		finishButton.setBackground(new Color(25,181,254));
		finishButton.setFocusPainted(false);
		finishButton.setForeground(Color.white);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自动生成的方法存根
		
		if(e.getSource() == userTextField && e.getKeyCode() == KeyEvent.VK_ENTER){
			passwordField.requestFocus();
		}else if(e.getSource() == passwordField && e.getKeyCode() == KeyEvent.VK_ENTER){
			confirmPasswordField.requestFocus();
		}else if(e.getSource() == confirmPasswordField && e.getKeyCode() == KeyEvent.VK_ENTER){
			nameTextField.requestFocus();
		}else if(e.getSource() == nameTextField && e.getKeyCode() == KeyEvent.VK_ENTER){
			idCardTextField.requestFocus();
		}else if(e.getSource() == idCardTextField && e.getKeyCode() == KeyEvent.VK_ENTER){
			phoneTextField.requestFocus();
		}else if(e.getSource() == phoneTextField && e.getKeyCode() == KeyEvent.VK_ENTER){
			finishButton.requestFocus();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}
}
