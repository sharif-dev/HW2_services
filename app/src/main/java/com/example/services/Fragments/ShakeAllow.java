package com.example.services.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.services.R;

public class ShakeAllow extends DialogFragment {
    private boolean allowed = false;


    private void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public boolean shakingAllowed() {
        return this.allowed;
    }

    private void clickPositiveButton(DialogInterface dialogInterface, int i) {
        setAllowed(true);
        ShakeRange shakeRange = new ShakeRange();
        shakeRange.show(getFragmentManager(), getString(R.string.shaking_range_tag));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.shake_allow, null))
                .setPositiveButton(getString(R.string.accept),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clickPositiveButton(dialogInterface, i);
                    }
                })
                .setNegativeButton(getString(R.string.deny),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setAllowed(false);
                    }
                });
        return builder.create();
    }
}
