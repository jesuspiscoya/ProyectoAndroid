package com.example.deliveryyutwu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliveryyutwu.R;
import com.example.deliveryyutwu.helper.ManagementCart;
import com.example.deliveryyutwu.interfaces.CambiarCantidadListener;
import com.example.deliveryyutwu.model.Platillos;

import java.util.ArrayList;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolderCarrito> {
    private ArrayList<Platillos> listaPlatillos;
    private ManagementCart managementCart;
    private CambiarCantidadListener cambiarCantidadListener;
    private Context context;
    /*private String host = "http://192.168.1.5/YutWuSac/";*/
    private String host = "http://yutwu.000webhostapp.com/";

    public CarritoAdapter(ArrayList<Platillos> listaPlatillos, Context context, CambiarCantidadListener cambiarCantidadListener) {
        this.listaPlatillos = listaPlatillos;
        this.managementCart = new ManagementCart(context);
        this.context = context;
        this.cambiarCantidadListener = cambiarCantidadListener;
    }

    @Override
    public CarritoAdapter.ViewHolderCarrito onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_carrito, parent, false);
        return new CarritoAdapter.ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.ViewHolderCarrito holder, int position) {
        holder.titulo.setText(listaPlatillos.get(position).getNombreProducto());
        holder.costo.setText(String.valueOf(listaPlatillos.get(position).getPrecio()));
        holder.total.setText(String.valueOf(Math.round((listaPlatillos.get(position).getNumeroCart() * listaPlatillos.get(position).getPrecio()) * 100) / 100));
        holder.cantidad.setText(String.valueOf(listaPlatillos.get(position).getNumeroCart()));

        Glide.with(this.context)
                .load(host+"start/img"+listaPlatillos.get(position).getImagen())
                .into(holder.icono);

        holder.agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.agregarCantidad(listaPlatillos, position, new CambiarCantidadListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        cambiarCantidadListener.changed();
                    }
                });
            }
        });

        holder.quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.quitarCantidad(listaPlatillos, position, new CambiarCantidadListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        cambiarCantidadListener.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPlatillos.size();
    }

    public class ViewHolderCarrito extends RecyclerView.ViewHolder {
        TextView titulo, costo, total, cantidad;
        Button agregar, quitar;
        ImageView icono;

        public ViewHolderCarrito(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.nomCarrito);
            costo = itemView.findViewById(R.id.costoCarrito);
            icono = itemView.findViewById(R.id.iconCarrito);
            total = itemView.findViewById(R.id.totalCarrito);
            cantidad = itemView.findViewById(R.id.cantidadCarrito);
            agregar = itemView.findViewById(R.id.btnAgregar);
            quitar = itemView.findViewById(R.id.btnQuitar);
        }
    }
}
