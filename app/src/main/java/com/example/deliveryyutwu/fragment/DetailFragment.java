package com.example.deliveryyutwu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.deliveryyutwu.R;
import com.example.deliveryyutwu.helper.ManagementCart;
import com.example.deliveryyutwu.model.Platillos;
import com.shashank.sony.fancytoastlib.FancyToast;

public class DetailFragment extends Fragment {
    private Button añadirCart, agregar, quitar;
    private TextView titulo, precio, descripcion, cantidad;
    private ImageView icono;
    private Platillos objPlatillo;
    int cant = 1;
    private ManagementCart managementCart;
    private View root;
    /*private String host = "http://192.168.1.5/YutWuSac/";*/
    private String host = "http://yutwu.000webhostapp.com/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail, container, false);

        managementCart = new ManagementCart(getContext());
        añadirCart = root.findViewById(R.id.btnAñadirCart);
        titulo = root.findViewById(R.id.nomPlato);
        precio = root.findViewById(R.id.precioPlato);
        cantidad = root.findViewById(R.id.cantidadPlatillo);
        agregar = root.findViewById(R.id.btnAgregarPlatillo);
        quitar = root.findViewById(R.id.btnQuitarPlatillo);
        icono = root.findViewById(R.id.iconPlatillo);

        getBundle();
        return root;
    }

    private void getBundle() {
        objPlatillo = (Platillos) getArguments().getSerializable("object");
        titulo.setText(objPlatillo.getNombreProducto());

        Glide.with(root.getContext())
                .load(host+"start/img"+objPlatillo.getImagen())
                .into(icono);

        precio.setText("S/ " + objPlatillo.getPrecio());
        cantidad.setText(String.valueOf(cant));

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cant = cant + 1;
                cantidad.setText(String.valueOf(cant));
            }
        });

        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cant > 1) {
                    cant = cant - 1;
                }
                cantidad.setText(String.valueOf(cant));
            }
        });

        añadirCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objPlatillo.setNumeroCart(cant);
                managementCart.añadirCarrito(objPlatillo);
                showToast("Se agregó al carrito", FancyToast.SUCCESS);
            }
        });
    }

    public void showToast(String msg, int type) {
        Toast toast = FancyToast.makeText(getContext(), msg, FancyToast.LENGTH_SHORT, type, false);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 145);
        toast.show();
    }
}