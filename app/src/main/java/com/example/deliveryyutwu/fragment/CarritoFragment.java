package com.example.deliveryyutwu.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.deliveryyutwu.R;
import com.example.deliveryyutwu.activity.ConfirmacionActivity;
import com.example.deliveryyutwu.activity.MainActivity;
import com.example.deliveryyutwu.activity.TarjetaActivity;
import com.example.deliveryyutwu.adapter.CarritoAdapter;
import com.example.deliveryyutwu.helper.ManagementCart;
import com.example.deliveryyutwu.interfaces.CambiarCantidadListener;
import com.example.deliveryyutwu.model.Platillos;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoFragment extends Fragment {
    private TextInputEditText txtNombre, txtDireccion, txtReferencia, txtTelefono;
    private RecyclerView.Adapter adaptadorCarrito;
    private RecyclerView recyclerCarrito;
    private ManagementCart managementCart;
    private TextView monto, igv, delivery, total;
    private LinearLayout linearVacio;
    private Button agregar, pagar;
    private double impuesto;
    private ScrollView scrollView;
    private Bundle bundle = new Bundle();
    private boolean rec = true;
    /*private String host = "http://192.168.1.5/YutWuSac/";*/
    private String host = "http://yutwu.000webhostapp.com/";
    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_carrito, container, false);
        managementCart = new ManagementCart(getContext());
        monto = root.findViewById(R.id.monto);
        igv = root.findViewById(R.id.igv);
        delivery = root.findViewById(R.id.delivery);
        total = root.findViewById(R.id.total);
        linearVacio = root.findViewById(R.id.linearVacio);
        agregar = root.findViewById(R.id.btnVacio);
        pagar = root.findViewById(R.id.btnPagar);
        scrollView = root.findViewById(R.id.scrollView3);
        recyclerCarrito = root.findViewById(R.id.rvCarrito);
        bundle.putString("idCliente", getArguments().getString("idCliente"));

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_main, new HomeFragment());
                ft.commit();

                MeowBottomNavigation bottomNav = ((AppCompatActivity) getContext()).findViewById(R.id.bottomNav);
                bottomNav.show(1, true);
            }
        });

        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
        recyclerViewCarrito();
        calcularCart();
        return root;
    }

    private void recyclerViewCarrito() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerCarrito.setLayoutManager(linearLayoutManager);
        adaptadorCarrito = new CarritoAdapter(managementCart.getListCart(), getContext(), new CambiarCantidadListener() {
            @Override
            public void changed() {
                calcularCart();
                if (managementCart.getListCart().isEmpty()) {
                    scrollView.setVisibility(View.GONE);
                    linearVacio.setVisibility(View.VISIBLE);
                }
            }
        });
        recyclerCarrito.setAdapter(adaptadorCarrito);

        if (managementCart.getListCart().isEmpty()) {
            linearVacio.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            linearVacio.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calcularCart() {
        double costoDelivery = 8;

        impuesto = Math.round(managementCart.montoTotal() * 0.18 * 100.0) / 100.0;
        double montoItem = Math.round((managementCart.montoTotal() - impuesto) * 100.0) / 100.0;
        double montoTotal = Math.round((managementCart.montoTotal() + costoDelivery) * 100.0) / 100.0;

        monto.setText("S/ " + montoItem);
        igv.setText("S/ " + impuesto);
        delivery.setText("S/ " + costoDelivery);
        total.setText("S/ " + montoTotal);
    }

    public void mostrarDatos(TextInputEditText nombre, TextInputEditText telefono, TextInputEditText direccion, TextInputEditText referencia) {
        String id = getArguments().getString("idCliente");
        StringRequest postRequest = new StringRequest(Request.Method.GET, host+"ClienteController/clientePerfil/" + id + "/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("cliente");
                            nombre.setText(jsonObject1.getString("nombreCliente"));
                            telefono.setText(jsonObject1.getString("numTelef"));
                            if(rec){
                                direccion.setText("Cal. Loma Redonda Nro. 391");
                                referencia.setText("Prolongacion Benavides (Alt Nueva Municipalidad de Surco)");
                            }else{
                                direccion.setText("");
                                referencia.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error en el servidor: " + error.getMessage(), FancyToast.ERROR);
            }
        });
        Volley.newRequestQueue(getContext()).add(postRequest);
    }

    public void alertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.Theme_AlertDialog).create();
        View alertView = getLayoutInflater().inflate(R.layout.alertdialog, null);
        alertDialog.setView(alertView);
        alertDialog.show();

        Button recojo = alertDialog.findViewById(R.id.btnRecojo);
        Button delivery = alertDialog.findViewById(R.id.btnDelivery);

        TextInputEditText txtNombre = alertDialog.findViewById(R.id.txtNombre);
        TextInputEditText txtTelefono = alertDialog.findViewById(R.id.txtTelefono);
        TextInputEditText txtDireccion = alertDialog.findViewById(R.id.txtDireccion);
        TextInputEditText txtReferencia = alertDialog.findViewById(R.id.txtReferencia);

        rec = true;
        mostrarDatos(txtNombre, txtTelefono, txtDireccion, txtReferencia);

        Button aceptar = alertDialog.findViewById(R.id.btnAceptar);

        recojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recojo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3F51B5")));
                delivery.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9EA9E4")));
                txtDireccion.setFocusable(false);
                txtReferencia.setFocusable(false);
                rec = true;
                mostrarDatos(txtNombre, txtTelefono, txtDireccion, txtReferencia);
            }
        });

        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delivery.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3F51B5")));
                recojo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9EA9E4")));
                txtDireccion.setFocusableInTouchMode(true);
                txtDireccion.setFocusable(true);
                txtReferencia.setFocusable(true);
                txtReferencia.setFocusableInTouchMode(true);
                txtDireccion.requestFocus();
                rec = false;
                mostrarDatos(txtNombre, txtTelefono, txtDireccion, txtReferencia);
            }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String direccion = "Dirección: "+txtDireccion.getText().toString() + "   Referencia: "+txtReferencia.getText().toString();
                String servicio = rec ? "recojo":"delivery";
                if (validarEntrega(alertDialog)) {
                    registrarPedido(direccion, servicio);
                    Intent intent = new Intent(getActivity(), ConfirmacionActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }

    private void registrarDetallePedido(Platillos platillo){
        StringRequest postRequest = new StringRequest(Request.Method.POST, host+"DetalleOrdenController/detalleOrdenInsert/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Correcto", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
                showToast("Error en el servidor: " + error.getMessage(), FancyToast.ERROR);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idCliente", getArguments().getString("idCliente"));
                params.put("idProducto", String.valueOf(platillo.getIdProducto()));
                params.put("precioUnit", String.valueOf(platillo.getPrecio()));
                params.put("totalDetalle", String.valueOf(platillo.getPrecio() * platillo.getNumeroCart()));
                params.put("cantidad", String.valueOf(platillo.getNumeroCart()));

                return params;
            }
        };
        Volley.newRequestQueue(root.getContext()).add(postRequest);
    }

    private void registrarPedido(String direccion, String servicio){
        StringRequest postRequest = new StringRequest(Request.Method.POST, host+"OrdenController/ordenInsert/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Correcto", response);
                        List<Platillos> lista = managementCart.getListCart();
                        for (Platillos platillo : lista) {
                            registrarDetallePedido(platillo);
                        }
                        managementCart.vaciarCarrito(managementCart.getListCart());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error en el servidor: " + error.getMessage(), FancyToast.ERROR);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                /*Obtener la fecha actual*/
                SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");
                Calendar calendar = Calendar.getInstance();
                Date dateObj = calendar.getTime();
                String fechaActual = dtf.format(dateObj);

                Map<String, String> params = new HashMap<>();
                params.put("direccionOrden", direccion);
                params.put("fechaOrden", fechaActual);
                params.put("total", String.valueOf(managementCart.montoTotal()));
                params.put("idCliente", getArguments().getString("idCliente"));
                params.put("tipoServicio", servicio);

                return params;
            }
        };
        Volley.newRequestQueue(root.getContext()).add(postRequest);
    }

    public boolean validarEntrega(AlertDialog alertDialog) {
        TextInputEditText nombre = alertDialog.findViewById(R.id.txtNombre);
        TextInputEditText direccion = alertDialog.findViewById(R.id.txtDireccion);
        TextInputEditText referencia = alertDialog.findViewById(R.id.txtReferencia);
        TextInputEditText telefono = alertDialog.findViewById(R.id.txtTelefono);
        TextInputLayout valNombre = alertDialog.findViewById(R.id.valNombre);
        TextInputLayout valDireccion = alertDialog.findViewById(R.id.valDireccion);
        TextInputLayout valReferencia = alertDialog.findViewById(R.id.valReferencia);
        TextInputLayout valTelefono = alertDialog.findViewById(R.id.valTelefono);
        boolean val = true;

        if (nombre.getText().toString().length() < 7) {
            valNombre.setError("Ingrese un nombre válido.");
            val = false;
        } else
            valNombre.setErrorEnabled(false);

        if (direccion.getText().toString().length() < 7) {
            valDireccion.setError("Ingrese una dirección válida.");
            val = false;
        } else
            valDireccion.setErrorEnabled(false);

        if (referencia.getText().toString().length() < 5) {
            valReferencia.setError("Ingrese una referencia válida.");
            val = false;
        } else
            valReferencia.setErrorEnabled(false);

        if (telefono.getText().toString().length() < 9) {
            valTelefono.setError("Ingrese un teléfono válido.");
            val = false;
        } else
            valTelefono.setErrorEnabled(false);

        return val;
    }

    public void showToast(String msg, int type) {
        Toast toast = FancyToast.makeText(getContext(), msg, FancyToast.LENGTH_SHORT, type, false);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 170);
        toast.show();
    }
}