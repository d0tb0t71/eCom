package com.example.ecom;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecom.adapters.CategoryAdapter;
import com.example.ecom.adapters.ProductAdapter;
import com.example.ecom.adapters.SliderAdapter;
import com.example.ecom.models.CategoryModel;
import com.example.ecom.models.ProductModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    View view;
    RecyclerView recyclerView, productRecyclerView;

    ImageView add_product_btn;

    ArrayList<CategoryModel> categoryModels;
    CategoryAdapter categoryAdapter;

    ProductAdapter productAdapter;
    ArrayList<ProductModel> list;

    FirebaseFirestore db;

    int[] categoryLogo = {
            R.drawable.clothes_hanger,
            R.drawable.running_shoes,
            R.drawable.electronic_device,
            R.drawable.phone_case,
            R.drawable.playtime,
            R.drawable.house,
            R.drawable.food,
            R.drawable.book,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,
            R.drawable.logo,

    };

    String[] categoryName = {

            "Clothing",
            "Shoes",
            "Electronics",
            "Accessories",
            "Kids",
            "Home",
            "Food",
            "Books",
            "E-Shop",
            "Health",
            "Sports",
            "Others"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false);

        //sliderView = view.findViewById(R.id.imageSlider);

        add_product_btn = view.findViewById(R.id.add_product_btn);

        //SliderAdapter sliderAdapter = new SliderAdapter(images);

//        sliderView.setSliderAdapter(sliderAdapter);
//        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
//        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
//        sliderView.startAutoCycle();

        recyclerView = view.findViewById(R.id.categoryRecyclerView);

        productRecyclerView = view.findViewById(R.id.productRecyclerView);
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));


        categoryModels = new ArrayList<>();

        for (int i = 0; i < categoryLogo.length; i++) {
            CategoryModel catModel = new CategoryModel(categoryLogo[i], categoryName[i]);
            categoryModels.add(catModel);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        categoryAdapter = new CategoryAdapter(getContext(), categoryModels);

        recyclerView.setAdapter(categoryAdapter);


        db = FirebaseFirestore.getInstance();

        list = new ArrayList<ProductModel>();

        productAdapter = new ProductAdapter(getContext(), list);

        productRecyclerView.setAdapter(productAdapter);

        getData();




        FirebaseFirestore db = FirebaseFirestore.getInstance();


        DocumentReference documentReference1 = db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                String st = "";
                st += value.getString("userStatus");


                if(st.equals("Customer")){
                    add_product_btn.setVisibility(View.GONE);
                }

            }
        })
        ;


        add_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddProduct.class));
            }
        });

        return view;
    }

    private void getData() {

        db.collection("products")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Toast.makeText(getContext(), "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                list.add(dc.getDocument().toObject(ProductModel.class));
                            }

                            productAdapter.notifyDataSetChanged();

                        }

                    }
                });

    }
}