import java.util.*;
import java.io.IOException;
import java.sql.*;


public class DatabaseAccessObject {
	public Connection myConn;
	public DatabaseAccessObject(){
		
	try{
	 myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightReservation?useSSL=true","root","password");
	}
	catch(Exception exc){
		exc.printStackTrace();
	}
}}