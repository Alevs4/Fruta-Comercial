package com.example.frutacomercial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.desplegable, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id==R.id.IdIngresarFolios){
            Intent Registro = new Intent(this, IngresoFruta.class);
            startActivity(Registro);

        }else if(id==R.id.idLiberarFolios){
            Intent Liberar = new Intent(this, LiberarFolio.class);
            startActivity(Liberar);
        }else if(id==R.id.IdAtras){
            Intent Atras = new Intent(this, MainActivity.class);
            startActivity(Atras);
        }else if(id==R.id.idConsultarFolio){
        Intent Consultar = new Intent(this, ConsultarFolio.class);
        startActivity(Consultar);
    }
        return super.onOptionsItemSelected(item);
    }

}