package com.example.gearshop.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.utility.GoogleDriveService;
import com.google.api.services.drive.model.File;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class ProductImageListAdapter extends BaseAdapter {
    private Context context;
    private List<File> files;
    private Dialog dialog;

    private GoogleDriveService.OnFileSelectedListener onFileSelectedListener;

    public ProductImageListAdapter(Context context, List<File> files, Dialog dialog, GoogleDriveService.OnFileSelectedListener onFileSelectedListener) {
        this.context = context;
        this.files = files;
        this.dialog = dialog;
        this.onFileSelectedListener = onFileSelectedListener;
    }
    public void setData(List<File> files){
        this.files = files;
    }
    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.product_image_item, parent, false);
        }

        File file = files.get(position);
        if (isImageFile(file)){
            @SuppressLint("InflateParams")
            ImageView fileImageView = convertView.findViewById(R.id.file_image_view);
            TextView fileNameTextView = convertView.findViewById(R.id.file_name_text_view);

            // Set the file name
            fileNameTextView.setText(file.getName());
            String imageURL = "https://drive.google.com/uc?id=" + file.getId();
            // Set the image
            Picasso.get()
                    .load(imageURL)
                    .into(fileImageView);
        }
        return convertView;
    }
    private boolean isImageFile(File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        // List of supported image file extensions
        List<String> imageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif", "bmp");

        return imageExtensions.contains(extension.toLowerCase());
    }
}
