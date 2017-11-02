import java.util.*;
import java.sql.*;


public class javaConnection{
public static void main(String[] args)
{
	Scanner in = new Scanner(System.in);
	try {
		System.out.println("Enter name");
		String name = in.nextLine();
		System.out.println("Enter age");
		int age = in.nextInt();

		// get a connection
		Connection mycon = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbctest?useSSL=true", "root","password");
		//Create a statement.
		Statement mystmt = mycon.createStatement();
		//Execute sql query
		PreparedStatement ps = mycon.prepareStatement("insert into user(name,age) values(?,?)");
		ps.setString(1,name); //1 = parameter 1, arg2 = value 
		ps.setInt(2,age); //2 = parameter 2, firstname = value
		ps.executeUpdate();
		 
		 ResultSet myRs = mystmt.executeQuery("select * from user");
		while (myRs.next()) {
			System.out.println(myRs.getString("name")+" , "+myRs.getString("age"));
		}
	}
	catch (Exception exc) {
	exc.printStackTrace();
	}
}};