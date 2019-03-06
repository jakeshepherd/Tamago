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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//TODO -- popup for adding stuff to shopping list
public class ShoppingListView extends AppCompatActivity {
    ShoppingList shoppingList;
    ArrayList<foodItem> foodArray;
    TextView scrollingText1, scrollingText2;
    String foodName, foodNameToAdd;
    int foodQuantity, foodQuantityToAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingList = ShoppingList.getInstance();

        // local arraylist to store shopping list
        foodArray = shoppingList.getShoppingList();


        // testing stuff

//        addToShoppingList(new foodItem("Egg", 2));
//        addToShoppingList(new foodItem("Bacon", 500));
//        addToShoppingList(new foodItem("Banana", 6));
//        addToShoppingList(new foodItem("Yogurt", 1));
//        addToShoppingList(new foodItem("Granola", 100));
//        addToShoppingList(new foodItem("Egg", 2));
//        addToShoppingList(new foodItem("Bacon", 500));
//        addToShoppingList(new foodItem("Banana", 6));
//        addToShoppingList(new foodItem("Yogurt", 1));
//        addToShoppingList(new foodItem("Granola", 100));
//        addToShoppingList(new foodItem("Egg", 2));
//        addToShoppingList(new foodItem("Bacon", 500));
//        addToShoppingList(new foodItem("Banana", 6));
//        addToShoppingList(new foodItem("Yogurt", 1));
//        addToShoppingList(new foodItem("Granola", 100));
//        addToShoppingList(new foodItem("Egg", 2));
//        addToShoppingList(new foodItem("Bacon", 500));
//        addToShoppingList(new foodItem("Banana", 6));
//        addToShoppingList(new foodItem("Yogurt", 1));
//        addToShoppingList(new foodItem("Granola", 100));
//        addToShoppingList(new foodItem("Egg", 2));
//        addToShoppingList(new foodItem("Bacon", 500));
//        addToShoppingList(new foodItem("Banana", 6));
//        addToShoppingList(new foodItem("Yogurt", 1));
//        addToShoppingList(new foodItem("Granola", 100));
//        addToShoppingList(new foodItem("Egg", 2));
//        addToShoppingList(new foodItem("Bacon", 500));
//        addToShoppingList(new foodItem("Banana", 6));
//        addToShoppingList(new foodItem("Yogurt", 1));
//        addToShoppingList(new foodItem("Granola", 100));
//        addToShoppingList(new foodItem("Egg", 2));
//        addToShoppingList(new foodItem("Bacon", 500));
//        addToShoppingList(new foodItem("Banana", 6));
//        addToShoppingList(new foodItem("Yogurt", 1));
//        addToShoppingList(new foodItem("Granola", 100));

        showShoppingList();
        setupOnClickListeners();
    }

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
     * Method is used to dynamically add more and more text views,
     * dependent on the length of the shopping list
     */
    public void showShoppingList(){
        // sets up layout for the textviews etc to be dynamically added to
        LinearLayout linearLayout=findViewById(R.id.scrllinear);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //-------

        //-------

        // iterates through entire foodArray
        for (int i = 0; i < foodArray.size(); i++) {
            // build TextView 1
            scrollingText1 = new TextView(this);

            scrollingText1.setText(new StringBuilder().append("  ").append(i).append(": ").append(foodArray.get(i).getFoodName()).toString());
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

    // Testing
    public void addToShoppingList(foodItem toAdd){
        shoppingList.addToShoppingList(toAdd);
        foodArray = shoppingList.getShoppingList();
    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    private void refreshShoppingList(){
        LinearLayout linearLayout=findViewById(R.id.scrllinear);
        linearLayout.removeAllViews();
        showShoppingList();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                foodName = data.getStringExtra("FoodName");
                foodQuantity = data.getIntExtra("FoodQuantity", 0);
                showMessage("name: " + foodName + " Quantity: " + foodQuantity);

                removeFromShoppingList(foodName);
                refreshShoppingList();

            }

            if(resultCode == Activity.RESULT_CANCELED){
                Log.d("UserInputEmpty", "Nothing returned");
            }
        }
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                foodNameToAdd = data.getStringExtra("FoodName");
                foodQuantityToAdd = data.getIntExtra("FoodQuantity", 0);
                showMessage("name: " + foodNameToAdd + " Quantity: " + foodQuantityToAdd);

                addToShoppingList(new foodItem(foodNameToAdd, foodQuantityToAdd));
                refreshShoppingList();
            }

            if(resultCode == Activity.RESULT_CANCELED){
                Log.d("UserInputEmpty", "Nothing returned");
            }
        }
    }

    public int searchShoppingList(String foodName){
        for(int i = 0; i<foodArray.size(); i++){
            if(foodArray.get(i).getFoodName().equals(foodName)){
                Log.d("success?", "FOOD NAME FOUND");
                return i;
            }
        }

        return -1;
    }


    public void removeFromShoppingList(String foodName){
        int i = searchShoppingList(foodName);
        if(i>=0){
            shoppingList.removeByIndex(i);
            foodArray = shoppingList.getShoppingList();
        }else{
            //todo -- better error thing
            Log.d("error", "ERROR");
        }

    }
}

