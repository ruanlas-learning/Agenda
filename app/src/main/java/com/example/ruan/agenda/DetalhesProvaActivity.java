package com.example.ruan.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ruan.agenda.modelo.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    private ListView lst_view_detalhes_prova_topicos;
    private TextView txt_detalhes_prova_materia, txt_detalhes_prova_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        lst_view_detalhes_prova_topicos = (ListView)findViewById(R.id.lst_view_detalhes_prova_topicos);
        txt_detalhes_prova_data = (TextView)findViewById(R.id.txt_detalhes_prova_data);
        txt_detalhes_prova_materia = (TextView)findViewById(R.id.txt_detalhes_prova_materia);

        Intent intent = getIntent();
        Prova prova = (Prova)intent.getSerializableExtra("prova");

        txt_detalhes_prova_materia.setText(prova.getMateria());
        txt_detalhes_prova_data.setText(prova.getData());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getTopicos());
        lst_view_detalhes_prova_topicos.setAdapter(adapter);
    }
}
