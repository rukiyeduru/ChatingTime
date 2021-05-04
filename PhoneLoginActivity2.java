package com.example.chatapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneLoginActivity extends AppCompatActivity {
    private Button SendVerificationCodeButton, VerifyButton;
    private EditText InputPhoneNumber, InputVerificationCode;

   private  PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks ;
   private String mVerificationId ;
   private PhoneAuthProvider.ForceResendingToken mResendToken;
    FirebaseAuth mAuth;

    private ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        mAuth=FirebaseAuth.getInstance();

        SendVerificationCodeButton = (Button) findViewById(R.id.send_ver_code_button);
        VerifyButton=(Button) findViewById(R.id.verify_button);
        InputPhoneNumber= (EditText) findViewById(R.id.phone_number_input);
        InputVerificationCode=(EditText) findViewById(R.id.verification_code_input);
        loadingbar=new ProgressDialog(this);


        SendVerificationCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = InputPhoneNumber.getText().toString();

                if(TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(PhoneLoginActivity.this,"Telefon Numaranızı Giriniz..",Toast.LENGTH_SHORT).show();
                }else{
                    loadingbar.setTitle("Telefon Doğrulama");
                    loadingbar.setMessage("Lütfen bekleyin..");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(phoneNumber)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(PhoneLoginActivity.this)                 // Activity (for callback binding)
                                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }

            }
        });
        VerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendVerificationCodeButton.setVisibility(View.INVISIBLE);
                InputPhoneNumber.setVisibility(View.INVISIBLE);

                String verificationCode= InputVerificationCode.getText().toString();

                if(TextUtils.isEmpty(verificationCode)){
                    Toast.makeText(PhoneLoginActivity.this, "Lütfen Doğrulama Kodunu Yazınız..", Toast.LENGTH_SHORT).show();
                }else {

                    loadingbar.setTitle("Doğrulama Kodu");
                    loadingbar.setMessage("Lütfen bekleyin..");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();


                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);

                }

            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

                String verificationCode= InputVerificationCode.getText().toString();
                if(TextUtils.isEmpty(verificationCode)){
                    Toast.makeText(PhoneLoginActivity.this, "Lütfen Doğrulama Kodunu Yazınız..", Toast.LENGTH_SHORT).show();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                    signInWithPhoneAuthCredential(credential);

                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                loadingbar.dismiss();
                Toast.makeText(PhoneLoginActivity.this,"Geçersiz Telefon Numarası,Lütfen Geçerli bir Telefon Numarası Giriniz..",Toast.LENGTH_SHORT).show();

                SendVerificationCodeButton.setVisibility(View.VISIBLE);
                InputPhoneNumber.setVisibility(View.VISIBLE);

                VerifyButton.setVisibility(View.INVISIBLE);
                InputVerificationCode.setVisibility(View.INVISIBLE);
            }
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {


                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                loadingbar.dismiss();
                Toast.makeText(PhoneLoginActivity.this,"Kod Gönderildi,Lütfen Kontrol Ediniz..",Toast.LENGTH_SHORT).show();

                SendVerificationCodeButton.setVisibility(View.INVISIBLE);
                InputPhoneNumber.setVisibility(View.INVISIBLE);

                VerifyButton.setVisibility(View.VISIBLE);
                InputVerificationCode.setVisibility(View.VISIBLE);
            }
        };
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingbar.dismiss();
                            Toast.makeText(PhoneLoginActivity.this, "Giriş Başarılı..", Toast.LENGTH_SHORT).show();
                            SendUserToMainActivity();

                        } else {
                            String message= task.getException().toString();
                            Toast.makeText(PhoneLoginActivity.this, "Hata.."+ message, Toast.LENGTH_SHORT).show();
                            }
                        }

                });
    }

    private void SendUserToMainActivity() {
        Intent maiIntnet= new Intent(PhoneLoginActivity.this,MainActivity.class);
        startActivity(maiIntnet);
        finish();
    }
}
