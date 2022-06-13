package com.example.liveasy_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class mobilenumber extends AppCompatActivity {
Button b2;

    CountryCodePicker ccp;
    EditText t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobilenumber);
        b2=findViewById(R.id.b2);
        t1=(EditText)findViewById(R.id.t1);
        ccp=(CountryCodePicker)findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(t1);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=t1.getText().toString();
                Intent intent=new Intent(mobilenumber.this,otp_page.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                intent.putExtra("message_key", str);
                startActivity(intent);
            }
        });
    }
}