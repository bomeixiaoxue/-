package com.jd.userFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.jd.login.LoginMainClass;
import com.jd.sql.Sunsql;

import frameUtil.InitFrame;

//���������
public class OrderJDialog extends JDialog{
	JLabel orderLabel,userLabel,roomNumLabel,numLabel,userName;
	JLabel inLabel,outLabel,priceLabel,stateLabel,roomType;
	JButton finishButton;
	Font font;
	ResultSet result = null;
	Vector<String> vector;
	Vector<Integer> rTypeIDV;
	BigDecimal bigDecimal = null;
	JTextField roomTextField,roomTypeTextField;
	String[] date = new String[30];
	public OrderJDialog(JFrame frame,String title,boolean mode,Vector<String> v){
		super(frame, title, mode);
		vector = v;
		rTypeIDV = new Vector<>();
		setLayout(null);
		font = new Font("����", Font.BOLD, 20);
		orderLabel = new JLabel("�����ţ�" + makeOrder());
		userLabel = new JLabel("�û�ID��" + LoginMainClass.USER);
		userName = new JLabel("������" + getName());
		roomNumLabel = new JLabel("����ţ�");
		numLabel = new JLabel("������" + vector.size());
		priceLabel = new JLabel("�۸�" + getTotalPrice());
		inLabel = new JLabel("���ʱ�䣺" + RoomStatePanel.comboBoxIn.getSelectedItem().toString());
		outLabel = new JLabel("���ʱ�䣺" + RoomStatePanel.comboBoxOut.getSelectedItem().toString());
		stateLabel = new JLabel("״̬��δ֧��");
		roomType = new JLabel("�������ͣ�");
		roomTypeTextField = new JTextField();
		finishButton = new JButton("�ύ����");
		roomTextField = new JTextField(50);
		//�������
		setcom();
		
		add(orderLabel);
		add(userLabel);
		add(userName);
		add(roomNumLabel);
		add(numLabel);
		add(inLabel);
		add(outLabel);
		add(priceLabel);
		add(stateLabel);
		add(finishButton);
		add(roomTextField);
		add(roomType);
		add(roomTypeTextField);
		//��ɶ�����ť
		finishButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				String orderID = makeOrder();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = simpleDateFormat.format(Calendar.getInstance().getTime());
				String sql = "insert into orderNum values('"
						+ makeOrder() +"','"
						+ LoginMainClass.USER +"','"
						+ getName() +"','"
						+ getRoom() +"','"
						+ vector.size() +"','"
						+ roomTypeTextField.getText().trim() +"','"
						+ RoomStatePanel.comboBoxIn.getSelectedItem().toString() +"','"
						+ RoomStatePanel.comboBoxOut.getSelectedItem().toString() +"','"
						+ getTotalPrice() +"','"
						+ time +"','δ֧��')";
				Connection connection = Sunsql.getConnection();
				try {
					Statement statement = connection.createStatement();
					statement.executeUpdate(sql);
					statement.close();// �ر�����״̬����
				} catch (SQLException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				Sunsql.closeConnection();
				
				System.out.println("�ύ�ɹ�....");
				/*
				 * �ύ�ɹ���Ҫ�ѷ��������µ����ķ��������ػ�
				 */
				UserFrame.roomStatePanel.getNoRoom();
				UserFrame.roomStatePanel.setSmallerRoom(RoomStatePanel.actionButton.getText());
				RoomStateSouthPanel.centerPane.removeAll();
				RoomStateSouthPanel.centerPane.repaint();
				vector.removeAllElements();
				JOptionPane.showMessageDialog(frame, "�ɹ��µ�...", "��ܰ��ʾ", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		//��ʼ������
		this.setResizable(false);
		InitFrame.initFrame(this, 800, 400);
	}
	//�������
	public void setcom(){
		orderLabel.setBounds(60, 20, 400, 20);
		userLabel.setBounds(420, 20, 300, 20);
		userName.setBounds(60, 70,  200, 20);
		roomNumLabel.setBounds(420, 70, 100, 20);
		numLabel.setBounds(60, 120, 100, 20);
		priceLabel.setBounds(420, 120, 300, 20);
		inLabel.setBounds(60, 170, 450, 20);
		outLabel.setBounds(420, 170, 450, 20);
		stateLabel.setBounds(60, 220, 150, 20);
		roomType.setBounds(420, 220, 450, 20);
		roomTypeTextField.setBounds(520, 220, 250, 20);
		roomTypeTextField.setText(getRoomType());
		roomTypeTextField.setForeground(new Color(154,18,179));
		finishButton.setBounds(310, 290, 150, 35);
		
		roomTextField.setBounds(500, 70, 250, 20);
		roomTextField.setForeground(new Color(154,18,179));
		roomTextField.setHorizontalAlignment(JTextField.LEFT);
		//��ӷ����
		roomTextField.setText(getRoom());
		
		orderLabel.setFont(font);
		userLabel.setFont(font);
		userName.setFont(font);
		roomNumLabel.setFont(font);
		numLabel.setFont(font);
		inLabel.setFont(font);
		outLabel.setFont(font);
		roomType.setFont(font);
		roomTypeTextField.setFont(font);
		priceLabel.setFont(font);
		stateLabel.setFont(font);
		
		finishButton.setFont(font);
		finishButton.setForeground(Color.WHITE);
		finishButton.setBackground(new Color(154,18,179));
		finishButton.setFocusPainted(false);
		
		roomTextField.setFont(new Font("����", Font.BOLD, 16));
		roomTypeTextField.setFont(new Font("����", Font.BOLD, 16));
		roomTextField.setEditable(false);
		roomTypeTextField.setEditable(false);
	}
	//��ȡ���з��������
	public String getRoomType(){
		StringBuffer sBuffer = new StringBuffer();
		Connection connection = Sunsql.getConnection();
		for(int index : rTypeIDV){
			String sql = "select roomtype from roomtype where rtypeid='" + index + "'";
			try {
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery(sql);
				
				while(result.next()){
					sBuffer.append("," + result.getString(1).trim());
				}
				
				result.close();// �رս��������
				statement.close();// �ر�����״̬����
			} catch (SQLException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}
		Sunsql.closeConnection();
		sBuffer.deleteCharAt(0);
		return sBuffer.toString();
	}
	//��ȡ�û�����
	public String getName(){
		String name = null;
		String sql = "select name from userInformation where userid='"+ LoginMainClass.USER +"'";
		Connection connection = Sunsql.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			result.next();
			name = result.getString(1);
			
			result.close();// �رս��������
			statement.close();// �ر�����״̬����
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		Sunsql.closeConnection();
		return name;
	}
	//��ȡ�۸�
	public String getTotalPrice(){
		rTypeIDV.removeAllElements();
		int price = 0;
		Iterator<String> it = vector.iterator();
		String string = null;
		Connection connection = Sunsql.getConnection();
		Statement statement = null;
		ResultSet result = null;
		while(it.hasNext()){
			try {
				statement = connection.createStatement();
				result = statement.executeQuery("select rprice,rtypeid from room where rid = '" + it.next() + "'");
				
				result.next();
				string = result.getString(1);
				string = string.substring(0, string.indexOf('.'));
				price += Integer.valueOf(string);
				//˳���ȡ�·������ͱ��
				rTypeIDV.addElement(result.getInt(2));
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		try {
			result.close();			// �رս��������
			statement.close();		// �ر�����״̬����
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		return String.valueOf(price*((RoomStatePanel.indexOut-RoomStatePanel.indexIN)+1));
	}
	//��ȡȫ�������
	public String getRoom(){
		StringBuffer sBuffer = new StringBuffer();
		if(vector.size() == 1){
			sBuffer.append(vector.get(0));
		}else{
			int i = 0;
			for(; i < vector.size()-1; i++){
				sBuffer.append(vector.get(i) + ",");
			}
			sBuffer.append(vector.get(i));
		}
		return sBuffer.toString();
	}
	//��������
	public String makeOrder(){
		SimpleDateFormat sdfIn = new SimpleDateFormat("yyyyMMdd");
		String date = sdfIn.format(Calendar.getInstance().getTime());
		String sql = "select MAX(soleid) from orderNum where soleid like '" + date + "%'";
		Connection connection = Sunsql.getConnection();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			result.next();
			if(result.getString(1) != null){
				bigDecimal = new BigDecimal(result.getString(1));
				bigDecimal = bigDecimal.add(new BigDecimal("1"));
				date = bigDecimal.toString();
			}else{
				date = date + "001";
			}
			
			result.close();			// �رս��������
			statement.close();		// �ر�����״̬����
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		return date;
	}
}
