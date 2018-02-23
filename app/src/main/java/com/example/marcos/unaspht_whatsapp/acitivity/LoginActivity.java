package com.example.marcos.unaspht_whatsapp.acitivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marcos.unaspht_whatsapp.R;
import com.example.marcos.unaspht_whatsapp.config.ConfiguracaoFirebase;
import com.example.marcos.unaspht_whatsapp.helper.AlertDialogClass;
import com.example.marcos.unaspht_whatsapp.helper.Base64Custom;
import com.example.marcos.unaspht_whatsapp.helper.Preferencias;
import com.example.marcos.unaspht_whatsapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private AlertDialogClass mostrarAlerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        veriFicarUsuarioLogado();

        email = findViewById(R.id.edit_login_email);
        senha = findViewById(R.id.edit_login_senha);
        botaoLogar = findViewById(R.id.bt_logar);
        mostrarAlerta = new AlertDialogClass(this);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });

    }

    private void veriFicarUsuarioLogado(){

        /*caso o usuário esteja logado, ao utilizar o metodo getCurrentUser() nós vamos recuperar um objeto: o usuário que está
        logado no sistema.
        Caso ao recuperar o objeto ele for nulo, nada será feito, mase se for diferente de nulo, iremos mandá-lo para a tela principal*/
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private void validarLogin(){

        try {
            if (email.getText().toString().equals("") || senha.getText().toString().equals("") ) {
                mostrarAlerta.showText("Campo imcompleto", "Verifique se todos os campos estão preenchidos corretamente");
            }else {
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signInWithEmailAndPassword(
                        usuario.getEmail(),
                        usuario.getSenha()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //Se der certo o processo de autenticacao ele vai cair dentro desse if
                        if (task.isSuccessful()) {

                            //Salva os dados do usuario logado diretamente no próprio dispositivo
                            Preferencias preferencias = new Preferencias(LoginActivity.this);
                            String identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());
                            preferencias.salvarDados((identificadorUsuarioLogado));

                            abrirTelaPrincipal();
                            //Toast.makeText(LoginActivity.this, "Bem-vindo ao UNASP Notícias", Toast.LENGTH_LONG);
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                senha.setError("Senha incorreta");
                            } catch (FirebaseAuthInvalidUserException e) {
                                email.setError("Por favor, verifique se o e-mail está correto");
                            } catch (Exception e) {
                                mostrarAlerta.showText("Erro ao logar", "Verifique se todos os campos foram preenchidos corretamente.");
                            }
                        }
                    }
                });
            }
        }catch (Exception e){
            mostrarAlerta.showText("Erro de conexão", "Verifique a sua conexão com a internet");
        }
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view){

        //abrirCadsatroUsuario está no xml do acivity_login no botão cadastrar
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }
}
