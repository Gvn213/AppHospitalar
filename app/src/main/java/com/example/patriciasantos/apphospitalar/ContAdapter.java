package com.example.patriciasantos.apphospitalar;

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

public class ContAdapter extends RecyclerView.Adapter<ContAdapter.MyViewHolder> {

    Context context;
    ArrayList<Contagem> contagems;
    ImageView iDelete;
    TextView tIDD, tData;

    public ContAdapter(Context c, ArrayList<Contagem> cont){
        context = c;
        contagems = cont;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.listaview,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tIDD.setText(String.valueOf(contagems.get(i).getId()));
        myViewHolder.tData.setText(String.valueOf(contagems.get(i).getData()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(String.valueOf(contagems.get(i).getData()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String day = (String) DateFormat.format("dd", date);
        String month = (String) DateFormat.format("MMM", date);
        String year = (String) DateFormat.format("yyyy", date);
        myViewHolder.tDia.setText(day);
        myViewHolder.tMes.setText(month);
        myViewHolder.tAno.setText(year);
        myViewHolder.tVermelho.setText(String.valueOf(contagems.get(i).getVermelha()));
        myViewHolder.tAmarelo.setText(String.valueOf(contagems.get(i).getAmarela()));
        myViewHolder.tVerde.setText(String.valueOf(contagems.get(i).getVerde()));
        myViewHolder.tAzul.setText(String.valueOf(contagems.get(i).getAzul()));
        myViewHolder.tSemClass.setText(String.valueOf(contagems.get(i).getSemclass()));
        myViewHolder.tCirurgico.setText(String.valueOf(contagems.get(i).getCirurgico()));
        myViewHolder.tOrtopedia.setText(String.valueOf(contagems.get(i).getOrtopedia()));
        myViewHolder.tCanceladas.setText(String.valueOf(contagems.get(i).getCanceladas()));
        myViewHolder.tDesaparecidas.setText(String.valueOf(contagems.get(i).getDesaparecidas()));
        myViewHolder.tDesistencia.setText(String.valueOf(contagems.get(i).getDesistencia()));
    }

    @Override
    public int getItemCount() {
        return contagems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tIDD, tData, tVermelho, tAmarelo, tVerde, tAzul, tSemClass, tCirurgico, tOrtopedia, tCanceladas, tDesaparecidas, tDesistencia, tDia, tMes, tAno;
        ImageView iVermelho, iAmarelo, iVerde, iAzul, iSemClass, iCirurgico, iOrtopedia, iCanceladas, iDesaparecidas, iDesistencia, iEdit, iDelete;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tIDD = (TextView) itemView.findViewById(R.id.txtIDD);
            tData = (TextView) itemView.findViewById(R.id.txtData);
            tDia = (TextView) itemView.findViewById(R.id.txtDatte);
            tMes = (TextView) itemView.findViewById(R.id.txtMese);
            tAno = (TextView) itemView.findViewById(R.id.txtAnno);
            tVermelho = (TextView) itemView.findViewById(R.id.txtVermelha);
            tAmarelo = (TextView) itemView.findViewById(R.id.txtAmarela);
            tVerde = (TextView) itemView.findViewById(R.id.txtVerde);
            tAzul = (TextView) itemView.findViewById(R.id.txtAzul);
            tSemClass = (TextView) itemView.findViewById(R.id.txtSemclass);
            tCirurgico = (TextView) itemView.findViewById(R.id.txtCirurgico);
            tOrtopedia = (TextView) itemView.findViewById(R.id.txtOrtopedia);
            tCanceladas = (TextView) itemView.findViewById(R.id.txtCanceladas);
            tDesaparecidas = (TextView) itemView.findViewById(R.id.txtDesaparecidas);
            tDesistencia = (TextView) itemView.findViewById(R.id.txtDesistencia);

            iVermelho = (ImageView) itemView.findViewById(R.id.imgVer);
            iAmarelo = (ImageView) itemView.findViewById(R.id.imgAmar);
            iVerde = (ImageView) itemView.findViewById(R.id.imgVer);
            iAzul = (ImageView) itemView.findViewById(R.id.imgAzul);
            iSemClass = (ImageView) itemView.findViewById(R.id.imgSemcl);
            iCirurgico = (ImageView) itemView.findViewById(R.id.imgCirug);
            iOrtopedia = (ImageView) itemView.findViewById(R.id.imgOrto);
            iCanceladas = (ImageView) itemView.findViewById(R.id.imgCanc);
            iDesaparecidas = (ImageView) itemView.findViewById(R.id.imgDsp);
            iDesistencia = (ImageView) itemView.findViewById(R.id.imgDesist);
            iEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            iDelete = (ImageView) itemView.findViewById(R.id.imgDelete);

            iDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                            .setTitle("Apagar Registro")
                            .setMessage("Tem certeza que deseja apagar este registro?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
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
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("contagem");
                                    myRef.child(tIDD.getText().toString()).setValue(null);
                                    DatabaseReference myRef1 = database.getReference("codigosbarra");
                                    myRef1.child(day + "-" + month + "-" + year).removeValue();
                                    contagems.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    Toast.makeText(context, "Registro apagado com sucesso!", Toast.LENGTH_SHORT).show();
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
                    Intent intent = new Intent(context, detalhescont.class);
                    intent.putExtra("key", tIDD.getText().toString());
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
                    intent.putExtra("vermelho", tVermelho.getText().toString());
                    intent.putExtra("amarelo", tAmarelo.getText().toString());
                    intent.putExtra("verde", tVerde.getText().toString());
                    intent.putExtra("azul", tAzul.getText().toString());
                    intent.putExtra("semclass", tSemClass.getText().toString());
                    intent.putExtra("cirurgico", tCirurgico.getText().toString());
                    intent.putExtra("ortopedia", tOrtopedia.getText().toString());
                    intent.putExtra("canceladas", tCanceladas.getText().toString());
                    intent.putExtra("desaparecidas", tDesaparecidas.getText().toString());
                    intent.putExtra("desistencia", tDesistencia.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
