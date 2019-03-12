package com.jd.userFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.jd.sql.Sunsql;

//��̬���
public class RoomStatePanel extends JPanel implements ActionListener{
	JPanel northPanel,centerPanel,centerNorthPanel,centerCenterPanel;
	RoomStateSouthPanel southPanel;
	SamllerRoomPanel samllerRoomPanel;
	static JComboBox<String> comboBoxIn,comboBoxOut;
	Vector<JButton> buttons;
	Vector<String> rooms;
	static JButton actionButton;
	ResultSet result = null;
	static int indexIN = 0;   //���������б���±�
	static int indexOut = 0;
	public RoomStatePanel(JFrame frame){
		setLayout(new BorderLayout());
		
		northPanel = new JPanel();
		centerPanel = new JPanel();
		centerNorthPanel = new JPanel();
		centerCenterPanel = new JPanel();
		southPanel = new RoomStateSouthPanel(frame);
		buttons = new Vector<JButton>();
		rooms = new Vector<String>();
//		actionButton = new JButton("���˷�");
		JLabel inTime = new JLabel("                  ���ʱ��:");
		JLabel outTime = new JLabel("                 ���ʱ��:");
		comboBoxIn = new JComboBox<>();
		comboBoxOut = new JComboBox<>();
		
		northPanel.setLayout(new GridLayout(1, getRow("roomtype")));
		centerPanel.setLayout(new BorderLayout());
		centerNorthPanel.setLayout(new GridLayout(1, 7));
		centerCenterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		northPanel.setPreferredSize(new Dimension(1150, 50));
		centerNorthPanel.setPreferredSize(new Dimension(1150, 30));
		southPanel.setPreferredSize(new Dimension(1150, 100));
		
		southPanel.setBackground(Color.gray);
		centerNorthPanel.setBackground(Color.gray);
		southPanel.validate();
		
		//��ʼ�������б�
		frameUtil.Time.setInOutTime(comboBoxIn, comboBoxOut);
		
		JLabel jLabel = new JLabel("    ��ɫ����Ԥ��");
		jLabel.setForeground(Color.red);
		centerNorthPanel.add(inTime);
		centerNorthPanel.add(comboBoxIn);
		centerNorthPanel.add(outTime);
		centerNorthPanel.add(comboBoxOut);
		centerNorthPanel.add(new JLabel());
		centerNorthPanel.add(new JLabel());
		centerNorthPanel.add(jLabel);
		
		centerPanel.add(centerNorthPanel,BorderLayout.NORTH);
		centerPanel.add(centerCenterPanel,BorderLayout.CENTER);
		centerPanel.invalidate();
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		//1 ������������ѡ��˵�����2 ��ȡ�����÷���ż��ϣ�3 ��ʼ�������������
		setMenu();
		actionButton = buttons.get(0);
		getNoRoom();
		setSmallerRoom("���˷�");
		
		
		comboBoxIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				indexIN = comboBoxIn.getSelectedIndex();
				indexOut = comboBoxOut.getSelectedIndex();
				int count = indexOut-indexIN;
				if(count < 0){
					comboBoxOut.setSelectedIndex(comboBoxIn.getSelectedIndex());
					count = 1;
				}
				//��ȡ����Ԥ�������
				getNoRoom();
				//���м������ӷ���
				setSmallerRoom(actionButton.getText());
				//���ݲ���Ԥ������
				southPanel.setRoom(rooms);
				//��ɾ���µ������۰�ť���ʶ����
				RoomStateSouthPanel.flag = false;
			}
		});
		comboBoxOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				indexIN = comboBoxIn.getSelectedIndex();
				indexOut = comboBoxOut.getSelectedIndex();
				int count = indexOut-indexIN;
				if(count < 0){
					JOptionPane.showMessageDialog(frame, "���ʱ�䲻��С�ڻ�������ʱ��", "��ܰ��ʾ", JOptionPane.INFORMATION_MESSAGE);
					comboBoxOut.setSelectedIndex(comboBoxIn.getSelectedIndex());
					count = 1;
				}
				getNoRoom();
				setSmallerRoom(actionButton.getText());
				southPanel.setRoom(rooms);
				
				//��ɾ���µ������۰�ť���ʶ����
				RoomStateSouthPanel.flag = false;
			}
		});
	}
	
	//�����������Ͳ˵���
	public void setMenu(){
		northPanel.removeAll();
		String sql = "select * from roomtype";
		
		Connection connection = Sunsql.getConnection();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()){
				buttons.add(new JButton(result.getString(2)));
				buttons.lastElement().setFocusPainted(false);
				buttons.lastElement().setBackground(new Color(210,215,211));
				northPanel.add(buttons.lastElement());
			}
			
			result.close();			// �رս��������
			statement.close();		// �ر�����״̬����
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		buttons.get(0).setBackground(new Color(211,84,0));
		/*
		 * ��ѡ������ť����¼�
		 */
		for(int i = 0; i < buttons.size(); i++){
			buttons.get(i).addActionListener(new  ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO �Զ����ɵķ������
					actionButton = (JButton) e.getSource();
					getNoRoom();
					setSmallerRoom(actionButton.getText());
					
					//��ɾ���µ������۰�ť���ʶ����
					RoomStateSouthPanel.flag = false;
					
					//���ð�ťѡ�кͲ�ѡ�е���ɫ
					actionButton.setBackground(new Color(211,84,0));
					for(JButton button: buttons){
						if(button != actionButton){
							button.setBackground(new Color(210,215,211));
						}
					}
				}
			});
		}
	}
	//��������������
	public int getRow(String table){
		int row = 0;
		String sql = "select count(*) from " + table;
		Connection connection = Sunsql.getConnection();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			result.next();
			row = result.getInt(1);
			
			result.close();			// �رս��������
			statement.close();		// �ر�����״̬����
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		return row;
	}
	
	//���м������ӷ���
	public void setSmallerRoom(String roomtype){
		centerCenterPanel.removeAll();
		centerCenterPanel.repaint();
		YuDingButton button = null;
		String sql = "select room.rid,room.rprice,room.rstate"
				     + " from room,roomtype "
					 + "where room.rtypeid=roomtype.rtypeid and roomtype.roomtype='" + roomtype +"'";
		Connection connection = Sunsql.getConnection();
		
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()){
				String roomID = result.getString(1);
				String roomPrice = result.getString(2).substring(0,result.getString(2).indexOf('.'));
				String state = result.getString(3);
				samllerRoomPanel = new SamllerRoomPanel();
				samllerRoomPanel.setLabel(roomID, roomPrice);
				button = new YuDingButton("Ԥ��");
				button.setBounds(0, 90, 120, 30);
				button.setRoomID(roomID);
				button.setBorderPainted(false);
				button.setFocusPainted(false);
				button.setBackground(new Color(191,191,191));
				button.addActionListener(this);
				//������䲻���ã������ñ�����ɫ��ɫ�Ҳ����Ԥ����ť
				if(!rooms.contains(roomID)){
					samllerRoomPanel.add(button);
				}else {
					samllerRoomPanel.setBackground(Color.red);
				}
				if(state.equals("����")){
					centerCenterPanel.add(samllerRoomPanel);
				}
				centerCenterPanel.validate();
			}
			
			result.close();			// �رս��������
			statement.close();		// �ر�����״̬����
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Sunsql.closeConnection();
		
	}
	//��ȡ����Ԥ���ķ���ż���
	public void getNoRoom() {
		rooms.removeAllElements();
		String inTime = comboBoxIn.getSelectedItem().toString();
		String outTime = comboBoxOut.getSelectedItem().toString();
		
		String number = null;
		String sql = "select rid,number from dbo.orderNum AS O "
				+ " where O.soleid not in (select soleid from dbo.orderNum "
										+ "where outtime<'" + inTime + "' "
										+ "or intime>'" + outTime + "') and state!='�������'";
		
		Connection connection = Sunsql.getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()){
				String rid = result.getString(1);
				number = result.getString(2).trim();
				if(number.equals("1")){
					rooms.add(rid);
				}else {
					int count = Integer.valueOf(number).intValue();
					String[] strings = new String[count];
					strings = rid.split(",");
					for(int i = 0; i < strings.length; i++){
						rooms.addElement(strings[i]);
					}
				}
			}
			
			result.close();			// �رս��������
			statement.close();		// �ر�����״̬����
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		Sunsql.closeConnection();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		YuDingButton yuDingButton = (YuDingButton) e.getSource();
		//�����������Ԥ���ķ����
		southPanel.addRoomID(yuDingButton.getRoomID());
		//ˢ�����
		southPanel.validate();
		
		//��ɾ���µ������۰�ť���ʶ����
		RoomStateSouthPanel.flag = false;
	}
}
