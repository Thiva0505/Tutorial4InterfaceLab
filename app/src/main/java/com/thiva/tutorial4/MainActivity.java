package com.thiva.tutorial4;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// Home menu: opens one of the four Tutorial 04 apps
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.cardDataSet).setOnClickListener(v -> open(DataSetActivity.class));
        findViewById(R.id.cardEdible).setOnClickListener(v -> open(EdibleActivity.class));
        findViewById(R.id.cardMobile).setOnClickListener(v -> open(MobileActivity.class));
        findViewById(R.id.cardPrice).setOnClickListener(v -> open(PriceActivity.class));
    }

    private void open(Class<?> screen) {
        startActivity(new Intent(this, screen));
    }
}
