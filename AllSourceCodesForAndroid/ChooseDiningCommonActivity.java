package com.example.shihualu.gourmettest.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shihualu.gourmettest.Module.DiningCommons;
import com.example.shihualu.gourmettest.Module.FileManager;
import com.example.shihualu.gourmettest.Module.UserData;
import com.example.shihualu.gourmettest.R;

public class ChooseDiningCommonActivity extends AppCompatActivity {


    private UserData u;
    private int[] LocationPref = new int[4];
    private int PTL_recorder=0;
    private int OTG_recorder=0;
    private int Carrillo_recorder=0;
    private int DLG_recorder=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dining_common);
    }

    public void click_PTL(View view){
        PTL_recorder++;
        if (PTL_recorder % 2 == 1) {
            LocationPref[0] = DiningCommons.PTL;
            ((Button) findViewById(R.id.button_PTL)).setBackgroundColor(Color.GRAY);
        }
        else{
            LocationPref[0] = DiningCommons.NULL;
            ((Button) findViewById(R.id.button_PTL)).setBackgroundColor(Color.LTGRAY);
        }
    }

    public void click_OTG(View view){
        OTG_recorder++;
        if (OTG_recorder % 2 == 1) {
            LocationPref[1] = DiningCommons.OTG;
            ((Button) findViewById(R.id.button_OTG)).setBackgroundColor(Color.GRAY);
        }
        else{
            LocationPref[1] = DiningCommons.NULL;
            ((Button) findViewById(R.id.button_OTG)).setBackgroundColor(Color.LTGRAY);
        }
    }

    public void click_Carrillo(View view){
        Carrillo_recorder++;
        if (Carrillo_recorder % 2 == 1) {
            LocationPref[2] = DiningCommons.CARRILLO;
            ((Button) findViewById(R.id.button_OTG)).setBackgroundColor(Color.GRAY);
        }
        else{
            LocationPref[2] = DiningCommons.NULL;
            ((Button) findViewById(R.id.button_OTG)).setBackgroundColor(Color.LTGRAY);
        }
    }


    public void click_DLG(View view){
        DLG_recorder++;
        if (DLG_recorder % 2 == 1) {
            LocationPref[3] = DiningCommons.DLG;
            ((Button) findViewById(R.id.button_CAR)).setBackgroundColor(Color.GRAY);
        }
        else{
            LocationPref[3] = DiningCommons.NULL;
            ((Button) findViewById(R.id.button_CAR)).setBackgroundColor(Color.LTGRAY);
        }
    }

    public void createAndSaveUserData(){
        this.u = new UserData();
        double MVRatio = getIntent().getIntExtra("Ratio",0);
        u.setMVRatio(MVRatio);
        u.setLocationPref(LocationPref);
        FileManager.saveObj(this,u);
    }

    public void click_Button(View view){
        createAndSaveUserData();
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

}