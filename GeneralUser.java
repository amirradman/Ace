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
	
	
	
		JButton addUserButton = new JButton("Add User");
		JButton reserveButton = new JButton("Reserve Flight");
		JButton findFlight = new JButton("Find Flight");
		JButton notReservedFlights = new JButton("Not Reserved Flights");
		JButton findHighSeason = new JButton("Find High Season(s)");

	
	
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();

		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		
		//Create user using name and age fields + Button 
		northPanel.add(new JLabel("Name: "));
		northPanel.add(nameText);
		northPanel.add(new JLabel("Age: "));
		northPanel.add(ageText);
		northPanel.add(addUserButton);
		
		//Flight Reservation using uid and fid fields + button
		northPanel.add(new JLabel ("User ID: "));
		northPanel.add(uidReserve);
		northPanel.add(new JLabel ("Flight ID: "));
		northPanel.add(fidReserve);
		northPanel.add(reserveButton);
		

		//Find flights using airline Name fields + button
		northPanel.add(new JLabel ("AirLine Name: "));
		northPanel.add(aName);
		northPanel.add(findFlight);
		
		//Features that do not require user input-Return data statistics
		southPanel.add(notReservedFlights);
		southPanel.add(findHighSeason);
		
		
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
		
		
		

//		{{CHOOSE A BUTTON}}.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0) {
//				// CANCEL RESERVATION
//			}
//		});

		
		
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
		            JFrame frame = new JFrame("Search Result");
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
		            JFrame frame = new JFrame("Search Result");
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

		//
		findHighSeason.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				PreparedStatement stmt = null;
				try{
					
		            Choice choices = new Choice();
		            JFrame frame = new JFrame("Search Result");
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
		

//		
//		
//		{{CHOOSE A BUTTON}}.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent arg0) {
//				// FINDING FLIGHTS WITH ? # SEATS
//			}
//		});
//		
//	
	
	}
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 800;

}