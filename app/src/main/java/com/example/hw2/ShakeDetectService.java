package com.example.hw2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;

public class ShakeDetectService extends Service {
    public ShakeDetectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),"This is a Service running in Background",
                Toast.LENGTH_LONG).show();
        String hour = intent.getExtras().getString("hour");
        String minute = intent.getExtras().getString("minute");
        String speed = intent.getExtras().getString("speed");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReciever.class);
        alarmIntent.putExtra("speed", speed);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0010000, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        time.set(Calendar.MINUTE, Integer.parseInt(minute));
        time.set(Calendar.SECOND, 0);

        alarmManager.set(AlarmManager.RTC, time.getTimeInMillis(), pendingIntent);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }
}
