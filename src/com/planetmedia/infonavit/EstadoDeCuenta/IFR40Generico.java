package com.planetmedia.infonavit.EstadoDeCuenta;

public interface IFR40Generico {

	public void setDescuentoTasaInteres (String descuentoTasaInteres);
	public void setTasaInteresAnualCobrada (String tasaInteresAnualCobrada);
	public void setInteresesGeneradosEnPeriodo (String intereseGeneradosEnPeriodo);
	public void setInteresesGeneradosEnPeriodoVSM (String intereseGeneradosEnPeriodoVSM);
	public void setInteresesCubiertosInfonavit (String interesesCubiertosInfonavit);
	public void setInteresesCubiertosInfonavitVSM (String interesesCubiertosInfonavitVSM);
	public void setInteresesCubiertosAcreditado (String interesesCubiertosAcreditado);
	public void setInteresesCubiertosAcreditadoVSM (String interesesCubiertosAcreditadoVSM);
	public void setTasaInteresInicioAplicada1 (String tasaInteresInicioAplicada1);
	public void setTasaInteresFinAplicada1 (String tasaInteresFinAplicada1);
	public void setTasaInteresInicioAplicada2 (String tasaInteresInicioAplicada2);
	public void setTasaInteresFinAplicada2 (String tasaInteresFinAplicada2);
	 
	public String getDescuentoTasaInteres ();
	public String getTasaInteresAnualCobrada ();
	public String getInteresesGeneradosEnPeriodo ();
	public String getInteresesGeneradosEnPeriodoVSM ();
	public String getInteresesCubiertosInfonavit ();
	public String getInteresesCubiertosInfonavitVSM ();
	public String getInteresesCubiertosAcreditado ();
	public String getInteresesCubiertosAcreditadoVSM ();
	public String getTasaInteresInicioAplicada1 ();
	public String getTasaInteresFinAplicada1 ();
	public String getTasaInteresInicioAplicada2 ();
	public String getTasaInteresFinAplicada2 ();
		
}
