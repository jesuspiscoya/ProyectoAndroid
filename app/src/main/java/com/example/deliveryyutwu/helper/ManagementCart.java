package com.example.deliveryyutwu.helper;

import android.content.Context;
import android.widget.Toast;

import com.example.deliveryyutwu.interfaces.CambiarCantidadListener;
import com.example.deliveryyutwu.model.Platillos;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private CartBD cartBD;

    public ManagementCart(Context context) {
        this.context = context;
        this.cartBD = new CartBD(context);
    }

    public void añadirCarrito(Platillos item) {
        ArrayList<Platillos> listaPlatillos = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listaPlatillos.size(); i++) {
            if (listaPlatillos.get(i).getNombreProducto().equals(item.getNombreProducto())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            listaPlatillos.get(n).setNumeroCart(item.getNumeroCart());
        } else {
            listaPlatillos.add(item);
        }
        cartBD.putListObject("CartList", listaPlatillos);
    }

    /*Obtienes todos los detalles*/
    public ArrayList<Platillos> getListCart() {
        return cartBD.getListObject("CartList");
    }

    public void agregarCantidad(ArrayList<Platillos> listaPlatillos, int position, CambiarCantidadListener cambiarCantidadListener) {
        listaPlatillos.get(position).setNumeroCart(listaPlatillos.get(position).getNumeroCart() + 1);
        cartBD.putListObject("CartList", listaPlatillos);
        cambiarCantidadListener.changed();
    }

    public void vaciarCarrito(ArrayList<Platillos> listaPlatillos){
        listaPlatillos.clear();
        cartBD.putListObject("CartList", listaPlatillos);
    }

    public void quitarCantidad(ArrayList<Platillos> listaPlatillos, int position, CambiarCantidadListener cambiarCantidadListener) {
        if (listaPlatillos.get(position).getNumeroCart() == 1) {
            listaPlatillos.remove(position);
        } else {
            listaPlatillos.get(position).setNumeroCart(listaPlatillos.get(position).getNumeroCart() - 1);
        }
        cartBD.putListObject("CartList", listaPlatillos);
        cambiarCantidadListener.changed();
    }

    public Double montoTotal() {
        ArrayList<Platillos> listaPlatillos = getListCart();
        double monto = 0;
        for (int i = 0; i < listaPlatillos.size(); i++) {
            monto = monto + (listaPlatillos.get(i).getPrecio() * listaPlatillos.get(i).getNumeroCart());
        }
        return monto;
    }
}
