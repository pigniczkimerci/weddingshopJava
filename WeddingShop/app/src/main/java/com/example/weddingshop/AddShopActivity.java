package com.example.weddingshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddShopActivity extends AppCompatActivity {

    EditText nev, ar, kategoria, url;
    Button submit, back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nev =(EditText)findViewById(R.id.add_nev);
        kategoria =(EditText)findViewById(R.id.add_kategoria);
        ar =(EditText)findViewById(R.id.add_ar);
        url =(EditText)findViewById(R.id.add_kep);

        back = (Button)findViewById(R.id.add_vissza);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ActivityShop.class));
                finish();
            }
        });

        submit=(Button)findViewById(R.id.add_add);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });

    }

    private void processinsert()
    {
        String neve = nev.getText().toString().trim();
        String ara = ar.getText().toString().trim();
        String kat = kategoria.getText().toString().trim();
        String urlId = url.getText().toString().trim();
        if(neve.isEmpty()){
            nev.setError("Név hiányzik");
            nev.requestFocus();
            return;
        }
        if(ara.isEmpty()){
            ar.setError("Ár hiányzik");
            ar.requestFocus();
            return;
        }
        if(kat.isEmpty()){
            kategoria.setError("Kategória hiányzik");
            kategoria.requestFocus();
            return;
        }
        if(urlId.isEmpty()){
            url.setError("Kép hiányzik");
            url.requestFocus();
            return;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("nev", neve);
        map.put("ar", ara);
        map.put("kategoria", kat);
        map.put("url", urlId);



        FirebaseDatabase.getInstance().getReference().child("Shop").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        nev.setText("");
                        ar.setText("");
                        kategoria.setText("");
                        url.setText("");
                        Toast.makeText(getApplicationContext(),"Sikeres feltöltés",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Sikertelen feltöltés",Toast.LENGTH_LONG).show();
                    }
                });

    }
}