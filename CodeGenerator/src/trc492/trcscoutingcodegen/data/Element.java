package trc492.trcscoutingcodegen.data;

import java.io.Serializable;

import org.json.JSONObject;

public class Element implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public String elementId;
    
    public ElementType elementType;
    
    public Field field;
    
    public Element(String elementId, ElementType elementType, Field field)
    {
        this.elementId = elementId;
        this.elementType = elementType;
        this.field = field;
    }
    
    @Override
    public String toString()
    {
        return String.format("Element[elementId=%s, elementType=%s, field=%s]", elementId, elementType, field);
    }
    
    public JSONObject toJSONObject()
    {
        JSONObject head = new JSONObject();
        head.put("elementId", elementId);
        head.put("elementType", elementType);
        head.put("fieldName", field.fieldName);
        return head;
    }
}
