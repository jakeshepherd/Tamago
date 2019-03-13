package com.example.jakeshepherd.tamago;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
//TODO -- date picker activity

public class ManualEntry extends AppCompatActivity {
    Database db = new Database(this);

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    EditText foodNameText;
    EditText foodCategoryText;
    EditText quantityText;

    TextView calenderSetter;

    String date;

    boolean dateValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manualentrylayout);

        Button insert = findViewById(R.id.insertButton);
        ImageView calender = findViewById(R.id.calenderView);
        calenderSetter = findViewById(R.id.calender);

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodNameText = findViewById(R.id.foodItem);
                foodCategoryText = findViewById(R.id.foodCategory);
                quantityText = findViewById(R.id.quantity);


                if (foodNameText.getText().length() == 0 || foodCategoryText.getText().length() == 0 || quantityText.getText().length() == 0) {
                    if (foodNameText.getText().length() == 0) {
                        foodNameText.setError("Cannot be blank");
                    }
                    if (foodCategoryText.getText().length() == 0) {
                        foodCategoryText.setError("Cannot be blank");
                    }
                    if (quantityText.getText().length() == 0) {
                        quantityText.setError("Cannot be blank");
                    }

                } else {
                    if (Integer.parseInt(String.valueOf(quantityText.getText())) <= 0) {
                        quantityText.setError("Must be more than 0");
                    }

                    if (dateValid) {
                        db.insertDataFromObject(new FoodItem(String.valueOf(foodNameText.getText()), String.valueOf(foodCategoryText.getText()),
                                Integer.parseInt(String.valueOf(quantityText.getText())), date));

                        startActivity(new Intent(ManualEntry.this, MainActivity.class));
                    }
                }
            }
        });
    }


    private void showDatePicker() {
        Calendar calender = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(ManualEntry.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                date = Integer.toString(dayOfMonth) + "/" + Integer.toString(month) + "/" + Integer.toString(year);

                //todo add the is expired thing
                calenderSetter.setText(date);
                dateValid = true;
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));

        dpd.show();
    }

    protected int daysToExpiry(String date) {
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    protected boolean isExpired(String date) {
        return daysToExpiry(date) <= 0;
    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}


