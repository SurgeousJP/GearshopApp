package com.example.gearshop.utility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleDriveService {

    private static final String APPLICATION_NAME = "GearshopApp";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String CREDENTIALS_FILE_NAME =
            "client_secret_199304663584-012h45i7be2l1vdhdngp1ocbhv7r35tn.apps.googleusercontent.com.json";
    private static final int REQUEST_CODE_OPEN_FILE = 1001;
    private Drive driveService;
    private Activity activity;
    private OnFileSelectedListener fileSelectedListener;

    public interface OnFileSelectedListener {
        void onFileSelected(String fileUrl);
    }

    public GoogleDriveService(Activity activity) {
        this.activity = activity;

        String fileName = "ornate-method-380613-a4adf8d5ecb4.json";
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

            // Load client secrets from your credentials.json file
//            InputStream credentialsStream = GoogleDriveService.class.getResourceAsStream(fileName);
            InputStream credentialsStream = activity.getAssets().open(fileName);
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

    public void openFileSelection(OnFileSelectedListener listener) {
        fileSelectedListener = listener;
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        activity.startActivityForResult(intent, REQUEST_CODE_OPEN_FILE);
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

