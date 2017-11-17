import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;



public class adminPopUp extends JFrame{
	
	//Create connection to DatabaseAccessObject/DB class
	DatabaseAccessObject dao = new DatabaseAccessObject();
	Connection myConn = dao.myConn;
	
	
	public static void main(String[] args)
	{
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				adminPopUp frame = new adminPopUp();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}

	public adminPopUp()
	{
		setTitle("Admin");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		final JTextField uIDText = new JTextField();
		final JTextField aNameText = new JTextField();
		final JTextField numOfSeatsText = new JTextField();
		final JTextField flightID = new JTextField();
		final JTextField archiveDate = new JTextField("YYYY-MM-DD");
		final JTextField reservedAge = new JTextField();
		final JTextField canceledAge = new JTextField();
		final JTextField cencellationQuantity = new JTextField();
		

		JButton deleteUserButton = new JButton("Delete User");
		JButton addFlightButton = new JButton("Add Flight");
		JButton showUsersButton = new JButton("Current Users");
		JButton showFlightsButton = new JButton("Current Flights");
		JButton reserveButton = new JButton("Reserve Flight");
		JButton deleteFlightButton = new JButton("Delete Flight");
		JButton archiveButton = new JButton("Archive Reservation");
		JButton reservationAge = new JButton("Reservation Statistics");
		JButton cancellationAge = new JButton("Cancellation Statistics");
		JButton loyalCustomer = new JButton("Loyal Customers");
		JButton reservationAverageAge = new JButton("AvgAge of users with reservation");
		JButton numUsercancellations = new JButton("Users with given # of cancellations");
		
		JPanel northPanel = new JPanel();
//		northPanel.setLayout(new GridLayout(1,3));
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
//		northPanel.add(new JLabel("uID: ", SwingConstants.LEFT));
		northPanel.add(new JLabel("uID: "));
		northPanel.add(uIDText);
		northPanel.add(deleteUserButton);

		northPanel.add(new JLabel("                                                  "));
		
		northPanel.add(new JLabel("Airline Name: "));
		northPanel.add(aNameText);
		northPanel.add(new JLabel("Number of Seats: "));
		northPanel.add(numOfSeatsText);
		northPanel.add(addFlightButton);
		
		northPanel.add(new JLabel("                                                  "));
		
		northPanel.add(new JLabel("Flight ID: "));
		northPanel.add(flightID);
		northPanel.add(deleteFlightButton);
		
		northPanel.add(new JLabel("                                                  "));
		
		northPanel.add(new JLabel("Enter Date to archive: "));
		northPanel.add(archiveDate);
		northPanel.add(archiveButton);
		
		northPanel.add(new JLabel("                                                  "));
		
		northPanel.add(new JLabel("Reservation with age greater than: "));
		northPanel.add(reservedAge);
		northPanel.add(reservationAge);
		
		northPanel.add(new JLabel("                                                  "));
		
		northPanel.add(new JLabel("Cancellation with age greater than: "));
		northPanel.add(canceledAge);
		northPanel.add(cancellationAge);
		
		
		northPanel.add(new JLabel("                                                  "));
		
		northPanel.add(new JLabel("Users with __ number of flight Cancellation"));
		northPanel.add(cencellationQuantity);
		northPanel.add(numUsercancellations);
		
		
		
		JPanel southPanel = new JPanel();
//		southPanel.add(cancelButton);
		southPanel.add(showUsersButton);
		southPanel.add(showFlightsButton);
		southPanel.add(loyalCustomer);
		southPanel.add(reservationAverageAge);
		
		
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);

