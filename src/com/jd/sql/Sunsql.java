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
			//注册驱动
			Class.forName(DRIVERCLASS);
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//获取数据库连接
	public static Connection getConnection() {	// 创建数据库连接的方法
        Connection conn = threadLocal.get();	// 从线程中获得数据库连接
        if (conn == null) {// 没有可用的数据库连接
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);// 创建新的数据库连接
                threadLocal.set(conn);			// 将数据库连接保存到线程中
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
	//关闭数据库连接
	public static boolean closeConnection() {	// 关闭数据库连接的方法
        boolean isClosed = true;
        Connection conn = threadLocal.get();	// 从线程中获得数据库连接
        threadLocal.set(null);					// 清空线程中的数据库连接
        if (conn != null) {						// 数据库连接可用
            try {
                conn.close();					// 关闭数据库连接
            } catch (SQLException e) {
                isClosed = false;
                e.printStackTrace();
            }
        }
        return isClosed;
    }
	//数据库实现插入，删除，更改操作
	public static void upData(String sql){
		Connection conn = threadLocal.get();
		Statement statement;
		if(conn != null){
			try {
				statement = conn.createStatement();
				statement.executeUpdate(sql);
				statement.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else{
			try {
				conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				statement = conn.createStatement();
				statement.executeUpdate(sql);

				statement.close();
			} catch (SQLException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		closeConnection();
	}
}
