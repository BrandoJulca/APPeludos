package com.MasterSolutions.appeludos.adapter;

import android.app.Activity;
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

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private final Activity activity;
    private List<Pet> petList;

    public PetAdapter(Activity activity, List<Pet> petList) {
        this.activity = activity;
        this.petList = petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pet_single, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = petList.get(position);

        holder.nombre.setText(pet.getNombre());
        holder.edad.setText(pet.getEdad());
        holder.peso.setText(pet.getPeso());
        holder.ubicacion.setText(pet.getUbicacion());

        // Usa Glide para cargar la imagen desde Firebase Storage
        Glide.with(activity)
                .load(pet.getImagen())
                .placeholder(R.drawable.ic_launcher_foreground) // Imagen de carga
                .error(R.drawable.ic_launcher_foreground) // Imagen en caso de error
                .into(holder.imagen);

        holder.btnDelete.setOnClickListener(v -> {
            petList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, petList.size());
            Toast.makeText(activity, "Mascota eliminada", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, edad, peso, ubicacion;
        ImageView imagen, btnDelete;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            edad = itemView.findViewById(R.id.edad);
            peso = itemView.findViewById(R.id.peso);
            ubicacion = itemView.findViewById(R.id.ubicacion);
            imagen = itemView.findViewById(R.id.photo);
            btnDelete = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
