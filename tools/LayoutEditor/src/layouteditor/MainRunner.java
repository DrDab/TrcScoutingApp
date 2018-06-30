package layouteditor;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

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
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		home_frame = new JFrame("TRC Scouting App Layout Editor");
		home_frame.setResizable(false);
		optionpanel = new JPanel();
		optionpanel.setLayout(null);
		l1 = new DefaultListModel<>();  
        // l1.addElement("Item1");  
        list = new JList<ElementHandler>(l1);  
        list.setBounds(100, 100, 250, 100);  
        optionpanel.add(list);
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
