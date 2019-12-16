package com.example.patriciasantos.apphospitalar.Cadastro;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.MainActivity;
import com.example.patriciasantos.apphospitalar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {
    EditText edtEmail;
    EditText edtSenha;
    EditText edtConfsenha;
    Button btnSalvar;
    ImageView iSave;
    ProgressBar progressBar;
    TextView txtSalvar;
    private FirebaseAuth auth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        auth = FirebaseAuth.getInstance();


        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtConfsenha = (EditText) findViewById(R.id.edtConfsenha);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        iSave = (ImageView) findViewById(R.id.imgSave);
        txtSalvar = (TextView) findViewById(R.id.textView7);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);

        iSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtSenha.getText().toString().trim();
                String password2 = edtConfsenha.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Digite o seu e-mail!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.equals(password,password2)) {
                    Toast.makeText(getApplicationContext(), "As senhas informadas não coincidem!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Informe sua senha!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Senha muito curta! Informe, no mínimo, seis caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(), "Informe um e-mail válido!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                iSave.setVisibility(View.INVISIBLE);
                txtSalvar.setVisibility(View.INVISIBLE);
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(CadastroActivity.this, "Este e-mail já possui uma conta cadastrada",
                                            Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    iSave.setVisibility(View.VISIBLE);
                                    txtSalvar.setVisibility(View.VISIBLE);
                                } else {
                                    startActivity(new Intent(CadastroActivity.this, MainActivity.class));
                                    Toast.makeText(CadastroActivity.this, "Cadastro realizado, seja bem-vindo(a)!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
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