package com.example.ruan.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.ruan.agenda.converter.AlunoConverter;
import com.example.ruan.agenda.dao.AlunoDAO;
import com.example.ruan.agenda.modelo.Aluno;

import java.util.List;

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context){
        this.context = context;
    }

    // Este método será executado em background em uma Thread secundária
    @Override
    protected String doInBackground(Void... aVoid) {
        AlunoDAO alunoDAO = new AlunoDAO(context);
        List<Aluno> alunoList = alunoDAO.buscaAlunos();
        alunoDAO.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJSON(alunoList);

        WebClient client = new WebClient();
        String resposta = client.post(json);

//        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        return resposta;
    }

    // Este método será executado depois do método "doInBackground()" ser executado. O 'Object' que
    // está sendo passado como parâmetro neste método é o mesmo objeto que o método "doInBackground()"
    // está retornando.
    // Este método será executado na Thread primária
    @Override
    protected void onPostExecute(String resposta) {
//        super.onPostExecute(o);
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        dialog.dismiss();
    }

    // Este método é executado antes do método "doInBackground()" ser executado, e é executado na
    // Thread principal
    @Override
    protected void onPreExecute() {
//        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos.....", true, true);
    }
}
