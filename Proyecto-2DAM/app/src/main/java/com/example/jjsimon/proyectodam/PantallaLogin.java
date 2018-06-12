package com.example.jjsimon.proyectodam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjsimon.proyectodam.Referencias.PreferenciasReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 *
 * COMPROBAR QUE LOS CAMPOS ESTAN COMPLETADOS
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 * **********************************************
 */
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

        //comprobarLogin();

        //Compruebo si el usuario esta logueado si no esta logueado abro la pantalla de login
        /*if(isLogueado()){
           Log.w("PREFERENCIAS", "Abro pantalla login");
          startActivity(new Intent(this, MainActivity.class));
        }else
           Log.w("PREFERENCIAS", "El usuario no esta logueado");
*/
        //Inicializo los componentes
        mailET = (EditText) findViewById(R.id.mail_et_wlog);
        pwdET = (EditText) findViewById(R.id.pwd_et_wlog);
        loginBT = (Button) findViewById(R.id.entrar_bt_wlog);
        crearCuenta = (TextView) findViewById(R.id.crearCuenta_tv_wlog);

        /**
         *Instancio el listener para comprobar si el usuario ya ha iniciado sesion
         * en el caso de que haya iniciado sesion lanzo la actividad principal
         */
        authStateListener = new FirebaseAuth.AuthStateListener() {
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
        };


        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(authStateListener);


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
                iniciarSesion();
            }
        });
    }


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
                    Log.w("SESION", "Sesion iniciada");

                //En caso de no poder iniciar sesion se muestra un toast informando al usuario
                }else {
                    Toast.makeText(PantallaLogin.this, "No se ha podido iniciar sesion", Toast.LENGTH_LONG).show();
                    Log.w("SESION", "No se ha podido iniciar sesion");
                }
            }
        });
    }
}


