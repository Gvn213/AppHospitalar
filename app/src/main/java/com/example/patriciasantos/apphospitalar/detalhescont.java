package com.example.patriciasantos.apphospitalar;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.ContagemPasta.CodigoBarras;
import com.example.patriciasantos.apphospitalar.ContagemPasta.Contagem;
import com.example.patriciasantos.apphospitalar.ContagemPasta.abacontagem;
import com.example.patriciasantos.apphospitalar.ContagemPasta.consultacontagem;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigoamarela;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigoazul;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigocanceladas;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigocirurgico;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigodesaparecidas;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigodesistencia;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigoortopedia;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigosemclass;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigoverde;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigovermelha;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class detalhescont extends AppCompatActivity {
    EditText edtData, edtOrtopedia, edtCirurgico, edtVermelha, edtAmarela, edtAzul, edtVerde, edtSemclass, edtDesaparecidas, edtDesistencia, edtCanceladas;

    ImageView iSave, iVermelho, iAmarelo, iVerde, iAzul, iSemClass, iCirurgico, iOrtopedia, iCanceladas, iDesaparecidas, iDesistencia;

    String key, data, vermelho, amarelo, verde, azul, semclass, cirurgico, ortopedia, canceladas, desaparecidas, desistencia;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseReference postRef;
    private DatabaseReference postRef1, postRef2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhescont);

        key = getIntent().getStringExtra("key");
        data = getIntent().getStringExtra("data");
        vermelho = getIntent().getStringExtra("vermelho");
        amarelo = getIntent().getStringExtra("amarelo");
        verde = getIntent().getStringExtra("verde");
        azul = getIntent().getStringExtra("azul");
        semclass = getIntent().getStringExtra("semclass");
        cirurgico = getIntent().getStringExtra("cirurgico");
        ortopedia = getIntent().getStringExtra("ortopedia");
        canceladas = getIntent().getStringExtra("canceladas");
        desaparecidas = getIntent().getStringExtra("desaparecidas");
        desistencia = getIntent().getStringExtra("desistencia");

        edtData = (EditText) findViewById(R.id.edtData);
        edtData.setText(data);
        edtOrtopedia = (EditText) findViewById(R.id.edtOrtopedia);
        edtOrtopedia.setText(ortopedia);
        edtCirurgico = (EditText) findViewById(R.id.edtCirurgico);
        edtCirurgico.setText(cirurgico);
        edtVermelha = (EditText) findViewById(R.id.edtVermelha);
        edtVermelha.setText(vermelho);
        edtAmarela = (EditText) findViewById(R.id.edtAmarela);
        edtAmarela.setText(amarelo);
        edtAzul = (EditText) findViewById(R.id.edtAzul);
        edtAzul.setText(azul);
        edtVerde = (EditText) findViewById(R.id.edtVerde);
        edtVerde.setText(verde);
        edtSemclass = (EditText) findViewById(R.id.edtSemclass);
        edtSemclass.setText(semclass);
        edtDesaparecidas = (EditText) findViewById(R.id.edtDesaparecidas);
        edtDesaparecidas.setText(desaparecidas);
        edtDesistencia = (EditText) findViewById(R.id.edtDesistencia);
        edtDesistencia.setText(desistencia);
        edtCanceladas = (EditText) findViewById(R.id.edtCanceladas);
        edtCanceladas.setText(canceladas);

        iSave = (ImageView) findViewById(R.id.imgSave);
        iOrtopedia = (ImageView) findViewById(R.id.imgOrtopedia);
        iVermelho = (ImageView) findViewById(R.id.imageView13);
        iAzul = (ImageView) findViewById(R.id.imageView3);
        iVerde = (ImageView) findViewById(R.id.imageView18);
        iAmarelo = (ImageView) findViewById(R.id.imageView19);
        iCirurgico = (ImageView) findViewById(R.id.imageView7);
        iDesaparecidas = (ImageView) findViewById(R.id.imgDsp);
        iDesistencia = (ImageView) findViewById(R.id.imageView11);
        iCanceladas = (ImageView) findViewById(R.id.imageView21);
        iSemClass = (ImageView) findViewById(R.id.imageView20);

        iOrtopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigoortopedia.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });
        iVermelho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigovermelha.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });
        iAmarelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigoamarela.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });
        iVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigoverde.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });
        iAzul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigoazul.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });
        iCirurgico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigocirurgico.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });
        iDesaparecidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigodesaparecidas.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });
        iDesistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigodesistencia.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });
        iSemClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigosemclass.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });
        iCanceladas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detalhescont.this, codigocanceladas.class);
                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(edtData.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final String day = (String) DateFormat.format("dd", date);
                final String month = (String) DateFormat.format("MM", date);
                final String year = (String) DateFormat.format("yyyy", date);
                intent.putExtra("datinha", year + "-" + month + "-" + day);
                getApplicationContext().startActivity(intent);
            }
        });

        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int ano = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(detalhescont.this, mDateSetListener, ano, mes, dia);
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 += 1;
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(i2 + "-" + i1 + "-" + i));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String day = (String) DateFormat.format("dd", date);
                String month = (String) DateFormat.format("MM", date);
                String year = (String) DateFormat.format("yyyy", date);
                edtData.setText(day + "-" + month + "-" + year);
            }
        };

        iSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog myQuittingDialogBox =new AlertDialog.Builder(detalhescont.this)
                        .setTitle("Confirmação")
                        .setMessage("Tem certeza que deseja atualizar o registro?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = null;
                                Date date1 = null;
                                try {
                                    date = format.parse(String.valueOf(edtData.getText().toString()));
                                    date1 = format.parse(String.valueOf(data));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                final String day = (String) DateFormat.format("dd", date);
                                final String month = (String) DateFormat.format("MM", date);
                                final String year = (String) DateFormat.format("yyyy", date);
                                final String day1 = (String) DateFormat.format("dd", date1);
                                final String month1 = (String) DateFormat.format("MM", date1);
                                final String year1 = (String) DateFormat.format("yyyy", date1);

                                postRef = FirebaseDatabase.getInstance().getReference().child("contagem");
                                postRef1 = FirebaseDatabase.getInstance().getReference("codigosbarra").child(day1 + "-" + month1 + "-" + year1);
                                postRef2 = FirebaseDatabase.getInstance().getReference("codigosbarra").child(day + "-" + month + "-" + year);

                                postRef.orderByChild("data").equalTo(year + "-" + month + "-" + day)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    Toast.makeText(detalhescont.this, "Já existe um registro com essa data!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    postRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                                                CodigoBarras codigoBarras = dataSnap.getValue(CodigoBarras.class);
                                                                dataSnap.getRef().removeValue();
                                                                String ab = codigoBarras.getCod();
                                                                postRef2.child(ab).setValue(codigoBarras);
                                                            }
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_OR")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()){
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                    DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                    refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                    refamarela.setValue(year + "-" + month + "-" + day + "_OR");
                                                                                }
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_VM")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                refamarela.setValue(year + "-" + month + "-" + day + "_VM");
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_AZ")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()){
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                    DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                    refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                    refamarela.setValue(year + "-" + month + "-" + day + "_AZ");
                                                                                }
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_VR")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()){
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                    DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                    refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                    refamarela.setValue(year + "-" + month + "-" + day + "_VR");
                                                                                }
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_AM")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()){
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                    DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                    refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                    refamarela.setValue(year + "-" + month + "-" + day + "_AM");
                                                                                }
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_CR")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()){
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                    DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                    refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                    refamarela.setValue(year + "-" + month + "-" + day + "_CR");
                                                                                }
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_DS")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()){
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                    DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                    refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                    refamarela.setValue(year + "-" + month + "-" + day + "_DS");
                                                                                }
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_DT")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()){
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                    DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                    refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                    refamarela.setValue(year + "-" + month + "-" + day + "_DT");
                                                                                }
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_CC")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()){
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                    DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                    refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                    refamarela.setValue(year + "-" + month + "-" + day + "_CC");
                                                                                }
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                            postRef2.orderByChild("fichainfo").equalTo(year1 + "-" + month1 + "-" + day1 + "_SC")
                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                                            if (dataSnapshot.exists()){
                                                                                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                                                    DatabaseReference refamarela = appleSnapshot.getRef().child("fichainfo");
                                                                                    DatabaseReference refamarela1 = appleSnapshot.getRef().child("data");
                                                                                    refamarela1.setValue(year + "-" + month + "-" + day);
                                                                                    refamarela.setValue(year + "-" + month + "-" + day + "_SC");
                                                                                }
                                                                            }
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                    Contagem cont = new Contagem();
                                                    cont.setId(day + "-" + month + "-" + year);
                                                    cont.setData(year + "-" + month + "-" + day);
                                                    cont.setVermelha(Integer.parseInt(edtVermelha.getText().toString()));
                                                    cont.setAmarela(Integer.parseInt(edtAmarela.getText().toString()));
                                                    cont.setVerde(Integer.parseInt(edtVerde.getText().toString()));
                                                    cont.setAzul(Integer.parseInt(edtAzul.getText().toString()));
                                                    cont.setSemclass(Integer.parseInt(edtSemclass.getText().toString()));
                                                    cont.setCirurgico(Integer.parseInt(edtCirurgico.getText().toString()));
                                                    cont.setOrtopedia(Integer.parseInt(edtOrtopedia.getText().toString()));
                                                    cont.setCanceladas(Integer.parseInt(edtCanceladas.getText().toString()));
                                                    cont.setDesaparecidas(Integer.parseInt(edtDesaparecidas.getText().toString()));
                                                    cont.setDesistencia(Integer.parseInt(edtDesistencia.getText().toString()));
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference myRef = database.getReference("contagem");
                                                    myRef.child(key).removeValue();
                                                    myRef.child(day + "-" + month + "-" + year).setValue(cont);
                                                    Toast.makeText(detalhescont.this, "Registro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
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
    }

    public void onRestart(){
        super.onRestart();
        postRef = FirebaseDatabase.getInstance().getReference().child("contagem");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format.parse(String.valueOf(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String day = (String) DateFormat.format("dd", date);
        String month = (String) DateFormat.format("MM", date);
        String year = (String) DateFormat.format("yyyy", date);
        postRef.orderByChild("data").equalTo(year + "-" + month + "-" + day)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                Contagem contagem = appleSnapshot.getValue(Contagem.class);
                                edtVermelha.setText(String.valueOf(contagem.getVermelha()));
                                edtAmarela.setText(String.valueOf(contagem.getAmarela()));
                                edtVerde.setText(String.valueOf(contagem.getVerde()));
                                edtAzul.setText(String.valueOf(contagem.getAzul()));
                                edtSemclass.setText(String.valueOf(contagem.getSemclass()));
                                edtOrtopedia.setText(String.valueOf(contagem.getOrtopedia()));
                                edtCirurgico.setText(String.valueOf(contagem.getCirurgico()));
                                edtDesistencia.setText(String.valueOf(contagem.getDesistencia()));
                                edtDesaparecidas.setText(String.valueOf(contagem.getDesaparecidas()));
                                edtCanceladas.setText(String.valueOf(contagem.getCanceladas()));
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
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
