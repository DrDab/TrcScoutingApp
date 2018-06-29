package layouteditor;

import java.util.ArrayList;

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
		DataStore.elementList.add(new ElementHandler(1337, "TestTextBox", EditText, Integer_, "borbTest", "testVar", "test option"));
		System.out.println(generateCodeSegment(DataStore.elementList));
	}

	public static String generateCodeSegment(ArrayList<ElementHandler> elementList)
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
		
		
		// SECOND: Add onCreate code.
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
				kawai += "            " + tmp.getHandlerName() + ".setChecked(OwOWhatsThis[" + tmp.getCSVPos() + "].matches(\"*\");\n";
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
		
		// THIRD: Add confirmTypes code.
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
						"            if ("+ tmp.getHandlerName() +".isChecked())\n" + 
						"            {\n" + 
						"                " + tmp.getReturnVariableName() + " = true;\n" + 
						"            }\n" + 
						"            else\n" + 
						"            {\n" + 
						"                " + tmp.getReturnVariableName() + " = false;\n" + 
						"            }\n";
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
		
		
		return wholeString;
	}
	
}
