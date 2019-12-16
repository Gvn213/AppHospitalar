package com.example.patriciasantos.apphospitalar;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.patriciasantos.apphospitalar.Cadastro.CadastroActivity;
import com.example.patriciasantos.apphospitalar.Cadastro.LoginActivity;
import com.example.patriciasantos.apphospitalar.ContagemPasta.abacontagem;
import com.example.patriciasantos.apphospitalar.ContagemPasta.consultacontagem;
import com.example.patriciasantos.apphospitalar.TransferenciasPasta.abatrans;

public class MainActivity extends AppCompatActivity {
    Button button2;
    Button btnTeste;
    Button btnSair;
    Button btnGrafic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().getItem(0).setChecked(true);
        Fragment ft = new telainicial();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                ft).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new telainicial();
                            break;
                        case R.id.nav_cad:
                            selectedFragment = new abacontagem();
                            break;
                        case R.id.nav_cons:
                            selectedFragment = new consultacontagem();
                            break;
                        case R.id.nav_trans:
                            selectedFragment = new abatrans();
                            break;
                        case R.id.nav_rel:
                            selectedFragment = new Relatorio();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
    }