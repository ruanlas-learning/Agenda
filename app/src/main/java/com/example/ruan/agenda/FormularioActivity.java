package com.example.ruan.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ruan.agenda.dao.AlunoDAO;
import com.example.ruan.agenda.modelo.Aluno;

import java.io.File;
import java.io.Serializable;

public class FormularioActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CAMERA = 567;
    private FormularioHelper formularioHelper;
    private Button btn_formulario_add_foto;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        formularioHelper = new FormularioHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno)intent.getSerializableExtra("aluno");
        if (aluno != null){
            formularioHelper.preencheFormulario(aluno);
        }

        btn_formulario_add_foto = (Button)findViewById(R.id.btn_formulario_add_foto);
        btn_formulario_add_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // esta intent abaixo está passando para o android que temos a intenção
                // de tirar uma foto (capturar uma imagem com a câmera)
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/"+ System.currentTimeMillis() +".jpg";
                File arquivoFoto = new File(caminhoFoto);
//                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto)); // não funciona no android 7
                String authority = BuildConfig.APPLICATION_ID +".fileprovider";// valor contido no atributo
                // [ android:authorities ] do provider do AndroidManifest
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(FormularioActivity.this, authority, arquivoFoto));
                // este método é utilizado quando inicia-se uma activity e é esperado que ela
                // retorne com algum resultado. Quando este método é invocado para iniciar uma activity,
                // quando ela finaliza, é executado o método "onActivityResult()" logo na sequencia.
                // Devemos passar um requestCode para identificar quem chamou a activity para poder
                // executar ou obter o resultado
                startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
            }
        });

//        Button btn_formulario_salvar = (Button) findViewById(R.id.btn_formulario_salvar);
//        btn_formulario_salvar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(FormularioActivity.this, "Aluno salvo com sucesso!", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Verifica se o usuário cancelou a activity que foi chamada no "startActivityForResult()"
        if (resultCode == Activity.RESULT_OK){
            // verifica quem chamou o método "startActivityForResult()" checando o requestCode
            if (requestCode == REQUEST_CODE_CAMERA){
//            Toast.makeText(this, caminhoFoto, Toast.LENGTH_SHORT).show();
                formularioHelper.carregaImagem(caminhoFoto);


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case R.id.menu_formulario_ok:
                Aluno aluno = formularioHelper.pegaAluno();
                AlunoDAO alunoDAO = new AlunoDAO(this);
                if (aluno.getId() != null){
                    alunoDAO.altera(aluno);
                }else {
                    alunoDAO.insere(aluno);
                }

                alunoDAO.close();
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() +" salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
