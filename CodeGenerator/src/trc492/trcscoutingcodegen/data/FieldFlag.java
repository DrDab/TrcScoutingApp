package trc492.trcscoutingcodegen.data;

public enum FieldFlag
{
    MUST_BE_FILLED(),
    MATCH_NUM(FieldType.Integer),
    ALLIANCE_TYPE(FieldType.String),
    MATCH_TYPE(FieldType.String),
    TEAM_NUM(FieldType.Integer);
    
    public FieldType[] eligibleFields;
    
    FieldFlag(FieldType... eligibleFields)
    {
        this.eligibleFields = eligibleFields;
    }
    
}
