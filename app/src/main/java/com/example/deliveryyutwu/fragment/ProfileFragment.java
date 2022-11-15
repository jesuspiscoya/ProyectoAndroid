package com.example.deliveryyutwu.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryyutwu.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileFragment extends Fragment {

    private TextInputEditText nombre, correo, direccion, telefono;
    private TextView txtNombre, txtCorreo, txtDireccion, txtTelefono;
    private TextInputLayout valNombre, valCorreo, valDireccion, valTelefono;
    Button actualizar, guardar;
    LinearLayout perfil, update;
    String id;
    private Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    /*private String host = "http://192.168.1.5/YutWuSac/";*/
    private String host = "http://yutwu.000webhostapp.com/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        id = getArguments().getString("idCliente");

        nombre = root.findViewById(R.id.txtNombre);
        correo = root.findViewById(R.id.txtCorreo);
        direccion = root.findViewById(R.id.txtDireccion);
        telefono = root.findViewById(R.id.txtTelefono);
        valNombre = root.findViewById(R.id.valNombre);
        valCorreo = root.findViewById(R.id.valCorreo);
        valDireccion = root.findViewById(R.id.valDireccion);
        valTelefono = root.findViewById(R.id.valTelefono);
        txtNombre = root.findViewById(R.id.nombre);
        txtCorreo = root.findViewById(R.id.correo);
        txtDireccion = root.findViewById(R.id.direccion);
        txtTelefono = root.findViewById(R.id.telefono);
        perfil = root.findViewById(R.id.lyPerfil);
        update = root.findViewById(R.id.lyActualizar);
        actualizar = root.findViewById(R.id.btnActualizar);
        guardar = root.findViewById(R.id.btnGuardar);
        mostrarDatos();
        actualizarDatos();
        onTextChanged();

        return root;
    }

    public void mostrarDatos() {
        StringRequest postRequest = new StringRequest(Request.Method.GET, host+"ClienteController/clientePerfil/" + id + "/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("cliente");
                            txtNombre.setText(jsonObject1.getString("nombreCliente"));
                            txtCorreo.setText(jsonObject1.getString("correo"));
                            txtDireccion.setText(jsonObject1.getString("direccion"));
                            txtTelefono.setText(jsonObject1.getString("numTelef"));
                            nombre.setText(jsonObject1.getString("nombreCliente"));
                            correo.setText(jsonObject1.getString("correo"));
                            direccion.setText(jsonObject1.getString("direccion"));
                            telefono.setText(jsonObject1.getString("numTelef"));
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

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(nombre.getWindowToken(), 0);

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfil.setVisibility(View.INVISIBLE);
                update.setVisibility(View.VISIBLE);
            }
        });
    }

    public void actualizarDatos() {
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    StringRequest postRequest = new StringRequest(Request.Method.POST, host+"ClienteController/clienteUpdate/" + id + "/",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    update.setVisibility(View.INVISIBLE);
                                    perfil.setVisibility(View.VISIBLE);
                                    mostrarDatos();
                                    showToast("Se ha actualizado correctamente: " + response, FancyToast.SUCCESS);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showToast("Error en el servidor: " + error.getMessage(), FancyToast.ERROR);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            String nombreUpd = nombre.getText().toString();
                            String correoUpd = correo.getText().toString();
                            String direccionUpd = direccion.getText().toString();
                            String telefonoUpd = telefono.getText().toString();
                            Map<String, String> params = new HashMap<>();
                            params.put("nombreCliente", nombreUpd);
                            params.put("direccion", direccionUpd);
                            params.put("numTelef", telefonoUpd);
                            params.put("correo", correoUpd);
                            return params;
                        }
                    };
                    Volley.newRequestQueue(getContext()).add(postRequest);
                } else
                    showToast("Complete los campos requeridos", FancyToast.ERROR);
            }
        });
    }

    public boolean validar() {
        boolean val = true;
        Matcher mather = pattern.matcher(correo.getText().toString());

        if (nombre.getText().toString().length() < 7) {
            valNombre.setError("Ingrese un nombre válido.");
            val = false;
        } else
            valNombre.setErrorEnabled(false);

        if (!mather.find()) {
            valCorreo.setError("Ingrese un correo electrónico válido.");
            val = false;
        } else
            valCorreo.setErrorEnabled(false);

        if (direccion.getText().toString().length() < 7) {
            valDireccion.setError("Ingrese una dirección válida.");
            val = false;
        } else
            valDireccion.setErrorEnabled(false);

        if (telefono.getText().toString().length() < 9) {
            valTelefono.setError("Ingrese un teléfono válido.");
            val = false;
        } else
            valTelefono.setErrorEnabled(false);

        return val;
    }

    public void onTextChanged() {
        nombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valNombre.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valCorreo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        direccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valDireccion.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        telefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valTelefono.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void showToast(String msg, int type) {
        Toast toast = FancyToast.makeText(getContext(), msg, FancyToast.LENGTH_SHORT, type, false);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 170);
        toast.show();
    }
}