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

public class ConfirmDeleteSpecRowDialog extends DialogFragment {
    public interface DialogListener {
        void onDialogResult(boolean result);
    }
    public ConfirmDeleteSpecRowDialog() {
        // Required empty public constructor
    }
    private DialogListener dialogListener;
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.confirm_box, null);
        TextView confirmFilterTextContent = dialogView.findViewById(R.id.content_rem);
        confirmFilterTextContent.setText("Bạn xác nhận xóa dòng cấu hình sản phẩm này chứ?");
        TextView confirmDeleteTextView = dialogView.findViewById(R.id.cta_reset);
        TextView cancelDeleteTextView = dialogView.findViewById(R.id.cta_apply);

        confirmDeleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null) {
                    dialogListener.onDialogResult(true);
                }
                dismiss();
            }
        });

        cancelDeleteTextView.setOnClickListener(new View.OnClickListener() {
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
