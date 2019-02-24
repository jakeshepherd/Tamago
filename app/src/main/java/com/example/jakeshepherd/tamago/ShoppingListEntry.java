package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ShoppingListEntry extends AppCompatActivity {

    ShoppingList shoppingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_entry);

        Button insert = (Button) findViewById(R.id.insertButton);

        shoppingList = ShoppingList.getInstance();

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName = ((EditText)findViewById(R.id.foodItem)).getText().toString();
                String quantity = ((EditText)findViewById(R.id.quantity)).getText().toString();
                int integerQuantity = Integer.parseInt(quantity);

                //-----------

                //-----------
                if (checkName((foodName)) && checkQuantity(integerQuantity)) {
                    Snackbar.make(view, "All accepted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Not all accepted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                addToShoppingList(new foodItem(foodName, integerQuantity));
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        });

    }

    public boolean checkName(String name) {
        if(name == "") {
            System.err.print("Insert a food name.");
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkQuantity(int quantity) {
        if(quantity <= 0) {
            System.err.print("Insert a quantity.");
            return false;
        }
        else {
            return true;
        }
    }

    public void addToShoppingList(foodItem toAdd){
        shoppingList.addToShoppingList(toAdd);
    }

}
