package com.planetmedia.infonavit.EstadoDeCuenta;

public class IFROAR40Reestructura extends IFROANormal implements
		IFReestructuraGenerico, IFR40Generico {

	// Propiedades de R40
	private String descuentoTasaInteres;
	private String tasaInteresAnualCobrada;
	private String interesesGeneradosEnPeriodo;
	private String interesesGeneradosEnPeriodoVSM;
	private String interesesCubiertosInfonavit;
	private String interesesCubiertosInfonavitVSM;
	private String interesesCubiertosAcreditado;
	private String interesesCubiertosAcreditadoVSM;
	private String tasaInteresInicioAplicada1;
	private String tasaInteresFinAplicada1;
	private String tasaInteresInicioAplicada2;
	private String tasaInteresFinAplicada2;

	// Propiedades Reestructura
	private String ultimoPagoInfonavit;
	private String saldoACargoAcreditado;
	private String saldoACargoInfonavit;
	private String saldoActualReestructura;

	private String ultimoPagoInfonavitVSM;
	private String saldoACargoAcreditadoVSM;
	private String saldoACargoInfonavitVSM;
	private String saldoActualReestructuraVSM;

	public IFROAR40Reestructura() {
		super();
	}

	public String getDescuentoTasaInteres() {
		return descuentoTasaInteres;
	}

	public void setDescuentoTasaInteres(String descuentoTasaInteres) {
		this.descuentoTasaInteres = descuentoTasaInteres;
	}

	public String getTasaInteresAnualCobrada() {
		return tasaInteresAnualCobrada;
	}

	public void setTasaInteresAnualCobrada(String tasaInteresAnualCobrada) {
		this.tasaInteresAnualCobrada = tasaInteresAnualCobrada;
	}

	public String getInteresesGeneradosEnPeriodo() {
		return interesesGeneradosEnPeriodo;
	}

	public void setInteresesGeneradosEnPeriodo(
			String interesesGeneradosEnPeriodo) {
		this.interesesGeneradosEnPeriodo = interesesGeneradosEnPeriodo;
	}

	public String getInteresesGeneradosEnPeriodoVSM() {
		return interesesGeneradosEnPeriodoVSM;
	}

	public void setInteresesGeneradosEnPeriodoVSM(
			String interesesGeneradosEnPeriodoVSM) {
		this.interesesGeneradosEnPeriodoVSM = interesesGeneradosEnPeriodoVSM;
	}

	public String getInteresesCubiertosInfonavit() {
		return interesesCubiertosInfonavit;
	}

	public void setInteresesCubiertosInfonavit(
			String interesesCubiertosInfonavit) {
		this.interesesCubiertosInfonavit = interesesCubiertosInfonavit;
	}

	public String getInteresesCubiertosInfonavitVSM() {
		return interesesCubiertosInfonavitVSM;
	}

	public void setInteresesCubiertosInfonavitVSM(
			String interesesCubiertosInfonavitVSM) {
		this.interesesCubiertosInfonavitVSM = interesesCubiertosInfonavitVSM;
	}

	public String getInteresesCubiertosAcreditado() {
		return interesesCubiertosAcreditado;
	}

	public void setInteresesCubiertosAcreditado(
			String interesesCubiertosAcreditado) {
		this.interesesCubiertosAcreditado = interesesCubiertosAcreditado;
	}

	public String getInteresesCubiertosAcreditadoVSM() {
		return interesesCubiertosAcreditadoVSM;
	}

	public void setInteresesCubiertosAcreditadoVSM(
			String interesesCubiertosAcreditadoVSM) {
		this.interesesCubiertosAcreditadoVSM = interesesCubiertosAcreditadoVSM;
	}

	public String getTasaInteresInicioAplicada1() {
		return tasaInteresInicioAplicada1;
	}

	public void setTasaInteresInicioAplicada1(String tasaInteresInicioAplicada1) {
		this.tasaInteresInicioAplicada1 = tasaInteresInicioAplicada1;
	}

	public String getTasaInteresFinAplicada1() {
		return tasaInteresFinAplicada1;
	}

	public void setTasaInteresFinAplicada1(String tasaInteresFinAplicada1) {
		this.tasaInteresFinAplicada1 = tasaInteresFinAplicada1;
	}

	public String getTasaInteresInicioAplicada2() {
		return tasaInteresInicioAplicada2;
	}

	public void setTasaInteresInicioAplicada2(String tasaInteresInicioAplicada2) {
		this.tasaInteresInicioAplicada2 = tasaInteresInicioAplicada2;
	}

	public String getTasaInteresFinAplicada2() {
		return tasaInteresFinAplicada2;
	}

	public void setTasaInteresFinAplicada2(String tasaInteresFinAplicada2) {
		this.tasaInteresFinAplicada2 = tasaInteresFinAplicada2;
	}

	public String getUltimoPagoInfonavit() {
		return ultimoPagoInfonavit;
	}

	public void setUltimoPagoInfonavit(String ultimoPagoInfonavit) {
		this.ultimoPagoInfonavit = ultimoPagoInfonavit;
	}

	public String getSaldoACargoAcreditado() {
		return saldoACargoAcreditado;
	}

	public void setSaldoACargoAcreditado(String saldoACargoAcreditado) {
		this.saldoACargoAcreditado = saldoACargoAcreditado;
	}

	public String getSaldoACargoInfonavit() {
		return saldoACargoInfonavit;
	}

	public void setSaldoACargoInfonavit(String saldoACargoInfonavit) {
		this.saldoACargoInfonavit = saldoACargoInfonavit;
	}

	public String getSaldoActualReestructura() {
		return saldoActualReestructura;
	}

	public void setSaldoActualReestructura(String saldoActualReestructura) {
		this.saldoActualReestructura = saldoActualReestructura;
	}

	public String getUltimoPagoInfonavitVSM() {
		return ultimoPagoInfonavitVSM;
	}

	public void setUltimoPagoInfonavitVSM(String ultimoPagoInfonavitVSM) {
		this.ultimoPagoInfonavitVSM = ultimoPagoInfonavitVSM;
	}

	public String getSaldoACargoAcreditadoVSM() {
		return saldoACargoAcreditadoVSM;
	}

	public void setSaldoACargoAcreditadoVSM(String saldoACargoAcreditadoVSM) {
		this.saldoACargoAcreditadoVSM = saldoACargoAcreditadoVSM;
	}

	public String getSaldoACargoInfonavitVSM() {
		return saldoACargoInfonavitVSM;
	}

	public void setSaldoACargoInfonavitVSM(String saldoACargoInfonavitVSM) {
		this.saldoACargoInfonavitVSM = saldoACargoInfonavitVSM;
	}

	public String getSaldoActualReestructuraVSM() {
		return saldoActualReestructuraVSM;
	}

	public void setSaldoActualReestructuraVSM(String saldoActualReestructuraVSM) {
		this.saldoActualReestructuraVSM = saldoActualReestructuraVSM;
	}

}