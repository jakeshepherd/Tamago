package com.example.jakeshepherd.tamago;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /* Button insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insert = (Button) findViewById(R.id.insertButton);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Button Test", Toast.LENGTH_LONG).show();//display the text of button1
            }
        });
    } */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "MANUAL FORM SHOULD GO ON THIS BUTTON PRESS", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, ManualEntry.class));
            }
        });


        // just some database testing
        /* Database db = new Database(this);

        //Update object database testing
        foodItem egg = new foodItem("Egg", "2019-02-11", "From Cow", 2, "Eggs");
        foodItem sausages = new foodItem("Sausages", "2019-02-12", "From Cow", 6, "Sausages");
        foodItem bacon = new foodItem("Bacon", "2019-02-15", "From pig", 8, "Rashers");
        foodItem mushrooms = new foodItem("Mushrooms", "2019-02-19", "From plant", 10, "Shrooms");
        foodItem beans = new foodItem("Beans", "2019-02-18", "From plant", 100, "Grams");

        db.insertDataFromObject(egg);
        db.insertDataFromObject(sausages);
        db.insertDataFromObject(bacon);
        db.insertDataFromObject(mushrooms);
        db.insertDataFromObject(beans);

        TextView textView = findViewById(R.id.tester);
        textView.setText(db.getFoodName(5)); */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
