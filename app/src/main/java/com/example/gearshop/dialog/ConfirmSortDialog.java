package com.example.gearshop.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.gearshop.R;

public class ConfirmSortDialog extends DialogFragment {
    public interface DialogListener {
        void onDialogResult(boolean result);
    }
    public ConfirmSortDialog() {
        // Required empty public constructor
    }
    private DialogListener dialogListener;
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.confirm_box, null);

        TextView confirmSortTextContent = dialogView.findViewById(R.id.content_rem);
        confirmSortTextContent.setText("Bạn xác nhận sort theo yêu cầu chứ ?");
        TextView confirmSortTextView = dialogView.findViewById(R.id.cta_reset);
        TextView cancelSortTextView = dialogView.findViewById(R.id.cta_apply);

        confirmSortTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null) {
                    dialogListener.onDialogResult(true);
                }
                dismiss();
            }
        });

        cancelSortTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null) {
                    dialogListener.onDialogResult(false);
                }
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        return builder.create();
    }
    public void setDialogListener(DialogListener listener) {
        this.dialogListener = listener;
    }
}

