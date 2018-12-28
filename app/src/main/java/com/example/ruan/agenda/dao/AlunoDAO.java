package com.example.ruan.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.ruan.agenda.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "agenda";
    private static final int DATABASE_VERSION = 2;

    public AlunoDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE alunos (" +
                            "id INTEGER PRIMARY KEY," +
                            "nome TEXT NOT NULL," +
                            "endereco TEXT," +
                            "telefone TEXT," +
                            "site TEXT," +
                            "nota REAL," +
                            "caminhoFoto TEXT" +
                    ");";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // O trecho abaixo é indicado somente quando o app estiver em desenvolvimento e não tiver sido
//        // lançado em prod ainda. Quando o app tiver em prod, o ideal a ser feito é uma atualização
//        // do schema do banco de dados para não perder os dados que foram registrados na versão
//        // anterior do banco de dados.
//        String sql = "DROP TABLE IF EXISTS alunos";
//        db.execSQL(sql);
//        onCreate(db);
        String sql = "";
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE alunos ADD COLUMN caminhoFoto TEXT";
                db.execSQL(sql);
        }
    }

    public void insere(Aluno aluno) {

        ContentValues alunoContent = extractAlunoToContentValues(aluno);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("alunos", null, alunoContent);
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        List<Aluno> alunoList = new ArrayList<Aluno>();
        while (cursor.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));

            alunoList.add(aluno);
        }
        cursor.close();

        return alunoList;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = { aluno.getId().toString() };
        db.delete("alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        ContentValues alunoContent = extractAlunoToContentValues(aluno);

        String[] params = { aluno.getId().toString() };

        SQLiteDatabase db = getWritableDatabase();
        db.update("alunos", alunoContent, "id = ?", params);
    }

    @NonNull
    private ContentValues extractAlunoToContentValues(Aluno aluno) {
        ContentValues alunoContent = new ContentValues();
        alunoContent.put("nome", aluno.getNome());
        alunoContent.put("endereco", aluno.getEndereco());
        alunoContent.put("telefone", aluno.getTelefone());
        alunoContent.put("site", aluno.getSite());
        alunoContent.put("nota", aluno.getNota());
        alunoContent.put("caminhoFoto", aluno.getCaminhoFoto());
        return alunoContent;
    }

    public boolean ehAluno(String telefone){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alunos WHERE telefone = ?", new String[]{telefone});
        boolean hasAluno = (cursor.getCount() > 0);
        cursor.close();

        return hasAluno;
    }
}
