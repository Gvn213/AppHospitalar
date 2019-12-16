package com.example.patriciasantos.apphospitalar.TransferenciasPasta;

public class Transferencia {
    private String paciente;
    private String data;
    private String hospital;

    public Transferencia() {
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    @Override
    public String toString() {
        return this.data;
    }
}
