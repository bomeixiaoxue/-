package com.jd.adminFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.jd.sql.Sunsql;

import frameUtil.InitFrame;

//修改用户信息对话框
public class ModifyUserJDialog extends JDialog{
	JLabel userid,name,idCard,phone;
	JTextField nameTextField,idCardTextField,phoneTextField;
	JButton finishButton;
	Vector<String> vector;
	
	public ModifyUserJDialog(JFrame frame,String title,boolean bool,Vector<String> v){
		super(frame,title,bool);
		setLayout(null);
		vector = v;
		
		userid = new JLabel("账号:     " + vector.get(0));
		name = new JLabel("姓名: ");
		idCard = new JLabel("身份证: ");
		phone = new JLabel("联系方式: ");
		nameTextField = new JTextField(15);
		idCardTextField = new JTextField(15);
		phoneTextField = new JTextField(15);
		finishButton = new JButton("完成");
		
		//设置组件
		setCom();
		
		//完成按钮事件
		finishButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 自动生成的方法存根
				String sql = "update userInformation set "
						+ "name='"+ nameTextField.getText().trim() +"',"
						+ "idCard='"+ idCardTextField.getText().trim() +"',"
						+ "phone='"+ phoneTextField.getText().trim() +"',"
						+ "type='"+ vector.get(4) +"'  "
						+ "where userid='"+ vector.get(0) +"'";
				Sunsql.upData(sql);
				JOptionPane.showMessageDialog(frame, "修改成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO 自动生成的方法存根
				finishButton.setBackground(new Color(191,85,236));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO 自动生成的方法存根
				finishButton.setBackground(new Color(154,18,179));
			}
		});
		
		add(userid);
		add(name);
		add(idCard);
		add(phone);
		add(nameTextField);
		add(idCardTextField);
		add(phoneTextField);
		add(finishButton);
		InitFrame.initFrame(this, 500, 500);
	}
	//设置组件
	public void setCom(){
		Font font = new Font("宋体", Font.BOLD, 20);
		userid.setFont(font);
		name.setFont(font);
		nameTextField.setFont(font);
		idCard.setFont(font);
		idCardTextField.setFont(font);
		phone.setFont(font);
		phoneTextField.setFont(font);
		finishButton.setFont(font);
		
		userid.setBounds(100, 30, 200, 30);
		name.setBounds(100, 110, 200, 30);
		nameTextField.setBounds(210, 110, 200, 30);
		idCard.setBounds(100, 190, 200, 30);
		idCardTextField.setBounds(210, 190, 200, 30);
		phone.setBounds(100, 280, 200, 30);
		phoneTextField.setBounds(210, 280, 200, 30);
		finishButton.setBounds(150, 400, 200, 30);
		finishButton.setForeground(Color.WHITE);
		finishButton.setFocusPainted(false);
		finishButton.setBackground(new Color(154,18,179));
		finishButton.setBorderPainted(false);
		
		nameTextField.setText(vector.get(1));
		idCardTextField.setText(vector.get(2));
		phoneTextField.setText(vector.get(3));
		
	}
	
}
