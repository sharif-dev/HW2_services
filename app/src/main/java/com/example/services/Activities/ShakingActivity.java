package com.example.services.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.services.Fragments.ShakeAllow;
import com.example.services.R;

public class ShakingActivity extends AppCompatActivity {
    private ShakeAllow shakeAllow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shakeAllow = new ShakeAllow();
        shakeAllow.show(getSupportFragmentManager(), getString(R.string.shaking_allow_tag));
    }
}
