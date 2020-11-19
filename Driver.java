package ca.java.exercise;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ca.java.exercise.connection.DBConstants;

/*
 * download jdbc driver from https://dev.mysql.com/downloads/connector/j/
 * OS: Platform independent (zip file)
 */
public class Driver {
	
	public static final String ALL_DEP_QUERY = "select * from department";
	public static final String ALL_EMP_QUERY = "select * from employee";
	public static final String INSERT_EMP = 
			"INSERT INTO EMPLOYEE (emp_fname, emp_lname, start_date, dep_id) VALUES (?,?,?,?)";
	
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
		ResultSet rsDep = null, rsEmp = null;
		
		try {
			conn = getConnection();
//					DriverManager.getConnection(DBConstants.CONN_STRING, DBConstants.USER, DBConstants.PASSWORD);
//			stmt = conn.createStatement();
			rsDep = getQuery(conn, ALL_DEP_QUERY);
//					stmt.executeQuery("select * from department");
			while(rsDep.next()) {
				System.out.println(rsDep.getInt("dep_id") + ": " + rsDep.getString("dep_name"));
			}
			
//			stmt = conn.createStatement();
//			stmt.executeUpdate("INSERT INTO EMPLOYEE (emp_fname, emp_lname, start_date, dep_id)"
//					+ "VALUES ('John','Doe','2020-08-08',1)");
			
			//INSERT USING PREPARED STATEMENT
			PreparedStatement pStmt = conn.prepareStatement(INSERT_EMP);
			pStmt.setString(1, "Kira");
			pStmt.setString(2, "Dora");
			pStmt.setDate(3, Date.valueOf("2020-09-10"));
			pStmt.setInt(4, 2);
			int result = pStmt.executeUpdate();
			System.out.println("Rows affected: " + result);
			
			//PRINT ALL EMPLOYEES
			rsEmp = getQuery(conn, ALL_EMP_QUERY);
			while(rsEmp.next()) {
				System.out.println(rsEmp.getString("emp_fname"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			if (rsDep != null) {
				rsDep.close();
			}
			
			if (rsEmp != null) {
				rsEmp.close();
			}
			
			if (stmt != null) {
				stmt.close();
			}
			
			if (conn != null) {
				conn.close();
			}
		}
	}




}
