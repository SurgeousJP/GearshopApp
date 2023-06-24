package com.example.gearshop.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class GoogleDriveService {

    private static final String APPLICATION_NAME = "GearshopApp";
    private static final String CREDENTIALS_FILE_NAME = "ornate-method-380613-a4adf8d5ecb4.json";
    private static final int REQUEST_CODE_OPEN_FILE = 1001;
    private Drive driveService;
    private Activity activity;
    private OnFileSelectedListener fileSelectedListener;

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
                    FileList fileList = driveService.files().list().execute();
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
                    // Display a file picker dialog or UI to let the user select a file
                    // ...

                    // Example: Using an AlertDialog to display file names and let the user select one
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Select a file");
                    final String[] fileNames = new String[files.size()];
                    for (int i = 0; i < files.size(); i++) {
                        fileNames[i] = files.get(i).getName();
                    }
                    builder.setItems(fileNames, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String fileId = files.get(which).getId();
                            String fileUrl = getFileUrl(fileId);
                            if (fileSelectedListener != null) {
                                fileSelectedListener.onFileSelected(fileUrl);
                            }
                        }
                    });
                    builder.show();
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

    private String getFileUrl(String fileId) {
        try {
            File file = driveService.files().get(fileId).execute();
            return file.getWebViewLink();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

