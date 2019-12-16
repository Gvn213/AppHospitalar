package com.example.patriciasantos.apphospitalar.ContagemPasta;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.R;
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

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class abacontagem extends Fragment {
    private static final String TAG = "Tab1Fragment";
    private ZXingScannerView ScannerView;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button btnTese;
    private EditText edtData;
    private EditText edtOrtopedia;
    private EditText edtCirurgico;
    private EditText edtVermelha;
    private EditText edtAmarela;
    private EditText edtAzul;
    private EditText edtVerde;
    private EditText edtSemclass;
    private EditText edtDesaparecidas;
    private EditText edtDesistencia;
    private EditText edtCanceladas;
    private Integer contador;
    private Integer contador1;
    private Integer contador2;
    private Integer contador3;
    private Integer contador4;
    private Integer contador5;
    private Integer contador6;
    private Integer contador7;
    private Integer contador8;
    private Integer contador9;
    private ImageView iOrtopedia;
    private ImageView iVermelho;
    private ImageView iAmarelo;
    private ImageView iVerde;
    private ImageView iAzul;
    private ImageView iCirurgico;
    private ImageView iDesaparecidas;
    private ImageView iDesistencia;
    private ImageView iCanceladas;
    private ImageView iSemclass;
    private DatabaseReference postRef, postRef1;
    private TextView txtOrtopedia;
    private TextView txtVermelha;
    private TextView txtAzul;
    private TextView txtVerde;
    private TextView txtAmarela;
    private TextView txtCirurgico;
    private TextView txtDesaparecidas;
    private TextView txtDesistencia;
    private TextView txtCanceladas;
    private TextView txtSemclass;
    private TextView txtZerar;
    private ProgressBar progressBar;
    private static int editTextNumber = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Integer anoFinal, diaFinal, mesFinal;

        View view = inflater.inflate(R.layout.contagem, container, false);
        setHasOptionsMenu(true);
        edtData = (EditText) view.findViewById(R.id.edtData);
        edtOrtopedia = (EditText) view.findViewById(R.id.edtOrtopedia);
        edtCirurgico = (EditText) view.findViewById(R.id.edtCirurgico);
        edtVermelha = (EditText) view.findViewById(R.id.edtVermelha);
        edtAmarela = (EditText) view.findViewById(R.id.edtAmarela);
        edtAzul = (EditText) view.findViewById(R.id.edtAzul);
        edtVerde = (EditText) view.findViewById(R.id.edtVerde);
        edtSemclass = (EditText) view.findViewById(R.id.edtSemclass);
        edtDesaparecidas = (EditText) view.findViewById(R.id.edtDesaparecidas);
        edtDesistencia = (EditText) view.findViewById(R.id.edtDesistencia);
        edtCanceladas = (EditText) view.findViewById(R.id.edtCanceladas);

        txtOrtopedia = (TextView) view.findViewById(R.id.txtOrtopedia);
        txtVermelha = (TextView) view.findViewById(R.id.txtVermelha);
        txtAzul = (TextView) view.findViewById(R.id.txtAzul);
        txtVerde = (TextView) view.findViewById(R.id.txtVerde);
        txtAmarela = (TextView) view.findViewById(R.id.txtAmarela);
        txtCirurgico = (TextView) view.findViewById(R.id.txtCirurgico);
        txtDesaparecidas = (TextView) view.findViewById(R.id.txtDesaparecidas);
        txtDesistencia = (TextView) view.findViewById(R.id.txtDesistencia);
        txtCanceladas = (TextView) view.findViewById(R.id.txtCanceladas);
        txtSemclass = (TextView) view.findViewById(R.id.txtSemclass);
        txtZerar = (TextView) view.findViewById(R.id.txtZerar);

        iOrtopedia = (ImageView) view.findViewById(R.id.imgOrtopedia);
        iVermelho = (ImageView) view.findViewById(R.id.imageView13);
        iAzul = (ImageView) view.findViewById(R.id.imageView3);
        iVerde = (ImageView) view.findViewById(R.id.imageView18);
        iAmarelo = (ImageView) view.findViewById(R.id.imageView19);
        iCirurgico = (ImageView) view.findViewById(R.id.imageView7);
        iDesaparecidas = (ImageView) view.findViewById(R.id.imgDsp);
        iDesistencia = (ImageView) view.findViewById(R.id.imageView11);
        iCanceladas = (ImageView) view.findViewById(R.id.imageView21);
        iSemclass = (ImageView) view.findViewById(R.id.imageView20);

        btnTese = (Button) view.findViewById(R.id.btnTese);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.INVISIBLE);

        contador = 0;
        contador1 = 0;
        contador2 = 0;
        contador3 = 0;
        contador4 = 0;
        contador5 = 0;
        contador6 = 0;
        contador7 = 0;
        contador8 = 0;
        contador9 = 0;

        iOrtopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 0;
            }
        });
        iVermelho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 1;
            }
        });
        iAzul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 2;
            }
        });
        iVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 3;
            }
        });
        iAmarelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 4;
            }
        });
        iCirurgico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 5;
            }
        });
        iDesaparecidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 6;
            }
        });
        iDesistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 7;
            }
        });
        iCanceladas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 8;
            }
        });
        iSemclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.forSupportFragment(abacontagem.this).initiateScan();
                editTextNumber = 9;
            }
        });

        txtOrtopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("ortopedia")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador = 0;
                                            edtOrtopedia.setText(contador.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

        txtVermelha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("vermelha")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador1 = 0;
                                            edtVermelha.setText(contador1.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

        txtAzul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("azul")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador2 = 0;
                                            edtAzul.setText(contador2.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

        txtVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("verde")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador3 = 0;
                                            edtVerde.setText(contador3.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

        txtAmarela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("amarela")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador4 = 0;
                                            edtAmarela.setText(contador4.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

        txtCirurgico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("cirurgico")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador5 = 0;
                                            edtCirurgico.setText(contador5.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

        txtDesaparecidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("desaparecidas")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador6 = 0;
                                            edtDesaparecidas.setText(contador6.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

        txtDesistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("desistencia")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador7 = 0;
                                            edtDesistencia.setText(contador7.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

        txtCanceladas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("canceladas")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador8 = 0;
                                            edtCanceladas.setText(contador8.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

        txtSemclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRef = FirebaseDatabase.getInstance().getReference("codigosbarra").child(edtData.getText().toString());
                postRef.orderByChild("ficha").equalTo("semclass")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                    txtZerar.setVisibility(View.VISIBLE);
                                    txtZerar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                                appleSnapshot.getRef().removeValue();
                                            }
                                            contador9 = 0;
                                            edtSemclass.setText(contador9.toString());
                                            txtZerar.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                } else {
                                    txtZerar.setVisibility(View.INVISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
            }
        });

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

        edtData.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {

                } else {
                    String Data = edtData.getText().toString().trim();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = null;
                    try {
                        date = format.parse(Data);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String day = (String) DateFormat.format("dd", date);
                    String month = (String) DateFormat.format("MM", date);
                    String year = (String) DateFormat.format("yyyy", date);
                    postRef = FirebaseDatabase.getInstance().getReference().child("contagem");
                    progressBar.setVisibility(View.VISIBLE);
                    postRef.orderByChild("data").equalTo(year + "-" + month + "-" + day)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        Toast.makeText(getContext(), "Já existe um registro com essa data!", Toast.LENGTH_SHORT).show();
                                        txtOrtopedia.setVisibility(View.INVISIBLE);
                                        txtVermelha.setVisibility(View.INVISIBLE);
                                        txtAzul.setVisibility(View.INVISIBLE);
                                        txtVerde.setVisibility(View.INVISIBLE);
                                        txtAmarela.setVisibility(View.INVISIBLE);
                                        txtCirurgico.setVisibility(View.INVISIBLE);
                                        txtDesaparecidas.setVisibility(View.INVISIBLE);
                                        txtDesistencia.setVisibility(View.INVISIBLE);
                                        txtCanceladas.setVisibility(View.INVISIBLE);
                                        txtSemclass.setVisibility(View.INVISIBLE);

                                        edtOrtopedia.setVisibility(View.INVISIBLE);
                                        edtVermelha.setVisibility(View.INVISIBLE);
                                        edtAzul.setVisibility(View.INVISIBLE);
                                        edtVerde.setVisibility(View.INVISIBLE);
                                        edtAmarela.setVisibility(View.INVISIBLE);
                                        edtCirurgico.setVisibility(View.INVISIBLE);
                                        edtDesaparecidas.setVisibility(View.INVISIBLE);
                                        edtDesistencia.setVisibility(View.INVISIBLE);
                                        edtCanceladas.setVisibility(View.INVISIBLE);
                                        edtSemclass.setVisibility(View.INVISIBLE);

                                        iOrtopedia.setVisibility(View.INVISIBLE);
                                        iVermelho.setVisibility(View.INVISIBLE);
                                        iAzul.setVisibility(View.INVISIBLE);
                                        iVerde.setVisibility(View.INVISIBLE);
                                        iAmarelo.setVisibility(View.INVISIBLE);
                                        iCirurgico.setVisibility(View.INVISIBLE);
                                        iDesaparecidas.setVisibility(View.INVISIBLE);
                                        iDesistencia.setVisibility(View.INVISIBLE);
                                        iCanceladas.setVisibility(View.INVISIBLE);
                                        iSemclass.setVisibility(View.INVISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                        setHasOptionsMenu(false);
                                    } else {
                                        txtOrtopedia.setVisibility(View.VISIBLE);
                                        txtVermelha.setVisibility(View.VISIBLE);
                                        txtAzul.setVisibility(View.VISIBLE);
                                        txtVerde.setVisibility(View.VISIBLE);
                                        txtAmarela.setVisibility(View.VISIBLE);
                                        txtCirurgico.setVisibility(View.VISIBLE);
                                        txtDesaparecidas.setVisibility(View.VISIBLE);
                                        txtDesistencia.setVisibility(View.VISIBLE);
                                        txtCanceladas.setVisibility(View.VISIBLE);
                                        txtSemclass.setVisibility(View.VISIBLE);

                                        edtOrtopedia.setVisibility(View.VISIBLE);
                                        edtVermelha.setVisibility(View.VISIBLE);
                                        edtAzul.setVisibility(View.VISIBLE);
                                        edtVerde.setVisibility(View.VISIBLE);
                                        edtAmarela.setVisibility(View.VISIBLE);
                                        edtCirurgico.setVisibility(View.VISIBLE);
                                        edtDesaparecidas.setVisibility(View.VISIBLE);
                                        edtDesistencia.setVisibility(View.VISIBLE);
                                        edtCanceladas.setVisibility(View.VISIBLE);
                                        edtSemclass.setVisibility(View.VISIBLE);

                                        iOrtopedia.setVisibility(View.VISIBLE);
                                        iVermelho.setVisibility(View.VISIBLE);
                                        iAzul.setVisibility(View.VISIBLE);
                                        iVerde.setVisibility(View.VISIBLE);
                                        iAmarelo.setVisibility(View.VISIBLE);
                                        iCirurgico.setVisibility(View.VISIBLE);
                                        iDesaparecidas.setVisibility(View.VISIBLE);
                                        iDesistencia.setVisibility(View.VISIBLE);
                                        iCanceladas.setVisibility(View.VISIBLE);
                                        iSemclass.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                        setHasOptionsMenu(true);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

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
        return view;
    }


    @Override
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
                String Ortopedia = edtOrtopedia.getText().toString().trim();
                String Cirurgico = edtCirurgico.getText().toString().trim();
                String Vermelha = edtVermelha.getText().toString().trim();
                String Amarela = edtAmarela.getText().toString().trim();
                String Verde = edtVerde.getText().toString().trim();
                String Azul = edtAzul.getText().toString().trim();
                String Semclass = edtSemclass.getText().toString().trim();
                String Desaparecidas = edtDesaparecidas.getText().toString().trim();
                String Desistencia = edtDesistencia.getText().toString().trim();
                String Canceladas = edtCanceladas.getText().toString().trim();
                if (TextUtils.isEmpty(Data) || TextUtils.isEmpty(Ortopedia) || TextUtils.isEmpty(Cirurgico) || TextUtils.isEmpty(Vermelha) || TextUtils.isEmpty(Amarela) ||
                        TextUtils.isEmpty(Verde) || TextUtils.isEmpty(Verde) || TextUtils.isEmpty(Azul) || TextUtils.isEmpty(Semclass) ||
                        TextUtils.isEmpty(Desaparecidas) || TextUtils.isEmpty(Desistencia) || TextUtils.isEmpty(Canceladas)) {
                    Toast.makeText(getContext(), "Preencha todos os campos antes de enviar!", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(Ortopedia) > 999 || Integer.parseInt(Cirurgico) > 999 || Integer.parseInt(Vermelha) > 999 ||
                        Integer.parseInt(Amarela) > 999 || Integer.parseInt(Verde) > 999 || Integer.parseInt(Azul) > 999 ||
                        Integer.parseInt(Semclass) > 999 || Integer.parseInt(Desaparecidas) > 999 || Integer.parseInt(Desistencia) > 999 ||
                        Integer.parseInt(Canceladas) > 999) {
                    Toast.makeText(getContext(), "Os campos devem ser preenchidos com valores de 0 a 999!", Toast.LENGTH_SHORT).show();
                } else {
                    Contagem cont = new Contagem();
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
                    myRef.child(day + "-" + month + "-" + year).setValue(cont);
                    cont.setId(day + "-" + month + "-" + year);
                    myRef.child(day + "-" + month + "-" + year).setValue(cont);
                    Toast.makeText(getActivity(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    Fragment frag = new consultacontagem();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        final String scanContent = scanningResult.getContents();
        if (scanContent != null && editTextNumber == 0) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                    if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                        Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                                    }else{
                                            CodigoBarras cod = new CodigoBarras();
                                            cod.setCod(scanContent);
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
                                            cod.setData(year + "-" + month + "-" + day);
                                            cod.setFicha("ortopedia");
                                            cod.setFichainfo(year + "-" + month + "-" + day + "_OR");
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                            myRef.setValue(cod);
                                            contador += 1;
                                            edtOrtopedia.setText(contador.toString());
                                    }
                                }
                            }
                            else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("ortopedia");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_OR");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador += 1;
                                edtOrtopedia.setText(contador.toString());
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        } else if (scanContent != null && editTextNumber == 1) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                            }else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("vermelha");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_VM");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador1 += 1;
                                edtVermelha.setText(contador1.toString());
                            }
                        }
                    } else{
                        CodigoBarras cod = new CodigoBarras();
                        cod.setCod(scanContent);
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
                        cod.setData(year + "-" + month + "-" + day);
                        cod.setFicha("vermelha");
                        cod.setFichainfo(year + "-" + month + "-" + day + "_VM");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                        myRef.setValue(cod);
                        contador1 += 1;
                        edtVermelha.setText(contador1.toString());
                        return;
                     }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (scanContent != null && editTextNumber == 2) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                            }else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("azul");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_AZ");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador2 += 1;
                                edtAzul.setText(contador2.toString());
                            }
                        }
                    } else{
                        CodigoBarras cod = new CodigoBarras();
                        cod.setCod(scanContent);
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
                        cod.setData(year + "-" + month + "-" + day);
                        cod.setFicha("azul");
                        cod.setFichainfo(year + "-" + month + "-" + day + "_AZ");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                        myRef.setValue(cod);
                        contador2 += 1;
                        edtAzul.setText(contador2.toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (scanContent != null && editTextNumber == 3) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                            }else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("verde");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_VR");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador3 += 1;
                                edtVerde.setText(contador3.toString());
                            }
                        }
                    } else{
                        CodigoBarras cod = new CodigoBarras();
                        cod.setCod(scanContent);
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
                        cod.setData(year + "-" + month + "-" + day);
                        cod.setFicha("verde");
                        cod.setFichainfo(year + "-" + month + "-" + day + "_VR");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                        myRef.setValue(cod);
                        contador3 += 1;
                        edtVerde.setText(contador3.toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (scanContent != null && editTextNumber == 4) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                            }else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("amarela");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_AM");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador4 += 1;
                                edtAmarela.setText(contador4.toString());
                            }
                        }
                    } else{
                        CodigoBarras cod = new CodigoBarras();
                        cod.setCod(scanContent);
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
                        cod.setData(year + "-" + month + "-" + day);
                        cod.setFicha("amarela");
                        cod.setFichainfo(year + "-" + month + "-" + day + "_AM");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                        myRef.setValue(cod);
                        contador4 += 1;
                        edtAmarela.setText(contador4.toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (scanContent != null && editTextNumber == 5) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                            }else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("cirurgico");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_CR");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador5 += 1;
                                edtCirurgico.setText(contador5.toString());
                            }
                        }
                    } else{
                        CodigoBarras cod = new CodigoBarras();
                        cod.setCod(scanContent);
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
                        cod.setData(year + "-" + month + "-" + day);
                        cod.setFicha("cirurgico");
                        cod.setFichainfo(year + "-" + month + "-" + day + "_CR");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                        myRef.setValue(cod);
                        contador5 += 1;
                        edtCirurgico.setText(contador5.toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (scanContent != null && editTextNumber == 6) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                            }else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("desaparecidas");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_DS");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador6 += 1;
                                edtDesaparecidas.setText(contador6.toString());
                            }
                            }
                    } else{
                        CodigoBarras cod = new CodigoBarras();
                        cod.setCod(scanContent);
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
                        cod.setData(year + "-" + month + "-" + day);
                        cod.setFicha("desaparecidas");
                        cod.setFichainfo(year + "-" + month + "-" + day + "_DS");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                        myRef.setValue(cod);
                        contador6 += 1;
                        edtDesaparecidas.setText(contador6.toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (scanContent != null && editTextNumber == 7) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                            }else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("desistencia");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_DT");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador7 += 1;
                                edtDesistencia.setText(contador7.toString());
                            }
                        }
                    } else{
                        CodigoBarras cod = new CodigoBarras();
                        cod.setCod(scanContent);
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
                        cod.setData(year + "-" + month + "-" + day);
                        cod.setFicha("desistencia");
                        cod.setFichainfo(year + "-" + month + "-" + day + "_DT");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                        myRef.setValue(cod);
                        contador7 += 1;
                        edtDesistencia.setText(contador7.toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (scanContent != null && editTextNumber == 8) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                            }else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("canceladas");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_CC");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador8 += 1;
                                edtCanceladas.setText(contador8.toString());
                            }
                        }
                    } else{
                        CodigoBarras cod = new CodigoBarras();
                        cod.setCod(scanContent);
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
                        cod.setData(year + "-" + month + "-" + day);
                        cod.setFicha("canceladas");
                        cod.setFichainfo(year + "-" + month + "-" + day + "_CC");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                        myRef.setValue(cod);
                        contador8 += 1;
                        edtCanceladas.setText(contador8.toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (scanContent != null && editTextNumber == 9) {
            postRef = FirebaseDatabase.getInstance().getReference("codigosbarra");
            postRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            if (dataSnapshot1.child(dataSnapshot1.child(scanContent).getKey()).exists()){
                                Toast.makeText(getActivity(), "Já existe um registro com esse codigo!", Toast.LENGTH_SHORT).show();
                            }else{
                                CodigoBarras cod = new CodigoBarras();
                                cod.setCod(scanContent);
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
                                cod.setData(year + "-" + month + "-" + day);
                                cod.setFicha("semclass");
                                cod.setFichainfo(year + "-" + month + "-" + day + "_SC");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                                myRef.setValue(cod);
                                contador9 += 1;
                                edtSemclass.setText(contador9.toString());
                            }
                        }
                    } else{
                        CodigoBarras cod = new CodigoBarras();
                        cod.setCod(scanContent);
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
                        cod.setData(year + "-" + month + "-" + day);
                        cod.setFicha("semclass");
                        cod.setFichainfo(year + "-" + month + "-" + day + "_SC");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("codigosbarra").child(day + "-" + month + "-" + year).child(scanContent);
                        myRef.setValue(cod);
                        contador9 += 1;
                        edtSemclass.setText(contador9.toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else{
            Toast.makeText(getContext(), "Código de barras não encontrado!", Toast.LENGTH_SHORT).show();
        }
    }
}