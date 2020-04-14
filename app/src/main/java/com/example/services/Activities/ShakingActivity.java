package com.example.services.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.services.Fragments.ShakeAllow;
import com.example.services.R;

public class ShakingActivity extends AppCompatActivity {
    private ShakeAllow shakeAllow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaking);
        shakeAllow = new ShakeAllow();
        shakeAllow.show(getSupportFragmentManager(), getString(R.string.shaking_allow_tag));
    }
}
