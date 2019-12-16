package com.example.patriciasantos.apphospitalar.codigos_barras;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.ContagemPasta.CodigoBarras;
import com.example.patriciasantos.apphospitalar.ContagemPasta.Contagem;
import com.example.patriciasantos.apphospitalar.MainActivity;
import com.example.patriciasantos.apphospitalar.R;
import com.example.patriciasantos.apphospitalar.detalhescont;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class codigoortopedia extends AppCompatActivity {

    private List<CodigoBarras> list = new ArrayList<>();
    private List<Contagem> lista = new ArrayList<>();
    private ListView listView;
    private DatabaseReference postRef;
    private String data;
    private ProgressBar progressBar45;
    private ImageView txtAA;
    private TextView txtAAA;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codigobarra);
        carregarListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void carregarListView() {
        listView = findViewById(R.id.listviewcod);
        progressBar45 = findViewById(R.id.progressBar45);
        txtAA = findViewById(R.id.txtAA);
        txtAAA = findViewById(R.id.txtAAA);
        final ArrayAdapter<CodigoBarras> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, list);
        adaptador.clear();
        listView.setAdapter(adaptador);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        progressBar45.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        data = getIntent().getStringExtra("datinha");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(String.valueOf(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String day = (String) DateFormat.format("dd", date);
        String month = (String) DateFormat.format("MM", date);
        String year = (String) DateFormat.format("yyyy", date);
        DatabaseReference reference = database.getReference("codigosbarra").child(day + "-" + month + "-" + year);
        reference.orderByChild("fichainfo").equalTo(data + "_OR").
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBar45.setVisibility(View.VISIBLE);
                if (!dataSnapshot.exists()) {
                    progressBar45.setVisibility(View.GONE);
                    txtAA.setVisibility(View.VISIBLE);
                    txtAAA.setVisibility(View.VISIBLE);
                }
                else{
                    progressBar45.setVisibility(View.GONE);
                    txtAA.setVisibility(View.GONE);
                    txtAAA.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year);
        myRef.orderByChild("fichainfo").equalTo(data + "_OR")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        list.add(dataSnapshot.getValue(CodigoBarras.class));
                        adaptador.notifyDataSetChanged();
                        progressBar45.setVisibility(View.GONE);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        progressBar45.setVisibility(View.VISIBLE);
                        list.remove(dataSnapshot.getValue(CodigoBarras.class));
                        adaptador.notifyDataSetChanged();
                        progressBar45.setVisibility(View.GONE);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getMenuInflater().inflate(R.menu.menuop, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.btapagar:
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                        .setTitle("Apagar Código de Barras")
                        .setMessage("Tem certeza que deseja apagar este Código de barras?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                final SparseBooleanArray checked = listView.getCheckedItemPositions();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra");
                                for (int i = 0; i < checked.size(); i++) {
                                    CodigoBarras cod = (CodigoBarras) listView.getItemAtPosition(checked.keyAt(i));
                                    String data = String.valueOf(cod.getData());
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = null;
                                    try {
                                        date = format.parse(String.valueOf(data));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    String day = (String) DateFormat.format("dd", date);
                                    String month = (String) DateFormat.format("MM", date);
                                    String year = (String) DateFormat.format("yyyy", date);
                                    myRef.child(day + "-" + month + "-" + year).child(cod.getCod()).removeValue();
                                    postRef = FirebaseDatabase.getInstance().getReference().child("contagem");
                                    postRef.orderByChild("data").equalTo(data)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(final DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                            Contagem contagem = appleSnapshot.getValue(Contagem.class);
                                                            DatabaseReference refortopedia = appleSnapshot.getRef().child("ortopedia");
                                                            refortopedia.setValue(contagem.getOrtopedia() - 1);
                                                            Intent intent = new Intent(codigoortopedia.this, detalhescont.class);
                                                            intent.putExtra("keya", contagem.getOrtopedia() - 1);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });
                                }
                                Toast.makeText(getApplicationContext(), "O código de barras foi apagado!", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                carregarListView();
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                myQuittingDialogBox.show();
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
}