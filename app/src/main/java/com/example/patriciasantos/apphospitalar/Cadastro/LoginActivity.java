package com.example.patriciasantos.apphospitalar.Cadastro;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.MainActivity;
import com.example.patriciasantos.apphospitalar.R;
import com.example.patriciasantos.apphospitalar.ResetSenha;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.util.TypedValue.TYPE_NULL;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    EditText edtLogin;
    EditText edtSenha;
    Button btnEntrar;
    Button btnCadastrar;
    TextView txtEsqueci;
    private FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton mGoogleLoginBtn;
    ProgressBar progressBar, progressBar3;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("490876384584-r3ub92vch1bvq81ehetprev9h2p799ue.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar3.setVisibility(View.INVISIBLE);

        txtEsqueci = (TextView) findViewById(R.id.txtEsqueci);

        mGoogleLoginBtn = findViewById(R.id.btnGoogle);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(tela);
            }
        });

        txtEsqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(LoginActivity.this, ResetSenha.class);
                startActivity(tela);
            }
        });

        mGoogleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                progressBar.setVisibility(View.VISIBLE);
                mGoogleLoginBtn.setVisibility(View.GONE);
                edtLogin.setInputType(InputType.TYPE_NULL);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtLogin.getText().toString().trim();
                String password = edtSenha.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Informe seu Login e Senha!", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar3.setVisibility(View.VISIBLE);
                    btnEntrar.setVisibility(View.INVISIBLE);
                    edtLogin.setInputType(InputType.TYPE_NULL);
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Erro: Login ou senha incorretos!",
                                                Toast.LENGTH_SHORT).show();
                                        progressBar3.setVisibility(View.GONE);
                                        btnEntrar.setVisibility(View.VISIBLE);
                                        edtLogin.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                                    } else {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        Toast.makeText(LoginActivity.this, "Bem-vindo!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        progressBar3.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Erro ao logar.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                mGoogleLoginBtn.setVisibility(View.VISIBLE);
                edtLogin.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Bem-vindo(a), "+user.getDisplayName()+"!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            Intent tela = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(tela);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Falha no Login! Verifique sua conexão com a internet.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            mGoogleLoginBtn.setVisibility(View.VISIBLE);
                            edtLogin.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                        }
                    }
                });
    }
}