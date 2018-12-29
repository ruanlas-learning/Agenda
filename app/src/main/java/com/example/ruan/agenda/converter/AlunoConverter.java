package com.example.ruan.agenda.converter;

import com.example.ruan.agenda.modelo.Aluno;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

public class AlunoConverter {
    public String converterParaJSON(List<Aluno> alunoList) {
        JSONStringer jsonStringer = new JSONStringer();

        try {
            jsonStringer.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : alunoList){
                jsonStringer.object().key("nome").value(aluno.getNome());
                jsonStringer.key("nota").value(aluno.getNota());
                jsonStringer.endObject();
            }
            jsonStringer.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonStringer.toString();
    }
}
