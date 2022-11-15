package com.example.deliveryyutwu.fragment;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryyutwu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecuperarFragment extends Fragment {
    private TextInputEditText correo;
    private TextInputLayout valCorreo;
    private TextView confirmacion;
    private Button recuperar;
    private FirebaseAuth mAuth;
    private Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recuperar, container, false);

        correo = root.findViewById(R.id.txtCorreo);
        valCorreo = root.findViewById(R.id.valCorreo);
        confirmacion = root.findViewById(R.id.txtConfirmacion);
        recuperar = root.findViewById(R.id.btnRecuperar);
        recuperar();
        onTextChanged();

        return root;
    }

    public boolean validar() {
        boolean val = true;
        Matcher mather = pattern.matcher(correo.getText().toString());

        if (!mather.find()) {
            valCorreo.setError("Ingrese un correo electrónico válido.");
            val = false;
        } else
            valCorreo.setErrorEnabled(false);
        return val;
    }

    public void onTextChanged() {
        correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Matcher mather = pattern.matcher(s.toString());
                if (mather.find())
                    valCorreo.setErrorEnabled(false);
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
                if (validar()) {
                    ProgressDialog progress = new ProgressDialog(getContext());
                    progress.setMessage("Cargando...");
                    progress.show();

                    mAuth = FirebaseAuth.getInstance();
                    mAuth.sendPasswordResetEmail(correo.getText().toString()).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                showToast("Se envió correctamente", FancyToast.SUCCESS);
                                confirmacion.setVisibility(View.VISIBLE);
                            } else {
                                valCorreo.setError("Ingrese un correo electrónico válido.");
                                showToast("El correo no está registrado", FancyToast.ERROR);
                            }
                            progress.dismiss();
                        }
                    });
                } else
                    showToast("Complete el campo requerido", FancyToast.ERROR);
            }
        });
    }

    public void showToast(String msg, int type) {
        Toast toast = FancyToast.makeText(getContext(), msg, FancyToast.LENGTH_SHORT, type, false);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 100);
        toast.show();
    }
}