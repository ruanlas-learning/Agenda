package com.example.ruan.agenda;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final GoogleApiClient apiClient;
    private MapaFragment mapaFragment;

//    private final GoogleMap mapa;

//    public Localizador(Context context, GoogleMap mapa) {
//        apiClient = new GoogleApiClient.Builder(context)
//                // foi necessário incluir na dependência do
//                // gradle [ implementation 'com.google.android.gms:play-services-location:16.0.0' ]
//                // para ser possível utilizar o LocationServices.API
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this) // este método informa um objeto de callback para
//                // tratar quando uma conexão for concluída
//                .build();
//
//        // este método funciona de forma assíncrona
//        apiClient.connect();
//
//        this.mapa = mapa;
//        this.context = context;
//    }

    public Localizador(Context context, MapaFragment mapaFragment) {
        apiClient = new GoogleApiClient.Builder(context)
                // foi necessário incluir na dependência do
                // gradle [ implementation 'com.google.android.gms:play-services-location:16.0.0' ]
                // para ser possível utilizar o LocationServices.API
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this) // este método informa um objeto de callback para
                // tratar quando uma conexão for concluída
                .build();

        // este método funciona de forma assíncrona
        apiClient.connect();

        this.mapaFragment = mapaFragment;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest();

        // estamos especificando que o localizador só irá mandar atualização da posição se caso
        // identificar um descolamento de 50 metros
        request.setSmallestDisplacement(50);
        // estamos especificando que o localizador mandará atualização a cada 1000 milissegundos
        // se caso a condição de deslocamento for atendida (deslocamento > 50m e intervalo = 1s)
        request.setInterval(1000);
        // estamos definindo a precisão da localização. Estamos definindo alta precisão (o que
        // fará gastar um pouco mais de energia da bateria)
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // o método abaixo fica obtendo a localização do aparelho. Espera-se três parâmetros:
        // o primeiro, é um objeto do tipo GoogleApiClient, o outro é do tipo LocationRequest, e
        // o último, é um objeto do tipo LocationListener que será responsável por receber a
        // atualização de posição e utilizá-lo
        LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, request, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(coordenada);
//        mapa.moveCamera(cameraUpdate);
        mapaFragment.centralizaMapaNasCoordenadas(coordenada);
    }
}
