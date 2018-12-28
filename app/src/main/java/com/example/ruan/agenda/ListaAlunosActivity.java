package com.example.ruan.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ruan.agenda.dao.AlunoDAO;
import com.example.ruan.agenda.modelo.Aluno;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_LIGACAO_TELEFONICA = 123;
    private ListView lista_alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        lista_alunos = (ListView) findViewById(R.id.lista_alunos);
        registerForContextMenu(lista_alunos);
        lista_alunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
//                Aluno aluno = (Aluno)lista_alunos.getItemAtPosition(position);
                Aluno aluno = (Aluno)lista.getItemAtPosition(position);
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intent.putExtra("aluno", aluno);
                startActivity(intent);
//                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();
            }
        });
        // uma opção para clique longo
//        lista_alunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ListaAlunosActivity.this, "Clique Longo ", Toast.LENGTH_SHORT).show();
////                return true; // com true, ele informa que somente este evento poderá tratar o clique longo,
////                             // com false, ele executa e passa para outro método (se existir) também tratar
//                return false;
//            }
//        });

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.context_menu_lista_alunos, menu);

        // outra forma de criar menu de contexto

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) lista_alunos.getItemAtPosition(info.position);
//////////////////////////////////////////////////////////////////////////////////////////
        MenuItem menuItemDeletar = menu.add("Deletar");
        menuItemDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlunoDAO alunoDAO = new AlunoDAO(ListaAlunosActivity.this);
                alunoDAO.deleta(aluno);
                alunoDAO.close();

                carregaLista();
//                Toast.makeText(ListaAlunosActivity.this, "Deletar o aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
//////////////////////////////////////////////////////////////////////////////////////////
        MenuItem menuItemVisitarSite = menu.add("Visitar site");
        //Estamos passando para o android que queremos abrir(visualizar) alguma coisa (que pode
        // ser o browser, player, sms, etc)
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = aluno.getSite();
        // é necessário o site começar com o protocolo "http://" para o android saber que ele deve
        // abrir o navegador para acessar o recurso
        if (!site.startsWith("http://")){
            site = "http://" + site;
        }
        intentSite.setData( Uri.parse( site ) );
        menuItemVisitarSite.setIntent(intentSite);
///////////////////////////////////////////////////////////////////////////////////////////
        MenuItem menuItemEnviarSMS = menu.add("enviarSMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        // é necessário o telefone começar com o protocolo "sms:" para o android saber que ele deve
        // abrir a tela para enviar SMS
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone() ));
        menuItemEnviarSMS.setIntent(intentSMS);
/////////////////////////////////////////////////////////////////////////////////////////
        MenuItem menuItemVisualizarNoMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        // é necessário as coordenadas do endereço começar com o protocolo "geo:" para o android
        // saber que deve abrir o mapa. As coordenadas que devem ser passadas após o protocolo "geo:"
        // são as altitude e longitude (x,y). Ficará mais ou menos assim "geo:0,0"
        //
        // Quando não se sabe as coordenadas do endereço, pode-se pedir para o google localizar
        // passando o parâmetro ["geo:0,0?q=" + endereço], que assim ele já abrirá o mapa no local
        // correto
        //
        // Adicionando o parâmetro "z=14&", informamos o zoom que queremos que seja adicionado
        // no mapa para visualizarmos. ex: [ "geo:0,0?z=14&q=" + endereco ]
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        menuItemVisualizarNoMapa.setIntent(intentMapa);
//////////////////////////////////////////////////////////////////////////////////////////
        MenuItem menuItemLigar = menu.add("Ligar");
        menuItemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // para realizar a ligação, devemos passar para o android a ação de ligar
                Intent intentLigar = new Intent(Intent.ACTION_CALL);
                // é necessário o número do telefone começar com o protocolo "tel:" para o android
                // fazer a ligação. É necessário também incluir a permissão android.permission.CALL_PHONE
                // no AndroidManifest.xml do aplicativo para que este app tenha permissão para fazer ligação
                intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                // é necessárrio verificar neste trecho se o usuário já deu a permissão de ligação para o app,
                // e caso ele não concedeu, solicitar a permissão
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            ListaAlunosActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_CODE_LIGACAO_TELEFONICA
                    );
                    // Depois de solicitar a permissão para o usuário, é chamado o método
                    // "onRequestPermissionsResult()". Para identificar onde e quem pediu a permissão
                    // e consequentemente quem chamou o método, é passado o parâmetro "requestCode"
                    // para identificar e distinguir os diversos requests (para cada request deve ser
                    // passado um requestCode diferente)
                }
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(intentLigar);
                }

                return false;
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // request code da solicitação de permissão da chamada telefônica
        if (requestCode == REQUEST_CODE_LIGACAO_TELEFONICA){
            Toast.makeText(this, "Request Code: " + requestCode, Toast.LENGTH_SHORT).show();
        }
    }


    //    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        item.getMenuInfo();
//      // utilizamos este método para saber qual item do menu foi clicado quando
//      // utilizamos arquivo xml para os menus, pois temos o id dos itens de menu
//        if (item.getItemId() == R.id.context_menu_delete);
//        if (item.getItemId() == R.id.context_menu_edit);
//        return super.onContextItemSelected(item);
//    }
}
