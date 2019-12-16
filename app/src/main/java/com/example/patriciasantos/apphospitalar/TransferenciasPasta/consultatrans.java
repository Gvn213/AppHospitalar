package com.example.patriciasantos.apphospitalar.TransferenciasPasta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.model.Model;
import com.example.patriciasantos.apphospitalar.ContagemPasta.Contagem;
import com.example.patriciasantos.apphospitalar.ContagemPasta.abacontagem;
import com.example.patriciasantos.apphospitalar.ContagemPasta.consultacontagem;
import com.example.patriciasantos.apphospitalar.R;
import com.example.patriciasantos.apphospitalar.detalhescont;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class consultatrans extends RecyclerView.Adapter<consultatrans.MyViewHolder> {

    Context context;
    ArrayList<Transferencia> transferencias;

    public consultatrans(Context c, ArrayList<Transferencia> trans){
        context = c;
        transferencias = trans;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.consultatrans,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tPaciente.setText(String.valueOf(transferencias.get(i).getPaciente()));
        myViewHolder.tData.setText(String.valueOf(transferencias.get(i).getData()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(String.valueOf(transferencias.get(i).getData()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String day = (String) DateFormat.format("dd", date);
        String month = (String) DateFormat.format("MMM", date);
        String year = (String) DateFormat.format("yyyy", date);
        myViewHolder.tDia.setText(day);
        myViewHolder.tMes.setText(month);
        myViewHolder.tAno.setText(year);
        myViewHolder.tHospital.setText(String.valueOf(transferencias.get(i).getHospital()));
    }

    @Override
    public int getItemCount() {
        return transferencias.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tPaciente, tData, tDia, tMes, tAno, tHospital;
        ImageView iPaciente, iHospital, iEdit, iDelete;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tPaciente = (TextView) itemView.findViewById(R.id.txtPaciente);
            tHospital = (TextView) itemView.findViewById(R.id.txtHospital);
            tData = (TextView) itemView.findViewById(R.id.txtData);
            tDia = (TextView) itemView.findViewById(R.id.txtDatte);
            tMes = (TextView) itemView.findViewById(R.id.txtMese);
            tAno = (TextView) itemView.findViewById(R.id.txtAnno);

            iPaciente = (ImageView) itemView.findViewById(R.id.imgPaciente);
            iHospital = (ImageView) itemView.findViewById(R.id.imgHospital);
            iEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            iDelete = (ImageView) itemView.findViewById(R.id.imgDelete);

            iDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                            .setTitle("Remover Transferência")
                            .setMessage("Tem certeza que deseja remover esta Transferência?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = null;
                                    try {
                                        date = format.parse(String.valueOf(tData.getText().toString()));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    final String day1 = (String) DateFormat.format("dd", date);
                                    final String month1 = (String) DateFormat.format("MM", date);
                                    final String year1 = (String) DateFormat.format("yyyy", date);
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef = database.getReference("transferencia").child(day1 + "-" + month1 + "-" + year1);
                                    DatabaseReference myRef1 = database.getReference("transferencia").child(day1 + "-" + month1 + "-" + year1 + "-"+tPaciente.getText().toString());
                                    myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(final DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        dataSnapshot.getRef().removeValue();
                                                    } else{
                                                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(final DataSnapshot dataSnapshot1) {
                                                                if (dataSnapshot1.exists()) {
                                                                    dataSnapshot1.getRef().removeValue();
                                                                }
                                                            }
                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            }
                                                        });
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });
                                    transferencias.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    Toast.makeText(context, "A transferência foi removida!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    myQuittingDialogBox.show();
                }
            });
            iEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, detalhesconsu.class);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = format.parse(String.valueOf(tData.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String day = (String) DateFormat.format("dd", date);
                    String month = (String) DateFormat.format("MM", date);
                    String year = (String) DateFormat.format("yyyy", date);
                    intent.putExtra("data", day + "-" + month + "-" + year);
                    intent.putExtra("paciente", tPaciente.getText().toString());
                    intent.putExtra("hospital", tHospital.getText().toString());
                    context.startActivity(intent);
                }
            });

        }
    }
}
