package com.MasterSolutions.appeludos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.MasterSolutions.appeludos.adapter.PetAdapter;
import com.MasterSolutions.appeludos.model.Pet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerViewSingle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        petAdapter = new PetAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(petAdapter);

        fetchPetsFromFirestore();

        findViewById(R.id.btn_add).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CreatePetActivity.class))
        );

        findViewById(R.id.btn_refresh).setOnClickListener(v -> {
            Toast.makeText(this, "Actualizando lista...", Toast.LENGTH_SHORT).show();
            fetchPetsFromFirestore();
        });
    }

    private void fetchPetsFromFirestore() {
        firestore.collection("pet").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Pet> petList = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Pet pet = document.toObject(Pet.class);
                petList.add(pet);
            }
            petAdapter.setPetList(petList);
            Toast.makeText(this, "Lista actualizada", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Error al actualizar la lista", Toast.LENGTH_SHORT).show();
        });
    }
}
