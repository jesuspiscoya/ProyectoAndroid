package com.example.deliveryyutwu.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

public class LoginFragment extends Fragment {
    private TextInputEditText correo, contraseña;
    private TextInputLayout valCorreo, valPassword;
    private Button login, crear;
    private TextView recuperar;
    private FirebaseAuth mAuth;
    private Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    /*private String host = "http://192.168.1.5/YutWuSac/";*/
    private String host = "http://yutwu.000webhostapp.com/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        correo = root.findViewById(R.id.txtCorreo);
        contraseña = root.findViewById(R.id.txtContraseña);
        valCorreo = root.findViewById(R.id.valCorreo);
        valPassword = root.findViewById(R.id.valPassword);
        login = root.findViewById(R.id.btnLogin);
        crear = root.findViewById(R.id.btnCrear);
        recuperar = root.findViewById(R.id.txtRecuperar);
        ingresar();
        onTextChanged();
        recuperar();

        return root;
    }

    private void buscarUsuario(){
        String correo = this.correo.getText().toString();
        StringRequest postRequest = new StringRequest(Request.Method.POST, host+"ClienteController/clienteLogin/",
                new Response.Listener<String>(){
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("correo", correo);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(postRequest);
    }

    public void ingresar() {
        ProgressDialog progress = new ProgressDialog(getContext());
        progress.setMessage("Cargando...");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    progress.show();
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                                buscarUsuario();
                            else {
                                valCorreo.setError("Ingrese un correo electrónico válido.");
                                valPassword.setError("Ingrese una contraseña válida.");
                                showToast("Correo y/o contraseña incorrecta", FancyToast.ERROR);
                            }
                            progress.dismiss();
                        }
                    });
                } else
                    showToast("Complete los campos requeridos", FancyToast.ERROR);
            }
        });

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_login, new RegisterFragment(), "login");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    public boolean validar() {
        boolean val = true;
        Matcher mather = pattern.matcher(correo.getText().toString());

        if (!mather.find()) {
            valCorreo.setError("Ingrese un correo electrónico válido.");
            val = false;
        } else
            valCorreo.setErrorEnabled(false);

        if (contraseña.getText().toString().length() < 6) {
            valPassword.setError("Ingrese una contraseña válida.");
            val = false;
        } else
            valPassword.setErrorEnabled(false);

        return val;
    }

    public void onTextChanged() {
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
    }

    public void recuperar() {
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_login, new RecuperarFragment(), "login");
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    public void showToast(String msg, int type) {
        Toast toast = FancyToast.makeText(getContext(), msg, FancyToast.LENGTH_SHORT, type, false);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.show();
    }
}