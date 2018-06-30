package layouteditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainRunner 
{
	public static final int EditText = 1;
	public static final int Spinner = 2;
	public static final int CheckBox = 3;
	
	public static int Integer_ = 1;
	public static int Double_ = 2;
	public static int Boolean_ = 3;
	public static int String_ = 4;
	
	// EditText available options (ok): Integer, Double, Boolean, String
	// Spinner available options (ok): String
	// CheckBox available options (ok): Boolean
	
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


	
}
