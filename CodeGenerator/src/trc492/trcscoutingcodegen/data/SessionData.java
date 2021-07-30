package trc492.trcscoutingcodegen.data;

import java.util.List;

public class SessionData
{
    public List<Field> fields;
    public List<Page> pages;
    
    public SessionData(List<Field> fields, List<Page> pages)
    {
        this.fields = fields;
        this.pages = pages;
    }
    
    public SessionData(String fileName)
    {
        
    }
}
