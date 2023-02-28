package com.example.frutacomercial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText Usu, Pass;
    Button Ingreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Usu = findViewById(R.id.TxtUsuario);
        Pass = findViewById(R.id.TxtPass);
        Ingreso = findViewById(R.id.BtnIngreso);
    }
    public void Ingreso(View view){
        LoginFrio();
    }


     //Conexion a base de datos SqlServer

    public Connection conexionBD(){
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.201.11;databaseName=Fruta;user=alejandro;password=1976;");

        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return conexion;
    }
    // Generamos Validacion de Usuario

    public Integer LoginFrio(){
        Integer resultado = 0;
        try {
            Statement ejecutor = conexionBD().createStatement();
            ResultSet rs = ejecutor.executeQuery("SELECT * FROM Usuarios WHERE usuario = '"+Usu.getText().toString()+"' and clave = '"+Pass.getText().toString()+"' ");

            if(rs.next()){
                Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG).show();
                resultado = 1;
                Intent siguiente = new Intent(this, Inicio.class);
                startActivity(siguiente);
            }else{
                Toast.makeText(getApplicationContext(),"Problemas Con el Usuario o Contraseña",Toast.LENGTH_LONG).show();
                resultado = 0;
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Error de base de Datos",Toast.LENGTH_LONG).show();
        }
        return resultado;
    }

    // Generamos metodo de control de salida de la app

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("¿Desea Salir de la App?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent=new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }
}