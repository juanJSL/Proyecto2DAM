package com.example.jjsimon.proyectodam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jjsimon.proyectodam.Clases.Jugador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class PantallaCrearCuenta extends AppCompatActivity {
    EditText pwdET;
    EditText repeatPwdET;
    EditText mailET;
    EditText nickET;
    Button btCrearCuenta;
    Spinner listaRoles;

    //Componentes de Firebase
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_crear_cuenta);


        //Inicializo los componentes
        pwdET = (EditText) findViewById(R.id.pwd_et_wcreacu);
        repeatPwdET = (EditText) findViewById(R.id.repetir_pwd_et_wcreacu);
        btCrearCuenta = (Button) findViewById(R.id.crearCuenta_bt_wcreacu);
        mailET = (EditText) findViewById(R.id.mail_et_wcreacu);
        nickET = (EditText) findViewById(R.id.nick_et_wcreacu);
        listaRoles = (Spinner) findViewById(R.id.spinner_roles);

        //Cargo la lista con los roles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.roles_array,android.R.layout.simple_spinner_item);
        listaRoles.setAdapter(adapter);

        //Tambien los de Firebase
        firebaseAuth = FirebaseAuth.getInstance();


        btCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comprobarCampos())//Llamo al metodo que se encarga de comprobar los campos, si todos son correctos se inicia el registro
                    iniciarRegistro();
            }
        });

    }


    /**
     * Este metodo se encarga de comprobar que se han introducido datos y son validos
     *
     * @return true -> DATOS VALIDOS       false -> DATOS NO VALIDOS
     */
    public boolean comprobarCampos() {
        if (nickET.getText().length() > 0 && mailET.getText().length() > 0 && pwdET.getText().length() > 0 && repeatPwdET.getText().length() > 0) {
            if (comprobarCampoMail() && comprobarPwd())
                return true;
            else return false;
        } else {
            Toast.makeText(PantallaCrearCuenta.this, R.string.error_faltan_campos, Toast.LENGTH_LONG).show();
            return false;
        }
    }



    /**
     * Este metodo se encarga de comprobar que las contraseñas coinciden en ambos campos
     *
     * @return true -> COINCIDEN       false -> NO COINCIDEN
     */
    public boolean comprobarPwd() {
        //Guardo el contenido de los EditText que contienen las contraseñas en 2 cadenas auxiliares
        String pwdStr1 = pwdET.getText() + "";
        String pwdStr2 = repeatPwdET.getText() + "";

        if (pwdStr1.length() >= 6) {//Compruebo que la contraseña tiene una longitud de al menos 6 caracteres (Es el minimo que pide firebase)
            //Compruebo si estas 2 cadenas son iguales
            if (pwdStr1.equals(pwdStr2))//Si son iguales devuelvo "true"
                return true;
            else//Si son distintas devuelvo "false"
                Toast.makeText(PantallaCrearCuenta.this, R.string.error_pwd_no_match, Toast.LENGTH_LONG).show();
            return false;
        } else {
            Toast.makeText(PantallaCrearCuenta.this, R.string.error_pwd_corta, Toast.LENGTH_LONG).show();
            return false;
        }
    }//FIN COMPROBAR PWD



    /**
     * Este metodo se encarga de comprobar que el E-mail introducido es validos
     *
     * @return true -> MAIL VALIDO       false -> MAIL NO VALIDO
     */
    public boolean comprobarCampoMail() {
        //Expresion regular para el E-mail
        String emailRegexp = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
        if (Pattern.matches(emailRegexp, mailET.getText()))
            return true;
        else {
            Toast.makeText(PantallaCrearCuenta.this, R.string.error_mail_no_valido, Toast.LENGTH_LONG).show();
            return false;
        }
    }//FIN COMPROBAR MAIL



    /**
     * Este metodo se encarga de crear un usuario con los datos que se han introducido
     * crea un usuario auth y ademas inserta en la base de datos un registro con los datos
     * del usuario
     */
    public void iniciarRegistro() {
        Log.w("INICIAR REGISTRO", "LLAMADO");
        final String correoStr = mailET.getText() + "";
        final String pwdStr = pwdET.getText() + "";

        firebaseAuth.createUserWithEmailAndPassword(correoStr, pwdStr)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(PantallaCrearCuenta.this, R.string.ok_registro_completado, Toast.LENGTH_LONG).show();

                            //Guardo el usuario en la base de datos
                            guardarUser(firebaseAuth.getCurrentUser().getUid(), correoStr);

                            //Antes de cerrar la actividad inicio sesion con el usuario recien creado
                            firebaseAuth.signInWithEmailAndPassword(correoStr, pwdStr);
                            Log.w("CONEXION", "antessss       "+FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            startActivity(new Intent(PantallaCrearCuenta.this, MainActivity.class));
                            //finish();
                        }else {
                            Toast.makeText(PantallaCrearCuenta.this, R.string.error_registro_no_completado, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }//FIN INICIAR REGISTRO


    /**
     * Este metodo se ejecuta cuando un usuario se registra, permite guardar los datos del uusario en la base de datos
     * @param idUser es el id que se genera automaticamente al registrar un usuario
     */
    public void guardarUser(String idUser, String mail){
        String nickStr = nickET.getText() + "";
        //Creo un objeto con los datos introducidos por el usuario
        Jugador jugador = new Jugador(idUser, mail, nickStr, String.valueOf(listaRoles.getSelectedItem()),"Foto");

        //Creo una referencia a la base de datos
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        //Guardo el uusario en la base de datos
        databaseReference.child("jugadores").child(jugador.getIdJugador()).setValue(jugador);
    }//FIN GUARDAR USER



}
