package com.jd.userFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jd.login.LoginMainClass;
import com.jd.sql.Sunsql;

import frameUtil.Encryption;
import frameUtil.InitFrame;

//��������Ի���
public class ResetPassword extends JDialog implements MouseListener,KeyListener{
	JLabel oldPasswordLabel,newPasswordLabel,confirmNewPaswordLabel;
	JTextField oldPasswordTextField;
	JPasswordField newPasswordFiled,confirmNewPaswordFiled;
	JButton finishButton;
	Font font;
	Color color;
	JFrame frame;
	public ResetPassword(JFrame frame,String title,boolean bool){
		super(frame,title,bool);
		setLayout(null);
		this.frame = frame;
		font = new Font("����", Font.BOLD, 25);
		color = new Color(154,18,179);
		
		oldPasswordLabel = new JLabel("ԭ����");
		newPasswordLabel = new JLabel("������");
		confirmNewPaswordLabel = new JLabel("ȷ��������");
		oldPasswordTextField = new JTextField(15);
		newPasswordFiled = new JPasswordField('*');
		confirmNewPaswordFiled = new JPasswordField('*');
		finishButton = new JButton("���");
		
		
		oldPasswordLabel.setBounds(80, 50, 150, 30);
		oldPasswordTextField.setBounds(200, 50, 200, 30);
		newPasswordLabel.setBounds(80, 150, 150, 30);
		newPasswordFiled.setBounds(200, 150, 200, 30);
		confirmNewPaswordLabel.setBounds(30, 250, 150, 30);
		confirmNewPaswordFiled.setBounds(200, 250, 200, 30);
		finishButton.setBounds(175, 370, 150, 30);
		
		
		oldPasswordLabel.setFont(font);
		newPasswordLabel.setFont(font);
		confirmNewPaswordLabel.setFont(font);
		oldPasswordTextField.setFont(font);
		newPasswordFiled.setFont(font);
		confirmNewPaswordFiled.setFont(font);
		finishButton.setFont(font);
		finishButton.setFocusPainted(false);
		finishButton.setBorderPainted(false);
		finishButton.setBackground(new Color(154,18,179));
		finishButton.setForeground(Color.WHITE);
		/*
		 * �¼�
		 * 
		 */
		finishButton.addMouseListener(this);
		oldPasswordTextField.addKeyListener(this);
		newPasswordFiled.addKeyListener(this);
		confirmNewPaswordFiled.addKeyListener(this);
		
		add(oldPasswordLabel);
		add(newPasswordLabel);
		add(confirmNewPaswordLabel);
		add(oldPasswordTextField);
		add(newPasswordFiled);
		add(confirmNewPaswordFiled);
		add(finishButton);
		this.setResizable(false);
		InitFrame.initFrame(this, 500, 500);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������
		if(oldPasswordTextField.getText().trim().equals("") 
				|| String.valueOf(newPasswordFiled.getPassword()).equals("") 
				|| String.valueOf(confirmNewPaswordFiled.getPassword()).equals("")){
			JOptionPane.showMessageDialog(frame, "��Ϣ����Ϊ��...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}else if(!oldPasswordTextField.getText().trim().equals(LoginMainClass.PASSWORD)){
			JOptionPane.showMessageDialog(frame, "ԭ�������...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}else if(!String.valueOf(newPasswordFiled.getPassword()).equals(String.valueOf(confirmNewPaswordFiled.getPassword()))){
			JOptionPane.showMessageDialog(frame, "�������ȷ�����벻ͬ...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}else{
			//��������޸�
			LoginMainClass.PASSWORD = String.valueOf(newPasswordFiled.getPassword());
			String password = Encryption.MD5(LoginMainClass.PASSWORD);
			String sql = "update client set password='"+password+"' where userid='"+LoginMainClass.USER+"'";
			
			Sunsql.upData(sql);
			JOptionPane.showMessageDialog(frame, "�޸ĳɹ�...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������
		finishButton.setBackground(new Color(191,85,236));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		finishButton.setBackground(color);
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO �Զ����ɵķ������
		if(e.getSource() == oldPasswordTextField 
				&& e.getKeyCode() == KeyEvent.VK_ENTER){
			newPasswordFiled.requestFocus();
		}else if(e.getSource() == newPasswordFiled 
				&& e.getKeyCode() == KeyEvent.VK_ENTER){
			confirmNewPaswordFiled.requestFocus();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
}
