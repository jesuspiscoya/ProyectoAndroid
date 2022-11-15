package com.example.deliveryyutwu.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryyutwu.R;
import com.example.deliveryyutwu.adapter.PopularAdapter;
import com.example.deliveryyutwu.model.Platillos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView.Adapter adaptadorCategoria, adaptadorPopular;
    private RecyclerView recyclerCategoria, recyclerPopular;
    /*private String host = "http://192.168.1.5/YutWuSac/";*/
    private String host = "http://yutwu.000webhostapp.com/";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewPopular(root);
        return root;
    }


    private void recyclerViewPopular(View root) {
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Cargando...");
        progress.show();

        StringRequest postRequest = new StringRequest(Request.Method.GET, host+"CategoriaController/categoriaSelect/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int ids[] = {R.id.rvTallarines, R.id.rvClasicos, R.id.rvSopas, R.id.rvBebidas};
                        try {
                            JSONObject resultado = new JSONObject(response);
                            JSONArray categorias = resultado.getJSONArray("categorias");
                            for (int i = 0; i < categorias.length(); i++) {
                                JSONObject jsonObject = categorias.getJSONObject(i);
                                llenarPlatillosCategoria(jsonObject.getInt("idCategoria"), root, ids[i]);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progress.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getContext()).add(postRequest);
    }

    private void llenarPlatillosCategoria(Integer idCategoria, View root, int indice) {
        StringRequest postRequest = new StringRequest(Request.Method.GET, host+"ProductoController/productoCategoria/" + idCategoria + "/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerPopular = root.findViewById(indice);
                        recyclerPopular.setLayoutManager(linearLayoutManager);
                        ArrayList<Platillos> listaPlatillos = new ArrayList<>();
                        try {
                            JSONObject arregloPlatillos = new JSONObject(response);
                            JSONArray platillos = arregloPlatillos.getJSONArray("productos");
                            for (int i = 0; i < platillos.length(); i++) {
                                JSONObject jsonObject = platillos.getJSONObject(i);
                                Platillos nuevo = new Platillos();
                                nuevo.setIdProducto(jsonObject.getInt("idProducto"));
                                nuevo.setNombreProducto(jsonObject.getString("nombreProducto"));
                                nuevo.setPrecio(jsonObject.getDouble("precio"));
                                nuevo.setIdCategoria(jsonObject.getInt("idCategoria"));
                                nuevo.setImagen(jsonObject.getString("imagen"));
                                listaPlatillos.add(nuevo);
                            }
                            adaptadorPopular = new PopularAdapter(listaPlatillos, getContext());
                            recyclerPopular.setAdapter(adaptadorPopular);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getContext()).add(postRequest);
    }
}