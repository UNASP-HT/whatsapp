package com.example.marcos.unaspht_whatsapp.acitivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcos.unaspht_whatsapp.R;
import com.example.marcos.unaspht_whatsapp.config.ConfiguracaoFirebase;
import com.example.marcos.unaspht_whatsapp.helper.AlertDialogClass;
import com.example.marcos.unaspht_whatsapp.helper.Permissao;
import com.example.marcos.unaspht_whatsapp.helper.Preferencias;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

public class LoginActivity_SMS extends AppCompatActivity {

    private EditText telefone;
    private EditText codPais;
    private EditText codArea;
    private Button cadastrar;
    private DatabaseReference referenciaFirebase;
    private AlertDialogClass mostrarAlerta;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sms);

        Permissao.validaPermissoes(1,this, permissoesNecessarias);

        telefone = findViewById(R.id.edit_telefone_SMS);
        codPais = findViewById(R.id.edit_cod_pais_SMS);
        codArea = findViewById(R.id.edit_cod_area_SMS);
        cadastrar = findViewById(R.id.bt_cadastrar_SMS);
        referenciaFirebase = ConfiguracaoFirebase.getFirebase();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String nomeUsuario = nome.getText().toString();

                if(codArea.getText().toString().equals("") || telefone.getText().toString().equals("") || telefone.getText().toString().length() < 8) {

                    mostrarAlerta.setButtonMeth(new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            codArea.setText("");
                            telefone.setText("");
                            codPais.setText("+55");
                        }
                    });

                    mostrarAlerta.showText("Erro ao preencher os campos", "Verifique se todos os campos foram preenchidos corretamente.");

                }else{
                    String telefoneCompleto =
                            codPais.getText().toString() +
                                    codArea.getText().toString() +
                                    telefone.getText().toString();

                    //Gerar token
                    Random randomico = new Random();
                    int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;
                    String token = String.valueOf( numeroRandomico );
                    String mensagemEnvio = "UNASP-HT Código de Confirmação: " + token;

                    //salvar os dados para validação
                    Preferencias preferencias = new Preferencias(LoginActivity_SMS.this);
                    preferencias.salvarUsuariosPreferencias(telefoneCompleto, token);

                    //Envio de SMS
//                    telefoneCompleto = "8135";
                    boolean enviadoSMS = enviaSMS(telefoneCompleto, mensagemEnvio);

                    if ( enviadoSMS) {
                        //enviar para a tela de autentificação
                        Intent intent = new Intent (LoginActivity_SMS.this, AvalidadorActivity_SMS.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(LoginActivity_SMS.this, "Problema ao enviar o SMS, por favor tenta novamente", Toast.LENGTH_LONG).show();
                    }


//                HashMap<String, String> usuario = preferencias.getDadousuario();
//                Log.i("TOKEN", "T: " + usuario.get("token") + "NOME: " + usuario.get("nome") + "TELEFONE: " + usuario.get("telefone"));
                }
            }
        });
    }
    //Envio do SMS
    private boolean enviaSMS(String telefone, String mensagem){

        try {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int resultado : grantResults){
            if (resultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }
    private void alertaValidacaoPermissao(){

        mostrarAlerta.setButtonMeth(new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        mostrarAlerta.showText("Permissão Negada", "Para utilizar esse aplicativo é necessário aceitar as permissões.");
    }
}