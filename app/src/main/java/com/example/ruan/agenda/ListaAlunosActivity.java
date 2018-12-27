package com.example.ruan.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ruan.agenda.dao.AlunoDAO;
import com.example.ruan.agenda.modelo.Aluno;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Button btn_novo_aluno = (Button)findViewById(R.id.btn_novo_aluno);
        btn_novo_aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
    }

    private void carregaLista() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunoList = alunoDAO.buscaAlunos();
        alunoDAO.close();

//        String[] alunos = {"Daniel", "Ronaldo", "Jeferson", "Felipe"};
        ListView lista_alunos = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunoList);
        lista_alunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        //ciclo de vida de uma nova activity: OnCreate => OnStart => OnResume => [ Activity Rodando ]
        // Activity pausada: OnPause => OnResume [ Activity Rodando ]
        // Activity parada: OnStop => OnResume => OnStart => OnResume [ Activity Rodando ]
        super.onResume();
        carregaLista();
    }
}
