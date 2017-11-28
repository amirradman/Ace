import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;

public class GeneralUser extends JFrame{
	
	//Create connection to DatabaseAccessObject/DB class
	DatabaseAccessObject dao = new DatabaseAccessObject();
	Connection myConn = dao.myConn;
		
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				GeneralUser frame = new GeneralUser();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
	}
	
	
	
	public GeneralUser()
	{
		setTitle("General User");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		final JTextField reservedDate = new JTextField();
		final JTextField nameText = new JTextField();
		final JTextField ageText = new JTextField();
		final JTextField uidReserve = new JTextField();
		final JTextField fidReserve = new JTextField();
		final JTextField aName = new JTextField();
		final JTextField seatNum = new JTextField();
		final JTextField uidCanceledReserve = new JTextField();
		final JTextField fidCanceledReserve = new JTextField();
		final JTextField viewReservation = new JTextField();
	
	
	
		JButton addUserButton = new JButton("Register");
		JButton reserveButton = new JButton("Reserve Flight");
		JButton findFlight = new JButton("Find Flight");
		JButton notReservedFlights = new JButton("Not Reserved Flights");
		JButton findHighSeason = new JButton("Find High Season(s)");
//		JButton showUsersButton = new JButton("Show Current Users");
		JButton flightswithseats = new JButton("Show flights with given seat number");
		JButton cancelReservationButton = new JButton("Cancel Reservation");
		JButton viewReservationButton = new JButton("View my reservation(s)");
	
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();

		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		
		//Create user using name and age fields + Button 
		northPanel.add(new JLabel("Name: "));
		northPanel.add(nameText);
		northPanel.add(new JLabel("Age: "));
		northPanel.add(ageText);
		northPanel.add(addUserButton);
		
		northPanel.add(new JLabel("                                                                  "));
		
		//Flight Reservation using uid and fid fields + button
		northPanel.add(new JLabel ("User ID: "));
		northPanel.add(uidReserve);
		northPanel.add(new JLabel ("Flight ID: "));
		northPanel.add(fidReserve);
		northPanel.add(reserveButton);
		northPanel.add(new JLabel("                                                                  "));


		//Find flights using airline Name fields + button
		northPanel.add(new JLabel ("AirLine Name: "));
		northPanel.add(aName);
		northPanel.add(findFlight);
		northPanel.add(new JLabel("                                                                  "));
		
		//Find flights using seat number
		northPanel.add(new JLabel ("Number of seats"));
		northPanel.add(seatNum);
		northPanel.add(flightswithseats);
		northPanel.add(new JLabel("                                                                  "));
		
		
		//Cancel Reservation
		northPanel.add(new JLabel ("User ID: "));
		northPanel.add(uidCanceledReserve);
		northPanel.add(new JLabel ("Flight ID: "));
		northPanel.add(fidCanceledReserve);
		northPanel.add(new JLabel("Enter reserved Date(YYYY-MM-DD): "));
		northPanel.add(reservedDate);
		northPanel.add(cancelReservationButton);
		northPanel.add(new JLabel("                                                                  "));
		
		
		
		northPanel.add(new JLabel ("User ID: "));
		northPanel.add(viewReservation);
		northPanel.add(viewReservationButton);
		northPanel.add(new JLabel("                                                                  "));

		
		//Features that do not require user input-Return data statistics
		southPanel.add(notReservedFlights);
		southPanel.add(findHighSeason);
		
		
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
	
	
		//view a user's list of reservation
		viewReservationButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String viewID = viewReservation.getText().trim();
				PreparedStatement stmt = null;
				try{
					
		            Choice choices = new Choice();
		            JFrame frame = new JFrame("Your reservation(s)");
					stmt = myConn.prepareStatement("Select uid,fid,reservedDate from reservation where uid = ?");
					if(viewID.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please provide User ID !");
					}
					else{
					stmt.setString(1, viewID);
					stmt.executeQuery();
					
					
					viewReservation.setText("");
					
					ResultSet rs = stmt.executeQuery();
			        if(!rs.isBeforeFirst())
			        	JOptionPane.showMessageDialog(null,"1: The provided UID ("+viewID+") does not exists \n or \n 2: The user has not made a reservation");
					else{
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String fid = rs.getString("fid");
		            String resDate = rs.getString("reservedDate");
					String Query = "UID | FID | ReservedDate"+" => "+uid +" | "+ fid + " | "+resDate;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);
		        }}
		  }}
				catch(SQLException exc){
					JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
				}
				finally{
					try {
						stmt.close();
					} catch (SQLException exc) {
						JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());					
					}
				}
			}
		});
	
		//ActionListener to create a user
		addUserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String namevalue = nameText.getText().trim();
				String agevalue = ageText.getText().trim();
				PreparedStatement stmt = null;
				try{
					stmt = myConn.prepareStatement("insert into user(uName,age) values(?,?)",Statement.RETURN_GENERATED_KEYS);
					if(namevalue.isEmpty() || agevalue.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please provide name & age !");
					}
					else{
					stmt.setString(1, namevalue);
					stmt.setString(2, agevalue);
					stmt.executeUpdate();
					
					ResultSet rs = stmt.getGeneratedKeys();
					if(rs.next())
					{
						JOptionPane.showMessageDialog(null, "Registered Successfully. \n Your id is: "+rs.getInt(1));
					}
					
					nameText.setText("");
					ageText.setText("");
				}}
				catch(SQLException exc){
					JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());					
				}
				finally{
					try {
						stmt.close();
					} catch (SQLException exc) {
						JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
					}
				}
			}
		});
		
		//Cancel Reservation
		cancelReservationButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String uid = uidCanceledReserve.getText().trim();
				String fid = fidCanceledReserve.getText().trim();
				String reservationDate = reservedDate.getText().trim();
				PreparedStatement stmt = null;
				try{
					stmt = myConn.prepareStatement("delete from Reservation where uid= ? and fid=? and reservedDate=?");
					if(uid.isEmpty() || fid.isEmpty() || reservationDate.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please provide User ID and Flight ID and reservation Date !");
					}
					else{
					stmt.setString(1, uid);
					stmt.setString(2, fid);
					stmt.setString(3, reservationDate);
					int row = stmt.executeUpdate();
					switch(row){
					case 0: 
						JOptionPane.showMessageDialog(null, "Unable to cancel with provided information !");
						break;
					case 1: 
						JOptionPane.showMessageDialog(null, "Reservation cancelled successfully !");
						uidCanceledReserve.setText("");
						fidCanceledReserve.setText("");
						reservedDate.setText("");
						break;
					}
					
				}}
				catch(SQLException exc)
				{
					JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
				}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException exc){
						JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
					}
				}
			}
		});

		
		// Actionlistener to make reservation
		reserveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String uid = uidReserve.getText().trim();
				String fid = fidReserve.getText().trim();
				PreparedStatement result = null;
				try{
					result = myConn.prepareStatement("insert into reservation(uid,fid,reservedDate) values(?,?,current_Date())");
					if(uid.isEmpty() || fid.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please provide User ID and Flight ID !");
					}
					else{
					result.setString(1, uid);
					result.setString(2, fid);
					result.executeUpdate();
					
					uidReserve.setText("");
					fidReserve.setText("");
					
					JOptionPane.showMessageDialog(null, "Reservation successful !");
				}}
				catch(SQLException exc)
				{
					JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
				}
				finally{
					try{
						result.close();
					}
					catch(SQLException exc){
						JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());

					}
				}
			}
		});
		
		//ActionListener to find a flight using airline Name
		findFlight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String airlineName = aName.getText().trim().toLowerCase();
				PreparedStatement stmt = null;
				try{
					
		            Choice choices = new Choice();
		            JFrame frame = new JFrame("Locate flights with airline name");
					stmt = myConn.prepareStatement("Select * from flightList where aName = ?");
					if(airlineName.isEmpty())
					{
						JOptionPane.showMessageDialog(null, "Please provide airline name !");
					}
					else{
					stmt.setString(1, airlineName);
					stmt.executeQuery();
					
					
					aName.setText("");
					
					ResultSet rs = stmt.executeQuery();
			        if(!rs.isBeforeFirst())
			        	JOptionPane.showMessageDialog(null, airlineName+" airline was not found !");
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
		  }}
				catch(SQLException exc){
					JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
				}
				finally{
					try {
						stmt.close();
					} catch (SQLException exc) {
						JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
					}
				}
			}
		});
		
		//Flights that have not been reserved AT ALL
		notReservedFlights.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				PreparedStatement stmt = null;
				try{
					
		            Choice choices = new Choice();
		            JFrame frame = new JFrame("Flights with 0 reservations");
					stmt = myConn.prepareStatement("select FL.fid,aName,numSeats,uid,reservedDate from flightList FL left Outer JOIN reservation R on FL.fid = R.fid;");
					
					ResultSet rs = stmt.executeQuery();
			    if(!rs.isBeforeFirst())
			    	JOptionPane.showMessageDialog(null, "There are currently no flights !");
				else{
					while (rs.next()) {
						String fid = rs.getString("fid");
						String aName = rs.getString("aName");
						String numSeats = rs.getString("numSeats");
						String reservedDate = rs.getString("reservedDate");
						String Query = "ID | Airline | Seats | Reservation Date"+" => "+fid +" | "+ aName + " | "+numSeats+" | "+reservedDate;
						choices.addItem(Query);
						frame.add(choices);
						frame.setSize(500, 200);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
		        }}
			}
				catch(SQLException exc){
					JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
				}
				finally{
					try {
						stmt.close();
					} catch (SQLException exc) {
						JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
					}
				}
			}
		});

		// Find Months with reservations more than 3
		findHighSeason.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				PreparedStatement stmt = null;
				try{
					
		            Choice choices = new Choice();
		            JFrame frame = new JFrame("High Season Month(s)");
					stmt = myConn.prepareStatement("select distinct MonthName(reservedDate) as 'High Season Month(s)' from reservation group by Month(reservedDate) having(count(*)>=3)");
									
					ResultSet rs = stmt.executeQuery();
			    if(!rs.isBeforeFirst())
			    	JOptionPane.showMessageDialog(null, "There are currently no high season(s) !");
				else{
					while (rs.next()) {
						String Season = rs.getString("High Season Month(s)");
						String Query = "High Season Month => "+Season;
						choices.addItem(Query);
						frame.add(choices);
						frame.setSize(350, 150);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
		        }}
		}
				catch(SQLException exc){
					JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
				}
				finally{
					try {
						stmt.close();
					} catch (SQLException exc) {
						JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
					}
				}
			}
		});
		
				
		
		//Find flights with certain # seats
		flightswithseats.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
				String seat = seatNum.getText().trim();
		        try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Flights with __ Seats");
		             stmt =  myConn.prepareStatement("Select * from flightList where numSeats = ?");
						if(seat.isEmpty())
						{
							JOptionPane.showMessageDialog(null, "Please provide number of seats per flight !");
						}
						else{
		             stmt.setString(1,seat);
		             ResultSet rs = stmt.executeQuery();
		             
		             seatNum.setText("");
		        if(!rs.isBeforeFirst())
		        	JOptionPane.showMessageDialog(null, "There are no flights with "+seat+" seats");
				else{
					while (rs.next()) {
						String fid = rs.getString("fid");
						String airlineName = rs.getString("aName");
						String numSeat = rs.getString("numSeats");
						String Query = "Flight ID | Airline Name | Seats"+" => "+fid +" | "+ airlineName + " | "+numSeat;
						choices.addItem(Query);
						frame.add(choices);
						frame.setSize(350, 150);
						frame.setVisible(true);
						frame.setLocationRelativeTo(null);
		        }}
		    } }
				catch (SQLException exc) {
					JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
				}
		        finally{
		        	try{
		        		stmt.close();
		        	}
		        	catch(SQLException exc)
		        	{
		        		JOptionPane.showMessageDialog(null, "An error occured. Error: => "+exc.getMessage());
		        	}
		        }
			}
		});
	}
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 800;

}