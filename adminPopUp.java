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
		final JTextField nameText = new JTextField();
		final JTextField ageText = new JTextField();
		final JTextField aNameText = new JTextField();
		final JTextField numOfSeatsText = new JTextField();

		JButton deleteUserButton = new JButton("Delete User");
		JButton addUserButton = new JButton("Add User");
		JButton addFlightButton = new JButton("Add Flight");
		JButton cancelButton = new JButton("Cancel");
		JButton showUsersButton = new JButton("Show Current Users");
		JButton showFlightsButton = new JButton("Show Current Flights");
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
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
		
		JPanel southPanel = new JPanel();
		southPanel.add(showUsersButton);
		southPanel.add(showFlightsButton);
		southPanel.add(cancelButton);
		
		
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		
		//Show Current Users from DB
		showUsersButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				currentUsers currentUsersBox = new currentUsers();
				currentUsersBox.setVisible(true);		
			}
		});
		
		//Show Current Flights from DB
		showFlightsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				currentFlights currentFlightsBox = new currentFlights();
				currentFlightsBox.setVisible(true);			
			}
		});

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
					
						
					nameText.setText("");
					ageText.setText("");
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
//		showUsersButton.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				try{
//					Statement result = myConn.createStatement();
//					ResultSet myset = result.executeQuery("Select * from user");
//					while(myset.next()){
//					System.out.println(myset.getString("uid"));
//				}}
//				catch(Exception exc)
//				{
//					exc.printStackTrace();
//				}
//			}
//		});
	}
		
	
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 600;
}
