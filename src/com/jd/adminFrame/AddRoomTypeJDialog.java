package com.jd.adminFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.jd.sql.Sunsql;

//�޸ķ������ͶԻ���
public class AddRoomTypeJDialog extends JDialog{
	Vector<String> roomTypeV;
	JLabel roomTypeIDLabel,roomTypeLabel,priceLabel;
	JTextField roomTypeTextField,priceTextField;
	JButton finishButton;
	public AddRoomTypeJDialog(JFrame frame,String title,boolean boo,int row){
		super(frame,title,boo);
		setLayout(null);
		roomTypeV = new Vector<>();
		//�����ݿ��ȡ����
		getData();
//		roomTypeIDLabel = new JLabel("�������ͱ��: " + String.valueOf(row+1));
		roomTypeIDLabel = new JLabel("�������ͱ��: " + (roomTypeV.size()+1));
		roomTypeLabel = new JLabel("��������: ");
		roomTypeTextField = new JTextField(15);
		priceLabel = new JLabel("�۸�: ");
		priceTextField = new JTextField(15);
		finishButton = new JButton("���");
		
		//�������
		setCom();
		/*
		 * �¼�
		 */
		finishButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				int rtypeid = row+1;
				String roomType = roomTypeTextField.getText().trim();
				String price = priceTextField.getText().trim();
				//�ж������Ƿ��Ѿ�������
				if(!roomType.equals("") && !price.equals("")){
					if(!roomTypeV.contains(roomType) ){
						String sql = "insert into roomtype values('"
								+ rtypeid +"','"+ roomType +"','"+ Integer.parseInt(price) +"')";
						Sunsql.upData(sql);
						dispose();
					}else{
						JOptionPane.showMessageDialog(frame, "��������ظ���������...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(frame, "��Ϣ����Ϊ��...", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		roomTypeTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO �Զ����ɵķ������
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					priceTextField.requestFocus();
				}
			}
			
		});
		priceTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// TODO �Զ����ɵķ������
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					finishButton.requestFocus();
				}
			}
		});
		add(roomTypeIDLabel);
		add(roomTypeLabel);
		add(roomTypeTextField);
		add(priceLabel);
		add(priceTextField);
		add(finishButton);
		frameUtil.InitFrame.initFrame(this, 500, 500);
	}
	//�������
	public void setCom(){
		Font font = new Font("����", Font.BOLD, 20);
		roomTypeIDLabel.setFont(font);
		roomTypeLabel.setFont(font);
		roomTypeTextField.setFont(font);
		priceLabel.setFont(font);
		priceTextField.setFont(font);
		finishButton.setFont(font);
		
		roomTypeIDLabel.setBounds(50, 50, 300, 30);
		roomTypeLabel.setBounds(90, 150, 150, 30);
		roomTypeTextField.setBounds(200, 150, 200, 30);
		priceLabel.setBounds(130, 250, 150, 30);
		priceTextField.setBounds(200, 250, 200, 30);
		finishButton.setBounds(175, 350, 150, 30);
	}
	//�����ݿ��ȡ����
	public void getData(){
		String sql = "select roomtype from roomtype";
		Connection connection = Sunsql.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()){
				roomTypeV.add(resultSet.getString(1));
			}
			resultSet.close();
			statement.close();
			
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		
	}
}
