package layouteditor;

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
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		DataStore.elementList.add(new ElementHandler(1337, "TestTextBox", CheckBox, Boolean_, "borbTest", "testVar", "test option"));
		DataStore.elementList.add(new ElementHandler(1338, "TestTextBox2", CheckBox, Boolean_, "borbTest2", "testVar2", "test option2"));
		DataStore.elementList.add(new ElementHandler(1339, "TestTextBox3", CheckBox, Boolean_, "borbTest3", "testVar3", "test option3"));
		DataStore.prevCfg = new PreviewConfig("T1", DataStore.elementList.get(0), "T2", DataStore.elementList.get(1), "T3", DataStore.elementList.get(2));
		System.out.println(DataStore.generateDataStoreClass());
	}


	
}
