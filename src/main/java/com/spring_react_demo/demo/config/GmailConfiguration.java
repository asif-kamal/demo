package com.spring_react_demo.demo.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

@Configuration
public class GmailConfiguration {

    // Application name for the Gmail API
    private static final String APPLICATION_NAME = "Fwitter";
    // JSON factory instance for parsing JSON
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    // Directory to store authorization tokens
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    // Scopes required for the Gmail API
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    // Path to the credentials file
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    // Method to get OAuth2 credentials
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets from the credentials file
        InputStream in = GmailConfiguration.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Credentials file not found");
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build the Google Authorization Code Flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();

        // Set up a local server receiver to handle the OAuth2 callback
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        // Authorize the user and get the credentials
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return credential;
    }     
    
    // Bean to create and configure the Gmail service
    @Bean
    public Gmail getService() {
        NetHttpTransport HTTP_TRANSPORT;

        try {
            // Initialize the HTTP transport
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            // Build and return the Gmail service
            return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
            
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalStateException("Failed to create Gmail service: " + e.getMessage(), e);
        }
    }
}