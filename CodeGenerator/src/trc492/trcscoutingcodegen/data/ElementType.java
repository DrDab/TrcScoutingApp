package trc492.trcscoutingcodegen.data;

public enum ElementType
{
    EditText(FieldType.String, FieldType.Double, FieldType.Integer),
    CheckBox(FieldType.Boolean),
    NumberPicker(FieldType.Integer),
    Spinner(FieldType.String),
    RadioButton(FieldType.String),
    Switch(FieldType.Boolean);
    
    public FieldType[] eligibleFields;
    
    ElementType(FieldType... eligibleFields)
    {
        this.eligibleFields = eligibleFields;
    }
}
