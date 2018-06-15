package com.example.jjsimon.proyectodam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Esta pantalla permite al usuario loguearse en la aplicacion
 */
public class PantallaLogin extends AppCompatActivity {
    //View
    EditText mailET;
    EditText pwdET;
    Button loginBT;
    TextView crearCuenta;

    //Componentes FireBAse
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializo los componentes
        mailET = (EditText) findViewById(R.id.mail_et_wlog);
        pwdET = (EditText) findViewById(R.id.pwd_et_wlog);
        loginBT = (Button) findViewById(R.id.entrar_bt_wlog);
        crearCuenta = (TextView) findViewById(R.id.crearCuenta_tv_wlog);

        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null) {
                    Log.w("SESION", "El usuario a iniciado sesion " + user.getEmail());
                    finish();
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                }else {
                    Log.w("SESION", "El usuario a cerrado la sesion ");
                }
            }
        });


        //Añado el OnClick al texto de crear cuenta
        crearCuenta.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    abrirCrearCuenta();
            }
        });

        //Añado el OnClick al boton de iniciar sesion
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Comprueba que el campo contraseña y el campo email esten completos
                if(!mailET.getText().toString().equals("") && !pwdET.getText().toString().equals("")) {
                    //Si los campos se han completado llamo al metodo que se encarga de iniciar sesion
                    iniciarSesion();
                } else {
                    //Si no se han completado muestro un dialogo para indicar que faltan campos
                    AlertDialog.Builder builder = new AlertDialog.Builder(PantallaLogin.this);

                    builder.setTitle(R.string.faltan_campos_titulo)
                            .setMessage(R.string.faltan_campos_msj)
                            .setPositiveButton(R.string.aceptar,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                    builder.create().show();
                }
            }
        });
    }//Fin OnCreate

    /**
     *Este metodo abre la pantalla de crear cuenta es llamado cuando se pulsa el texto de crear cuenta
     */
    public void abrirCrearCuenta(){
        Intent intent = new Intent(this, PantallaCrearCuenta.class);
        startActivityForResult(intent, 0);
    }

    /**
     *Este metodo se encarga de iniciar sesion en firebase,
     *es llamado cuando se pulsa el boton de iniciar sesion
     */
    public void iniciarSesion(){
        String mail = mailET.getText()+"";
        String pwd = pwdET.getText()+"";


        auth.signInWithEmailAndPassword(mail, pwd)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //En caso de que se haya podido iniciar sesion se muestra un toast informando al usuario, se cambia la preferencia y se finaliza la activvidad
                if (task.isSuccessful()) {
                    Toast.makeText(PantallaLogin.this, "Sesion iniciada", Toast.LENGTH_LONG).show();
                    //Creo el editor para las preferencias
                    startActivity(new Intent(PantallaLogin.this, MainActivity.class));
                    finish();
                //En caso de no poder iniciar sesion se muestra un toast informando al usuario
                }else {
                    Toast.makeText(PantallaLogin.this, "No se ha podido iniciar sesion, intentelo mas tarde", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


