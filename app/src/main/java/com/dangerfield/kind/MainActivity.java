package com.dangerfield.kind;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.dangerfield.kind.api.CurrentUser;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CurrentUser.INSTANCE.buildDatabase(getApplicationContext());

    }
}

