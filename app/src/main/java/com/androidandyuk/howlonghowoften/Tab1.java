package com.androidandyuk.howlonghowoften;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by AndyCr15 on 09/05/2017.
 */

public class Tab1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab1, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText howMany = (EditText) getView().findViewById(R.id.howMany);
        final EditText completeIn = (EditText) getView().findViewById(R.id.completeIn);
        final TextView projectedEnd = (TextView) getView().findViewById(R.id.projectedEnd);


        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

                int howManyInt = Integer.parseInt(howMany.getText().toString());
                int completeInInt = Integer.parseInt(completeIn.getText().toString());
                int projectedEndInt = howManyInt / completeInInt;

                projectedEnd.setText("" + projectedEndInt);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };

        completeIn.addTextChangedListener(inputTextWatcher);
    }
}
