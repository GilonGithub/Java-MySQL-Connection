package ca.java.sql.exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;
import java.sql.Date;

import ca.java.sql.exercise.connection.DbConstants;

public class Driver {
	
	private static final String ALL_DEP_QUERY = "SELECT * FROM Department";
	private static final String ALL_EMP_QUERY = "SELECT * FROM Employee";
	private static final String ADD_EMP_QUERY = 
			"INSERT INTO Employee(emp_fname, emp_lname, start_date, dep_id) VALUES(?,?,?,?)";
	private static final String UPDATE_EMP_QUERY = 
			"UPDATE Employee SET emp_lname = ? WHERE emp_id = ?";
	
	
	//method to connect to DB
	public static Connection getConnection() throws SQLException {
		Connection conn = DriverManager.
				getConnection(DbConstants.CONN_STRING, DbConstants.USER, DbConstants.PASSWORD);
		return conn;
	}
	
	//method to get data from DB
	public static ResultSet getData(Connection conn, String query) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;
	}
	
	//method to insert data to DB
	public static void addEmployee(Connection conn) throws SQLException {
		//In this method you can get all the data from user Scanner
		PreparedStatement prestmt = null;
		prestmt = conn.prepareStatement(ADD_EMP_QUERY);
		prestmt.setString(1, "Zhaleh");
		prestmt.setString(2, "Sojoodi");
		prestmt.setDate(3, Date.valueOf("2020-06-10"));
		prestmt.setInt(4, 1);
		prestmt.executeUpdate();
	}
	
	//method to update employee last name
	public static void updateEmployee(Connection conn) throws SQLException {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the last name of the employee");
		String lname = input.nextLine();
		System.out.println("Enter the employee id which you want to update");
		int empId = input.nextInt();
		PreparedStatement prestmt = null;
		prestmt = conn.prepareStatement(UPDATE_EMP_QUERY);
		prestmt.setString(1, lname);
		prestmt.setInt(2, empId);
		prestmt.executeUpdate();
	}
	
	//method to print menu
	public static int printMenu() {
		Scanner input = new Scanner(System.in);
		System.out.println("Choose your option :");
		System.out.println("1- Get the department list");
		System.out.println("2- Get the employee list");
		System.out.println("3- Add a employee to the list");
		System.out.println("4- Update the last name of the employee");
		return input.nextInt();
	}
	
	
	public static void main(String[] args) throws SQLException   {
		Connection conn = null;
		int option = printMenu();
		try {
			conn = getConnection();
			switch(option) {
			case 1:
				ResultSet rsDep = getData(conn, ALL_DEP_QUERY);
				while(rsDep.next()) {
					System.out.println(
							rsDep.getInt("dep_id") + " : " + rsDep.getString("dep_name"));
					}
				break;
			case 2:
				ResultSet rsEmp = getData(conn, ALL_EMP_QUERY);
				while(rsEmp.next()) {
					System.out.println(rsEmp.getString("emp_fname") + " " + rsEmp.getString("emp_lname"));
				}
				break;
			case 3: 
				addEmployee(conn);
				break;
			case 4:
				updateEmployee(conn);
				break;
			default :
				System.out.println("The option is wrong !");
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.close();
			}
			
		}

	}

}