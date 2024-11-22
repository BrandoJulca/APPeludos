package com.MasterSolutions.appeludos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class CreatePetActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private EditText nombre, edad, peso, ubicacion;
    private Spinner especieChooser, edadUnidadChooser, tamañoChooser, pesoUnidadChooser, carácterChooser;
    private ImageView imageView;
    private Button btnAddImage, btnAdd;
    private ImageButton btnGetLocation;

    private Uri imageUri;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_pet);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Inicializar Firebase y servicios de ubicación
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Vincular vistas
        nombre = findViewById(R.id.Nombre);
        edad = findViewById(R.id.Edad);
        peso = findViewById(R.id.Peso);
        ubicacion = findViewById(R.id.Ubicación);
        especieChooser = findViewById(R.id.EspecieChooser);
        edadUnidadChooser = findViewById(R.id.EdadUnidadChooser);
        tamañoChooser = findViewById(R.id.TamañoChooser);
        pesoUnidadChooser = findViewById(R.id.PesoUnidadChooser);
        carácterChooser = findViewById(R.id.CarácterChooser);
        imageView = findViewById(R.id.image1);
        btnAddImage = findViewById(R.id.btn_add_images);
        btnGetLocation = findViewById(R.id.btn_get_location);
        btnAdd = findViewById(R.id.btn_add);

        // Configurar Spinners
        setupSpinners();

        // Configurar acciones
        btnGetLocation.setOnClickListener(v -> fetchLocation());

        btnAddImage.setOnClickListener(v -> selectImage());

        btnAdd.setOnClickListener(v -> {
            if (validateInputs()) {
                savePetData();
            }
        });
    }

    private void setupSpinners() {
        // Especie
        ArrayAdapter<String> especieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{
                "Perro", "Gato", "Conejo", "Ave", "Hamster",
                "Tortuga", "Pez", "Iguana", "Ajolote", "Quién sabe"
        });
        especieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        especieChooser.setAdapter(especieAdapter);

        // Unidad de Edad
        ArrayAdapter<String> edadUnidadAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Años", "Meses"});
        edadUnidadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edadUnidadChooser.setAdapter(edadUnidadAdapter);

        // Tamaño
        ArrayAdapter<String> tamañoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{
                "Pequeñito/a", "Mediano/a", "Grandote/a"
        });
        tamañoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tamañoChooser.setAdapter(tamañoAdapter);

        // Unidad de Peso
        ArrayAdapter<String> pesoUnidadAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"kg", "g"});
        pesoUnidadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pesoUnidadChooser.setAdapter(pesoUnidadAdapter);

        // Carácter
        ArrayAdapter<String> carácterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{
                "Juguetón", "Cariñoso", "Independiente", "Activo", "Tranquilo",
                "Protector", "Sociable", "Inteligente", "Leal", "Otro"
        });
        carácterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carácterChooser.setAdapter(carácterAdapter);
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                ubicacion.setText("Perú, Lima, Callao"); // Aquí puedes integrar geocodificación inversa si lo deseas
            } else {
                Toast.makeText(this, "No se pudo obtener la ubicación. Ingresa manualmente.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        } else {
            Toast.makeText(this, "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                imageView.setImageURI(imageUri);
            } else {
                Toast.makeText(this, "No se pudo cargar la imagen seleccionada.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateInputs() {
        if (nombre.getText().toString().trim().isEmpty() || edad.getText().toString().trim().isEmpty() ||
                peso.getText().toString().trim().isEmpty() || ubicacion.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (imageUri == null) {
            Toast.makeText(this, "Por favor, selecciona una imagen.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void savePetData() {
        String imageName = "images/" + System.currentTimeMillis() + ".jpg";
        StorageReference imageRef = storageReference.child(imageName);

        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    Map<String, Object> petData = new HashMap<>();
                    petData.put("nombre", nombre.getText().toString().trim());
                    petData.put("especie", especieChooser.getSelectedItem().toString());
                    petData.put("edad", edad.getText().toString().trim() + " " + edadUnidadChooser.getSelectedItem().toString());
                    petData.put("tamaño", tamañoChooser.getSelectedItem().toString());
                    petData.put("peso", peso.getText().toString().trim() + " " + pesoUnidadChooser.getSelectedItem().toString());
                    petData.put("carácter", carácterChooser.getSelectedItem().toString());
                    petData.put("ubicación", ubicacion.getText().toString().trim());
                    petData.put("imagen", imageUrl);

                    firestore.collection("pet")
                            .add(petData)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(this, "Mascota registrada con éxito.", Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(this, "Error al registrar la mascota.", Toast.LENGTH_SHORT).show());
                }))
                .addOnFailureListener(e -> Toast.makeText(this, "Error al subir la imagen.", Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
