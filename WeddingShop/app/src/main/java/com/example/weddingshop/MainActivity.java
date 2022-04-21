package com.example.weddingshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn, toShop;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.sample_anim);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signin);
        signIn.setOnClickListener(this);

        toShop = (Button) findViewById(R.id.toshop);
        toShop.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextEmail.setOnClickListener(this);

        editTextPassword = (EditText) findViewById(R.id.password);
        editTextPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


        register.startAnimation(animation);
        signIn.startAnimation(animation);
        toShop.startAnimation(animation);
        editTextPassword.startAnimation(animation2);
        editTextEmail.startAnimation(animation2);

        if(Build.VERSION.SDK_INT >=  Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Sikeres bejelentkezés!","bejelentkezés", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUserActivity.class));
                break;
            case R.id.signin:
                userLogin();
                break;
            case R.id.toshop:
                startActivity(new Intent(this, ActivityShop.class));
                break;

        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email hiányzik");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Jelszó hiányzik");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Sikeres bejelentkezés!");
                    builder.setContentTitle("Bejelentkezés");
                    builder.setContentText("Bejelentkeztél a WeddingShopba!");
                    builder.setAutoCancel(true);
                    builder.setSmallIcon(R.drawable.ic_baseline_add_circle_outline_24);
                    NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                    managerCompat.notify(1, builder.build());
                    startActivity(new Intent(MainActivity.this, LogoutActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "Sikeretelen bejelentkezés", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuHome:
                Toast.makeText(this,"Home",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuKijelentkezes:
                Toast.makeText(this,"Kiejelentkezes",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuTermekek:
                Toast.makeText(this,"Termekek",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/
}