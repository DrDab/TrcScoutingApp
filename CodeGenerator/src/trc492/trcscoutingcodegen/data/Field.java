package trc492.trcscoutingcodegen.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Field implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @SerializedName("fieldType")
    public FieldType fieldType;
    
    @SerializedName("fieldName")
    public String fieldName;
    
    @SerializedName("fieldFlags")
    public List<FieldFlag> fieldFlags;

    public Field(FieldType fieldType, String fieldName, List<FieldFlag> fieldFlags)
    {
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.fieldFlags = fieldFlags;
    }

    public Field(FieldType fieldType, String fieldName)
    {
        this(fieldType, fieldName, new ArrayList<>());
    }

    @Override
    public String toString()
    {
        return String.format("Field[fieldType=%s, fieldName=%s, fieldFlags=%s]", fieldType, fieldName,
            fieldFlags == null ? "NULL" : Arrays.toString(fieldFlags.toArray()));
    }

}
