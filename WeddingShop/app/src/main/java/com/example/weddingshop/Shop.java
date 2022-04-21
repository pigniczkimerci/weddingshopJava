package com.example.weddingshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop extends AppCompatActivity {

    private static final String TAG = "";
    private EditText editTextNev, editTextAr, editTextKategoria, editTextKep;
    private Button btn;
    String key = FirebaseAuth.getInstance().getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Shop");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.getKey();
                    list.add(uid);
                }
                System.out.println(list);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }

        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);

        editTextNev = (EditText) findViewById(R.id.name);
        editTextAr = (EditText) findViewById(R.id.price);
        editTextKategoria = (EditText) findViewById(R.id.category);
        editTextKep = (EditText) findViewById(R.id.picture);
        btn = (Button) findViewById(R.id.add);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });


        /*DAOProducts dao = new DAOProducts();
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
            System.out.println(key);

         /*   HashMap<String, Object> hashMap =  new HashMap<>();
            hashMap.put("nev", editTextNev.getText().toString());
            hashMap.put("ar", editTextAr.getText().toString());
            hashMap.put("kategoria", editTextKategoria.getText().toString());
            hashMap.put("kep", editTextKep.getText().toString());
            dao.update(key, hashMap).addOnCompleteListener(suc ->{
                Toast.makeText(this,"Record felvéve",Toast.LENGTH_LONG).show();
            }).addOnFailureListener(er ->{
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
            });*/
        //});


    }

    private void processinsert() {

        Map<String,Object> map=new HashMap<>();
        map.put("nev",editTextNev.getText().toString());
        map.put("ar",editTextAr.getText().toString());
        map.put("kategoria",editTextKategoria.getText().toString());
        map.put("kep",editTextKep.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Shop").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        editTextNev.setText("");
                        editTextAr.setText("");
                        editTextKategoria.setText("");
                        editTextKep.setText("");
                        Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Could not insert",Toast.LENGTH_LONG).show();
                    }
                });
    }


}