package com.MasterSolutions.appeludos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Verificar si el usuario está autenticado
        if (firebaseAuth.getCurrentUser() == null) {
            redirectToLogin();
            return;
        }

        // Inicializar Firestore
        firestore = FirebaseFirestore.getInstance();

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewSingle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        petAdapter = new PetAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(petAdapter);

        // Cargar mascotas desde Firestore
        fetchPetsFromFirestore();

        // Botón para agregar mascotas
        findViewById(R.id.btn_add).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CreatePetActivity.class));
        });

        // Botón para refrescar la lista
        findViewById(R.id.btn_refresh).setOnClickListener(v -> {
            Toast.makeText(this, "Actualizando lista...", Toast.LENGTH_SHORT).show();
            fetchPetsFromFirestore();
        });

        // Botón para cerrar sesión
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            firebaseAuth.signOut();
            redirectToLogin();
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

    private void redirectToLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
