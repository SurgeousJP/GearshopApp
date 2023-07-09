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

public class ConfirmChangeShippingInfoDialog extends DialogFragment {
    public interface DialogListener {
        void onDialogResult(boolean result);
    }
    public ConfirmChangeShippingInfoDialog() {
        // Required empty public constructor
    }
    private ConfirmDeleteCartItemDialog.DialogListener dialogListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.confirm_box, null);
        TextView confirmChangeTextContent = dialogView.findViewById(R.id.content_rem);
        confirmChangeTextContent.setText("Bạn xác nhận đổi thông tin vận chuyển chứ ?");
        TextView confirmChangeTextView = dialogView.findViewById(R.id.cta_reset);
        TextView cancelChangeTextView = dialogView.findViewById(R.id.cta_apply);

        confirmChangeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null) {
                    dialogListener.onDialogResult(true);
                }
                dismiss();
            }
        });

        cancelChangeTextView.setOnClickListener(new View.OnClickListener() {
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
    public void setDialogListener(ConfirmDeleteCartItemDialog.DialogListener listener) {
        this.dialogListener = listener;
    }
}
