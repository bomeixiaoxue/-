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

//ע�����
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
		font = new Font("����", Font.BOLD, 25);
		finishButton = new JButton("���");
		
		userid = new JLabel("�˺ţ�");   
		password = new JLabel("���룺");
		confirmPassword = new JLabel("ȷ�����룺");
		name = new JLabel("������");
		idCard = new JLabel("���֤��");
		phone = new JLabel("��ϵ��ʽ��");
		
		userTextField = new JTextField(15);
		nameTextField = new JTextField();
		idCardTextField = new JTextField();
		phoneTextField= new JTextField();
		passwordField = new JPasswordField(15);
		confirmPasswordField = new JPasswordField();
		
		//�������
		setCom();
		//����¼�
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
		//����¼�
		finishButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				String userID = userTextField.getText().trim();
				String name = nameTextField.getText().trim();
				String idc = idCardTextField.getText().trim();
				String phone = phoneTextField.getText().trim();
				String pass = String.valueOf(passwordField.getPassword());
				String cpass = String.valueOf(confirmPasswordField.getPassword());
				
				if(userID.equals("") || name.equals("") || idc.equals("") 
						|| phone.equals("") || pass.equals("") || cpass.equals("")){
					JOptionPane.showMessageDialog(frame, "������Ϣ������Ϊ��...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}else{
					String sql = "select * from client where userid='" + userID + "'";
					Connection connection = Sunsql.getConnection();
					try {
						Statement statement = connection.createStatement();
						ResultSet result = statement.executeQuery(sql);
						
						if(result.next()){
							JOptionPane.showMessageDialog(frame, "���û��Ѵ���...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						}else {
							if(pass.equals(cpass)){
								sql = "insert into client values('" + userID+ "','" + Encryption.MD5(pass) + "','" + type +"')";
								statement.executeUpdate(sql);
								sql = "insert into userInformation values('" + userID + "','" + name + "','" + idc + "','" + phone + "','" + type +"')";
								statement.executeUpdate(sql);
								JOptionPane.showMessageDialog(frame, "ע��ɹ�...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}else{
								JOptionPane.showMessageDialog(frame, "������ȷ�����벻ͬ...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
							}
						}
						result.close();			// �رս��������
						statement.close();		// �ر�����״̬����
					} catch (SQLException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
					Sunsql.closeConnection();
				}
			}
		});
		
		InitFrame.initFrame(this, 430, 570);
	}
	//�������
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
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
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
		// TODO �Զ����ɵķ������
		
	}
}
