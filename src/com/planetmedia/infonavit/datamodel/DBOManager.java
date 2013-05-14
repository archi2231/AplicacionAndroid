package com.planetmedia.infonavit.datamodel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBOManager {
	
	private Context context;
	private String DB_PATH; 
	private static final String DB_NAME = "DBInfonavit.sqlite";	
	
	public DBOManager(Context context) {
		this.context = context;
		DB_PATH = context.getPackageName()+"/databases/";		
		
		try {
			if(!checkdatabase())
				copyDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SQLiteDatabase getDB(){
		SQLiteDatabase DB = SQLiteDatabase.openDatabase("/data/data/" + DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
		//DB.execSQL("PRAGMA foreign_keys=ON;");
		return DB;
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
		
		context.openOrCreateDatabase("init", Context.MODE_PRIVATE, null).close();
		
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
    }

}
