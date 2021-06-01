package com.example.nomasfilas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity_detalle extends AppCompatActivity {

    private String cita, consul, fecha;
    TextView consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detalle);

        consulta = (TextView) findViewById(R.id.tv_det_consultorio);

        cita = getIntent().getStringExtra("detalle");


        consulta.setText(cita);
    }

    public void Eliminar(View view){
        consul = getIntent().getStringExtra("data_consul");
        fecha = getIntent().getStringExtra("data_fecha");

        AdminSQLiteOpenHelper2 admin = new AdminSQLiteOpenHelper2
                (this, "consultas", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        if(!consul.isEmpty()){
            int cantidad = BaseDatabase.delete("citas","consultorio = '" + consul +"' and fecha = '" + fecha + "'",null);
            BaseDatabase.close();

            if(cantidad == 1){
                Toast.makeText(this,"Cita eliminada exitosamente",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity_detalle.this, Main2Activity_registro.class);
                startActivity(intent);
            }else{
                Toast.makeText(this,"El Artículo no existe", Toast.LENGTH_SHORT).show(); }
        }else{
            Toast.makeText(this, "Debes introducir el código del artículo",
                    Toast.LENGTH_SHORT).show(); }
    }

    public void Modificar(View view){
        consul = getIntent().getStringExtra("data_consul");
        fecha = getIntent().getStringExtra("data_fecha");
        Intent intent = new Intent(MainActivity_detalle.this, MainActivity_modificar.class);
        intent.putExtra("data_consul", consul);
        intent.putExtra("data_fecha", fecha);
        startActivity(intent);
    }
}