package com.planetmedia.infonavit.datamodel.dto;


public class ControlCatalogoDTO {

	private String idControlCatalogo;
	private String versionCatalogo;
	private String marcaActualizacion;
	private String fechaUltimaActualizacion;

	public ControlCatalogoDTO() {

	}

	public String getIdControlCatalogo() {
		return idControlCatalogo;
	}

	public String getVersionCatalogo() {
		return versionCatalogo;
	}

	public String getMarcaActualizacion() {
		return marcaActualizacion;
	}

	public String getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	public void setIdControlCatalogo(String idControlCatalogo) {
		this.idControlCatalogo = idControlCatalogo;
	}

	public void setVersionCatalogo(String versionCatalogo) {
		this.versionCatalogo = versionCatalogo;
	}

	public void setMarcaActualizacion(String marcaActualizacion) {
		this.marcaActualizacion = marcaActualizacion;
	}

	public void setFechaUltimaActualizacion(String fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

}
