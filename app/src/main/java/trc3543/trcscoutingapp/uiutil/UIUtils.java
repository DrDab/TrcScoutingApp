package trc3543.trcscoutingapp.uiutil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.travijuu.numberpicker.library.NumberPicker;

import androidx.appcompat.app.AlertDialog;
import trc3543.trcscoutingapp.activities.AddMatches;

public class UIUtils {
    public static void setSpinnerByTextValue(Spinner spinner, String toSelect) {
        if (toSelect == null) {
            return;
        }
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(toSelect)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public static void setEditTextValue(EditText editText, Object toSet) {
        if (toSet == null) {
            return;
        }
        editText.setText(toSet.toString() + "");
    }

    public static void setCheckbox(CheckBox checkbox, Boolean toSet) {
        if (toSet == null) {
            return;
        }
        checkbox.setChecked(toSet);
    }

    public static void setNumberPickerVal(NumberPicker numberPicker, Integer toSet) {
        if (toSet == null) {
            return;
        }
        numberPicker.setValue(toSet);
    }

    public static String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }

    public static boolean isEditTextEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    public static void launchPopUpMessage(Context context, String title, String contents)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(contents);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            }
        });
        alertDialog.show();
    }
}
