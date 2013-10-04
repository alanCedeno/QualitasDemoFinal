package com.planetmedia.qualitas.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

public class DataBaseHelper {
	
	private Context context;
	private static String DB_PATH; 
	private static final String DB_NAME = "db_cp";	
	public static int banderaBd=0;
	
	private static SQLiteDatabase db;
	
	public DataBaseHelper(Context context) {
		this.context = context;
		
		if(DB_PATH == null) {
			DB_PATH = context.getPackageName()+"/databases/";		
		}
		
		try {
			if(!checkdatabase())
				copyDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SQLiteDatabase getDB(){
		//SQLiteDatabase DB = SQLiteDatabase.openDatabase("/data/data/" + DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);		
		//DB.execSQL("PRAGMA foreign_keys=ON;");
		if(DB_PATH == null) {
			DB_PATH = "com.ejemplo.pruabedbexterna/databases/";
		}
		if(db == null) {
			db = SQLiteDatabase.openDatabase("/data/data/" + DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
		}
		if(db != null && !db.isOpen()) {
			db = SQLiteDatabase.openDatabase("/data/data/" + DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
		}
		return db;
	}
	
	private boolean checkdatabase() {
		 
        boolean checkdb = false;
        try {
            String myPath = "/data/data/" + DB_PATH + DB_NAME;
            File dbfile = new File(myPath);          
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }
	
	private void copyDatabase() throws IOException {
		
		
		
		context.openOrCreateDatabase("db_cp", Context.MODE_PRIVATE, null).close();
		
        //Open your local db as the input stream
        InputStream myinput = context.getAssets().open(DB_NAME);
        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream("/data/data/" + DB_PATH + DB_NAME);
        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }
        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
        Toast.makeText(context, "listo!", Toast.LENGTH_SHORT);
        banderaBd=1;
    }
}