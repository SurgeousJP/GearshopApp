package com.example.gearshop.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gearshop.R;
import com.example.gearshop.adapter.ProductImageListAdapter;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleDriveService {

    private static final String APPLICATION_NAME = "GearshopApp";
    private static final String CREDENTIALS_FILE_NAME = "ornate-method-380613-a4adf8d5ecb4.json";
    private static final int REQUEST_CODE_OPEN_FILE = 1001;
    private Drive driveService;
    private final Activity activity;
    private OnFileSelectedListener fileSelectedListener;
    private FileList fileList;

    public interface OnFileSelectedListener {
        void onFileSelected(String fileUrl);
    }

    public GoogleDriveService(Activity activity) {
        this.activity = activity;
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            // Load client secrets from your credentials.json file
            InputStream credentialsStream = activity.getAssets().open(CREDENTIALS_FILE_NAME);
            GoogleCredential credentials = GoogleCredential.fromStream(credentialsStream)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));

            // Build the Drive service using the credentials
            driveService = new Drive.Builder(httpTransport, jsonFactory, credentials)
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void openFileSelection(OnFileSelectedListener listener) {
        fileSelectedListener = listener;
        new AsyncTask<Void, Void, List<File>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected List<File> doInBackground(Void... voids) {
                try {
                    // List all files in the Drive
                    System.out.println("Async Task get Product Image List from Drive");
                    fileList = driveService.files().list().execute();
                    return fileList.getFiles();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception
                    return null;
                }
            }
            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(List<File> files) {
                if (files != null && !files.isEmpty()) {
                    // Create a custom dialog
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.product_image_picker_dialog);
                    dialog.setTitle("Select a file");

                    // Create a listview to contains the item
                    ListView productImageListView = dialog.findViewById(R.id.product_image_list_view);
                    ProductImageListAdapter productImageListAdapter = new ProductImageListAdapter(activity, files,
                            dialog, fileSelectedListener);
                    productImageListView.setAdapter(productImageListAdapter);

                    // Set click listener for the file item
                    productImageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            File file = (File) adapterView.getItemAtPosition(i);
                            String fileId = file.getId();
                            String fileUrl = getFileUrl(fileId);
                            if (fileSelectedListener != null) {
                                fileSelectedListener.onFileSelected(fileUrl);
                            }
                            dialog.dismiss();
                        }
                    });
                    // Show the dialog
                    dialog.show();
                }
            }
        }.execute();
    }

    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_OPEN_FILE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String fileId = getFileIdFromUri(uri);
                if (fileId != null) {
                    String fileUrl = getFileUrl(fileId);
                    if (fileSelectedListener != null) {
                        fileSelectedListener.onFileSelected(fileUrl);
                    }
                }
            }
        }
    }

    private String getFileIdFromUri(Uri uri) {
        // Extract the file ID from the URI
        return uri.getLastPathSegment();
    }
    @SuppressLint("StaticFieldLeak")
    private String getFileUrl(String fileId) {
        return "https://drive.google.com/uc?id=" + fileId;
    }
}

