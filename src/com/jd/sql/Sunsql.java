package com.jd.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Sunsql {
	
	private static final String DRIVERCLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://localhost:51325;DatabaseName=hotel";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "5606552";
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	private Sunsql(){
		
	}
	static{
		try {
			//ע������
			Class.forName(DRIVERCLASS);
		} catch (ClassNotFoundException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	//��ȡ���ݿ�����
	public static Connection getConnection() {	// �������ݿ����ӵķ���
        Connection conn = threadLocal.get();	// ���߳��л�����ݿ�����
        if (conn == null) {// û�п��õ����ݿ�����
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// �����µ����ݿ�����
                threadLocal.set(conn);			// �����ݿ����ӱ��浽�߳���
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
	//�ر����ݿ�����
	public static boolean closeConnection() {	// �ر����ݿ����ӵķ���
        boolean isClosed = true;
        Connection conn = threadLocal.get();	// ���߳��л�����ݿ�����
        threadLocal.set(null);					// ����߳��е����ݿ�����
        if (conn != null) {						// ���ݿ����ӿ���
            try {
                conn.close();					// �ر����ݿ�����
            } catch (SQLException e) {
                isClosed = false;
                e.printStackTrace();
            }
        }
        return isClosed;
    }
	//���ݿ�ʵ�ֲ��룬ɾ�������Ĳ���
	public static void upData(String sql){
		Connection conn = threadLocal.get();
		Statement statement;
		if(conn != null){
			try {
				statement = conn.createStatement();
				statement.executeUpdate(sql);
				statement.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}else{
			try {
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				statement = conn.createStatement();
				statement.executeUpdate(sql);

				statement.close();
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		closeConnection();
	}
}
