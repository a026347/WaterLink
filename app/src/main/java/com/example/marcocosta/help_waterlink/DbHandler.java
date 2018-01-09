package com.example.marcocosta.help_waterlink;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbHandler {
    private DbHellper dbHelper;//call the method DbHellper
    public SQLiteDatabase database; //call the SQLite database

    public DbHandler(Context context) {

        dbHelper = new DbHellper(context.getApplicationContext());
    }

    public DbHandler open() {
        database = dbHelper.getWritableDatabase();//get DB writable and return her values
        return this;
    }

    public void close() {

        dbHelper.close();// method to DB close
    }

    public Cursor obterTodosRegistos(String pesq) { // method to get all values
        String where = "";
        String[] colunas = new String[8];
        colunas[0] = "_id";
        colunas[1] = "meuID";
        colunas[2] = "nome";
        colunas[3] = "tipo";
        colunas[4] = "areaNeg";
        colunas[5] = "lat";
        colunas[6] = "longi";
        colunas[7] = "observ";

        if (!pesq.equals("")) {
            where = "nome like '%" + pesq + "%' ";
        }

        return database.query("infraestruturas", colunas, where,null, null, null, "meuID");
    }
    /*
    Criação de um método para verificação e atualização dos dados nas DB.
    Este método apenas verifica e devolve um contador
     */
    public boolean infrastructureExists(String id) {
        database = dbHelper.getReadableDatabase();
        //String Query = "Select count(meuID) from infraestruturas where meuID = '" + id + "'";
        Cursor cursor = database.query("infraestruturas", null, "meuID = ?", new String[]{
                id
        }, null, null, null);

        return cursor.getCount()>0;
    }

    /*
    Criação do método para inserir dados na DB
     */

    public void insertJasonData2(ArrayList<Infrastructure> varios) {
        for (Infrastructure cada :
                varios) {
            //TODO
            if (infrastructureExists(cada.meuID)==false) {
                // se o cada.meuID já existir na DB passa à frente
                // se não existir faz o insert abaixo
                insertJasonData(cada.meuID, cada.nome, cada.tipo, cada.areaNeg, cada.lat, cada.longi);
            }
        }
    }

    public long insertJasonData(String meuID, String nome, String tipo, String areaNeg, String lat, String longi) {
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("meuID", meuID);
        values.put("nome", nome);
        values.put("tipo", tipo);
        values.put("areaNeg", areaNeg);
        values.put("lat", lat);
        values.put("longi", longi);
        return database.insert("infraestruturas", null, values);
    }

    /*
    #############################################################################
    List Names
    List Num
    List all Items
    #############################################################################
     */


    public List<String> obterTodosNumeros() {
        ArrayList<String> numeros = new ArrayList<String>();
        Cursor cursor = obterTodosRegistos("");
        if (cursor.moveToFirst()) {
            do {
                numeros.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return numeros;
    }

    public HashMap<String,String> obterTodosNomes() {
        HashMap<String,String> nomes = new HashMap<String,String>();
        Cursor cursor = obterTodosRegistos("");
        if (cursor.moveToFirst()) {
            do {
                nomes.put(cursor.getString(1), cursor.getString(2));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return nomes;
    }

    public int obterTodosCampos(List<Integer> osID, List<String> osNomes , List<String> osTipos,List<String> asAreaNegocio, List<String> asLat, List<String> asLongi, List<String> asObserv ) {
        String[] colunas = new String[23];
        colunas[0] = "meuID";
        colunas[1] = "nome";
        colunas[2] = "tipo";
        colunas[3] = "areaNeg";
        colunas[4] = "lat";
        colunas[5] = "longi";
        colunas[6] = "observ";
        Cursor c = database.query("infraestruturas", colunas, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                osID.add(c.getInt(0));
                osNomes.add(c.getString(1));
                osTipos.add(c.getString(2));
                asAreaNegocio.add(c.getString(3));
                asLat.add(c.getString(4));
                asLongi.add(c.getString(5));
                asObserv.add(c.getString(6));

            } while (c.moveToNext());
        }
        c.close();
        return osID.size();
    }





    public void deleteDb (){
        database = dbHelper.getWritableDatabase();
        database.delete ("infraestruturas",null,null);
    }

    public int updateObs(String oId, String observ) {
        database = dbHelper.getWritableDatabase();
        String whereClause = "meuID = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = new String(oId);
        ContentValues values = new ContentValues();
        values.put("observ", observ);
        return database.update("infraestruturas", values, whereClause, whereArgs);
    }
}

