package com.example.gearshop.interfaces;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

public interface OnFragmentViewCreatedListener {
    @NonNull
    Dialog onCreateDialog(Bundle savedInstanceState);

    void onFragmentViewCreated(View fragmentView);
}
