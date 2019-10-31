package com.dangerfield.kind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import com.dangerfield.kind.di.DaggerMyComponent;
import com.dangerfield.kind.di.MyComponent;
import com.dangerfield.kind.di.MyModule;


public class MainActivity extends AppCompatActivity {
    MyComponent MainComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate injection method
        MainComponent = createMainComponent();
    }

    // ** Class for instantiating DI into feed ** //
    private MyComponent createMainComponent() {
        return DaggerMyComponent
                .builder()
                .myModule(new MyModule())
                .build();
    }
}

