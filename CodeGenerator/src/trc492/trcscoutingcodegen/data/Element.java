package trc492.trcscoutingcodegen.data;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Element implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @SerializedName("elementId")
    public String elementId;
    
    @SerializedName("elementType")
    public ElementType elementType;
    
    @SerializedName("field")
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
}
