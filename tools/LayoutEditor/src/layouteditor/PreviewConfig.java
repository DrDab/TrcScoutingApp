package layouteditor;

public class PreviewConfig 
{
	private ElementHandler e1;
	private ElementHandler e2;
	private ElementHandler e3;
	
	private String e1_descr;
	private String e2_descr;
	private String e3_descr;
	
	public PreviewConfig(String e1_descr, ElementHandler e1, String e2_descr, ElementHandler e2, String e3_descr, ElementHandler e3)
	{
		this.e1_descr = e1_descr;
		this.e1 = e1;
		this.e2_descr = e2_descr;
		this.e2 = e2;
		this.e3_descr = e3_descr;
		this.e3 = e3;
	}
	
	@Override
	public String toString()
	{
		return e1_descr + ":\"+" + e1.getReturnVariableName() + "+\" " + e2_descr + ":\" +" + e2.getReturnVariableName() + "+\" " + e3_descr + ":\" +" + e3.getReturnVariableName();
	}
	
	
}
