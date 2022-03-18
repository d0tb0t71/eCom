package com.example.ecom;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProductDetails extends AppCompatActivity {

    TextView p_name,p_type,p_price;
    Button p_edit,e_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        String id = getIntent().getStringExtra("id");

        p_name = findViewById(R.id.p_name);
        p_type = findViewById(R.id.p_type);
        p_price = findViewById(R.id.p_price);
        p_edit = findViewById(R.id.p_edit);
        p_name = findViewById(R.id.p_name);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference documentReference = db.collection("products").document(id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                p_name.setText(""+value.getString("pName"));
                p_type.setText(""+value.getString("pType"));
                p_price.setText(""+value.getString("pPrice"));

            }
        });

    }
}