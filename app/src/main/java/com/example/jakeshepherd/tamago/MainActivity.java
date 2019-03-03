package com.example.jakeshepherd.tamago;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    Database db = new Database(this);
    LinearLayout fridge;
    TextView foodView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        final TextView tester = findViewById(R.id.tester);

        if(db.getNumberOfRows() > 1)
            tester.setVisibility(View.GONE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, ManualEntry.class), -1);
                if(tester.getVisibility() == View.VISIBLE && db.getNumberOfRows() > 1)
                    tester.setVisibility(View.GONE);
            }
        });
        updateFridge();
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
        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("debugTag", db.getNumberOfRows() + "");
        fridge.removeView(foodView);
        updateFridge();
    }

    private void updateFridge(){
        String temp;
        fridge = findViewById(R.id.fridge);
        for(int i = 0; i < db.getNumberOfRows(); i ++){
            foodView = new TextView(findViewById(R.id.scrollView).getContext());
            temp = db.getFoodName(i) + "\n" + db.getFoodCategory(i) + "\n" +
                    db.getFoodQuantity(i) + "\n" + db.getFoodExpiryDate(i);
            foodView.setText(temp);
            foodView.setTextSize(30);
            foodView.setTextColor(Color.BLACK);
            fridge.addView(foodView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
