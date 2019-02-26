package com.example.jakeshepherd.tamago;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ManualEntry extends AppCompatActivity {

    protected static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manualentrylayout);

        Button insert = findViewById(R.id.insertButton);

        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean validName, validCategory, validQuantity, validDate, alreadyExpired;
                String foodName, foodCategory, foodQuantity, foodExpirationDate;
                foodName = ((EditText)findViewById(R.id.foodItem)).getText().toString().trim();
                foodCategory = ((EditText)findViewById(R.id.foodCategory)).getText().toString().trim();
                foodQuantity = ((EditText)findViewById(R.id.quantity)).getText().toString().trim();
                foodExpirationDate = ((EditText)findViewById(R.id.expirationDate)).getText().toString().trim();
                int integerQuantity;

                try{
                    integerQuantity = Integer.parseInt(foodQuantity);
                }
                catch(NumberFormatException e){
                    integerQuantity = 0;
                }

                validName = checkName(foodName);
                validCategory = checkName(foodCategory);    // checkName() method is sufficient for this, so far
                validQuantity = checkQuantity(integerQuantity);
                validDate = checkDateFormat(foodExpirationDate);
                alreadyExpired = isExpired(foodExpirationDate);

                int numberOfErrors = 0;
                if(!validName) numberOfErrors ++;
                if(!validCategory) numberOfErrors ++;
                if(!validQuantity) numberOfErrors ++;
                if(!validDate) numberOfErrors ++;
                if(alreadyExpired) numberOfErrors ++;

                if(numberOfErrors == 0)
                    Snackbar.make(view, "Accepted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                else if(numberOfErrors == 1){
                    if (!validName)
                        Snackbar.make(view, "Invalid name", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    if (!validCategory)
                        Snackbar.make(view, "Invalid food category", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    if (!validQuantity)
                        Snackbar.make(view, "Invalid quantity", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    if (!validDate)
                        Snackbar.make(view, "Invalid date format", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    else if(alreadyExpired)
                        Snackbar.make(view, "Food has already expired", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }
                else{
                    Snackbar.make(view, "Multiple input errors", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }

                Database db = new Database(ManualEntry.this);
                db.insertDataFromObject(new FoodItem(foodName, foodExpirationDate, foodCategory));
                List <String> listOfData = db.getAllFoodNames();
                for(String x: listOfData)
                    Log.d("data", x);
                finish();
            }
        });

    }

    protected boolean checkName(String name) {
        if(name.equals("")) {
            Log.d("myTag", "Insert a food name");
            return false;
        }
        else {
            return true;
        }
    }

    protected boolean checkQuantity(int quantity) {
        if(quantity <= 0)
            return false;
        else return true;
    }

    protected boolean checkDateFormat(String date) {   // more comprehensive date checking
        try {
            sdf.parse(date);

            int day, month, year, maxDay;
            day = Integer.parseInt(date.substring(0, 2));
            month = Integer.parseInt(date.substring(3, 5));
            year = Integer.parseInt(date.substring(6));

            if(month < 1 || month > 12)
                return false;
            if(month == 4 || month == 6 || month == 9 || month == 11)
                maxDay = 30;
            else if(month == 2)
                if(year % 4 == 0)
                    maxDay = 29;
                else maxDay = 28;
            else maxDay = 31;
            if(day < 1 || day > maxDay)
                return false;
            if(year < 2000 || year > 2050)
                return false;
        }
        catch(ParseException e) {
            Log.d("myTag", "Invalid format");
            return false;
        }
        catch(NumberFormatException e2 ) {
            Log.d("myTag", "Invalid date");
            return false;
        }
        return true;
    }


    protected boolean isExpired(String date) {
        try {
            Date expiryDate = sdf.parse(date);
            Date todayDate = sdf.parse(sdf.format(new Date()));
            if(todayDate.compareTo(expiryDate) > 0)
                return true;
            else return false;
        }
        catch (ParseException e) {
            return true;
        }

    }
}
