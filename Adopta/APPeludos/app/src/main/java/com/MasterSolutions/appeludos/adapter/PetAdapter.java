package com.MasterSolutions.appeludos.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MasterSolutions.appeludos.R;
import com.MasterSolutions.appeludos.model.Pet;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private final Context context;
    private List<Pet> petList; // Lista de mascotas

    public PetAdapter(Context context, List<Pet> petList) {
        this.context = context;
        this.petList = petList != null ? petList : new ArrayList<>(); // Evitar null
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pet_single, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);

        // Mostrar la imagen del perro
        Glide.with(context)
                .load(pet.getImagen())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imagen);

        // Crear la historia de la mascota
        String historia = String.format(
                "Hola, soy %s, un(a) %s de %s.\nSoy de tamaño %s, peso %s y tengo un carácter %s.\nMe encuentro en %s.",
                pet.getNombre(),
                pet.getEspecie(),
                pet.getEdad(),
                pet.getTamaño(),
                pet.getPeso(),
                pet.getCarácter(),
                pet.getUbicacion()
        );
        holder.historia.setText(historia);

        // Configurar botón Tel para llamar
        holder.btnTel.setOnClickListener(v -> {
            if (pet.getNumeroContacto() != null && !pet.getNumeroContacto().isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+51" + pet.getNumeroContacto()));
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "El número de contacto no está disponible.", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar botón Eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            petList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "La mascota ha sido eliminada del listado.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen, btnTel, btnEliminar;
        TextView historia;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);

            imagen = itemView.findViewById(R.id.photo);
            btnTel = itemView.findViewById(R.id.btn_tel);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
            historia = itemView.findViewById(R.id.historia);
        }
    }

    // Método para actualizar la lista de mascotas
    public void setPetList(List<Pet> petList) {
        this.petList = petList != null ? petList : new ArrayList<>(); // Manejar listas nulas
        notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }
}
