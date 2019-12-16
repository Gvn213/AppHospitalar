package com.example.patriciasantos.apphospitalar.ContagemPasta;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.ContAdapter;
import com.example.patriciasantos.apphospitalar.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class consultacontagem extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<Contagem> lista;
    ContAdapter adapter;
    private ProgressBar progressBarrec;
    private ImageView txtAA;
    private TextView txtAAA;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recicler, container, false);

        recyclerView = view.findViewById(R.id.recyclercont);
        progressBarrec = view.findViewById(R.id.progressBarrec);
        txtAA = view.findViewById(R.id.txtAA);
        txtAAA = view.findViewById(R.id.txtAAA);
        lista = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("contagem");
        adapter = new ContAdapter(getActivity(), lista);
        recyclerView.setAdapter(adapter);
        progressBarrec.setVisibility(View.VISIBLE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressBarrec.setVisibility(View.VISIBLE);
                if (!dataSnapshot.exists()) {
                    progressBarrec.setVisibility(View.GONE);
                    txtAA.setVisibility(View.VISIBLE);
                    txtAAA.setVisibility(View.VISIBLE);
                }
                else{
                    progressBarrec.setVisibility(View.GONE);
                    txtAA.setVisibility(View.GONE);
                    txtAAA.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onStart(){
        lista.clear();
        super.onStart();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                lista.add(dataSnapshot.getValue(Contagem.class));
                adapter.notifyDataSetChanged();
                progressBarrec.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                progressBarrec.setVisibility(View.VISIBLE);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                lista.remove(dataSnapshot.getValue(Contagem.class));
                adapter.notifyDataSetChanged();
                progressBarrec.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onStop() {
       lista.clear();
        super.onStop();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                lista.add(dataSnapshot.getValue(Contagem.class));
                adapter.notifyDataSetChanged();
                progressBarrec.setVisibility(View.GONE);
                reference.removeEventListener(this);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                progressBarrec.setVisibility(View.VISIBLE);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                lista.remove(dataSnapshot.getValue(Contagem.class));
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
}