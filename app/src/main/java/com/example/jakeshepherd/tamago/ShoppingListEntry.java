package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ShoppingListEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_entry);

        setupOnClickListeners();
    }

    /**
     * On button click, check length of name and quantity then:
     * Set errors
     * then send data back through intents
     */
    public void setupOnClickListeners(){
        Button insertButton = findViewById(R.id.shoppingInsertButton);
        Button cancelButton = findViewById(R.id.shoppingCancelButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                EditText foodName = findViewById(R.id.editRemoveName);
                EditText foodQuantity = findViewById(R.id.editDeleteQuantity);

                if(foodName.getText().length() == 0 || foodQuantity.getText().length() == 0){
                    if(foodName.getText().length() == 0){
                        foodName.setError("Cannot be blank");
                    }
                    if(foodQuantity.getText().length() == 0){
                        foodQuantity.setError("Cannot be blank");
                    }
                }else{
                    if(Integer.parseInt(foodQuantity.getText().toString()) <= 0){
                        foodQuantity.setError("Must be more than 0");
                    }else{
                        returnIntent.putExtra("FoodName", String.valueOf(foodName.getText()));
                        returnIntent.putExtra("FoodQuantity", Integer.parseInt(String.valueOf(foodQuantity.getText())));

                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }
}