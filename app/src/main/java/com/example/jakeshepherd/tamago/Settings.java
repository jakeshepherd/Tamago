package com.example.jakeshepherd.tamago;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button deleteButton = findViewById(R.id.confirmButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllRows();
                showMessage("Your ingredients list was removed");
                startActivity(new Intent(Settings.this, MainActivity.class));
            }
        });
    }

    private void showMessage(String msg) {
        Context c = getApplicationContext();
        Toast t = Toast.makeText(c, msg, Toast.LENGTH_SHORT);
        t.show();
    }
}
