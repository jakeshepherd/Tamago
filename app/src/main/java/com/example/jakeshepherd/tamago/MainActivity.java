package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView scrollingText1, scrollingText2;
    ImageView imageView;

    Database db = new Database(this);
    Calendar myCal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setOnClickListeners();
        showDBList();
    }

    public void setOnClickListeners(){
        FloatingActionButton addFood = findViewById(R.id.addFoodButton);
        FloatingActionButton deleteFood = findViewById(R.id.delete);

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ManualEntry.class));
            }
        });

        deleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, Popup.class), 1); //1 for popup return

                Log.d("should: ", "remove data from database");
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        String foodName;
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                foodName = data.getStringExtra("FoodName");
                removeFromDB(foodName);
                refreshList();
            }else{
                Log.d("returned: ", "nothing");
            }
        }
    }

    public void refreshList(){
        LinearLayout linearLayout=findViewById(R.id.scrllinearMain);
        linearLayout.removeAllViews();
        showDBList();
    }

    public void removeFromDB(String toRemove){
        int i = db.deleteRowDataFromName(toRemove);
        if(i < 0){
            showMessage("Could not find item in your ingredients");
        }else{
            db.deleteRowData(i);
        }
    }


    public void showDBList(){
        LinearLayout linearLayout=findViewById(R.id.scrllinearMain);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //-------

        //-------
        Log.d("Database", String.valueOf(db.getNumberOfRows()));
        for(int i = 1; i<db.getNumberOfRows(); i++){
            Log.d("Items", db.getFoodName(i));
        }

        // iterates through entire foodArray
        for (int i = 0; i < db.getNumberOfRows(); i++) {
            // build TextView 1
            scrollingText1 = new TextView(this);

            scrollingText1.setText(new StringBuilder().append("  ").append(db.getFoodName(i)).toString());
            scrollingText1.setTextSize(30);
            scrollingText1.setTextColor(Color.BLACK);
            scrollingText1.setBackgroundColor(calculateColourFromDate(i));
            scrollingText1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            // build TextView 2
            scrollingText2 = new TextView(this);

            scrollingText2.setText(new StringBuilder().append("     Quantity: ").append(String.valueOf(db.getFoodQuantity(i))).append("\n     Expiry Date: ").append(String.valueOf(db.getFoodExpiryDate(i))).toString());
            scrollingText2.setTextSize(14);
            scrollingText2.setTextColor(Color.DKGRAY);
            scrollingText2.setBackgroundColor(calculateColourFromDate(i));
            scrollingText2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            imageView = new ImageView(this);

            // add the dynamically created views
            linearLayout.addView(scrollingText1, lp);
            linearLayout.addView(scrollingText2, lp);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, Settings.class));
            return true;
        }
        if(id == R.id.app_update){
            startActivity(new Intent(MainActivity.this, EditPopup.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_shopping_list) {
            startActivity(new Intent(this, ShoppingListView.class));
        } else if (id == R.id.nav_api_test) {
            startActivity(new Intent(this, APILink.class));
        } else if (id == R.id.nav_sync) {
            new SyncData(this).doSyncing();
            refreshList();
            showMessage("Items from your shopping list have been added to your fridge");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    private int calculateColourFromDate (int foodID){
        String foodDateString = db.getFoodExpiryDate(foodID);
        if (db.getFoodExpiryDate(foodID) == null){
            return Color.TRANSPARENT;
        }
        try {
            Date foodDate = new SimpleDateFormat("dd/MM/yyyy").parse(foodDateString);
            Date today = new Date();
            long diff = foodDate.getTime() - today.getTime();
            long daysToExpiry = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            Log.d("date Colour", foodDate.toString());
            Log.d("date Colour", today.toString());
            Log.d("date Colour", Long.toString(daysToExpiry));

            if(daysToExpiry > 3){
                return Color.GREEN;
            }

            if (daysToExpiry > 0){
                return Color.YELLOW;
            }

            return Color.RED;
        } catch (ParseException e) {
            return Color.TRANSPARENT;
        }
    }

    
}