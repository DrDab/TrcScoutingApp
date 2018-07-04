package layouteditor;

public class ElementHandler implements Comparable<ElementHandler>
{
	private int CSVPosition;
	private String formName;
	private int formType;
	private int dataType;
	private String handlerName;
	private String returnVariableName;
	private String description;
	
	/**
	 * Initializes a new ElementHandler. This will represent an element handled by SetCompetitionName class.
	 * 
	 * - CSVposition: The column # in the CSV the information parsed by the form this handles will be written to.
	 * - formName: The name of the form in R.java. (R.id.xxx)
	 * - formType: The type of form.
	 * 				1 : EditText (return a String)
	 * 				2 : Spinner (return a String)
	 * 				3 : CheckBox (return [*] or [ ]) 
	 * - dataType: The datatype of the info to be parsed from the form.
	 * 				1 : Integer
	 * 				2 : Double
	 * 				3 : Boolean
	 * 				4 : String
	 * - handlerName: The name of the form to be specified in code.
	 * - returnVariableName: The name of the variable to contain the data in the form.
	 * 
	 * 
	 * @param CSVposition
	 * @param formName
	 * @param formType
	 */
	public ElementHandler(int CSVPosition, String formName, int formType, int dataType, String handlerName, String returnVariableName, String description)
	{
		this.CSVPosition = CSVPosition;
		this.formName = formName;
		this.formType = formType;
		this.dataType = dataType;
		this.handlerName = handlerName;
		this.returnVariableName = returnVariableName;
		this.description = description;
	}
	
	public int getCSVPos()
	{
		return CSVPosition;
	}
	
	public String getFormName()
	{
		return formName;
	}
	
	public int getFormType()
	{
		return formType;
	}
	
	public int getDataType()
	{
		return dataType;
	}
	
	public String getHandlerName()
	{
		return handlerName;
	}
	
	public String getReturnVariableName()
	{
		return returnVariableName;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	@Override
	public String toString()
	{
		return "[" + CSVPosition + "] - " + description;
	}

	@Override
	public int compareTo(ElementHandler o) 
	{
		return toString().compareTo(o.toString());
	}
	
	
	
}
