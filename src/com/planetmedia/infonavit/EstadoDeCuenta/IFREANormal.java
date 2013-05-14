package com.planetmedia.infonavit.EstadoDeCuenta;

public class IFREANormal extends IFEstadoDeCuentaGenerico {

	private String fechaLimitePago;
	private String lineaCaptura;

	public IFREANormal() {
		super();
	}

	public void setFechaLimitePago(String fechaLimitePago) {
		this.fechaLimitePago = fechaLimitePago;
	}

	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
	}

	public String getFechaLimitePago() {
		return this.fechaLimitePago;
	}

	public String getLineaCaptura() {
		return this.lineaCaptura;
	}

}
