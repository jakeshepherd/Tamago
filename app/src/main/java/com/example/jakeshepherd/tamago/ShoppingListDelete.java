package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShoppingListDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_delete);

        setUpClickListeners();
    }

    public void setUpClickListeners(){
        Button delButton = findViewById(R.id.deleteButton);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                EditText foodName = findViewById(R.id.editRemoveName);
                EditText foodQuantity = findViewById(R.id.editDeleteQuantity);

                returnIntent.putExtra("FoodName", String.valueOf(foodName.getText()));
                returnIntent.putExtra("FoodQuantity", Integer.parseInt(String.valueOf(foodQuantity.getText())));

                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
