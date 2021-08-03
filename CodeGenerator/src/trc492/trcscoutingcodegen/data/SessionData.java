package trc492.trcscoutingcodegen.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class SessionData implements Serializable
{
    private static final long serialVersionUID = 1L;

    @SerializedName("fields")
    public List<Field> fields;

    @SerializedName("pages")
    public List<Page> pages;
    
    @SerializedName("csvBindings")
    public SortedMap<Integer, Field> csvBindings;

    public SessionData(List<Field> fields, List<Page> pages, SortedMap<Integer, Field> csvBindings)
    {
        this.fields = fields;
        this.pages = pages;
        this.csvBindings = csvBindings;
    }
    
    public SessionData()
    {
        this(new ArrayList<>(), new ArrayList<>(), new TreeMap<>());
    }

    public SessionData(File file) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new FileReader(file));
        String jsonData = "";
        while (sc.hasNextLine())
            jsonData += sc.nextLine() + "\n";
        sc.close();
        SessionData data = (SessionData) new Gson().fromJson(jsonData, SessionData.class);
        this.fields = data.fields;
        this.pages = data.pages;
        this.csvBindings = data.csvBindings;
    }
    
    public SessionData(String jsonStr)
    {
        SessionData data = (SessionData) new Gson().fromJson(jsonStr, SessionData.class);
        this.fields = data.fields;
        this.pages = data.pages;
        this.csvBindings = data.csvBindings;
    }
    
    public String toJSONString()
    {
        return new Gson().toJson(this);
    }
}
