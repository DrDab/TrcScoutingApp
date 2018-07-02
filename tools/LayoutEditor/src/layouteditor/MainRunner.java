package layouteditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MainRunner 
{
	public static final int EditText = 1;
	public static final int Spinner = 2;
	public static final int CheckBox = 3;
	
	public static int Integer_ = 1;
	public static int Double_ = 2;
	public static int Boolean_ = 3;
	public static int String_ = 4;
	
	public static JFrame home_frame;
	public static JPanel optionpanel;
	public static DefaultListModel<ElementHandler> l1;
	public static JList<ElementHandler> list;
	public static JButton add_entry;
	public static JButton del_entry;
	public static JScrollPane listPane;
	
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
		del_entry.setBounds(150, 45, 50, 50);
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
		
		
		DataStore.addElementToList(new ElementHandler(1337, "TestTextBox", CheckBox, Boolean_, "borbTest", "testVar", "test option"));
		DataStore.addElementToList(new ElementHandler(1338, "TestTextBox2", CheckBox, Boolean_, "borbTest2", "testVar2", "test option2"));
		DataStore.addElementToList(new ElementHandler(1339, "TestTextBox3", CheckBox, Boolean_, "borbTest3", "testVar3", "test option3"));
		
		DataStore.setPrevCfg(new PreviewConfig("T1", DataStore.getElementFromList(0), "T2", DataStore.getElementFromList(1), "T3", DataStore.getElementFromList(2)));;
		System.out.println(DataStore.generateSetCompetitionNameClass());
	}

	public static void openFormAdderForm()
	{	
		// 0 EditText available options (ok): Integer, Double, Boolean, String
		// 1 Spinner available options (ok): String
		// 2 CheckBox available options (ok): Boolean
		
		JFrame inputFrame = new JFrame("Add Form");
		inputFrame.setResizable(false);
		inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inputFrame.setSize(500, 250);
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
		
		JButton confirm_Data = new JButton("OK");
		confirm_Data.setFont(new Font("Verdana", Font.BOLD, 18));
		confirm_Data.setText("OK");
		confirm_Data.setBounds(400, 60, 80, 80);
		inputPanel.add(confirm_Data);
		
		inputFrame.add(inputPanel);
		inputFrame.setVisible(true);

	}

	
}
