package com.example.services.Services;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.Nullable;

public class MyService extends IntentService implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    public MyService() {
        super("MyService");
    }

    @Override
    public void onCreate() {
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (Math.abs(event.values[2]) > 9.79) {
            // TODO: 5/5/2020 lock phone
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
