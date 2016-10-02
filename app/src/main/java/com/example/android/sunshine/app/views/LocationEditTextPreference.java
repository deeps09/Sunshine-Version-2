package com.example.android.sunshine.app.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.sunshine.app.R;

/**
 * Created by Deepesh_Gupta1 on 10/01/2016.
 */

public class LocationEditTextPreference extends EditTextPreference {
    public static final String LOG_TAG = LocationEditTextPreference.class.getSimpleName();
    int mMinLength;

    public LocationEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LocationEditTextPreference, 0, 0);
        try {
            mMinLength = a.getInteger(R.styleable.LocationEditTextPreference_minLength, 2);
        } finally {
            a.recycle();
        }
        Log.e(LOG_TAG, String.valueOf(mMinLength));
    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);

        final EditText et = getEditText();

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Dialog d = getDialog();
                Button positiveButton;

                if (d instanceof AlertDialog){
                    AlertDialog dialog = (AlertDialog) d;
                    positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);

                    if (et.length() >= mMinLength)
                        positiveButton.setEnabled(true);
                    else
                        positiveButton.setEnabled(false);
                }
            }
        });

    }
}


