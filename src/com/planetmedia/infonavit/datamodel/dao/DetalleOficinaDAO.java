package com.planetmedia.infonavit.datamodel.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.planetmedia.infonavit.datamodel.dto.DetalleOficinaDTO;

public class DetalleOficinaDAO extends GenericDAO {

	public DetalleOficinaDAO() {
		super();
		this.setNombreTabla("DETALLE_OFICINA");
	}

	public List<DetalleOficinaDTO> getRegistros() {

		List<DetalleOficinaDTO> lista = new ArrayList<DetalleOficinaDTO>();

		Cursor cursor = dataBase.rawQuery("SELECT * FROM " + getNombreTabla(),
				null);
		if (cursor.moveToFirst()) {
			do {

				DetalleOficinaDTO detalle = new DetalleOficinaDTO();
				
				detalle.setIdDetalleOficina(cursor.getInt(cursor.getColumnIndex("idDetalleOficina")));
				detalle.setIdOficina(cursor.getInt(cursor.getColumnIndex("idOficina")));
				detalle.setCalle(cursor.getString(cursor.getColumnIndex("calle")));
				detalle.setNumExt(cursor.getString(cursor.getColumnIndex("numExt")));
				detalle.setNumInt(cursor.getString(cursor.getColumnIndex("numInt")));
				detalle.setColonia(cursor.getString(cursor.getColumnIndex("colonia")));
				detalle.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
				detalle.setEntreCalle(cursor.getString(cursor.getColumnIndex("entreCalle")));
				detalle.setyCalle(cursor.getString(cursor.getColumnIndex("yCalle")));
				detalle.setReferencia(cursor.getString(cursor.getColumnIndex("referencia")));
				detalle.setCodigoPostal(cursor.getString(cursor.getColumnIndex("codigoPostal")));
				detalle.setLada(cursor.getString(cursor.getColumnIndex("lada")));
				detalle.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
				detalle.setExtension(cursor.getString(cursor.getColumnIndex("extension")));
				detalle.setHorario(cursor.getString(cursor.getColumnIndex("horario")));
				
				lista.add(detalle);
				detalle = null;

			} while (cursor.moveToNext());
		}

		return lista;

	}

	public DetalleOficinaDTO getRegistroPorOficina(int idOficina) {

		DetalleOficinaDTO detalle = null;

		Cursor cursor = dataBase.rawQuery("SELECT * FROM " + getNombreTabla() + " WHERE idOficina = " + idOficina,
				null);

		if (cursor.moveToFirst()) {
			do {
				
				detalle = new DetalleOficinaDTO();
				
				detalle.setIdDetalleOficina(cursor.getInt(cursor.getColumnIndex("idDetalleOficina")));
				detalle.setIdOficina(cursor.getInt(cursor.getColumnIndex("idOficina")));
				detalle.setCalle(cursor.getString(cursor.getColumnIndex("calle")));
				detalle.setNumExt(cursor.getString(cursor.getColumnIndex("numExt")));
				detalle.setNumInt(cursor.getString(cursor.getColumnIndex("numInt")));
				detalle.setColonia(cursor.getString(cursor.getColumnIndex("colonia")));
				detalle.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
				detalle.setEntreCalle(cursor.getString(cursor.getColumnIndex("entreCalle")));
				detalle.setyCalle(cursor.getString(cursor.getColumnIndex("yCalle")));
				detalle.setReferencia(cursor.getString(cursor.getColumnIndex("referencia")));
				detalle.setCodigoPostal(cursor.getString(cursor.getColumnIndex("codigoPostal")));
				detalle.setLada(cursor.getString(cursor.getColumnIndex("lada")));
				detalle.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));
				detalle.setExtension(cursor.getString(cursor.getColumnIndex("extension")));
				detalle.setHorario(cursor.getString(cursor.getColumnIndex("horario")));

			} while (cursor.moveToNext());
		}

		return detalle;
	}

	public void insertaRegistro(DetalleOficinaDTO detalle) {

		try {
			String query = "INSERT INTO " + getNombreTabla() + 
					" (idOficina, calle, numExt, numInt, colonia, municipio, entreCalle, yCalle, referencia, codigoPostal, lada, telefono, extension, horario) VALUES " + 
					"(" + detalle.getIdOficina() + ",\"" + detalle.getCalle() + "\",\"" + detalle.getNumExt() + "\",\"" + detalle.getNumInt() + "\",\"" + detalle.getColonia() + "\",\"" + detalle.getMunicipio() + "\",\"" + detalle.getEntreCalle() + "\",\"" + detalle.getyCalle() + 
					"\",\"" + detalle.getReferencia() + "\",\"" + detalle.getCodigoPostal() + "\",\"" + detalle.getLada() + "\",\"" + detalle.getTelefono() + "\",\"" + detalle.getExtension() + "\",\"" + detalle.getHorario() + "\")";
			dataBase.execSQL(query);
		} catch (Exception e) {
			Log.e("Error en inserci—n: ", e.getMessage());
		}

	}

	public void borraRegistro(long id) {

		int code = dataBase.delete(getNombreTabla(), null, null);

		Log.i("Eliminaci—n Detalle Oficina", "Code: " + code);
	}
}
