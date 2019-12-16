package com.example.patriciasantos.apphospitalar.ContagemPasta;

public class CodigoBarras {
    private String cod;
    private String ficha;
    private String data;
    private String fichainfo;

    public CodigoBarras() {
    }

    public CodigoBarras(String cod, String ficha, String data, String fichainfo) {
        this.cod = cod;
        this.ficha = ficha;
        this.data = data;
        this.fichainfo = fichainfo;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getFicha() {
        return ficha;
    }

    public void setFicha(String ficha) {
        this.ficha = ficha;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFichainfo() {
        return fichainfo;
    }

    public void setFichainfo(String fichainfo) {
        this.fichainfo = fichainfo;
    }

    @Override
    public String toString() {
        return this.cod;
    }
}