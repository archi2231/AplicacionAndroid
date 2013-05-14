package com.planetmedia.infonavit.datamodel.dao;

import android.database.sqlite.SQLiteDatabase;

import com.planetmedia.infonavit.MainActivity;

public class GenericDAO {
	
	protected static SQLiteDatabase dataBase;
	private String nombreTabla;
	
	public GenericDAO() {
		super();
		if (dataBase == null) {
			dataBase = MainActivity.dbInfonavit; 
		}
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

}
