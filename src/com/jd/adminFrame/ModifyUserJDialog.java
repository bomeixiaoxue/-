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

//�޸��û���Ϣ�Ի���
public class ModifyUserJDialog extends JDialog{
	JLabel userid,name,idCard,phone;
	JTextField nameTextField,idCardTextField,phoneTextField;
	JButton finishButton;
	Vector<String> vector;
	
	public ModifyUserJDialog(JFrame frame,String title,boolean bool,Vector<String> v){
		super(frame,title,bool);
		setLayout(null);
		vector = v;
		
		userid = new JLabel("�˺�:     " + vector.get(0));
		name = new JLabel("����: ");
		idCard = new JLabel("���֤: ");
		phone = new JLabel("��ϵ��ʽ: ");
		nameTextField = new JTextField(15);
		idCardTextField = new JTextField(15);
		phoneTextField = new JTextField(15);
		finishButton = new JButton("���");
		
		//�������
		setCom();
		
		//��ɰ�ť�¼�
		finishButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO �Զ����ɵķ������
				String sql = "update userInformation set "
						+ "name='"+ nameTextField.getText().trim() +"',"
						+ "idCard='"+ idCardTextField.getText().trim() +"',"
						+ "phone='"+ phoneTextField.getText().trim() +"',"
						+ "type='"+ vector.get(4) +"'  "
						+ "where userid='"+ vector.get(0) +"'";
				Sunsql.upData(sql);
				JOptionPane.showMessageDialog(frame, "�޸ĳɹ�!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO �Զ����ɵķ������
				finishButton.setBackground(new Color(191,85,236));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO �Զ����ɵķ������
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
	//�������
	public void setCom(){
		Font font = new Font("����", Font.BOLD, 20);
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
