package com.example.ruan.agenda;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ruan.agenda.modelo.Prova;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesProvaFragment extends Fragment {

    private ListView lst_view_detalhes_prova_topicos;
    private TextView txt_detalhes_prova_materia, txt_detalhes_prova_data;

    public DetalhesProvaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        lst_view_detalhes_prova_topicos = (ListView)view.findViewById(R.id.lst_view_detalhes_prova_topicos);
        txt_detalhes_prova_data = (TextView)view.findViewById(R.id.txt_detalhes_prova_data);
        txt_detalhes_prova_materia = (TextView)view.findViewById(R.id.txt_detalhes_prova_materia);

        Bundle parametros = getArguments();

        if (parametros != null){
            Prova prova = (Prova) parametros.getSerializable("prova");
            populaCamposCom(prova);
        }

        return view;
    }

    public void populaCamposCom(Prova prova){

        txt_detalhes_prova_materia.setText(prova.getMateria());
        txt_detalhes_prova_data.setText(prova.getData());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());
        lst_view_detalhes_prova_topicos.setAdapter(adapter);
    }

}
