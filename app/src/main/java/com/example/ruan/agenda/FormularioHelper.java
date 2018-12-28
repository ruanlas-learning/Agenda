package com.example.ruan.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.ruan.agenda.modelo.Aluno;

public class FormularioHelper {

    private EditText edt_formulario_nome, edt_formulario_endereco,
            edt_formulario_telefone, edt_formulario_site;
    private RatingBar rt_bar_formulario_nota;
    private ImageView img_view_formulario_foto;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){
        edt_formulario_nome = (EditText)activity.findViewById(R.id.edt_formulario_nome);
        edt_formulario_endereco = (EditText)activity.findViewById(R.id.edt_formulario_endereco);
        edt_formulario_telefone = (EditText)activity.findViewById(R.id.edt_formulario_telefone);
        edt_formulario_site = (EditText)activity.findViewById(R.id.edt_formulario_site);

        rt_bar_formulario_nota = (RatingBar)activity.findViewById(R.id.rt_bar_formulario_nota);
        img_view_formulario_foto = (ImageView) activity.findViewById(R.id.img_view_formulario_foto);

//        aluno = new Aluno();
    }

    public Aluno pegaAluno() {
//        Aluno aluno = new Aluno();
        if (aluno == null){
            aluno = new Aluno();
        }
        aluno.setNome(edt_formulario_nome.getText().toString());
        aluno.setEndereco(edt_formulario_endereco.getText().toString());
        aluno.setTelefone(edt_formulario_telefone.getText().toString());
        aluno.setSite(edt_formulario_site.getText().toString());
        aluno.setNota(Double.valueOf(rt_bar_formulario_nota.getProgress()));
        aluno.setCaminhoFoto((String) img_view_formulario_foto.getTag());

        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        edt_formulario_nome.setText(aluno.getNome());
        edt_formulario_endereco.setText(aluno.getEndereco());
        edt_formulario_telefone.setText(aluno.getTelefone());
        edt_formulario_site.setText(aluno.getSite());

        rt_bar_formulario_nota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());

        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto){
        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduced = Bitmap.createScaledBitmap(bitmap, 300, 300, true);

            img_view_formulario_foto.setImageBitmap(bitmapReduced);
            // irá redimensionar a imagem para ocupar toda a largura
            img_view_formulario_foto.setScaleType(ImageView.ScaleType.FIT_XY);
            // estamos utilizando esta propiedade TAG do ImageView para armazenar o caminho da
            // foto
            img_view_formulario_foto.setTag(caminhoFoto);
        }
    }
}
