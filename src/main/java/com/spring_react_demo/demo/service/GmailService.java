package com.spring_react_demo.demo.service;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.spring_react_demo.demo.exception.EmailFailedToSendException;

import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class GmailService {

    private final Gmail gmailService;

    // Constructor to inject the Gmail service dependency
    @Autowired
    public GmailService(Gmail gmailService) {
        this.gmailService = gmailService;
    }

    // Method to send an email
    public void sendEmail(String toAddress, String subject, String content) throws Exception {

        // Set up mail session properties
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);

        try {
            // Set the sender's email address
            email.setFrom(new InternetAddress("asif.kamal1489@gmail.com"));
            // Add the recipient's email address
            email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(toAddress));
            // Set the email subject
            email.setSubject(subject);
            // Set the email content
            email.setText(content);

            // Create a buffer to hold the email data
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            // Write the email content to the buffer
            email.writeTo(buffer);
            // Convert the buffer content to a byte array
            byte[] rawMessageBytes = buffer.toByteArray();
            // Encode the byte array to a Base64 URL-safe string
            String encodedEmail = java.util.Base64.getUrlEncoder().encodeToString(rawMessageBytes);

            // Create a new Message object
            Message message = new Message();
            // Set the encoded email content as the raw message
            message.setRaw(encodedEmail);
            
            // Send the email using the Gmail API
            message = gmailService.users().messages().send("me", message).execute();

        } catch (Exception e) {
            // Throw a custom exception if sending the email fails
            throw new EmailFailedToSendException();
        }
    }
}