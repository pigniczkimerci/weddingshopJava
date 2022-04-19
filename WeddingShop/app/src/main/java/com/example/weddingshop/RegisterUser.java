package com.example.weddingshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Console;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView banner, registerUser;
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView)  findViewById(R.id.reg);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.emailReg);
        editTextPassword = (EditText) findViewById(R.id.passwordReg);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.reg:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email hiányzik");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Nem létező email");
            editTextEmail.requestFocus();
        }
        if(password.isEmpty()){
            editTextPassword.setError("Jelszó hiányzik");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6 ){
            editTextPassword.setError("Nem elég hosszú jelszó");
            editTextPassword.requestFocus();
            return;
        }

       // progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    System.out.println("ne gec");
                    User user = new User(email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterUser.this, "Sikeres regisztráció", Toast.LENGTH_LONG).show();
                             //   progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(RegisterUser.this, "Sikertelen regisztráció", Toast.LENGTH_LONG).show();
                            //    progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }else{
                    Toast.makeText(RegisterUser.this, "Sikertelen regisztráció", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }
}