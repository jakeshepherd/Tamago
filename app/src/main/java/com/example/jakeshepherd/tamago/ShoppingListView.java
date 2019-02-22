package com.example.jakeshepherd.tamago;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


//TODO -- popup for adding stuff to shopping list
public class ShoppingListView extends AppCompatActivity {
    ShoppingList shoppingList;
    ArrayList<foodItem> foodArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingList = ShoppingList.getInstance();
        foodArray = shoppingList.getShoppingList();

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Open adding to shopping list", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        viewShoppingList();
    }

    //Testing
    public void addToShoppingList(foodItem toAdd){
        shoppingList.addToShoppingList(toAdd);
    }

    public void viewShoppingList(){
        TextView scrollingText;
        LinearLayout linearLayout=findViewById(R.id.scrllinear);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < foodArray.size(); i++) {
            scrollingText = new TextView(this);
            scrollingText.setText(new StringBuilder().append(new StringBuilder().append("  ").append(foodArray.get(i).getFoodName()).toString()));
            scrollingText.setTextSize(30);
            linearLayout.addView(scrollingText, lp);
        }





    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}
