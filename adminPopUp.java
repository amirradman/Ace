import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
		final JTextField nameText = new JTextField();
		final JTextField ageText = new JTextField();
		final JTextField aNameText = new JTextField();
		final JTextField numOfSeatsText = new JTextField();
		
		//Adding reservation Test 
		final JTextField uidReserve = new JTextField();
		final JTextField fidReserve = new JTextField();

		JButton deleteUserButton = new JButton("Delete User");
		JButton addUserButton = new JButton("Add User");
		JButton addFlightButton = new JButton("Add Flight");
//		JButton cancelButton = new JButton("Cancel");
		JButton showUsersButton = new JButton("Show Current Users");
		JButton showFlightsButton = new JButton("Show Current Flights");
		
		//add reserve button 
		JButton reserveButton = new JButton("Reserve Flight");

		
		JPanel northPanel = new JPanel();
//		northPanel.setLayout(new GridLayout(1,3));
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
//		northPanel.add(new JLabel("uID: ", SwingConstants.LEFT));
		northPanel.add(new JLabel("uID: "));
		northPanel.add(uIDText);
		northPanel.add(deleteUserButton);

		
		northPanel.add(new JLabel("Name: "));
		northPanel.add(nameText);
		northPanel.add(new JLabel("Age: "));
		northPanel.add(ageText);
		northPanel.add(addUserButton);
		
		northPanel.add(new JLabel("Airline Name: "));
		northPanel.add(aNameText);
		northPanel.add(new JLabel("Number of Seats: "));
		northPanel.add(numOfSeatsText);
		northPanel.add(addFlightButton);
		
		northPanel.add(new JLabel("uid"));
		northPanel.add(uidReserve);
		northPanel.add(new JLabel("fid"));
		northPanel.add(fidReserve);
		northPanel.add(reserveButton);
		
		JPanel southPanel = new JPanel();
//		southPanel.add(cancelButton);
		southPanel.add(showUsersButton);
		southPanel.add(showFlightsButton);
		
		
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

					//Call for archive. Enter value in the format YYYY-MM-DD
//					CallableStatement cll = myConn.prepareCall("{call archivedReservation(?)}");
//					
//					cll.setString(1, uidvalue);
//					cll.executeQuery();
					
					uIDText.setText("");
				}				
				catch(Exception exc)
				{
					exc.printStackTrace();
				}
			}
		});
		//Adding user to database
		addUserButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String namevalue = nameText.getText();
				String agevalue = ageText.getText();
				try{
					PreparedStatement stmt = myConn.prepareStatement("insert into user(uName,age) values(?,?)");
					stmt.setString(1, namevalue);
					stmt.setString(2, agevalue);
					stmt.executeUpdate();
					
					
					
					PreparedStatement stmt1 = myConn.prepareStatement("select uid from user where uName=? and age = ?");
					stmt1.setString(1,namevalue);
					stmt1.setString(2, agevalue);
					
					ResultSet rs = stmt1.executeQuery();
					
					nameText.setText("");
					ageText.setText("");
					while(rs.next())
					{
						System.out.println("Your uid is "+rs.getString("uid"));
					}
				}
				catch(Exception exc){
					exc.printStackTrace();
				}
			}
		});
		//adding flights to database
		addFlightButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String aName = aNameText.getText();
				String numOfSeats = numOfSeatsText.getText();
				try{
					PreparedStatement stmt = myConn.prepareStatement("insert into flightList(aName,numSeats) values(?,?)");
					stmt.setString(1, aName);
					stmt.setString(2, numOfSeats);
					stmt.executeUpdate();
					
					aNameText.setText("");
					numOfSeatsText.setText("");
				}
				catch(Exception exc){
					exc.printStackTrace();
				}
			}
		});

		
		showUsersButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				currentUsers currentUsersBox = new currentUsers();
//				currentUsersBox.setVisible(true);
		        try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Current Users");
		             PreparedStatement pstmt =  myConn.prepareStatement("Select * from user");
		             ResultSet rs = pstmt.executeQuery();
		        while (rs.next()) {
		            String uid = rs.getString("uid");
		            String uName = rs.getString("uName");
		            String age = rs.getString("age");
		            String Query = uid +" "+ uName + " "+age;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
		        }

		    } catch (Exception e) {

		        JOptionPane.showMessageDialog(null, e);
		    }
			}
		});
		
		showFlightsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
//				currentFlights currentFlightsBox = new currentFlights();
//				currentFlightsBox.setVisible(true);			
		        try {
		            Choice choices = new Choice();
		             JFrame frame = new JFrame("Available Flights");
		             PreparedStatement pstmt =  myConn.prepareStatement("Select * from flightList");
		             ResultSet rs = pstmt.executeQuery();
		        while (rs.next()) {
		            String fid = rs.getString("fid");
		            String aName = rs.getString("aName");
		            String numSeats = rs.getString("numSeats");
		            String Query = fid +" "+ aName + " "+numSeats;
		            choices.addItem(Query);
		            frame.add(choices);
		            frame.setSize(350, 150);
		            frame.setVisible(true);
		        }

		    } catch (Exception e) {

		        JOptionPane.showMessageDialog(null, e);
		    }
			}
		});
		reserveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String uid = uidReserve.getText();
				String fid = fidReserve.getText();
				try{
					PreparedStatement result = myConn.prepareStatement("insert into reservation(uid,fid,reservedDate) values(?,?,current_Date())");
					result.setString(1, uid);
					result.setString(2, fid);
					result.executeUpdate();
					ResultSet rs = result.executeQuery("Select uid,fid,reservedDate from reservation");
					while(rs.next()){
					System.out.println(rs.getString("uid")+" "+rs.getString("fid")+" "+rs.getString("reservedDate"));
					
					uidReserve.setText("");
					fidReserve.setText("");
				}}
				catch(Exception exc)
				{
					exc.printStackTrace();
				}
			}
		});
	}

	
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 600;
}
