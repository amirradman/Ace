import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class loginPopUp extends JFrame 
{
	static loginPopUp frame = new loginPopUp();

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
	}
	
	public loginPopUp()
	{
		setTitle("Login");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		JButton userButton = new JButton("User Login");
		JButton adminButton = new JButton("Admin Login");
		
		JPanel centerPanel = new JPanel();
		centerPanel.add(userButton);
		centerPanel.add(adminButton);
		
		adminButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				adminPopUp adminBox = new adminPopUp();
				adminBox.setVisible(true);
				adminBox.setLocationRelativeTo(null);
				
			}
		});
		
		userButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				GeneralUser generalUser = new GeneralUser();
				generalUser.setVisible(true);
				generalUser.setLocationRelativeTo(null);
			}
		});
		
		
		
		add(centerPanel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		add(southPanel, BorderLayout.SOUTH);
		
	}
	
	
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 150;
}