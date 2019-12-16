package com.example.patriciasantos.apphospitalar.TransferenciasPasta;

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
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.ContagemPasta.CodigoBarras;
import com.example.patriciasantos.apphospitalar.ContagemPasta.Contagem;
import com.example.patriciasantos.apphospitalar.ContagemPasta.abacontagem;
import com.example.patriciasantos.apphospitalar.ContagemPasta.consultacontagem;
import com.example.patriciasantos.apphospitalar.R;
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

public class detalhesconsu extends AppCompatActivity {
    EditText edtData, edtPaciente;
    Button btnConsulta;
    ImageView iSave, iLer;
    TextView txtVew;
    String data, paciente, hospital;
    private Spinner spinner1;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseReference postRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transferencia);

        data = getIntent().getStringExtra("data");
        paciente = getIntent().getStringExtra("paciente");
        hospital = getIntent().getStringExtra("hospital");

        txtVew = (TextView) findViewById(R.id.txtVew);
        txtVew.setVisibility(View.VISIBLE);

        edtPaciente = (EditText) findViewById(R.id.edtPaciente);
        edtPaciente.setText(paciente);

        spinner1 = (Spinner) findViewById(R.id.spinhosp);
        for(int i= 0; i < spinner1.getAdapter().getCount(); i++)
        {
            if(spinner1.getAdapter().getItem(i).toString().contains(hospital))
            {
                spinner1.setSelection(i);
            }
        }

        iLer = (ImageView) findViewById(R.id.imgLer);
        iLer.setVisibility(View.INVISIBLE);

        btnConsulta = (Button) findViewById(R.id.btnConsulta);
        btnConsulta.setVisibility(View.INVISIBLE);

        iSave = (ImageView) findViewById(R.id.imgSave);
        iSave.setVisibility(View.VISIBLE);

        edtData = (EditText) findViewById(R.id.edtData);
        edtData.setText(data);

        final Calendar ca = Calendar.getInstance();
        final int anos = ca.get(Calendar.YEAR);
        final int mess = ca.get(Calendar.MONTH);
        final int dias = ca.get(Calendar.DAY_OF_MONTH);

        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(detalhesconsu.this, mDateSetListener, anos, mess, dias);
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 += 1;
                edtData.setText(i2 + "-" + i1 + "-" + i);
            }
        };

        iSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog myQuittingDialogBox =new AlertDialog.Builder(detalhesconsu.this)
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
                                postRef = FirebaseDatabase.getInstance().getReference().child("transferencia");
                                postRef.orderByChild("data").equalTo(year1 + "-" + month1 + "-" + day1)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (!dataSnapshot.exists()) {
                                                    Transferencia trans = new Transferencia();
                                                    trans.setData(year + "-" + month + "-" + day);
                                                    trans.setPaciente(edtPaciente.getText().toString());
                                                    trans.setHospital(spinner1.getSelectedItem().toString());
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference myRef = database.getReference("transferencia");
                                                    myRef.child(day1 + "-" + month1 + "-" + year1).removeValue();
                                                    myRef.child(day + "-" + month + "-" + year).setValue(trans);
                                                    Toast.makeText(detalhesconsu.this, "Registro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                                Transferencia trans = new Transferencia();
                                                trans.setData(year + "-" + month + "-" + day);
                                                trans.setPaciente(edtPaciente.getText().toString());
                                                trans.setHospital(spinner1.getSelectedItem().toString());
                                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                DatabaseReference myRef = database.getReference("transferencia");
                                                myRef.child(day1 + "-" + month1 + "-" + year1 + "-"+paciente).removeValue();
                                                myRef.child(day + "-" + month + "-" + year + "-"+paciente).setValue(trans);
                                                Toast.makeText(detalhesconsu.this, "Registro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                                                finish();
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
