package com.example.marcos.unaspht_whatsapp.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText senha2;
    private Button botaoCadastrar;
    private Usuario usuario;
    private AlertDialogClass mostrarErro;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        mostrarErro = new AlertDialogClass(this);

        try {
            nome = findViewById(R.id.edit_cadastro_nome);
            email = findViewById(R.id.edit_login_email);
            senha = findViewById(R.id.edit_login_senha);
            senha2 = findViewById(R.id.edit_cadastro_senha2);
            botaoCadastrar = findViewById(R.id.bt_logar);

            botaoCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usuario = new Usuario();
                    usuario.setNome(nome.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    if (senha.getText().toString().equals(senha2.getText().toString())) {
                        usuario.setSenha(senha.getText().toString());
                        cadastrarUsuario();
                    }
                    else{
                        senha2.setError("Senhas não conferem");
                    }
                }
            });
        }catch (Exception e){
            mostrarErro.showText("Erro inesperado", "Por favor passe no CTI para informações sobre o erro: " + e);
        }
    }

    private void cadastrarUsuario() {
        try {
            if (email.getText().toString().equals("") || nome.getText().toString().equals("") || senha.getText().toString().equals("") ){
                mostrarErro.showText("Campo imcompleto", "Verifique se todos os campos estão preenchidos corretamente");
            }else{

                //instanciar a autenticacao do firebase para o login com email e senha
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.createUserWithEmailAndPassword(
                        usuario.getEmail(),
                        usuario.getSenha()
                ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuário", Toast.LENGTH_LONG).show();

                            String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                            usuario.setId(identificadorUsuario);
                            usuario.salvar();

                            //Salva os dados do usuario logado diretamente no próprio dispositivo
                            Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
                            preferencias.salvarDados((identificadorUsuario));

                            abrirLoginUsuario();

//                            //para fechar a atividade e logar novamente
//                            autenticacao.signOut();
//                            finish();

                        } else {

                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e){
                                senha.setError("A senha deve ter no mínimo 6 dígitos");
                            } catch (FirebaseAuthUserCollisionException e) {
                                email.setError("O e-mail já está cadastrado");
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                email.setError("E-mail inválido");
                            } catch (Exception e) {
                                mostrarErro.showText("Erro","Falha em criar usuario: "+task.getException().getMessage());
                            }
                        }
                    }
                });
            }
        }
        catch (Exception e){
            mostrarErro.showText("Erro de conexão", "Verifique a sua conexão com a internet");
        }
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
