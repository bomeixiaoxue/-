package com.jd.userFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jd.adminFrame.ModifyUserJDialog;
import com.jd.login.LoginMainClass;
import com.jd.sql.Sunsql;

//个人信息面板
public class PersonInformationPanel extends JPanel implements MouseListener{
	JLabel userid,name,idCard,phone;
	Vector<String> vector;
	JButton modifyInformation,resetPassword;
	Color color;
	JFrame frame;
	public PersonInformationPanel(JFrame frame){
		this.frame = frame;
		setLayout(null);
		vector = new Vector<>();
		color = new Color(25,181,254);
		userid = new JLabel("账号:      "+LoginMainClass.USER);
		name = new JLabel("");
		idCard = new JLabel("");
		phone = new JLabel("");
		modifyInformation = new JButton("修改信息");
		resetPassword = new JButton("重置密码");
	
		//设置组件
		setCom();
		
		add(userid);
		add(name);
		add(idCard);
		add(phone);
		add(modifyInformation);
		add(resetPassword);
		
		modifyInformation.addMouseListener(this);
		resetPassword.addMouseListener(this);
		
	}
	//设置组件
	public void setCom(){
		Font font = new Font("宋体", Font.BOLD, 20);
		userid.setFont(font);
		name.setFont(font);
		idCard.setFont(font);
		phone.setFont(font);
		modifyInformation.setFont(font);
		resetPassword.setFont(font);
		
		userid.setBounds(450, 60, 200, 30);
		name.setBounds(450, 140, 300, 30);
		idCard.setBounds(450, 220, 300, 30);
		phone.setBounds(450, 300, 300, 30);
		modifyInformation.setBounds(430, 380, 150, 30);
		resetPassword.setBounds(590, 380, 150, 30);
		modifyInformation.setFocusPainted(false);
		modifyInformation.setBorderPainted(false);
		modifyInformation.setBackground(color);
		modifyInformation.setForeground(Color.white);
		resetPassword.setFocusPainted(false);
		resetPassword.setBorderPainted(false);
		resetPassword.setBackground(color);
		resetPassword.setForeground(Color.white);
		
		getInfomation();
	}
	//获取登录用户的信息
	public void getInfomation(){
		vector.clear();
		vector.add(LoginMainClass.USER);
		Connection connection = Sunsql.getConnection();
		String sql = "select * from userInformation where userid='"+LoginMainClass.USER+"'";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				name.setText("姓名:      "+resultSet.getString(2));
				idCard.setText("身份证:    "+resultSet.getString(3));
				phone.setText("联系方式:  "+resultSet.getString(4));
				vector.add(resultSet.getString(2));
				vector.add(resultSet.getString(3));
				vector.add(resultSet.getString(4));
				vector.add(resultSet.getString(5));
			}
			resultSet.close();// 关闭结果集对象
			statement.close();// 关闭连接状态对象
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton) e.getSource();
		if(button == modifyInformation){
			new ModifyUserJDialog(frame,"修改个人资料",true,vector);
			getInfomation();
		}else if(button == resetPassword){
			new ResetPassword(frame,"重置密码",true);
			
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton) e.getSource();
		button.setBackground(new Color(107,185,240));
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton) e.getSource();
		button.setBackground(color);
	}
	
}
