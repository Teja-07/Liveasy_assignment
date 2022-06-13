package com.example.liveasy_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class otp_page extends AppCompatActivity {
    TextView receiver_msg;
    Button b3;
    EditText t2;
    String phonenumber;
    String otpid;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);
        receiver_msg = (TextView)findViewById(R.id.received_value_id);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        receiver_msg.setText(str);


        b3=findViewById(R.id.b3);
        phonenumber = getIntent().getStringExtra("mobile").toString();
        t2 = (EditText) findViewById(R.id.t2);
        mAuth = FirebaseAuth.getInstance();
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (t2.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Blank Field can not be processed", Toast.LENGTH_LONG).show();
                else if (t2.getText().toString().length() != 6)
                    Toast.makeText(getApplicationContext(), "INvalid OTP", Toast.LENGTH_LONG).show();
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, t2.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
        initiateotp();


    }

    private void initiateotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpid = s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(otp_page.this, profileSelection.class));
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Signin Code Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

