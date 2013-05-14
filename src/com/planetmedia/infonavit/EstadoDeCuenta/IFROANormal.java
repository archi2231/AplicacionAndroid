package com.planetmedia.infonavit.EstadoDeCuenta;

public class IFROANormal extends IFEstadoDeCuentaGenerico {

	private String pagoDeTuPatron;
	private String pagoDeTuPatronVSM;

	public IFROANormal() {
		super();
	}

	public void setpagoDeTuPatron(String pagoDeTuPatron) {
		this.pagoDeTuPatron = pagoDeTuPatron;
	}

	public String getpagoDeTuPatron() {
		return this.pagoDeTuPatron;
	}

	public String getPagoDeTuPatronVSM() {
		return pagoDeTuPatronVSM;
	}

	public void setPagoDeTuPatronVSM(String pagoDeTuPatronVSM) {
		this.pagoDeTuPatronVSM = pagoDeTuPatronVSM;
	}

}
