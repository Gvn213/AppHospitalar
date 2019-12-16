package com.example.patriciasantos.apphospitalar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.patriciasantos.apphospitalar.Cadastro.CadastroActivity;
import com.example.patriciasantos.apphospitalar.Cadastro.LoginActivity;
import com.example.patriciasantos.apphospitalar.ContagemPasta.consultacontagem;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class telainicial extends Fragment {
    GoogleSignInClient mGoogleSignInClient;
    TextView nameTV;
    ImageView photoIV;

    @android.support.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.telainicial, container, false);
        nameTV = view.findViewById(R.id.name);
        photoIV = view.findViewById(R.id.photo);
        setHasOptionsMenu(true);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            Uri personPhoto = acct.getPhotoUrl();
            nameTV.setText(personName);
            Glide.with(this).load(personPhoto).into(photoIV);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.perfil, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.imgPerfil:
                startActivity(new Intent(getContext(), Perfil.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
}
