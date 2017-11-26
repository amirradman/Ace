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
		setTitle("Administrator");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		final JTextField uIDText = new JTextField();
		final JTextField aNameText = new JTextField();
		final JTextField numOfSeatsText = new JTextField();
		final JTextField flightID = new JTextField();
		final JTextField archiveDate = new JTextField();
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
		JButton numUsercancellations = new JButton("Users with more than __ cancellations");
		
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
		
		northPanel.add(new JLabel("Enter Date(YYYY-MM-DD) to archive: "));
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
		
		northPanel.add(new JLabel("Users with more than __  cancellation(s): "));
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
				String uidvalue = uIDText.getText().trim();
				PreparedStatement stmt = null;
				try{
					stmt = myConn.prepareStatement("delete from user where uid = ?");
					if(uidvalue.trim().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please provide User ID !");
					}
					else{
					stmt.setString(1,uidvalue);
					stmt.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "User deleted Successfully !");

					
					uIDText.setText("");
			}}	
				catch(SQLException exc)
				{
					JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
				}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException exc){
						JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
					}
				}
			}
		});
		//adding flights to database
		addFlightButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String aName = aNameText.getText().trim();
				String numOfSeats = numOfSeatsText.getText().trim();
				PreparedStatement stmt = null;
				try{
					stmt = myConn.prepareStatement("insert into flightList(aName,numSeats) values(?,?)",Statement.RETURN_GENERATED_KEYS);
					if(aName.trim().isEmpty() || numOfSeats.trim().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please provide airline name and number of seats !");
					}
					else{
					stmt.setString(1, aName);
					stmt.setString(2, numOfSeats);
					stmt.executeUpdate();
					
					ResultSet rs = stmt.getGeneratedKeys();
					if(rs.next())
					{
						JOptionPane.showMessageDialog(null, "Flight added Successfully. \n Flight id is: "+rs.getInt(1));
					}
					
					aNameText.setText("");
					numOfSeatsText.setText("");
				}}
				catch(SQLException exc){
					JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
				}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException exc){
						JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
					}
				}
			}
		});
		
		//Delete flights from Database
		deleteFlightButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String fid = flightID.getText().trim();
				PreparedStatement stmt = null;
				try{
					stmt = myConn.prepareStatement("Delete from flightList where fid = ? ");
					if(fid.trim().isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please provide flight ID !");
					}
					else{
					stmt.setString(1, fid);
					stmt.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "Flight deleted Successfully");
					
					flightID.setText("");
				}}
				catch(SQLException exc){
					JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
				}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException exc){
						JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
					}
				}
			}
		});
		
		
		//user's reservation with age older than ?
		reservationAge.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
				String getAge = reservedAge.getText().trim();
				try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("User's reservation with age > __");
		             stmt =  myConn.prepareStatement("select * from user JOIN reservation using(uID) group by uid having(age > ?)");
						if(getAge.trim().isEmpty())
						{
							JOptionPane.showMessageDialog(null, "Please provide the age !");
						}
						else{
		             stmt.setString(1, getAge);
		             ResultSet rs = stmt.executeQuery();
		             
		             reservedAge.setText(" ");
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String uName = rs.getString("uName");
		            String age = rs.getString("age");
		            String Query = "User ID | User Name | Age"+" => "+uid +" | "+ uName + " | "+age;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);

		        }}
		    } catch (SQLException exc) {
		    	JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
		    	}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException exc){
						JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
					}
				}
			}
		});
		
		//user's cancellation with age older than ?
		cancellationAge.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
				String cancelAge = canceledAge.getText().trim();
				try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("User's cancellation with age > __");
		             stmt =  myConn.prepareStatement("select * from user JOIN canceledReservation using(uID) group by uid having(age > ?)");
						if(cancelAge.trim().isEmpty())
						{
							JOptionPane.showMessageDialog(null, "Please provide the age !");
						}
						else{
		             stmt.setString(1, cancelAge);
		             ResultSet rs = stmt.executeQuery();
		             
		             canceledAge.setText(" ");
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String uName = rs.getString("uName");
		            String age = rs.getString("age");
		            String Query = "User ID | User Name | Age"+" => "+uid +" | "+ uName + " | "+age;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);

		        }}
		    } catch (SQLException exc) {

		    	JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
		    	}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException exc){
						JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
					}
				}
			}
		});
		
		//Users who have canceled more than ? reservation
		numUsercancellations.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
				String cancelQuantity = cencellationQuantity.getText().trim();
				try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Users with more than __ cancellations");
		             stmt =  myConn.prepareStatement("Select uid,uname,age from user u1 where ? < (select count(*) from canceledreservation where u1.uid = uid group by uid)");
						if(cancelQuantity.trim().isEmpty())
						{
							JOptionPane.showMessageDialog(null, "Please provide a value !");
						}
						else{
		             stmt.setString(1, cancelQuantity);
		             ResultSet rs = stmt.executeQuery();
		             
		             cencellationQuantity.setText(" ");
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String uName = rs.getString("uName");
		            String age = rs.getString("age");
		            String Query = "User ID | User Name | Age"+" => "+uid +" | "+ uName + " | "+age;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);

		        }}
		    } catch (SQLException exc) {
		    	JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
		    	}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException exc){
						JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
					}
				}
			}
		});

		
		
		//Archive reservation
		archiveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				CallableStatement cstmt = null;
				String date = archiveDate.getText().trim();
				try { 
					String sql = "{call archivedReservation(?)}";
				
				cstmt = myConn.prepareCall(sql);
				if(date.trim().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Please provide a date, formated 'YYYY-MM-DD' ");
				}
				else{
				cstmt.setString(1,date);
				cstmt.execute();
				
				JOptionPane.showMessageDialog(null, "Data archived successfully");

				
				archiveDate.setText(" ");
				}}
				catch(SQLException exc)
				{
					JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
				}
				finally{
					try {
						cstmt.close();
					} catch (SQLException exc) {
						JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
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
		        if(!rs.isBeforeFirst())
		        	JOptionPane.showMessageDialog(null, "There are currently no users");
				else{
					while (rs.next()) {
						String uid = rs.getString("uid");
						String uName = rs.getString("uName");
						String age = rs.getString("age");
						String Query = "User ID | User Name | Age"+" => "+uid +" | "+ uName + " | "+age;
						choices.addItem(Query);
						frame.add(choices);
						frame.setSize(350, 150);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);

					}}

		    } catch (SQLException exc) {
		    	JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
		    	}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException exc){
						JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
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
				if(!rs.isBeforeFirst())
					JOptionPane.showMessageDialog(null, "There are currently no flights");
				else{
					while (rs.next()) {
						String fid = rs.getString("fid");
						String aName = rs.getString("aName");
						String numSeats = rs.getString("numSeats");
						String Query = "Flight ID | Airline Name | Seats"+" => "+fid +" | "+ aName + " | "+numSeats;
						choices.addItem(Query);
						frame.add(choices);
						frame.setSize(350, 150);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
		        }}

		    } catch (SQLException exc) {
		    	JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
		    }
		        finally{
		        	try{
		        		stmt.close();
		        	}
		        	catch(SQLException exc){
		        		JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
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
		        if(!rs.isBeforeFirst())
		        	JOptionPane.showMessageDialog(null, "There are currently no loyal customers");
				else{
					while (rs.next()) {
						String uid = rs.getString("uid"); 
						String Query = "user ID => "+uid;
						choices.addItem(Query);
						frame.add(choices);
						frame.setSize(350, 150);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
		        }}

		    } catch (SQLException exc) {
		    	JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
		    }
		        finally{
		        	try{
		        		stmt.close();
		        	}
		        	catch(SQLException exc){
		        		JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
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
		             boolean empty = true;
					while (rs.next()) {
						if(empty)
				        	JOptionPane.showMessageDialog(null, "There are currently no users with reservation");
						else{
						String averageAge = rs.getString("averageAge");
						String Query = "Average age => "+averageAge;
						choices.addItem(Query);
						frame.add(choices);
						frame.setSize(350, 150);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
		        }}
		    } catch (SQLException exc) {
		    	JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
		    }
		        finally{
		        	try{
		        		stmt.close();
		        	}
		        	catch(SQLException exc){
		        		JOptionPane.showMessageDialog(null, "An error occured. Error # => "+exc.getErrorCode());
		        	}
		        }
			}
		});
	}

	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 800;
}
