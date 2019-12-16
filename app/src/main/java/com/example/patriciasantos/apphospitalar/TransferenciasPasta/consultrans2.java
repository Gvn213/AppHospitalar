package com.example.patriciasantos.apphospitalar.TransferenciasPasta;

import android.net.TrafficStats;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.patriciasantos.apphospitalar.ContAdapter;
import com.example.patriciasantos.apphospitalar.ContagemPasta.Contagem;
import com.example.patriciasantos.apphospitalar.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class consultrans2 extends AppCompatActivity {
    private static final String TAG = "Tab2Fragment";
    private List<Transferencia> list = new ArrayList<Transferencia>();
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<Transferencia> lista;
    consultatrans adapter;
    private FirebaseDatabase mDatabase;
    private ProgressBar progressBarrec;
    private ImageView txtAA;
    private TextView txtAAAA;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recicler);
        recyclerView = findViewById(R.id.recyclercont);
        progressBarrec = findViewById(R.id.progressBarrec);
        txtAA = findViewById(R.id.txtAA);
        txtAAAA = findViewById(R.id.txtAAAA);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lista = new ArrayList<>();
        progressBarrec.setVisibility(View.VISIBLE);
        mDatabase = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("transferencia");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarrec.setVisibility(View.VISIBLE);
                if (!dataSnapshot.exists()) {
                    progressBarrec.setVisibility(View.GONE);
                    txtAA.setVisibility(View.VISIBLE);
                    txtAAAA.setVisibility(View.VISIBLE);
                }
                else{
                    progressBarrec.setVisibility(View.GONE);
                    txtAA.setVisibility(View.GONE);
                    txtAAAA.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter = new consultatrans(this, lista);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void onStart() {
        lista.clear();
        super.onStart();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                lista.add(dataSnapshot.getValue(Transferencia.class));
                adapter.notifyDataSetChanged();
                progressBarrec.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                progressBarrec.setVisibility(View.VISIBLE);
                lista.remove(dataSnapshot.getValue(Transferencia.class));
                adapter.notifyDataSetChanged();
                progressBarrec.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void onStop() {
        lista.clear();
        super.onStop();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                lista.add(dataSnapshot.getValue(Transferencia.class));
                adapter.notifyDataSetChanged();
                progressBarrec.setVisibility(View.GONE);
                reference.removeEventListener(this);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                progressBarrec.setVisibility(View.VISIBLE);
                lista.remove(dataSnapshot.getValue(Transferencia.class));
                adapter.notifyDataSetChanged();
                progressBarrec.setVisibility(View.GONE);
                reference.removeEventListener(this);
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
