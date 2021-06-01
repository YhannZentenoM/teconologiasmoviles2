package com.example.nomasfilas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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

public class Main2Activity_registro extends AppCompatActivity {
    private TextView nom_usuario, tv_hora_cita, tv_fecha_cita;
    private Spinner sp_consultorio, sp_medicos;

    String nombre_usuario, dni_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_registro);

        nom_usuario = (TextView) findViewById(R.id.tv_usuario);
        sp_consultorio = (Spinner) findViewById(R.id.sp_consultorios);
        sp_medicos = (Spinner) findViewById(R.id.sp_medicos);
        tv_fecha_cita = (TextView) findViewById(R.id.tv_fecha);
        tv_hora_cita = (TextView) findViewById(R.id.tv_hora);

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

        nombre_usuario = getIntent().getStringExtra("nombres");
        nom_usuario.setText(nombre_usuario);

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
    }

    public void abrirCalendario(View view){
        Calendar cal = Calendar.getInstance();
        int anio = cal.get(Calendar.YEAR);
        int mes = cal.get(Calendar.MONTH);
        int dia = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(Main2Activity_registro.this, new DatePickerDialog.OnDateSetListener() {
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

        TimePickerDialog tpd = new TimePickerDialog(Main2Activity_registro.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora_final = "Hora: " + hourOfDay + ":" + minute;
                tv_hora_cita.setText(hora_final);
            }
        }, hora, min, false);
        tpd.show();
    }

    public void Registrar(View view) {
        AdminSQLiteOpenHelper2 admin= new AdminSQLiteOpenHelper2(this, "consultas", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String idusuario = getIntent().getStringExtra("usuario");
        String consultorio = sp_consultorio.getSelectedItem().toString();
        String medico = sp_medicos.getSelectedItem().toString();
        String fecha = tv_fecha_cita.getText().toString();
        String hora = tv_hora_cita.getText().toString();

        if(consultorio != "Seleccione Consultorio" && medico != "Seleccione Medico" && !fecha.isEmpty() && !hora.isEmpty()){

            ContentValues registro = new ContentValues();
            registro.put("idusuario", idusuario);
            registro.put("consultorio", consultorio);
            registro.put("medico", medico);
            registro.put("fecha", fecha);
            registro.put("hora", hora);

            BaseDeDatos.insert("citas", null, registro);
            BaseDeDatos.close();

            sp_medicos.setSelection(0);
            sp_consultorio.setSelection(0);
            tv_fecha_cita.setText("");
            tv_hora_cita.setText("");

            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Debera llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void verCitas(View view){
        String idusuario = getIntent().getStringExtra("usuario");
        Intent intent = new Intent(this, Main3Activity_consultas.class);
        intent.putExtra("usuario", idusuario);
        startActivity(intent);
        finish();
    }
}