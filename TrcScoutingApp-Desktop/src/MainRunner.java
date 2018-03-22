import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import java.io.IOException;

import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("all")
public class MainRunner 
{
	static JButton exportToCSV = new JButton("Export to CSV");
	static JButton settings = new JButton("Settings");
	static JButton addMatch = new JButton("Add match");
	static JButton about = new JButton("About");
	static JButton xlear = new JButton("Clear Entries");
	static JFrame home_frame;
	static JPanel optionpanel;
	static JTextArea field = new JTextArea();
	static JScrollPane scroll;
	
	public static void main (String[] args)
	{
		  try
		  {
			  DataStore.parseTeamNum();
			  DataStore.parseFirstName();
			  DataStore.parseLastName();
		  } 
		  catch (IOException e)
		  {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  home_frame = new JFrame("TRC Scouting App Desktop | FRC Edition");
		  home_frame.setResizable(false);
		  optionpanel = new JPanel();
		  optionpanel.setLayout(null);
		  // x, y, width, height
		  addMatch.setBounds(500, 700, 200, 40);
		  xlear.setBounds(0, 0, 200, 20);
		  exportToCSV.setBounds(200, 0, 200, 20);
		  settings.setBounds(400, 0, 200, 20);
		  about.setBounds(600, 0, 200, 20);
		  field.setFont(new Font("Consolas", Font.PLAIN, 16));
		  scroll = new JScrollPane(field);
		  scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		  scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		        public void adjustmentValueChanged(AdjustmentEvent e) {  
		            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
		        }
		    });
		  scroll.setBounds(200, 80, 800, 600);
		  optionpanel.add(xlear);
		  optionpanel.add(addMatch);
		  optionpanel.add(exportToCSV);
		  optionpanel.add(settings);
		  optionpanel.add(about);
		  optionpanel.add(scroll);
		  home_frame.add(optionpanel);
		  addMatch.addActionListener(new ActionListener()
		  {
			   public void actionPerformed(ActionEvent ae)
			   {
				   DataStore.hasAdded = true;
				   OpenInputWindow();
			       // open a new window to enter match info.
			   }
		  });
		  xlear.addActionListener(new ActionListener()
		  {
			   public void actionPerformed(ActionEvent ae)
			   {
				   if (openClearDialog())
				   {
					   DataStore.CsvFormattedContests.clear();
					   DataStore.contests.clear();
					   clearField();
					   updateList();
					   addln("\nList cleared!");
				   }
			   }
		  });
		  exportToCSV.addActionListener(new ActionListener()
		  {
			   public void actionPerformed(ActionEvent ae)
			   {
				   try 
				   {
					   addln("\nWriting entries to CSV...");
					   DataStore.writeContestsToCsv(DataStore.FIRST_NAME + "_" + DataStore.LAST_NAME + "_results.csv");
					   if (DataStore.contests.size() == 0)
					   {
						   addln("\nNo changes written.");
					   }
					   else if (DataStore.contests.size() == 1)
					   {
						   addln("\nDone. 1 entry written to: ~/Desktop/TrcScoutingApp/" + DataStore.FIRST_NAME + "_" + DataStore.LAST_NAME + "_results.csv");
					   }
					   else
					   {
						   addln("\nDone. " + DataStore.contests.size() +" entries written to: ~/Desktop/TrcScoutingApp/" + DataStore.FIRST_NAME + "_" + DataStore.LAST_NAME + "_results.csv");
					   }
				   } 
				   catch (IOException e)
				   {
					   // TODO Auto-generated catch block
					   e.printStackTrace();
				   }
			   }
		  });
		  settings.addActionListener(new ActionListener()
		  {
			   public void actionPerformed(ActionEvent ae)
			   {
				   settingsBox();
			   }
		  });
		  about.addActionListener(new ActionListener()
		  {
			   public void actionPerformed(ActionEvent ae)
			   {
				   addln("\n================================================");
				   addln("             About TRC Scouting App               ");
				   addln("                  Version: 0.2a                   ");
				   addln("                                                  ");
				   addln("   This software is open source and freely        ");
				   addln("   redistributable under the MIT license.         ");
				   addln("                                                  ");
				   addln("         (C) Titan Robotics Club 2018             ");
				   addln("================================================\n");
			   }
		  });
		  home_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  home_frame.setSize(1200, 800);
		  home_frame.setVisible(true);
		  field.setText("TRC Scouting App Desktop - FRC edition v0.2a\n"
			  		+ "(C) 2018 Titan Robotics Club.\n\n"
			  		+ "Name: " + DataStore.FIRST_NAME + " " + DataStore.LAST_NAME + "\n"
			  		+ "Team Number: " + DataStore.SELF_TEAM_NUMBER + "\n"
			  		+ "Welcome to the TRC Scouting app for FIRST Tech Challenge.\n\n"
			  		+ "There are no match records stored yet.\n"
			  		+ "Click \"Add Match\" to add a record.\n");
		  addFunFact();
	}
	public static void OpenInputWindow()
	{
		JFrame inputFrame = new JFrame("Add Game");
		inputFrame.setResizable(false);
		inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inputFrame.setSize(800, 850);
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(null);
		
		JButton confirm_button = new JButton("Confirm");
		confirm_button.setBounds(300, 750, 200, 50);
		
		JLabel l_numb = new JLabel();
		l_numb.setText("Match #");
		l_numb.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l_numb.setBounds(370, 10, 100, 20);
		
		JTextField f_numb = new JTextField();
		f_numb.setBounds(350, 30, 100, 20);
		// field.getText() gets text
		
		// JLabel l_name = new JLabel();
		// l_name.setText("Competition Name");
		// l_name.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		// l_name.setBounds(340, 50, 120, 20);
		
		// JTextField f_name = new JTextField();
		// f_name.setBounds(335, 70, 130, 20);
		
		JLabel l_mtype = new JLabel();
		l_mtype.setText("Match Type");
		l_mtype.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l_mtype.setBounds(340, 90, 120, 20);
		
		String[] types = {"Practice", "Qualification", "Semi-Final", "Final"};
	    JComboBox<String> f_mtype = new JComboBox<String>(types);
	    f_mtype.setBounds(340, 112, 120, 20);
		
	    JLabel l_spec = new JLabel();
		l_spec.setText("Spectating Team");
		l_spec.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l_spec.setBounds(340, 132, 120, 20);
		
		String[] spectypes = {"Red Alliance 1 (Right)", "Red Alliance 2 (Center)", "Red Alliance 3 (Left)", "Blue Alliance 1 (Right)", "Blue Alliance 2 (Center)", "Blue Alliance 3 (Left)"};
	    JComboBox<String> f_spec = new JComboBox<String>(spectypes);
	    f_spec.setBounds(330, 154, 200, 20);
	    
		JLabel l_spec_team_num = new JLabel();
		l_spec_team_num.setText("Team Number");
		l_spec_team_num.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l_spec_team_num.setBounds(355, 45, 120, 20);
		// l_spec_team_num.setForeground(Color.RED);
		
		JTextField f_spec_team_num = new JTextField();
		f_spec_team_num.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		f_spec_team_num.setBounds(350, 65, 100, 30);
		
		JLabel l_starting_position = new JLabel();
		l_starting_position.setText("Starting Position");
		l_starting_position.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l_starting_position.setBounds(350, 170, 150, 30);
		
		String[] start_pos_types = {"Left", "Middle", "Right"};
	    JComboBox<String> f_starting_position = new JComboBox<String>(start_pos_types);
	    f_starting_position.setBounds(330, 200, 150, 20);
	    
		JLabel autonomous_label = new JLabel();
		autonomous_label.setText("Autonomous Phase");
		autonomous_label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		autonomous_label.setBounds(100, 250, 200, 20);
		
		JLabel teleop_label = new JLabel();
		teleop_label.setText("Teleoperated Phase");
		teleop_label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		teleop_label.setBounds(100, 400, 200, 20);
		
		/**
		 * ==================================================================
		 * The editable objective options are right below the following line.
		 * ==================================================================
		 */
		
		// Initialize the objectives below.
		
		// Driver's PoV
		String[] left_or_right = {"Left", "Right"};
		
		
		
		// Add the initialized objectives below.
		
		
		//=============== [ END EDITABLE OBJECTIVE OPTIONS HERE ] ===============//

		inputPanel.add(confirm_button);
		inputPanel.add(l_numb);
		inputPanel.add(f_numb);
		inputPanel.add(l_starting_position);
		inputPanel.add(f_starting_position);
		// inputPanel.add(l_name);
		// inputPanel.add(f_name);
		inputPanel.add(l_mtype);
		inputPanel.add(f_mtype);
		inputPanel.add(l_spec);
		inputPanel.add(f_spec);
		inputPanel.add(l_spec_team_num);
		inputPanel.add(f_spec_team_num);
		inputPanel.add(autonomous_label);
		inputPanel.add(teleop_label);
		// inputPanel.add(l_redalliance2);
		// inputPanel.add(f_redalliance2);
		// inputPanel.add(l_redalliance3);
		// inputPanel.add(f_redalliance3);
		// inputPanel.add(l_bluealliance1);
		// inputPanel.add(f_bluealliance1);
		// inputPanel.add(l_bluealliance2);
		// inputPanel.add(f_bluealliance2);
		// inputPanel.add(l_bluealliance3);
		// inputPanel.add(f_bluealliance3);
		
		inputPanel.setVisible(true);
		
		inputFrame.add(inputPanel);
		inputFrame.setVisible(true);
		
		confirm_button.addActionListener(new ActionListener()
		 {
			   public void actionPerformed(ActionEvent ae)
			   {
				   System.out.println("Parsing values...");
				   try 
				   {
					   // parse the entered values on confirm.
					   int matchNum = Integer.parseInt(f_numb.getText());
					   // String compName = f_name.getText();
					   
					   String compType = f_mtype.getSelectedItem().toString();
					   int compTypeInt = -428957892;
					   if (compType.matches("Practice"))
					   {
						   compTypeInt = 1;
					   }
					   else if (compType.matches("Qualification"))
					   {
						   compTypeInt = 2;
					   }
					   else if (compType.matches("Semi-Final"))
					   {
						   compTypeInt = 3;
					   }
					   else
					   {
						   compTypeInt = 4;
					   }
					   
					   int spectatingTeamNumber = Integer.parseInt(f_spec_team_num.getText());
					  
					   boolean containsOurTeam = false;
					   
					   if (spectatingTeamNumber == DataStore.SELF_TEAM_NUMBER)
					   {
						   containsOurTeam = true;
					   }
					   
					   String specType = f_spec.getSelectedItem().toString();
					   
					   System.out.println("Match Number            : " + matchNum);
					   System.out.println("Spectating Team Number  : " + spectatingTeamNumber);
					   System.out.println("Spectating Team Type    : " + specType);
					   
					   /**
						 * ==================================================================
						 * The editable parsing options are right below the following line.
						 * ==================================================================
						 */
					   
					  
					   
					  // format the parsed values into CSV format and store them in DataStore.java.
					  
					   /**
					     * The String "CSVFormattedString" should be in the format:
					     * "Team Contained Status, Date, Match #, Competition Name, Competition Type, Red Alliance 1, Red Alliance 2, Blue Alliance 1, Blue Alliance 2, SampleCondition1, SampleCond2"
					     */
					  
					  String merveille_million = " ";
					  
					  if (containsOurTeam)
					  {
						  merveille_million = "*";
					  }
					  
					   String CSVFormattedString = merveille_million + "," + DataStore.getDateAsString() + "," + matchNum + "," + compType + "," + spectatingTeamNumber + "," + specType + ",";
					   String DisplayString = "Match #: " + matchNum + " | Match Type: " + compType + " | R: " + merveille_million + " | " + " S: " + spectatingTeamNumber + " | " + DataStore.getDateAsString();
					 
					  
					   //=============== [ END EDITABLE OBJECTIVE OPTIONS HERE ] ===============//
					  
					   DataStore.CsvFormattedContests.add(CSVFormattedString);
					   DataStore.contests.add(DisplayString);
					   System.out.println("CSV Formatted String: " + CSVFormattedString);
					   // update the displayed entries.
					   updateList();
					   // close the window.    
				       inputFrame.dispose();
				   } 
				   catch (NumberFormatException arg0)
				   {
					    arg0.printStackTrace();
					    JFrame kawaii = new JFrame("Error");
						JLabel kawaiichan = new JLabel("kawaiichan", JLabel.CENTER);
						JButton button99 = new JButton("OK");
						 kawaii.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						  kawaiichan.setText("Issue with input: " + arg0);
						  kawaii.add(kawaiichan);
						  kawaii.add(button99, BorderLayout.SOUTH);
						  kawaii.setSize(800, 200);
						  kawaii.setVisible(true);
						  button99.addActionListener(new ActionListener()
						  {
							   public void actionPerformed(ActionEvent ae)
							   {
							        kawaii.dispose();
							   }
						});
				   }   
			   }
		 });

	}
	
	static int clearD = 0;
	
	public static boolean openClearDialog()
	{
		int response = JOptionPane.showConfirmDialog(null, "Do you want to clear all entries?", "Confirm",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (response == JOptionPane.NO_OPTION) 
		    {
		      return false;
		    } 
		    else if (response == JOptionPane.YES_OPTION)
		    {
		      return true;
		    } 
		    else if (response == JOptionPane.CLOSED_OPTION)
		    {
		      return false;
		    }
		    else
		    {
		    	return false;
		    }
	}
	
	public static void settingsBox()
	{
		JFrame inputFrame = new JFrame("Settings...");
		inputFrame.setResizable(false);
		inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inputFrame.setSize(400, 200);
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(null);
		
		JLabel l_fname = new JLabel();
		l_fname.setText("First Name");
		l_fname.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l_fname.setBounds(40, 20, 100, 20);
		
		JTextField f_fname = new JTextField();
		f_fname.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		f_fname.setBounds(40, 40, 100, 30);
		
		JLabel l_lname = new JLabel();
		l_lname.setText("Last Name");
		l_lname.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l_lname.setBounds(260, 20, 100, 20);
		
		JTextField f_lname = new JTextField();
		f_lname.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		f_lname.setBounds(260, 40, 100, 30);
		
		JLabel l_tnum = new JLabel();
		l_tnum.setText("Team #");
		l_tnum.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		l_tnum.setBounds(150, 40, 100, 20);
		
		JTextField f_tnum = new JTextField();
		f_tnum.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		f_tnum.setBounds(150, 60, 100, 30);
		
		JButton confirm_button = new JButton("Confirm");
		confirm_button.setBounds(100, 100, 200, 50);
		
		confirm_button.addActionListener(new ActionListener()
		 {
			   public void actionPerformed(ActionEvent ae)
			   {
				   System.out.println("Parsing setting values...");
				   try 
				   {
					   int teamNum = Integer.parseInt(f_tnum.getText());
					   String firstName = f_fname.getText();
					   String lastName = f_lname.getText();
					   DataStore.writeSettingsToFile(firstName, lastName, teamNum);
					   addln("\nConfigured Data: \nName: " + firstName + " " + lastName + "\nTeam: " + teamNum);
					   inputFrame.dispose();
				   }
				   catch (NumberFormatException | IOException e)
				   {
					    e.printStackTrace();
					    JFrame kawaii = new JFrame("Error");
						JLabel kawaiichan = new JLabel("kawaiichan", JLabel.CENTER);
						JButton button99 = new JButton("OK");
						 kawaii.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						  kawaiichan.setText("Issue with input: " + e);
						  kawaii.add(kawaiichan);
						  kawaii.add(button99, BorderLayout.SOUTH);
						  kawaii.setSize(800, 200);
						  kawaii.setVisible(true);
						  button99.addActionListener(new ActionListener()
						  {
							   public void actionPerformed(ActionEvent ae)
							   {
							        kawaii.dispose();
							   }
						});
				   }
			   }
		 });
		
		inputPanel.add(l_fname);
		inputPanel.add(f_fname);
		inputPanel.add(l_lname);
		inputPanel.add(f_lname);
		inputPanel.add(l_tnum);
		inputPanel.add(f_tnum);
		inputPanel.add(confirm_button);
		
		inputPanel.setVisible(true);
		
		inputFrame.add(inputPanel);
		inputFrame.setVisible(true);
	}
	
	public static void clearField()
	{
		for(int i = 0; i < 70; i++)
		{
			field.append("\n");
		}
	}
	
	public static void updateList()
	{
		System.out.println("Updating list...");
		clearField();
		addln("\nCurrent Entries (" + DataStore.contests.size() + " entries):") ;
		for (String s : DataStore.contests)
		{
			addln(s);
		}
	}
	
	public static void addln(String s)
	{
		field.append(s+"\n");
	}
	
	public static void addFunFact()
	{
		String msg = "I'm inserting this quote because I don't think anyone will see this in the actual program."
				+ "\n If you see this, something is wrong, really wrong with either the program or your JRE.";
		Random d25 = new Random();
		int choice = d25.nextInt(27);
		if (choice == 0)
		{
			msg = "ABC's of FIRST: A is for Altruism.";
		}
		else if (choice == 1)
		{
			msg = "ABC's of FIRST: C is for Commitment.";
		}
		else if (choice == 2)
		{
			msg = "ABC's of FIRST: D is for Determination.";
		}
		else if (choice == 3)
		{
			msg = "ABC's of FIRST: E is for Engineering.";
		}
		else if (choice == 4)
		{
			msg = "ABC's of FIRST: F is for Fairness.";
		}
		else if (choice == 5)
		{
			msg = "ABC's of FIRST: G is for Gracious Professionalism.";
		}
		else if (choice == 6)
		{
			msg = "ABC's of FIRST: H is for Honesty.";
		}
		else if (choice == 7)
		{
			msg = "ABC's of FIRST: I is for Inspiration.";
		}
		else if (choice == 8)
		{
			msg = "ABC's of FIRST: L is for Leadership.";
		}
		else if (choice == 9)
		{
			msg = "ABC's of FIRST: M is for mutual gain and respect.";
		}
		else if (choice == 10)
		{
			msg = "ABC's of FIRST: N is for Negotiation.";
		}
		else if (choice == 11)
		{
			msg = "ABC's of FIRST: P is for Persistance.";
		}
		else if (choice == 12)
		{
			msg = "ABC's of FIRST: R is for Responsibility.";
		}
		else if (choice == 13)
		{
			msg = "ABC's of FIRST: S is for Self-Improvement.";
		}
		else if (choice == 14)
		{
			msg = "ABC's of FIRST: T is for Teamwork.";
		}
		else if (choice == 15)
		{
			msg = "ABC's of FIRST: U is for Unity.";
		}
		else if (choice == 16)
		{
			msg = "History of FIRST:\n1951: Dean Kamen, the founder of FIRST, is born.\n" + 
					"	\"Life is so short. We shouldn't waste any of it trying to do anything marginal.\"\n" + 
					"";
		}
		else if (choice == 17)
		{
			msg = "History of FIRST:\n1956: Dean Kamen, the founder of FIRST, creates the bed making machine, his first invention.\n";
		}
		else if (choice == 18)
		{
			msg = "History of FIRST:\n1989: Dean Kamen forms FIRST Robotics.\n" + 
					"	\"Let's make something new.\"\n" + 
					"		-Dean Kamen\n";
		}
		else if (choice == 19)
		{
			msg = "History of FIRST:\n1992: The first FIRST (pardon the pun) Robotics competition is held at a high school gym in New Hampshire. 28 teams attended. \n" + 
					"";
		}
		else if (choice == 20)
		{
			msg = "History of FIRST:\n1995-2002: Disney EPCOT hosts FIRST championship.\n" + 
					"";
		}
		else if (choice == 21)
		{
			msg = "History of FIRST:\n1996: Inaugural FIRST scholarship made available by WPI (Worcester Polytechnic Institude).\n" + 
					"";
		}
		else if (choice == 22)
		{
			msg = "History of FIRST:\n1998: FLL is founded when FIRST cooperates with LEGO.\n" + 
					"";
		}
		else if (choice == 23)
		{
			msg = "History of FIRST:\n 2004: FLL (First Lego League) was launched.\n" + 
					"";
		}
		else if (choice == 24)
		{
			msg = "History of FIRST:\n 2005: FIRST Tech Challenge is launched after FLL and FRC.\n" + 
					"";
		}
		else if (choice == 25)
		{
			msg = "TRC Fun Fact:\n The Titan Robotics Club was founded in 2001 as a club for FIRST Robotics Challenge.\n";
		}
		else if (choice == 26)
		{
			msg = "TRC Fun Fact:\n The Titan Robotics Club entered FIRST Tech Challenge in 2005.\n";
		}
		addln("\n"+msg);
	}
}
