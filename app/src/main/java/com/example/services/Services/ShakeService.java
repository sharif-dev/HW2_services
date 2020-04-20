package com.example.services.Services;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.services.R;

public class ShakeService extends IntentService implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float shakeThreshold;
    private boolean started = false;
    private float xAccPrevious = 0f, yAccPrevious = 0f, zAccPrevious = 0f;
    private float xAcc = 0f, yAcc = 0f, zAcc = 0f;
    public ShakeService() {
        super("ShakeService");
    }


    private void updateAccParams(float xNew, float yNew, float zNew) {
        this.xAccPrevious = this.xAcc;
        this.yAccPrevious = this.yAcc;
        this.zAccPrevious = this.zAcc;

        this.xAcc = xNew;
        this.yAcc = yNew;
        this.zAcc = zNew;
    }

    private boolean isShaking() {
        float deltaXAcc = this.xAcc - this.xAccPrevious;
        float deltaYAcc = this.yAcc - this.yAccPrevious;
        float deltaZAcc = this.zAcc - this.zAccPrevious;
        float totalAcc = (float) Math.sqrt(Math.pow(deltaXAcc, 2) +
                Math.pow(deltaYAcc, 2) + Math.pow(deltaZAcc, 2));
        Log.i(getString(R.string.shaking_range_tag), totalAcc + "");
        if (totalAcc > this.shakeThreshold) {
            boolean value = this.started;
            this.started = !this.started? true: this.started;
            return value;

        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void turnOnScreen() {
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isInteractive();
        PowerManager powerManager = (PowerManager) getBaseContext()
                .getSystemService(getBaseContext().POWER_SERVICE);
        PowerManager.WakeLock wakeLock =
                powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.ON_AFTER_RELEASE, "appname::WakeLock");
        if (!isScreenOn) {
            wakeLock.acquire();
            wakeLock.release();
        }
    }

    @Override
    public void onCreate() {
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.accelerometer = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        this.shakeThreshold = 6f /
                intent.getIntExtra(getString(R.string.shaking_range_tag), 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorEvent se = sensorEvent;
        updateAccParams(se.values[0], se.values[1], se.values[2]);
        if (isShaking())
            turnOnScreen();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
