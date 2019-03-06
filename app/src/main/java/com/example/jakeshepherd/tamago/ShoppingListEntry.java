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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_entry);

        setupOnClickListeners();
    }

    public void setupOnClickListeners(){
        Button insertButton = findViewById(R.id.insertButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                EditText foodName = findViewById(R.id.foodItem);
                EditText foodQuantity = findViewById(R.id.quantity);

                if(foodName.getText().length() == 0 || foodQuantity.getText().length() == 0){
                    if(foodName.getText().length() == 0){
                        foodName.setError("Cannot be blank");
                    }
                    if(foodQuantity.getText().length() == 0){
                        foodQuantity.setError("Cannot be blank");
                    }
//                    if(Integer.parseInt(foodQuantity.getText().toString()) <= 0){
//                        foodQuantity.setError("Cannot be less than 0");
//                    }
                }else{
                    returnIntent.putExtra("FoodName", String.valueOf(foodName.getText()));
                    returnIntent.putExtra("FoodQuantity", Integer.parseInt(String.valueOf(foodQuantity.getText())));

                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });

    }
}