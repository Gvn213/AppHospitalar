package com.example.patriciasantos.apphospitalar.ContagemPasta;

public class Contagem {
    private String id;
    private String data;
    private int ortopedia;
    private int cirurgico;
    private int vermelha;
    private int azul;
    private int verde;
    private int amarela;
    private int semclass;
    private int desaparecidas;
    private int desistencia;
    private int canceladas;

    public Contagem(){
    }

    public Contagem(String id, String data, int ortopedia, int cirurgico, int vermelha, int azul, int verde, int amarela, int semclass, int desaparecidas, int desistencia, int canceladas) {
        this.id = id;
        this.data = data;
        this.ortopedia = ortopedia;
        this.cirurgico = cirurgico;
        this.vermelha = vermelha;
        this.azul = azul;
        this.verde = verde;
        this.amarela = amarela;
        this.semclass = semclass;
        this.desaparecidas = desaparecidas;
        this.desistencia = desistencia;
        this.canceladas = canceladas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getOrtopedia() {
        return ortopedia;
    }
    public void setOrtopedia(int ortopedia) {
        this.ortopedia = ortopedia;
    }

    public int getCirurgico() {
        return cirurgico;
    }
    public void setCirurgico(int cirurgico) {
        this.cirurgico = cirurgico;
    }

    public int getVermelha() {
        return vermelha;
    }
    public void setVermelha(int vermelha) {
        this.vermelha = vermelha;
    }


    public int getAzul() {
        return azul;
    }

    public void setAzul(int azul) {
        this.azul = azul;
    }

    public int getVerde() {
        return verde;
    }

    public void setVerde(int verde) {
        this.verde = verde;
    }

    public int getAmarela() {
        return amarela;
    }
    public void setAmarela(int amarela) {
        this.amarela = amarela;
    }

    public int getSemclass() {
        return semclass;
    }
    public void setSemclass(int semclass) {
        this.semclass = semclass;
    }

    public int getDesaparecidas() {
        return desaparecidas;
    }
    public void setDesaparecidas(int desaparecidas) {
        this.desaparecidas = desaparecidas;
    }

    public int getDesistencia() {
        return desistencia;
    }
    public void setDesistencia(int desistencia) {
        this.desistencia = desistencia;
    }

    public int getCanceladas() {
        return canceladas;
    }
    public void setCanceladas(int canceladas) {
        this.canceladas = canceladas;
    }

    @Override
    public String toString() {
        return this.data;
    }
}
