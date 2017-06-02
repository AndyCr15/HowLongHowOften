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

import java.util.Calendar;

import static com.androidandyuk.howlonghowoften.MainActivity.completeInInt;
import static com.androidandyuk.howlonghowoften.MainActivity.currentCompletionInt;
import static com.androidandyuk.howlonghowoften.MainActivity.currentRatePerDay;
import static com.androidandyuk.howlonghowoften.MainActivity.displayTime;
import static com.androidandyuk.howlonghowoften.MainActivity.howManyInt;
import static com.androidandyuk.howlonghowoften.MainActivity.itemsLeft;
import static com.androidandyuk.howlonghowoften.MainActivity.iveDoneInt;
import static com.androidandyuk.howlonghowoften.MainActivity.multiplier;
import static com.androidandyuk.howlonghowoften.MainActivity.sdf;
import static com.androidandyuk.howlonghowoften.MainActivity.timeSinceInt;

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
        final EditText timeSince = (EditText) getView().findViewById(R.id.timeSince2);
        final TextView currentCompletion = (TextView) getView().findViewById(R.id.currentCompletion);
        final TextView dateDone = (TextView) getView().findViewById(R.id.dateDone);

        mAdView = (AdView) getView().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                howManyInt = Integer.parseInt(howMany.getText().toString());
                completeInInt = 1;
                Log.i("completeIn is", completeIn.getText().toString());
                try {
                    completeInInt = (Integer.parseInt(completeIn.getText().toString())) * multiplier;
                    iveDoneInt = Integer.parseInt(iveDone.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                itemsLeft = howManyInt - iveDoneInt;
                double projectedEndInt = Math.ceil(itemsLeft / completeInInt);

                projectedEnd.setText(displayTime(projectedEndInt));
            }
        };

        TextWatcher startedTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

                howManyInt = 1;
                iveDoneInt = 1;
                timeSinceInt = 1;
                try {
                    howManyInt = Integer.parseInt(howMany.getText().toString());
                    iveDoneInt = Integer.parseInt(iveDone.getText().toString());
                    timeSinceInt = Integer.parseInt(timeSince.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double currentCompletionInt = 0;
                try {
                    currentRatePerDay = (iveDoneInt / timeSinceInt);
                    itemsLeft = howManyInt - iveDoneInt;
                    currentCompletionInt = Math.ceil(itemsLeft / currentRatePerDay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Calendar now = Calendar.getInstance();
                now.add(Calendar.DATE, (int) currentCompletionInt);
                Log.i("Target Date ", "" + sdf.format(now.getTime()));

                currentCompletion.setText(displayTime(currentCompletionInt));
                dateDone.setText("Finished by " + sdf.format(now.getTime()));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };

        TextWatcher iveDoneTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

                iveDoneInt = 0;
                timeSinceInt = 1;
                try {
                    howManyInt = Integer.parseInt(howMany.getText().toString());
                    iveDoneInt = Integer.parseInt(iveDone.getText().toString());
                    timeSinceInt = Integer.parseInt(timeSince.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                currentRatePerDay = 0;
                try {
                    currentRatePerDay = (iveDoneInt / timeSinceInt);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                itemsLeft = howManyInt - iveDoneInt;
                currentCompletionInt = Math.ceil(itemsLeft / currentRatePerDay);

                Calendar now = Calendar.getInstance();
                now.add(Calendar.DATE, (int) currentCompletionInt);
                Log.i("Target Date ", "" + sdf.format(now.getTime()));

                currentCompletion.setText(displayTime(currentCompletionInt));
                dateDone.setText("Finished by " + sdf.format(now.getTime()));

                // now update the daily rate, considering some have been done

                double projectedEndInt = Math.ceil(itemsLeft / completeInInt);

                projectedEnd.setText(displayTime(projectedEndInt));



            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };

        howMany.addTextChangedListener(inputTextWatcher);

        completeIn.addTextChangedListener(inputTextWatcher);

        iveDone.addTextChangedListener(iveDoneTextWatcher);

        timeSince.addTextChangedListener(startedTextWatcher);
    }


}
