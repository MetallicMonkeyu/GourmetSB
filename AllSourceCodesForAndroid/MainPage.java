package com.example.shihualu.gourmettest.Controller;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.shihualu.gourmettest.Module.DiningCommons;
import com.example.shihualu.gourmettest.Module.Dish;
import com.example.shihualu.gourmettest.Module.UserData;
import com.example.shihualu.gourmettest.R;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    private UserData userData = new UserData();

    private LinearLayout mClick;

    private TextView title_one, title_two, title_three, title_four;

    private float mDensity;

    private ArrayList<Dish> MENU = new ArrayList<Dish>();

    private int mHiddenViewMeasuredHeight, screenHeight, screenheight;

    ArrayList<Dish> conciseMenu_otg,conciseMenu_dlg,conciseMenu_ptl,conciseMenu_crl;



//    private int statusBarHeight;


    DataManager mdm = new DataManager();
    int dc_numbers, concise_number;
    String dining_title;
    private TableLayout tablelayoutone, tablelayouttwo, tablelayoutthree, tablelayoutfour;

    private Point size;
    private DisplayMetrics DISPLAY_METRICS;
    int navBarHeight;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_page);
        Intent intent = getIntent();
        userData = (UserData) intent.getSerializableExtra("user_data");
        // mdm =

//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        screenHeight = dm.heightPixels;








//        doit d = new doit();
        new MainPage.doit().execute();


    }

    public class doit extends AsyncTask<Void, Void, Void> {
        MainPage mainpage;
        @Override

        protected Void doInBackground(Void... voids) {
            //data.getData();
            mdm.getDataFetch().getData();

            return null;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Log.d("Debug",mdm.getConciseMenu(6,DiningCommons.PTL,this).size()+"THING");
//            conciseMenu_otg = mdm.getConciseMenu(6,DiningCommons.OTG);
//            conciseMenu_dlg = mdm.getConciseMenu(6,DiningCommons.DLG);
//            conciseMenu_crl = mdm.getConciseMenu(6,DiningCommons.CARRILLO);
//            conciseMenu_ptl = mdm.getConciseMenu(6,DiningCommons.PTL);
//            Log.d("Debug", conciseMenu_otg.size() + "bs");
            on_Create();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void on_Create(){
        mHiddenViewMeasuredHeight = ((getScreenH()-getNavbarH()/2)/2);

        addWegit(R.id.tablelayoutone, mdm.sortDC(userData).get(0),"#fae1aa");
        Log.d("Debug", "title"+getDC_titles(mdm.sortDC(userData).get(0)));
        addWegit(R.id.tablelayouttwo, mdm.sortDC(userData).get(1),"#f4b489");
        addWegit(R.id.tablelayoutthree, mdm.sortDC(userData).get(2),"#f0917c");
        addWegit(R.id.tablelayoutfour, mdm.sortDC(userData).get(3),"#c36e77");

        tablelayoutone = (TableLayout) findViewById(R.id.tablelayoutone);
        tablelayouttwo = (TableLayout) findViewById(R.id.tablelayouttwo);
        tablelayoutthree = (TableLayout) findViewById(R.id.tablelayoutthree);
        tablelayoutfour = (TableLayout) findViewById(R.id.tablelayoutfour);

        title_one = (TextView) findViewById(R.id.title_one);
        title_two = (TextView) findViewById(R.id.title_two);
        title_three = (TextView) findViewById(R.id.title_three);
        title_four = (TextView) findViewById(R.id.title_four);
        title_one.setText(getDC_titles(mdm.sortDC(userData).get(0)));
        title_two.setText(getDC_titles(mdm.sortDC(userData).get(1)));
        title_three.setText(getDC_titles(mdm.sortDC(userData).get(2)));
        title_four.setText(getDC_titles(mdm.sortDC(userData).get(3)));

        setHeight(R.id.clickone);
        setHeight(R.id.clicktwo);
        setHeight(R.id.clickthree);
        setHeight(R.id.clickfour);

        animateOpen(tablelayoutone);
        animateClose(tablelayouttwo);
        animateClose(tablelayoutthree);
        animateClose(tablelayoutfour);
    }

    //open/close tabs
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.clickone:

                if (tablelayoutone.getVisibility() == View.GONE) {
                    if (tablelayouttwo.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayouttwo);
                    }
                    if (tablelayoutthree.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutthree);
                    }
                    if (tablelayoutfour.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutfour);
                    }
                    animateOpen(tablelayoutone);

