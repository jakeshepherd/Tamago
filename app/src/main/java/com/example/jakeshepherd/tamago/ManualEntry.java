package com.example.jakeshepherd.tamago;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ManualEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manualentrylayout);

        Button insert = (Button) findViewById(R.id.insertButton);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName = ((EditText)findViewById(R.id.editRemoveName)).getText().toString();
                String quantity = ((EditText)findViewById(R.id.editDeleteQuantity)).getText().toString();
                int integerQuantity = Integer.parseInt(quantity);
                String expirationDate = ((EditText)findViewById(R.id.expirationDate)).getText().toString();
                if (checkName((foodName)) && checkQuantity(integerQuantity) && checkDateFormat(expirationDate)) {
                    Snackbar.make(view, "All accepted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "Not all accepted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public boolean checkName(String name) {
        if(name == "") {
            System.err.print("Insert a food name.");
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkQuantity(int quantity) {
        if(quantity <= 0) {
            System.err.print("Insert a quantity.");
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isExpired(String date) {
        String s = getToday("yyyy/MM/dd");
        try {
            Date expiryDate = sdf.parse(date);
            Date todayDate = sdf.parse(s);
            if(todayDate.compareTo(expiryDate) >= 0) {
                System.err.print("Can't add foods with expiry date before today");
                return false;
            }
            else {
                return true;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkDateFormat(String date) {
        try {
            Date toCheck = sdf.parse(date);
            if(isExpired(date)) {
                System.out.print("Successfully inserted.");
            }
        }
        catch(ParseException e) {
            System.err.print("Invalid format.");
            return false;
        }
        return true;
    }

    public String getToday(String format) {
        Date date = new Date();
        return new SimpleDateFormat(format).format(date);
    }
}
