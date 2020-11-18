package ca.java.exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ca.java.exercise.connection.DBConstants;

public class Driver {
	
	public static final String ALL_DEP_QUERY = "select * from department";
	public static final String ALL_EMP_QUERY = "select * from employee";
	
	private static Connection getConnection() throws SQLException {		
		return DriverManager.getConnection(DBConstants.CONN_STRING, DBConstants.USER, DBConstants.PASSWORD);
	}

	private static ResultSet getQuery(Connection conn, String query) throws SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(query);
	}
	
	public static void main(String[] args) throws SQLException {
		
		Connection conn = null;
		Statement stmt = null;
//		ResultSet rs = null;
		
		try {
			conn = getConnection();
//					DriverManager.getConnection(DBConstants.CONN_STRING, DBConstants.USER, DBConstants.PASSWORD);
//			stmt = conn.createStatement();
			ResultSet rsDep = getQuery(conn, ALL_DEP_QUERY);
//					stmt.executeQuery("select * from department");
			while(rsDep.next()) {
				System.out.println(rsDep.getInt("dep_id") + ": " + rsDep.getString("dep_name"));
			}
			
//			stmt = conn.createStatement();
//			stmt.executeUpdate("INSERT INTO EMPLOYEE (emp_fname, emp_lname, start_date, dep_id)"
//					+ "VALUES ('John','Doe','2020-08-08',1)");
			
			ResultSet rsEmp = getQuery(conn, ALL_EMP_QUERY);
			while(rsEmp.next()) {
				System.out.println(rsEmp.getString("emp_fname"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
//			if (rsDep != null) {
//				rsDep.close();
//			}
			
			if (stmt != null) {
				stmt.close();
			}
			
			if (conn != null) {
				conn.close();
			}
		}
	}




}
