package com.example.patriciasantos.apphospitalar.TransferenciasPasta;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.ContagemPasta.CodigoBarras;
import com.example.patriciasantos.apphospitalar.ContagemPasta.Contagem;
import com.example.patriciasantos.apphospitalar.ContagemPasta.abacontagem;
import com.example.patriciasantos.apphospitalar.ContagemPasta.consultacontagem;
import com.example.patriciasantos.apphospitalar.R;
import com.example.patriciasantos.apphospitalar.TransActivity;
import com.example.patriciasantos.apphospitalar.TransferenciasPasta.Transferencia;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigodesistencia;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigovermelha;
import com.example.patriciasantos.apphospitalar.detalhescont;
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

public class abatrans extends Fragment {
    private static final String TAG = "Tab3Fragment";
    private Spinner spinner1;
    Button btnConsulta;
    EditText edtData;
    EditText edtPaciente;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    ImageView imgLer;
    DatabaseReference postRef;
    private static int editTextNumber = 0;
    private Integer contador;
    private String key;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transferencia, container, false);
        setHasOptionsMenu(true);
        edtData = (EditText) view.findViewById(R.id.edtData);
        edtPaciente = (EditText) view.findViewById(R.id.edtPaciente);
        spinner1 = (Spinner) view.findViewById(R.id.spinhosp);
        imgLer = (ImageView) view.findViewById(R.id.imgLer);
        btnConsulta = (Button) view.findViewById(R.id.btnConsulta);
        contador = 0;
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int ano = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), mDateSetListener, ano, mes, dia);
                datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
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
        eaemen();

        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), consultrans2.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    public void eaemen() {
        imgLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abatrans.this).initiateScan();
                editTextNumber  = 0;
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        final String scanContent = scanningResult.getContents();
        if (scanningResult != null && editTextNumber == 0){
            postRef = FirebaseDatabase.getInstance().getReference().child("transferencia");
            postRef.orderByChild("paciente").equalTo(scanContent)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(getContext(), "Este paciente já foi transferido!", Toast.LENGTH_SHORT).show();
                                edtPaciente.getText().clear();
                            } else {
                                edtPaciente.setText(scanContent);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Código de barras não encontrado!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menuha, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.btenv:
                    String Data = edtData.getText().toString().trim();
                    String Paciente = edtPaciente.getText().toString().trim();
                    String hospital = spinner1.getSelectedItem().toString();
                if (TextUtils.isEmpty(Data) || TextUtils.isEmpty(Paciente) || hospital.contains("Hospital...")){
                    Toast.makeText(getContext(), "Preencha todos os campos antes de enviar!", Toast.LENGTH_SHORT).show();
                }
                else{
                    postRef = FirebaseDatabase.getInstance().getReference("transferencia").child(edtData.getText().toString());
                    postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {
                                        final Transferencia trans = new Transferencia();
                                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                        Date date = null;
                                        try {
                                            date = format.parse(String.valueOf(edtData.getText().toString()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        String day = (String) DateFormat.format("dd", date);
                                        String month = (String) DateFormat.format("MM", date);
                                        String year = (String) DateFormat.format("yyyy", date);
                                        trans.setData(year + "-" + month + "-" + day);
                                        trans.setPaciente(edtPaciente.getText().toString());
                                        trans.setHospital(spinner1.getSelectedItem().toString());
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        contador += 1;
                                        myRef = database.getReference("transferencia").child(day + "-" + month + "-" + year);
                                        myRef.setValue(trans);
                                        key = myRef.child(day + "-" + month + "-" + year).getKey();
                                        Toast.makeText(getActivity(), "Transferência registrada!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), consultrans2.class);
                                        startActivity(intent);
                                        edtData.getText().clear();
                                        edtPaciente.getText().clear();
                                        spinner1.setSelection(0);

                                    } else {
                                        final Transferencia trans = new Transferencia();
                                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                        Date date = null;
                                        try {
                                            date = format.parse(String.valueOf(edtData.getText().toString()));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        String day = (String) DateFormat.format("dd", date);
                                        String month = (String) DateFormat.format("MM", date);
                                        String year = (String) DateFormat.format("yyyy", date);
                                        trans.setData(year + "-" + month + "-" + day);
                                        trans.setPaciente(edtPaciente.getText().toString());
                                        trans.setHospital(spinner1.getSelectedItem().toString());
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef1 = database.getReference("transferencia").child(dataSnapshot.getKey() + "-"+edtPaciente.getText().toString());
                                        myRef1.setValue(trans);
                                        Toast.makeText(getActivity(), "Transferência registrada!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), consultrans2.class);
                                        startActivity(intent);
                                        edtData.getText().clear();
                                        edtPaciente.getText().clear();
                                        spinner1.setSelection(0);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
