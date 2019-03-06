package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// TODO -- move everything to caps

public class ShoppingListView extends AppCompatActivity {
    ShoppingList shoppingList;
    ArrayList<foodItem> foodArray;
    TextView scrollingText1, scrollingText2;
    String foodName, foodNameToAdd;
    String filename = "saved_shopping_list.txt";
    int foodQuantityToAdd;

    private void printList(ArrayList<foodItem>foodArray){
        for (int i = 0; i < foodArray.size(); i++){
            Log.d("File I/O", foodArray.get(i).getFoodName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingList = ShoppingList.getInstance();

        // local arraylist to store shopping list
        foodArray = shoppingList.getShoppingList();
        loadShoppingList(foodArray);


        //-----------
        printList(foodArray);
        //-----------

        showShoppingList();
        setupOnClickListeners();
    }

    /**
     * Set up on click listeners for delete and add button
     */
    public void setupOnClickListeners(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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
                addToShoppingList(new foodItem(foodNameToAdd, foodQuantityToAdd));
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

        // iterates through entire foodArray
        for (int i = 0; i < foodArray.size(); i++) {
            // build TextView 1
            scrollingText1 = new TextView(this);

            scrollingText1.setText(new StringBuilder().append("  ").append(foodArray.get(i).getFoodName()).toString());
            scrollingText1.setTextSize(30);
            scrollingText1.setTextColor(Color.BLACK);

            // build TextView 2
            scrollingText2 = new TextView(this);

            scrollingText2.setText(new StringBuilder().append("     Amount: ").append(foodArray.get(i).getQuantity()).toString());
            scrollingText2.setTextSize(14);
            scrollingText2.setTextColor(Color.DKGRAY);

            // add the dynamically created views
            linearLayout.addView(scrollingText1, lp);
            linearLayout.addView(scrollingText2, lp);
        }
    }

    /**
     * add items to shopping list (from other object) and
     * update the local foodArray
     * @param toAdd
     *      foodItem object to be added
     */
    public void addToShoppingList(foodItem toAdd){
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

    private void saveShoppingList(ArrayList<foodItem> foodArray){

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

    private void loadShoppingList(ArrayList<foodItem> initialList){
        ArrayList<foodItem> toReturn = initialList;

        try{
            FileInputStream is = openFileInput(filename);
            ObjectInputStream in = new ObjectInputStream(is);
            toReturn = (ArrayList<foodItem>)in.readObject();
            in.close();
            is.close();
            showMessage("Loading Succeeded");
            for (int i = 0; i < toReturn.size(); i++){
                shoppingList.addToShoppingList(toReturn.get(i));
            }
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            showMessage("Loading Failed");
        }

        Log.d("File I/O", toReturn.toString());


    }
}

