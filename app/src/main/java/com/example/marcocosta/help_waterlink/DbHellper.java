package com.example.marcocosta.help_waterlink;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
Classe para criação de uma base de dados.
Classname : DbHellper
DatabaseName : myDB.db
TableName : infraestruturas c/id autoincrementável

 */
public class DbHellper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDB.db";
    private static final int VERSION = 1;
    public DbHellper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String s = "CREATE TABLE infraestruturas(_id integer primary key autoincrement, meuID varchar(40), nome varchar(40), tipo varchar(40), areaNeg varchar(40), lat varchar(40), longi varchar(40), observ varchar(200))";
        db.execSQL(s);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //do nothing ;
    }
}