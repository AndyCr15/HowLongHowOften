package com.androidandyuk.howlonghowoften;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    public static String dayOrWeek = "Day";
    public static double multiplier = 1;

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
    public Calendar today;
    public DatePickerDialog.OnDateSetListener datePickerSetListener;
    public DatePickerDialog.OnDateSetListener donePickerSetListener;
    public DatePickerDialog.OnDateSetListener donePickerSetListener2;

    private static final String TAG = "MainActivity";

    public static double howManyInt, howManyInt2;
    public static double completeInInt, completeInInt2;
    public static double dailyRate, dailyRate2;
    public static double iveDoneInt, iveDoneInt2;
    public static double itemsLeft, itemsLeft2;
    public static double currentCompletionInt, currentCompletionInt2;
    public static double currentRatePerDay, currentRatePerDay2;
    public static double timeSinceInt, timeSinceInt2;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        today = Calendar.getInstance();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        datePickerSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar date = Calendar.getInstance(TimeZone.getDefault());
                date.set(year, month, day);

                int chosenDOY = date.get(Calendar.DAY_OF_YEAR);
                int todayDOY = today.get(Calendar.DAY_OF_YEAR);
                int diff = (chosenDOY - todayDOY) + (date.get(Calendar.YEAR) - today.get(Calendar.YEAR)) * 365;

                final EditText completeIn = (EditText) findViewById(R.id.completeIn1);

                completeIn.setText(Integer.toString(diff));

            }
        };

        donePickerSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar date = Calendar.getInstance(TimeZone.getDefault());
                date.set(year, month, day);

                int chosenDOY = date.get(Calendar.DAY_OF_YEAR);
                int todayDOY = today.get(Calendar.DAY_OF_YEAR);
                int diff = (todayDOY - chosenDOY) + (today.get(Calendar.YEAR) - date.get(Calendar.YEAR)) * 365;

                final EditText timeSince = (EditText) findViewById(R.id.timeSince);

                timeSince.setText(Integer.toString(diff));

            }
        };

        donePickerSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar date = Calendar.getInstance(TimeZone.getDefault());
                date.set(year, month, day);

                int chosenDOY = date.get(Calendar.DAY_OF_YEAR);
                int todayDOY = today.get(Calendar.DAY_OF_YEAR);
                int diff = (todayDOY - chosenDOY) + (today.get(Calendar.YEAR) - date.get(Calendar.YEAR)) * 365;

                final EditText timeSince2 = (EditText) findViewById(R.id.timeSince2);

                timeSince2.setText(Integer.toString(diff));

            }
        };


    }

    public void calPicker(View view) {

        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);

        dayOrWeek = "day";
        multiplier = 1;
        updateDayOrWeek();

        DatePickerDialog dialog = new DatePickerDialog(
                MainActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                datePickerSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void donePicker(View view) {

        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);

        dayOrWeek = "day";
        multiplier = 1;
        updateDayOrWeek();

        DatePickerDialog dialog = new DatePickerDialog(
                MainActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                donePickerSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void donePicker2(View view) {

        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);

        dayOrWeek = "day";
        multiplier = 1;
        updateDayOrWeek();

        DatePickerDialog dialog = new DatePickerDialog(
                MainActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                donePickerSetListener2,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_days) {
            dayOrWeek = "day";
            multiplier = 1;
            updateDayOrWeek();
            return true;
        }
        if (id == R.id.action_weeks) {
            dayOrWeek = "week";
            multiplier = 0.142857142857;
            updateDayOrWeek();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateDayOrWeek() {
        TextView completeInDayOrWeek = (TextView) findViewById(R.id.completeInDayOrWeek);
        completeInDayOrWeek.setText(dayOrWeek + "s");
        TextView howManyWillYouDo = (TextView) findViewById(R.id.howManyWillYouDo2);
        howManyWillYouDo.setText("How many will you do a " + dayOrWeek);
    }

    public static String displayTime(double days) {

        if (days < 14) {
            return (int) days + " days";
        }

        String display = "";
        int weeks = (int) days / 7;
        display = weeks + " weeks";

        if ((int) days % 7 > 0) {
            display += " " + (int) days % 7 + " day";
        }

        if (days % 7 > 1) {
            display += "s";
        }

        return display;
    }

    public static String completePer(double dailyRate) {

        Log.i("dailyRate is ", "" + dailyRate);

        double accuracy = dailyRate - Math.floor(dailyRate);

        if (accuracy > 0.857142857 || accuracy == 0 || Math.ceil(dailyRate * 7) > 14) {
            // show daily amount
            return (int) Math.ceil(dailyRate) + " a day";
        }

        Log.i("Complete Per", "Return a per week amount");

        String display = "";

        display += (int) Math.ceil(dailyRate * 7) + " a week";

        return display;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1 tab1 = new Tab1();
                    return tab1;
                case 1:
                    Tab2 tab2 = new Tab2();
                    return tab2;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "HOW OFTEN";
                case 1:
                    return "HOW LONG";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
