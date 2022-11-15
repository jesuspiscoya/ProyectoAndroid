package com.example.deliveryyutwu.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliveryyutwu.R;
import com.example.deliveryyutwu.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {
    private TextInputEditText nombre, correo, contraseña, contraseña2, direccion, telefono;
    private TextInputLayout valNombre, valCorreo, valPassword, valPassword2, valDireccion, valTelefono;
    private Button registrar;
    private FirebaseAuth mAuth;
    private Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    /*private String host = "http://192.168.1.5/YutWuSac/";*/
    private String host = "http://yutwu.000webhostapp.com/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        nombre = root.findViewById(R.id.txtNombre);
        correo = root.findViewById(R.id.txtCorreo);
        contraseña = root.findViewById(R.id.txtPass);
        contraseña2 = root.findViewById(R.id.txtPass2);
        direccion = root.findViewById(R.id.txtDireccion);
        telefono = root.findViewById(R.id.txtTelefono);
        valNombre = root.findViewById(R.id.valNombre);
        valCorreo = root.findViewById(R.id.valCorreo);
        valPassword = root.findViewById(R.id.valPassword);
        valPassword2 = root.findViewById(R.id.valPassword2);
        valDireccion = root.findViewById(R.id.valDireccion);
        valTelefono = root.findViewById(R.id.valTelefono);
        registrar = root.findViewById(R.id.btnRegistrar);
        registro();
        onTextChanged();

        return root;
    }

    private void registrarse() {
        String nombre = this.nombre.getText().toString();
        String correo = this.correo.getText().toString();
        String contraseña = this.contraseña.getText().toString();
        String direccion = this.direccion.getText().toString();
        String telefono = this.telefono.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, host+"ClienteController/clienteInsert/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        buscarUsuario();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error en el servidor: " + error.getMessage(), FancyToast.ERROR);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombreCliente", nombre);
                params.put("direccion", direccion);
                params.put("numTelef", telefono);
                params.put("correo", correo);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(postRequest);
    }

    private void buscarUsuario() {
        String correo = this.correo.getText().toString();
        String contraseña = this.contraseña.getText().toString();
        StringRequest postRequest = new StringRequest(Request.Method.GET, host+"ClienteController/obtenerUltimoCliente/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("cliente");
                            int codigoCliente = jsonObject1.getInt("idCliente");
                            Bundle bundle = new Bundle();
                            bundle.putString("idCliente", String.valueOf(codigoCliente));
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            getActivity().finish();
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

    public void registro() {
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Cargando...");

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    progress.show();
                    // Insertar a Firebase
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // Insertar a mysql
                            registrarse();
                            progress.dismiss();
                        }
                    });
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

        if (contraseña.getText().toString().length() < 6 || !contraseña2.getText().toString().equals(contraseña.getText().toString())) {
            valPassword.setError("Ingrese una contraseña de al menos 6 caracteres.");

            if (contraseña.getText().toString().length() >= 6)
                valPassword.setError("La contraseña debe coincidir.");

            valPassword2.setError("La contraseña debe coincidir.");
            val = false;
        } else {
            valPassword.setErrorEnabled(false);
            valPassword2.setErrorEnabled(false);
        }

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

        contraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                valPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contraseña2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (contraseña.getText().toString().equals(s.toString()))
                    valPassword2.setErrorEnabled(false);
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
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.show();
    }
}