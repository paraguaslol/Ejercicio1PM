package com.example.victor.ejercicio1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Pantalla2 extends AppCompatActivity implements View.OnClickListener{
    private TextView twIntro;
    private EditText etEdad2;
    private Button btContinuar2,btCancelar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla2);

        getViews();
        getInfoFromIntent();
        setController();
    }

    private void getViews() {
        twIntro = (TextView) findViewById(R.id.twIntro);
        etEdad2 = (EditText) findViewById(R.id.etEdad2);
        btContinuar2 = (Button)findViewById(R.id.btContinuar2);
        btCancelar2 = (Button)findViewById(R.id.btCancelar2);
    }

    private void getInfoFromIntent() {//coje la informacion del main y lo pasa a esta pantalla
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String nombre = (String)extras.get("nombre");
            String sexo = (String)extras.get("sexo");
            setIntro(nombre);
        }
    }

    public void setIntro (String nombre){//rellena el textview de inicio
        twIntro.setText("Hola "+nombre+", indicame los siguientes datos:");
    }

    private void setController() {//listeners
        btContinuar2.setOnClickListener(this);
        btCancelar2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {//metodo para ver si clica en continuar o cancelar
        if (v.getId() == btContinuar2.getId()) {
            if(edadIsOk()) {
                continuar();
                finish();
            }
        } else {
            cancelar();
            finish();
        }
    }

    private boolean edadIsOk() {//metodo para ver si se ha rellenado el campo y si se ha hecho bien con numero
        String edadAux  = etEdad2.getText().toString().trim();
        if (!edadIsEmpty(edadAux)) {
            try {
                int edad = Integer.parseInt(edadAux);
                if (edadIsPositive(edad))
                    return true;
                else
                    Toast.makeText(getBaseContext(), "Introduce un numero positivo", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException nfe){
                Toast.makeText(getBaseContext(), "Introduce un valor numerico", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else
            Toast.makeText(getBaseContext(), "Introduce tu edad", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean edadIsEmpty(String edad) {//comprueba campo vacio
        return edad.length() > 0 ? false : true;
    }

    private boolean edadIsPositive(int edad) {//comprueba si es un numero positivo
        return edad > 0;
    }

    public void continuar() {//obtenemos la info para pasarla al intent y iniciar la otra actividad con esa info
        int edad = Integer.parseInt(etEdad2.getText().toString());
        Intent i = getIntent();
        i.putExtra("edad", edad);
        setResult(RESULT_OK,i);
    }

    public void cancelar() {//cancel
        setResult(RESULT_CANCELED);
    }
}
