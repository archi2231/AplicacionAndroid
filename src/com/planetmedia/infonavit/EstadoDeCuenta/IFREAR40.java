package com.planetmedia.infonavit.EstadoDeCuenta;

public class IFREAR40 extends IFREANormal implements IFR40Generico {

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

	public IFREAR40() {
		super();
	}

	@Override
	public String getDescuentoTasaInteres() {
		return descuentoTasaInteres;
	}

	@Override
	public void setDescuentoTasaInteres(String descuentoTasaInteres) {
		this.descuentoTasaInteres = descuentoTasaInteres;
	}

	@Override
	public String getTasaInteresAnualCobrada() {
		return tasaInteresAnualCobrada;
	}

	@Override
	public void setTasaInteresAnualCobrada(String tasaInteresAnualCobrada) {
		this.tasaInteresAnualCobrada = tasaInteresAnualCobrada;
	}

	@Override
	public String getInteresesGeneradosEnPeriodo() {
		return interesesGeneradosEnPeriodo;
	}

	@Override
	public void setInteresesGeneradosEnPeriodo(
			String interesesGeneradosEnPeriodo) {
		this.interesesGeneradosEnPeriodo = interesesGeneradosEnPeriodo;
	}

	@Override
	public String getInteresesGeneradosEnPeriodoVSM() {
		return interesesGeneradosEnPeriodoVSM;
	}

	@Override
	public void setInteresesGeneradosEnPeriodoVSM(
			String interesesGeneradosEnPeriodoVSM) {
		this.interesesGeneradosEnPeriodoVSM = interesesGeneradosEnPeriodoVSM;
	}

	@Override
	public String getInteresesCubiertosInfonavit() {
		return interesesCubiertosInfonavit;
	}

	@Override
	public void setInteresesCubiertosInfonavit(
			String interesesCubiertosInfonavit) {
		this.interesesCubiertosInfonavit = interesesCubiertosInfonavit;
	}

	@Override
	public String getInteresesCubiertosInfonavitVSM() {
		return interesesCubiertosInfonavitVSM;
	}

	@Override
	public void setInteresesCubiertosInfonavitVSM(
			String interesesCubiertosInfonavitVSM) {
		this.interesesCubiertosInfonavitVSM = interesesCubiertosInfonavitVSM;
	}

	@Override
	public String getInteresesCubiertosAcreditado() {
		return interesesCubiertosAcreditado;
	}

	@Override
	public void setInteresesCubiertosAcreditado(
			String interesesCubiertosAcreditado) {
		this.interesesCubiertosAcreditado = interesesCubiertosAcreditado;
	}

	@Override
	public String getInteresesCubiertosAcreditadoVSM() {
		return interesesCubiertosAcreditadoVSM;
	}

	@Override
	public void setInteresesCubiertosAcreditadoVSM(
			String interesesCubiertosAcreditadoVSM) {
		this.interesesCubiertosAcreditadoVSM = interesesCubiertosAcreditadoVSM;
	}

	@Override
	public String getTasaInteresInicioAplicada1() {
		return tasaInteresInicioAplicada1;
	}

	@Override
	public void setTasaInteresInicioAplicada1(String tasaInteresInicioAplicada1) {
		this.tasaInteresInicioAplicada1 = tasaInteresInicioAplicada1;
	}

	@Override
	public String getTasaInteresFinAplicada1() {
		return tasaInteresFinAplicada1;
	}

	@Override
	public void setTasaInteresFinAplicada1(String tasaInteresFinAplicada1) {
		this.tasaInteresFinAplicada1 = tasaInteresFinAplicada1;
	}

	@Override
	public String getTasaInteresInicioAplicada2() {
		return tasaInteresInicioAplicada2;
	}

	@Override
	public void setTasaInteresInicioAplicada2(String tasaInteresInicioAplicada2) {
		this.tasaInteresInicioAplicada2 = tasaInteresInicioAplicada2;
	}

	@Override
	public String getTasaInteresFinAplicada2() {
		return tasaInteresFinAplicada2;
	}

	@Override
	public void setTasaInteresFinAplicada2(String tasaInteresFinAplicada2) {
		this.tasaInteresFinAplicada2 = tasaInteresFinAplicada2;
	}
}
