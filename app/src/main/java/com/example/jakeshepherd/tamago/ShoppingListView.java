package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ShoppingListView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ShoppingList shoppingList;
    ArrayList<FoodItem> foodArray;
    TextView scrollingText1, scrollingText2;
    String foodName, foodNameToAdd;
    String filename = "saved_shopping_list.txt";
    int foodQuantityToAdd;


    private void printList(ArrayList <FoodItem> foodArray){
        for (int i = 0; i < foodArray.size(); i++){
            Log.d("File I/O", foodArray.get(i).getFoodName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shopping_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        shoppingList = ShoppingList.getInstance();

        // local ArrayList to store shopping list
        foodArray = shoppingList.getShoppingList();
        if(foodArray.size() == 0){
            loadShoppingList(foodArray);
        }

        //-----------
        printList(foodArray);
        //-----------

        showShoppingList();
        setupOnClickListeners();
    }

    public void setupOnClickListeners(){
        FloatingActionButton fab = findViewById(R.id.addFoodButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ShoppingListView.this,ShoppingListEntry.class),1);
            }
        });

        // click listener for the delete button
        ImageButton ib = findViewById(R.id.deleteButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ShoppingListView.this, ShoppingListDelete.class), 2);
            }
        });
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
        getMenuInflater().inflate(R.menu.new_shopping_list_view, menu);
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

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * When intents have been run, will return their code as well as extra data
     * This is then accessed and used in this class
     * @param requestCode
     *      Code set when the intent was called with a result
     * @param resultCode
     *      If the activity was completed or cancelled
     * @param data
     *      Resulting data from the intent
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                foodNameToAdd = data.getStringExtra("FoodName");
                foodQuantityToAdd = data.getIntExtra("FoodQuantity", 0);

                printList(foodArray);
                Log.d("Before Add","Above");
                addToShoppingList(new FoodItem(foodNameToAdd, foodQuantityToAdd));
                printList(foodArray);
                saveShoppingList(foodArray);
                printList(foodArray);
                refreshShoppingList();
            }

            if(resultCode == Activity.RESULT_CANCELED){
                Log.d("UserInputEmpty", "Nothing returned");
            }
        }

        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                foodName = data.getStringExtra("FoodName");

                removeFromShoppingList(foodName);
                refreshShoppingList();

            }

            if(resultCode == Activity.RESULT_CANCELED){
                Log.d("UserInputEmpty", "Nothing returned");
            }
        }
    }

    /**
     * Method is used to dynamically add more and more text views,
     * dependent on the length of the shopping list
     */
    public void showShoppingList(){
        LinearLayout linearLayout=findViewById(R.id.scrllinear);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //-------

        //-------

        if(foodArray.size()<=0){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.trolley);

            linearLayout.addView(imageView, lp);
        }else{
            for (int i = 0; i < foodArray.size(); i++) {
                // build TextView 1
                scrollingText1 = new TextView(this);

                scrollingText1.setText(new StringBuilder().append("  ").append(foodArray.get(i).getFoodName()).toString());
                scrollingText1.setTextSize(30);
                scrollingText1.setTextColor(Color.BLACK);

                // build TextView 2
                scrollingText2 = new TextView(this);

                scrollingText2.setText(new StringBuilder().append("     Amount: ").append(foodArray.get(i).getFoodQuantity()).toString());
                scrollingText2.setTextSize(14);
                scrollingText2.setTextColor(Color.DKGRAY);

                // add the dynamically created views
                linearLayout.addView(scrollingText1, lp);
                linearLayout.addView(scrollingText2, lp);
            }
        }
        // iterates through entire foodArray

    }

    /**
     * add items to shopping list (from other object) and
     * update the local foodArray
     * @param toAdd
     *      foodItem object to be added
     */
    public void addToShoppingList(FoodItem toAdd){
        shoppingList.addToShoppingList(toAdd);
        foodArray = shoppingList.getShoppingList();
    }

    /**
     * Search through list with name, remove name from shoppingList object
     * Update local foodArray
     * @param foodName
     *      Name of food to be removed
     */
    public void removeFromShoppingList(String foodName){
        int i = searchShoppingList(foodName.toUpperCase());
        if(i>=0){
            shoppingList.removeByIndex(i);
            foodArray = shoppingList.getShoppingList();
        }else{

            showMessage(foodName + " does't exist in the shopping list");
            Log.d("error", "Food not found in list");
        }
    }

    /**
     * Searches through list to find name to remove
     * Convert everything to upper case so users case doesn't matter
     * @param foodName
     *      Name of food trying to find an remove
     * @return
     *      Return index of the food to remove (or -1 for not found)
     */
    public int searchShoppingList(String foodName){
        for(int i = 0; i<foodArray.size(); i++){
            if(foodArray.get(i).getFoodName().toUpperCase().equals(foodName)){
                Log.d("success?", "FOOD NAME FOUND");
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes all views from layout
     * and calls method to show the foodArray again
     */
    private void refreshShoppingList(){
        LinearLayout linearLayout=findViewById(R.id.scrllinear);
        linearLayout.removeAllViews();
        saveShoppingList(foodArray);
        showShoppingList();
    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    private void saveShoppingList(ArrayList <FoodItem> foodArray){

        try{
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(foodArray);
            out.close();
            fos.close();
            showMessage("Saving Successful");
        }
        catch (IOException e){
            e.printStackTrace();
            showMessage("Saving was Unsuccessful");
        }
    }

    private void loadShoppingList(ArrayList <FoodItem> initialList){
        ArrayList <FoodItem> toReturn = initialList;

        try{
            FileInputStream is = openFileInput(filename);
            ObjectInputStream in = new ObjectInputStream(is);
            toReturn = (ArrayList <FoodItem>)in.readObject();   // need to 'check' this cast,
            in.close();                                         // whatever that means
            is.close();
            Log.d("Loading state: ",  "Succeeded");
            for (int i = 0; i < toReturn.size(); i++){
                shoppingList.addToShoppingList(toReturn.get(i));
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            Log.d("Loading state: ","Failed");
        }

        Log.d("File I/O", toReturn.toString());


    }
}
