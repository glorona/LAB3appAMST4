package com.example.amstapiapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class nuevos_datos extends AppCompatActivity {

    private RequestQueue mQueue = null;
    private String token = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevos_datos);
        Bundle extras = getIntent().getExtras();
        token = extras.getString("access");
    }

    public void agregarDatosBefore(View v){
        final EditText temp = (EditText) findViewById(R.id.edtTemp);
        final EditText peso = (EditText) findViewById(R.id.edtPeso);
        final EditText hum = (EditText) findViewById(R.id.edtHumedad);
        String str_temp = temp.getText().toString();
        String str_peso = peso.getText().toString();
        String str_hum = hum.getText().toString();

        agregarData(str_temp, str_peso, str_hum);
    }

    private void agregarData(String temperatura, String peso, String humedad){
        Map<String, String> params = new HashMap();
        params.put("temperatura", temperatura);
        params.put("peso", peso);
        params.put("humedad",humedad);
        JSONObject parametros = new JSONObject(params);

        String add_url = " https://amst-lab-api.herokuapp.com/api/sensores/";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, add_url, parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            AlertDialog alertDialog = new AlertDialog.Builder(nuevos_datos.this).create();
                            alertDialog.setTitle("Alerta");
                            alertDialog.setMessage("Se ha realizado la subida de los datos!");
                            alertDialog.show();
                            Intent menuPrincipal = new Intent(getBaseContext(), menu.class);
                            menuPrincipal.putExtra("access", token);
                            startActivity(menuPrincipal);



                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog alertDialog = new AlertDialog.Builder(nuevos_datos.this).create();
                alertDialog.setTitle("Alerta");
                alertDialog.setMessage("No se pudo completar el request");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        mQueue.add(request);
    }
}