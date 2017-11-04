import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class adminPopUp extends JFrame{
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
		
		JPanel southPanel = new JPanel();
		southPanel.add(cancelButton);
		
		
		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		
	}
	
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 600;
}




