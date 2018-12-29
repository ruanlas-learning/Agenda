package com.example.ruan.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruan.agenda.modelo.Prova;

import java.util.Arrays;
import java.util.List;

public class ListaProvasFragment extends Fragment {

    private ListView lst_view_lista_provas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosPort = Arrays.asList("Sugeito", "Objeto direto", "Objeto indireto");
        Prova provaPortugues = new Prova("Português", "25/05/2016", topicosPort);

        List<String> topicosMat = Arrays.asList("Equações do segundo grau", "Trigonometria");
        Prova provaMatematica = new Prova("Matemática", "27/05/2016", topicosMat);

        List<Prova> listaProvas = Arrays.asList(provaMatematica, provaPortugues);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, listaProvas);

        lst_view_lista_provas = (ListView)view.findViewById(R.id.lst_view_lista_provas);
        lst_view_lista_provas.setAdapter(adapter);
        lst_view_lista_provas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou na prova de " + prova, Toast.LENGTH_SHORT).show();

                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);
//                Intent intentDetalhes = new Intent(getContext(), DetalhesProvaActivity.class);
//                intentDetalhes.putExtra("prova", prova);
//                startActivity(intentDetalhes);
            }
        });

        return view;
    }
}
