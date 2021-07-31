package trc492.trcscoutingcodegen.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Page implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @SerializedName("pageNum")
    public int pageNum;
    
    @SerializedName("tabName")
    public String tabName;
    
    @SerializedName("className")
    public String className;
    
    @SerializedName("fragmentName")
    public String fragmentName;
    
    @SerializedName("elements")
    public List<Element> elements;

    public Page(int pageNum, String tabName, String className, String fragmentName, List<Element> elements)
    {
        this.pageNum = pageNum;
        this.tabName = tabName;
        this.className = className;
        this.fragmentName = fragmentName;
        this.elements = elements;
    }

    public Page(int pageNum, String tabName, String className, String fragmentName)
    {
        this(pageNum, tabName, className, fragmentName, new ArrayList<>());
    }

    @Override
    public String toString()
    {
        return String.format("Page[pageNum=%d, tabName=%s, className=%s, fragmentName=%s, elements=%s]", pageNum, tabName,
            className, fragmentName, elements == null ? "NULL" : Arrays.toString(elements.toArray()));
    }

}
