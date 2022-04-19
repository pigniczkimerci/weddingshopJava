package com.example.weddingshop;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOProducts {

    private DatabaseReference databaseReference;

    public DAOProducts() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        //this.databaseReference = databaseReference;
        databaseReference = db.getReference(Shop.class.getSimpleName());
    }

    public Task<Void> add(Product product){
        return databaseReference.push().setValue(product);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

}