		//Delete user from DB
		deleteUserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				String uidvalue = uIDText.getText();
				try{
					PreparedStatement stmt = myConn.prepareStatement("delete from user where uid = ?");
					stmt.setString(1,uidvalue);
					stmt.executeUpdate();
					
					uIDText.setText("");
				}				
				catch(SQLException exc)
				{
					exc.printStackTrace();
				}
			}
		});
		//adding flights to database
		addFlightButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String aName = aNameText.getText();
				String numOfSeats = numOfSeatsText.getText();
				PreparedStatement stmt = null;
				try{
					stmt = myConn.prepareStatement("insert into flightList(aName,numSeats) values(?,?)");
					stmt.setString(1, aName);
					stmt.setString(2, numOfSeats);
					stmt.executeUpdate();
					
					aNameText.setText("");
					numOfSeatsText.setText("");
				}
				catch(SQLException exc){
					exc.printStackTrace();
				}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException e2){
						e2.printStackTrace();
					}
				}
			}
		});
		
		//Delete flights from Database
		deleteFlightButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String fid = flightID.getText();
				PreparedStatement stmt = null;
				try{
					stmt = myConn.prepareStatement("Delete from flightList where fid = ? ");
					stmt.setString(1, fid);
					stmt.executeUpdate();
					
					flightID.setText("");
				}
				catch(SQLException exc){
					exc.printStackTrace();
				}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException e2){
						e2.printStackTrace();
					}
				}
			}
		});
		
		
		//user's reservation with age older than ?
		reservationAge.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
				String getAge = reservedAge.getText();
				try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("User's reservation with age > __");
		             stmt =  myConn.prepareStatement("select * from user JOIN reservation using(uID) group by uid having(age > ?)");
		             stmt.setString(1, getAge);
		             ResultSet rs = stmt.executeQuery();
		             
		             reservedAge.setText(" ");
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String uName = rs.getString("uName");
		            String age = rs.getString("age");
		            String Query = uid +" "+ uName + " "+age;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);

		        }

		    } catch (SQLException e) {

		    	e.printStackTrace();
		    	}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		});
		
		//user's cancellation with age older than ?
		cancellationAge.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
				String cancelAge = canceledAge.getText();
				try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("User's cancellation with age > __");
		             stmt =  myConn.prepareStatement("select * from user JOIN canceledReservation using(uID) group by uid having(age > ?)");
		             stmt.setString(1, cancelAge);
		             ResultSet rs = stmt.executeQuery();
		             
		             canceledAge.setText(" ");
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String uName = rs.getString("uName");
		            String age = rs.getString("age");
		            String Query = uid +" "+ uName + " "+age;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);

		        }

		    } catch (SQLException e) {

		    	e.printStackTrace();
		    	}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		});
		
		//Users who have canceled more than ? reservation
		numUsercancellations.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
				String cancelQuantity = cencellationQuantity.getText();
				try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Users with more than __ cancellations");
		             stmt =  myConn.prepareStatement("Select uid,uname,age from user u1 where ? < (select count(*) from canceledreservation where u1.uid = uid group by uid)");
		             stmt.setString(1, cancelQuantity);
		             ResultSet rs = stmt.executeQuery();
		             
		             cencellationQuantity.setText(" ");
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String uName = rs.getString("uName");
		            String age = rs.getString("age");
		            String Query = uid +" "+ uName + " "+age;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);

		        }

		    } catch (SQLException e) {

		    	e.printStackTrace();
		    	}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		});

		
		
		//Archive reservation
		archiveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				CallableStatement cstmt = null;
				String date = archiveDate.getText();
				try { 
					String sql = "{call archivedReservation(?)}";
				
				cstmt = myConn.prepareCall(sql);
				cstmt.setString(1,date);
				cstmt.execute();
				
				archiveDate.setText(" ");
				}
				catch(SQLException exc)
				{
					exc.printStackTrace();
				}
				finally{
					try {
						cstmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		//Show current users
		showUsersButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
				try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Current Users");
		             stmt =  myConn.prepareStatement("Select * from user");
		             ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String uName = rs.getString("uName");
		            String age = rs.getString("age");
		            String Query = uid +" "+ uName + " "+age;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);

		        }

		    } catch (SQLException e) {

		    	e.printStackTrace();
		    	}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		});
		
		
		
		
		//Show current flights
		showFlightsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
		        try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Available Flights");
		             stmt =  myConn.prepareStatement("Select * from flightList");
		             ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            String fid = rs.getString("fid");
		            String aName = rs.getString("aName");
		            String numSeats = rs.getString("numSeats");
		            String Query = fid +" "+ aName + " "+numSeats;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);
		        }

		    } catch (SQLException e) {
		    	e.printStackTrace();
		    }
		        finally{
		        	try{
		        		stmt.close();
		        	}
		        	catch(SQLException e2){
		        		e2.printStackTrace();
		        	}
		        }
			}
		});
		
		//users with reservations who have not cancelled ANY FLIGHTS AT ALL
		loyalCustomer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
		        try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Customers with reservation and NO Cancellation");
		             stmt =  myConn.prepareStatement("select distinct uid from reservation where uid NOT IN (select uid from canceledreservation)");
		             ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String Query = uid;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);
		        }

		    } catch (SQLException e) {
		    	e.printStackTrace();
		    }
		        finally{
		        	try{
		        		stmt.close();
		        	}
		        	catch(SQLException e2){
		        		e2.printStackTrace();
		        	}
		        }
			}
		});
		
		//Average age of users who made a reservation
		reservationAverageAge.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
		        try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Average age of users with reservation");
		             stmt =  myConn.prepareStatement("select avg(age) as 'averageAge' from user U Natural join (select distinct uid from reservation group by uid) T");
		             ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            String uid = rs.getString("averageAge");
		            String Query = uid;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);
		        }

		    } catch (SQLException e) {
		    	e.printStackTrace();
		    }
		        finally{
		        	try{
		        		stmt.close();
		        	}
		        	catch(SQLException e2){
		        		e2.printStackTrace();
		        	}
		        }
			}
		});
		
		
		
		
		
	}

	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 800;
}
