package com.example.gearshop.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.gearshop.R;
import com.example.gearshop.interfaces.OnFragmentViewCreatedListener;

public class SearchNotFoundFragment extends DialogFragment implements OnFragmentViewCreatedListener {
    public interface DialogListener {
        void onDialogResult(boolean result);
    }
    public SearchNotFoundFragment() {
        // Required empty public constructor
    }
    private ConfirmDeleteCartItemDialogFragment.DialogListener dialogListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.not_found_search, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView).setCancelable(true);
        return builder.create();
    }
    public void setDialogListener(ConfirmDeleteCartItemDialogFragment.DialogListener listener) {
        this.dialogListener = listener;
    }
    @Override
    public void onFragmentViewCreated(View fragmentView) {

    }
}