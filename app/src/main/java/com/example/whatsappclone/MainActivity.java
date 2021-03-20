package com.example.whatsappclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private EditText nPhoneNumber, mCode;
    private Button mSend;


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        FirebaseApp.initializeApp( this );

        nPhoneNumber = findViewById( R.id.phoneNumber );
        mCode = findViewById( R.id.code );

        mSend = findViewById( R.id.send );

       mSend.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startPhoneNumberVerification();

           }
       } );

       mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
           @Override
           public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
               signInWithPhoneAuthCredential(phoneAuthCredential);
           }

           @Override
           public void onVerificationFailed(FirebaseException e) { }
       };


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential( phoneAuthCredential ).addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    userIsLoggedIn();
            }
        } );
    }

    private void userIsLoggedIn() {
    }


    private void startPhoneNumberVerification() {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        nPhoneNumber.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        this,
                        mCallbacks);

            }
        }