//                    title=(TextView) findViewById(R.id.title_one);
//                    Log.d("Debug",title.getText() +" opened");
                } else {
                    if (tablelayouttwo.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayouttwo);
                    }
                    if (tablelayoutthree.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutthree);
                    }
                    if (tablelayoutfour.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutfour);
                    }
                    animateClose(tablelayoutone);


                }
                break;
            case R.id.clicktwo:
                if (tablelayouttwo.getVisibility() == View.GONE) {
                    if (tablelayoutone.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutone);
                    }
                    if (tablelayoutthree.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutthree);
                    }
                    if (tablelayoutfour.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutfour);
                    }
                    animateOpen(tablelayouttwo);



                } else {
                    if (tablelayoutone.getVisibility() == View.GONE) {
                        animateOpen(tablelayoutone);
                    }
                    if (tablelayoutthree.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutthree);
                    }
                    if (tablelayoutfour.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutfour);
                    }
                    animateClose(tablelayouttwo);

                }
                break;
            case R.id.clickthree:
                if (tablelayoutthree.getVisibility() == View.GONE) {
                    if (tablelayouttwo.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayouttwo);
                    }
                    if (tablelayoutone.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutone);
                    }
                    if (tablelayoutfour.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutfour);
                    }
                    animateOpen(tablelayoutthree);



                } else {
                    if (tablelayoutone.getVisibility() == View.GONE) {
                        animateOpen(tablelayoutone);
                    }
                    if (tablelayouttwo.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayouttwo);
                    }
                    if (tablelayoutfour.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutfour);
                    }
                    animateClose(tablelayoutthree);

                }
                break;
            case R.id.clickfour:
                if (tablelayoutfour.getVisibility() == View.GONE) {
                    if (tablelayoutone.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutone);
                    }
                    if (tablelayouttwo.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayouttwo);
                    }
                    if (tablelayoutthree.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutthree);
                    }
                    animateOpen(tablelayoutfour);



                } else {
                    if (tablelayoutone.getVisibility() == View.GONE) {
                        animateOpen(tablelayoutone);
                    }
                    if (tablelayoutthree.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayoutthree);
                    }
                    if (tablelayouttwo.getVisibility() == View.VISIBLE) {
                        animateClose(tablelayouttwo);
                    }
                    animateClose(tablelayoutfour);

                }
                break;
        }
    }


    private void animateOpen(View v) {
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0,
                mHiddenViewMeasuredHeight);
        animator.start();

    }


    private void animateClose(final View view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }

        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.height = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
    //add specific dish to corresponding table view, dc is mdm.sort().get(position), table view is R.id.tablelayoutone
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addWegit(int tableview, int dc, String color) {
//        ArrayList<Dish> dish = new ArrayList<Dish>();
        TableLayout table = (TableLayout) findViewById(tableview);
        //Log.d("debug", tableview+"table");
        table.setStretchAllColumns(true);
//        Log.d("Debug",conciseMenu_otg.size()+"bbs");
//        if (dc == DiningCommons.CARRILLO){
//            //dish = conciseMenu_crl;
//        }
//        else if (dc == DiningCommons.DLG){
//            //dish = conciseMenu_dlg;
//        }else if(dc == DiningCommons.OTG){
//            //dish = conciseMenu_otg;
//        }else if(dc == DiningCommons.PTL){
//            //dish = conciseMenu_ptl;
//        }
        concise_number= mdm.getConciseMenu(6,dc, userData).size();
        //Log.d("Debug",concise_number+"");
        if (concise_number != 0) {

            for (int i = 0; i < concise_number; i++) {
                TableRow tablerow = new TableRow(MainPage.this);
                tablerow.setBackgroundColor(Color.parseColor(color));
                //might have bug
                tablerow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, mHiddenViewMeasuredHeight/concise_number));
//                rgb(222, 220, 210)
                TextView tv = new TextView(MainPage.this);
                tv.setText(mdm.getConciseMenu(6,dc, userData).get(i).getDishName());
                //new
//                tv.setTextSize(mHiddenViewMeasuredHeight/concise_number);
                tv.setHeight(new Integer(mHiddenViewMeasuredHeight/(concise_number+2)));
                tv.setGravity(Gravity.CENTER);
                tv.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
                tablerow.addView(tv);
                table.addView(tablerow, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }else {
            TableRow tablerow = new TableRow(MainPage.this);

            tablerow.setBackgroundColor(Color.parseColor(color));
            tablerow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, mHiddenViewMeasuredHeight));
            TextView tv = new TextView(MainPage.this);
            tv.setText("No item to display");
            tv.setGravity(Gravity.CENTER);
            tablerow.addView(tv);
            table.addView(tablerow, new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        }
    }
    public void setHeight(int view){
        mClick = (LinearLayout) findViewById(view);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mClick.getLayoutParams();
        params.height = (getScreenH()-getNavbarH()/2)/8;
    }
    public String getDC_titles(int num){
        if (num == DiningCommons.PTL) {
            dining_title = "Portola";
        }
        if (num == DiningCommons.OTG) {
            dining_title = "Ortega";
        }
        if (num == DiningCommons.CARRILLO) {
            dining_title = "Carrillo";
        }
        if (num == DiningCommons.DLG) {
            dining_title = "De La Guerra";
        }
        if (num == DiningCommons.NULL) {
            dining_title = "NULL";
        }
        if (num == DiningCommons.IV) {
            dining_title = "IV";
        }
        return dining_title;
    }
    public int getNavbarH(){
        navBarHeight = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
//        Log.d("Debug", "The info id is: " + String.valueOf(resourceId));
        if (resourceId > 0) {
            navBarHeight = getResources().getDimensionPixelSize(resourceId);
//            Log.d("Debug", "The height is: " + String.valueOf(navBarHeight));
        }
        return navBarHeight;
    }
    public int getScreenH(){
        WindowManager windowManager = getWindowManager();
        Display display = getWindowManager().getDefaultDisplay();
        screenheight = display.getHeight();
        return screenheight;
    }




}