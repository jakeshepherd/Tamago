package com.example.jakeshepherd.tamago;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


//TODO -- popup for adding stuff to shopping list
public class ShoppingListView extends AppCompatActivity {
    ShoppingList shoppingList;
    ArrayList<foodItem> foodArray;
    TextView scrollingText1, scrollingText2;

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
        /*
        addToShoppingList(new foodItem("Egg", 2));
        addToShoppingList(new foodItem("Bacon", 500));
        addToShoppingList(new foodItem("Banana", 6));
        addToShoppingList(new foodItem("Yogurt", 1));
        addToShoppingList(new foodItem("Granola", 100));
        addToShoppingList(new foodItem("Egg", 2));
        addToShoppingList(new foodItem("Bacon", 500));
        addToShoppingList(new foodItem("Banana", 6));
        addToShoppingList(new foodItem("Yogurt", 1));
        addToShoppingList(new foodItem("Granola", 100));
        addToShoppingList(new foodItem("Egg", 2));
        addToShoppingList(new foodItem("Bacon", 500));
        addToShoppingList(new foodItem("Banana", 6));
        addToShoppingList(new foodItem("Yogurt", 1));
        addToShoppingList(new foodItem("Granola", 100));
        addToShoppingList(new foodItem("Egg", 2));
        addToShoppingList(new foodItem("Bacon", 500));
        addToShoppingList(new foodItem("Banana", 6));
        addToShoppingList(new foodItem("Yogurt", 1));
        addToShoppingList(new foodItem("Granola", 100));
        addToShoppingList(new foodItem("Egg", 2));
        addToShoppingList(new foodItem("Bacon", 500));
        addToShoppingList(new foodItem("Banana", 6));
        addToShoppingList(new foodItem("Yogurt", 1));
        addToShoppingList(new foodItem("Granola", 100));
        addToShoppingList(new foodItem("Egg", 2));
        addToShoppingList(new foodItem("Bacon", 500));
        addToShoppingList(new foodItem("Banana", 6));
        addToShoppingList(new foodItem("Yogurt", 1));
        addToShoppingList(new foodItem("Granola", 100));
        addToShoppingList(new foodItem("Egg", 2));
        addToShoppingList(new foodItem("Bacon", 500));
        addToShoppingList(new foodItem("Banana", 6));
        addToShoppingList(new foodItem("Yogurt", 1));
        addToShoppingList(new foodItem("Granola", 100));
        */

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
                showMessage("Should delete...");
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

    // Testing
    public void addToShoppingList(foodItem toAdd){
        shoppingList.addToShoppingList(toAdd);
    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }

    private void refreshShoppingList(){
        LinearLayout linearLayout=findViewById(R.id.scrllinear);
        linearLayout.removeView(scrollingText1);
        linearLayout.removeView(scrollingText2);
        showShoppingList();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        refreshShoppingList();
    }
}

