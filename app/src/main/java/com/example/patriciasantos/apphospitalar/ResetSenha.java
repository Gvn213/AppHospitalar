package com.example.patriciasantos.apphospitalar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetSenha extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnRedefinir;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetarsenha);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnRedefinir = (Button) findViewById(R.id.btnRedefinir);

        mAuth = FirebaseAuth.getInstance();

        btnRedefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Informe o seu e-mail!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetSenha.this, "Um e-mail foi enviado para a redefinição de senha!", Toast.LENGTH_SHORT).show();
                                        btnRedefinir.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(ResetSenha.this, "O e-mail informado não possui uma conta registrada no sistema!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
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
