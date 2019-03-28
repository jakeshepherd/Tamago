package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class ShoppingListDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_delete);
        setUpDeleteDropDown();
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
                Spinner foodName2 = findViewById(R.id.spinner);


                if(String.valueOf(foodName2.getSelectedItem()).toUpperCase().length() == 0 ){
                    //setError("Cannot be blank");
                }else{
                    returnIntent.putExtra("FoodName", String.valueOf(foodName2.getSelectedItem()).toUpperCase());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }

    private void setUpDeleteDropDown(){
        ShoppingList msl = ShoppingList.getInstance();
        Spinner ddb = findViewById(R.id.spinner);
        ArrayList<FoodItem> currentShoppingList = msl.getShoppingList();
        ArrayList<String> currentStringList = new ArrayList<>();
        for (FoodItem item : currentShoppingList){
            currentStringList.add(item.getFoodName());
        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currentStringList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddb.setAdapter(myAdapter);
    }
}
