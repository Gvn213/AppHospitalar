package com.example.patriciasantos.apphospitalar;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class graficoteste extends AppCompatActivity {

    private String data, data2, orto, vermelha, azul, verde, amarela, cirurgico, desaparecidas, desistencia, canceladas, semclass;
    private ImageView btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graficoteste);

        BarChart bchart = findViewById(R.id.chart);
        ImageView btnSave = findViewById(R.id.btnSave);

        data = getIntent().getStringExtra("datinha");
        data2 = getIntent().getStringExtra("datinha2");
        orto = getIntent().getStringExtra("orto");
        vermelha = getIntent().getStringExtra("vermelha");
        azul = getIntent().getStringExtra("azul");
        verde = getIntent().getStringExtra("verde");
        amarela = getIntent().getStringExtra("amarela");
        cirurgico = getIntent().getStringExtra("cirurgico");
        desaparecidas = getIntent().getStringExtra("desaparecidas");
        desistencia = getIntent().getStringExtra("desistencia");
        canceladas = getIntent().getStringExtra("canceladas");
        semclass = getIntent().getStringExtra("semclass");

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        yVals1.add(new BarEntry(0, Integer.parseInt(vermelha)));
        yVals1.add(new BarEntry(1, Integer.parseInt(amarela)));
        yVals1.add(new BarEntry(2, Integer.parseInt(verde)));
        yVals1.add(new BarEntry(3, Integer.parseInt(azul)));
        yVals1.add(new BarEntry(4, Integer.parseInt(semclass)));
        yVals1.add(new BarEntry(5, Integer.parseInt(orto)));
        yVals1.add(new BarEntry(6, Integer.parseInt(cirurgico)));
        yVals1.add(new BarEntry(7, Integer.parseInt(desistencia)));
        yVals1.add(new BarEntry(8, Integer.parseInt(desaparecidas)));
        yVals1.add(new BarEntry(9, Integer.parseInt(canceladas)));

        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "Atendimentos no período de " + data + " a " + data2);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(ContextCompat.getColor(this, R.color.corVermelha));
        colors.add(ContextCompat.getColor(this, R.color.corAmarela));
        colors.add(ContextCompat.getColor(this, R.color.corVerde));
        colors.add(ContextCompat.getColor(this, R.color.corAzul));
        colors.add(ContextCompat.getColor(this, R.color.corSemclass));
        colors.add(ContextCompat.getColor(this, R.color.corOrtopedia));
        colors.add(ContextCompat.getColor(this, R.color.corCirurgico));
        colors.add(ContextCompat.getColor(this, R.color.corDesistencias));
        colors.add(ContextCompat.getColor(this, R.color.corDesaparecidas));
        colors.add(ContextCompat.getColor(this, R.color.corCanceladas));
        set1.setColors(colors);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setHighlightEnabled(true);
        set1.setValueTextSize(12);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        ArrayList<String> labels = new ArrayList<> ();
        labels.add("Vermelha");
        labels.add("Amarela");
        labels.add("Verde");
        labels.add("Azul");
        labels.add("Sem Class.");
        labels.add("Ortopedia");
        labels.add("Cirúrgico");
        labels.add("Desist.");
        labels.add("Desaparec.");
        labels.add("Canceladas");

        bchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        bchart.animateY(3000);
        bchart.getDescription().setText("");
        bchart.getDescription().setTextSize(12);
        bchart.setDrawMarkers(true);
        bchart.getAxisLeft().setAxisMinimum(0);
        bchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        bchart.getXAxis().setGranularityEnabled(true);
        bchart.getXAxis().setGranularity(1.0f);
        bchart.getXAxis().setLabelCount(set1.getEntryCount());

        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        bchart.setTouchEnabled(false);
        bchart.setData(data);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(graficoteste.this)
                        .setTitle("Salvar Relatório")
                        .setMessage("Deseja salvar o Relatório em PDF?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                createPdf();
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
    }

    private void createPdf(){
        View content = findViewById(R.id.chart);
        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(content.getWidth(), content.getHeight(), 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        content.draw(page.getCanvas());

        // finish the page
        document.finishPage(page);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String pdfName = "Relatório "
                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";
        // write the document content
        String targetPdf = "/sdcard/Relatórios Hosp./";
        File filePath = new File(targetPdf);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        try {
            document.writeTo(new FileOutputStream(filePath+"/"+pdfName));
            Toast.makeText(this, "O relatório foi salvo em PDF na pasta 'Relatórios Hosp.' em seu Armazenamento Interno.", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }
        document.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}