package trc3543.trcscoutingapp.fragmentcommunication;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

public class CollectorTransaction implements Serializable
{
    public enum TransactionType
    {
        REQUEST_AUTH,
        AUTH_RESPONSE,
        REQUEST_FIELDS,
        FIELDS_RESPONSE,
        SET_FIELDS
    }

    @SerializedName("clientId")
    public int clientId;
    @SerializedName("transactionType")
    public TransactionType transactionType;
    @SerializedName("transactionInfo")
    public String transactionInfo;

    public CollectorTransaction(TransactionType transactionType, JSONObject transactionInfo, int clientId)
    {
        this.transactionType = transactionType;
        if (transactionInfo != null)
        {
            this.transactionInfo = transactionInfo.toString();
        }
        this.clientId = clientId;
    }

    public CollectorTransaction(TransactionType transactionType, JSONObject transactionInfo)
    {
        this(transactionType, transactionInfo, -1);
    }

    @Override
    public String toString()
    {
        return String.format("Id=%d, Type=%s, Info=%s",
                clientId,
                transactionType,
                transactionInfo == null ? "NULL" :
                        transactionInfo);
    }
}
