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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

import static com.androidandyuk.howlonghowoften.MainActivity.completeInInt2;
import static com.androidandyuk.howlonghowoften.MainActivity.currentCompletionInt2;
import static com.androidandyuk.howlonghowoften.MainActivity.currentRatePerDay;
import static com.androidandyuk.howlonghowoften.MainActivity.displayTime;
import static com.androidandyuk.howlonghowoften.MainActivity.howManyInt2;
import static com.androidandyuk.howlonghowoften.MainActivity.itemsLeft2;
import static com.androidandyuk.howlonghowoften.MainActivity.iveDoneInt2;
import static com.androidandyuk.howlonghowoften.MainActivity.multiplier;
import static com.androidandyuk.howlonghowoften.MainActivity.sdf;
import static com.androidandyuk.howlonghowoften.MainActivity.timeSinceInt2;

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
        final EditText iveDone = (EditText) getView().findViewById(R.id.iveDone);
        final EditText timeSince = (EditText) getView().findViewById(R.id.timeSince2);

        howManyInt2 = 1;
        iveDoneInt2 = 0;
        completeInInt2 = 1;
        timeSinceInt2 = 1;

        mAdView = (AdView) getView().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    howManyInt2 = Integer.parseInt(howMany.getText().toString());
                    completeInInt2 = (Integer.parseInt(completeIn.getText().toString())) * multiplier;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                itemsLeft2 = howManyInt2 - iveDoneInt2;
                updateDisplay();
            }
        };

        TextWatcher startedTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {


                try {
//                    howManyInt2 = Integer.parseInt(howMany.getText().toString());
//                    iveDoneInt = Integer.parseInt(iveDone.getText().toString());
                    timeSinceInt2 = Integer.parseInt(timeSince.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    currentRatePerDay = (iveDoneInt2 / timeSinceInt2);
                    itemsLeft2 = howManyInt2 - iveDoneInt2;
                    currentCompletionInt2 = Math.ceil(itemsLeft2 / currentRatePerDay);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                updateDisplay();

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };

        TextWatcher iveDoneTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {

                try {
                    iveDoneInt2 = Integer.parseInt(iveDone.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                currentRatePerDay = 0;
                try {
                    currentRatePerDay = (iveDoneInt2 / timeSinceInt2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                itemsLeft2 = howManyInt2 - iveDoneInt2;
                currentCompletionInt2 = Math.ceil(itemsLeft2 / currentRatePerDay);

                updateDisplay();

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

    public void updateDisplay() {

        final TextView projectedEnd = (TextView) getView().findViewById(R.id.projectedEnd);
        final TextView currentCompletion = (TextView) getView().findViewById(R.id.currentCompletion);
        final TextView dateDone = (TextView) getView().findViewById(R.id.dateDone);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, (int) currentCompletionInt2);

        currentCompletion.setText(displayTime(currentCompletionInt2));
        dateDone.setText("Finished by " + sdf.format(now.getTime()));

        double projectedEndInt = Math.ceil(itemsLeft2 / completeInInt2);

        projectedEnd.setText(displayTime(projectedEndInt));

    }

}
