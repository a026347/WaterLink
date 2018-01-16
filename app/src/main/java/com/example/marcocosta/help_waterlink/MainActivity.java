package com.example.marcocosta.help_waterlink;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    private  TextView search;
    private Button sync;
    SimpleCursorAdapter adapter;
    DbHandler d;
    ArrayList<Infrastructure> infrastructureList;
    HashMap<String,String> osNomes;
    ArrayList<String> osID;

    // declaração do método para mudar Atividade
    private void mudarActivity (Class<?> subActividade, String oID, String nome, String tipo, String areaNeg, String lat, String longi, String observ) {
        Intent x = new Intent(this, subActividade);
        x.putExtra("meuID", oID);
        x.putExtra( "nome", nome);
        x.putExtra("tipo", tipo);
        x.putExtra("areaNeg", areaNeg);
        x.putExtra("lat", lat);
        x.putExtra("longi", longi);
        x.putExtra("observ", observ);
        startActivity(x);
    }

    private void preencherLista(String pesquisa) {
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, d.obterTodosRegistos(pesquisa),
                new String[]{"nome"}, new int[]{android.R.id.text1});
        if (!pesquisa.equals("")) {
            lv.setAdapter(adapter);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        infrastructureList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);
        sync=(Button) findViewById(R.id.sync);
        search = (TextView) findViewById(R.id.search);
        d = new DbHandler(this);
        d = d.open();
        new GetContacts().execute();
        //d.deleteDb();

        lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        lv.setStackFromBottom(true);

        /*
        action Syn button
         */

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d = new DbHandler(getApplicationContext());
                d = d.open();
                new GetContacts().execute();
                Toast.makeText(MainActivity.this, "Os seus dados foram atualizados com sucesso!!", Toast.LENGTH_LONG).show();
            }
        });
        /*
        Declaração da SearchBox para pesquisa inteligente da infraestrutura
         */
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int
                    count) {
                preencherLista(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /*
        Declaração de um arrayList de hashMap que recebe a relação ID+Nome
         */
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();// declaração de um arrayList de hashMaps
        osNomes = d.obterTodosNomes();//Declaração do hashMap
        preencherLista("");
        lv.setAdapter(adapter);

        //Toast que apresenta o total de infraestruturas carregadas na DB
        Toast.makeText(MainActivity.this, "Total de Infraestruturas: " + lv.getAdapter().getCount(), Toast.LENGTH_LONG).show();

        /*
        setOnItemListClickListner que vai passar para a outra view os dados relativos a infraestututra selecionada
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor item = (Cursor) adapterView.getItemAtPosition(i);
                //String idInfra = item.getString(0);
                String meuID = item.getString(1);
                String infraNome = item.getString(2);
                String infraTipo = item.getString(3);
                String areaNeg = item.getString(4);
                String lat = item.getString(5);
                String longi = item.getString(6);
                String observ = item.getString(7);
                Toast.makeText(MainActivity.this, "Selecionou a infraestrutura " + infraNome, Toast.LENGTH_LONG).show();
                mudarActivity(InfraDetail.class, meuID, infraNome, infraTipo, areaNeg, lat, longi,observ);
            }
        });

    }

    /*###############################################################################################################
    Declaração da classe privada AsyncTask para ativação da Thread em background que irá estabelecer e
    importar o ficheiro Json das infraestruturas
    #################################################################################################################
     */
    //AsyncTask to web connection and get Json Data
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Os dados estão a ser carregados...",Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this,"Por favor aguarde...",Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://waterlink.addp.pt/infraestrutura/getinfraestruturas";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray listaNomes = new JSONArray(jsonStr);
                    // looping through All Contacts
                    for (int i = 0; i < listaNomes.length(); i++) {
                        JSONObject c = listaNomes.getJSONObject(i);
                        String meuID = c.getString("I_Codigo");
                        String nome = c.getString("I_DesignacaoSAP");
                        String tipo = c.getString("I_TipoInfraestrutura_Designacao");
                        String areaNeg = c.getString("I_AreaNegocio_Designacao");
                        String lat = c.getString("I_Latitude");
                        String longi = c.getString("I_Longitude");

                        Infrastructure novaInfraestrutura = new Infrastructure();

                        novaInfraestrutura.meuID = meuID;
                        novaInfraestrutura.nome = nome;
                        novaInfraestrutura.tipo = tipo;
                        novaInfraestrutura.areaNeg=areaNeg;
                        novaInfraestrutura.lat=lat;
                        novaInfraestrutura.longi=longi;

                        // adding contact to contact list
                        infrastructureList.add(novaInfraestrutura);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        /*
        #######################################################################################################
         */

        @Override
        protected void onPostExecute(Void result) {
            DbHandler hDB = new DbHandler(getApplicationContext());
            hDB.insertJasonData2(infrastructureList);
        }
    }

}