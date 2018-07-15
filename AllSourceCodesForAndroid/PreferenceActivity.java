package com.example.shihualu.gourmettest.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.shihualu.gourmettest.Module.DiningCommons;
import com.example.shihualu.gourmettest.Module.FileManager;
import com.example.shihualu.gourmettest.Module.UserData;
import com.example.shihualu.gourmettest.R;

import java.util.ArrayList;
import java.util.List;

public class PreferenceActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    List<View> viewList;

    private UserData userData = new UserData();

    private int[] LocationPref = new int[4];
    private int PTL_recorder=0;
    private int OTG_recorder=0;
    private int Carrillo_recorder=0;
    private int DLG_recorder=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        userData = (UserData)intent.getSerializableExtra("user_data");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty);
        LayoutInflater mInflater = getLayoutInflater().from(this);

        View v1 = mInflater.inflate(R.layout.activity_preference, null);
        View v2 = mInflater.inflate(R.layout.activity_choose_dining_common, null);
        viewList = new ArrayList<View>();
        viewList.add(v1);
        viewList.add(v2);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new MyPagerAdapter(viewList));
        mViewPager.setCurrentItem(0);
    }

    public void click_PTL(View view){
        PTL_recorder++;
        if (PTL_recorder % 2 == 1) {
            LocationPref[3] = 1;
            ((Button) findViewById(R.id.button_PTL)).setBackgroundColor(Color.GRAY);
        }
        else{
            LocationPref[3] = DiningCommons.NULL;
            ((Button) findViewById(R.id.button_PTL)).setBackgroundColor(Color.LTGRAY);
        }
    }

    public void click_OTG(View view){
        OTG_recorder++;
        if (OTG_recorder % 2 == 1) {
            LocationPref[0] = 1;
            ((Button) findViewById(R.id.button_OTG)).setBackgroundColor(Color.GRAY);
        }
        else{
            LocationPref[0] = DiningCommons.NULL;
            ((Button) findViewById(R.id.button_OTG)).setBackgroundColor(Color.LTGRAY);
        }
    }

    public void click_Carrillo(View view){
        Carrillo_recorder++;
        if (Carrillo_recorder % 2 == 1) {
            LocationPref[1] = 1;
            ((Button) findViewById(R.id.button_CAR)).setBackgroundColor(Color.GRAY);
        }
        else{
            LocationPref[1] = DiningCommons.NULL;
            ((Button) findViewById(R.id.button_CAR)).setBackgroundColor(Color.LTGRAY);
        }
    }

    public void click_DLG(View view){
        DLG_recorder++;
        if (DLG_recorder % 2 == 1) {
            LocationPref[2] = 1;
            ((Button) findViewById(R.id.button_DLG)).setBackgroundColor(Color.GRAY);
        }
        else{
            LocationPref[2] = DiningCommons.NULL;
            ((Button) findViewById(R.id.button_DLG)).setBackgroundColor(Color.LTGRAY);
        }
    }


    public void createAndSaveUserData(){
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);
        double res = seekbar.getProgress();
        double vegMeatRatio = res / 6.0;
        this.userData.setMVRatio(vegMeatRatio);
        this.userData.setLocationPref(LocationPref);

        /**
        Log.d("debug",userData.getMVRatio()+"MV");
        Log.d("debug",userData.getLocationPref()[0] + "pref1");
        Log.d("debug",userData.getLocationPref()[1] + "pref2");
        Log.d("debug",userData.getLocationPref()[2] + "pref3");
        Log.d("debug",userData.getLocationPref()[3] + "pref4");
         */
        FileManager.saveObj(this,this.userData);
    }

    public void click_Button(View view){
        createAndSaveUserData();
        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("user_data", userData);
        startActivity(intent);
    }

    public class MyPagerAdapter extends PagerAdapter {
        private List<View> mList;
        public MyPagerAdapter(List<View> mList) {
            super();
            this.mList = mList;
        }
        @Override
        public int getCount() {
            return mList.size();
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

    }

}