package com.example.nomasfilas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_dni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_dni = (EditText)findViewById(R.id.et_dni);
    }
    public void Registrar(View view) {
        AdminSQLiteOpenHelper admin= new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String dni = et_dni.getText().toString();
        if(!dni.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("dni", dni);
            registro.put("nombres", "Usuario Prueba");
            BaseDeDatos.insert("usuarios", null, registro);
            BaseDeDatos.close();
            et_dni.setText("");
            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Debera llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion",
                null,1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();
        String dni = et_dni.getText().toString();
        if(!dni.isEmpty()){
            Cursor file = BaseDeDatabase.rawQuery(
                    "select dni,nombres from usuarios where dni = " + dni,
                    null);
            if(file.moveToFirst()){
                String temp_nombres = file.getString(1);
                String temp_dni = file.getString(0);
                /*BaseDeDatabase.close();*/
                Intent intent = new Intent(this, Main2Activity_registro.class);
                intent.putExtra("usuario", temp_dni);
                intent.putExtra("nombres", temp_nombres);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "No existe el artículo", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }
        }else{
            Toast.makeText(this, "Debes introducir el código del artículo", Toast.LENGTH_SHORT).show();
        }
    }
}