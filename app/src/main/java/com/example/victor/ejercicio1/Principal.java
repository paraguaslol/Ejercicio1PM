package com.example.victor.ejercicio1;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{
    private Button btEnviar;
    private EditText etNombre;
    private RadioGroup rgSexo;
    private String sexo;
    private TextView twEdad;
    private RadioButton rbMasculino, rbFemenino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        getViews();
        setController();
    }

    private void getViews() {//guarda los id de las vistas en variables para acceder a ellos
        etNombre = (EditText) findViewById(R.id.etNombre);
        rgSexo = (RadioGroup) findViewById(R.id.rgSexo);
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        rbFemenino = (RadioButton) findViewById(R.id.rbFemenino);
        btEnviar = (Button)findViewById(R.id.btEnviar);
        twEdad = (TextView) findViewById(R.id.twEdad);
    }

    private void setController() {//listeners
        btEnviar.setOnClickListener(this);
        rgSexo.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {//pasamos nombre y sexo de la activity principal a la pantalla 2
        if(sexoIsSelected() && nombreIsOk()){
            Intent intent = new Intent(this, Pantalla2.class);
            String nombre = etNombre.getText().toString();
            intent.putExtra("nombre", nombre);
            intent.putExtra("sexo", this.sexo);
            startActivityForResult(intent,1);
        } else if (!nombreIsOk()) {//captura de errores
            Toast.makeText(getBaseContext(), "Escribe un nombre correcto", Toast.LENGTH_SHORT).show();
        } else if (!sexoIsSelected()) {
            Toast.makeText(getBaseContext(), "Selecciona un sexo", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean nombreIsOk() {//captura que no este vacio el campo
        return etNombre.getText().toString().trim().length() > 0 ? true : false;
    }

    private boolean sexoIsSelected() {//captura que haya una opcion seleccionada
        return rgSexo.getCheckedRadioButtonId() !=  -1 ? true : false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {//mediante el id del radiobutton pasamos un string u otro a la pantalla 2
        int selectedId = group.getCheckedRadioButtonId();
        RadioButton radio = (RadioButton) findViewById(selectedId);
        sexo = (String) radio.getText();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//desabilita las opciones para cuando vuelva al main
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            getInfoFromIntent(data);
            disableOptions();
        }
    }

    private void getInfoFromIntent(Intent intent) {//guarda los datos de la pantalla 2 para mostrarlos en el main con un mensaje
        Bundle extras = intent.getExtras();
        int edad = (int)extras.get("edad");
        setTwEdad(edad);
    }

    public void setTwEdad (int edad){
        this.twEdad.setText(prepareMessage(edad));
    }

    private String prepareMessage(int edad) {//depende del numero mostrará un mensaje u otro
        if (edad>=18 && edad<25) {
            return "Ya eres mayor de edad";
        } else if (edad>=25 && edad<=35){
            return "Estas en la flor de la vida";
        } else if (edad>35){
            return "ai, ai, ai...";
        }
        return "Eres menor de edad";
    }

    private void disableOptions() {//metodo para desactivar las opciones
        rbMasculino.setEnabled(false);
        rbFemenino.setEnabled(false);
        etNombre.setEnabled(false);
        btEnviar.setEnabled(false);
    }
}