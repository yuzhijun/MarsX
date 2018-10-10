package com.winning.marsx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.winning.mars_security.core.DirectiveManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DirectiveManager.getInstance(this);
    }

}
