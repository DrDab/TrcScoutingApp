package trc3543.trcscoutingapp.fragmentcommunication;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public abstract class CollectorClient implements Runnable
{
    private Socket sock;
    private BufferedWriter bw;
    private InputStream istream;
    private BufferedReader receiveRead;
    private Gson gson = new Gson();
    private int id;

    public CollectorClient(int id)
    {
        this.id = id;
    }

    private void sendMessageOverride(String msg) throws IOException
    {
        bw.write(msg+"\n");
        bw.flush();
    }

    private void sendTransactionMsg(CollectorTransaction transaction) throws IOException
    {
        sendMessageOverride(gson.toJson(transaction));
    }

    @Override
    public void run()
    {
        try
        {
            sock = new Socket();
            sock.connect(new InetSocketAddress("127.0.0.1", 36541), 5000);
            bw = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
            istream = sock.getInputStream();
            receiveRead = new BufferedReader(new InputStreamReader(istream));
            boolean isclosed = false;
            String receiveMessage = null;

            for(;;)
            {
                if (sock.isClosed())
                {
                    isclosed = true;
                    break;
                }
                if (isclosed)
                {
                    break;
                }
                receiveMessage = receiveRead.readLine();
                if (receiveMessage != null)
                {
                    CollectorTransaction transaction = (CollectorTransaction) gson.fromJson(receiveMessage, CollectorTransaction.class);
                    JSONObject transactionInfo = transaction.transactionInfo == null ? null : new JSONObject(transaction.transactionInfo);
                    if (transaction.transactionType == CollectorTransaction.TransactionType.REQUEST_AUTH)
                    {
                        JSONObject data = new JSONObject();
                        CollectorTransaction toSend = new CollectorTransaction(
                                CollectorTransaction.TransactionType.AUTH_RESPONSE, data, id);
                        sendTransactionMsg(toSend);
                    }
                    else if (transaction.transactionType == CollectorTransaction.TransactionType.REQUEST_FIELDS)
                    {
                        sendTransactionMsg(
                                new CollectorTransaction(CollectorTransaction.TransactionType.FIELDS_RESPONSE,
                                        onRequestFields(),
                                        id));
                    }
                    else if (transaction.transactionType == CollectorTransaction.TransactionType.SET_FIELDS)
                    {
                        onSettingFields(transactionInfo.getJSONObject("fieldData"));
                    }
                }
            }
            if (isclosed)
            {
                receiveRead.close();
                istream.close();
                bw.close();
                sock.close();
            }
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
    }

    public abstract JSONObject onRequestFields() throws JSONException;

    public abstract void onSettingFields(JSONObject fieldData) throws JSONException;
}
