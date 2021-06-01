package com.example.nomasfilas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity_modificar extends AppCompatActivity {

    private TextView tv_hora_cita, tv_fecha_cita;
    private Spinner sp_consultorio, sp_medicos;
    String fecha_data, consul_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_modificar);

        sp_consultorio = (Spinner) findViewById(R.id.sp_edit_consul);
        sp_medicos = (Spinner) findViewById(R.id.sp_edit_med);
        tv_fecha_cita = (TextView) findViewById(R.id.tv_edit_fecha);
        tv_hora_cita = (TextView) findViewById(R.id.tv_edit_hora);

        fecha_data = getIntent().getStringExtra("data_fecha");
        consul_data = getIntent().getStringExtra("data_consul");

        ArrayList<String> comboConsultorio = new ArrayList<>();
        comboConsultorio.add("Seleccione Consultorio");
        comboConsultorio.add("Pediatria");
        comboConsultorio.add("Otorrino");
        comboConsultorio.add("Traumatologia");
        comboConsultorio.add("Medicina");
        comboConsultorio.add("Dental");

        ArrayList<String> comboMedicoList = new ArrayList<>();
        comboMedicoList.add("Seleccione Medico");
        comboMedicoList.add("Juan Perez");
        comboMedicoList.add("Lucho Suarez");
        comboMedicoList.add("Virna Flores");
        comboMedicoList.add("Melisa Ayllon");
        comboMedicoList.add("Joel Medina");

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, comboConsultorio);
        sp_consultorio.setAdapter(adapter);
        sp_consultorio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, comboMedicoList);
        sp_medicos.setAdapter(adapter2);
        sp_medicos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AdminSQLiteOpenHelper2 admin = new AdminSQLiteOpenHelper2(this, "consultas",
                null,1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();
        Cursor file = BaseDeDatabase.rawQuery(
                "select idusuario, consultorio, medico, fecha, hora from citas where consultorio = '" + consul_data + "' and fecha = '" + fecha_data +"'",
                null);
        if(file.moveToFirst()){
            String consultorio_d = file.getString(1);
            String medico_d = file.getString(2);
            String fecha_d = file.getString(3);
            String hora_d = file.getString(4);
            sp_consultorio.setSelection(obtenerPosicionConsul(sp_consultorio,consultorio_d));
            sp_medicos.setSelection(obtenerPosicionConsul(sp_medicos,medico_d));
            tv_fecha_cita.setText(fecha_d);
            tv_hora_cita.setText(hora_d);
            BaseDeDatabase.close();
        }
    }

    public void abrirCalendario(View view){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(MainActivity_modificar.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = "Fecha: " + dayOfMonth + "/" + month + "/" + year;
                tv_fecha_cita.setText(fecha);
            }
        },anio,mes,dia);
        dpd.show();
    }

    public void abrirCalendarioHora(View view){
        Calendar cal = Calendar.getInstance();
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);

        TimePickerDialog tpd = new TimePickerDialog(MainActivity_modificar.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora_final = "Hora: " + hourOfDay + ":" + minute;
                tv_hora_cita.setText(hora_final);
            }
        }, hora, min, false);
        tpd.show();
    }

    public static int obtenerPosicionConsul(Spinner spinner, String data) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(data)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }

    public void Modificar(View view){
        fecha_data = getIntent().getStringExtra("data_fecha");
        consul_data = getIntent().getStringExtra("data_consul");

        AdminSQLiteOpenHelper2 admin = new AdminSQLiteOpenHelper2
                (this, "consultas", null,1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String consultorio = sp_consultorio.getSelectedItem().toString();
        String medico = sp_medicos.getSelectedItem().toString();
        String fecha = tv_fecha_cita.getText().toString();
        String hora = tv_hora_cita.getText().toString();

        if(consultorio != "Seleccione Consultorio" && medico != "Seleccione Medico" && !fecha.isEmpty() && !hora.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("consultorio", consultorio);
            registro.put("medico", medico);
            registro.put("fecha", fecha);
            registro.put("hora", hora);

            int cantidad = BaseDatabase.update("citas", registro, "consultorio='"+consul_data+"' and fecha ='"+fecha_data+"'", null);
            BaseDatabase.close();
            if(cantidad == 1){
                Toast.makeText(this, "Cita modificada correctamente",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Main2Activity_registro.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "El artículo no existe", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes llenar todos los campos",
                    Toast.LENGTH_SHORT).show();
        }
    }
}