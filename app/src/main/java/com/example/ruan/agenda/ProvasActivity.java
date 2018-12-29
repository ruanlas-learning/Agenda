package com.example.ruan.agenda;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruan.agenda.modelo.Prova;

import java.util.Arrays;
import java.util.List;

public class ProvasActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_principal, new ListaProvasFragment());

        if (estaNoModoPaisagem()){
            ft.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        ft.commit();
    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modo_paisagem);
    }

    public void selecionaProva(Prova prova) {
        if (!estaNoModoPaisagem()){
            FragmentTransaction ft = fragmentManager.beginTransaction();

            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();

            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);

            detalhesProvaFragment.setArguments(parametros);

            ft.replace(R.id.frame_principal, detalhesProvaFragment);
            // este método faz com que o botão de voltar do aparelho volte para o fragment de
            // listagem de provas, ao invés de voltar para a listagem de alunos.
            // Este método permite que seja adicionado uma transação na pilha de "Activity" abertas
            // para que seja possível voltar para a listagem de provas
            ft.addToBackStack(null);

            ft.commit();
        }else {
            DetalhesProvaFragment detalhesProvaFragment =
                    (DetalhesProvaFragment) fragmentManager.findFragmentById(R.id.frame_secundario);
            detalhesProvaFragment.populaCamposCom(prova);
        }
    }
}
