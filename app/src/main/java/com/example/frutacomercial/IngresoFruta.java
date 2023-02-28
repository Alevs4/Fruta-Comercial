package com.example.frutacomercial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class IngresoFruta extends AppCompatActivity {

    EditText Folio, Fecha, Variedad, Kilos, Cajas, Csg;
    Spinner Spn, SpnCategoria, Productor, Turno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_fruta);

        Folio = findViewById(R.id.TxtFolio);
        Productor = findViewById(R.id.SpnExport);
        Fecha = findViewById(R.id.TxtFecha);
        Variedad = findViewById(R.id.txtVariedad);
        Kilos = findViewById(R.id.TxtKilos);
        Cajas = findViewById(R.id.TxtCajas);
        Csg = findViewById(R.id.TxtCsg);
        Spn = findViewById(R.id.SpnBins);
        SpnCategoria = findViewById(R.id.spinCategoria);
        Turno= findViewById(R.id.spTurno);

        String[] turno = {"Selecciona Turno","Turno 1", "Turno 2"};

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, turno);
        Turno.setAdapter(adapter3);

        String[] SiNo = {"Selecciona","Si", "No"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SiNo);
        Spn.setAdapter(adapter);

        String[] Categoria = {"Selecciona","Comercial", "Pre Calibre","Desecho"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Categoria);
        SpnCategoria.setAdapter(adapter1);

        String[] Export = {"Exportadora","Huaquen", "Fruttita","CYD"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Export);
        Productor.setAdapter(adapter2);

    }
    public Connection conexionBD() {
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.201.11;databaseName=Fruta;user=alejandro;password=1976;");

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return conexion;
    }
    public void IngresoFruta(View view) throws Exception{
        AgregarPallets();
    }
    public void Guardar(View view) throws Exception{
        AgregarPallets();
    }

    public void AgregarPallets() throws Exception {


        if (Folio.getText().toString().isEmpty() || Fecha.getText().toString().isEmpty() ||
                Variedad.getText().toString().isEmpty() || Kilos.getText().toString().isEmpty() || Cajas.getText().toString().isEmpty() || Spn.getSelectedItem().toString().isEmpty() ) {
            Toast.makeText(getApplicationContext(), "No debe haber campos vacios", Toast.LENGTH_LONG).show();
        } else {

            try {
                Statement ejecutor = conexionBD().createStatement();
                ResultSet rs = ejecutor.executeQuery("SELECT * FROM FrutaComercial WHERE Folio = '" + Folio.getText().toString() + "' ");

                if (rs.next() == true) {

                    Toast.makeText(getApplicationContext(), "Folio Existe", Toast.LENGTH_LONG).show();
                    Folio.setText("");
                } else {
                    PreparedStatement ps = conexionBD().prepareStatement("insert into FrutaComercial values (?,?,?,?,?,?,?,?,?,?,?)");
                    ps.setString(1, Folio.getText().toString());
                    ps.setString(2, Productor.getSelectedItem().toString());
                    ps.setString(3, Fecha.getText().toString());
                    ps.setString(5, Variedad.getText().toString());
                    ps.setString(4, Kilos.getText().toString());
                    ps.setString(6, Cajas.getText().toString());
                    ps.setString(7, Spn.getSelectedItem().toString());
                    ps.setString(8, "Existencia");
                    ps.setString(9, SpnCategoria.getSelectedItem().toString());
                    ps.setString(10,Csg.getText().toString());
                    ps.setString(11, Turno.getSelectedItem().toString());

                    ps.executeUpdate();
                    Toast.makeText(getApplicationContext(),"Guardado", Toast.LENGTH_LONG).show();
                    limpiar();


                }
            }
            catch(Exception e){
                Toast.makeText(IngresoFruta.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }


    }
    public void limpiar(){
        Folio.setText("");
        Fecha.setText("");
        Variedad.setText("");
        Kilos.setText("");
        Cajas.setText("");
        Csg.setText("");

    }
}