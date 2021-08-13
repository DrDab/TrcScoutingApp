package trc492.trcscoutingcodegen.classgen;

import java.io.File;
import java.io.PrintStream;

public abstract class ClassGenerator
{
    private File exportFile;

    public ClassGenerator(String... exportPath)
    {
        String pathStr = "";
        for (int i = 0; i < exportPath.length; i++)
        {
            pathStr += exportPath[i];
            if (i != exportPath.length - 1)
            {
                pathStr += "/";
            }
        }
        exportFile = new File(pathStr);
    }

    public File getExportFile()
    {
        return exportFile;
    }

    public abstract String generateCode();

    public boolean exportToFile()
    {
        String code = generateCode();
        if (code == null)
            return false;
        try
        {
            if (!exportFile.getParentFile().exists())
            {
                exportFile.getParentFile().mkdirs();
            }

            if (!exportFile.exists())
            {
                exportFile.createNewFile();
            }

            PrintStream ps = new PrintStream(exportFile);
            ps.println(code);
            ps.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
