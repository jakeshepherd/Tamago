package com.example.jakeshepherd.tamago;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Popup extends AppCompatActivity {
    Button cancelButton, confirmButton;
    Spinner deleteSpinner;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.65), (int) (height * 0.25));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setElevation(20);

        db = new Database(this);

        setUpDeleteSpinner();
        setOnCLickListeners();
    }

    public void setOnCLickListeners() {
        final Intent returnIntent = new Intent();
        cancelButton = findViewById(R.id.buttonCancel);
        confirmButton = findViewById(R.id.buttonConfirm);
        deleteSpinner = findViewById(R.id.spinner2);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnIntent.putExtra("FoodName", String.valueOf(deleteSpinner.getSelectedItem()).toUpperCase());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });
    }

    public void setUpDeleteSpinner() {
        Spinner deleteSpinner = findViewById(R.id.spinner2);
        ArrayList<String> currentStringList = (ArrayList<String>) db.getAllFoodNames();
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currentStringList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        deleteSpinner.setAdapter(myAdapter);
    }
}