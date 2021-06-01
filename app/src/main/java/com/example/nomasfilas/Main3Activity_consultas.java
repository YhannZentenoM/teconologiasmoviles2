package com.example.nomasfilas;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main3Activity_consultas extends AppCompatActivity {

    ListView lv_citas;

    ArrayList<String> listaInformacion;
    ArrayList<Citas> listaCitas;

    String dni_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_consultas);

        lv_citas = (ListView) findViewById(R.id.lv_citas);

        dni_usuario = getIntent().getStringExtra("usuario");

        AdminSQLiteOpenHelper2 admin = new AdminSQLiteOpenHelper2(this, "consultas",
                null,1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        Citas citas = null;
        listaCitas = new ArrayList<Citas>();

        Cursor file = BaseDeDatabase.rawQuery(
                "select idusuario, consultorio, medico, fecha, hora from citas where idusuario = " + dni_usuario,
                null);

        if(file.moveToFirst()){
            while(file.moveToNext()){
                citas = new Citas();
                citas.setIdusuario(file.getInt(0));
                citas.setConsultorio(file.getString(1));
                citas.setMedico(file.getString(2));
                citas.setFecha(file.getString(3));
                citas.setHora(file.getString(4));

                listaCitas.add(citas);
            }

            listaInformacion = new ArrayList<String>();
            for (int i=0; i<listaCitas.size();i++){
                listaInformacion.add( "Consultorio: "+ listaCitas.get(i).getConsultorio()
                        +" - Médico: "+ listaCitas.get(i).getMedico()
                        + "\n" + listaCitas.get(i).getFecha()
                        + "-  " + listaCitas.get(i).getHora());
            }

            ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
            lv_citas.setAdapter(adaptador);
        }else{
            Toast.makeText(this, "No existe el artículo", Toast.LENGTH_SHORT).show();
            BaseDeDatabase.close();
        }
    }
}