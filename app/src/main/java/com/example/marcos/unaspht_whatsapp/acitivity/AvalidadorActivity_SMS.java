package com.example.marcos.unaspht_whatsapp.acitivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marcos.unaspht_whatsapp.R;
import com.example.marcos.unaspht_whatsapp.helper.AlertDialogClass;
import com.example.marcos.unaspht_whatsapp.helper.Preferencias;

import java.util.HashMap;

public class AvalidadorActivity_SMS extends AppCompatActivity {

    private EditText codigoValidacao;
    private Button validar;
    private AlertDialogClass mostrarMensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avalidador_sms);

        codigoValidacao = findViewById(R.id.edit_cod_validacao);
        validar = findViewById(R.id.bt_validar);
        mostrarMensagem = new AlertDialogClass(this);

        validar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                //Recuperar dados das preferencias do usuario
                Preferencias preferencias = new Preferencias(AvalidadorActivity_SMS.this);
                HashMap<String, String> usuario = preferencias.getDadousuario();

                String tokenGerado = usuario.get("token");
                //recuperar o token digitado
                String tokenDigitado = codigoValidacao.getText().toString();

                if ( tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(AvalidadorActivity_SMS.this, "Token validado com sucesso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent (AvalidadorActivity_SMS.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    mostrarMensagem.showText("Erro de validação", "Token incorreto, por favor tente novamente");
                }
            }
        });
    }
}
