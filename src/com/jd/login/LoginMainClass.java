package com.jd.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jd.adminFrame.AdminFrame;
import com.jd.sql.Sunsql;
import com.jd.userFrame.UserFrame;

import frameUtil.Encryption;
import frameUtil.InitFrame;

//登录窗口
public class LoginMainClass extends JFrame implements MouseListener,KeyListener{
	JLabel userLabel,passwordLabel,loginLabel,exitLabel,imageLabel;
	JTextField textField;
	JPasswordField passwordField;
	JButton loginButton,exitButton,enrollButton;
	JPanel northPanel,southPanel;
	ImageIcon imageIcon;
	Color color;
	public static String USER = null;
	public static String PASSWORD = null;
	public static String TYPE = null;
	public static String TYPE1 = "用户"; 	//用户登录
	public static String TYPE2 = "管理员";	//管理员登录
	public LoginMainClass() {
		northPanel = new JPanel();
		southPanel = new JPanel();
		
		userLabel = new JLabel("用户名：");
		passwordLabel = new JLabel("密  码：");
		loginLabel = new JLabel("   登录");
		exitLabel = new JLabel("   退出");
		
		textField = new JTextField(20);
		passwordField = new JPasswordField(20);
		passwordField.setEchoChar('*');
		
		loginButton = new JButton();
		exitButton = new JButton();
		enrollButton = new JButton("<<新用户注册");
		//设置组件位置
		setLocaltion();
		
		//添加事件
		loginButton.addMouseListener(this);
		exitButton.addMouseListener(this);
		enrollButton.addMouseListener(this);
		textField.addKeyListener(this);
		passwordField.addKeyListener(this);
		//添加面板
		getContentPane().add(northPanel);
		getContentPane().add(southPanel);
		//给上面板添加图片
		imageIcon = new ImageIcon("src/image/jiarujd.jpg");
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(northPanel.getWidth(),northPanel.getHeight(),Image.SCALE_DEFAULT));
		imageLabel = new JLabel();
		imageLabel.setIcon(imageIcon);
		imageLabel.setBounds(0, 0, 500, 150);
		northPanel.add(imageLabel);
		//添加用户名和密码两行
		southPanel.add(userLabel);
		southPanel.add(passwordLabel);
		southPanel.add(textField);
		southPanel.add(passwordField);
		//添加登录退出按钮一行
		loginButton.add(loginLabel,JButton.CENTER);
		exitButton.add(exitLabel,JButton.CENTER);
		southPanel.add(loginButton);
		southPanel.add(exitButton);
		//添加注册
		southPanel.add(enrollButton);
		//去掉边框
		this.setUndecorated(true);
		//初始化窗体
		InitFrame.initFrame(this, 500, 400);
		//窗口大小不可变
		this.setResizable(false);
	}
	//设置组件位置
	public void setLocaltion(){
		this.setLayout(null);
		southPanel.setLayout(null);
		northPanel.setLayout(null);
		northPanel.setBounds(0, 0, 500, 150);
		southPanel.setBounds(0, 200, 500, 250);
		userLabel.setBounds(90, 5, 100, 30);
		passwordLabel.setBounds(90, 50, 100, 30);
		textField.setBounds(205, 5, 175, 30);
		passwordField.setBounds(205, 50, 175, 30);
		loginButton.setBounds(80, 115, 120, 30);
		exitButton.setBounds(300, 115, 120, 30);
		enrollButton.setBounds(380, 180, 150, 20);
		//设置字体样式
		userLabel.setFont(new Font("宋体", Font.BOLD, 20));
		passwordLabel.setFont(new Font("宋体", Font.BOLD, 20));
		textField.setFont(new Font("黑体", Font.BOLD, 17));
		passwordField.setFont(new Font("黑体", Font.BOLD, 17));
		loginLabel.setFont(new Font("宋体", Font.BOLD, 15));
		exitLabel.setFont(new Font("宋体", Font.BOLD, 15));
		loginLabel.setForeground(Color.WHITE);
		exitLabel.setForeground(Color.WHITE);
		loginButton.setBackground(new Color(25,181,254));
		exitButton.setBackground(new Color(25,181,254));
		loginButton.setBorderPainted(false);
		exitButton.setBorderPainted(false);
		enrollButton.setBorderPainted(false);
		enrollButton.setFocusPainted(false);
	}
	
	public void login(){
		USER = textField.getText().trim();
		PASSWORD = String.valueOf(passwordField.getPassword());
		//对密码加密下
		String password = Encryption.MD5(PASSWORD);
		Connection connection = Sunsql.getConnection();
		String sql = "select * from client where userid='" + USER +"' and password='" + password + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			if(result.next()){
				this.setVisible(false);
				TYPE = result.getString(3);
				if(TYPE.equals(TYPE1)){
					UserFrame userFrame = new UserFrame();
					userFrame.validate();
				}else if(TYPE.equals(TYPE2)){
					AdminFrame adminFrame = new AdminFrame();
					adminFrame.validate();
				}
				
			}else{
				JOptionPane.showMessageDialog(this, "用户名或密码错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
			result.close();			// 关闭结果集对象
			statement.close();		// 关闭连接状态对象
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Sunsql.closeConnection();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JButton button = (JButton) e.getSource();
		if(button == loginButton){
			login();
		}
		else if(button == exitButton){
			System.exit(0);
		}else if(button == enrollButton){
			new EnrollDialog(this,"注册",true,"用户");
		}
			
	}
	/*
	 * 鼠标事件
	*/
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton) e.getSource();
		color = button.getBackground();
		button.setBackground(new Color(137,196,244));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		JButton button = (JButton) e.getSource();
		button.setBackground(color);
	}
	/*
	 * 键盘事件
	*/
	@Override
	public void keyTyped(KeyEvent e) {}
	//实现鼠标焦点切换
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自动生成的方法存根
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ENTER){
			if(e.getSource() == textField){
				passwordField.requestFocus(true);  //将焦点从用户名给密码
			}
			else if(passwordField.getPassword().length > 0){
				login();
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	public static void main(String[] args) {
		new LoginMainClass();
	}
}
