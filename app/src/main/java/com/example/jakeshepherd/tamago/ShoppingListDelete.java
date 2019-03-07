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

    /**
     * On button click:
     * Check length of input, then set errors
     * then send data back through intent
     */
    public void setUpClickListeners(){
        Button delButton = findViewById(R.id.deleteButton);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                EditText foodName = findViewById(R.id.editRemoveName);

                if(foodName.getText().length() == 0 ){
                    foodName.setError("Cannot be blank");
                }else{
                    returnIntent.putExtra("FoodName", String.valueOf(foodName.getText()).toUpperCase());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }
}