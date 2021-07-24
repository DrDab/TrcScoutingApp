package trc3543.trcscoutingapp.uiutil;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.travijuu.numberpicker.library.NumberPicker;

public class UIUtils
{
    public static void setSpinnerByTextValue(Spinner spinner, String toSelect)
    {
        if (toSelect == null)
        {
            return;
        }
        for (int i = 0; i < spinner.getCount(); i++)
        {
            if (spinner.getItemAtPosition(i).equals(toSelect))
            {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static void setEditTextValue(EditText editText, Object toSet)
    {
        if (toSet == null)
        {
            return;
        }
        editText.setText(toSet.toString() + "");
    }

    public static void setCheckbox(CheckBox checkbox, Boolean toSet)
    {
        if (toSet == null)
        {
            return;
        }
        checkbox.setChecked(toSet);
    }

    public static void setNumberPickerVal(NumberPicker numberPicker, Integer toSet)
    {
        if (toSet == null)
        {
            return;
        }
        numberPicker.setValue(toSet);
    }

    public static String getEditTextValue(EditText editText)
    {
        return editText.getText().toString();
    }
}
