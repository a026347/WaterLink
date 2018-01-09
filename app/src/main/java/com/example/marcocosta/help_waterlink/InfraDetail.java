package com.example.marcocosta.help_waterlink;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

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
        button = (Button) findViewById(R.id.back) ;
        maps = (Button) findViewById(R.id.googleMaps);
        webView= (WebView) findViewById(R.id.webView);
        obs = (EditText) findViewById(R.id.obs);
        upload = (Button) findViewById(R.id.upload);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://maps.googleapis.com/maps/api/staticmap?center="+lat+","+longi+"&zoom=12&size=512x512" +
                "&maptype=roadmap&markers=color:red%7Clabel:H%7C"+lat + "," + longi+"&sensor=false");


        textView.setText("ID: "+meuID +"\n" + "Nome: " + infraNome + "\n" + "Tipo: " + infraTipo + "\n" + "Area de Negocio: " + areaNeg);

        obs.setText(observ);


        //upload information
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String observacoes = (obs.getText().toString());
                DbHandler db = new DbHandler(getApplicationContext());
                db.updateObs(meuID,observacoes);

                Toast.makeText(getApplicationContext(),
                        "Your data are sent!!",
                        Toast.LENGTH_LONG).show();
            }

        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl("http://maps.google.com/maps?saddr="+lat+","+longi);
            }
        });

    }

}


