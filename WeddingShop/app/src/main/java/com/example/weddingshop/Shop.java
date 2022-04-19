package com.example.weddingshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class Shop extends AppCompatActivity {

    private EditText editTextNev, editTextAr, editTextKategoria, editTextKep;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        editTextNev = (EditText) findViewById(R.id.name);
        editTextAr = (EditText) findViewById(R.id.price);
        editTextKategoria = (EditText) findViewById(R.id.category);
        editTextKep = (EditText) findViewById(R.id.picture);
        btn = (Button) findViewById(R.id.add);
        DAOProducts dao = new DAOProducts();
        btn.setOnClickListener(v -> {
           Product product = new Product(
                    editTextNev.getText().toString(),
                    editTextAr.getText().toString(),
                    editTextKategoria.getText().toString(),
                    editTextKep.getText().toString()
                    );
            dao.add(product).addOnCompleteListener(suc ->{
                Toast.makeText(this,"Record felvéve",Toast.LENGTH_LONG).show();
            }).addOnFailureListener(er ->{
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            });
        });

    }
}