package com.example.ruan.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.ruan.agenda.dao.AlunoDAO;
import com.example.ruan.agenda.modelo.Aluno;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        // este método irá preparar uma instância do Google Maps para podermos manipular o mapa, como
        // dezenhar, colocar pinos, etc.
        // Este método espera um parâmetro de um objeto que será responsável por tratar o
        // mapa quando estiver pronto
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng posicaoDaEscola = pegaCoordenadaDoEndereco("Rua Mercídio Pazelli 150, São Luiz, Valinhos - São Paulo");
//        LatLng posicaoDaEscola = pegaCoordenadaDoEndereco("Rua Mercídio Pazelli 750, São Luiz, Valinhos - São Paulo, 13272706");
//        LatLng posicaoDaEscola = pegaCoordenadaDoEndereco("13272706, 750");
        if (posicaoDaEscola != null){

            centralizaMapaNasCoordenadas(posicaoDaEscola);

//            new Localizador(getContext(), googleMap);


//// Está marcando no mapa o endereço da escola
//
//            MarkerOptions marcadorEscola = new MarkerOptions();
//            marcadorEscola.position(posicaoDaEscola);
//            marcadorEscola.title("Meu endereço");
//            marcadorEscola.snippet("Home");
//            googleMap.addMarker(marcadorEscola);
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        List<Aluno> alunoList = alunoDAO.buscaAlunos();
        alunoDAO.close();
        for (Aluno aluno : alunoList){
            LatLng coordenada = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if (coordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }

    }

    public void centralizaMapaNasCoordenadas(LatLng coordenada){
        // o primeiro parâmetro é a posição da localização, e o segundo é a quantidade de zoom
        // do google maps. Quanto maior o zoom, mais próximo você está
        float nivelZoom = 17;
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coordenada, nivelZoom);
        googleMap.moveCamera(update);
//        MarkerOptions marcador = new MarkerOptions();
//        marcador.position(coordenada);
//        marcador.title("MEU LOCAL");
//        googleMap.addMarker(marcador);
    }

    private LatLng pegaCoordenadaDoEndereco(String endereco){
        try {
            Geocoder geocoder = new Geocoder(getContext());
            int quantidadeMaximaDeResultadosEncontrados = 1;
            List<Address> resultados = geocoder.
                    getFromLocationName(endereco, quantidadeMaximaDeResultadosEncontrados);

            if ( !resultados.isEmpty() ){
                double latitude = resultados.get(0).getLatitude();
                double longitude = resultados.get(0).getLongitude();
                LatLng posicao = new LatLng(latitude, longitude);
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
