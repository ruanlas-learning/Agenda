package com.example.ruan.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) lista_alunos.getItemAtPosition(info.position);

                AlunoDAO alunoDAO = new AlunoDAO(ListaAlunosActivity.this);
                alunoDAO.deleta(aluno);
                alunoDAO.close();

                carregaLista();
//                Toast.makeText(ListaAlunosActivity.this, "Deletar o aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
//        MenuItem editar = menu.add("Editar");


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
