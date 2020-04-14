package com.example.services.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.services.R;

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
                                shakeRange.dismiss();
                            }
                        });
        return builder.create();
    }
}
