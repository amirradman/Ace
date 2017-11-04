import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class loginPopUp extends JFrame 
{
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				loginPopUp frame = new loginPopUp();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	
	public loginPopUp()
	{
		setTitle("Login");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		JButton userButton = new JButton("User");
		JButton adminButton = new JButton("Admin");
		
		JPanel centerPanel = new JPanel();
		centerPanel.add(userButton);
		centerPanel.add(adminButton);
		
		adminButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				adminPopUp adminBox = new adminPopUp();
				adminBox.setVisible(true);
				
			}
		});
		
		add(centerPanel, BorderLayout.CENTER);
		
		JButton cancelButton = new JButton("Cancel");
		JPanel southPanel = new JPanel();
		southPanel.add(cancelButton);
		add(southPanel, BorderLayout.SOUTH);
		
	}
	
	public static final int DEFAULT_WIDTH = 300;
	public static final int DEFAULT_HEIGHT = 150;
}

