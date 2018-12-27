package com.example.ruan.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

import com.example.ruan.agenda.modelo.Aluno;

public class FormularioHelper {

    private EditText edt_formulario_nome, edt_formulario_endereco,
            edt_formulario_telefone, edt_formulario_site;
    private RatingBar rt_bar_formulario_nota;

    public FormularioHelper(FormularioActivity activity){
        edt_formulario_nome = (EditText)activity.findViewById(R.id.edt_formulario_nome);
        edt_formulario_endereco = (EditText)activity.findViewById(R.id.edt_formulario_endereco);
        edt_formulario_telefone = (EditText)activity.findViewById(R.id.edt_formulario_telefone);
        edt_formulario_site = (EditText)activity.findViewById(R.id.edt_formulario_site);

        rt_bar_formulario_nota = (RatingBar)activity.findViewById(R.id.rt_bar_formulario_nota);
    }

    public Aluno pegaAluno() {
        Aluno aluno = new Aluno();

        aluno.setNome(edt_formulario_nome.getText().toString());
        aluno.setEndereco(edt_formulario_endereco.getText().toString());
        aluno.setTelefone(edt_formulario_telefone.getText().toString());
        aluno.setSite(edt_formulario_site.getText().toString());
        aluno.setNota(Double.valueOf(rt_bar_formulario_nota.getProgress()));

        return aluno;
    }
}