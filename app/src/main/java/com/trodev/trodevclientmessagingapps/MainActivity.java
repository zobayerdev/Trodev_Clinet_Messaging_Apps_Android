package com.trodev.trodevclientmessagingapps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText recipientEditText;
    private EditText messageEditText;
    private String senderNumber;
    Button send_btn;
    String title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*init views*/
        recipientEditText = findViewById(R.id.et_recipient);
        messageEditText = findViewById(R.id.et_message);
        send_btn = findViewById(R.id.btn_send);

        // Replace with your desired sender number (format: +1234567890)
        senderNumber = "+8801955554447";

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    sendSMS();
                } else {
                    //when permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // check condition
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //permission is granted
            sendSMS();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSMS() {
        String recipient = recipientEditText.getText().toString();
        String message = messageEditText.getText().toString();

        if (recipient.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Please enter recipient and message", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(recipient, senderNumber, message, null, null);
            Toast.makeText(this, "SMS sent from " + senderNumber, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error sending SMS: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}