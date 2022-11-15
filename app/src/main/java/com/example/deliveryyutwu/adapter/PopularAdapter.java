package com.example.deliveryyutwu.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliveryyutwu.fragment.DetailFragment;
import com.example.deliveryyutwu.model.Platillos;
import com.example.deliveryyutwu.R;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolderPopular> {
    ArrayList<Platillos> listaPlatillos;
    Context context;
    /*private String host = "http://192.168.1.5/YutWuSac/";*/
    private String host = "http://yutwu.000webhostapp.com/";

    public PopularAdapter(ArrayList<Platillos> listaPlatillos, Context context) {
        this.listaPlatillos = listaPlatillos;
        this.context = context;
    }

    @Override
    public ViewHolderPopular onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_popular, parent, false);
        return new ViewHolderPopular(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPopular holder, int position) {
        holder.titulo.setText(listaPlatillos.get(position).getNombreProducto());
        holder.precio.setText(String.valueOf(listaPlatillos.get(position).getPrecio()));

        Glide.with(this.context)
                .load(host+"start/img"+listaPlatillos.get(position).getImagen())
                .into(holder.icono);

        holder.mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailFragment fragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", listaPlatillos.get(position));
                fragment.setArguments(bundle);

                FragmentManager fm = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_main, fragment);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPlatillos.size();
    }

    public class ViewHolderPopular extends RecyclerView.ViewHolder {
        TextView titulo, precio, mostrar;
        ImageView icono;

        public ViewHolderPopular(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.nomPopular);
            icono = itemView.findViewById(R.id.iconPopular);
            precio = itemView.findViewById(R.id.precioPopular);
            mostrar = itemView.findViewById(R.id.btnMostrar);
        }
    }
}
