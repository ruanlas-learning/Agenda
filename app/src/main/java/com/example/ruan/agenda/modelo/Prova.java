package com.example.ruan.agenda.modelo;

import java.io.Serializable;
import java.util.List;

public class Prova implements Serializable {
    private String materia;
    private String data;
    private List<String> topicos;

    public Prova(String materia, String data, List<String> topicos) {
        this.materia = materia;
        this.data = data;
        this.topicos = topicos;
    }

    public Prova() {

    }

    public String getMateria() {
        return materia;
    }

    public Prova setMateria(String materia) {
        this.materia = materia;
        return this;
    }

    public String getData() {
        return data;
    }

    public Prova setData(String data) {
        this.data = data;
        return this;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    public Prova setTopicos(List<String> topicos) {
        this.topicos = topicos;
        return this;
    }

    @Override
    public String toString() {
        return this.materia;
    }
}
