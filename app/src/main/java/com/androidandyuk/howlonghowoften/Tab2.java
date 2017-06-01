package com.androidandyuk.howlonghowoften;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import static com.androidandyuk.howlonghowoften.MainActivity.displayTime;
import static com.androidandyuk.howlonghowoften.MainActivity.multiplier;

/**
 * Created by AndyCr15 on 09/05/2017.
 */

public class Tab2 extends Fragment {

    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText howMany = (EditText) getView().findViewById(R.id.howMany);
        final EditText completeIn = (EditText) getView().findViewById(R.id.completeIn2);
        final TextView projectedEnd = (TextView) getView().findViewById(R.id.projectedEnd);
        final EditText iveDone = (EditText) getView().findViewById(R.id.iveDone);
        final EditText timeSince = (EditText) getView().findViewById(R.id.timeSince);
        final TextView currentCompletion = (TextView) getView().findViewById(R.id.currentCompletion);

        mAdView = (AdView) getView().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double howManyInt = Integer.parseInt(howMany.getText().toString());
                double completeInInt = 1;
                Log.i("completeIn is", completeIn.getText().toString());
                try {
                    completeInInt = (Integer.parseInt(completeIn.getText().toString())) * multiplier;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double projectedEndInt = Math.ceil(howManyInt / completeInInt);

                projectedEnd.setText(displayTime(projectedEndInt));
            }
        };

        TextWatcher startedTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

                double howManyInt = 1;
                double iveDoneInt = 1;
                double timeSinceInt = 1;
                try {
                    howManyInt = Integer.parseInt(howMany.getText().toString());
                    iveDoneInt = Integer.parseInt(iveDone.getText().toString());
                    timeSinceInt = Integer.parseInt(timeSince.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double currentCompletionInt = 0;
                try {
                    double currentRatePerDay = (iveDoneInt / timeSinceInt);
                    double itemsLeft = howManyInt - iveDoneInt;
                    currentCompletionInt = Math.ceil(itemsLeft / currentRatePerDay);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                currentCompletion.setText(displayTime(currentCompletionInt));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };

        completeIn.addTextChangedListener(inputTextWatcher);

        timeSince.addTextChangedListener(startedTextWatcher);
    }


}
