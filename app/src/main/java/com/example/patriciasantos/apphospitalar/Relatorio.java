package com.example.patriciasantos.apphospitalar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.patriciasantos.apphospitalar.Cadastro.LoginActivity;
import com.example.patriciasantos.apphospitalar.codigos_barras.codigodesaparecidas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Relatorio extends Fragment {
    EditText edtDatainicio;
    EditText edtDatafim;
    EditText edtOrtopedia;
    EditText edtCirurgico;
    EditText edtVermelha;
    EditText edtAmarela;
    EditText edtAzul;
    EditText edtVerde;
    EditText edtSemclass;
    EditText edtDesaparecidas;
    EditText edtDesistencia;
    EditText edtCanceladas;
    Button btnBuscar;
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
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.relatorio, container, false);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        edtDatainicio = view.findViewById(R.id.edtDatainicio);
        edtDatafim = view.findViewById(R.id.edtDatafim);

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

        final Calendar ca = Calendar.getInstance();
        final int anos = ca.get(Calendar.YEAR);
        final int mess = ca.get(Calendar.MONTH);
        final int dias = ca.get(Calendar.DAY_OF_MONTH);

        edtDatainicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), mDateSetListener, anos, mess, dias);
                datePickerDialog.show();
            }
        });

        edtDatafim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int ano = c.get(Calendar.YEAR);
                int mes = c.get(Calendar.MONTH);
                int dia = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), mDateSetListener2, ano, mes, dia);
                String string_date = edtDatainicio.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = format.parse(String.valueOf(string_date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                long checkOutDate = c.getTimeInMillis();
                datePickerDialog.getDatePicker().setMinDate(checkOutDate - 1000);
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
                edtDatainicio.setText(day + "-" + month + "-" + year);
                edtDatafim.setEnabled(true);
            }
        };
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
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
                edtDatafim.setText(day + "-" + month + "-" + year);
            }
        };
        return view;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menuha, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btenv:
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                String Data = edtDatainicio.getText().toString().trim();
                String Data2 = edtDatafim.getText().toString().trim();
                if (TextUtils.isEmpty(Data) || TextUtils.isEmpty(Data2)) {
                    Toast.makeText(getActivity(), "As Datas não foram informadas!", Toast.LENGTH_SHORT).show();
                }else {
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = null;
                    Date date1 = null;
                    try {
                        date = format.parse(String.valueOf(Data));
                        date1 = format.parse(String.valueOf(Data2));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    final String day = (String) DateFormat.format("dd", date);
                    final String month = (String) DateFormat.format("MM", date);
                    final String year = (String) DateFormat.format("yyyy", date);

                    final String day1 = (String) DateFormat.format("dd", date1);
                    final String month1 = (String) DateFormat.format("MM", date1);
                    final String year1 = (String) DateFormat.format("yyyy", date1);
                    Query query = rootRef.child("contagem").orderByChild("data").startAt(year + "-" + month + "-" + day).endAt(year1 + "-" + month1 + "-" + day1);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            long ortopedia = 0;
                            long vermelha = 0;
                            long azul = 0;
                            long verde = 0;
                            long amarela = 0;
                            long cirurgico = 0;
                            long desaparecidas = 0;
                            long desistencia = 0;
                            long canceladas = 0;
                            long semclass = 0;
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                ortopedia += ds.child("ortopedia").getValue(long.class);
                                vermelha += ds.child("vermelha").getValue(long.class);
                                azul += ds.child("azul").getValue(long.class);
                                verde += ds.child("verde").getValue(long.class);
                                amarela += ds.child("amarela").getValue(long.class);
                                cirurgico += ds.child("cirurgico").getValue(long.class);
                                desaparecidas += ds.child("desaparecidas").getValue(long.class);
                                desistencia += ds.child("desistencia").getValue(long.class);
                                canceladas += ds.child("canceladas").getValue(long.class);
                                semclass += ds.child("semclass").getValue(long.class);
                                edtOrtopedia.setText(String.valueOf(ortopedia));
                                edtVermelha.setText(String.valueOf(vermelha));
                                edtAzul.setText(String.valueOf(azul));
                                edtVerde.setText(String.valueOf(verde));
                                edtAmarela.setText(String.valueOf(amarela));
                                edtCirurgico.setText(String.valueOf(cirurgico));
                                edtDesaparecidas.setText(String.valueOf(desaparecidas));
                                edtDesistencia.setText(String.valueOf(desistencia));
                                edtCanceladas.setText(String.valueOf(canceladas));
                                edtSemclass.setText(String.valueOf(semclass));

                                if (Integer.parseInt(String.valueOf(ortopedia)) == 0 && Integer.parseInt(String.valueOf(vermelha)) == 0 && Integer.parseInt(String.valueOf(azul)) == 0 &&
                                        Integer.parseInt(String.valueOf(verde)) == 0 && Integer.parseInt(String.valueOf(cirurgico)) == 0 && Integer.parseInt(String.valueOf(amarela)) == 0 &&
                                        Integer.parseInt(String.valueOf(desaparecidas)) == 0 && Integer.parseInt(String.valueOf(desistencia)) == 0 && Integer.parseInt(String.valueOf(canceladas)) == 0 &&
                                        Integer.parseInt(String.valueOf(semclass)) == 0) {
                                    Toast.makeText(getContext(), "Não há registros disponíveis neste intervalo para gerar o relatório.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(getActivity(), graficoteste.class);
                                    intent.putExtra("datinha", day + "-" + month + "-" + year);
                                    intent.putExtra("datinha2", day1 + "-" + month1 + "-" + year1);
                                    intent.putExtra("orto", edtOrtopedia.getText().toString());
                                    intent.putExtra("vermelha", edtVermelha.getText().toString());
                                    intent.putExtra("azul", edtAzul.getText().toString());
                                    intent.putExtra("verde", edtVerde.getText().toString());
                                    intent.putExtra("amarela", edtAmarela.getText().toString());
                                    intent.putExtra("cirurgico", edtCirurgico.getText().toString());
                                    intent.putExtra("desaparecidas", edtDesaparecidas.getText().toString());
                                    intent.putExtra("desistencia", edtDesistencia.getText().toString());
                                    intent.putExtra("canceladas", edtCanceladas.getText().toString());
                                    intent.putExtra("semclass", edtSemclass.getText().toString());
                                    getActivity().startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    };
                    query.addListenerForSingleValueEvent(valueEventListener);
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
}

