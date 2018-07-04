package layouteditor;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;

public class DataStore
{
	private static ArrayList<ElementHandler> elementList = new ArrayList<ElementHandler>();
	private static PreviewConfig prevCfg = null;
	private static HashMap<ElementHandler, Integer> kemono = new HashMap<ElementHandler, Integer>();
	
	public static final int EditText = 1;
	public static final int Spinner = 2;
	public static final int CheckBox = 3;
	
	public static final int Integer_ = 1;
	public static final int Double_ = 2;
	public static final int Boolean_ = 3;
	public static final int String_ = 4;
	
	public static String[] DataTypes;
	public static JComboBox<String> f_dtype;
	
	public static ElementHandler getElementFromList(int idx)
	{
		return elementList.get(idx);
	}
	
	public static int getElementIndexByCSVIndex(int csvidx)
	{
		for (int i = 0; i < elementList.size(); i++)
		{
			if (elementList.get(i).getCSVPos() == csvidx)
			{
				return i;
			}
		}
		return -1;
	}
	
	public static void addElementToList(ElementHandler e)
	{
		elementList.add(e);
		kemono.put(e, elementList.size() - 1);
		MainRunner.l1.addElement(e);
	}
	
	public static void removeElementFromList(ElementHandler e)
	{
		int rmidx = kemono.get(e);
		elementList.remove(rmidx);
		kemono.remove(e);
		MainRunner.l1.removeElementAt(rmidx);
	}
	
	public static void removeElementFromList(int idx)
	{
		MainRunner.l1.removeElementAt(idx);
		ElementHandler eh = elementList.get(idx);
		kemono.remove(eh);
		elementList.remove(idx);
	}
	
	public static void setPrevCfg(PreviewConfig pc)
	{
		prevCfg = pc;
	}
	
	public static PreviewConfig getPrevCfg()
	{
		return prevCfg;
	}
	
