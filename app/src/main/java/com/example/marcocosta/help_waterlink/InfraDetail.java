package com.example.marcocosta.help_waterlink;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class InfraDetail extends Activity {

    protected TextView textView;
    EditText obs;
    String meuID, infraNome, infraTipo, areaNeg,lat, longi, observ;
    protected Button button, maps, upload;
    protected WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infra_detail);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent oIntent = getIntent();
        meuID = oIntent.getStringExtra("meuID");
        infraNome = oIntent.getStringExtra("nome");
        infraTipo = oIntent.getStringExtra("tipo");
        areaNeg = oIntent.getStringExtra("areaNeg");
        lat = oIntent.getStringExtra("lat");
        longi = oIntent.getStringExtra("longi");
        observ = oIntent.getStringExtra("observ");


        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.back);
        maps = (Button) findViewById(R.id.googleMaps);
        webView= (WebView) findViewById(R.id.webView);
        obs = (EditText) findViewById(R.id.obs);
        upload = (Button) findViewById(R.id.upload);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://maps.googleapis.com/maps/api/staticmap?center="+lat+","+longi+"&zoom=12&size=512x512" +
                "&maptype=roadmap&markers=color:red%7Clabel:H%7C"+lat + "," + longi+"&sensor=false");


        textView.setText("ID: "+meuID +"\n" + "Nome: " + infraNome + "\n" + "Tipo: " + infraTipo + "\n" + "Area de Negocio: " + areaNeg);

     //*************************
        DbHandler db = new DbHandler(getApplicationContext());
        String output = db.getValues(meuID);
        obs.setText(output);
        //************************


        //upload information
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String observacoes = (obs.getText().toString());
                DbHandler db = new DbHandler(getApplicationContext());
                db.updateObs(meuID,observacoes);

                Toast.makeText(getApplicationContext(),
                        "Os seus dados foram gravados com sucesso!!",
                        Toast.LENGTH_LONG).show();
            }

        });


        //back button declaration
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //goToGoogleMaps button
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("http://maps.google.com/maps?saddr="+lat+","+longi);
                Toast.makeText(InfraDetail.this, "A abrig o GoogleMaps...", Toast.LENGTH_LONG).show();
            }
        });

    }

}


