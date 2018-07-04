package layouteditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class MainRunner 
{
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
		// TODO Auto-generated method stub
		home_frame = new JFrame("TRC Scouting App Layout Editor");
		home_frame.setResizable(false);
		optionpanel = new JPanel();
		optionpanel.setLayout(null);
		l1 = new DefaultListModel<>();  
		add_entry = new JButton("+");
		add_entry.setFont(new Font("Verdana", Font.BOLD, 18));
		add_entry.setText("+");
		add_entry.setBounds(100, 45, 50, 50);
		del_entry = new JButton("-");
		del_entry.setFont(new Font("Verdana", Font.BOLD, 20));
		del_entry.setText("-");
		del_entry.setBounds(160, 45, 50, 50);
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
		optionpanel.add(seg1);
		
		colonOne = new JLabel();
		colonOne.setText(":");
		colonOne.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		colonOne.setBounds(170, 230, 30, 30);
		optionpanel.add(colonOne);
		
		seg2 = new JTextField();
		seg2.setBounds(185, 230, 60, 30);  
		optionpanel.add(seg2);
		
		seg3 = new JTextField();
		seg3.setBounds(250, 230, 60, 30);  
		optionpanel.add(seg3);
		
		colonTwo = new JLabel();
		colonTwo.setText(":");
		colonTwo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		colonTwo.setBounds(320, 230, 30, 30);
		optionpanel.add(colonTwo);
		
		seg4 = new JTextField();
		seg4.setBounds(335, 230, 60, 30);  
		optionpanel.add(seg4);
		
		seg5 = new JTextField();
		seg5.setBounds(400, 230, 60, 30);  
		optionpanel.add(seg5);
		
		colonThree = new JLabel();
		colonThree.setText(":");
		colonThree.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		colonThree.setBounds(470, 230, 30, 30);
		optionpanel.add(colonThree);
		
		seg6 = new JTextField();
		seg6.setBounds(485, 230, 60, 30);  
		optionpanel.add(seg6);
		
		dataStoreBox = new JTextArea();
		dataStoreBox.setBounds(600, 300, 500, 400);
		dataStoreBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		JScrollPane dsbPane = new JScrollPane(dataStoreBox);
		dsbPane.setBounds(600, 300, 500, 400);
		dsbPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		optionpanel.add(dsbPane);
		
		setCompetitionNameBox = new JTextArea();
		setCompetitionNameBox.setBounds(300, 300, 500, 400);
		setCompetitionNameBox.setFont(new Font("Consolas", Font.PLAIN, 12));
		JScrollPane scnPane = new JScrollPane(setCompetitionNameBox);
		scnPane.setBounds(100, 300, 500, 400);
		scnPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		optionpanel.add(scnPane);
		
		generateDataButton = new JButton("+");
		generateDataButton.setFont(new Font("Verdana", Font.BOLD, 18));
		generateDataButton.setText("GENERATE");
		generateDataButton.setBounds(800, 100, 200, 100);
		generateDataButton.addActionListener(new ActionListener()
		  {
			   public void actionPerformed(ActionEvent ae)
			   {
				   setCompetitionNameBox.setText("");
				   dataStoreBox.setText("");
			       DataStore.setPrevCfg(new PreviewConfig(seg1.getText().toString(), DataStore.getElementFromList(Integer.parseInt(seg2.getText().toString())), seg3.getText().toString(), DataStore.getElementFromList(Integer.parseInt(seg4.getText().toString())), seg5.getText().toString(), DataStore.getElementFromList(Integer.parseInt(seg6.getText().toString()))));
			       dataStoreBox.setText(DataStore.generateDataStoreClass());
			       setCompetitionNameBox.setText(DataStore.generateSetCompetitionNameClass());
			   }
			   
		});
		optionpanel.add(generateDataButton);
				

		
	//	JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
	//	BoundedRangeModel brm = dataStoreBox
	//	scrollBar.setModel(brm);
	   // panel.add(textField);
	   // panel.add(scrollBar)
		
        list = new JList<ElementHandler>(l1);  
        //list.setBounds(100, 100, 250, 100);  
        listPane = new JScrollPane(list);
        listPane.setBounds(100, 100, 250, 100);
        optionpanel.add(listPane);
        optionpanel.add(add_entry);
        optionpanel.add(del_entry);
		home_frame.add(optionpanel);
		home_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		home_frame.setSize(1200, 800);
		home_frame.setVisible(true);
		
		// DataStore.setPrevCfg(new PreviewConfig("T1", DataStore.getElementFromList(0), "T2", DataStore.getElementFromList(1), "T3", DataStore.getElementFromList(2)));;
		// System.out.println(DataStore.generateSetCompetitionNameClass());
	}

	public static void openFormAdderForm()
	{	
		// 0 EditText available options (ok): Integer, Double, Boolean, String
		// 1 Spinner available options (ok): String
		// 2 CheckBox available options (ok): Boolean
		
		// TODO: Add description form
		
		JFrame inputFrame = new JFrame("Add Form");
		inputFrame.setResizable(false);
		inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inputFrame.setSize(500, 350);
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(null);
		
		JTextField formName = new JTextField();  
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
		
		String[] types = {"EditText", "Spinner", "CheckBox"};
	    JComboBox<String> f_mtype = new JComboBox<String>(types);
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
	    
		JTextField parseVarName = new JTextField();  
		parseVarName.setBounds(135, 90, 200, 30);  
		inputPanel.add(parseVarName);
		
		JLabel parseVarText = new JLabel();
		parseVarText.setText("Parse Var. Name:");
		parseVarText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		parseVarText.setBounds(10, 95, 120, 20);
		inputPanel.add(parseVarText);
		
		JTextField CSVPosForm = new JTextField();  
		CSVPosForm.setBounds(80, 130, 80, 30);  
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
		descriptionForm.setBounds(18, 220, 150, 30);  
		inputPanel.add(descriptionForm);
		
		JLabel descriptionText = new JLabel();
		descriptionText.setText("Description:");
		descriptionText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		descriptionText.setBounds(20, 200, 120, 20);
		inputPanel.add(descriptionText);
		
		JButton confirm_Data = new JButton("OK");
		confirm_Data.setFont(new Font("Verdana", Font.BOLD, 18));
		confirm_Data.setText("OK");
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
				   DataStore.addElementToList(new ElementHandler(csvpos, formname, formtype, datatype, parseVarName.getText().toString(), formname, description));
			       inputFrame.dispose();
			   }
		});
		
		inputFrame.add(inputPanel);
		inputFrame.setVisible(true);

	}

	
}