	public static String generateSetCompetitionNameClass()
	{
		String wholeString = "";
		String begin = "// THIS CODE WAS GENERATED AUTOMATICALLY BY Victor's SmartBuilder script. DO NOT MODIFY.\n\npackage trc3543.trcscoutingapp;\n" + 
				"\n" + 
				"import android.content.Intent;\n" + 
				"import android.os.Environment;\n" + 
				"import android.support.design.widget.Snackbar;\n" + 
				"import android.support.v7.app.AppCompatActivity;\n" + 
				"import android.os.Bundle;\n" + 
				"import android.util.Log;\n" + 
				"import android.view.View;\n" + 
				"import android.widget.ArrayAdapter;\n" + 
				"import android.widget.CheckBox;\n" + 
				"import android.widget.EditText;\n" + 
				"import android.widget.Spinner;\n" + 
				"\n" + 
				"import java.io.File;\n" + 
				"import java.io.FileWriter;\n" + 
				"import java.io.IOException;\n" + 
				"import java.io.PrintWriter;\n" + 
				"\n" + 
				"@SuppressWarnings(\"all\")\n" + 
				"public class SetCompetitionName extends AppCompatActivity\n" + 
				"{\n" + 
				"    /**\n" + 
				"     *\n" + 
				"     *  Copyright (c) 2018 Titan Robotics Club, _c0da_ (Victor Du)\n" + 
				"     *\n" + 
				"     *	Permission is hereby granted, free of charge, to any person obtaining a copy\n" + 
				"     *	of this software and associated documentation files (the \"Software\"), to deal\n" + 
				"     *	in the Software without restriction, including without limitation the rights\n" + 
				"     *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" + 
				"     *	copies of the Software, and to permit persons to whom the Software is\n" + 
				"     *	furnished to do so, subject to the following conditions:\n" + 
				"     *\n" + 
				"     *	The above copyright notice and this permission notice shall be included in all\n" + 
				"     *	copies or substantial portions of the Software.\n" + 
				"     *\n" + 
				"     *	THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" + 
				"     *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" + 
				"     *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" + 
				"     *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" + 
				"     *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" + 
				"     *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" + 
				"     *	SOFTWARE.\n" + 
				"     */\n" + 
				"\n" + 
				"    public static final boolean USE_DEBUG = false;\n" + 
				"\n" + 
				"    static int editingoption = -1;\n\n\n    // BEGIN AUTO-GENERATED VARIABLES\n";
		wholeString += begin;
		// FIRST: Generate all static variables.
		for(int i = 0; i < elementList.size(); i++)
		{
			ElementHandler tmp = elementList.get(i);
			String declareString = "    static ";
			if(tmp.getDataType() == Integer_)
			{
				declareString += "int ";
			}
			else if (tmp.getDataType() == Double_)
			{
				declareString += "double ";
			}
			else if (tmp.getDataType() == Boolean_)
			{
				declareString += "boolean ";
			}
			else
			{
				declareString += "String ";
			}
			declareString += tmp.getReturnVariableName() + ";";
			declareString += "\n";
			wholeString += declareString;
		}
		wholeString += "\n    // END AUTO-GENERATED VARIABLES\n";
		
		
		// SECOND: Generate onCreate method code.
		wholeString +=
				"    \n    @Override\n" + 
				"    protected void onCreate(Bundle savedInstanceState)\n" + 
				"    {\n" + 
				"        super.onCreate(savedInstanceState);\n" + 
				"        setContentView(R.layout.activity_set_competition_name2);\n" + 
				"        try\n" + 
				"        {\n" + 
				"            DataStore.parseTeamNum();\n" + 
				"            DataStore.parseFirstName();\n" + 
				"            DataStore.parseLastName();\n" + 
				"            DataStore.parseDirectSave();\n" + 
				"        }\n" + 
				"        catch (IOException e)\n" + 
				"        {\n" + 
				"            // TODO Auto-generated catch block\n" + 
				"        }\n" + 
				"\n" + 
				"        try\n" + 
				"        {\n" + 
				"            Intent myIntent = getIntent(); // gets the previously created intent\n" + 
				"            String editoptionstr = myIntent.getStringExtra(\"EditOption\"); // will return option to edit on the fly\n" + 
				"            Log.d(\"SetCompetitionName\", \"editoptionsstr=\\\"\" + editoptionstr + \"\\\"\");\n" + 
				"            editingoption = Integer.parseInt(editoptionstr);\n" + 
				"            Log.d(\"SetCompetitionName\", \"Got edit option: \" + editingoption);\n" + 
				"        }\n" + 
				"        catch (Exception e)\n" + 
				"        {\n" + 
				"            Log.d(\"SetCompetitionName\",\"You shouldn't see this message\");\n" + 
				"            editingoption = -1;\n" + 
				"        }\n" + 
				"\n" + 
				"        // populate the boxes if already filled.\n" + 
				"        if (editingoption != -1)\n" + 
				"        {\n"+
				"            String read = DataStore.CsvFormattedContests.get(editingoption);\n" + 
				"            Log.d(\"SetCompetitionName\", editingoption + \" \" + read);\n" + 
				"            String[] OwOWhatsThis = read.split(\",\");\n" + 
				"\n";
		
		for(int i = 0; i < elementList.size(); i++)
		{
			String kawai = "            // populate the ";
			ElementHandler tmp = elementList.get(i);
			kawai += tmp.getDescription() + ".\n";
			if (tmp.getFormType() == CheckBox)
			{
				kawai += "            CheckBox " + tmp.getHandlerName() +" = (CheckBox) findViewById(R.id." + tmp.getFormName() + ");\n";
				kawai += "            " + tmp.getHandlerName() + ".setChecked(OwOWhatsThis[" + tmp.getCSVPos() + "].matches(\"\\\\*\"));\n";
			}
			else if (tmp.getFormType() == Spinner)
			{
				kawai += "            Spinner " + tmp.getHandlerName() +" = (Spinner) findViewById(R.id." + tmp.getFormName() + ");\n";
				kawai += "            " + tmp.getHandlerName() + ".setSelection(((ArrayAdapter)"+ tmp.getHandlerName() +".getAdapter()).getPosition(OwOWhatsThis["+ tmp.getCSVPos() +"]));\n";
			}
			else
			{
				kawai += "            EditText " + tmp.getHandlerName() +" = (EditText) findViewById(R.id." + tmp.getFormName() + ");\n";
				kawai += "            " + tmp.getHandlerName() + ".setText(OwOWhatsThis[" + tmp.getCSVPos() + "].replaceAll(\"^\\\"|\\\"$\", \"\"));\n";
			}
			wholeString += kawai;
		}
		wholeString += "        }\n" + 
				"    }\n\n";
		
		// THIRD: Generate confirmTypes method code.
		wholeString += "    public void confirmTypes(View view)\n" + 
				"    {\n" + 
				"        boolean breakCond = false;\n";
		for(int i = 0; i < elementList.size(); i++)
		{
			String kawai = "        // read the ";
			ElementHandler tmp = elementList.get(i);
			kawai += tmp.getDescription() + ".\n        if(!breakCond)\n        {\n";
			if (tmp.getFormType() == CheckBox)
			{
				kawai +=
						"            CheckBox " + tmp.getHandlerName() + " = (CheckBox) findViewById(R.id." + tmp.getFormName() +");\n" +
						"            " + tmp.getReturnVariableName() + " = " + tmp.getHandlerName() + ".isChecked();\n";
			}
			else if (tmp.getFormType() == Spinner)
			{
				kawai +=
						"            Spinner " + tmp.getHandlerName() + " = (Spinner) findViewById(R.id." + tmp.getFormName() + ");\n" + 
						"            " + tmp.getReturnVariableName() + " = " + tmp.getHandlerName() + ".getSelectedItem().toString();\n";
			}
			else
			{
				kawai += 
						"            Log.d(\"SetCompetitionName\",\"Parsing "+ tmp.getDescription()  +"\");\n" + 
						"            try\n" + 
						"            {\n" + 
						"                EditText editText = (EditText) findViewById(R.id." + tmp.getFormName() +");\n                " + 
						((tmp.getDataType() == Integer_) ? tmp.getReturnVariableName() + " = Integer.parseInt(editText.getText().toString());\n" : "") +
						((tmp.getDataType() == Double_) ? tmp.getReturnVariableName() + " = Double.parseDouble(editText.getText().toString());\n" : "") +
						((tmp.getDataType() == Boolean_) ? tmp.getReturnVariableName() + " = editText.getText().toString().toLowerCase.contains(\"yes\");\n" : "") +
						((tmp.getDataType() == String_) ? tmp.getReturnVariableName() + " = editText.getText().toString();\n" : "") +
						"            }\n" + 
						"            catch(NumberFormatException e)\n" + 
						"            {\n" + 
						"                Snackbar.make(view, \"Issue with number formatting\", Snackbar.LENGTH_LONG)\n" + 
						"                        .setAction(\"Action\", null).show();\n" + 
						"                breakCond = true;\n" + 
						"            }\n" + 
						"            catch(NullPointerException e)\n" + 
						"            {\n" + 
						"                Snackbar.make(view, \"Text box cannot be empty.\", Snackbar.LENGTH_LONG)\n" + 
						"                        .setAction(\"Action\", null).show();\n" + 
						"                breakCond = true;\n" + 
						"            }\n";
			}
			kawai += "        }";
			wholeString += kawai;
		}
		
		wholeString += "\n        if (!breakCond)\n" + 
				"        {\n" + 
				"           // All values are confirmed, move to next screen.\n" + 
				"            Log.d(\"SetCompetitionName\",\"Esketit!\");\n" + 
				"            moveToNextScreen(view);\n" + 
				"        }\n" + 
				"    }\n";
		
		// TODO: FOURTH: Generate moveToNextScreen method code.
		wholeString += "    public void moveToNextScreen(View view)\n" + 
						"    {\n" +
						"        String listMsg = \"" + DataStore.prevCfg + ";\n"+
						"        String CSVFormat = ";
		for(int i = 0; i < elementList.size(); i++)
		{
			String legalizeAwoo = "";
			ElementHandler tmp = elementList.get(i);
			if(tmp.getDataType() == Integer_)
			{
				legalizeAwoo += tmp.getReturnVariableName();
			}
			else if (tmp.getDataType() == Double_)
			{
				legalizeAwoo += tmp.getReturnVariableName();
			}
			else if (tmp.getDataType() == Boolean_)
			{
				legalizeAwoo += "(" + tmp.getReturnVariableName() + " ? \"*\" : \" \")";
			}
			else
			{
				legalizeAwoo += "\"" + tmp.getReturnVariableName() + "\"";
			}
			if (elementList.size() - 1 == i)
			{
				legalizeAwoo += ";\n";
			}
			else
			{
				legalizeAwoo += "+\",\"+";
			}
			
			wholeString += legalizeAwoo;
		}
		
		wholeString += "        if (USE_DEBUG)\n" + 
				"        {\n" + 
				"            Snackbar.make(view, CSVFormat, Snackbar.LENGTH_LONG).setAction(\"Action\", null).show();\n" + 
				"        }\n" + 
				"\n" + 
				"        if (editingoption == -1)\n" + 
				"        {\n" + 
				"            Log.d(\"SetCompetitionName\",\"Adding new entry to list.\");\n" + 
				"            AddCompetitions.addToList(listMsg);\n" + 
				"            DataStore.CsvFormattedContests.add(CSVFormat);\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            Log.d(\"SetCompetitionName\",\"Resetting list entry: \" + editingoption);\n" + 
				"            AddCompetitions.resetListItem(listMsg, editingoption);\n" + 
				"            DataStore.CsvFormattedContests.set(editingoption, CSVFormat);\n" + 
				"        }\n" + 
				"\n" + 
				"        // if using direct save, write the generated results directly to CSV file.\n" + 
				"        if (DataStore.USE_DIRECT_SAVE)\n" + 
				"        {\n" + 
				"            String filename = DataStore.FIRST_NAME+\"_\"+DataStore.LAST_NAME+\"_results.csv\";\n" + 
				"            File writeDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"            if (!writeDirectory.exists())\n" + 
				"            {\n" + 
				"                writeDirectory.mkdir();\n" + 
				"            }\n" + 
				"            File log = new File(writeDirectory, filename);\n" + 
				"            if(!log.exists())\n" + 
				"            {\n" + 
				"                try\n" + 
				"                {\n" + 
				"                    log.createNewFile();\n" + 
				"                }\n" + 
				"                catch (IOException e)\n" + 
				"                {\n" + 
				"                    e.printStackTrace();\n" + 
				"                }\n" + 
				"            }\n" + 
				"            PrintWriter madoka = null;\n" + 
				"            try\n" + 
				"            {\n" + 
				"                madoka = new PrintWriter(new FileWriter(log, true));\n" + 
				"            }\n" + 
				"            catch (IOException e)\n" + 
				"            {\n" + 
				"                e.printStackTrace();\n" + 
				"            }\n" + 
				"            madoka.println(CSVFormat);\n" + 
				"            madoka.flush();\n" + 
				"            madoka.close();\n" + 
				"        }\n        if (!USE_DEBUG)\n" + 
				"        {  finish();  }" + 
				"" + 
				"\n    }\n    public void cancel(View view) { finish(); }\n";
		
		wholeString += "}\n";
		return wholeString;
	}
	
