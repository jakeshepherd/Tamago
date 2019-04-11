package com.example.jakeshepherd.tamago;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ManualEntry extends AppCompatActivity {
    Database db = new Database(this);

    EditText foodNameText;
    EditText foodCategoryText;
    EditText quantityText;

    TextView calenderSetter;

    String date;
    int alertDay, alertMonth, alertYear;


    private static String CHANNEL_ID = "default";

    boolean dateValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manualentrylayout);



        calenderSetter = findViewById(R.id.calender);
        setupOnClickListeners(this);
    }

    public void setupOnClickListeners(final Context context){
        Button insert = findViewById(R.id.insertButton);
        ImageView calender = findViewById(R.id.calenderView);
        Button cancel = findViewById(R.id.cancelButton);

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
                        Log.d("DB Insert", date);

                        //setAlarm(String.valueOf(foodNameText.getText()), Integer.parseInt(String.valueOf(quantityText.getText())), date);


                        //createNotification(context, "Tamago", "Your " + String.valueOf(foodNameText.getText()) + " goes out of date on: " +date);

                        startActivity(new Intent(ManualEntry.this, MainActivity.class));
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManualEntry.this, MainActivity.class));
            }
        });
    }

    private void showDatePicker() {
        Calendar calender = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(ManualEntry.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                date = Integer.toString(dayOfMonth) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year);
                alertDay = dayOfMonth;
                alertMonth = month+1;
                alertYear = year;

                //todo add the is expired thing
                calenderSetter.setText(date);
                dateValid = true;
            }
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));

        dpd.show();
    }



    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}


