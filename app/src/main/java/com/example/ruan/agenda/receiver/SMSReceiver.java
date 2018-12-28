package com.example.ruan.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.ruan.agenda.R;
import com.example.ruan.agenda.dao.AlunoDAO;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[])intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage smsMessage = SmsMessage.createFromPdu(pdu, formato);

        String telefone = smsMessage.getDisplayOriginatingAddress();

        AlunoDAO alunoDAO = new AlunoDAO(context);
        if (alunoDAO.ehAluno(telefone)){
            Toast.makeText(context, "Chegou um SMS!", Toast.LENGTH_LONG).show();
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.msg);
            mediaPlayer.start();
        }
        alunoDAO.close();
    }
}
