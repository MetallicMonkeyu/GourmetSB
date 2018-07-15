package com.example.shihualu.gourmettest.Controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.shihualu.gourmettest.Module.FileManager;
import com.example.shihualu.gourmettest.Module.UserData;
import com.example.shihualu.gourmettest.R;

public class MainActivity extends AppCompatActivity {

    private UserData userData = new UserData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //if (FileManager.loadObj(MainActivity.this) == null){
                    Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
                    intent.putExtra("user_data", userData);
                    startActivity(intent);
                    finish();
                //}
                /**
                else{
                    userData = (UserData) FileManager.loadObj(MainActivity.this);
                    Intent another_intent = new Intent(MainActivity.this, MainPage.class);
                    another_intent.putExtra("user_data", userData);
                    startActivity(another_intent);
                    finish();
                }
                 */
            }
        }, 2000);

    }


}
