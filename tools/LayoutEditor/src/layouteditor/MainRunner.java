package layouteditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class MainRunner 
{
	public static final String VERSION_ID = "0.1a";
	
	public static final int EditText = 1;
	public static final int Spinner = 2;
	public static final int CheckBox = 3;
	
	public static final int Integer_ = 1;
	public static final int Double_ = 2;
	public static final int Boolean_ = 3;
	public static final int String_ = 4;
	
	public static JFrame home_frame;
	public static JPanel optionpanel;
	public static DefaultListModel<ElementHandler> l1;
	public static JList<ElementHandler> list;
	public static JButton add_entry;
	public static JButton del_entry;
	public static JScrollPane listPane;
	
	public static JLabel previewText;
	
	public static JLabel colonOne;
	public static JLabel colonTwo;
	public static JLabel colonThree;
	
	public static JTextField seg1;
	public static JTextField seg2;
	public static JTextField seg3;
	public static JTextField seg4;
	public static JTextField seg5;
	public static JTextField seg6;
	
	public static JTextArea dataStoreBox;
	public static JTextArea setCompetitionNameBox;
	
	public static JButton generateDataButton;
	
	public static void main(String[] args) 
	{
		home_frame = new JFrame("TRC Scouting App Layout Editor");
		home_frame.setResizable(false);
		optionpanel = new JPanel();
		optionpanel.setLayout(null);
		optionpanel.setBackground(Color.CYAN);
		l1 = new DefaultListModel<>();  
		add_entry = new JButton("+");
		add_entry.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add_entry.setText("+");
		add_entry.setBounds(100, 45, 50, 50);
		del_entry = new JButton("-");
		del_entry.setFont(new Font("Segoe UI", Font.BOLD, 20));
		del_entry.setText("-");
		del_entry.setBounds(160, 45, 50, 50);
		add_entry.setForeground(Color.GREEN);
		add_entry.setBackground(Color.DARK_GRAY);
		del_entry.setForeground(Color.RED);
		del_entry.setBackground(Color.DARK_GRAY);
		del_entry.addActionListener(new ActionListener()
		  {
			   public void actionPerformed(ActionEvent ae)
			   {
			        // get the highlighted entry in the list and remove it
				   int idx = list.getSelectedIndex();
				   if (idx != -1)
				   {
					   DataStore.removeElementFromList(idx);
				   }
			   }
		});
		add_entry.addActionListener(new ActionListener()
		  {
			   public void actionPerformed(ActionEvent ae)
			   {
			       openFormAdderForm();
			   }
		});

		previewText = new JLabel();
		previewText.setText("Preview Text:");
		previewText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		previewText.setBounds(100, 210, 100, 20);
		optionpanel.add(previewText);
		
		seg1 = new JTextField();
		seg1.setBounds(100, 230, 60, 30);  
		seg1.setBackground(Color.DARK_GRAY);
		seg1.setForeground(Color.CYAN);
		seg1.setCaretColor(Color.CYAN);
		optionpanel.add(seg1);
		
		colonOne = new JLabel();
		colonOne.setText(":");
		colonOne.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		colonOne.setBounds(170, 230, 30, 30);
		optionpanel.add(colonOne);
		
		seg2 = new JTextField();
		seg2.setBounds(185, 230, 60, 30);  
		seg2.setBackground(Color.DARK_GRAY);
		seg2.setForeground(Color.CYAN);
		seg2.setCaretColor(Color.CYAN);
		optionpanel.add(seg2);
		
		seg3 = new JTextField();
		seg3.setBounds(250, 230, 60, 30); 
		seg3.setBackground(Color.DARK_GRAY);
		seg3.setForeground(Color.CYAN);
		seg3.setCaretColor(Color.CYAN);
		optionpanel.add(seg3);
		
		colonTwo = new JLabel();
		colonTwo.setText(":");
		colonTwo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		colonTwo.setBounds(320, 230, 30, 30);
		optionpanel.add(colonTwo);
		
		seg4 = new JTextField();
		seg4.setBounds(335, 230, 60, 30);  
		seg4.setBackground(Color.DARK_GRAY);
		seg4.setForeground(Color.CYAN);
		seg4.setCaretColor(Color.CYAN);
		optionpanel.add(seg4);
		
		seg5 = new JTextField();
		seg5.setBounds(400, 230, 60, 30);  
		seg5.setBackground(Color.DARK_GRAY);
		seg5.setForeground(Color.CYAN);
		seg5.setCaretColor(Color.CYAN);
		optionpanel.add(seg5);
		
		colonThree = new JLabel();
		colonThree.setText(":");
		colonThree.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		colonThree.setBounds(470, 230, 30, 30);
		optionpanel.add(colonThree);
		
		seg6 = new JTextField();
		seg6.setBounds(485, 230, 60, 30);  
		seg6.setBackground(Color.DARK_GRAY);
		seg6.setForeground(Color.CYAN);
		seg6.setCaretColor(Color.CYAN);
		optionpanel.add(seg6);
		
		dataStoreBox = new JTextArea();
		dataStoreBox.setBounds(600, 300, 500, 400);
		dataStoreBox.setBackground(Color.DARK_GRAY);
		dataStoreBox.setForeground(Color.CYAN);
		dataStoreBox.setCaretColor(Color.CYAN);
		dataStoreBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		JScrollPane dsbPane = new JScrollPane(dataStoreBox);
		dsbPane.setBounds(600, 300, 500, 400);
		dsbPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		optionpanel.add(dsbPane);
		
		setCompetitionNameBox = new JTextArea();
		setCompetitionNameBox.setBounds(300, 300, 500, 400);
		setCompetitionNameBox.setBackground(Color.DARK_GRAY);
		setCompetitionNameBox.setForeground(Color.CYAN);
		setCompetitionNameBox.setCaretColor(Color.CYAN);
		setCompetitionNameBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		JScrollPane scnPane = new JScrollPane(setCompetitionNameBox);
		scnPane.setBounds(100, 300, 500, 400);
		scnPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		optionpanel.add(scnPane);
		
		JLabel scnText = new JLabel();
		scnText.setText("SetCompetitionName.java");
		scnText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		scnText.setBounds(100, 280, 200, 20);
		optionpanel.add(scnText);
		
		JLabel dsBox = new JLabel();
		dsBox.setText("DataStore.java");
		dsBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		dsBox.setBounds(600, 280, 200, 20);
		optionpanel.add(dsBox);
		
		JLabel versionLabel = new JLabel();
		versionLabel.setText("TRC Scouting App Layout Editor version " + VERSION_ID + " by Victor Du");
		versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		versionLabel.setBounds(460, 750, 300, 20);
		optionpanel.add(versionLabel);
		
		setCompetitionNameBox.setText("\n" + 
				"		   ___  _____  _____ \n" + 
				"		  /   ||  _  |/ __  \\ Titan\n" + 
				"		 / /| || |_| |`' / /' Robotics\n" + 
				"		/ /_| |\\____ |  / /   Club\n " + 
				"		\\___  |.___/ /./ /___\n" + 
				"		    |_/\\____/ \\_____/\n" + 
				"	                     \n" + 
				"	                     \n"
				+ "After being introduced to the FIRST Robotics Competition by\nBellarmine Prep (Team 360) during the 2000 season, Larry Barello\nsought to enter the 2001 Competition closer to home at his\ndaughter’s school, the Bellevue International School (IS). At the\nend of Ryan McElroy’s Junior year at IS, Larry came with team 360\nand made a presentation with the robot he had helped build the\nprevious year. Interested students signed up, and Ryan happened to\nsign up first. That summer, Larry contacted Ryan about getting the\nclub started. \n" + 
				"\n" + 
				"Ryan wrote a constitution for the new club and guided it\nthrough the ASB approval process, and thus the TRC was born.\n" + 
				"\n" + 
				"Due to the nature of IS as a college prep alternative public school,\nthere were and are no engineering related classes. There were and\nare no woodworking class, metal class, auto class, or computer\nclass. For this reason, the Titan Robotics Club became immediately\npopular with students who longed for these unavailable\nopportunities." + 
				"");
		dataStoreBox.setText("' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' # ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' ' ' ' ' + ' ' # ' ' # # # ' ' ' ' ' ' ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' ' ' # # # # ' # ' + # # # ' ' ' ' ' ' ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' ' ' # # # # # # + # # # # + ' ' ' ' ' ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' ' ' # # # # # ' + # # # # # # + ' # # ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' ' # # # # + ' ' ; ' ' ' + # # # # # # # ' ' ' ' ' ' \n" + 
				"' ' ' ' ' # # # # # # ' ' ' ' ' ' ' ' # ' ' ' # # # # # ' ' ' ' ' ' \n" + 
				"' ' ' ' # # # # # # ' ' ' ' ' ' ' ' ' ' # ' ' ' # # # + ' ' ' ' ' ' \n" + 
				"' ' ' ' + # # # + ' ' ' ' + ' ' ' ' # ' # ' ' ' ' # # # ' ' ' ' ' ' \n" + 
				"' ' ' ' ' # # # ' ' ' ' ' # ' ' ' ' # ' # ' ' ' ' ' # # # ' ' ' ' ' \n" + 
				"' ' ' ' ' # # ' ' ' ' ' ' # ' ' ' + # + # ' ' ' ' ' ' # # # # # ' ' \n" + 
				"' ' ' ' # # + ' ' ' ' ' + + ' ' ' # ' # # ' ' ' ' ' ' + # # # # ' ' \n" + 
				"' ' # # # # ' ' + + + + + # + + + + + + + + + + + + + ' # # # # ' ' \n" + 
				"' + # # # # ' ' + # # # ; ' ' + + # # # ' ' ' # # # ' ' # # # ' ' ' \n" + 
				"' # # # # + ' ' ' ' # + ' + # # # # + ' ' # # # ' ' ' ' + # # ' ' ' \n" + 
				"' # # # # ' ' ' ' ' ' ' + + + + # + + + + + ' ' ' ' ' ' ' # # ' ' ' \n" + 
				"' ' ' # # ' ' ' ' ' ' ' ' ' + ' ' ' ' + ' ' ' ' ' ' ' ' ' # # ' ' ' \n" + 
				"' ' ' # # ' ' ' ' ' ' ' ' ' # ' ' ' + # ' ' ' ' ' ' ' ' ' # # # ' ' \n" + 
				"' ' ' # # ' ' ' ' ' ' ' ' # # ' ' ' + # # ' ' ' ' ' ' ' ' # # # # ' \n" + 
				"' ' ' # # + ' ' ' ' ' ' ' # ' + ' ' + ' # ' ' ' ' ' ' ' # # # # # ' \n" + 
				"' ' # # # # ' ' ' ' ' ' # # + + ' ' + + # # ' ' ' ' ' ' # # # # ' ' \n" + 
				"' ' # # # # ' ' ' ' ' + # ; # # ' ' # # ' # + ' ' ' ' ' # # ; ' ' ' \n" + 
				"' ' # # # # # ' ' ' ' # # ' ' + ' ' + ' ' + # ' ' ' ' # # + ' ' ' ' \n" + 
				"' ' ' + ' # # + ' ' # # ' ' ' ' ' ' + ' ' ' # # ' ' ' # # ' ' ' ' ' \n" + 
				"' ' ' ' ' ' # # ' ' # + ' ' ' ' ' ' + ' ' ' ' # ' ' # # # ' ' ' ' ' \n" + 
				"' ' ' ' ' ' # # # + # ' ' ' ' + ' ' + ' ' ' ' # + # # # # # ' ' ' ' \n" + 
				"' ' ' ' ' ' # # # # + ' ' ' ' + + + + ' ' ' ' + # # # # # + ' ' ' ' \n" + 
				"' ' ' ' ' ' # # # # # # ' ' ' # # # # ' ' + # # # + ' # # ' ' ' ' ' \n" + 
				"' ' ' ' ' ' + # # # # # # # # # + + # # # # # # ' ' ' ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' + ' ' + # # # # # # # # # # # # ' ' ' ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' ' ' ' ' ' # # # # + + ' # # # # ' ' ' ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' ' ' ' ' ' # # # ' ' ' ' ' # + ' ' ' ' ' ' ' ' ' ' ' \n" + 
				"' ' ' ' ' ' ' ' ' ' ' ' ' ' ' + ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' ' \n" + 
				"");
		
		generateDataButton = new JButton("+");
		generateDataButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
		generateDataButton.setText("GENERATE");
		generateDataButton.setForeground(Color.CYAN);
		generateDataButton.setBackground(Color.DARK_GRAY);
		generateDataButton.setBounds(800, 100, 200, 100);
		generateDataButton.addActionListener(new ActionListener()
		  {
			   public void actionPerformed(ActionEvent ae)
			   {
				   try
				   {
					   if (DataStore.getListSize() == 0)
					   {
						   JFrame kawaii = new JFrame("Error");
						   kawaii.setResizable(false);
						   kawaii.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
						   JPanel inputPanel = new JPanel();
						   inputPanel.setLayout(null);
						   inputPanel.setBackground(Color.DARK_GRAY);
						   JLabel kawaiichan = new JLabel();
						   JButton button99 = new JButton("OK");
						   button99.setBounds(375, 100, 50, 50);
						   button99.setFont(new Font("Segoe UI", Font.BOLD, 20));
						   button99.setMargin(new Insets(0, 0, 0, 0));
						   button99.setBackground(Color.DARK_GRAY);
						   button99.setForeground(Color.CYAN);
						   kawaii.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						   kawaiichan.setText("Please add at least one form before generating code.");
						   kawaiichan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
						   kawaiichan.setForeground(Color.CYAN);
						   kawaiichan.setBounds(150, 40, 800, 50);
						   inputPanel.add(button99);
						   inputPanel.add(kawaiichan);
						   kawaii.add(inputPanel);
						   kawaii.setSize(800, 200);
						   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
						   kawaii.setLocation(dim.width/2-kawaii.getSize().width/2, dim.height/2-kawaii.getSize().height/2);
						   kawaii.setVisible(true);
						   button99.addActionListener(new ActionListener()
						   {
							   public void actionPerformed(ActionEvent ae)
							   {
								   kawaii.dispose();
							   }
						   });
					   }
					   else
					   {
						   setCompetitionNameBox.setText("");
						   dataStoreBox.setText("");
					       DataStore.setPrevCfg(new PreviewConfig(seg1.getText().toString(), DataStore.getElementFromList(DataStore.getElementIndexByCSVIndex(DataStore.getElementIndexByCSVIndex(Integer.parseInt(seg2.getText().toString())))), seg3.getText().toString(), DataStore.getElementFromList(DataStore.getElementIndexByCSVIndex(Integer.parseInt(seg4.getText().toString()))), seg5.getText().toString(), DataStore.getElementFromList(DataStore.getElementIndexByCSVIndex(Integer.parseInt(seg6.getText().toString())))));
					       dataStoreBox.setText(DataStore.generateDataStoreClass());
					       setCompetitionNameBox.setText(DataStore.generateSetCompetitionNameClass());   
					   }
				   }
				   catch (ArrayIndexOutOfBoundsException arg0)
				   {
					   arg0.printStackTrace();
					   JFrame kawaii = new JFrame("Error");
					   kawaii.setResizable(false);
					   kawaii.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
					   JPanel inputPanel = new JPanel();
					   inputPanel.setLayout(null);
					   inputPanel.setBackground(Color.DARK_GRAY);
					   JLabel kawaiichan = new JLabel();
					   JButton button99 = new JButton("OK");
					   button99.setBounds(375, 100, 50, 50);
					   button99.setFont(new Font("Segoe UI", Font.BOLD, 20));
					   button99.setMargin(new Insets(0, 0, 0, 0));
					   button99.setBackground(Color.DARK_GRAY);
					   button99.setForeground(Color.CYAN);
					   kawaii.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					   kawaiichan.setText("All fields must be filled in correctly before generating the classes.");
					   kawaiichan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
					   kawaiichan.setForeground(Color.CYAN);
					   kawaiichan.setBounds(150, 40, 800, 50);
					   inputPanel.add(button99);
					   inputPanel.add(kawaiichan);
					   kawaii.add(inputPanel);
					   kawaii.setSize(800, 200);
					   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					   kawaii.setLocation(dim.width/2-kawaii.getSize().width/2, dim.height/2-kawaii.getSize().height/2);
					   kawaii.setVisible(true);
					   button99.addActionListener(new ActionListener()
					   {
						   public void actionPerformed(ActionEvent ae)
						   {
							   kawaii.dispose();
						   }
					   });
				   }
				   catch (Exception arg0)
				   {
					   arg0.printStackTrace();
					   JFrame kawaii = new JFrame("Error");
					   kawaii.setResizable(false);
					   kawaii.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
					   JPanel inputPanel = new JPanel();
					   inputPanel.setLayout(null);
					   inputPanel.setBackground(Color.DARK_GRAY);
					   JLabel kawaiichan = new JLabel();
					   JButton button99 = new JButton("OK");
					   button99.setBounds(375, 100, 50, 50);
					   button99.setFont(new Font("Segoe UI", Font.BOLD, 20));
					   button99.setMargin(new Insets(0, 0, 0, 0));
					   button99.setBackground(Color.DARK_GRAY);
					   button99.setForeground(Color.CYAN);
					   kawaii.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					   kawaiichan.setText("All fields must be filled in before generating the classes.");
					   kawaiichan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
					   kawaiichan.setForeground(Color.CYAN);
					   kawaiichan.setBounds(150, 40, 800, 50);
					   inputPanel.add(button99);
					   inputPanel.add(kawaiichan);
					   kawaii.add(inputPanel);
					   kawaii.setSize(800, 200);
					   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					   kawaii.setLocation(dim.width/2-kawaii.getSize().width/2, dim.height/2-kawaii.getSize().height/2);
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
		optionpanel.add(generateDataButton);
				
        list = new JList<ElementHandler>(l1);  
        list.setBackground(Color.DARK_GRAY);
        list.setForeground(Color.CYAN);
        listPane = new JScrollPane(list);
        listPane.setBounds(100, 100, 250, 100);
        optionpanel.add(listPane);
        optionpanel.add(add_entry);
        optionpanel.add(del_entry);
		home_frame.add(optionpanel);
		home_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		home_frame.setSize(1200, 800);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		home_frame.setLocation(dim.width/2-home_frame.getSize().width/2, dim.height/2-home_frame.getSize().height/2);
		home_frame.setVisible(true);
	}

	public static void openFormAdderForm()
	{	
		JFrame inputFrame = new JFrame("Add Form");
		inputFrame.setResizable(false);
		inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inputFrame.setSize(500, 350);
		inputFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setBackground(Color.CYAN);
		
		JTextField formName = new JTextField();  
		formName.setBackground(Color.DARK_GRAY);
		formName.setForeground(Color.CYAN);
		formName.setCaretColor(Color.CYAN);
		formName.setBounds(100, 25, 200, 30);  
		inputPanel.add(formName);
		
		JLabel fNameText = new JLabel();
		fNameText.setText("Form Name:");
		fNameText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		fNameText.setBounds(10, 30, 100, 20);
		inputPanel.add(fNameText);
		
		DataStore.DataTypes = new String[]{"Integer", "Double", "Boolean", "String"};
	    DataStore.f_dtype = new JComboBox<String>(DataStore.DataTypes);
	    DataStore.f_dtype.setBounds(80, 170, 120, 20);
		inputPanel.add(DataStore.f_dtype);
		DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<String>(DataStore.DataTypes);
		DataStore.f_dtype.setModel(model2);
		DataStore.f_dtype.setBackground(Color.DARK_GRAY);
		DataStore.f_dtype.setForeground(Color.CYAN);
		
		String[] types = {"EditText", "Spinner", "CheckBox"};
	    JComboBox<String> f_mtype = new JComboBox<String>(types);
	    f_mtype.setBackground(Color.DARK_GRAY);
		f_mtype.setForeground(Color.CYAN);
	    f_mtype.setBounds(100, 60, 120, 20);
	    f_mtype.addItemListener (new ItemListener () 
	    {
	    	@Override
	        public void itemStateChanged(ItemEvent e)
	        {
	        	if (f_mtype.getSelectedItem().toString().matches("EditText"))
	        	{
	        		DataStore.DataTypes = new String[]{"Integer", "Double", "Boolean", "String"};
	        		model2.removeAllElements();
	        		for(String s : DataStore.DataTypes)
	        		{
	        			model2.addElement(s);
	        		}
	        	}
	        	else if (f_mtype.getSelectedItem().toString().matches("Spinner"))
	        	{
	        		DataStore.DataTypes = new String[]{"String"};
	        		model2.removeAllElements();
	        		for(String s : DataStore.DataTypes)
	        		{
	        			model2.addElement(s);
	        		}
	        	}
	        	else
	        	{
	        		DataStore.DataTypes = new String[]{"Boolean"};
	        		model2.removeAllElements();
	        		for(String s : DataStore.DataTypes)
	        		{
	        			model2.addElement(s);
	        		}
	        	}
	        	inputPanel.add(DataStore.f_dtype);
	        }
	    });
		inputPanel.add(f_mtype);
		
		JLabel fTypesText = new JLabel();
		fTypesText.setText("Form Type:");
		fTypesText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		fTypesText.setBounds(15, 60, 80, 20);
		inputPanel.add(fTypesText);
	    
		int time = (int)(System.currentTimeMillis() / 1000.0);
		int endRes = new Random(time).nextInt(1337) + 1; 
		String parseVarDefaultText = ("varDef" + DataStore.getListSize() + "_$OwOTfKa" + endRes + "TI" + time + "O");
		
		JTextField parseVarName = new JTextField();
		parseVarName.setBackground(Color.DARK_GRAY);
		parseVarName.setForeground(Color.CYAN);
		parseVarName.setCaretColor(Color.CYAN);
		parseVarName.setBounds(135, 90, 200, 30);  
		parseVarName.setText(parseVarDefaultText);
		inputPanel.add(parseVarName);
		
		JLabel parseVarText = new JLabel();
		parseVarText.setText("Parse Var. Name:");
		parseVarText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		parseVarText.setBounds(10, 95, 120, 20);
		inputPanel.add(parseVarText);
		
		JTextField CSVPosForm = new JTextField(); 
		CSVPosForm.setBackground(Color.DARK_GRAY);
		CSVPosForm.setForeground(Color.CYAN);
		CSVPosForm.setCaretColor(Color.CYAN);
		CSVPosForm.setBounds(80, 130, 80, 30);  
		CSVPosForm.setText(DataStore.getListSize() + "");
		inputPanel.add(CSVPosForm);
		
		JLabel CSVPosText = new JLabel();
		CSVPosText.setText("CSV Idx:");
		CSVPosText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		CSVPosText.setBounds(15, 135, 80, 20);
		inputPanel.add(CSVPosText);
		
		JLabel DataTypeText = new JLabel();
		DataTypeText.setText("Data Type:");
		DataTypeText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		DataTypeText.setBounds(10, 170, 80, 20);
		inputPanel.add(DataTypeText);
		
		JTextField descriptionForm = new JTextField();  
		descriptionForm.setBackground(Color.DARK_GRAY);
		descriptionForm.setForeground(Color.CYAN);
		descriptionForm.setCaretColor(Color.CYAN);
		descriptionForm.setBounds(18, 220, 150, 30);  
		inputPanel.add(descriptionForm);
		
		JLabel descriptionText = new JLabel();
		descriptionText.setText("Description:");
		descriptionText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descriptionText.setBounds(20, 200, 120, 20);
		inputPanel.add(descriptionText);
		
		JButton confirm_Data = new JButton("OK");
		confirm_Data.setFont(new Font("Segoe UI", Font.BOLD, 18));
		confirm_Data.setText("OK");
		confirm_Data.setBackground(Color.DARK_GRAY);
		confirm_Data.setForeground(Color.CYAN);
		confirm_Data.setBounds(400, 60, 80, 80);
		inputPanel.add(confirm_Data);
		
		confirm_Data.addActionListener(new ActionListener()
		{
			   public void actionPerformed(ActionEvent ae)
			   {
				   int csvpos = Integer.parseInt(CSVPosForm.getText().toString());
				   String formname = formName.getText().toString();
				   String formtypestr = f_mtype.getSelectedItem().toString();
				   int formtype = -1;
				   if (formtypestr.matches("EditText"))
				   {
					   formtype = EditText;
				   }
				   else if (formtypestr.matches("Spinner"))
				   {
					   formtype = Spinner;
				   }
				   else
				   {
					   formtype = CheckBox;
				   }
				   String datatypestr = DataStore.f_dtype.getSelectedItem().toString();
				   int datatype = -1;
				   if (datatypestr.matches("Integer"))
				   {
					   datatype = Integer_;
				   }
				   else if (datatypestr.matches("Double"))
				   {
					   datatype = Double_;
				   }
				   else if (datatypestr.matches("Boolean"))
				   {
					   datatype = Boolean_;
				   }
				   else
				   {
					   datatype = String_;
				   }
				   String description = descriptionForm.getText().toString();
				   DataStore.addElementToList(new ElementHandler(csvpos, formname, formtype, datatype, formname, parseVarName.getText().toString(), description));
			       inputFrame.dispose();
			   }
		});
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		inputFrame.setLocation(dim.width/2-inputFrame.getSize().width/2, dim.height/2-inputFrame.getSize().height/2);
		inputFrame.add(inputPanel);
		inputFrame.setVisible(true);

	}

	
}
