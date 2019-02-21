package com.example.jakeshepherd.tamago;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


//TODO -- popup for adding stuff to shopping list
public class ShoppingListView extends AppCompatActivity {
    ShoppingList shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        shoppingList = ShoppingList.getInstance();

        addToShoppingList(new foodItem("Egg", 2));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //Testing
    public void addToShoppingList(foodItem toAdd){
        shoppingList.addToShoppingList(toAdd);
        ArrayList<foodItem> foodArray = shoppingList.getShoppingList();

        TextView textView = findViewById(R.id.tester);
        textView.setText(foodArray.get(0).getFoodName());

    }



}
