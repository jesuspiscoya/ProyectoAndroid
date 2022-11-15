package com.example.deliveryyutwu.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.deliveryyutwu.R;
import com.example.deliveryyutwu.fragment.CarritoFragment;
import com.example.deliveryyutwu.fragment.HomeFragment;
import com.example.deliveryyutwu.fragment.ProfileFragment;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNav;
    ArrayList<Integer> arrayId;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("idCliente");

        args = new Bundle();
        args.putString("idCliente", id);

        arrayId = new ArrayList<>();
        bottomNavigation();
    }

    private void bottomNavigation() {
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNav.add(new MeowBottomNavigation.Model(2, R.drawable.ic_profile));
        bottomNav.add(new MeowBottomNavigation.Model(3, R.drawable.ic_cartshop));

        arrayId.add(1);
        replace(new HomeFragment());
        bottomNav.show(1, true);

        bottomNav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        arrayId.add(1);
                        replace(new HomeFragment());
                        break;
                    case 2:
                        arrayId.add(2);
                        ProfileFragment perfil = new ProfileFragment();
                        perfil.setArguments(args);
                        replace(perfil);
                        break;
                    case 3:
                        arrayId.add(3);
                        CarritoFragment carrito = new CarritoFragment();
                        carrito.setArguments(args);
                        replace(carrito);
                        break;
                }
                return null;
            }
        });

        bottomNav.setOnReselectListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        replace(new HomeFragment());
                        break;
                    case 2:
                        ProfileFragment perfil = new ProfileFragment();
                        perfil.setArguments(args);
                        replace(perfil);
                        break;
                    case 3:
                        CarritoFragment carrito = new CarritoFragment();
                        carrito.setArguments(args);
                        replace(carrito);
                        break;
                }
                return null;
            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_main, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (arrayId.get(arrayId.size() - 1) != 1) {
                arrayId.remove(arrayId.size() - 1);
                bottomNav.show(arrayId.get(arrayId.size() - 1), true);
            } else if (arrayId.get(arrayId.size() - 1) == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Estas seguro de salir?").setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}