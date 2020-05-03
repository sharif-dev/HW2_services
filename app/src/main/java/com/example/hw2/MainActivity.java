package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean isActive = false;
    int timeHour = 0;
    int timeMinute;
    TextView textView;
    Context context = this;
    Button setTimeButton;
    Button setAlarmButton;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTimeButton = findViewById(R.id.button);
        textView = findViewById(R.id.textView2);
        setAlarmButton = findViewById(R.id.button2);
        editText = findViewById(R.id.editText);
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                setAlarmButton.setVisibility(View.VISIBLE);
            }
        });

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isActive){

                    setAlarmButton.setText("set alarm");
                    Intent serviceIntent = new Intent();
                    serviceIntent.setClass(context, ShakeDetectService.class);
                    stopService(serviceIntent);

                }

                else{
                    setAlarmButton.setText("unset alarm");
                    Intent serviceIntent = new Intent();
                    serviceIntent.setClass(context, ShakeDetectService.class);
                    serviceIntent.putExtra("minute", Integer.toString(timeMinute));
                    serviceIntent.putExtra("hour", Integer.toString(timeHour));
                    serviceIntent.putExtra("speed", editText.getText().toString());
                    startService(serviceIntent);
                }
                isActive = ! isActive;

            }
        });

    }
}

