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
        ShoppingList.getInstance();

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

        Button button = findViewById(R.id.testingButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShoppingListView.class));
            }
        });


        // just some database testing

//        Database db2 = new Database (this);
//
//        //Update object database testing
//        foodItem egg = new foodItem("Egg", "2019-02-11", "From Cow");
//        foodItem sausages = new foodItem("Sausages", "2019-02-12", "From Cow");
//        foodItem bacon = new foodItem("Bacon", "2019-02-15", "From pig");
//        foodItem mushrooms = new foodItem("Mushrooms", "2019-02-19", "From plant");
//        foodItem beans = new foodItem("Beans", "2019-02-18", "From plant");
//
//        db2.insertDataFromObject(egg);
//        db2.insertDataFromObject(sausages);
//        db2.insertDataFromObject(bacon);
//        db2.insertDataFromObject(mushrooms);
//        db2.insertDataFromObject(beans);
//
//        TextView textView = findViewById(R.id.tester);
//        textView.setText(db2.getFoodName(3));
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