	// This class needs to be procedurally generated since it contains the header for the CSV.
	public static String generateDataStoreClass()
	{
		String columnLabels = "";
		
		for(int i = 0; i < elementList.size(); i++)
		{
			columnLabels += elementList.get(i).getDescription() + ",";
		}
		
		String retStr = "package trc3543.trcscoutingapp;\n" + 
				"\n" + 
				"import android.content.Intent;\n" + 
				"import android.net.Uri;\n" + 
				"import android.os.Environment;\n" + 
				"import android.support.design.widget.Snackbar;\n" + 
				"import android.support.v7.app.AppCompatActivity;\n" + 
				"import android.util.Log;\n" + 
				"\n" + 
				"import java.io.BufferedReader;\n" + 
				"import java.io.File;\n" + 
				"import java.io.FileReader;\n" + 
				"import java.io.FileWriter;\n" + 
				"import java.io.IOException;\n" + 
				"import java.io.PrintWriter;\n" + 
				"import java.text.DateFormat;\n" + 
				"import java.text.SimpleDateFormat;\n" + 
				"import java.util.ArrayList;\n" + 
				"import java.util.Date;\n" + 
				"import java.util.regex.Matcher;\n" + 
				"import java.util.regex.Pattern;\n" + 
				"\n" + 
				"import static android.support.v4.content.ContextCompat.startActivity;\n" + 
				"\n" + 
				"/**\n" + 
				" * Created by citrus on 12/26/17.\n" + 
				" */\n" + 
				"\n" + 
				"@SuppressWarnings(\"All\")\n" + 
				"public class DataStore extends AppCompatActivity\n" + 
				"{\n" + 
				"    /**\n" + 
				"     *\n" + 
				"     *  Copyright (c) 2018 Titan Robotics Club, _c0da_ (Victor Du)\n" + 
				"     *\n" + 
				"     *	Permission is hereby granted, free of charge, to any person obtaining a copy\n" + 
				"     *	of this software and associated documentation files (the \"Software\"), to deal\n" + 
				"     *	in the Software without restriction, including without limitation the rights\n" + 
				"     *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" + 
				"     *	copies of the Software, and to permit persons to whom the Software is\n" + 
				"     *	furnished to do so, subject to the following conditions:\n" + 
				"     *\n" + 
				"     *	The above copyright notice and this permission notice shall be included in all\n" + 
				"     *	copies or substantial portions of the Software.\n" + 
				"     *\n" + 
				"     *	THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" + 
				"     *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" + 
				"     *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" + 
				"     *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" + 
				"     *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" + 
				"     *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" + 
				"     *	SOFTWARE.\n" + 
				"     */\n" + 
				"\n" + 
				"    static boolean USE_AUTOSAVE = true; // by default, autosave is enabled.\n" + 
				"    static int AUTOSAVE_SECONDS = 300;  // by default, save changes every 5 minutes.\n" + 
				"\n" + 
				"    static ArrayList<String> contests = new ArrayList<String>();\n" + 
				"    static ArrayList<String> CsvFormattedContests = new ArrayList<String>();\n" + 
				"\n" + 
				"    static int SELF_TEAM_NUMBER = 3543;\n" + 
				"    static String FIRST_NAME = \"Unknown\";\n" + 
				"    static String LAST_NAME = \"Unknown\";\n" + 
				"\n" + 
				"    static boolean USE_DIRECT_SAVE = false; // don't use direct save by default\n" + 
				"\n" + 
				"    public DataStore()\n" + 
				"    {\n" + 
				"        // TODO nothing\n" + 
				"    }\n" + 
				"\n" + 
				"\n" + 
				"    public static boolean writeContestsToCsv(String filename) throws IOException\n" + 
				"    {\n" + 
				"        File writeDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        if (!writeDirectory.exists())\n" + 
				"        {\n" + 
				"            writeDirectory.mkdir();\n" + 
				"        }\n" + 
				"        if (CsvFormattedContests.size() == 0)\n" + 
				"        {\n" + 
				"            return false;\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            File log = new File(writeDirectory, filename);\n" + 
				"            if(!log.exists())\n" + 
				"            {\n" + 
				"                log.createNewFile();\n" + 
				"            }\n" + 
				"            PrintWriter madoka = new PrintWriter(new FileWriter(log, true));\n" + 
				"            madoka.println(\"Log by: \" + FIRST_NAME + \" \" + LAST_NAME + \", written on \" + getDateAsString());" +				"            madoka.println(\"" + columnLabels +"\");\n" +
				"            for(String sk : CsvFormattedContests)\n" + 
				"            {\n" + 
				"                madoka.println(sk);\n" + 
				"            }\n" + 
				"            madoka.println(\"End Of Log\");\n" + 
				"            madoka.flush();\n" + 
				"            madoka.close();\n" + 
				"            return true;\n" + 
				"        }\n" + 
				"    }\n" + 
				"\n" + 
				"    public static String getDateAsString()\n" + 
				"    {\n" + 
				"        DateFormat dateFormat = new SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss\");\n" + 
				"        Date date = new Date();\n" + 
				"        return dateFormat.format(date);\n" + 
				"    }\n" + 
				"\n" + 
				"    public static void parseTeamNum() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        int saiodfjsajofojfdfjisafbj;\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(readDirectory, \"settings.coda\");\n" + 
				"        if (log.exists())\n" + 
				"        {\n" + 
				"            try\n" + 
				"            {\n" + 
				"                BufferedReader br = new BufferedReader(new FileReader(log));\n" + 
				"                saiodfjsajofojfdfjisafbj = Integer.parseInt(br.readLine());\n" + 
				"                SELF_TEAM_NUMBER = saiodfjsajofojfdfjisafbj;\n" + 
				"            }\n" + 
				"            catch (NumberFormatException e)\n" + 
				"            {\n" + 
				"                SELF_TEAM_NUMBER = 3543; // can't read team num, return to default value.\n" + 
				"            }\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            SELF_TEAM_NUMBER = 3543; // return by default\n" + 
				"        }\n" + 
				"    }\n" + 
				"    public static void parseFirstName() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        String saiodfjsajofojfdfjisafbj;\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(readDirectory, \"settings.coda\");\n" + 
				"        if (log.exists())\n" + 
				"        {\n" + 
				"                BufferedReader br = new BufferedReader(new FileReader(log));\n" + 
				"                br.readLine();\n" + 
				"                saiodfjsajofojfdfjisafbj = br.readLine();\n" + 
				"                FIRST_NAME = saiodfjsajofojfdfjisafbj;\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"                FIRST_NAME = \"Unknown\";\n" + 
				"        }\n" + 
				"    }\n" + 
				"    public static void parseLastName() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        String saiodfjsajofojfdfjisafbj;\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(readDirectory, \"settings.coda\");\n" + 
				"        if (log.exists())\n" + 
				"        {\n" + 
				"            BufferedReader br = new BufferedReader(new FileReader(log));\n" + 
				"            br.readLine();\n" + 
				"            br.readLine();\n" + 
				"            saiodfjsajofojfdfjisafbj = br.readLine();\n" + 
				"            LAST_NAME = saiodfjsajofojfdfjisafbj;\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            LAST_NAME = \"Unknown\";\n" + 
				"        }\n" + 
				"    }\n" + 
				"    public static void parseDirectSave() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        String saiodfjsajofojfdfjisafbj;\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(readDirectory, \"settings.coda\");\n" + 
				"        if (log.exists())\n" + 
				"        {\n" + 
				"            BufferedReader br = new BufferedReader(new FileReader(log));\n" + 
				"            br.readLine();\n" + 
				"            br.readLine();\n" + 
				"            br.readLine();\n" + 
				"            saiodfjsajofojfdfjisafbj = br.readLine();\n" + 
				"            if (saiodfjsajofojfdfjisafbj.matches(\"y\"))\n" + 
				"            {\n" + 
				"                USE_DIRECT_SAVE = true;\n" + 
				"            }\n" + 
				"            else\n" + 
				"            {\n" + 
				"                USE_DIRECT_SAVE = false;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            USE_DIRECT_SAVE = false;\n" + 
				"        }\n" + 
				"    }\n" + 
				"    public static void parseAutoSaveBoolean() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        String saiodfjsajofojfdfjisafbj;\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(readDirectory, \"autosave.coda\");\n" + 
				"        if (log.exists())\n" + 
				"        {\n" + 
				"            BufferedReader br = new BufferedReader(new FileReader(log));\n" + 
				"            saiodfjsajofojfdfjisafbj = br.readLine();\n" + 
				"            if (saiodfjsajofojfdfjisafbj.contains(\"y\"))\n" + 
				"            {\n" + 
				"                USE_AUTOSAVE = true;\n" + 
				"            }\n" + 
				"            else\n" + 
				"            {\n" + 
				"                USE_AUTOSAVE = false;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            USE_AUTOSAVE = true;\n" + 
				"        }\n" + 
				"    }\n" + 
				"    public static void parseAutoSaveTime() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        String saiodfjsajofojfdfjisafbj;\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(readDirectory, \"autosave.coda\");\n" + 
				"        if (log.exists())\n" + 
				"        {\n" + 
				"            BufferedReader br = new BufferedReader(new FileReader(log));\n" + 
				"            br.readLine();\n" + 
				"            saiodfjsajofojfdfjisafbj = br.readLine();\n" + 
				"            try\n" + 
				"            {\n" + 
				"                AUTOSAVE_SECONDS = Integer.parseInt(saiodfjsajofojfdfjisafbj);\n" + 
				"            }\n" + 
				"            catch (NumberFormatException e)\n" + 
				"            {\n" + 
				"                AUTOSAVE_SECONDS = 300;\n" + 
				"                e.printStackTrace();\n" + 
				"            }\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            AUTOSAVE_SECONDS = 300;\n" + 
				"        }\n" + 
				"    }\n" + 
				"    public static boolean existsSave()\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        File log = new File(readDirectory, \"settings.coda\");\n" + 
				"        if (!log.exists())\n" + 
				"        {\n" + 
				"            return false;\n" + 
				"        }\n" + 
				"        return true;\n" + 
				"    }\n" + 
				"    public static ArrayList<Match> matchArrayList = new ArrayList<Match>();\n" + 
				"    public static void parseSchedule() throws IOException\n" + 
				"    {\n" + 
				"        // after entering a match number the match type, and alliance teams will be auto-populated.\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        File sched = new File(readDirectory, \"schedule.csv\");\n" + 
				"        if (!sched.exists())\n" + 
				"        {\n" + 
				"            // we don't have a schedule!\n" + 
				"            return;\n" + 
				"        }\n" + 
				"        BufferedReader br = new BufferedReader(new FileReader(sched));\n" + 
				"        String read;\n" + 
				"        try\n" + 
				"        {\n" + 
				"            while ((read = br.readLine()) != null)\n" + 
				"            {\n" + 
				"                // parse each line.\n" + 
				"                if (!(read.contains(\"Match\") || read.contains(\"Red\")))\n" + 
				"                {\n" + 
				"                    // ignore the header\n" + 
				"                    // begin parsing info\n" + 
				"                    String[] matchLine = read.split(\",\");\n" + 
				"                    String matchInf = matchLine[0];\n" + 
				"                    Pattern p = Pattern.compile(\"-?\\\\d+\");\n" + 
				"                    Matcher m = p.matcher(matchInf);\n" + 
				"                    int matchNum = Integer.parseInt(m.group());\n" + 
				"                    String matchType;\n" + 
				"                    if (matchInf.contains(\"Practice\"))\n" + 
				"                    {\n" + 
				"                        matchType = \"Practice\";\n" + 
				"                    }\n" + 
				"                    else if (matchInf.contains(\"Qualification\"))\n" + 
				"                    {\n" + 
				"                        matchType = \"Qualification\";\n" + 
				"                    }\n" + 
				"                    else if (matchInf.contains(\"Semi\"))\n" + 
				"                    {\n" + 
				"                        matchType = \"Semi-Final\";\n" + 
				"                    }\n" + 
				"                    else\n" + 
				"                    {\n" + 
				"                        matchType = \"Final\";\n" + 
				"                    }\n" + 
				"                    String startTime = matchLine[1];\n" + 
				"                    int red1 = Integer.parseInt(matchLine[2]);\n" + 
				"                    int red2 = Integer.parseInt(matchLine[3]);\n" + 
				"                    int red3 = Integer.parseInt(matchLine[4]);\n" + 
				"                    int blu1 = Integer.parseInt(matchLine[5]);\n" + 
				"                    int blu2 = Integer.parseInt(matchLine[6]);\n" + 
				"                    int blu3 = Integer.parseInt(matchLine[7]);\n" + 
				"                    matchArrayList.add(new Match(matchNum, matchType, startTime, red1, red2, red3, blu1, blu2, blu3));\n" + 
				"                }\n" + 
				"            }\n" + 
				"        }\n" + 
				"        catch (Exception e)\n" + 
				"        {\n" + 
				"            Log.d(\"MatchParser\", \"Furballs, something happened!: \" + e);\n" + 
				"            e.printStackTrace();\n" + 
				"        }\n" + 
				"    }\n" + 
				"    public static Match getMatch(int matchNum)\n" + 
				"    {\n" + 
				"        for(int i = 0; i < matchArrayList.size(); i++)\n" + 
				"        {\n" + 
				"            if (matchArrayList.get(i).matchNum == matchNum)\n" + 
				"            {\n" + 
				"                return matchArrayList.get(i);\n" + 
				"            }\n" + 
				"        }\n" + 
				"        return null;\n" + 
				"    }\n" + 
				"\n" + 
				"}\n" + 
				"class Match\n" + 
				"{\n" + 
				"    public int matchNum;\n" + 
				"    public String type;\n" + 
				"    public String timestamp;\n" + 
				"    public int r1;\n" + 
				"    public int r2;\n" + 
				"    public int r3;\n" + 
				"    public int b1;\n" + 
				"    public int b2;\n" + 
				"    public int b3;\n" + 
				"    public Match(int matchNum, String type, String timestamp, int r1, int r2, int r3, int b1, int b2, int b3)\n" + 
				"    {\n" + 
				"        this.matchNum = matchNum;\n" + 
				"        this.type = type;\n" + 
				"        this.timestamp = timestamp;\n" + 
				"        this.r1 = r1;\n" + 
				"        this.r2 = r2;\n" + 
				"        this.r3 = r3;\n" + 
				"        this.b1 = b1;\n" + 
				"        this.b2 = b2;\n" + 
				"        this.b3 = b3;\n" + 
				"    }\n" + 
				"}\n";
		return retStr;
	}
	
}
