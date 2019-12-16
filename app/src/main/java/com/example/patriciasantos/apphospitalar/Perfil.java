package com.example.patriciasantos.apphospitalar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.Cadastro.LoginActivity;
import com.example.patriciasantos.apphospitalar.MainActivity;
import com.example.patriciasantos.apphospitalar.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import static android.support.test.InstrumentationRegistry.getContext;

public class Perfil extends AppCompatActivity {
    TextView txtMail;
    TextView txtNomea;
    Button btSair;
    ImageView iSave;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth auth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        txtMail = findViewById(R.id.txtmail);
        txtNomea = findViewById(R.id.txtNome);
        btSair = (Button) findViewById(R.id.btnSair);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            txtMail.setText(email);
            txtNomea.setText(name);
        } else {
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void btSair(View view){
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(Perfil.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Perfil.this, "Deslogado com sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Perfil.this, LoginActivity.class));
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}