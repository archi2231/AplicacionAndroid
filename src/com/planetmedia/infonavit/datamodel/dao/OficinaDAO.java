package com.planetmedia.infonavit.datamodel.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.planetmedia.infonavit.localizacion.OficinaDTO;

public class OficinaDAO extends GenericDAO {

	public OficinaDAO() {
		super();
		this.setNombreTabla("OFICINA");
	}

	public List<OficinaDTO> getRegistros() {

		List<OficinaDTO> lista = new ArrayList<OficinaDTO>();

		Cursor cursor = dataBase.rawQuery("SELECT * FROM " + getNombreTabla(),
				null);
		if (cursor.moveToFirst()) {
			do {

				OficinaDTO oficina = new OficinaDTO();

				oficina.setIdOficina(cursor.getInt(cursor.getColumnIndex("idOficina")));
				oficina.setIdEntidadFederativa(cursor.getString(cursor.getColumnIndex("idEntidadFederativa")));
				oficina.setIdPlaza(cursor.getString(cursor.getColumnIndex("idPlaza")));
				oficina.setTipoOficina(cursor.getString(cursor.getColumnIndex("tipoOficina")));
				oficina.setNombreEstado(cursor.getString(cursor.getColumnIndex("nombreEstado")));
				oficina.setNombreMunicipio(cursor.getString(cursor.getColumnIndex("nombreMunicipio")));
				oficina.setLatitud(cursor.getString(cursor.getColumnIndex("latitud")));
				oficina.setLongitud(cursor.getString(cursor.getColumnIndex("longitud")));

				lista.add(oficina);
				oficina = null;

			} while (cursor.moveToNext());
		}

		return lista;

	}

	public OficinaDTO getRegistro(int idRegistro) {

		OficinaDTO oficina = null;

		Cursor cursor = dataBase.rawQuery("SELECT * FROM " + getNombreTabla() + " WHERE idOficina = " + idRegistro,
				null);

		if (cursor.moveToFirst()) {
			do {
				
				oficina = new OficinaDTO();
				
				oficina.setIdOficina(cursor.getInt(cursor.getColumnIndex("idOficina")));
				oficina.setIdEntidadFederativa(cursor.getString(cursor.getColumnIndex("idEntidadFederativa")));
				oficina.setIdPlaza(cursor.getString(cursor.getColumnIndex("idPlaza")));
				oficina.setTipoOficina(cursor.getString(cursor.getColumnIndex("tipoOficina")));
				oficina.setNombreEstado(cursor.getString(cursor.getColumnIndex("nombreEstado")));
				oficina.setNombreMunicipio(cursor.getString(cursor.getColumnIndex("nombreMunicipio")));
				oficina.setLatitud(cursor.getString(cursor.getColumnIndex("latitud")));
				oficina.setLongitud(cursor.getString(cursor.getColumnIndex("longitud")));

			} while (cursor.moveToNext());
		}

		return oficina;
	}

	public void insertaRegistro(OficinaDTO oficina) {

		try {
			String query = "INSERT INTO " + getNombreTabla() + " (idEntidadFederativa, idPlaza, tipoOficina, latitud, longitud, nombreEstado, nombreMunicipio)" +
					" VALUES (\""+ oficina.getIdEntidadFederativa() + "\",\""+ oficina.getIdPlaza() + "\",\""+ oficina.getTipoOficina() + "\",\""+ oficina.getLatitud() + "\",\""+ oficina.getLongitud() + "\",\""+ oficina.getNombreEstado() + "\",\""+ oficina.getNombreMunicipio() + "\")";
			dataBase.execSQL(query);
		} catch (Exception e) {
			Log.e("Error en inserci—n: ", e.getMessage());
		}

	}

	public void borraRegistro(long id) {

		int code = dataBase.delete(getNombreTabla(), null, null);

		Log.i("Eliminaci—n Oficina", "Code: " + code);
	}
}
