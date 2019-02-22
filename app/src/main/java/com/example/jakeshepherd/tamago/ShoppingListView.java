package com.example.jakeshepherd.tamago;

import android.content.Context;
import android.graphics.Color;
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
    TextView scrollingText1, scrollingText2;



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

        showShoppingList();
        setupOnClickListeners();
    }

    public void setupOnClickListeners(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Open adding to shopping list", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void showShoppingList(){
        LinearLayout linearLayout=findViewById(R.id.scrllinear);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < foodArray.size(); i++) {
            // build TextView 1
            scrollingText1 = new TextView(this);

            scrollingText1.setText(new StringBuilder().append("  ").append(foodArray.get(i).getFoodName()).toString());
            scrollingText1.setTextSize(30);
            scrollingText1.setTextColor(Color.BLACK);

            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText("Finished?");
            linearLayout.addView(cb);

            linearLayout.addView(scrollingText1, lp);

            // build TextView 2
            scrollingText2 = new TextView(this);

            scrollingText2.setText(new StringBuilder().append("     Amount: ").append(foodArray.get(i).getQuantity()).toString());
            scrollingText2.setTextSize(14);
            scrollingText2.setTextColor(Color.DKGRAY);

            linearLayout.addView(scrollingText2, lp);
        }
    }

    //Testing
    public void addToShoppingList(foodItem toAdd){
        shoppingList.addToShoppingList(toAdd);
    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}

