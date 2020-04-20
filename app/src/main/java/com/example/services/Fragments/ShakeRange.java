package com.example.services.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.services.R;
import com.example.services.Services.ShakeService;

public class ShakeRange extends DialogFragment {
    private int range = 1;

    private void setRange(int range) {
        this.range = range;
    }

    private int shakeRange() {
        return range;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void configureSeekBar(View view) {
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        seekBar.setMin(1);
        seekBar.setMax(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setRange(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void startShakeService() {
        final Context currContext = getContext();
        final int range = this.range;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(currContext, ShakeService.class);
                        intent.putExtra(getString(R.string.shaking_range_tag), range);
                        currContext.startService(intent);
                    }
                });
            }
        });
        thread.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final ShakeRange shakeRange = this;
        View view = inflater.inflate(R.layout.shake_range, null);
        builder.setView(view);
        configureSeekBar(view);
        builder.setPositiveButton(getString(R.string.close),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startShakeService();
                                shakeRange.dismiss();
                            }
                        });
        return builder.create();
    }
}
