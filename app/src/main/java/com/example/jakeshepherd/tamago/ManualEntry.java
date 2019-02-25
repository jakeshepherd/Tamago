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

        ClickListener clickListenerObject = new ClickListener();
        insert.setOnClickListener(clickListenerObject);

    }

    private class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            boolean validName, validQuantity, validDate, validCategory, alreadyExpired;
            String foodName = ((EditText)findViewById(R.id.foodItem)).getText().toString();
            String foodQuantity = ((EditText)findViewById(R.id.quantity)).getText().toString();
            String foodExpirationDate = ((EditText)findViewById(R.id.expirationDate)).getText().toString();
            int integerQuantity;

            try{
                integerQuantity = Integer.parseInt(foodQuantity);
            }
            catch(NumberFormatException e){
                integerQuantity = 0;
            }

            validName = checkName(foodName);
            validQuantity = checkQuantity(integerQuantity);
            // validCategory = checkName()
            validDate = checkDateFormat(foodExpirationDate);
            alreadyExpired = isExpired(foodExpirationDate);

            if(validName && validQuantity && validDate && !alreadyExpired)
                Snackbar.make(view, "Accepted", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            else {
                if (!validName)
                    Snackbar.make(view, "Invalid name", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                if (!validQuantity)
                    Snackbar.make(view, "Invalid quantity", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                if (!validDate)
                    Snackbar.make(view, "Invalid date format", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                else if(alreadyExpired)
                    Snackbar.make(view, "Food has already expired", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
            Database db = new Database(ManualEntry.this);
            db.insertDataFromObject(new FoodItem(foodName, foodExpirationDate, "temp"));
            List <String> listOfData = db.getAllFoodNames();
            for(String x: listOfData)
                Log.d("data", x);
            //TextView textView = findViewById(R.id.tester);
            //textView.setText(db.getFoodName(0) + "\n" + db.getExpiryDate(0) + "\n" + db.getFoodCategory(0));
        }
    }

    protected boolean checkName(String name) {
        if(name.trim().equals("")) {
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
