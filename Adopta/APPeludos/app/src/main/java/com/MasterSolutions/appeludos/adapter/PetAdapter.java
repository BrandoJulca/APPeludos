package com.MasterSolutions.appeludos.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MasterSolutions.appeludos.R;
import com.MasterSolutions.appeludos.model.Pet;
import com.bumptech.glide.Glide;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private final Activity activity;
    private List<Pet> petList;

    // Constructor del adaptador
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

        // Construir la historia
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

        // Cargar imagen usando Glide
        Glide.with(activity)
                .load(pet.getImagen())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    // ViewHolder para el RecyclerView
    public static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView historia;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.photo);
            historia = itemView.findViewById(R.id.historia);
        }
    }
}
