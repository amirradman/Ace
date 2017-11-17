import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		setTitle("User");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	
		final JTextField nameText = new JTextField();
		final JTextField ageText = new JTextField();
		final JTextField uidReserve = new JTextField();
		final JTextField fidReserve = new JTextField();
		final JTextField aName = new JTextField();
		final JTextField seatNum = new JTextField();
		final JTextField uidCanceledReserve = new JTextField();
		final JTextField fidCanceledReserve = new JTextField();
	
	
	
		JButton addUserButton = new JButton("Add User");
		JButton reserveButton = new JButton("Reserve Flight");
		JButton findFlight = new JButton("Find Flight");
		JButton notReservedFlights = new JButton("Not Reserved Flights");
		JButton findHighSeason = new JButton("Find High Season(s)");
		JButton showUsersButton = new JButton("Show Current Users");
		JButton flightswithseats = new JButton("Show flights with given seat number");
		JButton cancelReservationButton = new JButton("Cancel Reservation");
	
	
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
		northPanel.add(cancelReservationButton);
		northPanel.add(new JLabel("                                                                  "));

		
		//Features that do not require user input-Return data statistics
		southPanel.add(notReservedFlights);
		southPanel.add(findHighSeason);
		southPanel.add(showUsersButton);
		
		
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
	
	
	
		//ActionListener to create a user
		addUserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String namevalue = nameText.getText();
				String agevalue = ageText.getText();
				PreparedStatement stmt = null;
				try{
					stmt = myConn.prepareStatement("insert into user(uName,age) values(?,?)");
					stmt.setString(1, namevalue);
					stmt.setString(2, agevalue);
					stmt.executeUpdate();
					
					nameText.setText("");
					ageText.setText("");
				}
				catch(Exception exc){
					exc.printStackTrace();
				}
				finally{
					try {
						stmt.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		//Cancel Reservation
		cancelReservationButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String uid = uidCanceledReserve.getText();
				String fid = fidCanceledReserve.getText();
				PreparedStatement stmt = null;
				try{
					stmt = myConn.prepareStatement("insert into canceledReservation(uid,fid,canceledDate) values(?,?,current_Date())");
					stmt.setString(1, uid);
					stmt.setString(2, fid);
					stmt.executeUpdate();
				
					uidCanceledReserve.setText("");
					fidCanceledReserve.setText("");
				}
				catch(Exception exc)
				{
					exc.printStackTrace();
				}
				finally{
					try{
						stmt.close();
					}
					catch(SQLException exc){
						exc.printStackTrace();

					}
				}
			}
		});

		
		// Actionlistener to make reservation
		reserveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String uid = uidReserve.getText();
				String fid = fidReserve.getText();
				PreparedStatement result = null;
				try{
					result = myConn.prepareStatement("insert into reservation(uid,fid,reservedDate) values(?,?,current_Date())");
					result.setString(1, uid);
					result.setString(2, fid);
					result.executeUpdate();
				
					uidReserve.setText("");
					fidReserve.setText("");
				}
				catch(Exception exc)
				{
					exc.printStackTrace();
				}
				finally{
					try{
						result.close();
					}
					catch(SQLException exc){
						exc.printStackTrace();

					}
				}
			}
		});
		
		//ActionListener to find a flight using airline Name
		findFlight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String airlineName = aName.getText();
				PreparedStatement stmt = null;
				try{
					
		            Choice choices = new Choice();
		            JFrame frame = new JFrame("Locate flights with airline name");
					stmt = myConn.prepareStatement("Select * from flightList where aName = ?");
					stmt.setString(1, airlineName);
					stmt.executeQuery();
					
					
					aName.setText("");
					
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
		        }}
				catch(Exception exc){
					exc.printStackTrace();
				}
				finally{
					try {
						stmt.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
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
					aName.setText("");
					
					ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            String fid = rs.getString("fid");
		            String aName = rs.getString("aName");
		            String numSeats = rs.getString("numSeats");
		            String uid = rs.getString("uid");
		            String reservedDate = rs.getString("reservedDate");
		            String Query = fid +" "+ aName + " "+numSeats+" "+ uid + " "+reservedDate;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);
		        }}
				catch(Exception exc){
					exc.printStackTrace();
				}
				finally{
					try {
						stmt.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
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
					
					aName.setText("");
					
					ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            String Season = rs.getString("High Season Month(s)");
		            String Query = Season;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
					frame.setLocationRelativeTo(null);
		        }}
				catch(Exception exc){
					exc.printStackTrace();
				}
				finally{
					try {
						stmt.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		//Show Current Users
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
		        	try {
						stmt.close();
					} catch (SQLException e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
		        }
			}
		});
		
		
		//Find flights with certain # seats
		flightswithseats.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement stmt = null;
				String seat = seatNum.getText();
		        try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Flights with __ Seats");
		             stmt =  myConn.prepareStatement("Select * from flightList where numSeats = ?");
		             stmt.setString(1,seat);
		             ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		            String fid = rs.getString("fid");
		            String airlineName = rs.getString("aName");
		            String numSeat = rs.getString("numSeats");
		            String Query = fid +" "+ airlineName + " "+numSeat;
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
		        	catch(SQLException exc)
		        	{
		        		exc.printStackTrace();
		        	}
		        }
			}
		});
	}
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 800;

}