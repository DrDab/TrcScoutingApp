package trc492.trcscoutingcodegen.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import trc492.trcscoutingcodegen.StrUtil;

public class SessionData implements Serializable
{
    private static final long serialVersionUID = 1L;

    @SerializedName("fields")
    public List<Field> fields;

    @SerializedName("pages")
    public List<Page> pages;

    @SerializedName("csvBindings")
    public SortedMap<Integer, Field> csvBindings;

    @SerializedName("appInfoSettings")
    public AppInfoSettings appInfoSettings;

    public SessionData(List<Field> fields, List<Page> pages, SortedMap<Integer, Field> csvBindings,
        AppInfoSettings appInfoSettings)
    {
        this.fields = fields;
        this.pages = pages;
        this.csvBindings = csvBindings;
        this.appInfoSettings = appInfoSettings;
    }

    public SessionData()
    {
        this(new ArrayList<>(), new ArrayList<>(), new TreeMap<>(), new AppInfoSettings());
    }

    public SessionData(File file) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new FileReader(file));
        String jsonData = "";
        while (sc.hasNextLine())
            jsonData += sc.nextLine() + "\n";
        sc.close();
        instantiate(jsonData);
    }

    public SessionData(String jsonStr)
    {
        instantiate(jsonStr);
    }

    public void instantiate(String jsonStr)
    {
        Gson gson = new Gson();
        JSONObject rootObj = new JSONObject(jsonStr);
        JSONArray fieldsJsonArr = rootObj.getJSONArray("fields");
        JSONArray pagesJsonArr = rootObj.getJSONArray("pages");

        Map<String, Field> fieldNameMap = new HashMap<>();
        fields = new ArrayList<>();

        for (int i = 0; i < fieldsJsonArr.length(); i++)
        {
            JSONObject cur = fieldsJsonArr.getJSONObject(i);
            Field field = gson.fromJson(cur.toString(), Field.class);
            fields.add(field);
            fieldNameMap.put(field.fieldName, field);
        }

        pages = new ArrayList<>();

        for (int i = 0; i < pagesJsonArr.length(); i++)
        {
            JSONObject cur = pagesJsonArr.getJSONObject(i);
            Integer pageNum = cur.getInt("pageNum");
            String tabName = cur.getString("tabName");
            String className = cur.getString("className");
            String fragmentName = cur.getString("fragmentName");
            JSONArray elementsJsonArr = cur.getJSONArray("elements");
            List<Element> elements = new ArrayList<>();
            for (int j = 0; j < elementsJsonArr.length(); j++)
            {
                JSONObject elementJson = elementsJsonArr.getJSONObject(j);
                String elementId = elementJson.getString("elementId");
                ElementType elementType = StrUtil.elementTypeFromStr(elementJson.getString("elementType"));
                Field mappedField = fieldNameMap.get(elementJson.getString("fieldName"));
                Element element = new Element(elementId, elementType, mappedField);
                elements.add(element);
            }
            Page page = new Page(pageNum, tabName, className, fragmentName, elements);
            pages.add(page);
        }

        csvBindings = new TreeMap<>();
        JSONObject csvBindingsJson = rootObj.getJSONObject("csvBindings");
        Map<String, Object> csvBindingJsonMap = csvBindingsJson.toMap();
        for (String key : csvBindingJsonMap.keySet())
        {
            String mappedFieldName = csvBindingsJson.getString(key);
            Field field = fieldNameMap.get(mappedFieldName);
            csvBindings.put(Integer.parseInt(key), field);
        }

        appInfoSettings = gson.fromJson(rootObj.getJSONObject("appInfoSettings").toString(), AppInfoSettings.class);

        System.out.printf("Loaded %d fields, %d pages and %d CSV mappings.\n", fields.size(), pages.size(),
            csvBindings.size());
    }

    public String toJSONString()
    {
        Gson gson = new Gson();
        JSONObject rootObj = new JSONObject();

        JSONArray fieldsArr = new JSONArray();
        JSONArray pagesArr = new JSONArray();

        for (Field field : fields)
        {
            fieldsArr.put(new JSONObject(gson.toJson(field)));
        }

        for (Page page : pages)
        {
            pagesArr.put(page.toJSONObject());
        }

        JSONObject csvBindingsMapObj = new JSONObject();
        JSONObject appInfoSettingsJsonObj = new JSONObject(gson.toJson(appInfoSettings));

        for (Integer colId : csvBindings.keySet())
        {
            Field boundField = csvBindings.get(colId);
            csvBindingsMapObj.put("" + colId, boundField.fieldName);
        }

        rootObj.put("fields", fieldsArr);
        rootObj.put("pages", pagesArr);
        rootObj.put("csvBindings", csvBindingsMapObj);
        rootObj.put("appInfoSettings", appInfoSettingsJsonObj);

        return rootObj.toString();
    }
}
