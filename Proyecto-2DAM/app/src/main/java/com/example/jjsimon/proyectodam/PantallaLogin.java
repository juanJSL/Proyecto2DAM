package com.example.jjsimon.proyectodam;

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

public class PantallaLogin extends AppCompatActivity {
    //View
    EditText mailET;
    EditText pwdET;
    Button loginBT;
    TextView crearCuenta;

    //Componentes FireBAse
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login);

        //Inicializo los componentes
        mailET = (EditText) findViewById(R.id.mail_et_wlog);
        pwdET = (EditText) findViewById(R.id.pwd_et_wlog);
        loginBT = (Button) findViewById(R.id.entrar_bt_wlog);
        crearCuenta = (TextView) findViewById(R.id.crearCuenta_tv_wlog);

        //Instancio el Listener
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                    Log.w("SESION", "El usuario a iniciado sesion" + user.getEmail());
                else
                    Log.w("SESION", "El usuario a cerrado la sesion");
            }
        };


        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(authStateListener);



        crearCuenta.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCrearCuenta();
            }
        });
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
    }


    /**
     *
     */
    public void abrirCrearCuenta(){
        startActivity(new Intent(this, PantallaCrearCuenta.class));
    }

    /**
     *
     */
    public void iniciarSesion(){
        String mail = mailET.getText()+"";
        String pwd = pwdET.getText()+"";

        auth.signInWithEmailAndPassword(mail, pwd)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(PantallaLogin.this, "Sesion iniciada", Toast.LENGTH_LONG).show();
                    Log.w("SESION", "Sesion iniciada");
                }else {
                    Toast.makeText(PantallaLogin.this, "No se ha podido iniciar sesion", Toast.LENGTH_LONG).show();
                    Log.w("SESION", "No se ha podido iniciar sesion");
                }
            }
        });
    }
}


