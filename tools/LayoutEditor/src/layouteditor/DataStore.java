package layouteditor;

import java.util.ArrayList;
import java.util.Collections;
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
	
	public static int getListSize()
	{
		return elementList.size();
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
		if (elementList.size() == 0)
		{
			throw new NullPointerException("There's nothing in the elements of H A R M O N Y");
		}
		ArrayList<ElementHandler> kitsune = elementList;
		Collections.sort(kitsune);
		String wholeString = "";
		String begin = "// THIS CODE WAS GENERATED AUTOMATICALLY BY Victor's SmartBuilder script. DO NOT MODIFY.\n\npackage trc3543.trcscoutingapp;\n" + 
				"\n" + 
				"import android.content.Context;\n" + 
				"import android.content.Intent;\n" + 
				"import android.graphics.Rect;\n" + 
				"import android.support.design.widget.Snackbar;\n" + 
				"import android.support.v7.app.AppCompatActivity;\n" + 
				"import android.os.Bundle;\n" + 
				"import android.util.Log;\n" + 
				"import android.view.MotionEvent;\n" + 
				"import android.view.View;\n" + 
				"import android.view.inputmethod.InputMethodManager;\n" + 
				"import android.widget.ArrayAdapter;\n" + 
				"import android.widget.CheckBox;\n" + 
				"import android.widget.EditText;\n" + 
				"import android.widget.Spinner;\n" + 
				"\n" + 
				"import java.io.IOException;" +
				"\n" + 
				"@SuppressWarnings(\"all\")\n" + 
				"public class SetMatchInfo extends AppCompatActivity\n" + 
				"{\n" + 
				"    /**\n" + 
				"     *\n" + 
				"     *  Copyright (c) 2019 Titan Robotics Club, _c0da_ (Victor Du)\n" + 
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
		// FIRST: Generate all variables.
		for(int i = 0; i < kitsune.size(); i++)
		{
			ElementHandler tmp = kitsune.get(i);
			String declareString = "   private ";
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
				"@Override\n" + 
				"    protected void onCreate(Bundle savedInstanceState)\n" + 
				"    {\n" + 
				"        super.onCreate(savedInstanceState);\n" + 
				"        setContentView(R.layout.activity_set_match_info2);\n" + 
				"        setTitle(\"Add Match\");\n" + 
				"        try\n" + 
				"        {\n" + 
				"            DataStore.parseUserInfoGeneral();\n" + 
				"        }\n" + 
				"        catch (IOException e)\n" + 
				"        {\n" + 
				"            // TODO Auto-generated catch block\n" + 
				"        }\n" + 
				"\n" + 
				"        try\n" + 
				"        {\n" + 
				"            Intent myIntent = getIntent();\n" + 
				"            String editoptionstr = myIntent.getStringExtra(\"EditOption\");\n" + 
				"            Log.d(\"SetMatchInfo\", \"editoptionsstr=\\\"\" + editoptionstr + \"\\\"\");\n" + 
				"            editingoption = Integer.parseInt(editoptionstr);\n" + 
				"            Log.d(\"SetMatchInfo\", \"Got edit option: \" + editingoption);\n" + 
				"        }\n" + 
				"        catch (Exception e)\n" + 
				"        {\n" + 
				"            Log.d(\"SetMatchInfo\",\"You shouldn't see this message\");\n" + 
				"            editingoption = -1;\n" + 
				"        }\n" + 
				"\n" + 
				"        // populate the boxes if already filled.\n" + 
				"        if (editingoption != -1)\n" + 
				"        {\n" + 
				"            String read = DataStore.matchList.get(editingoption).getCsvString();\n" + 
				"            Log.d(\"SetMatchInfo\", editingoption + \" \" + read);\n" + 
				"            String[] OwOWhatsThis = read.split(\",\");\n" + 
				"";
		
		for(int i = 0; i < kitsune.size(); i++)
		{
			String kawai = "            // populate the ";
			ElementHandler tmp = kitsune.get(i);
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
		for(int i = 0; i < kitsune.size(); i++)
		{
			String kawai = "        // read the ";
			ElementHandler tmp = kitsune.get(i);
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
		for(int i = 0; i < kitsune.size(); i++)
		{
			String legalizeAwoo = "";
			ElementHandler tmp = kitsune.get(i);
			if(tmp.getDataType() == Integer_ || tmp.getDataType() == Double_ || tmp.getDataType() == Boolean_)
			{
				legalizeAwoo += tmp.getReturnVariableName();
			}
			else
			{
				legalizeAwoo += "\"\\\"\"+" + tmp.getReturnVariableName() + "+\"\\\"\"";
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

		wholeString += "Log.d(\"SetMatchInfo\", CSVFormat);\n" + 
				"\n" + 
				"        if (USE_DEBUG)\n" + 
				"        {\n" + 
				"            Snackbar.make(view, CSVFormat, Snackbar.LENGTH_LONG).setAction(\"Action\", null).show();\n" + 
				"        }\n" + 
				"\n" + 
				"        if (editingoption == -1)\n" + 
				"        {\n" + 
				"            Log.d(\"SetMatchInfo\",\"Adding new entry to list.\");\n" + 
				"            AddMatches.addToList(listMsg, CSVFormat);\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            Log.d(\"SetMatchInfo\",\"Resetting list entry: \" + editingoption);\n" + 
				"            AddMatches.resetListItem(listMsg, CSVFormat, editingoption);\n" + 
				"        }\n" + 
				"\n" + 
				"        try\n" + 
				"        {\n" + 
				"            DataStore.writeArraylistsToJSON();\n" + 
				"        }\n" + 
				"        catch (IOException e)\n" + 
				"        {\n" + 
				"            e.printStackTrace();\n" + 
				"        }\n" + 
				"\n" + 
				"        if (!USE_DEBUG)\n" + 
				"        {\n" + 
				"            finish();\n" + 
				"        }\n" + 
				"    }\n" + 
				"\n" + 
				"    /**\n" + 
				"     * This code snippet written by ZMan; may great honor be laid upon this act of chivalry:\n" + 
				"     *\n" + 
				"     * Answer: https://stackoverflow.com/questions/4828636/edittext-clear-focus-on-touch-outside\n" + 
				"     * User: https://stackoverflow.com/users/1591623/zman\n" + 
				"     */\n" + 
				"    @Override\n" + 
				"    public boolean dispatchTouchEvent(MotionEvent event)\n" + 
				"    {\n" + 
				"        if (event.getAction() == MotionEvent.ACTION_DOWN)\n" + 
				"        {\n" + 
				"            View v = getCurrentFocus();\n" + 
				"            if (v instanceof EditText)\n" + 
				"            {\n" + 
				"                Rect outRect = new Rect();\n" + 
				"                v.getGlobalVisibleRect(outRect);\n" + 
				"                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY()))\n" + 
				"                {\n" + 
				"                    v.clearFocus();\n" + 
				"                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);\n" + 
				"                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);\n" + 
				"                }\n" + 
				"            }\n" + 
				"        }\n" + 
				"        return super.dispatchTouchEvent(event);\n" + 
				"    }\n" + 
				"\n" + 
				"}\n";

		return wholeString;
	}
	
	// This class needs to be procedurally generated since it contains the header for the CSV.
	public static String generateDataStoreClass()
	{
		if (elementList.size() == 0)
		{
			throw new NullPointerException("There's nothing in the elements of H A R M O N Y");
		}
		String columnLabels = "";
		
		ArrayList<ElementHandler> kitsune = elementList;
		Collections.sort(kitsune);
		
		for(int i = 0; i < kitsune.size(); i++)
		{
			columnLabels += kitsune.get(i).getDescription() + ",";
		}
		
		String retStr = "/*\n" + 
				" * Copyright (c) 2017-2019 Titan Robotics Club\n" + 
				" *\n" + 
				" * Permission is hereby granted, free of charge, to any person obtaining a copy\n" + 
				" * of this software and associated documentation files (the \"Software\"), to deal\n" + 
				" * in the Software without restriction, including without limitation the rights\n" + 
				" * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" + 
				" * copies of the Software, and to permit persons to whom the Software is\n" + 
				" * furnished to do so, subject to the following conditions:\n" + 
				" *\n" + 
				" * The above copyright notice and this permission notice shall be included in all\n" + 
				" * copies or substantial portions of the Software.\n" + 
				" *\n" + 
				" * THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" + 
				" * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" + 
				" * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" + 
				" * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" + 
				" * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" + 
				" * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" + 
				" * SOFTWARE.\n" + 
				" */\n" + 
				"\n" + 
				"package trc3543.trcscoutingapp;\n" + 
				"\n" + 
				"import android.os.Environment;\n" + 
				"import android.support.v7.app.AppCompatActivity;\n" + 
				"\n" + 
				"import org.json.JSONArray;\n" + 
				"import org.json.JSONException;\n" + 
				"import org.json.JSONObject;\n" + 
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
				"import java.util.Locale;\n" + 
				"\n" + 
				"@SuppressWarnings(\"All\")\n" + 
				"public class DataStore extends AppCompatActivity\n" + 
				"{\n" + 
				"    // Begin season-specific info.\n" + 
				"    public static final String DATA_FOLDER_NAME = \"TrcScoutingApp\";\n" + 
				"    public static final String CSV_HEADER = \"" + columnLabels + "\";\n" + 
				"    public static final String VERSION_NUMBER = \"1.3.3-frc-INDEV\";\n" + 
				"    // End season-specific info.\n" + 
				"\n" + 
				"\n" + 
				"    // The following variables are not supposed to be changed.\n" + 
				"    // DO NOT CHANGE THESE VARIABLES.\n" + 
				"    public static boolean useAutosave = true;\n" + 
				"    public static int autosaveSeconds = 300;\n" + 
				"\n" + 
				"    public static ArrayList<Match> matchList = new ArrayList<Match>();\n" + 
				"\n" + 
				"    public static int selfTeamNumber = 3543;\n" + 
				"    public static String firstName = \"Unknown\";\n" + 
				"    public static String lastName = \"Unknown\";\n" + 
				"\n" + 
				"    public static boolean autoSaveRunnableInit = false;\n" + 
				"\n" + 
				"    public static boolean deviceSupportsNfc = false;\n" + 
				"\n" + 
				"    public static String serverIP = null;\n" + 
				"    public static int serverPort = 3621;\n" + 
				"    public static String username = null;\n" + 
				"    public static String password = null;\n" + 
				"\n" + 
				"    public static synchronized boolean writeArraylistsToJSON() throws IOException\n" + 
				"    {\n" + 
				"        File writeDirectory = new File(Environment.getExternalStorageDirectory(), \"TrcScoutingApp\");\n" + 
				"        if (!writeDirectory.exists())\n" + 
				"        {\n" + 
				"            writeDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File cache = new File(writeDirectory, \"cache.json\");\n" + 
				"        if(!cache.exists())\n" + 
				"        {\n" + 
				"            cache.createNewFile();\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            cache.delete();\n" + 
				"            cache = new File(writeDirectory, \"cache.json\");\n" + 
				"            cache.createNewFile();\n" + 
				"        }\n" + 
				"        PrintWriter pw = new PrintWriter(new FileWriter(cache, true));\n" + 
				"        JSONObject jsonObject = new JSONObject();\n" + 
				"        JSONArray displayContestsArray = new JSONArray();\n" + 
				"        JSONArray csvContestsArray = new JSONArray();\n" + 
				"        JSONArray uuidArray = new JSONArray();\n" + 
				"\n" + 
				"        for(int i = 0; i < matchList.size(); i++)\n" + 
				"        {\n" + 
				"            displayContestsArray.put(matchList.get(i).getDispString());\n" + 
				"            csvContestsArray.put(matchList.get(i).getCsvString());\n" + 
				"            uuidArray.put(matchList.get(i).getUUID());\n" + 
				"        }\n" + 
				"\n" + 
				"        try\n" + 
				"        {\n" + 
				"            jsonObject.put(\"disp\", (Object)displayContestsArray);\n" + 
				"            jsonObject.put(\"csv\", (Object)csvContestsArray);\n" + 
				"            jsonObject.put(\"uuid\", (Object)uuidArray);\n" + 
				"        }\n" + 
				"        catch (JSONException e)\n" + 
				"        {\n" + 
				"            e.printStackTrace();\n" + 
				"            return false;\n" + 
				"        }\n" + 
				"\n" + 
				"        pw.println(jsonObject.toString());\n" + 
				"        pw.flush();\n" + 
				"        pw.close();\n" + 
				"        return true;\n" + 
				"    }\n" + 
				"\n" + 
				"    public static synchronized void readArraylistsFromJSON() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\n" + 
				"        int saiodfjsajofojfdfjisafbj;\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File cache = new File(readDirectory, \"cache.json\");\n" + 
				"        if (cache.exists())\n" + 
				"        {\n" + 
				"            BufferedReader br = new BufferedReader(new FileReader(cache));\n" + 
				"            String jsonData = \"\";\n" + 
				"            String in = null;\n" + 
				"            while ((in = br.readLine()) != null)\n" + 
				"            {\n" + 
				"                jsonData += in;\n" + 
				"            }\n" + 
				"            br.close();\n" + 
				"            try\n" + 
				"            {\n" + 
				"                matchList.clear();\n" + 
				"\n" + 
				"                JSONObject jsonObject = new JSONObject(jsonData);\n" + 
				"                JSONArray displayContestsArray = jsonObject.getJSONArray(\"disp\");\n" + 
				"                JSONArray csvContestsArray = jsonObject.getJSONArray(\"csv\");\n" + 
				"                JSONArray uuidArray = jsonObject.getJSONArray(\"uuid\");\n" + 
				"\n" + 
				"                for(int i = 0; i < csvContestsArray.length(); i++)\n" + 
				"                {\n" + 
				"                    matchList.add(new Match(displayContestsArray.getString(i), csvContestsArray.getString(i), uuidArray.getString(i)));\n" + 
				"                }\n" + 
				"            }\n" + 
				"            catch (JSONException e)\n" + 
				"            {\n" + 
				"                e.printStackTrace();\n" + 
				"            }\n" + 
				"        }\n" + 
				"    }\n" + 
				"\n" + 
				"    public static synchronized boolean writeContestsToCsv(String filename) throws IOException\n" + 
				"    {\n" + 
				"        File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\n" + 
				"        if (!writeDirectory.exists())\n" + 
				"        {\n" + 
				"            writeDirectory.mkdir();\n" + 
				"        }\n" + 
				"        if (matchList.size() == 0)\n" + 
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
				"            madoka.println(\"Log by: \" + firstName + \" \" + lastName + \", written on \" + getDateAsString());\n" + 
				"            madoka.println(CSV_HEADER);\n" + 
				"            for(Match match : matchList)\n" + 
				"            {\n" + 
				"                madoka.println(match.getCsvString());\n" + 
				"            }\n" + 
				"            madoka.println(\"End Of Log\");\n" + 
				"            madoka.flush();\n" + 
				"            madoka.close();\n" + 
				"            return true;\n" + 
				"        }\n" + 
				"    }\n" + 
				"\n" + 
				"    public static synchronized String getDateAsString()\n" + 
				"    {\n" + 
				"        DateFormat dateFormat = new SimpleDateFormat(\"yyyy/MM/dd HH:mm:ss\");\n" + 
				"        Date date = new Date();\n" + 
				"        return dateFormat.format(date);\n" + 
				"    }\n" + 
				"\n" + 
				"    public static synchronized void parseUserInfoGeneral() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(readDirectory, \"settings.coda\");\n" + 
				"        if (log.exists())\n" + 
				"        {\n" + 
				"            BufferedReader br = new BufferedReader(new FileReader(log));\n" + 
				"            try\n" + 
				"            {\n" + 
				"                selfTeamNumber = Integer.parseInt(br.readLine());\n" + 
				"            }\n" + 
				"            catch (NumberFormatException e)\n" + 
				"            {\n" + 
				"                selfTeamNumber = 3543; // can't read team num, return to default value.\n" + 
				"            }\n" + 
				"            firstName = br.readLine();\n" + 
				"            lastName = br.readLine();\n" + 
				"        }\n" + 
				"        else\n" + 
				"        {\n" + 
				"            selfTeamNumber = 3543; // return by default\n" + 
				"        }\n" + 
				"    }\n" + 
				"\n" + 
				"    public static synchronized void parseAutoSaveInfo() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(readDirectory, \"autosave.coda\");\n" + 
				"        if (log.exists())\n" + 
				"        {\n" + 
				"            BufferedReader br = new BufferedReader(new FileReader(log));\n" + 
				"            String yn = br.readLine();\n" + 
				"            if (yn.contains(\"y\"))\n" + 
				"            {\n" + 
				"                useAutosave = true;\n" + 
				"            }\n" + 
				"            else\n" + 
				"            {\n" + 
				"                useAutosave = false;\n" + 
				"            }\n" + 
				"            try\n" + 
				"            {\n" + 
				"                autosaveSeconds = Integer.parseInt(br.readLine());\n" + 
				"            }\n" + 
				"            catch (NumberFormatException e)\n" + 
				"            {\n" + 
				"                autosaveSeconds = 300;\n" + 
				"                e.printStackTrace();\n" + 
				"            }\n" + 
				"        }\n" + 
				"    }\n" + 
				"\n" + 
				"\n" + 
				"    public static synchronized void parseServerLoginData() throws IOException\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\n" + 
				"        if (!readDirectory.exists())\n" + 
				"        {\n" + 
				"            readDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(readDirectory, \"uploader.coda\");\n" + 
				"        if (log.exists())\n" + 
				"        {\n" + 
				"            try\n" + 
				"            {\n" + 
				"                BufferedReader br = new BufferedReader(new FileReader(log));\n" + 
				"                serverIP = br.readLine();\n" + 
				"                serverPort = Integer.parseInt(br.readLine());\n" + 
				"                username = br.readLine();\n" + 
				"                password = br.readLine();\n" + 
				"            }\n" + 
				"            catch (Exception e)\n" + 
				"            {\n" + 
				"                e.printStackTrace();\n" + 
				"            }\n" + 
				"        }\n" + 
				"    }\n" + 
				"\n" + 
				"    public static synchronized void writeServerLoginData() throws IOException\n" + 
				"    {\n" + 
				"        File writeDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\n" + 
				"        if (!writeDirectory.exists())\n" + 
				"        {\n" + 
				"            writeDirectory.mkdir();\n" + 
				"        }\n" + 
				"        File log = new File(writeDirectory, \"uploader.coda\");\n" + 
				"        if(!log.exists())\n" + 
				"        {\n" + 
				"            log.createNewFile();\n" + 
				"        }\n" + 
				"        PrintWriter pw = new PrintWriter(new FileWriter(log));\n" + 
				"        pw.println(serverIP);\n" + 
				"        pw.println(serverPort);\n" + 
				"        pw.println(username);\n" + 
				"        pw.println(password);\n" + 
				"        pw.flush();\n" + 
				"        pw.close();\n" + 
				"    }\n" + 
				"\n" + 
				"    public static synchronized boolean existsSave()\n" + 
				"    {\n" + 
				"        File readDirectory = new File(Environment.getExternalStorageDirectory(), DATA_FOLDER_NAME);\n" + 
				"        File log = new File(readDirectory, \"settings.coda\");\n" + 
				"        return log.exists();\n" + 
				"    }\n" + 
				"\n" + 
				"    public static String getTimeStamp(String format)\n" + 
				"    {\n" + 
				"        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);\n" + 
				"        return dateFormat.format(new Date());\n" + 
				"    }\n" + 
				"\n" + 
				"    public static String getFileName(String username)\n" + 
				"    {\n" + 
				"        return username + \"_\" + getTimeStamp(\"yyyyMMdd@HHmmss\") + \".csv\";\n" + 
				"    }\n" + 
				"\n" + 
				"}\n";
		return retStr;
	}
	
}
