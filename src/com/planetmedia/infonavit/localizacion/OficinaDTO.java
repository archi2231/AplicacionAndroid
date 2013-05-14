package com.planetmedia.infonavit.localizacion;

import android.os.Parcel;
import android.os.Parcelable;

public class OficinaDTO implements Parcelable{

	private int idOficina;
	private String idEntidadFederativa;
	private String idPlaza;
	private String tipoOficina;
	private String nombreEstado;
	private String nombreMunicipio;
	private String latitud;
	private String longitud;
	
	public static final Parcelable.Creator<OficinaDTO> CREATOR = new Parcelable.Creator<OficinaDTO>() {
		 public OficinaDTO createFromParcel(Parcel in) {
	            return new OficinaDTO(in);
	        }
	 
	        public OficinaDTO[] newArray(int size) {
	            return new OficinaDTO[size];
	        }
	};

	public OficinaDTO() {
		
	}
	
	public OficinaDTO (Parcel parcel){
		readFromParcel(parcel);
	}
	
	public int getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(int idOficina) {
		this.idOficina = idOficina;
	}

	public String getIdEntidadFederativa() {
		return idEntidadFederativa;
	}

	public void setIdEntidadFederativa(String idEntidadFederativa) {
		this.idEntidadFederativa = idEntidadFederativa;
	}

	public String getIdPlaza() {
		return idPlaza;
	}

	public void setIdPlaza(String idPlaza) {
		this.idPlaza = idPlaza;
	}

	public String getTipoOficina() {
		return tipoOficina;
	}

	public void setTipoOficina(String tipoOficina) {
		this.tipoOficina = tipoOficina;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	
	private void readFromParcel(Parcel parsel){
		idOficina = parsel.readInt();
		idEntidadFederativa = parsel.readString();
		idPlaza = parsel.readString();
		tipoOficina = parsel.readString();
		nombreEstado = parsel.readString();
		nombreMunicipio = parsel.readString();
		latitud = parsel.readString();
		longitud = parsel.readString();
	}

	/**
	 * MŽtodos de Interf‡z Parcelable
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(idOficina);
		dest.writeString(idEntidadFederativa);
		dest.writeString(idPlaza);
		dest.writeString(tipoOficina);
		dest.writeString(nombreEstado);
		dest.writeString(nombreMunicipio);
		dest.writeString(latitud);
		dest.writeString(longitud);
	}

}