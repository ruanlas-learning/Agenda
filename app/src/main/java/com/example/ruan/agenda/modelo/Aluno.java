package com.example.ruan.agenda.modelo;

import java.io.Serializable;

public class Aluno implements Serializable {
    private Long id;
    private String nome, endereco, telefone, site;
    private Double nota;
    private String caminhoFoto;

    public Long getId() {
        return id;
    }

    public Aluno setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Aluno setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public Aluno setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public Aluno setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public String getSite() {
        return site;
    }

    public Aluno setSite(String site) {
        this.site = site;
        return this;
    }

    public Double getNota() {
        return nota;
    }

    public Aluno setNota(Double nota) {
        this.nota = nota;
        return this;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public Aluno setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
        return this;
    }

    @Override
    public String toString() {
        return getId() + " - " + getNome();
    }
}
